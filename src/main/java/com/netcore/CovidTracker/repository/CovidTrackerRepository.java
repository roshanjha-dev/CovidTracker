package com.netcore.CovidTracker.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.netcore.CovidTracker.model.CovidTracker;

@Repository
public interface CovidTrackerRepository extends MongoRepository<CovidTracker, Integer>{
	
	public List<CovidTracker> findByState(String state);
	//public CovidTracker findByStateAndUpdatedOn(String state, String updatedOn);
}
