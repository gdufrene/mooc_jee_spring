package fr.eservices.drive.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import fr.eservices.drive.web.HistorySource;

@Component
@Qualifier("mock")
public class HistorySourceMock implements HistorySource {
	
	private List<StatusHistory> status;
	
	public HistorySourceMock() {
		status = new ArrayList<StatusHistory>( Arrays.asList(
			createStatus( "ORDERED",          "2017-11-28T10:00:00Z" ),
			createStatus( "READY_TO_DELIVER", "2017-11-28T10:00:00Z" )
		));
	}

	SimpleDateFormat sdf = new SimpleDateFormat("");
	private StatusHistory createStatus(String statusName, String dateTime) {
		StatusHistory status = new StatusHistory();
		status.setStatus( Status.valueOf(statusName) );
		status.setStatusDate( DatatypeConverter.parseDate(dateTime).getTime() );
		return status;
	}

	@Override
	public List<StatusHistory> orderHistory(int orderId) {
		return status;
	}
	
	@Override
	public void addHistoryStatus(int orderId, StatusHistory statusHistory) 
	throws DataException {
		if ( orderId == 666 ) throw new DataException("No such order");
		status.add(statusHistory);
	}
}
