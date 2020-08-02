package io.ripudaman.coronavirus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.ripudaman.coronavirus.models.LocationStats;
import io.ripudaman.coronavirus.service.CoronavirusService;

//We are not marking this as a RestController. Reason being we do not want the methods inside to return result as JSON as this
//will be used for UI purpose, hence only Controller
@Controller
public class CoronaController {

	@Autowired
	CoronavirusService coronaservice;
	
	@GetMapping("/")
	public String home(Model model) {
		List<LocationStats> allstats = coronaservice.getAllstat();
		int totalLatestCases = allstats.stream().mapToInt(total->total.getLatestTotalCases()).sum();
		int totalNewCases = allstats.stream().mapToInt(totNew -> totNew.getDelta()).sum();
		model.addAttribute("locationstats",allstats);
		model.addAttribute("totalLatestCases",totalLatestCases);
		model.addAttribute("totalNewCases",totalNewCases);
		
		return "Ripu";
	}
}
