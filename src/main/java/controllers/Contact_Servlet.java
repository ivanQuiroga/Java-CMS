package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import checkers.Page_Meta;
import models.Categories_Model;
import models.Pages_Model;
import dao.Categories_Dao;
import dao.Pages_Dao;

@WebServlet("/contact")

public class Contact_Servlet extends HttpServlet {
	
	private static final String MODULE = "contact";
	
	private static final long serialVersionUID = 1L;
       
    public Contact_Servlet() {
    	
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/public_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		List<Categories_Dao> categories = null;
		Pages_Dao site = null;
		
		Pages_Model modelObject = new Pages_Model();
		Categories_Model importObject = new Categories_Model();
		Page_Meta pageObject = new Page_Meta();
				
		pageObject.setMain_title(MODULE.toUpperCase());
		attributes = pageObject.setPageMeta(attributes);
		
		try {
			
			categories = importObject.getActives();
			site = modelObject.getContactPage();
		} 
		catch (SQLException e) {

			e.printStackTrace();
		} 
		catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		attributes.put("module", MODULE);
		attributes.put("title", MODULE.toUpperCase() + " - " + (site != null ? site.getTitle() : "(Not found)"));
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));
		
		request.setAttribute("page", attributes);
		request.setAttribute("navigator", categories);
		request.setAttribute("site", site);
		
		request.setAttribute("message", request.getSession().getAttribute("message"));
		request.getSession().removeAttribute("message");
		
		dispatcher.forward(request, response);
	}
}
