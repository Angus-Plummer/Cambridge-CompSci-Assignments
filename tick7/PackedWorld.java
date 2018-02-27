package uk.ac.cam.ap801.tick7;

public class PackedWorld extends WorldImpl {
	
	private long cells;
	
	public PackedWorld() {
		super(8,8);
		cells = 0;
	}
	
	public PackedWorld(PackedWorld prev){
		super(prev);
		cells = 0;
	}

	@Override
	public boolean getCell(int col, int row) {
		if ((col > 7) || (row > 7) || (col < 0) || (row < 0)){ //check if the cell is within the bounds of the 8x8 world
			return false;
		}
		else {
			return PackedLong.get(cells, row*8+col);
		}
	}

	@Override
	public void setCell(int col, int row, boolean alive) {
		if (!(col > 7) && !(row > 7) && !(col < 0) && !(row < 0)) {  //if out of bounds do nothing
			cells = PackedLong.set(cells, row*8+col, alive);
			}  //else update the status of the cell to the given value
	}

	@Override
	protected WorldImpl nextGeneration() {
		PackedWorld world = new PackedWorld(this);
		for(int row = 0; row < 8; row++){  //nested for loop to gain access to each cell
			for(int col = 0; col < 8; col++){
				world.setCell(col, row, computeCell(col,row)); //update cells in nextworld to those calculated in computeCell
			}
		}
		return world;
	}

}
