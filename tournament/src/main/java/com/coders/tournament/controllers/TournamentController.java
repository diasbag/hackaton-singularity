package com.coders.tournament.controllers;

import com.coders.tournament.exception.TournamentException;
import com.coders.tournament.model.Participant;
import com.coders.tournament.model.Tournament;
import com.coders.tournament.model.TournamentResponse;
import com.coders.tournament.service.ParticipantService;
import com.coders.tournament.service.TournamentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.System.in;

/**
 * @author Muhammadjon Hakimov (github.com/MrHakimov)
 *
 * Controller, which provides methods to create, get and start tournaments and some additional methods.
 */
@RestController
@RequestMapping("/api")
public class TournamentController {
    private final TournamentService tournamentService;

    private ParticipantService participantService;
    public TournamentController(TournamentService tournamentService ,  ParticipantService participantService) {
        this.tournamentService = tournamentService;
        this.participantService = participantService;
    }

    /**
     * Additional method, which returns all available tournaments.
     *
     * @return all available tournaments.
     */
    @RequestMapping(value = "/tournaments/get/{userId}", method = RequestMethod.GET)
    public TournamentResponse get(@PathVariable int userId) {
        TournamentResponse response = new TournamentResponse();
        response.tournaments = tournamentService.findAllTournaments();
        return response;
    }

    /**
     * Gets tournament by id.
     *
     * @param id - tournaments' id.
     * @return tournament by id.
     * @throws TournamentException if there is no tournament with provided id.
     */
    @GetMapping("/tournaments/{id}")
    public Tournament get(@PathVariable Long id) throws TournamentException {
        return tournamentService.findTournamentById(id);
    }

    /**
     * Creates new tournament.
     *
     * @param tournament - tournament to create.
     * @return created tournament.
     * @throws TournamentException if maxParticipants is not provided or is not multiple of 8.
     */
    @PostMapping("/tournaments")
    public Tournament create(@RequestBody Tournament tournament) {

        List<Participant> list = tournament.getParticipants();
        tournament.setParticipants(null);
        Tournament result = tournamentService.createTournament(tournament);

        for (Participant t: list
             ) {
            participantService.addParticipant(result.getId(), t);
        }
        return result;
    }

    /**
     * Starts tournament with provided id.
     *
     * @param id - id of tournament to start.
     * @return status message.
     * @throws TournamentException if there is no tournament with provided id.
     */
    @PutMapping("/tournaments/start/{id}")
    public String start(@PathVariable Long id) throws TournamentException {
        return tournamentService.startTournament(id);
    }

    /**
     * Additional method, which holds tournament with provided id.
     *
     * @param id - id of tournament to hold.
     * @return status message.
     * @throws TournamentException if there is no tournament with provided id.
     */
    @PutMapping("/tournaments/hold/{id}")
    public String hold(@PathVariable Long id) throws TournamentException {
        return tournamentService.holdTournament(id);
    }

    /**
     * Additional method, which provides interface to update tournament's fields.
     *
     * @param id - id of tournament to update.
     * @param tournamentUpdated - updated tournament.
     * @return updated tournament.
     * @throws TournamentException if there is no tournament with provided id.
     */
    @PutMapping("/tournaments/{id}")
    public Tournament update(@PathVariable Long id,
                             @RequestBody Tournament tournamentUpdated) throws TournamentException {
        return tournamentService.updateTournament(id, tournamentUpdated);
    }

    /**
     * Additional method, which provides interface to delete tournament by id.
     *
     * @param id - id of tournament to delete.
     * @return status message.
     * @throws TournamentException if there is no tournament with provided id.
     */
    @DeleteMapping("/tournaments/{id}")
    public String remove(@PathVariable Long id) throws TournamentException {
        return tournamentService.removeTournament(id);
    }
}
