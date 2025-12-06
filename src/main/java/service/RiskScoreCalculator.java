package service;

import model.Control;
import model.Result;

public class RiskScoreCalculator {

    /**
     * Formula:
     * - Inherent Risk = impact * likelihood (0-25, where impact and likelihood are 0-5)
     * - Control Effectiveness = based on audit history (0-1.0)
     *   - All passing: 0.85 (85% effective)
     *   - Mixed results: proportional to pass rate
     *   - All failing: 0.10 (10% effective, control doesn't work)
     *   - No audits: 0.50 (50% effective, unknown)
     * - Residual Risk = Inherent Risk * (1 - Control Effectiveness)
     * - Final Score = (Residual Risk / 25) * 100 (0-100)
     * - Result range: 0-100
     */

    public double calculateRiskScore(Control control) {
        int inherentRisk = control.getImpact() * control.getLikelihood();
        double controlEffectiveness = calculateControlEffectiveness(control);
        double residualRisk = inherentRisk * (1- controlEffectiveness);
        double normalizedRisk = (residualRisk / 25) * 100;

        return normalizedRisk;
    }

    private double calculateControlEffectiveness(Control control) {
        if (control.getAuditResults().isEmpty()) {
            return 0.50;
        }

        int passedAudits = control.getAuditResults().stream()
                .filter(audit -> audit.getResult() == Result.PASS)
                .toList().size();

        int totalAudits = control.getAuditResults().size();
        double passRate = (double) passedAudits / totalAudits;

        // Formula: 0.10 when 0% pass, 0.50 when 50% pass, 0.85 when 100% pass
        return 0.10 + (passRate * 0.75);
    }

    public double getControlEffectiveness(Control control) {
        return calculateControlEffectiveness(control) * 100;
    }
}
