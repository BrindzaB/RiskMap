package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Control {
    private int id;
    private String name;
    private String description;
    private int impact;
    private int likelihood;
    private User owner;
    private List<AuditResult> auditResults;

    public Control(String name, String description, int impact, int likelihood, User owner) {
        this.name = name;
        this.description = description;
        this.impact = impact;
        this.likelihood = likelihood;
        this.owner = owner;
        this.auditResults = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImpact() {
        return impact;
    }

    public int getLikelihood() {
        return likelihood;
    }

    public User getOwner() {
        return owner;
    }

}
