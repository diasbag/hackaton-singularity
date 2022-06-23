package com.coders.tournament.repositories;

import com.coders.tournament.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Tournament getById(Long id);
}
