/**
 * Author: Tan Pham, Indiana Davis
 * Last modified: 11/1/2015
 */

#include <stdio.h>
#include "readyQueue.h"


PCB *PriorityQueue_dequeue(PriorityQueue *queue) {
	int i = 0;
	while (i < PRIORITY_CLASSES) {
		if(queue->queues[i].head != NULL){
			PCB *toreturn;
			if(queue->queues[i].head == queue->queues[i].tail) {
				toreturn = queue->queues[i].head;
				queue->queues[i].head = queue->queues[i].tail = NULL;
			}else{
				toreturn = queue->queues[i].head;
				queue->queues[i].head = queue->queues[i].head->next;
			}
			return toreturn;
		}
		i++;
	}
	return NULL;
}


void PriorityQueue_enqueue(PriorityQueue *queue, PCB *pcb) {
	pcb->next = NULL;
	int i = pcb->priority;
	if (queue->queues[i].head == NULL) {
		queue->queues[i].head = pcb;
		queue->queues[i].tail = pcb;
	} else {
		queue->queues[i].tail->next = pcb;
		queue->queues[i].tail = pcb;
	}
}

PCB *PriorityQueue_peek(PriorityQueue *queue){
	int i = 0;
	while (i < PRIORITY_CLASSES) {
		while (queue->queues[i].head != NULL) {
			PCB *pcb = queue->queues[i].head;
			return pcb;
		}
		i++;
	}
	return NULL;
}

PCB *PriorityQueue_peekProcess(PriorityQueue *queue, int processID) {
	PCB *curr;
	int i = 0;
	for (; i < PRIORITY_CLASSES; ++i) {
		curr = queue->queues[i].head;
		while (curr != NULL) {
			if (curr->processID == processID) {
				return curr;
			}
			curr = curr->next;
		}
	}
	return NULL;
}
PCB * PriorityQueue_dequeueProcess(PriorityQueue *queue, int processID){
	PCB *curr, *pre;
	int i = 0;
	for (; i < PRIORITY_CLASSES; ++i) {
		curr = queue->queues[i].head;
		pre = curr;
		while (curr != NULL) {
			if (curr->processID == processID) {
				if(curr == queue->queues[i].head){
					queue->queues[i].head = queue->queues[i].head->next;
				}
				pre->next = curr->next;
				return curr;
			}
			pre = curr;
			curr = curr->next;
		}
	}
	return NULL;
}
