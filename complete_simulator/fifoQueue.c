/*
 * FifoQueue.c
 *
 *  Created on: Nov 18, 2015
 *      Author: Tan Pham, Indiana
 */
#include <stdio.h>
#include <stdlib.h>
#include "fifoQueue.h"


PCB * dequeue(FifoQueue *queue) {
	if(queue->head != NULL){
		PCB *toreturn;
		if(queue->head == queue->tail) {
			toreturn = queue->head;
			queue->head = queue->tail = NULL;
			queue->size = 0;
		}else{
			toreturn = queue->head;
			queue->head = queue->head->next;
			queue->size--;
		}
		return toreturn;
	}
	return NULL;
}

void enqueue(FifoQueue *queue, PCB *pcb) {
	pcb->next = NULL;
	if (queue->head == NULL) {
		queue->head = pcb;
		queue->tail = pcb;
		queue->size = 1;
	} else {
		queue->tail->next = pcb;
		queue->tail = pcb;
		queue->size++;
	}
}

PCB * peek(FifoQueue *queue){
	return queue->head;
}

PCB * peekProcess(FifoQueue *queue, int processID) {
	PCB *curr = queue->head;
	while (curr != NULL) {
		if (curr->processID == processID) {
			return curr;
		}
		curr = curr->next;
	}
	return curr;
}

PCB * dequeueProcess(FifoQueue *queue, int processID){
	PCB *curr, *pre;
	curr = queue->head;
	pre = curr;
	while (curr != NULL) {
		if (curr->processID == processID) {
			if(curr == queue->head){
				queue->head = queue->head->next;
			}
			pre->next = curr->next;
			return curr;
		}
		pre = curr;
		curr = curr->next;
	}

	return NULL;
}


int lockMutex(MUTEX * m, PCB *owner){
	if(m->lock == 0){
		m->lock = 1;
		m->ownerID = owner->processID;
		m->owner_type = owner->type;
		return 1;
	}
	return 0;
}

void unlockMutex(MUTEX *m){
	m->lock = 0;
	m->ownerID = 0;
}

