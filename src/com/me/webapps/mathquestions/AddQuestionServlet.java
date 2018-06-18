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

@WebServlet("/AddQuestionServlet")
public class AddQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private Statement stmt;
	private PreparedStatement addQuestion;
	
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
			addQuestion = conn.prepareStatement("INSERT INTO questions (id, question) "
					+ "VALUES (?, ?)");
		} 
		// for any exception throw an UnavailableException to
			// indicate that the servlet is not currently available
		catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	} // end of init method
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
	{	
		QuestionBean question = new QuestionBean();
		
		// declare integer used to keep track of number of questions
		int num;
		// attempt to update database
		try 
		{
			// question result set and # of records
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM questions");
			rs.next();
			num = rs.getInt(1);
			
			// retrieve request parameters
			question.setQuestionID(++num);
			question.setQuestion(request.getParameter("question"));
			
			
			// update questions
			if (question != null)
			{
				addQuestion.setInt(1, question.getQuestionID());
				addQuestion.setString(2, question.getQuestion());
				addQuestion.executeUpdate();
			}
		} 
		// if database exception occurs, return error page
		catch (SQLException sqlException) 
		{
			sqlException.printStackTrace();
			throw new UnavailableException(sqlException.getMessage());
		}
		
		response.sendRedirect("ListQuestionServlet");
	}

	// close SQL statements and database when servlet terminates
	public void destroy() {
		// attempt to close statements and database connection
		try 
		{
			addQuestion.close();
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
