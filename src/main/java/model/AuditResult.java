package model;

import java.time.LocalDate;
import java.util.UUID;

public class AuditResult {
    private UUID controlId;
    private LocalDate date;
    private Result result;

    public AuditResult(UUID controlId, LocalDate date, Result result) {
        this.controlId = controlId;
        this.date = date;
        this.result = result;
    }
}
