package uk.ac.cam.ap801.alg1;

import java.util.Vector;

public class MaxHeap {
	private char heapName;
	private Vector<Character> heapVector;
	private int length;
	
	MaxHeap(char name){
		heapName = name;
		heapVector = new Vector<Character>();
	}
	
	MaxHeap(char name, String str){
		heapName = name;
		heapVector = new Vector<Character>();
		char[] chars = str.toCharArray();
		for( char c : chars){
			insert(c);
		}
	}
	
	public void insert(char x){
		heapVector.add(length, x);
		heapifyUp(length);
		length++;
	}
	
	public char getMax(){
		if (length == 0){
			return "_".charAt(0);
		}
		else{
			char result = heapVector.get(0);
			heapVector.set(0, heapVector.get(length-1));
			heapifyDown(0);
			length--;
			return result;
		}
	}
	
	public void print(){
		System.out.println("print:");
		for(int i = 0; i < length; i++){
			System.out.println(heapVector.get(i) + " ");
		}
		System.out.println("\n");
	}
	
	public void heapifyDown(int pos){
		if(2*pos + 1 == length-1){
			char child = heapVector.get(2*pos+1);
			if (heapVector.get(pos) >= child){
				return;
			}
			else{
				heapVector.set(2*pos + 1, heapVector.get(pos));
				heapVector.set(pos, child);
				heapifyDown(2*pos+1);
			}
		}
		else { 
			if(2*pos + 2 <= length-1){
				char child1 = heapVector.get(2*pos+1);
				char child2 = heapVector.get(2*pos+2);
				if (heapVector.get(pos) >= child1 && heapVector.get(pos) >= child2){
					if (pos ==0){
						return;
					} else{
						heapifyDown((pos-1)/2);
					}
				}
				else{
					if (child1 >= child2){
						heapVector.set(2*pos + 1, heapVector.get(pos));
						heapVector.set(pos, child1);
						heapifyDown(2*pos+1);
					}
					else{ 
						heapVector.set(2*pos + 2, heapVector.get(pos));
						heapVector.set(pos, child2);
						heapifyDown(2*pos+2);
					}
				}
			}
			else{
				return;
			}
		}
	}
	
	public void heapifyUp(int pos){
		if (pos == 0){
			return;
		}
		else{
			char parent = heapVector.get((pos-1)/2);
			if (heapVector.get(pos) <= parent){
				return;
			}
			else{
				heapVector.set((pos - 1)/2, heapVector.get(pos));
				heapVector.set(pos, parent);
				heapifyUp((pos-1)/2);
			}
		}
	}
}
