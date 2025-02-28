import java.util.Random;
import java.util.Scanner;
import java.lang.Thread;
import java.io.IOException;
import java.io.InterruptedIOException;
//==============================================================================================
public class Fibonacci{
	//-------------------------------------
	public static void main(String[]args){
		Scanner in=new Scanner(System.in);
		int n;
		n = in.nextInt();
		int fib = fibonacci(n);
		System.out.println("fibonacci de: "+n+"es: "+fib);
	}
	//------------------------------------
	public static int fibonacci(int n){
		if(n<2){
			return 1;
		}else{
			int num[]=new int[2];
			final int nn= n;
			Thread h1 = new Thread(new Runnable(){public void run(){
				num[0] = fibonacci(nn-1);
			}});
			Thread h2 = new Thread(new Runnable(){public void run(){
				num[1] = fibonacci(nn-2);
			}});
			h1.start();
			h2.start();
			try{
				h1.join();
				h2.join();
			}catch(InterruptedException e){
				e.printStackTrace();//Thread.currentLine().thread();
			}
			return num[0] + num[1];
		}
	}
	//-------------------------------------
}
