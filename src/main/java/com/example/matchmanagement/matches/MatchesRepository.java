package com.example.matchmanagement.matches;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchesRepository extends JpaRepository<MatchModel, Long> {
}
