#include<iostream>
#include<omp.h>
#include<stdlib.h>
using namespace std;

int main(){
	#pragma omp parallel
	{
		#pragma omp critical
		{
			int id = omp_get_thread_num();
			cout<<"el id es:"<<id<<endl;
		}
	}
	omp_set_num_threads(4);
	#pragma omp parallel
	{
		int id = omp_get_thread_num();
		int total = omp_get_num_threads();
		cout<<"el id: "<<id<<" el total:"<<total<<endl;
	}
	cout<<"pragma omp parallel for "<<endl;
	int suma = 0;
	#pragma omp parallel for reduction(+:suma)
	for(int i=0;i<8;i++){
		cout<<"i: "<<i<<"id: "<<omp_get_thread_num()<<endl;
		#pragma omp critical
		{
			suma+=1;
			//cout<<"suma:"<<suma<<endl;
		}
		cout<<"suma: "<<suma<<endl;
	}
	//producto escalar de vectores
	int i,n=100;
	int a[n],b[n],sum;
	for(i=0;i<n;i++){
		a[i]=i;
		b[i]=i+1;
	}
	#pragma omp parallel for schedule(dynamic,16) reduction(+:sum)
	for(i=0;i<n;i++){
		sum=sum+(a[i]*b[i]);
	}
	cout<<"la suma es : "<<sum;
	#pragma omp parallel  
}
