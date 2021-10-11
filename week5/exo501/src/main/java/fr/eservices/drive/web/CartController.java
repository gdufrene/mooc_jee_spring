package fr.eservices.drive.web;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.eservices.drive.dao.CartDao;
import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.dao.OrderDao;
import fr.eservices.drive.web.dto.CartEntry;
import fr.eservices.drive.web.dto.SimpleResponse;
import fr.eservices.drive.web.dto.SimpleResponse.Status;

@Controller
@RequestMapping(path="/cart")
public class CartController {
	
	@Autowired
	CartDao daoCart;
	
	@Autowired
	OrderDao daoOrder;
	
	@ExceptionHandler(DataException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String dataExceptionHandler(Exception ex) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintWriter w = new PrintWriter( out );
		ex.printStackTrace(w);
		w.close();
		return 
			"ERROR"
			+ "<!--\n" + out.toString() + "\n-->";
	}
	
	@GetMapping(path="/{id}.html", produces="text/html")
	public String getCart(@PathVariable(name="id") int id, Model model) throws DataException {
		// get cart from dao
		// assign to model var "cart"
		// return view name to display content of /WEB-INF/views/_cart_header.jsp

		return "";
	}

	@ResponseBody
	@PostMapping(path="/{id}/add.json",consumes="application/json")
	public SimpleResponse add(@PathVariable(name="id") int id, @RequestBody CartEntry art) {
		SimpleResponse res = new SimpleResponse();
		
		/*
		System.out.println(
			"********************\n"
			+ "***** " + String.format("Add Article %d x [%s] to cart", art.getQty(), art.getId()) + "\n" 
			+ "********************"
		);
		*/
		
		res.status = Status.ERROR;
		res.message = "Not yet implemented";
		// return res;
		
		throw new RuntimeException("Not yet implemented");
	}
	
	@RequestMapping("/{id}/validate.html")
	public String validateCart(@PathVariable(name="id") int id, Model model) {
		
		// get cart by its id
		// create an order
		// for each article, add it to the order
		// set order date
		// set order amount (sum of each articles' price)
		// persist everything
		// redirect user to list of orders
		
		return "";
	}
}
