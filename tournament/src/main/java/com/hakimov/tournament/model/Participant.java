package com.hakimov.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Table(name = "participant")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Participant {
    @Id
    private Long id;
    private String nickname;

    @JsonIgnore
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id", nullable = false)
    @JsonIgnore
    private Match match;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable (
            name = "tournament_participant",
            joinColumns = @JoinColumn(name = "participants_id"),
            inverseJoinColumns = @JoinColumn(name = "tournament_id")
    )
    @JsonIgnore
    private List<Tournament> tournament = new ArrayList<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public List<Tournament> getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tour) {
        tournament.add(tour);
    }
}
