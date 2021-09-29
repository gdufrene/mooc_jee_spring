package fr.eservices.drive.web;

import java.util.List;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.dao.StatusHistory;

public interface HistorySource {
	
	List<StatusHistory> orderHistory( int orderId );
	void addHistoryStatus( int orderId, StatusHistory statusHistory ) throws DataException;

}
