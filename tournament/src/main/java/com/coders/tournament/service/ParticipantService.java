package com.coders.tournament.service;

import com.coders.tournament.model.Match;
import com.coders.tournament.model.Participant;
import com.coders.tournament.model.Tournament;
import com.coders.tournament.util.Utils;
import com.coders.tournament.exception.TournamentException;
import com.coders.tournament.repositories.ParticipantRepository;
import com.coders.tournament.repositories.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {
    private final TournamentRepository tournamentRepository;
    private final ParticipantRepository participantRepository;

    public ParticipantService(TournamentRepository tournamentRepository, ParticipantRepository participantRepository) {
        this.tournamentRepository = tournamentRepository;
        this.participantRepository = participantRepository;
    }

    public List<Participant> findAllParticipants(Long tournamentId) {
        if (!tournamentRepository.existsById(tournamentId)) {
            throw new TournamentException(Utils.tournamentNotFoundMessage(tournamentId));
        }

        return participantRepository.findByTournamentId(tournamentId);
    }

    public Participant addParticipant(Long tournamentId, Participant participant) {
        return tournamentRepository.findById(tournamentId)
                .map(tournament -> {
                    if (tournament.getParticipants().size() < tournament.getMaxParticipants()) {
                        participant.setActive(true);
                        participant.setMatch(new Match());
                        participant.setTournament(tournament);

                        return participantRepository.save(participant);
                    }

                    // TODO - size limit exceeded message
                    return null;
                }).orElseThrow(() -> new TournamentException(Utils.tournamentNotFoundMessage(tournamentId)));
    }

    public Participant updateParticipant(Long tournamentId,
                                         Long participantId,
                                         Participant participantUpdated) {
        if (!tournamentRepository.existsById(tournamentId)) {
            throw new TournamentException(Utils.tournamentNotFoundMessage(tournamentId));
        }
        Tournament tournament = tournamentRepository.getById(tournamentId);
        return participantRepository.findById(participantId)
                .map(participant -> {
                    participant.setId(participantUpdated.getId());
                    participant.setNickname(participantUpdated.getNickname());
                    participant.setTournament(tournament);
                    participant.setActive(participantUpdated.isActive());

                    return participantRepository.save(participant);
                }).orElseThrow(() -> new TournamentException(Utils.participantNotFoundMessage()));
    }

    public String removeParticipant(Long tournamentId, Long participantId) {
        if (!tournamentRepository.existsById(tournamentId)) {
            throw new TournamentException(Utils.tournamentNotFoundMessage(tournamentId));
        }

        Optional<Tournament> tournamentOptional = tournamentRepository.findById(tournamentId);

        if (tournamentOptional.isEmpty()) {
            throw new TournamentException(Utils.tournamentNotFoundMessage(tournamentId));
        }

        Tournament tournament = tournamentOptional.get();

        if (tournament.getStatus() == 1) {
            return "Can't delete participant, because tournament is not on hold!";
        }

        return participantRepository.findById(participantId)
                .map(participant -> {
                    participantRepository.delete(participant);

                    return "Participant deleted successfully!";
                }).orElseThrow(() -> new TournamentException(Utils.participantNotFoundMessage()));
    }
}
