package com.main.app.controller;

import com.main.app.model.AuditLog;
import com.main.app.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditLogController {
    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllAuditLogs() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(auditLogService.getAllAuditLogs());
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<List<AuditLog>> getNoteAuditLogs(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(auditLogService.getNoteAuditLogs(id));
    }
}
