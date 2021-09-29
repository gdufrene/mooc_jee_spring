package fr.eservices.drive.web;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import fr.eservices.drive.dao.StatusHistory;

// define as a REST Controller in spring context
public class RestHistoryController {
	
	// Inject reference from spring context
	HistorySource historySource;

	// map this opetation to GET only
	public List<StatusHistory> getHistory( @PathVariable int orderId ) {

		throw new RuntimeException("Not yet implemented");
	}
	
	// map this operation to POST only
	public String addStatus( @PathVariable int orderId, @RequestBody StatusHistory history ) {
		// try to add a status,
		// return "Ok" or "Error" if exception thrown 
		throw new RuntimeException("Not yet implemented");
	}
}
