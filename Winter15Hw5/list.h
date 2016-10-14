/*
 * list.h
 *
 *  Created on: Feb 25, 2015
 *      Author: Tan Pham
 *  Header for the list.c source file
 */

#ifndef LIST_H_
#define LIST_H_

typedef struct list *List;
typedef struct node Node;

#define MAX_WORD 40

struct node{
	char word[MAX_WORD];
	Node *next;
	int countRed;
	int countLit;
	int smallerCount;
};

struct list{
	Node *front;
	int size;
};


void terminate(const char *);
Node* createNode(char*);
List createList();
void add(List, Node *);
void getSmallCount(List);
void sort(List);
void swap(Node *, Node *);

#endif /* LIST_H_ */
