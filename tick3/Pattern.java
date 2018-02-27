package uk.ac.cam.ap801.tick3;

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
	 
	 public Pattern(String format) {
		 String[] formatStringArray = format.split(":");
		 name = formatStringArray[0];
		 author = formatStringArray[1];
		 width = Integer.parseInt(formatStringArray[2]);
		 height = Integer.parseInt(formatStringArray[3]);
		 startCol = Integer.parseInt(formatStringArray[4]);
		 startRow = Integer.parseInt(formatStringArray[5]);
		 cells = formatStringArray[6];
		 
	 }
	 public void initialise(boolean[][] world) {
	 //TODO: update the values in the 2D array representing the state of "world"
	 // as expressed by the contents of the field "cells".
		 String[] specCells = cells.split(" ");
		 boolean alivecell = false;
		 int row = startRow;
		 int col = startCol;
		 for (String rowString : specCells){
			 char[] cells = rowString.toCharArray();
			 for (char colChars : cells){
				 alivecell = false;
				 if (Integer.parseInt(Character.toString(colChars)) == 1){
					 alivecell = true;
				 }
				 StringArrayLife.setCell(world, col, row, alivecell);
				 col += 1;
			 }
			 row += 1;
			 col = startCol;	 
		 }
		 
		 
	 }
}
