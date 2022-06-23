package com.coders.tournament.service;

import com.coders.tournament.model.Match;
import com.coders.tournament.model.Participant;
import com.coders.tournament.model.Tournament;
import com.coders.tournament.util.Utils;
import com.coders.tournament.exception.TournamentException;
import com.coders.tournament.repositories.MatchRepository;
import com.coders.tournament.repositories.ParticipantRepository;
import com.coders.tournament.repositories.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final MatchRepository matchRepository;
    private final ParticipantRepository participantRepository;

    public TournamentService(TournamentRepository tournamentRepository, MatchRepository matchRepository, ParticipantRepository participantRepository) {
        this.tournamentRepository = tournamentRepository;
        this.matchRepository = matchRepository;
        this.participantRepository = participantRepository;
    }

    public List<Tournament> findAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Tournament findTournamentById(Long id) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);

        return tournamentOptional.orElseThrow(() -> new TournamentException(Utils.tournamentNotFoundMessage(id)));
    }

    public Tournament createTournament(Tournament tournament) {
//        if (tournament.getMaxParticipants() == null || tournament.getMaxParticipants() % 8 != 0) {
//            throw new TournamentException("Max number of participants should be multiple of 8!");
//        }

        return tournamentRepository.save(tournament);
    }

    public String startTournament(Long id) {
        if (!tournamentRepository.existsById(id)) {
            throw new TournamentException(Utils.tournamentNotFoundMessage(id));
        }
        return tournamentRepository.findById(id)
                .map(tournament -> {
                    tournament.setStatusId(2);
                    List<Participant> participants = tournament.getParticipants();
                    generateMatch(tournament);
                    tournament.setParticipants(participants);
                    Utils.matchParticipants(tournament, matchRepository, participants, participantRepository);

                    return "Tournament started successfully!\n";
                }).orElseThrow(() -> new TournamentException("Participant not found!"));
    }

    public List<Match> generateMatch(Tournament tournament) {
        List<Participant> participants = tournament.getParticipants();
        Collections.shuffle(participants);
        List<Match> matchList = new ArrayList<>();
        for (int i = 0; i < participants.size()/2; i++) {
            Match match = new Match();
            match.setFirstParticipantId(participants.get(i).getId());
            match.setSecondParticipantId(participants.get(i + participants.size()/2).getId());
            matchRepository.save(match);
            matchList.add(match);
        }
        shuffleMatch(participants);
        return matchList;
    }

    public List<Participant> shuffleMatch(List<Participant> participants) {
        Participant participant = participants.get(participants.size()-1);
//        for (int i = 0; i < participants.size(); i++) {
//            for (int j = (i+1); j < participants.size(); j++) {
//                List<Participant> list = new ArrayList<>();
//                list.add(participants.get(i));
//                list.add(participants.get(j));
//
//                Collections.shuffle(list);
//                participants.set(0, participant);
//                participants.set(1, participant);
//            }
//        }
        for (int i = participants.size()-1; i > 0; i--) {
            if (i == 1) {
                participants.set(i, participant);
                break;
            }
            participants.set(i, participants.get(i-1));
        }
        return participants;
    }
    public String holdTournament(Long id) {
        if (!tournamentRepository.existsById(id)) {
            throw new TournamentException(Utils.tournamentNotFoundMessage(id));
        }

        return tournamentRepository.findById(id)
                .map(tournament -> {
                    tournament.setStatusId(2);

                    return "Tournament is now on hold!";
                }).orElseThrow(() -> new TournamentException(Utils.tournamentNotFoundMessage(id)));
    }

    public Tournament updateTournament(Long id, Tournament tournamentUpdated) {
        if (!tournamentRepository.existsById(id)) {
            throw new TournamentException(Utils.tournamentNotFoundMessage(id));
        }

        return tournamentRepository.findById(id)
                .map(tournament -> {
//                    tournament.setMaxParticipants(tournamentUpdated.getMaxParticipants());
                    tournament.setStatusId(0);
                    tournament.setParticipants(tournamentUpdated.getParticipants());

                    return tournamentRepository.save(tournament);
                }).orElseThrow(() -> new TournamentException(Utils.tournamentNotFoundMessage(id)));
    }

    public String removeTournament(Long id) {
        if (!tournamentRepository.existsById(id)) {
            throw new TournamentException(Utils.tournamentNotFoundMessage(id));
        }

        return tournamentRepository.findById(id)
                .map(tournament -> {
                    tournamentRepository.delete(tournament);

                    return "Tournament deleted successfully!";
                }).orElseThrow(() -> new TournamentException(Utils.tournamentNotFoundMessage(id)));
    }
}
