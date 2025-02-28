#include <iostream>
//#include <cilk.h>

using namespace std;
int fibonacci(int);
int main(){
	cout<<"ingrese el numero"<<endl;
	int numero;
	cin>>numero;
	int fib = fibonacci(numero);
	cout<<"fibonacci de"<<numero<<": "<<fib;
	return 0;
}

int fibonacci(int n){
	if(n<2){
		return 1;
	}else{
		int x,y;
		x = fibonacci(n-1);
		y = fibonacci(n-2);
		return x + y;
	}
}
// compilar g++ nombre
//ejecutar ./a.out 
