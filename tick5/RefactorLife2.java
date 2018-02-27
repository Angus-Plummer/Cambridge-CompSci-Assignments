package uk.ac.cam.ap801.tick5;

import uk.ac.cam.acr31.life.World;
import uk.ac.cam.acr31.life.WorldViewer;

import java.util.LinkedList;
import java.util.List;

public class RefactorLife2 {
	
	public static void play(World world) throws Exception {
		int userResponse = 0;
		WorldViewer viewer = new WorldViewer();
		while (userResponse != 'q') {
			viewer.show(world);
			userResponse = System.in.read();
			world = world.nextGeneration(0);
		}
		viewer.close();
	}
	
	public static void main(String[] args) throws Exception {

		List<Pattern> patternList = new LinkedList<Pattern>();
		String source = null;
		String index = null;
		String worldType = "--array";
		try{
			
			if (args.length == 1){
				source = args[0];
				if (source.startsWith("http://")) {
					patternList = PatternLoader.loadFromURL(source);
				}
				else {
					patternList = PatternLoader.loadFromDisk(source);
				}
				int count = 0;
				for (Pattern p: patternList){
					System.out.println(String.valueOf(count) + ") " + p.toString() + "\n");
					count +=1 ;
				}	
			
			} else if (args.length == 2){
				source = args[0];
				index = args[1];
			
			} else if (args.length == 3){
				worldType = args[0];
				source = args[1];
				index = args[2];
				if (!(worldType.equals("--array")) && !(worldType.equals("--long")) && !(worldType.equals("--aging"))){
					throw new PatternFormatException("Error: The requested storage type is not valid. Please use a supported type in the form '--storageType'");
				}
			} else{ throw new PatternFormatException("Error: Input has incorrect number of arguments");}
			
			if (args.length == 2 || args.length == 3){ 
				try{
					if (source.startsWith("http://")) {
						patternList = PatternLoader.loadFromURL(source);
					}
					else {
						patternList = PatternLoader.loadFromDisk(source);
					}
					int indexInt = Integer.parseInt(index);
					if (indexInt < 0 || indexInt > patternList.size() - 1){
						throw new PatternFormatException("Error: Index argument out of bounds");
					}
					Pattern p = patternList.get(indexInt);
					World world = null;
					if (worldType.equals("--long")){
						world = new PackedWorld();
					}
					if(worldType.equals("--array")){
						world = new ArrayWorld(p.getWidth(), p.getHeight());
					}
					if(worldType.equals("--aging")){
						world = new AgingWorld(p.getWidth(), p.getHeight());
					}
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
