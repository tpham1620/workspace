#include <stdlib.h>
#include "mallok.h"
#include "orderedList.h"

void *pool;		// a void pointer for keep the pool
List ls;		//an ordered list to manage the pool


void create_pool(int size){

	pool = malloc(size);		//get the pool

	/*
	 * the first list will content only one element which has the whole pool
	 */
	ls = createList();
	ls->front->address = pool;
	ls->front->isFree = TRUE;
	ls->front->next = NULL;
	ls->front->size = size;
	ls->size = 1;
}

void *my_malloc(int size){
	int i;
	Node *curr = ls->front;
	for(i=0; i<ls->size; i++){
		if(curr->isFree == TRUE && curr->size >= size){

			if(curr->size == size){
				curr->isFree = FALSE;
				return curr->address;
			}

			Node *newNode = createNode();
			newNode->address = curr->address;
			newNode->size = size;
			newNode->isFree = FALSE;

			curr->address += size;
			curr->size -= size;

			void *toReturn = newNode->address;
			add(ls, newNode);
			return toReturn;
		}
		curr = curr->next;
	}
	return NULL;
}


void my_free(void *block){
	int i;

	Node *cond = ls->front;
	Node *pre = NULL;
	for(i = 0; i < ls->size; i++){
		if(cond->address == block){
			cond->isFree = TRUE;
			if(cond->next != NULL && cond->next->isFree == TRUE){
				cond->address = cond->next->address;
				cond->size += cond->next->size;
				Node *temp = cond->next;
				cond->next = cond->next->next;
				free(temp);
				ls->size--;
			}
			if(pre!=NULL && pre->isFree == TRUE){
				pre->address = cond->address;
				pre->size += cond->size;
				pre->next = cond->next;
				free(cond);
				ls->size--;
			}
			break;
		}
		pre = cond;
		cond = cond->next;
	}
}





