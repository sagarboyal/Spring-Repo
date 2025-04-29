package com.main.app.serviceImpl;

import com.main.app.model.Note;
import com.main.app.repository.NoteRepository;
import com.main.app.service.AuditLogService;
import com.main.app.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private AuditLogService auditLogService;

    @Override
    public Note createNoteForUser(String username, String content) {
        Note note = new Note();
        note.setContent(content);
        note.setOwnerUsername(username);

        Note savedNote = noteRepository.save(note);
        auditLogService.logNoteCreation(username, savedNote);

        return savedNote;
    }

    @Override
    public Note updateNoteForUser(Long noteId, String content, String username) {
        Note note = noteRepository.findById(noteId).orElseThrow(()
                -> new RuntimeException("Note not found"));
        note.setContent(content);

        Note savedNote = noteRepository.save(note);
        auditLogService.logNoteUpdation(username, savedNote);

        return savedNote;
    }

    @Override
    public void deleteNoteForUser(Long noteId, String username) {
        noteRepository.deleteById(noteId);
        auditLogService.logNoteDeletion(username, noteId);
    }

    @Override
    public List<Note> getNotesForUser(String username) {
        return noteRepository
                .findByOwnerUsername(username);
    }
}
