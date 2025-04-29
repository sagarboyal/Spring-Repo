package com.main.app.service;

import com.main.app.model.AuditLog;
import com.main.app.model.Note;

import java.util.List;

public interface AuditLogService {
    public void logNoteCreation(String username, Note note);
    public void logNoteUpdation(String username, Note note);
    public void logNoteDeletion(String username, Long noteId);
    public List<AuditLog> getAllAuditLogs();
    public List<AuditLog> getNoteAuditLogs(Long noteId);
}
