package com.coders.tournament.model;

public class MatchRequest {
    private Long firstParticipantScore;
    private Long secondParticipantScore;

    public Long getFirstParticipantScore() {
        return firstParticipantScore;
    }

    public Long getSecondParticipantScore() {
        return secondParticipantScore;
    }

    public void setFirstParticipantScore(Long firstParticipantScore) {
        this.firstParticipantScore = firstParticipantScore;
    }

    public void setSecondParticipantScore(Long secondParticipantScore) {
        this.secondParticipantScore = secondParticipantScore;
    }
}
