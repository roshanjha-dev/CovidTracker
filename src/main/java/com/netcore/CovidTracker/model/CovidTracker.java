package com.netcore.CovidTracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection = "CovidTracker")
public class CovidTracker {
	
	@Transient
	public static final String SEQUENCE_NAME = "user_sequence";
	
	@Id
	private int id;
	private String updatedOn;
	private String state;
	private String totalDosesAdministered;
	private String totalSessionConducted;
	private String totalSites;
	private String firstDoseAdministered;
	private String secondDoseAdministered;
	private String maleIndividualsVaccinated;
	private String femaleIndividualsVaccinated;
	private String transgenderIndividualsVaccinated;
	private String totalCovaxinAdministered;
	private String totalCoviShieldAdministered;
	private String totalSputnikVAdministered;
	private String AEFI;
	private String age18To45;
	private String age45To60;
	private String ageAbove60;
	private String totalIndividualsVaccinated;
}
