/*
 * list.c
 *
 *  Created on: Feb 25, 2015
 *      Author: Tan Pham
 */


#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "list.h"

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
Node* createNode(char *w){
	Node *n = malloc(sizeof(struct node));
	strcpy(n->word,w);
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
	ls->front = NULL;
	ls->size = 0;
	return ls;
}

/*
 * add a node to the front of the list
 */
void add(List ls, Node *n){
	if(ls == NULL) terminate("list is null!");
	n->next = ls->front;
	ls->front = n;
	ls->size++;
}

/*
 *decide with file the word appear less frequently, and store the least one into smallerCount
 */
void getSmallCount(List ls){
	Node *cond = ls->front;
	while(cond!=NULL){
		if(cond->countLit<cond->countRed){
			cond->smallerCount = cond->countLit;
		}else cond->smallerCount = cond->countRed;
		cond = cond->next;
	}
}

/*
 * sort the lsit base on smaller count
 */
void sort(List ls){
	int listSize = ls->size;
	int i;
	List sortedList = createList();
	while(listSize){
		Node *cond = ls->front;
		for(i = 0; i<listSize; i++){
			if(cond->next==NULL)break;
			if(cond->smallerCount < cond->next->smallerCount){
				swap(cond, cond->next);
			}
			cond = cond->next;
		}
		add(sortedList,cond);
		listSize--;
	}
	ls = sortedList;
	free(sortedList);

}

/*
 * swap the value of the two node, except its next pointer.
 */
void swap(Node* n1, Node *n2){
	Node *temp = createNode("");

	strcpy(temp->word, n1->word);
	temp->countLit = n1->countLit;
	temp->countRed = n1->countRed;
	temp->smallerCount = n1->smallerCount;

	strcpy(n1->word, n2->word);
	n1->countLit = n2->countLit;
	n1->countRed = n2->countRed;
	n1->smallerCount = n2->smallerCount;


	strcpy(n2->word, temp->word);
	n2->countLit = temp->countLit;
	n2->countRed = temp->countRed;
	n2->smallerCount = temp->smallerCount;

	free(temp);

}




