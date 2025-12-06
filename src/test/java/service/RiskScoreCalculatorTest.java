package service;

import model.Control;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}