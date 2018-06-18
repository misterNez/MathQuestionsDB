package com.me.webapps.mathquestions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddMapServlet")
public class AddMapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private Statement stmt;
	private PreparedStatement addMap;
	
	
	public void init(ServletConfig config) throws ServletException 
	{
		// attempt database connection and create PreparedStatements
		try {
			// db connectiona and statement
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mathdb", "root", "");
			stmt = conn.createStatement();
			
			// PreparedStatement to add one question
			addMap = conn.prepareStatement("INSERT INTO maps (id, q_id, k_id) "
					+ "VALUES (?, ?, ?)");
		} 
		// for any exception throw an UnavailableException to
			// indicate that the servlet is not currently available
		catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	} // end of init method
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
	{	
		// create new MapBean object
		MapBean map = new MapBean();
		
		// declare integer used to keep track of number of mappings
		int num;
		
		// get attributes from request scope
		int q_id = (int) request.getAttribute("q_id");
		int k_id = (int) request.getAttribute("k_id");
		
		// attempt to update database
		try 
		{
			// map result set and # of records
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM maps");
			rs.next();
			num = rs.getInt(1);
			
			// call MapBean setters
			map.setPrimaryID(++num);
			map.setQuestionID(q_id);
			map.setKeywordID(k_id);
			
			// update questions
			if (map != null)
			{
				// use prepared statement to update database
				addMap.setInt(1, map.getPrimaryID());
				addMap.setInt(2, q_id);
				addMap.setInt(3, k_id);
				addMap.executeUpdate();
			}
		} 
		// if database exception occurs, return error page
		catch (SQLException sqlException) 
		{
			sqlException.printStackTrace();
			throw new UnavailableException(sqlException.getMessage());
		}
		
		// redirect to main page
		response.sendRedirect("ListQuestionServlet");
	}

	// close SQL statements and database when servlet terminates
	public void destroy() 
	{
		// attempt to close statements and database connection
		try 
		{
			addMap.close();
			stmt.close();
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
