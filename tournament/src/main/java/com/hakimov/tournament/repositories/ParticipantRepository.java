package com.hakimov.tournament.repositories;

import com.hakimov.tournament.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    /**
     * Return participants of tournament by id.
     * @param id - tournament id
     * @return - list of tournament's participants
     */
    @Query(value = "select p.nickname from participant p Where p.tournament_id = ?1", nativeQuery = true)
    List<Participant> findParticipantByTournamentId(Long id);
    List<Participant> findByTournamentId(Long id);
}
