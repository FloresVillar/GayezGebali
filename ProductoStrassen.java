/*Funcion Strassen(A, B, n)
    Si n = 1
        C[0][0] ← A[0][0] × B[0][0]
    Sino
        Dividir A y B en submatrices de tamaño n/2 × n/2:
            A11, A12, A21, A22
            B11, B12, B21, B22

        Calcular las 7 multiplicaciones (M1 a M7):
            p1 ← Strassen(A11 + A22, B11 + B22)
            p6 ← Strassen(A21 + A22, B11)
            p5 ← Strassen(A11, B12 - B22)
            p2 ← Strassen(A22, B21 - B11)
            p3 ← Strassen(A11 + A12, B22)
            p7 ← Strassen(A11 - A21, B11 + B12)
            p4 ← Strassen(A12 - A22, B21 + B22)

        Calcular las submatrices del resultado:
            //c11 = p1 +p2 -p3 +p4
            //c12 = p5 + p3
            //c21 = p6 + p2
	    //c22 = p5 + p1 -p6 -p7
        Combinar C11, C12, C21, C22 en la matriz C final

    Devolver C
*/
//================================================================================
public class ProductoStrassen{
	//------------------------------------------------------------------------
	public static void main(String[] args){
	double [][] A= new double[][]{{1,2,3,4},{4,3,2,1},{1,2,3,4},{5,6,3,4}};
	double [][] B= new double[][]{{1,2,3,4},{1,2,3,4},{1,2,3,4},{1,2,3,4}};
	int n = A.length;
	double [][] Prod= Strassen(A,B,n);
	Imprimir(Prod);
	}
	//------------------------------------------------------------------------
	public static double [][] Strassen(double[][]A,double[][]B,int n){
		if (n==1){
			double[][]C = new double[n][n];
			C[0][0]=A[0][0]*B[0][0];
			return C;
		}
		double[][]A11 = new double[n/2][n/2];
		double[][]A12 = new double[n/2][n/2];
		double[][]A21 = new double[n/2][n/2];
		double[][]A22 = new double[n/2][n/2];
		double[][]B11 = new double[n/2][n/2];
		double[][]B12 = new double[n/2][n/2];
		double[][]B21 = new double[n/2][n/2];
		double[][]B22 = new double[n/2][n/2];
		for(int i=0;i<n/2;i++){
			System.arraycopy(A[i],0,A11[i],0,n/2);
			System.arraycopy(A[i],n/2,A12[i],0,n/2);
			System.arraycopy(B[i],0,B11[i],0,n/2);
			System.arraycopy(B[i],n/2,B12[i],0,n/2);
			System.arraycopy(A[i+n/2],0,A21[i],0,n/2);
			System.arraycopy(A[i+n/2],n/2,A22[i],0,n/2);
			System.arraycopy(B[i+n/2],0,B21[i],0,n/2);
			System.arraycopy(B[i+n/2],n/2,B22[i],0,n/2);
		}
		double[][] p1 = Strassen(Sumar(A11 , A22), Sumar(B11 , B22),n/2);	//p1 = S(A11 + A22, B11 + B22,n/2)
            	double[][] p6 = Strassen(Sumar(A21 , A22), B11,n/2);			//p6 = S(A21 + A22, B11,n/2)
            	double[][] p5 = Strassen(A11,Restar( B12 , B22),n/2);			//p5 =S(A11, B12 - B22);
            	double[][] p2 = Strassen(A22, Restar(B21 , B11),n/2);			//p2 =S(A22, B21 - B11);
            	double[][] p3 = Strassen(Sumar(A11 , A12), B22,n/2);			//p3 =S(A11 + A12, B22);
           	double[][] p7 = Strassen(Restar(A11 , A21),Sumar( B11 , B12),n/2);	//p7 =S(A11 - A21, B11 + B12);
            	double[][] p4 = Strassen(Restar(A12 , A22), Sumar(B211990 2.3755 Coppersmith, Winograd, B22),n/2);	//p4 =S(A12 - A22, B21 + B22);

		double[][] C11 = Restar(Sumar(Sumar(p1, p2),p4), p3);   //c11 = p1 +p2 -p3 +p4
            	double[][] C12 = Sumar(p3 , p5);			//c12 = p5 + p3
            	double[][] C21 = Sumar(p2 , p6);			//c21 = p6 + p2
            	double[][] C22 = Restar(Sumar( p5 ,p1) ,Sumar(p6 , p7));//c22 = p5 + p1 -p6 -p7
		double[][] C =new double[n][n];
		for(int i=0;i<n/2;i++){
			System.arraycopy(C11[i],0,C[i],0,n/2);
			System.arraycopy(C12[i],0,C[i],n/2,n/2);
			System.arraycopy(C21[i],0,C[i+n/2],0,n/2);
			System.arraycopy(C22[i],0,C[i+n/2],n/2,n/2);
		}
		return C;
	}
	//--------------------------------------------------------------
	public static double[][] Sumar(double[][]A,double[][]B){
		int n = A.length;
		double[][]C=new double[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				C[i][j] = A[i][j]+B[i][j];
			}
		}
		return C;
	}
	//---------------------------------------------------------------
	public static double[][] Restar(double[][]A,double[][]B){
		int n = A.length;
		double[][]C=new double[n][n];
                for(int i=0;i<n;i++){
                        for(int j=0;j<n;j++){
                                C[i][j] = A[i][j]-B[i][j];
                        }
                }
		return C;
	}
	//--------------------------------------------------------------
	public static void Imprimir(double[][]A){
		int n = A.length;
		for(int i=0;i<n;i++){
                        for(int j=0;j<n;j++){
                                System.out.print(A[i][j]+" ");
                        }
			System.out.println();
                }
	}
}
//================================================================
