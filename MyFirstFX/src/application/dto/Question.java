package application.dto;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import application.util.Base64;

public class Question implements IAPersistant {
	private SimpleStringProperty id;
	private SimpleStringProperty body;
	private SimpleStringProperty answer;
	
	public Question() {
		this.id = new SimpleStringProperty();
		this.body = new SimpleStringProperty();
		this.answer = new SimpleStringProperty();
	}

	public int getId() {
		return Integer.parseInt(id.get());
	}

	public void setId(int id) {
		this.id.set(Integer.toString(id));
	}

	public String getBody() {
		return body.get();
	}

	public void setBody(String body) {
		this.body.set(body);
	}

	public String getAnswer() {
		return answer.get();
	}

	public void setAnswer(String answer) {
		this.answer.set(answer);
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
