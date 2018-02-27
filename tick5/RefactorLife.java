package uk.ac.cam.ap801.tick5;

import uk.ac.cam.acr31.life.World;
import uk.ac.cam.acr31.life.WorldViewer;
import java.util.LinkedList;
import java.util.List;

public class RefactorLife {
	
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
		try{
			if (!(args.length == 1) && !(args.length == 2) && !(args.length == 3)){
				throw new PatternFormatException("Error: Input has incorrect number of arguments");
			}
			
			String data = null;
			String patternNum = null;
			String storageType = "--array";
			
			if (!(args.length == 3)){
				data = args[0];
			}
			if(args.length == 2){
				patternNum = args[1];
			}
			if(args.length == 3){
				storageType = args[0];
				data = args[1];
				patternNum = args[2];
				if (!(storageType.equals("--array")) && !(storageType.equals("--long")) && !(storageType.equals("--aging"))){
					throw new PatternFormatException("Error: The requested storage type is not valid. Please use a supported type in the form '--storageType'");
				}
			}
			
			if (data.startsWith("http://")) {
				patternList = PatternLoader.loadFromURL(data);
			}
			else {
				patternList = PatternLoader.loadFromDisk(data);
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
					if (Integer.parseInt(patternNum) < 0 || Integer.parseInt(patternNum) > patternList.size() - 1){
						throw new PatternFormatException("Error: Index argument out of bounds");
					}
					Pattern p = patternList.get(Integer.parseInt(patternNum));
					World world = null;
					if (storageType.equals("--long")){
						world = new PackedWorld();
					}
					if(storageType.equals("--array")){
						world = new ArrayWorld(p.getWidth(), p.getHeight());
					}
					if(storageType.equals("--aging")){
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
