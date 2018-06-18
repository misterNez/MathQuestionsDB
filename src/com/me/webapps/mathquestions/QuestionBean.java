package com.me.webapps.mathquestions;

import java.io.Serializable;

public class QuestionBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String question;
	private int questionID;
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getQuestion() {
		return question;
	}

	public void setQuestionID(int id) {
		questionID = id;
	}
	
	public int getQuestionID() {
		return questionID;
	}
}
