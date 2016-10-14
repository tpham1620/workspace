/*
 * Process.c
 *
 *  Created on: Nov 19, 2015
 *      Author: Tan Pham, Indiana
 */


#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include "fifoQueue.h"


FifoQueue readyQueue = {}; 	//the ready queue.

/**
 * IO and Mutex waiting queues.
 */
FifoQueue mu1 = {};
FifoQueue mu2 = {};
FifoQueue mu3 = {};

/**
 * IO and Mutex devices.
 */

MUTEX *mutex_critical;
MUTEX *mutex_write;
MUTEX *mutex_read;


int processID = 1;	//initial process ID start from 1.

/**
 * traverse and print out the current ready queue.
 */
void traverse(FifoQueue *queue);

/**
 * the scheduler.
 * schedule the processes in ready queue to run.
 */
void scheduler(FifoQueue *queue);



/**
 * when a mutex request occur, the pcb will try to lock the mutex identified by the interrupt code.
 * if locking fails, the pcb will be put into the mutex waiting queue.
 */
int Mutex_InterruptHandler(MUTEX *m, PCB * pcb);

/**
 * when a mutex - identified by interrupt code - is released,
 * the next pcb in its waiting queue will get the mutex lock.
 * this pcb then put back into the ready queue.
 */
void Mutex_ReleaseHandler(MUTEX *m);



int main(int argc, char *argv[]) {

	//Create the ready queue.
	//assign an integer to each process and count down as runtime instead of using system time.
	srand(time(NULL));
	//int numberOfProcess = rand()%10+50;
	int pro_con_pairs = 10;


	/**
	 * create IO and Mutex devices.
	 */

	mutex_critical = (MUTEX *)malloc(sizeof(MUTEX));
	mutex_critical->mutexQueue = &mu1;
	mutex_write = (MUTEX *)malloc(sizeof(MUTEX));
	mutex_write->mutexQueue = &mu2;
	mutex_read = (MUTEX *)malloc(sizeof(MUTEX));
	mutex_read->mutexQueue = &mu3;


	/**
	 * create ready queue
	 */

	while (pro_con_pairs-->0) {
		processID++;

		Space *share = (Space *)malloc(sizeof(Space));
		share->signal_r = 1;
		share->signal_w = 0;
		share->space = 0;

		PCB *pcb_producer = (PCB *) malloc(sizeof(PCB));
		pcb_producer->type = PRODUCER;
		pcb_producer->processID = processID;
		pcb_producer->state = NEW;
		pcb_producer->space = share;
		pcb_producer->runtime = rand()%1000 +1000;
		pcb_producer->completedTime = 0;

		enqueue(&readyQueue, pcb_producer);

		PCB *pcb_consumer = (PCB *) malloc(sizeof(PCB));
		pcb_consumer->type = CONSUMER;
		pcb_consumer->processID = processID;
		pcb_consumer->state = NEW;
		pcb_consumer->space = share;
		pcb_consumer->runtime = rand()%1000 +1000;
		pcb_consumer->completedTime = 0;
		enqueue(&readyQueue, pcb_consumer);
	}

	//schedule the ready queue to run.
	scheduler(&readyQueue);
	return 0;
}

/**
 * 	//process the queue and print out the result
 */
void scheduler(FifoQueue *queue){
	int programtime = 10000;
	PCB *pcb;
	pcb = dequeue(queue);

	unlockMutex(mutex_critical);
	unlockMutex(mutex_write);
	lockMutex(mutex_read, pcb);
	while(programtime>0){

		if(pcb!=NULL){
			pcb->state = RUNNING;

			while(pcb->state == RUNNING && pcb->completedTime++ < pcb->runtime){
				programtime--;
				if(pcb->type == PRODUCER){
					if(Mutex_InterruptHandler(mutex_critical, pcb)){
						if(Mutex_InterruptHandler(mutex_write, pcb)){
							pcb->space->space++;
							Mutex_ReleaseHandler(mutex_write);
						}
						Mutex_ReleaseHandler(mutex_critical);
					}
				}else if(pcb->type == CONSUMER){
					if(Mutex_InterruptHandler(mutex_critical, pcb)){
						if(Mutex_InterruptHandler(mutex_read,pcb)){
							pcb->read[pcb->space->space] = pcb->space->space;
							Mutex_ReleaseHandler(mutex_read);
						}
						Mutex_ReleaseHandler(mutex_critical);
					}
				}

			}
			if(pcb->completedTime >= pcb->runtime){
				//int terminate = rand()%1;
				//if(!terminate){
				pcb->completedTime = 0;
				pcb->state = READY;
				enqueue(&readyQueue, pcb);
				//}
			}
		}else{
			programtime--;


			pcb = dequeue(queue);

		}
	}

}



int Mutex_InterruptHandler(MUTEX *m, PCB * pcb){
	if(lockMutex(m, pcb)){
		printf("No deadlock detected.\n");
		return 1;
	}else{
		pcb->state = WAITING;
		enqueue(m->mutexQueue, pcb);
		if(m->mutexQueue->size >= 10){
			printf("deadlock detected for processes %d-producer and %d-consumer.\n", peek(m->mutexQueue)->processID,  peek(m->mutexQueue)->processID);
		}
		return 0;
	}
}

void Mutex_ReleaseHandler(MUTEX *m){

	unlockMutex(m);
	if(mutex_critical->mutexQueue->head != NULL){
		PCB * pcb = dequeue(mutex_critical->mutexQueue);
		lockMutex(mutex_critical, pcb);
		pcb->state = READY;
		enqueue(&readyQueue, pcb);
	}
}

/**
 * traverse the remaining queue to check for starvation
 * if there is a starvation, then dequeue, increase priority and re-enqueue it.
 */


void traverse(FifoQueue *queue) {
	PCB *pcb;
	// Print the processes in PID order.
	printf("\nProcesses (PID ASC):\n");
	int i = 1;
	pcb = peekProcess(queue, i++);
	while (pcb != NULL) {
		printf("Process ID: %d, runtime: %d, \n", pcb->processID, pcb->runtime);
		//printf("display IO: %d, %d, %d, %d\n",pcb->displayRequest[0], pcb->displayRequest[1], pcb->displayRequest[2], pcb->displayRequest[3]);
		//printf("display M1: %d, %d, %d, %d\n",pcb->m1Lock[0], pcb->m1Lock[1], pcb->m1Lock[2], pcb->m1Lock[3]);
		printf("\n");
		pcb = peekProcess(queue, i++);
	}
}
