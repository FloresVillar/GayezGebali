#include <iostream>
using namespace std;

void intercambiar(int& x, int& y);

int main(){
	int x=1,y=5;
	intercambiar(x, y);
	x= 2*x;
	cout<<"x: "<<x<<endl;
	cout<<"y: "<<y<<endl;
}

void intercambiar(int& x,int& y){
	int temp;
	temp = x;
	x= y;
	y = temp;
}
