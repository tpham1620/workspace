#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include "readyQueue.h"



int getPriority(void);

void traverse(PriorityQueue *queue);


int main(int argc, char *argv[]) {
    PriorityQueue readyQueue = {};
    int processID = 0;
    srand(time(NULL));
    int numOfPro = rand()%50 + 100;
    int priority = getPriority();
    while (numOfPro-->0) {
        PCB *pcb = (PCB *) malloc(sizeof(PCB));
        pcb->processID = processID++;
        pcb->priority = priority;
        pcb->state = NEW;
        pcb->runtime = rand()%10;
        pcb->timestamp = 0;

        PriorityQueue_enqueue(&readyQueue, pcb);

        priority = getPriority();
    }

    traverse(&readyQueue);

    return 0;
}

int getPriority(void) {
    srand(time(NULL));
    int priority = rand()%64;
    return priority;
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

    // Print the processes in priority order.
    printf("\nProcesses (Priority ASC):\n");
    pcb = PriorityQueue_dequeue(queue);
	while (pcb != NULL){
		printf("Process ID: %d\tPriority %d\n", pcb->processID, pcb->priority);
		pcb = PriorityQueue_dequeue(queue);
	}
}
