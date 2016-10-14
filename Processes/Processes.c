/**
 * Author: Tan Pham, Indiana Davis
 * Last modified: 11/1/2015
 */
#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include "readyQueue.h"



void traverse(PriorityQueue *queue);
void processes(PriorityQueue *queue);
void timeStamp(PriorityQueue *queue);



int main(int argc, char *argv[]) {

	//Create the ready queue.
	//assign an integer to each process and count down as runtime instead of using system time

	PriorityQueue readyQueue = {};
	int processID = 0;
	srand(time(NULL));
	int highPro = rand()%3 + 4;
	int midPro = rand()%20 + 80;
	int lowPro = rand()%10 +40;
	int priority;
	while (highPro-->0) {
		priority = rand()%2;
		PCB *pcb = (PCB *) malloc(sizeof(PCB));
		pcb->processID = processID++;
		pcb->priority = priority;
		pcb->state = NEW;
		pcb->runtime = rand()%10;
		pcb->timestamp = 0;

		PriorityQueue_enqueue(&readyQueue, pcb);
	}
	while (midPro-->0) {
		priority = rand()%4 + 3;
		PCB *pcb = (PCB *) malloc(sizeof(PCB));
		pcb->processID = processID++;
		pcb->priority = priority;
		pcb->state = NEW;
		pcb->runtime = rand()%20;
		pcb->timestamp = 0;

		PriorityQueue_enqueue(&readyQueue, pcb);
	}
	while (lowPro-->0) {
		priority = rand()%56 + 8;
		PCB *pcb = (PCB *) malloc(sizeof(PCB));
		pcb->processID = processID++;
		pcb->priority = priority;
		pcb->state = NEW;
		pcb->runtime = rand()%10;
		pcb->timestamp = 0;

		PriorityQueue_enqueue(&readyQueue, pcb);
	}

	//print out the original queue before processing
	traverse(&readyQueue);

	//process the queue and print out the result
	processes(&readyQueue);

	return 0;
}

/**
 * 	//process the queue and print out the result
 */
void processes(PriorityQueue *queue){
	PCB *pcb;
	pcb = PriorityQueue_dequeue(queue);
	int stamp = 0;
	while(pcb !=NULL){
		int timer = rand()%3 + 1;	//use an integer as a timer and count down.
		pcb->state = RUNNING;
		pcb->timestamp = 0;

		//if the priority has increased, set it back to the original
		if(pcb->tempPriority !=0){
			pcb->priority = pcb->tempPriority;
			pcb->tempPriority = 0;
		}

		//if the time slide has used up, re-enqueue, otherwise terminate.
		while(timer-->=0 && pcb->runtime-->=0){
			if(timer == 0){
				pcb->state = HALTED;
				PriorityQueue_enqueue(queue, pcb);
			}
			if(pcb->runtime == 0){
				pcb->state = PROCESSED;
				printf("Processed ID: %d\tPriority %d\tStarved %d time(s).\n", pcb->processID, pcb->priority, pcb->starved);
			}
		}

		//stamp all the remaining pcb in queue after every 100 processes to check if there is any starvation.
		if(++stamp > 100) {
			timeStamp(queue);
			stamp = 0;
		}

		pcb = PriorityQueue_dequeue(queue);
	}


}


/**
 * traverse the remaining queue to check for starvation
 * if there is a starvation, then dequeue, increase priority and re-enqueue it.
 */
void timeStamp(PriorityQueue *queue){
	PCB *curr, *starved, *temp;
	int i = 0;
	for(;i< PRIORITY_CLASSES; i++){
		curr = queue->queues[i].head;
		while (curr != NULL) {
			if(curr->timestamp > 0){
				temp = curr;
				curr = curr->next;
				starved = PriorityQueue_dequeueProcess(queue,temp->processID);
				starved->tempPriority = starved->priority;
				starved->priority = 1;
				starved->starved++;
				PriorityQueue_enqueue(queue, starved);
			}else{
				curr->timestamp++;
				curr = curr->next;
			}
		}
	}
}
void traverse(PriorityQueue *queue) {
	PCB *pcb;

	// Print the processes in PID order.
	printf("\nProcesses (PID ASC):\n");
	int i = 0;
	pcb = PriorityQueue_peekProcess(queue, i++);
	while (pcb != NULL) {
		printf("Process ID: %d\tPriority %d\n", pcb->processID, pcb->priority);
		pcb = PriorityQueue_peekProcess(queue, i++);
	}

	//Print the processes in priority order.
	printf("\nProcesses (Priority ASC):\n");
	i = 0;
	while (i < PRIORITY_CLASSES) {
		PCB *pcb = queue->queues[i].head;
		while (pcb != NULL) {
			printf("Process ID: %d\tPriority %d\n", pcb->processID, pcb->priority);
			pcb = pcb->next;
		}
		i++;
	}

}


