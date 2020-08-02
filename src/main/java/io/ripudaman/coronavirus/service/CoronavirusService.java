package io.ripudaman.coronavirus.service;


import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.ripudaman.coronavirus.models.LocationStats;

@Service
public class CoronavirusService {

	private static String VIRUS_VAR_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private List<LocationStats> allstat = new ArrayList<LocationStats>();
	
	//PostConstruct will tell spring to run this method after service classpath scan
	@PostConstruct
	@Scheduled(cron="* * 1 * * *")
	public void fetchVirusData() throws IOException, InterruptedException {
		List<LocationStats> newallstat = new ArrayList<LocationStats>();
		HttpClient client = HttpClient.newHttpClient();
		//Making a http request to website
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_VAR_URL)).build();
		//Sending the request and getting the response through client
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		//StringReader - An instance of reader that parses string
		StringReader csvreader = new StringReader(response.body());
		//Open source code from https://commons.apache.org/proper/commons-csv/user-guide.html for getting data through csv
		//-----------------------------------------------------------------------------------------
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvreader);
		for (CSVRecord record : records) {
			LocationStats locationstat = new LocationStats();
			locationstat.setProvince_State(record.get("Province/State"));
		    locationstat.setCountry(record.get("Country/Region"));
		    int totalcases = Integer.parseInt(record.get(record.size()-1));
		    int prevdaycases = Integer.parseInt(record.get(record.size()-2));
		    locationstat.setLatestTotalCases(totalcases);
		    locationstat.setDelta(totalcases-prevdaycases);
//		    String Country_Region = record.get("Country/Region");
//		    String Lat = record.get("Lat");
//		    String Long = record.get("Long");
//		    System.out.println(locationstat);
		    newallstat.add(locationstat);
		}
		this.allstat=newallstat;
		//-----------------------------------------------------------------------------------------
		 
	}

	public List<LocationStats> getAllstat() {
		return allstat;
	}
}


// Notes
/*
 * 1. Avoid saving state/values in spring services. Eg: totalcases,
 * prevdaycases. You should make a service a stateless. It is acceptable for
 * this app. 2. Write test cases of your code.
 */ 