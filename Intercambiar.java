import java.util.Scanner;
import java.io.InterruptedIOException;
import java.lang.Thread;
import java.io.IOException;
//=============================================================
public class Intercambiar{
	//---------------------------------------------------
	public static void main(String[] args){
		int arr[]= new int[]{1,10};
		Thread h1= new Thread(new Runnable(){
				public void run(){
					intercambiar(arr);
		}});
		//h1.start();
		arr[0]*=2;
		try{
			h1.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		arr[0]*=2;
		for(int i : arr){
			System.out.print(i+" ");
		}
		System.out.println();
	}
	//--------------------------------------------------

	public static void intercambiar(int []arr){
		int temp = arr[0];
		arr[0] = arr[1];
		arr[1] = temp;
	}
	//--------------------------------------------------
}
