package service;

import model.Control;
import model.Result;

public class RiskScoreCalculator {

    public double calculateRiskScore(Control control) {
        int baseRisk = control.getImpact() * control.getLikelihood();
        double auditMultiplier = calculateAuditMultiplier(control);
        double rawRisk = baseRisk * auditMultiplier;
        double normalizeRisk = (rawRisk / 37.5) * 100;

        return normalizeRisk;
    }

    private double calculateAuditMultiplier(Control control) {
        if (control.getAuditResults().isEmpty()) {
            return 1.0;
        }
        int failedAudits = control.getAuditResults().stream()
                .filter(audit -> audit.getResult() == Result.FAIL).toList().size();

        int totalAudits = control.getAuditResults().size();

        double failureRate = (double)  failedAudits / totalAudits;

        return 0.5 + failureRate;
    }
}
