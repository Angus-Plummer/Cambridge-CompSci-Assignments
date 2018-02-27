package uk.ac.cam.ap801.tick7;

import java.awt.Color;
import java.awt.Graphics;
import java.io.PrintWriter;
import java.io.Writer;

import uk.ac.cam.acr31.life.World;

public abstract class WorldImpl implements World {
	private int width;
	private int height;
	private int generation;
	
	protected WorldImpl(int width, int height) {
		this.width = width;
		this.height = height;
		this.generation = 0;
	}

	protected WorldImpl(WorldImpl prev) {
		this.width = prev.width;
		this.height = prev.height;
		this.generation = prev.generation + 1;
	} 
	
	public int getWidth() { return this.width; }
	public int getHeight() { return this.height; }
	public int getGeneration() { return this.generation; }
	public int getPopulation() { return 0; }
	
	protected String getCellAsString(int col,int row) {
		return getCell(col,row) ? "#" : "_";
	}
	
	protected Color getCellAsColour(int col,int row) {
		return getCell(col,row) ? Color.BLACK : Color.WHITE;
	} 
	
	public void draw(Graphics g,int width, int height) {
		int worldWidth = getWidth();
		int worldHeight = getHeight();

		double colScale = (double)width/(double)worldWidth;
		double rowScale = (double)height/(double)worldHeight;

		for(int col=0; col<worldWidth; ++col) {
			for(int row=0; row<worldHeight; ++row) {
				int colPos = (int)(col*colScale);
				int rowPos = (int)(row*rowScale);
				int nextCol = (int)((col+1)*colScale);
				int nextRow = (int)((row+1)*rowScale);
				if (g.hitClip(colPos,rowPos,nextCol-colPos,nextRow-rowPos)) {
					g.setColor(getCellAsColour(col, row));
					g.fillRect(colPos,rowPos,nextCol-colPos,nextRow-rowPos);
				}
			} 
		} 
	}
	
	public World nextGeneration(int log2StepSize) {
		WorldImpl world = this;
		for (int i = 0; i < (1 << log2StepSize); i++){
			world = world.nextGeneration();
		}
		return world;
	}
	
	public void print(Writer w) {
		PrintWriter pw = new PrintWriter(w);
		for (int row = 0; row < height; row++) { 
			 for (int col = 0; col < width; col++) {
				 pw.print(getCellAsString(col, row)); 
			 }
			 pw.println();
		 }
		pw.flush();
	}

	protected int countNeighbours(int col, int row) {
		int count = 0;  //create counter for how many alive cells are around the target cell
		int[] range = {-1,0,1};
		for (int i :range){  //create for loop for all 9 cells around target cell, including the target
			for (int j :range){
				if (getCell(col+i,row+j)){   //if the cell at (c+i,r+j) is alive add 1 to count
					count += 1;
				}
			}
		}
		if (getCell(col, row)) {  //if the original cell was alive we have to take 1 off the summed count
			count -= 1;
		}
		return count;
	}

	protected boolean computeCell(int col, int row) {
		

		 // liveCell is true if the cell at position (col,row) in world is live
		 boolean liveCell = getCell(col, row);
			
		 // neighbours is the number of live neighbours to cell (col,row)
		 int neighbours = countNeighbours(col, row);

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
//Compute whether this cell is alive or dead in the next generation
//using "countNeighbours"
	}

	// Will be implemented by child class. Return true if cell (col,row) is alive.
	public abstract boolean getCell(int col,int row);
	
	// Will be implemented by child class. Set a cell to be live or dead.
	public abstract void setCell(int col, int row, boolean alive);
	
	// Will be implemented by child class. Step forward one generation.
	protected abstract WorldImpl nextGeneration();
}

