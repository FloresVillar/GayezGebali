import java.util.Scanner;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.lang.Thread;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
//=================================================================================================
public class ProductoParalelo{
//------------------------------------------------------------------------------------------------
	public static void main(String[]args){
		double[][] A=new double[][]{{1,2,3,4},{4,3,2,1},{1,2,3,4},{5,6,3,4}};
		double[][] B=new double[][]{{1,2,3,4},{1,2,3,4},{1,2,3,4},{1,2,3,4}};
		double[][] C= new double[4][4];
		multiplicacion(A,B,C,4);
		imprimir(C);
	}
//------------------------------------------------------------------------------------------------
	public static void multiplicacion(double[][]A,double[][]B,double[][]C,int n){
		if(n ==1) {
			C[0][0] = A[0][0]*B[0][0];
		}else{
			double[][]T = new double[n][n];
			double[][]A11 =new double[n/2][n/2];
			double[][]A12=new double[n/2][n/2];
			double[][]A21=new double[n/2][n/2];
			double[][]A22=new double[n/2][n/2];
			double[][]B11=new double[n/2][n/2];
			double[][]B12=new double[n/2][n/2];
			double[][]B21=new double[n/2][n/2];
			double[][]B22=new double[n/2][n/2];
			double[][]T11=new double[n/2][n/2];
			double[][]T12=new double[n/2][n/2];
			double[][]T21=new double[n/2][n/2];
			double[][]T22=new double[n/2][n/2];
			double[][]C11=new double[n/2][n/2];
			double[][]C12=new double[n/2][n/2];
			double[][]C21=new double[n/2][n/2];
			double[][]C22=new double[n/2][n/2];
			for(int i=0;i<n/2;i++){
				System.arraycopy(A[i],0,A11[i],0,n/2);
				System.arraycopy(B[i],0,B11[i],0,n/2);
				System.arraycopy(A[i],n/2,A12[i],0,n/2);
				System.arraycopy(B[i],n/2,B12[i],0,n/2);
			}
			for(int i=n/2;i<n;i++){
				System.arraycopy(A[i],0,A21[i-n/2],0,n/2);
				System.arraycopy(B[i],0,B21[i-n/2],0,n/2);
                        	System.arraycopy(A[i],n/2,A22[i-n/2],0,n/2);
				System.arraycopy(B[i],n/2,B22[i-n/2],0,n/2);
			}
			multiplicacion(A11,B11,C11,n/2);
			multiplicacion(A12,B21,T11,n/2);
			multiplicacion(A11,B12,C12,n/2);
			multiplicacion(A12,B22,T12,n/2);
			multiplicacion(A21,B11,C21,n/2);
			multiplicacion(A22,B21,T21,n/2);
			multiplicacion(A21,B12,C22,n/2);
			multiplicacion(A22,B22,T22,n/2);
			//double[][]C=new double[n][n];//reemzamblando C, T
			//double[][]T=new double[n][n];
			for(int i=0;i<n/2;i++){
				System.arraycopy(C11[i],0,C[i],0,n/2);
				System.arraycopy(C12[i],0,C[i],n/2,n/2);
				System.arraycopy(T11[i],0,T[i],0,n/2);
				System.arraycopy(T12[i],0,T[i],n/2,n/2);
			}
			for(int i=n/2;i<n;i++){
				System.arraycopy(C21[i-n/2],0,C[i],0,n/2);
				System.arraycopy(C22[i-n/2],0,C[i],n/2,n/2);
				System.arraycopy(T21[i-n/2],0,T[i],0,n/2);
				System.arraycopy(T22[i-n/2],0,T[i],n/2,n/2);
			}
		
		sumar(C,T,n);
		}
}
//------------------------------------------------------------------------------------------------------------------------
	public static void sumar(double[][]C,double[][]T,int n){
		if(n==1){
			C[0][0]+=T[0][0];
		}else{
			double[][]C11=new double[n/2][n/2];
			double[][]C12=new double[n/2][n/2];
			double[][]C21=new double[n/2][n/2];
			double[][]C22=new double[n/2][n/2];
			double[][]T11=new double[n/2][n/2];
			double[][]T12=new double[n/2][n/2];
			double[][]T21=new double[n/2][n/2];
			double[][]T22=new double[n/2][n/2];
			for(int i=0;i<n/2;i++){
				System.arraycopy(C[i],0,C11[i],0,n/2);
				System.arraycopy(C[i],n/2,C12[i],0,n/2);
				System.arraycopy(T[i],0,T11[i],0,n/2);
				System.arraycopy(T[i],n/2,T12[i],0,n/2);
			}
			for(int i=n/2;i<n;i++){
				System.arraycopy(C[i],0,C21[i-n/2],0,n/2);
				System.arraycopy(C[i],n/2,C22[i-n/2],0,n/2);
				System.arraycopy(T[i],0,T21[i-n/2],0,n/2);
				System.arraycopy(T[i],n/2,T22[i-n/2],0,n/2);
			}
			Thread h1=new Thread(new Runnable(){public void run(){sumar(C11,T11,n/2);}});
			Thread h2=new Thread(new Runnable(){public void run(){sumar(C12,T12,n/2);}});//sumar(C12,T12,n/2);
			Thread h3=new Thread(new Runnable(){public void run(){sumar(C21,T21,n/2);}});//sumar(C21,T21,n/2);
			Thread h4=new Thread(new Runnable(){public void run(){sumar(C22,T22,n/2);}});//sumar(C22,T22,n/2);
			h1.start();
			h2.start();
			h3.start();
			h4.start();
			try{
				h1.join();
				h2.join();
				h3.join();
				h4.join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			//copiar a C T?
			for(int i=0;i<n/2;i++){
				System.arraycopy(C11[i],0,C[i],0,n/2);
				System.arraycopy(C12[i],0,C[i],n/2,n/2);
			}
			for(int i=n/2;i<n;i++){
				System.arraycopy(C21[i-n/2],0,C[i],0,n/2);
				System.arraycopy(C22[i-n/2],0,C[i],n/2,n/2);
			}
		}
	}
//-----------------------------------------------------------------------------------------------
	public static void imprimir(double[][]M){
		for(int i=0;i<M.length;i++){
			for(int j=0;j<M[0].length;j++){
				System.out.print(M[i][j]+" ");
			}
			System.out.println();
		}
}
//-----------------------------------------------------------------------------------------------
}

/*
	ejemplo detallado para n=4 
                                     B11       B12
	a11 12	 a13 a14             b11 b12  b13 b14		
A11	a21 a22	 a23 a24  A12	     b21 b22  b23 b24 
			            
				     b31 b32  b33 b34	
	a31 a32  a33 a34             b41 b42  b43 b44
A21	a41 a42  a43 a44  A22		B21     B22


realizando el producto 
ABSTRACCION
A11 * B11 + A12*B21  = C11 en teoria pero el algoritmo almacenara los segundos productos en T matrices
C11		T11
A11* B12 + A12*B22 
C12		T12

A21*B11 + A22*B21 
C21	       T21

A21*B12 + A22*B22
C22		T22
esta abstraccion se emplea incluso cuando n=1 y se realiza los calculos basicos recursivos
son dos matrices C y T por separado, 
inciando con los productos
ejemplo para A11 y B11  se separo paran=4/2 , en arrays de 2x2 
se hace 8 llamadas a producto
1 de las llamadas es el tratamiento de A11 y B11
como n=2!=1 entonces cada A11 y B11 se subdivide en matrices de n=2/2 = 1 dimension
como se dijo hay 8 llamadas entonces cada llamada almacena el producto aij*bj
siguiente el principio  de la ABSTRACCION
los elementos del lado derecho se almacenan en C* mientras que los elementos izquieros se almacenan en  T*, C y t son distintos
para cada iteracion
las ocho llamadas 
	producto(A11,B11,C11,..)   producto(A12,B21,T11,..)
	producto(A11,B12,C12,.)	   producto(A12,B22,T12,..)
	producto(A21,B11,C21,..)   producto(A22,B21,T21,..)
	producto(A21,B12,C22,..)   producto(A22,B22,T22,..)
devuelven los productos  
	a11*b11  a11*b12    
  	a21*b11  a21*b12  en C*
	
	a12*b21  a12*b22
	a22*21  a22*b22    en T*      esto es distinguible de acuerdo a los nombres de los parametros en 
					las llamadas a  producto

entonces reemzamplando los cij y tij en C y T
entonces hacer la llamada 
	sumar(C,T,n) en este caso n=2

nuevamente la recursividad busca el caso bas n=1  
si no es asi 
	e divide las matrices C y T en matrices n/2 , en este caso n/2=1
	y llama  a 
	a11*b11 + a12*b21 <- suma(C11,T11,1)                     a11*b12 + a12*b22 <- suma(C12,T12,1)
	a21*b11 + a22*b21 <- suma(C21,T21,1)			 a21*b12 + a22*b22 <-suma(C22,T22,1)
retornando las sumas..
estas sumas se asignan a C de dimension n=2
entonces la llamada a producto (A11,B11,n=4/2) en el la funcion producto (primero)
se ha completado, 
las siguientes llamadas completan producto (A12, B21,T11)  realiza lo mismo y almacena los productos en T11
	
	producto (A11, B12, C12) en C12     producto(A12,B22,T12) en T12

	producto (A21,B11,C21) en C21      producto (A22,B21, T21) en T21
	
	producto(A21,B12,c22) en C22       producto (A22,B22, T22) en T22
n=4 se hizo las llamdas para n/2=2
se reemzambla los C (tiene los productos derechos)y T(tienen los productos izquierdos)

C:a11*b11+a12*b21   a11*b12+a12*b22        a11*b13 +a12*b23  a11*b14 +a12*b24   T:a13*b31+a14*b41  a31*b32+a14*b42  a13*b33+a14*b43 a13*b34 +a14*b44  
  a21*b11+a22*b21   a21*b12+a22*b22        a21*b13+a22*b23   a21*b14 +a22*b24	  a23*b31+a24*b41  a23*b32+a24*b42  a23*b33+a24*b43  a32*b34+a24*b44
   
  a31*b11+a32*b21   a31*b12+a32*b22  	   a31*b13+a32*b23   a31*b14+a32*b24     a33*b31+a34*b41  a33*b32+a34*b42   a33*b33+a34*b43  a33*b34+a34*b44
  a41*b11+a42*b21   a41*b12+a42*b22        a41*b13+a42*b23   a41*b14+a42*b24	 a43*b31+a44*b41  a43*b32+a44*b42   a43*b33+a44*b43  a43*b34+a44*b44

se tiene  C "global" y T "global"

se llama a sumar(C,T,n=4)
nuevamente se divide las matrices en submatrices 2x2 
	suma(C11,T11,) a su vez llamara a sumar() c11 +t11 global
					  sumar() c12+t12 global
					  sumar() c21+t21...
					  sumar() c22+t22

	suma(C12, T12) a su vez llamara a sumar() c13 +t13
					  sumar() c14+t14
					  sumar() c23 +t23
					  sumar() c24+t24
	suma(C21,T21)...

	suma(C22,T22)...
	las actualizaciones se hacen en la matriz C pasada como argumento, luego se reemzambla para cada iteracion
entonces para la primera llamada a sumar(n=4) se tienen los subbloques Cij 
 de modo que se reemzambla hasta obtener el C GLOBAL del producto

*/

