/*
 * list.c
 *
 *  Created on: Mar 1, 2015
 *      Author: Tan Pham
 *  The ordered list
 *  Each element is being added or removed from this list will stay in order.
 */


#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "orderedList.h"

/*
 * terminate if error occur.
 */
void terminate(const char *message){
	printf("%s\n", message);
	exit(EXIT_FAILURE);
}

/*
 * create a node of word initializing with a word string
 */
Node* createNode(){
	Node *n = malloc(sizeof(struct node));
	n->address = NULL;
	n->isFree = TRUE;
	n->size = 0;
	n->next = NULL;
	return n;
}

/*
 * create a linked list with a null front node.
 */
List createList(){
	List ls = malloc(sizeof(struct list));
	if(ls == NULL){
		terminate("Out of memory!");
	}
	ls->front = createNode();
	ls->size = 0;
	return ls;
}

/*
 * add a node to the list where it should be
 */
void add(List ls, Node *n){
	if(ls == NULL) terminate("list is null!");
	n->next = ls->front;
	ls->front = n;
	ls->size++;

	int i;
	Node *cond = ls->front;
	for(i = 0; i<ls->size; i++){
		if(cond->next==NULL)break;
		if(cond->address < cond->next->address){
			swap(cond, cond->next);
		}
		cond = cond->next;
	}
}

/*
 * swap the value of the two node, except its next pointer.
 */
void swap(Node* n1, Node *n2){
	Node *temp = createNode("");

	temp->address = n1->address;
	temp->isFree = n1->isFree;
	temp->size = n1->size;

	n1->address = n2->address;
	n1->isFree = n2->isFree;
	n1->size = n2->size;

	n2->address = temp->address;
	n2->isFree = temp->isFree;
	n2->size = temp->size;

	free(temp);

}




