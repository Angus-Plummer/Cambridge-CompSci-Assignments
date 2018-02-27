package uk.ac.cam.ap801.tick7;

public class ArrayWorld extends WorldImpl {
	
	private boolean[][] cells;
	
	public ArrayWorld(int w, int h) {
		super(w,h);
		boolean[][] newcells = new boolean[h][w];
		cells = newcells;
	}
	
	protected ArrayWorld(ArrayWorld prev) {
		super(prev);
		boolean[][] newcells = new boolean[prev.getHeight()][prev.getWidth()];
		cells = newcells;
	}

	@Override
	public boolean getCell(int col, int row) {
		if (row < 0 || row > getHeight() - 1) return false;
	 	if (col < 0 || col > getWidth() - 1) return false;
	 	return cells[row][col];
	}

	@Override
	public void setCell(int col, int row, boolean alive) {
		if (!(row < 0) && !(row > getHeight() - 1) && !(col < 0) && !(col > getWidth() - 1)){  //if out of bounds do nothing
			cells[row][col] = alive;  //else update the status of the cell to the given value
		}
	}

	@Override
	protected WorldImpl nextGeneration() {
		ArrayWorld world = new ArrayWorld(this);
		for(int row = 0; row < getHeight(); row++){  //nested for loop to gain access to each cell
			for(int col = 0; col < getWidth(); col++){
				world.setCell(col, row, computeCell(col,row)); //update cells in nextworld to those calculated in computeCell
			}
		}
		return world;
	}
}
