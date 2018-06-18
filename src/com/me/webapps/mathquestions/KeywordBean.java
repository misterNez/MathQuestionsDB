package com.me.webapps.mathquestions;

import java.io.Serializable;

public class KeywordBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String keyword;
	private int keywordID;
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeywordID(int id) {
		keywordID = id;
	}
	
	public int getKeywordID() {
		return keywordID;
	}
}
