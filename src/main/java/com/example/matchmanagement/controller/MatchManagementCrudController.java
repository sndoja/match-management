package com.example.matchmanagement.controller;

import com.example.matchmanagement.exception.ResourceNotFoundException;
import com.example.matchmanagement.matches.MatchModel;
import com.example.matchmanagement.matches.MatchesRepository;
import com.example.matchmanagement.odds.MatchOddsModel;
import com.example.matchmanagement.odds.OddsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/match")
public class MatchManagementCrudController {
	
	@Autowired
	private MatchesRepository repository;
	
	@Autowired
	private OddsRepository oddsRepository;

	@PostMapping
	public MatchModel create(@RequestBody final MatchModel request) {
		return repository.save(request);
	}

	@GetMapping({"/{id}"})
	public ResponseEntity<MatchModel> retrieveById(@PathVariable(value = "id") final long matchId) throws ResourceNotFoundException {
		MatchModel response =  repository.findById(matchId)
				.orElseThrow(() -> new ResourceNotFoundException("Match not found for this id : " + matchId));
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<MatchModel>> retrieveAll() {
		List<MatchModel> response =  repository.findAll();
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<MatchModel> update(@PathVariable(value = "id") final long matchId, @RequestBody final MatchModel request) throws ResourceNotFoundException {
		MatchModel match = repository.findById(matchId)
				.orElseThrow(() -> new ResourceNotFoundException("Match not found for this id :: " + matchId));
		match.setDescription(request.getDescription());
		match.setMatchDate(request.getMatchDate());
		match.setMatchTime(request.getMatchTime());
		match.setTeamA(request.getTeamA());
		match.setTeamB(request.getTeamB());
		match.setSport(request.getSport());
		
		List<MatchOddsModel> odds = oddsRepository.findByMatchId(matchId);
		odds.forEach(odd -> {
			request.getOdds().forEach( requestOdd -> {
				odd.setSpecifier(requestOdd.getSpecifier());
				odd.setOdd(requestOdd.getOdd());
			});
		});
		
		final MatchModel updatedMatch = repository.save(match);
		return ResponseEntity.ok(updatedMatch);
	}


	@DeleteMapping({"/{id}"})
	public ResponseEntity<Void> delete(@PathVariable(value = "id") final long matchId) 
			throws ResourceNotFoundException {
		MatchModel match = repository.findById(matchId)
				.orElseThrow(() -> new ResourceNotFoundException("Match not found for this id :: " + matchId));
		repository.delete(match);
		
		return  ResponseEntity.noContent().build();
	}
	
}
