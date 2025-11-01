package model;

import java.time.LocalDate;
import java.util.UUID;

public class AuditResult {
    private int id;
    private int controlId;
    private LocalDate date;
    private Result result;

    public AuditResult(int id, int controlId, LocalDate date, Result result) {
        this.id = id;
        this.controlId = controlId;
        this.date = date;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public int getControlId() {
        return controlId;
    }

    public Result getResult() {
        return result;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }
}
