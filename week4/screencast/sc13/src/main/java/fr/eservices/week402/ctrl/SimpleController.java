package fr.eservices.week402.ctrl;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.eservices.week402.model.TimeObject;

@RestController
@RequestMapping(path="/simple")
public class SimpleController {

	@GetMapping(path="/add")
	public String add(
		@RequestParam int a,
		@RequestParam int b
	) {
		return Integer.toString( a + b );
	}
	
	@GetMapping(path="/time")
	public TimeObject getTime() {
		TimeObject t = new TimeObject();
		Date now = new Date();
		t.day = DateFormat.getDateInstance(DateFormat.SHORT).format(now);
		t.day = DateFormat.getTimeInstance(DateFormat.SHORT).format(now);
		t.locale = Locale.getDefault().toString();
		t.timestamp = (long) (now.getTime() / 1000);
		return t;
	}
	
}
