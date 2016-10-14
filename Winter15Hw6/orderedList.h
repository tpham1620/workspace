/*
 * list.h
 *
 *  Created on: Mar 1, 2015
 *      Author: Tan Pham
 *  Header for the orderedList.c source file
 */

#ifndef LIST_H_
#define LIST_H_

typedef enum {TRUE = 1, FALSE = 0} boolean;
typedef struct list *List;
typedef struct node Node;


struct node{
	Node *next;
	boolean isFree;
	void *address;
	int size;
};

struct list{
	Node *front;
	int size;
};


void terminate(const char *);
Node* createNode();
List createList();
void add(List, Node *);
void swap(Node *, Node *);

#endif /* LIST_H_ */
