package service.dto;

import model.casino.MatchResult;
import model.participant.Name;

public record PlayerScoreResult(Name name, MatchResult matchResult) {
    public String getNameAsString(){
        return name.getValue();
    }

    public String getVictoryAsString(){
        return matchResult.getValue();
    }
}