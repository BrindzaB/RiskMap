package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Control {
    private UUID id;
    private String name;
    private String description;
    private int impact;
    private int likelihood;
    private User owner;
    private List<AuditResult> auditResults;

    public Control(String name, String description, int impact, int likelihood, User owner) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.impact = impact;
        this.likelihood = likelihood;
        this.owner = owner;
        this.auditResults = new ArrayList<>();
    }

}
