package com.trustlink.trustlink;

import com.trustlink.trustlink.decision.DecisionEngine;
import com.trustlink.trustlink.model.Decision;
import com.trustlink.trustlink.model.RiskLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DecisionEngineTest {
    DecisionEngine decision = new DecisionEngine();

    // Decision
    @Test
    void score100_returnsAllowed(){
        assertEquals(Decision.ALLOWED, decision.decide(100));
    }

    @Test
    void score80_returnsAllowed(){
        assertEquals(Decision.ALLOWED, decision.decide(80));
    }

    @Test
    void score79_returnsFlagged() {
        assertEquals(Decision.FLAGGED, decision.decide(79));
    }

    @Test
    void score45_returnsFlagged() {
        assertEquals(Decision.FLAGGED, decision.decide(45));
    }

    @Test
    void score44_returnsBlocked() {
        assertEquals(Decision.BLOCKED, decision.decide(44));
    }

    @Test
    void score0_returnsBlocked() {
        assertEquals(Decision.BLOCKED, decision.decide(0));
    }


    //Classification
    @Test
    void score100_returnsTrusted() {
        assertEquals(RiskLevel.TRUSTED, decision.classify(100));
    }

    @Test
    void score80_returnsTrusted() {
        assertEquals(RiskLevel.TRUSTED, decision.classify(80));
    }

    @Test
    void score79_returnsSuspicious() {
        assertEquals(RiskLevel.SUSPICIOUS, decision.classify(79));
    }

    @Test
    void score45_returnsSuspicious() {
        assertEquals(RiskLevel.SUSPICIOUS, decision.classify(45));
    }

    @Test
    void score44_returnsHighRisk() {
        assertEquals(RiskLevel.HIGH_RISK, decision.classify(44));
    }

    @Test
    void score0_returnsHighRisk() {
        assertEquals(RiskLevel.HIGH_RISK, decision.classify(0));
    }
}
