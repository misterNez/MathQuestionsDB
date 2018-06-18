package com.me.webapps.mathquestions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchKeywordServlet")
public class SearchKeywordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private PreparedStatement query;
	
	
	public void init(ServletConfig config) throws ServletException 
	{
		// attempt database connection and create PreparedStatements
		try {
			// db connectiona and statement
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mathdb", "root", "");
			
			// Prepare statement to search by keyword
			query = conn.prepareStatement("SELECT questions.question FROM questions, keywords, maps "
					+ "WHERE keywords.keyword = ? "
					+ "AND maps.k_id = keywords.id "
					+ "AND maps.q_id = questions.id");
		} 
		// for any exception throw an UnavailableException to
			// indicate that the servlet is not currently available
		catch (Exception exception) 
		{
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	} // end of init method
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
	{	
		// create a ResultSet object
		ResultSet rs;
		
		// get the request parameter
		String search = request.getParameter("search");
		
		// attempt to search database
		try 
		{
			// search keywords
			query.setString(1, search);
			rs = query.executeQuery();
		} 
		
		// if database exception occurs, return error page
		catch (SQLException sqlException) 
		{
			sqlException.printStackTrace();
			throw new UnavailableException(sqlException.getMessage());
		}
		
		// create String ArrayList to store the matching questions
		List<String> quesList = new ArrayList<String>();
		
		
		try 
		{
			// while the result set has another element:
			while(rs.next()) 
			{
				// add the element to the List
				quesList.add(rs.getString(1));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		// set the list in the request scope
		request.setAttribute("quesList", quesList);
		
		// forward to main page.
		RequestDispatcher dispatcher = request.getRequestDispatcher("/viewSearch.jsp");

		dispatcher.forward(request, response);
	}

	// close SQL statements and database when servlet terminates
	public void destroy() {
		// attempt to close statements and database connection
		try 
		{
			query.close();
			conn.close();
		} 
		// handle database exceptions by returning error to client
		catch (SQLException sqlException) 
		{
			sqlException.printStackTrace();
		}
	} 
	// end of destroy method

}
