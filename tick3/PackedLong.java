package uk.ac.cam.ap801.tick3;

public class PackedLong {

	public static boolean get(long packed, int position) {
		long check = (packed>>(position) & 1);
		return (check == 1);
	}

	public static long set(long packed, int position, boolean value) {
		if (value) { packed = packed | (1L<<position);
		}
		else { 
			packed = packed & ~(1L<<position);
		}
		return packed;
	}
} 
