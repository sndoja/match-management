package com.example.matchmanagement.odds;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OddsRepository extends JpaRepository<MatchOddsModel, Long> {
	
	List<MatchOddsModel> findByMatchId(final long matchId);
	
}
