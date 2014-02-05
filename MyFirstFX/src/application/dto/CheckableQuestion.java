package application.dto;

import java.io.Serializable;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.DataFormat;

public class CheckableQuestion extends Question implements Serializable {
	


	public static final DataFormat CheckableQuestion_DATA_FORMAT = new DataFormat("application.dto.CheckableQuestion");
	
	private transient BooleanProperty checked;
	private boolean bound;
	
	public CheckableQuestion(Question q) {
		checked = new BooleanProperty() {
			
			@Override
			public void set(boolean arg0) {
				// TODO Auto-generated method stub
				bound = arg0;
			}
			
			@Override
			public boolean get() {
				// TODO Auto-generated method stub
				return bound;
			}
			
			@Override
			public void removeListener(InvalidationListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addListener(InvalidationListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeListener(ChangeListener<? super Boolean> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addListener(ChangeListener<? super Boolean> arg0) {
				
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getBean() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void unbind() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isBound() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void bind(ObservableValue<? extends Boolean> arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		this.setId(q.getId());
		this.setBody(q.getBody());
		this.setAnswer(q.getAnswer());
		this.setChecked(false);
	}

	public BooleanProperty isChecked() {
		return checked;
	}
	
	public BooleanProperty checkedProperty() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked.set(checked);
	}

}
