package com.me.webapps.mathquestions;

import java.io.Serializable;

public class MapBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int primaryID;
	private int questionID;
	private int keywordID;
	
	public void setPrimaryID(int id) {
		primaryID = id;
	}
	
	public int getPrimaryID() {
		return primaryID;
	}
	
	public void setQuestionID(int id) {
		questionID = id;
	}
	
	public int getQuestionID() {
		return questionID;
	}

	public void setKeywordID(int id) {
		keywordID = id;
	}
	
	public int getKeywordID() {
		return keywordID;
	}
}
