package com.coders.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//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
@Data
@Entity
@Table(name = "tournament")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // private Integer maxParticipants;

    @Column(name = "name")
    private String name;
   // @JsonIgnore
    private int statusId = 1;

    private int tournamentTypeId;

    private int userId;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Participant.class)
    @JoinTable (
            name = "tournament_participant"

    )
    private List<Participant> participants;


    private LocalDateTime starTime;

    @OneToMany
    private List<User> users;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tournament")
    private List<Match> matches;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Integer getMaxParticipants() {
//        return maxParticipants;
//    }
//
//    public void setMaxParticipants(Integer maxParticipants) {
//        this.maxParticipants = maxParticipants;
//    }


    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int status) {
        this.statusId = status;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
