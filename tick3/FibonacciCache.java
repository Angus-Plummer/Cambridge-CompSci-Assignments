package uk.ac.cam.ap801.tick3;

public class FibonacciCache {
	public static long[] fib = new long[20];

	 public static void store() {
		 if (fib.length == 1){
			 fib[0] = 1;
		 }
		 else{ 
			 if (fib.length>1){
				 fib[0] = 1;
				 fib[1] = 1;
				 for (int i=2; i<fib.length; i++){
					 fib[i] = fib[i-1]+fib[i-2];
				 }
			 }
		 }
	 }

	 public static void reset() {
		  for (int i = 0; i<fib.length; i++) {
			  fib[i] = 0;
		  }
	 }
	 
	 public static long get(int i) {
		 if ((i>fib.length)|(i<0)){
			 return -1L;
		 }
		 else {
			 return fib[i];
		 }
	 }
	 
	 public static void print(){
		 for (int i = 0; i < fib.length; i++){
			 System.out.println(fib[i]);
		 }		 
	 }
	 public static void main(String[] args){
		 FibonacciCache fibonacci = new FibonacciCache();
		 fibonacci.store();
		 fibonacci.print();
		 System.out.println(" ");
		 System.out.println(fibonacci.get(3));
		 fibonacci.reset();
		 System.out.println(" ");
		 fibonacci.print();
	 }

}
