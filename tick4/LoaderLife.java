package uk.ac.cam.ap801.tick4;

import java.util.LinkedList;
import java.util.List;

public class LoaderLife {
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
	
	public static void reset(boolean[][] world){
		for(int row = 0; row < world.length; row++){  //nested for loop to gain access to each cell
			for(int col = 0; col < world[row].length; col++){
				setCell(world, col, row, false); //update cells in nextworld to 0
			}
		}
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
		List<Pattern> patternList = new LinkedList<Pattern>();
		try{
			if (!(args.length == 1 | args.length == 2)){
				throw new PatternFormatException("Error: Input has incorrect number of arguments");
			}
			if (args[0].startsWith("http://")) {
				patternList = PatternLoader.loadFromURL(args[0]);
			}
			else {
				patternList = PatternLoader.loadFromDisk(args[0]);
			}
			if (args.length == 1){
				int count = 0;
				for (Pattern p: patternList){
					System.out.println(String.valueOf(count) + ") " + p.toString() + "\n");
					count +=1 ;
				}
			}
			else{	
				try{
					if (Integer.parseInt(args[1]) < 0 | Integer.parseInt(args[1]) > patternList.size() - 1){
						throw new PatternFormatException("Error: Index argument out of bounds");
					}
					Pattern p = patternList.get(Integer.parseInt(args[1]));
					boolean[][] world = new boolean[p.getHeight()][p.getWidth()];
					p.initialise(world);
					play(world);
				}catch (NumberFormatException formatError) {
					throw new PatternFormatException("Error: Second argument must be a positive integer");
				}
			}
		 } catch (PatternFormatException formatError){
			 System.out.println(formatError.getDescription());
		 }
	}
}
