package com.example.matchmanagement.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class PersistanceModel {
	@Id
	@GeneratedValue
	private long id;
}
