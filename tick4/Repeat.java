package uk.ac.cam.ap801.tick4;

public class Repeat {
	
	public static String parseAndRep(String[] input){
		try{
			if (Integer.parseInt(input[1]) > 0){
				int repeat = Integer.parseInt(input[1]);
				String stringOut = input[0];
				while (repeat > 1) {   /* This appends another repeat-1 of the string to itself */
					stringOut = stringOut + " " + input[0];
					repeat -= 1;
				}
				return stringOut;
		} 
		else{ 
			throw new NumberFormatException();
		}
		} catch (NumberFormatException formatError) {
			return "Error: second argument is not a positive integer";
		} catch (ArrayIndexOutOfBoundsException wrongArgs){
			return "Error: insufficient arguments";
		}
	}
	
	public static void main(String[] args){
		System.out.println(parseAndRep(args));
	}
}
