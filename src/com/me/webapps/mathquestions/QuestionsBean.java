package com.me.webapps.mathquestions;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionsBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private PreparedStatement quesQuery;

	// construct QuestionsBean object
	public QuestionsBean() throws Exception {
		// attempt database connection and setup SQL statements
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/mathdb",
						"root", "");

		quesQuery = connection
				.prepareStatement("SELECT * FROM  questions");
	}

	// return a List of QuestionBeans
	public List<QuestionBean> getQuestions() {
		List<QuestionBean> quesList = new ArrayList<QuestionBean>();

		// obtain list of questions
		try {
			ResultSet results = quesQuery.executeQuery();

			// get row data
			while (results.next()) {
				// create new QuestionBean
				QuestionBean question = new QuestionBean();

				// call setter methods
				question.setQuestionID(results.getInt("id"));
				question.setQuestion(results.getString("question"));

				// add to list
				quesList.add(question);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		// return the list of QuestionBeans
		return quesList;
	}

	// close statements and terminate database connection
	protected void finalize() {
		// attempt to close database connection
		try {
			quesQuery.close();
			connection.close();
		}

		// process SQLException on close operation
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
}
