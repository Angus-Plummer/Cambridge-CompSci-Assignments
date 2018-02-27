package uk.ac.cam.ap801.tick3;

public class StringArrayLife {
	public static boolean getCell(boolean[][] world, int col, int row){
		 if (row < 0 || row > world.length - 1) return false;
		 if (col < 0 || col > world[row].length - 1) return false;

		 return world[row][col];
	}
	
	public static void setCell(boolean[][] world, int col, int row, boolean value) {
		if (!(row < 0 || row > world.length - 1 || col < 0 || col > world[row].length - 1)){  //if out of bounds do nothing
			world[row][col] = value;  //else update the status of the cell to the given value
		}
	}
	
	public static int countNeighbours(boolean[][] world, int col, int row) {
		int count = 0;  //create counter for how many alive cells are around the target cell
		int[] range = {-1,0,1};
		for (int i :range){  //create for loop for all 9 cells around target cell, including the target
			for (int j :range){
				if (getCell(world,col+i,row+j)){   //if the cell at (c+i,r+j) is alive add 1 to count
					count += 1;
				}
			}
		}
		if (world[row][col] == true) {  //if the original cell was alive we have to take 1 off the summed count
			return (count-1);
		}
		else{
			return count;
		}
	}
	
	public static boolean computeCell(boolean[][] world,int col,int row) {

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
	
	public static boolean[][] nextGeneration(boolean[][] world){
		boolean[][] nextworld = new boolean[world.length][world[0].length];  //create the frame for world at t+1
		for(int row = 0; row < world.length; row++){  //nested for loop to gain access to each cell
			for(int col = 0; col < world[row].length; col++){
				setCell(nextworld, col, row, computeCell(world,col,row)); //update cells in nextworld to those calculated in computeCell
			}
		}
		return nextworld;
	}
	
	public static void print(boolean[][] world) { 
		 System.out.println("-"); 
		 for (int row = 0; row < world.length; row++) { 
			 for (int col = 0; col < world[row].length; col++) {
				 System.out.print(getCell(world, col, row) ? "#" : "_"); 
			 }
			 System.out.println(); 
		 } 
	}
	
	public static void play(boolean[][] world) throws Exception {
		int userResponse = 0;
		while (userResponse != 'q') {
			print(world);
			userResponse = System.in.read();
			world = nextGeneration(world);
		}
	}

	public static void main(String[] args) throws Exception {
		 String formatString = args[0];
		 String[] formatStringArray = formatString.split(":");
		 //TODO: Determine the dimensions of the game board
		 int width = Integer.parseInt(formatStringArray[2]);
		 int height = Integer.parseInt(formatStringArray[3]);
		 boolean[][] world = new boolean[height][width];
		 //TODO: Using loops, update the appropriate cells of "world" to "true"
		 String[] aliveCells = formatStringArray[6].split(" ");
		 int row = Integer.parseInt(formatStringArray[5]);
		 int col = Integer.parseInt(formatStringArray[4]);
		 boolean alivecell = false;
		 for (String rowString : aliveCells){
			 char[] cells = rowString.toCharArray();
			 for (char colChars : cells){
				 alivecell = false;
				 if (Integer.parseInt(Character.toString(colChars)) == 1){
					 alivecell = true;
				 }
				 setCell(world, col, row, alivecell);
				 col += 1;
			 }
			 row += 1;
			 col = Integer.parseInt(formatStringArray[4]);	 
		 }
		 play(world);
	}
}
