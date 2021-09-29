package web.ctrl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dao.RouteDao;
import flights.Route;

@RestController
public class RouteController {
	
	private static Logger log = LoggerFactory.getLogger(RouteController.class);
	
	@Autowired
	private RouteDao routeDao;
	
	@RequestMapping("/routes/search")
	public List<Route> searchRoute(
		@RequestParam(defaultValue="France") 
		String fromCountry,
		@RequestParam 
		String toCountry
	) {
		log.debug("GET Routes from {} to {}", fromCountry, toCountry);
		return routeDao.findRoutesByCountries(fromCountry, toCountry);
	}

}
