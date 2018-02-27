package uk.ac.cam.ap801.tick7;

import uk.ac.cam.ap801.tick5.PatternFormatException;

public class CommandLineOptions {
	public static String WORLD_TYPE_LONG = "--long";
	public static String WORLD_TYPE_AGING = "--aging";
	public static String WORLD_TYPE_ARRAY = "--array";
	public static String WORLD_TYPE_HASH = "--hash";
	private String worldType = null;
	private Integer index = null;
	private String source = null;
	 
	public CommandLineOptions(String[] args) throws CommandLineException {
		
		if (!(args.length == 1) && !(args.length == 2) && !(args.length == 3)){
			throw new CommandLineException("Error: Input has incorrect number of arguments");
		}
		
		if (!(args.length == 3)){
			source = args[0];
			worldType = WORLD_TYPE_AGING;
		}
		if(args.length == 2){
			index = Integer.parseInt(args[1]);
		}
		if(args.length == 3){
			worldType = args[0];
			source = args[1];
			index = Integer.parseInt(args[2]);
			if (!(worldType.equals(WORLD_TYPE_ARRAY)) && !(worldType.equals(WORLD_TYPE_LONG)) && !(worldType.equals(WORLD_TYPE_AGING)) && !(worldType.equals(WORLD_TYPE_HASH))){
				throw new CommandLineException("Error: The requested world type is not valid. Please use a supported type in the form '--worldType'");
			}
		}
	}
	public String getWorldType() {return worldType;}
	public Integer getIndex() {return index;}
	public String getSource() {return source;}


}
