package service;

import model.AuditResult;
import model.Control;
import model.Result;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RiskScoreCalculatorTest {

    private RiskScoreCalculator calculator;
    private User mockUser;
    private Control control;

    @BeforeEach
    public void setUp() {
        calculator = new RiskScoreCalculator();
        mockUser = new User(1, "Test User", "password");
    }

    @Test
    public void testRiskScore_MinimumValues_NoAudits() {
        control = new Control("Test control", "Test description", 0, 0, mockUser);
        double riskScore = calculator.calculateRiskScore(control);

        assertEquals(0.0, riskScore, 0.01);
    }

    @Test
    public void testRiskScore_MaximumValues_NoAudits() {
        control = new Control("Test control", "Test description", 5, 5, mockUser);
        double riskScore = calculator.calculateRiskScore(control);

        assertEquals(50.0, riskScore, 0.01);
    }

    @Test
    public void testRiskScore_MaximumValues_AllFailing() {
        control = new Control("Test Control", "Description", 5, 5, mockUser);
        control.getAuditResults().add(new AuditResult(1, 1, LocalDate.now(), Result.FAIL));
        double riskScore = calculator.calculateRiskScore(control);

        assertEquals(90.0, riskScore, 0.01);
    }

    @Test
    public void testRiskScore_MediumValues_NoAudits() {
        control = new Control("Test Control", "Description", 3, 3, mockUser);
        double riskScore = calculator.calculateRiskScore(control);

        assertEquals(18.0, riskScore, 0.01);
    }

    @Test
    public void testRiskScore_HighImpactLowLikelihood_AllPassing() {
        control = new Control("Test Control", "Description", 5, 1, mockUser);
        control.getAuditResults().add(new AuditResult(1, 1, LocalDate.now(), Result.PASS));
        control.getAuditResults().add(new AuditResult(2, 1, LocalDate.now(), Result.PASS));

        double riskScore = calculator.calculateRiskScore(control);

        assertEquals(3.0, riskScore, 0.01);
    }

    @Test
    public void testRiskScore_LowImpactHighLikelihood_AllFailing() {
        control = new Control("Test Control", "Description", 1, 5, mockUser);
        control.getAuditResults().add(new AuditResult(1, 1, LocalDate.now(), Result.FAIL));
        control.getAuditResults().add(new AuditResult(2, 1, LocalDate.now(), Result.FAIL));

        double riskScore = calculator.calculateRiskScore(control);

        assertEquals(18.0, riskScore, 0.01);
    }

    @Test
    public void testRiskScore_MediumValues_50PercentFailing() {
        control = new Control("Test Control", "Description", 3, 3, mockUser);
        control.getAuditResults().add(new AuditResult(1, 1, LocalDate.now(), Result.PASS));
        control.getAuditResults().add(new AuditResult(2, 1, LocalDate.now(), Result.FAIL));

        double riskScore = calculator.calculateRiskScore(control);

        assertEquals(18.9, riskScore, 0.01);
    }

    @Test
    public void testRiskScore_HighImpactHighLikelihood_25PercentFailing() {
        control = new Control("Test Control", "Description", 5, 5, mockUser);
        control.getAuditResults().add(new AuditResult(1, 1, LocalDate.now(), Result.PASS));
        control.getAuditResults().add(new AuditResult(2, 1, LocalDate.now(), Result.PASS));
        control.getAuditResults().add(new AuditResult(3, 1, LocalDate.now(), Result.PASS));
        control.getAuditResults().add(new AuditResult(4, 1, LocalDate.now(), Result.FAIL));

        double riskScore = calculator.calculateRiskScore(control);

        assertEquals(33.75, riskScore, 0.01);
    }

    @Test
    public void testRiskScore_HighImpactHighLikelihood_75PercentFailing() {
        control = new Control("Test Control", "Description", 5, 5, mockUser);
        control.getAuditResults().add(new AuditResult(1, 1, LocalDate.now(), Result.FAIL));
        control.getAuditResults().add(new AuditResult(2, 1, LocalDate.now(), Result.FAIL));
        control.getAuditResults().add(new AuditResult(3, 1, LocalDate.now(), Result.FAIL));
        control.getAuditResults().add(new AuditResult(4, 1, LocalDate.now(), Result.PASS));

        double riskScore = calculator.calculateRiskScore(control);

        assertEquals(71.25, riskScore, 0.01);
    }
}