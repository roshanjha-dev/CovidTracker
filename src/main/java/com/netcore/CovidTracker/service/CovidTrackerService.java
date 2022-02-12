package com.netcore.CovidTracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netcore.CovidTracker.model.CovidTracker;
import com.netcore.CovidTracker.repository.CovidTrackerRepository;

@Service
public class CovidTrackerService {
	
	@Autowired
	private CovidTrackerRepository covidTrackerRepository;
	
	public String getTotalIndividualVaccinatedByState(String state) {
		String totalIndividualsVaccinated = covidTrackerRepository.findByState(state).get(0).getTotalIndividualsVaccinated();
		return totalIndividualsVaccinated;
	}
	
	public String getTotalWastageByStateAndDate(String state, String date) {
		String updatedDate = date.substring(0, 6) + date.substring(8);
		List<CovidTracker> allRecords = covidTrackerRepository.findAll();
		long totalWastage = 0L;
		
		int i=0;
		while(i < allRecords.size()) {
			if(allRecords.get(i).getState() == state && allRecords.get(i).getUpdatedOn() == updatedDate) {
				long totalDosages = Long.parseLong(allRecords.get(i).getFirstDoseAdministered()) + 
						Long.parseLong(allRecords.get(i).getSecondDoseAdministered());
				totalWastage = Long.parseLong(allRecords.get(i).getTotalDosesAdministered()) - totalDosages;
				break;
			}
			i++;
		}
		return String.valueOf(totalWastage);
	}

	public List<CovidTracker> getAll() {
		return covidTrackerRepository.findAll();
	}

	public CovidTracker save(CovidTracker covidTracker) {
		return covidTrackerRepository.save(covidTracker);
	}
}
