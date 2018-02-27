package uk.ac.cam.ap801.tick3;

public class Cell {
	
	boolean alive;
	
	Cell() {
		alive=false;
	}
	
	boolean isAlive() {
		return alive;
	}
	
	void create() {
		alive=true;
	}
	
	void kill() {
		alive=false;
	}

}
