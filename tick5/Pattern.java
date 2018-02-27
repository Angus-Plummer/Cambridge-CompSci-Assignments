package uk.ac.cam.ap801.tick5;

import uk.ac.cam.acr31.life.World;

public class Pattern {
	
	private String name;
	 private String author;
	 private int width;
	 private int height;
	 private int startCol;
	 private int startRow;
	 private String cells;
	
	 public String getName() {
	 return name;
	 }
	 
	 public String getAuthor() {
		 return author;
	 }
	 
	 public int getWidth() {
		 return width;
	 }
	 
	 public int getHeight() {
		 return height;
	 }
	 
	 public int getStartCol() {
		 return startCol;
	 }
	 
	 public int getStartRow() {
		 return startRow;
	 }
	 
	 public String getCells() {
		 return cells;
	 }
	 
	 public Pattern(String format) throws PatternFormatException {
		 String[] formatStringArray = format.split(":");
		 if (!(formatStringArray.length == 7)){
			 throw new PatternFormatException("Error: Pattern has incorrect number of arguments");
		 }
		 try{
			 name = formatStringArray[0];
			 author = formatStringArray[1];
			 width = Integer.parseInt(formatStringArray[2]);
			 height = Integer.parseInt(formatStringArray[3]);
			 startCol = Integer.parseInt(formatStringArray[4]);
			 startRow = Integer.parseInt(formatStringArray[5]);
			 cells = formatStringArray[6];
		 } catch (NumberFormatException formatError){
			 	throw new PatternFormatException("Error: Integer argument is not an integer");
		 }
			 if (width < 0 || height < 0 || startCol < 0 || startCol > width ||startRow < 0 || startRow > height){
				 	throw new PatternFormatException("Error: Input contains impossible world bounds");
			 }
	}
	 
	 public String toString(){ //outputs the original string version of the 
		 return name + ":" + author + ":" + String.valueOf(width) + ":" + String.valueOf(height) + ":" + String.valueOf(startCol) + ":" + String.valueOf(startRow) + ":" + cells;
	 }
		 
	 public void initialise(World world) throws PatternFormatException {
	 // update the values in the 2D array representing the state of "world"
	 // as expressed by the contents of the field "cells".
		 try{
			 String[] specCells = cells.split(" ");
			 boolean alivecell = false;
			 int row = startRow;
			 int col = startCol;
			 for (String rowString : specCells){
			 	char[] cells = rowString.toCharArray();
			 	for (char colChar : cells){
			 		if (row > height || col > width){
			 			throw new PatternFormatException("Error: The specified cells are outside the world dimensions");
			 		}
			 		alivecell = false;
			 		if (!(Integer.parseInt(Character.toString(colChar)) == 1) && !(Integer.parseInt(Character.toString(colChar)) == 0)){
			 			throw new PatternFormatException("Error: The specified cells must only be of value 0 or 1");
			 		}
			 		if (Integer.parseInt(Character.toString(colChar)) == 1){
			 			alivecell = true;
			 		}
			 		world.setCell(col, row, alivecell);
			 		col += 1;
			 	}
			 	row += 1;
			 	col = startCol;	 
			 }
		}catch (NumberFormatException formatError){
		 	throw new PatternFormatException("Error: Cell argument in specified cells is not an integer");
			
		}
	} 
}
