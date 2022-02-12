package com.netcore.CovidTracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.netcore.CovidTracker.model.CovidTracker;
import com.netcore.CovidTracker.repository.CovidTrackerRepository;
import com.netcore.CovidTracker.service.SequenceGeneratorService;

@SpringBootApplication
public class CovidTrackerApplication {

	@Autowired
	private CovidTrackerRepository covidTrackerRepository;
	@Autowired
	private SequenceGeneratorService sequenceService;
	
	public static void main(String[] args) {
		SpringApplication.run(CovidTrackerApplication.class, args);
	}
	
	@PostConstruct
	public void saveCSVToDatabase() {
		
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/covid_vaccine_statewise.csv"));
			br.readLine();
			while((line = br.readLine()) != null) {
				String[] data = line.split(",");
				if(data.length == 18) {
					CovidTracker covidTracker = new CovidTracker();
					covidTracker.setId(sequenceService.getSequenceNumber(CovidTracker.SEQUENCE_NAME));
					covidTracker.setUpdatedOn(data[0].trim());
					covidTracker.setState(data[1].trim());
					covidTracker.setTotalDosesAdministered(data[2].trim());
					covidTracker.setTotalSessionConducted(data[3].trim());
					covidTracker.setTotalSites(data[4].trim());
					covidTracker.setFirstDoseAdministered(data[5].trim());
					covidTracker.setSecondDoseAdministered(data[6].trim());
					covidTracker.setMaleIndividualsVaccinated(data[7].trim());
					covidTracker.setFemaleIndividualsVaccinated(data[8].trim());
					covidTracker.setTransgenderIndividualsVaccinated(data[9].trim());
					covidTracker.setTotalCovaxinAdministered(data[10].trim());
					covidTracker.setTotalCoviShieldAdministered(data[11].trim());
					covidTracker.setTotalSputnikVAdministered(data[12].trim());
					covidTracker.setAEFI(data[13].trim());
					covidTracker.setAge18To45(data[14].trim());
					covidTracker.setAge45To60(data[15].trim());
					covidTracker.setAgeAbove60(data[16].trim());
					covidTracker.setTotalIndividualsVaccinated(data[17].trim());
					covidTrackerRepository.save(covidTracker);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
