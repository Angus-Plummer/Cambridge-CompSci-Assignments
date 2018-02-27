package uk.ac.cam.ap801.tick2;

public class TinyLife {
	
	public static int getPos(int col, int row){  //calculates the position of the cell in the long from the rows and colums
		return row*8+col;
	}
	
	public static boolean getCell(long world, int col, int row){
		if ((col > 7) | (row > 7) | (col < 0) | (row < 0)){ //check if the cell is within the bounds of the 8x8 world
			return false;
		}
		else {
			return (PackedLong.get(world, getPos(col,row))); //get position and use PackedLong to check if it is alive
		}
	}
	
	public static long setCell(long world, int col, int row, boolean value) {
		if ((col > 7) | (row > 7) | (col < 0) | (row < 0)) {  //if out of bounds return the same world
			return world;
		}
		else{
			return PackedLong.set(world,getPos(col,row), value);  //else update the status of the cell to the given value
		}
	}
	
	public static int countNeighbours(long world, int col, int row) {
		int count = 0;  //create counter for how many alive cells are around the target cell
		int[] range = {-1,0,1};
		for (int i :range){  //create for loop for all 9 cells around target cell, including the target
			for (int j :range){
				if ((col + i > 7) | (row + j > 7) | (col + i < 0) | (row + j < 0)){  //make sure the test cell is within the bounds
				}
				else{
					if (PackedLong.get(world, getPos(col+i,row+j))){   //if the cell at (c+i,r+j) is alive add 1 to count
						count += 1;
					}
				}
			}
		}
		if (PackedLong.get(world,getPos(col,row)) == true) {  //if the original cell was alive we have to take 1 off the summed count
			return (count-1);
		}
		else{
			return count;
		}
	}
	
	public static boolean computeCell(long world,int col,int row) {

		 // liveCell is true if the cell at position (col,row) in world is live
		 boolean liveCell = getCell(world, col, row);
			
		 // neighbours is the number of live neighbours to cell (col,row)
		 int neighbours = countNeighbours(world, col, row);

		 // we will return this value at the end of the method to indicate whether 
		 // cell (col,row) should be live in the next generation
		 boolean nextCell = false;
			
		 //A live cell with less than two neighbours dies (underpopulation)
		 if (neighbours < 2) {
		  nextCell = false;
		 }
		 
		 //A live cell with two or three neighbours lives (a balanced population)
		 if (((neighbours == 2) | (neighbours == 3)) & liveCell){
			 nextCell = true;
		 }

		 //A live cell with with more than three neighbours dies (overcrowding)
		 if ((neighbours > 3) & liveCell){
			 nextCell = false;
		 }
	
		 //A dead cell with exactly three live neighbours comes alive
		 if ((neighbours == 3) & !liveCell){
			 nextCell = true;
		 }	
		 return nextCell;
	}
	
	public static long nextGeneration(long world){
		long nextworld = 0;  //create the frame for world at t+1
		for(int row = 0; row < 8; row++){  //nested for loop to gain access to each cell
			for(int col = 0; col < 8; col++){
				nextworld = setCell(nextworld, col, row, computeCell(world,col,row)); //update cells in nextworld to those calculated in computeCell
			}
		}
		return nextworld;
	}
	
	public static void print(long world) { 
		 System.out.println("-"); 
		 for (int row = 0; row < 8; row++) { 
			 for (int col = 0; col < 8; col++) {
				 System.out.print(getCell(world, col, row) ? "#" : "_"); 
			 }
			 System.out.println(); 
		 } 
	}
	
	public static void play(long world) throws Exception {
		int userResponse = 0;
		while (userResponse != 'q') {
			print(world);
			userResponse = System.in.read();
			world = nextGeneration(world);
		}
	}

	public static void main(String[] args) throws Exception {
		play(Long.decode(args[0]));
	}
}	