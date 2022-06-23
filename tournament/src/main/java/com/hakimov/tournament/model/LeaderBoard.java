package com.hakimov.tournament.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "leaderboard")
public class LeaderBoard {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;


    private Long score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
