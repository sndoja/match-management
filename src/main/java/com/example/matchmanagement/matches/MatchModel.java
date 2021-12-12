package com.example.matchmanagement.matches;

import com.example.matchmanagement.common.PersistanceModel;
import com.example.matchmanagement.common.Sport;
import com.example.matchmanagement.odds.MatchOddsModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "tbl_matches")
@Data
@EqualsAndHashCode(callSuper = true ,exclude = "odds")
public class MatchModel extends PersistanceModel {
	
	@Column(name = "description")
	private String description;

	@Column(name = "match_date")
	private LocalDate matchDate;

	@Column(name = "match_time")
	private LocalTime matchTime;

	@Column(name = "team_a")
	private String teamA;

	@Column(name = "team_b")
	private String teamB;

	@Column(name = "sport")
	private Sport sport;

	@OneToMany(mappedBy = "match", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<MatchOddsModel> odds;
	
}
