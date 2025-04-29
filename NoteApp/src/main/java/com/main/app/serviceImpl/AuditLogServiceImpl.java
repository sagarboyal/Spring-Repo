package com.main.app.serviceImpl;

import com.main.app.model.AuditLog;
import com.main.app.model.Note;
import com.main.app.repository.AuditLogRepository;
import com.main.app.service.AuditLogService;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {
    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void logNoteCreation(String username, Note note) {
        AuditLog auditLog = AuditLog.builder()
                .ip(getIp())
                .action("CREATED")
                .username(username)
                .noteId(note.getId())
                .noteContent(note.getContent())
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
    }

    @Override
    public void logNoteUpdation(String username, Note note) {
        AuditLog auditLog = AuditLog.builder()
                .ip(getIp())
                .action("UPDATED")
                .username(username)
                .noteId(note.getId())
                .noteContent(note.getContent())
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
    }

    @Override
    public void logNoteDeletion(String username, Long noteId) {
        AuditLog auditLog = AuditLog.builder()
                .ip(getIp())
                .action("DELETED")
                .username(username)
                .noteId(noteId)
                .noteContent("")
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
    }

    @Override
    public List<AuditLog> getAllAuditLogs() {
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        if (auditLogs.isEmpty()) {
            throw new RuntimeException("No audit logs found");
        }
        return auditLogs;
    }

    @Override
    public List<AuditLog> getNoteAuditLogs(Long noteId) {
        List<AuditLog> auditLogs = auditLogRepository.findByNoteId(noteId);
        if (auditLogs.isEmpty()) {
            throw new RuntimeException("No audit logs found related to noteId: " + noteId);
        }
        return auditLogs;
    }

    private String getIp(){
        String ip = "";
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
        }catch (UnknownHostException ex){
            System.out.println(ex.getMessage());;
        }
        return ip;
    }
}
