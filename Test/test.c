/*
 * test.c
 *
 *  Created on: Jan 15, 2015
 *      Author: PaCMaN
 */


#include <stdio.h>
#define SUM(n1, n2) n1 + n2
char inverse(char ch){
	int i;
	char toReturn = 0;

	for(i = 0; i < 8; i++){
		toReturn <<= 1;
		if(ch & 1) toReturn |= 1;
		ch >>= 1;

	}

	return toReturn;
}
int fun1(int n){
	return n*n;
}
int fun2(int n){
	return n*n*n;
}
int foo(int n1, int n2, int fun(int n)){
	int t = 0;
	int i;
	for (i = n1; i <= n2; i++){
		t = t + fun(i);
	}
	return t;
}
union untag{
	int oneD[16];
	int twoD[4][4];
};
typedef union untag ExamUnion;

int main(void){

	ExamUnion eu;
	int r, c, i = 0;
	for( r = 0; r < 4; r++){
		for(c = 0; c <4; c++){
			eu.twoD[r][c] = 10*r + c;
		}
	}
	for(i = 0; i < 16; i++){
		eu.oneD[i] *= 2;
	}

	printf("%d\n", eu.twoD[2][3]);

	printf("%d\n", foo(1,4,fun1));
	printf("%d\n", foo(1,3,fun2));

	printf("%d\n", SUM(SUM(4,5),6));
	printf("%d\n", SUM(3,4)*SUM(5,6));

	return 0;
}
