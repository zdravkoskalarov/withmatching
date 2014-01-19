package application.dto;

import java.io.IOException;

import application.util.Base64;

public class Question implements IAPersistant {
	private int id;
	private String body;
	private String answer;
	
	public Question() {
		this.id = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public static String generateHash(int qid, int tid) throws Exception {
		try {
			return Base64.encodeObject(tid+"q"+qid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

	@Override
	public boolean isPersistant() {
		// TODO Auto-generated method stub
		return this.getId() > 0;
	}
	
}
