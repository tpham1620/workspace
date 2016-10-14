/*
 * Main.c
 *
 *  Created on: Mar 3, 2015
 *      Author: Tan Pham
 *  Testing the application
 */


#include <stdio.h>
#include <stdlib.h>
#include "mallok.h"

/*
 * expect to have 100 call to my_malloc
 */
void test1(){

	create_pool(1000);

	void *p = my_malloc(10);
	int count = 0;

	while(p != NULL){
		count++;
		p = my_malloc(10);
	}

	printf("test1 \n%d", count);

}

/*
 * this test to make sure the my_malloc wont work if the space available is smaller than the request
 */
void test2(){
	printf("\ntest2");
	create_pool(1000);
	void *p1 = my_malloc(200);
	void *p2 = my_malloc(200);
	void *p3 = my_malloc(200);
	void *p4 = my_malloc(200);
	void *p5 = my_malloc(200);
	my_free(p3);

	void *p6 = my_malloc(201);		//should be failed
	if(p6 == NULL){
		printf("\np6 fails");
	}else printf("\np6 success");

	void *p7 = my_malloc(150);		//should be succeed
	if(p7 == NULL){
		printf("\np7 fails");
	}else printf("\np7 success");

	void *p8 = my_malloc(60);			//should be failed
	if(p8 == NULL) printf("\np8 fails");

	void *p9 = my_malloc(50);			//should be succeed
	if(p9 == NULL){
		printf("\np9 fails");
	}else printf("\np9 success");
}

/*
 * this test to make sure after free blocks, if two or three blocks are next to each other, they then combine together
 */
void test3(){
	printf("\ntest3");
	create_pool(1000);
	void *p1 = my_malloc(200);
	void *p2 = my_malloc(200);
	void *p3 = my_malloc(200);
	void *p4 = my_malloc(200);
	void *p5 = my_malloc(200);

	my_free(p2);
	void *p6 = my_malloc(201);   //should be failed
	if(p6 == NULL){
		printf("\np6 fails");
	}else printf("\np6 success");

	my_free(p4);
	void *p7 = my_malloc(250);    //should be failed
	if(p7 == NULL){
		printf("\np7 fails");
	}else printf("\np7 success");

	//after free p3, we should have a big block of 600 from p2 to p4.
	my_free(p3);
	void *p8 = my_malloc(300);		//should be succeed
	if(p8 == NULL){
		printf("\np8 fails");
	}else printf("\np8 success");

	my_free(p8);
	void *p9 = my_malloc(600);		//should be succeed
	if(p9 == NULL){
		printf("\np9 fails");
	}else printf("\np9 success");
}

/*
 * this test should be all succeed and print out some data.
 */
void test4(){
	printf("\ntest4");
	create_pool(1000);

	void *p1 = my_malloc(250);
	if(p1 == NULL){
		printf("\np1 fails");
	}else printf("\np1 success");
	void *p2 = my_malloc(250);
	if(p2 == NULL){
		printf("\np2 fails");
	}else printf("\np2 success");
	void *p3 = my_malloc(250);
	if(p3 == NULL){
		printf("\np3 fails");
	}else printf("\np3 success");
	void *p4 = my_malloc(250);
	if(p4 == NULL){
		printf("\np4 fails");
	}else printf("\np4 success");

	p1 = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	p2 = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

	printf("\n%s\n%s", p1, p2);
}

/*
 * this test should be all succeed.
 */
void test5(){
	printf("\ntest5");
	int i;
	create_pool(1000);

	void *p = my_malloc(1000);
	if(p == NULL){
		printf("\np fails");
	}else printf("\np success");
	my_free(p);

	void *p1 = my_malloc(250);
	if(p1 == NULL){
		printf("\np1 fails");
	}else printf("\np1 success");
	void *p2 = my_malloc(250);
	if(p2 == NULL){
		printf("\np2 fails");
	}else printf("\np2 success");
	void *p3 = my_malloc(250);
	if(p3 == NULL){
		printf("\np3 fails");
	}else printf("\np3 success");
	void *p4 = my_malloc(250);
	if(p4 == NULL){
		printf("\np4 fails");
	}else printf("\np4 success");

	my_free(p1);
	my_free(p2);
	my_free(p3);
	my_free(p4);


	for(i = 0; i <10; i++){
		void *p = my_malloc(100);
		if(p == NULL){
			printf("\np%d fails", i+1);
		}else printf("\np%d success", i+1);

	}
}
int main(void){

	test1();
	printf("\n");
	test2();
	printf("\n");
	test3();
	printf("\n");
	test4();
	printf("\n");
	test5();

	return 0;
}
