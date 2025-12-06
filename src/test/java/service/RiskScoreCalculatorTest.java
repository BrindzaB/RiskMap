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
        control = new Control("Test Control", "Test description", 0, 0, mockUser);
        double riskScore = calculator.calculateRiskScore(control);
        assertEquals(0.0, riskScore, 0.01);
    }

    @Test
    public void testRiskScore_MaximumValues_NoAudits() {
        control = new Control("Test Control", "Test description", 5, 5, mockUser);
        double riskScore = calculator.calculateRiskScore(control);
        assertEquals(66.67, riskScore, 0.01);
    }

    @Test
    public void testRiskScore_MaximumValues_AllFailing() {
        control = new Control("Test Control", "Test description", 5, 5, mockUser);
        control.getAuditResults().add(new AuditResult(1, 1, LocalDate.now(), Result.FAIL));
        double riskScore = calculator.calculateRiskScore(control);
        assertEquals(100.0, riskScore, 0.01);
    }

    @Test
    public void testRiskScore_MaximumValues_AllPassing() {
        control = new Control("Test Control", "Test description", 5, 5, mockUser);
        control.getAuditResults().add(new AuditResult(1, 1, LocalDate.now(), Result.PASS));
        double riskScore = calculator.calculateRiskScore(control);
        assertEquals(33.333, riskScore, 0.01);
    }
}