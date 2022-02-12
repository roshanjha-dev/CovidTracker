package com.netcore.CovidTracker.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.netcore.CovidTracker.model.CovidTracker;
import com.netcore.CovidTracker.service.CovidTrackerService;
import com.netcore.CovidTracker.service.SequenceGeneratorService;

@RestController
public class CovidTrackerController {
	
	@Autowired
	private CovidTrackerService covidTrackerService;
	
	@Autowired
	private SequenceGeneratorService sequenceService;
	
	@GetMapping("/fetch")
	public List<CovidTracker> exportToCSV(HttpServletResponse response) throws IOException{
		response.setContentType("text/csv");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String filename = "CovidTracker_" + currentDateTime + ".csv";
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + filename;
		response.setHeader(headerKey, headerValue);
		
		List<CovidTracker> allRecords = covidTrackerService.getAll();
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader = {"Updated On",	"State",	"Total Doses Administered",	"Total Sessions Conducted",	"Total Sites",
				"First Dose Administered",	"Second Dose Administered",	"Male(Individuals Vaccinated)",	"Female(Individuals Vaccinated)",
				"Transgender(Individuals Vaccinated)",	"Total Covaxin Administered",	"Total CoviShield Administered",	
				"Total Sputnik V Administered",	"AEFI",	"18-45 years (Age)",	"45-60 years (Age)",	"60+ years (Age)",	
				"Total Individuals Vaccinated"};
		
		String[] nameMapping = {"updatedOn", "state", "totalDosesAdministered", "totalSessionConducted", "totalSites",
				"firstDoseAdministered", "secondDoseAdministered", "maleIndividualsVaccinated", "femaleIndividualsVaccinated",
				"transgenderIndividualsVaccinated", "totalCovaxinAdministered", "totalCoviShieldAdministered", 
				"totalSputnikVAdministered", "AEFI", "age18To45", "age45To60", "ageAbove60", "totalIndividualsVaccinated"};
		
		csvWriter.writeHeader(csvHeader);
		
		for(CovidTracker covidTracker : allRecords) {
			csvWriter.write(covidTracker, nameMapping);
		}
		csvWriter.close();
		return covidTrackerService.getAll();
	}
	
	@GetMapping("/{state}")
	public String getTotalIndividualVaccinated(@PathVariable String state) {
		return covidTrackerService.getTotalIndividualVaccinatedByState(state);
	}
	
	@GetMapping("/{state}/wastage/{date}")
	public String getTotalWastage(@PathVariable String state, @PathVariable String date) {
		return covidTrackerService.getTotalWastageByStateAndDate(state, date);
	}
	
	@PostMapping("/state")
	public CovidTracker addRecord(@RequestBody CovidTracker covidTracker) {
		covidTracker.setId(sequenceService.getSequenceNumber(CovidTracker.SEQUENCE_NAME));
		return covidTrackerService.save(covidTracker);
	}
}
