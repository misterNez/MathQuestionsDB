package com.me.webapps.mathquestions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddKeywordServlet")
public class AddKeywordServet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private Statement stmt;
	private PreparedStatement addKeyword;
	
	
	public void init(ServletConfig config) throws ServletException 
	{
		// attempt database connection and create PreparedStatements
		try {
			// db connectiona and statement
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mathdb", "root", "");
			stmt = conn.createStatement();
			
			// PreparedStatement to add one keyword
			addKeyword = conn.prepareStatement("INSERT INTO keywords (id, keyword) "
					+ "VALUES (?, ?)");
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
		// create a new KeywordBean object
		KeywordBean keyword = new KeywordBean();
		
		// declare integer used to keep track of number of keywords
		int num;
		// attempt to update database
		try 
		{
			// keyword result set and # of records
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM keywords");
			rs.next();
			num = rs.getInt(1);
			
			// retrieve request parameters
			keyword.setKeywordID(++num);
			keyword.setKeyword(request.getParameter("keyword"));
			
			
			// update keywords
			if (keyword != null)
			{
				addKeyword.setInt(1, keyword.getKeywordID());
				addKeyword.setString(2, keyword.getKeyword());
				addKeyword.executeUpdate();
			}
		} 
		// if database exception occurs, return error page
		catch (SQLException sqlException) 
		{
			sqlException.printStackTrace();
			throw new UnavailableException(sqlException.getMessage());
		}
		
		// retrieve question id and parse to an integer
		String quesID = request.getParameter("quesID");
		int questionID = Integer.parseInt(quesID.trim());
		
		// set request attributes
		request.setAttribute("k_id", keyword.getKeywordID());
		request.setAttribute("q_id", questionID);
		
		// forward to AddMapServlet
		RequestDispatcher dispatcher = request.getRequestDispatcher("/AddMapServlet");

		dispatcher.forward(request, response);
	}

	// close SQL statements and database when servlet terminates
	public void destroy() {
		// attempt to close statements and database connection
		try 
		{
			addKeyword.close();
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
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
	{	
		doGet(request, response);
	}

}
