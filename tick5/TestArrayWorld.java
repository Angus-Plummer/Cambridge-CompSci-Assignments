package uk.ac.cam.ap801.tick5;

import uk.ac.cam.acr31.life.World;
import java.io.Writer;
import java.awt.Graphics;
import java.io.PrintWriter;

public class TestArrayWorld implements World {
	
	private int generation;
	private int width;
	private int height;
	private boolean[][] cells;
 
	public TestArrayWorld(int w, int h) {
		width = w;
		height = h;
		generation = 0;
		boolean[][] newcells = new boolean[h][w];
		cells = newcells;
 }
 
	protected TestArrayWorld(TestArrayWorld prev) {
		width = prev.width;
		height = prev.height;
		generation = prev.getGeneration() + 1;
		boolean[][] newcells = new boolean[height][width];
		cells = newcells;
	}
 
	public boolean getCell(int col, int row) {
		if (row < 0 || row > height - 1) return false;
		 	if (col < 0 || col > width - 1) return false;
		 return cells[row][col];
		 }
	
	public void setCell(int col, int row, boolean alive) {
		if (!(row < 0) && !(row > height - 1) && !(col < 0) && !(col > width - 1)){  //if out of bounds do nothing
			cells[row][col] = alive;  //else update the status of the cell to the given value
		}
	}
	
	public int getWidth() { 
		return width;
	}
	public int getHeight() { 
		return height;
	}
	public int getGeneration() { 
		return generation;
	}
	public int getPopulation() { 
		return 0; 
		}
	
	public static int countNeighbours(World world, int col, int row) {
		int count = 0;  //create counter for how many alive cells are around the target cell
		int[] range = {-1,0,1};
		for (int i :range){  //create for loop for all 9 cells around target cell, including the target
			for (int j :range){
				if (world.getCell(col+i,row+j)){   //if the cell at (c+i,r+j) is alive add 1 to count
					count += 1;
				}
			}
		}
		if (world.getCell(col, row) == true) {  //if the original cell was alive we have to take 1 off the summed count
			count -= 1;
		}
		return count;
	}
	
	public static boolean computeCell(World world, int col,int row) {

		 // liveCell is true if the cell at position (col,row) in world is live
		 boolean liveCell = world.getCell(col, row);
			
		 // neighbours is the number of live neighbours to cell (col,row)
		 int neighbours = countNeighbours(world, col, row);

		 // We will return this value at the end of the method to indicate whether 
		 // cell (col,row) should be alive in the next generation. Default is dead.
		 boolean nextCell = false;
			
		 //A live cell with less than two neighbours dies (underpopulation)
		 if (neighbours < 2) {
			 nextCell = false;
		 }
		 
		 //A live cell with two or three neighbours lives (a balanced population)
		 if (((neighbours == 2) || (neighbours == 3)) && liveCell){
			 nextCell = true;
		 }

		 //A live cell with with more than three neighbours dies (overcrowding)
		 if ((neighbours > 3) && liveCell){
			 nextCell = false;
		 }
	
		 //A dead cell with exactly three live neighbours comes alive
		 if ((neighbours == 3) && !liveCell){
			 nextCell = true;
		 }	
		 return nextCell;
	}
	
	public void print(Writer w) {
		PrintWriter pw = new PrintWriter(w);
		for (int row = 0; row < height; row++) { 
			 for (int col = 0; col < width; col++) {
				 pw.print(getCell(col, row) ? "#" : "_"); 
			 }
			 pw.println();
		 }
		pw.flush();
		}

	public void draw(Graphics g, int width, int height) { 
	}
 
	private TestArrayWorld nextGeneration() {
		TestArrayWorld world = new TestArrayWorld(this);
		for(int row = 0; row < height; row++){  //nested for loop to gain access to each cell
			for(int col = 0; col < width; col++){
				world.setCell(col, row, computeCell(this,col,row)); //update cells in nextworld to those calculated in computeCell
			}
		}
		return world;
	}
 
	public World nextGeneration(int log2StepSize) { 
		TestArrayWorld world = this;
		for (int i = 0; i < (1 << log2StepSize); i++){
			world = world.nextGeneration();
		}
		return world;
	}

}
