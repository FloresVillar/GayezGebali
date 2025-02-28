
#include <iostream>
//#include <cilk.h>
using namespace std;
void intercambiar(int& x,int& y);
// cilk_main() 
int main(){
	int x=1,y=10;
	//cilk_spawn intercambiar(x,y);
	intercambiar(x,y);
	x = 2*x;
	//cilk_sync;
	cout<<"x: "<<x<<endl;
	cout<<"y: "<<y<<endl;
	return 0;
}

void intercambiar(int& x, int& y){
	int temp;
	temp = x;
	x = y;
	y = temp;
}
