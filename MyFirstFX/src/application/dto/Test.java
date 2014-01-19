package application.dto;

public class Test implements IAPersistant {
	
	private int id;
	private String name;
	private QuestionsList questions;
	
	public Test() {
		this.id = 0;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public QuestionsList getQuestions() {
		return questions;
	}

	public void setQuestions(QuestionsList questions) {
		this.questions = questions;
	}


	@Override
	public boolean isPersistant() {
		// TODO Auto-generated method stub
		return this.getId() > 0;
	}

	

}
