package uk.ac.cam.ap801.tick4;

public class PatternFormatException extends Exception {
	
	private String description;
	
	public PatternFormatException(String inputDescription){
		setDescription(inputDescription);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
