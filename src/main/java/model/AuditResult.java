package model;

import java.time.LocalDate;
import java.util.UUID;

public class AuditResult {
    private int controlId;
    private LocalDate date;
    private Result result;

    public AuditResult(int controlId, LocalDate date, Result result) {
        this.controlId = controlId;
        this.date = date;
        this.result = result;
    }
}
