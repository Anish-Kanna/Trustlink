package com.trustlink.trustlink.decision;

import com.trustlink.trustlink.model.Decision;
import com.trustlink.trustlink.model.RiskLevel;
import org.springframework.stereotype.Service;

@Service
public class DecisionEngine {

    public Decision decide(int score){
        if(score >= 80){
            return Decision.ALLOWED;
        }else if(score >= 45 && score < 80){
            return Decision.FLAGGED;
        }else{
            return Decision.BLOCKED;
        }
    }

    public RiskLevel classify(int score){
        if(score >= 80){
            return RiskLevel.TRUSTED;
        }else if(score >= 45 && score < 80){
            return RiskLevel.SUSPICIOUS;
        }else{
            return RiskLevel.HIGH_RISK;
        }
    }
}
