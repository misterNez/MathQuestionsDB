package com.me.webapps.mathquestions;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ListQuestionServlet")
public class ListQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// create session object
		HttpSession session = request.getSession(true);

		// attempt to create a list of QuestionBeans using QuestionsBean class 
		try 
		{
			QuestionsBean quesBean = new QuestionsBean();
			List<QuestionBean> questions = quesBean.getQuestions();

			// store questions in session for further use
			session.setAttribute("questions", questions);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		// forward to main page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mainPage.jsp");

		dispatcher.forward(request, response);

	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
