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
FifoQueue display = {};
FifoQueue print = {};
FifoQueue keyin = {};
FifoQueue mousein = {};
FifoQueue mu1 = {};
FifoQueue mu2 = {};
FifoQueue mu3 = {};
FifoQueue mu4 = {};

/**
 * IO and Mutex devices.
 */
IO *displayDevice;
IO *printDevice;
IO *keyInDevice;
IO *mouseInDevice;
MUTEX *mutex_critical;
MUTEX *mutex_write;
MUTEX *mutex_read;
MUTEX *m4;

int processID = 1;	//initial process ID start from 1.

/**
 * hold a random number that determine when a device will be completed.
 */
int nextDisplayComplete = 0;
int nextPrintComplete = 0;
int nextKeyComplete = 0;
int nextMouseComplete = 0;

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
 * while the pcb is running, if an IO interrupt occurs, this method check for which device is being requested.
 * return an integer identify of the IO device.
 */
int IO_RequestInterrupt(PCB *pcb);

/**
 * while the pcb is running, if a mutex interrupt occurs, this method check for which device is being requested.
 * return an integer identify of the mutex device.
 */
int Mutex_RequestInterrupt(PCB *pcb);

/**
 * when an IO service complete, this method check for which IO device was returned, so the next pcb in its waiting queue can run
 * return an integer identify of the IO device.
 */
int IO_CompleteInterrupt();

/**
 * while a pcb is running, if it release a mutex device, this method will check for which mutex was released,
 * so the next pcb in its waiting queue can take the mutex.
 * return an integer identify of the mutex device.
 */
int Mutex_ReleaseInterrupt(PCB *pcb);

/**
 * while running if an interrupt occurs, the pcb will be blocked
 * and put into the back of the IO waiting queue of the IO device identified by the interrupt code.
 * the next pcb in the ready queue will get to run.
 */
void IO_InterruptHandler(PCB * pcb, int interruptCode);

/**
 * when an IO complete interrupt occur, the first pcb in the IO device queue - identified by interrupt code waiting -
 * will be state as ready and placed back into the ready queue.
 */
void IO_CompleteHandler(int interruptCode);

/**
 * when a mutex request occur, the pcb will try to lock the mutex identified by the interrupt code.
 * if locking fails, the pcb will be put into the mutex waiting queue.
 */
void Mutex_InterruptHandler(PCB * pcb, int interruptCode);

/**
 * when a mutex - identified by interrupt code - is released,
 * the next pcb in its waiting queue will get the mutex lock.
 * this pcb then put back into the ready queue.
 */
void Mutex_ReleaseHandler(int interruptCode);

/**
 * generators of events.
 */
void generateDisplayReq(PCB *pcb);
void generateMouseInReq(PCB *pcb);
void generateKeyInReq(PCB *pcb);
void generatePrintReq(PCB *pcb);
void generateM1Req(PCB *pcb);
void generateM2Req(PCB *pcb);
void generateM3Req(PCB *pcb);
void generateM4Req(PCB *pcb);



int main(int argc, char *argv[]) {

	//Create the ready queue.
	//assign an integer to each process and count down as runtime instead of using system time.
	srand(time(NULL));
	int numberOfProcess = rand()%10 + 30;

	/**
	 * random completion time.
	 */
	nextDisplayComplete = rand()%200 + 400;
	nextPrintComplete = rand()%200 + 400;
	nextKeyComplete = rand()%100 + 400;
	nextMouseComplete = rand()%100 + 400;

	/**
	 * create IO and Mutex devices.
	 */
	displayDevice = (IO *)malloc(sizeof(IO));
	displayDevice->ioQueue = &display;
	printDevice = (IO *)malloc(sizeof(IO));
	printDevice->ioQueue = &print;
	keyInDevice = (IO *)malloc(sizeof(IO));
	keyInDevice->ioQueue = &keyin;
	mouseInDevice = (IO *)malloc(sizeof(IO));
	mouseInDevice->ioQueue = &mousein;

	mutex_critical = (MUTEX *)malloc(sizeof(MUTEX));
	mutex_critical->mutexQueue = &mu1;
	mutex_write = (MUTEX *)malloc(sizeof(MUTEX));
	mutex_write->mutexQueue = &mu2;
	mutex_read = (MUTEX *)malloc(sizeof(MUTEX));
	mutex_read->mutexQueue = &mu3;
	m4 = (MUTEX *)malloc(sizeof(MUTEX));
	m4->mutexQueue = &mu4;


	/**
	 * create ready queue
	 */
	while (numberOfProcess-->0) {
		PCB *pcb = (PCB *) malloc(sizeof(PCB));
		pcb->processID = processID++;
		pcb->state = NEW;
		pcb->runtime = rand()%1000 +1000;
		pcb->completedTime = 0;
		generateDisplayReq(pcb);
		generateKeyInReq(pcb);
		generateMouseInReq(pcb);
		generatePrintReq(pcb);
		generateM1Req(pcb);
		generateM2Req(pcb);
		generateM3Req(pcb);
		generateM4Req(pcb);
		enqueue(&readyQueue, pcb);
	}

	//schedule the ready queue to run.
	scheduler(&readyQueue);
	return 0;
}

/**
 * 	//process the queue and print out the result
 */
void scheduler(FifoQueue *queue){
	int programtime = 100000;
	PCB *pcb;
	pcb = dequeue(queue);
	int IO_com_irpt;
	int IO_req_irpt;
	int Mutex_req_irpt;
	int Mutex_release_irpt;

	while(programtime>0){
		IO_com_irpt = IO_CompleteInterrupt();
		if(IO_com_irpt) IO_CompleteHandler(IO_com_irpt);
		if(pcb!=NULL){
			pcb->state = RUNNING;

			while(pcb!=NULL && pcb->state == RUNNING && pcb->completedTime++ < pcb->runtime){
				programtime--;

				IO_com_irpt = IO_CompleteInterrupt();
				if(IO_com_irpt) IO_CompleteHandler(IO_com_irpt);

				Mutex_release_irpt = Mutex_ReleaseInterrupt(pcb);
				if(Mutex_release_irpt) Mutex_ReleaseHandler(Mutex_release_irpt);

				IO_req_irpt = IO_RequestInterrupt(pcb);
				if(IO_req_irpt) IO_InterruptHandler(pcb, IO_req_irpt);

				Mutex_req_irpt = Mutex_RequestInterrupt(pcb);
				if(Mutex_req_irpt) Mutex_InterruptHandler(pcb, Mutex_req_irpt);

			}
			if(pcb!=NULL && pcb->completedTime >= pcb->runtime){
				//int terminate = rand()%1;
				//if(!terminate){
				pcb->completedTime = 0;
				pcb->state = READY;
				enqueue(&readyQueue, pcb);
				//}
			}
		}else{
			programtime--;
		}
		pcb = dequeue(queue);
	}
}

int IO_RequestInterrupt(PCB *pcb){
		if(pcb->completedTime == pcb->displayRequest) return 1;
		if(pcb->completedTime == pcb->printRequest) return 2;
		if(pcb->completedTime == pcb->keyInRequest) return 3;
		if(pcb->completedTime == pcb->mouseInRequest) return 4;


	return 0;
}

int Mutex_RequestInterrupt(PCB *pcb){


	if(pcb->completedTime == pcb->m1Lock) return 1;
	if(pcb->completedTime == pcb->m2Lock) return 2;
	if(pcb->completedTime == pcb->m3Lock) return 3;
	if(pcb->completedTime == pcb->m4Lock) return 4;


	return 0;
}

int IO_CompleteInterrupt(){
	if(nextDisplayComplete-- == 0){
		nextDisplayComplete = rand()%200 + 400;
		return 1;
	}
	if(nextPrintComplete-- == 0){
		nextPrintComplete = rand()%200 + 400;
		return 2;
	}
	if(nextKeyComplete-- == 0){
		nextKeyComplete = rand()%300 + 400;
		return 3;
	}
	if(nextMouseComplete-- == 0){
		nextMouseComplete = rand()%100 + 400;
		return 4;
	}
	return 0;
}

int Mutex_ReleaseInterrupt(PCB *pcb){
	if(pcb->completedTime >= pcb->m1time) return 1;
	if(pcb->completedTime >= pcb->m2time) return 2;
	if(pcb->completedTime >= pcb->m3time) return 3;
	if(pcb->completedTime >= pcb->m4time) return 4;
	return 0;
}

void IO_InterruptHandler(PCB * pcb, int interruptCode){
	if(interruptCode == 1){
		pcb->state = WAITING;
		enqueue(displayDevice->ioQueue, pcb);
		printf("PID: %d - Display output - Blocked at quantum %d of %d total - awaiting completion.\n",
				pcb->processID,pcb->completedTime, pcb->runtime);
	}else if(interruptCode == 2){
		pcb->state = WAITING;
		enqueue(printDevice->ioQueue, pcb);
		printf("PID: %d - Printer output - Blocked at quantum %d of %d total - awaiting completion.\n",
				pcb->processID,pcb->completedTime, pcb->runtime);
	}else if(interruptCode == 3){
		pcb->state = WAITING;
		enqueue(keyInDevice->ioQueue, pcb);
		printf("PID: %d - Key in input - Blocked at quantum %d of %d total - awaiting completion.\n",
				pcb->processID, pcb->completedTime, pcb->runtime);
	}else if(interruptCode == 4){
		pcb->state = WAITING;
		enqueue(mouseInDevice->ioQueue, pcb);
		printf("PID: %d - Mouse click input - Blocked at quantum %d of %d total - awaiting completion.\n",
				pcb->processID,pcb->completedTime, pcb->runtime);
	}
}

void Mutex_InterruptHandler(PCB * pcb, int interruptCode){
	if(interruptCode == 1){
		if(lockMutex(mutex_critical, pcb)){
			pcb->m1time = pcb->completedTime + rand()%20;
		}else{
			pcb->state = WAITING;
			enqueue(mutex_critical->mutexQueue, pcb);
			printf("PID %d - MutexLock(m%d) - Blocked at quantum %d of %d total.\n",
					pcb->processID, interruptCode, pcb->completedTime, pcb->runtime);
		}
	}
	else if(interruptCode == 2){
		if(lockMutex(mutex_write, pcb)){
			pcb->m2time = pcb->completedTime + rand()%20;
		}else{
			pcb->state = WAITING;
			enqueue(mutex_write->mutexQueue, pcb);
			printf("PID %d - MutexLock(m%d) - Blocked at quantum %d of %d total.\n",
					pcb->processID, interruptCode, pcb->completedTime, pcb->runtime);
		}
	}
	else if(interruptCode == 3){
		if(lockMutex(mutex_read, pcb)){
			pcb->m3time = pcb->completedTime + rand()%20;
		}else{
			pcb->state = WAITING;
			enqueue(mutex_read->mutexQueue, pcb);
			printf("PID %d - MutexLock(m%d) - Blocked at quantum %d of %d total.\n",
					pcb->processID, interruptCode, pcb->completedTime, pcb->runtime);
		}
	}
	else if(interruptCode == 4){
		if(lockMutex(m4, pcb)){
			pcb->m4time = pcb->completedTime + rand()%20;
		}else{
			pcb->state = WAITING;
			enqueue(m4->mutexQueue, pcb);
			printf("PID %d - MutexLock(m%d) - Blocked at quantum %d of %d total.\n",
					pcb->processID, interruptCode, pcb->completedTime, pcb->runtime);
		}
	}
}

void Mutex_ReleaseHandler(int interruptCode){
	if(interruptCode == 1){
		unlockMutex(mutex_critical);
		if(mutex_critical->mutexQueue->head != NULL){
			PCB * pcb = dequeue(mutex_critical->mutexQueue);
			lockMutex(mutex_critical, pcb);
			pcb->state = READY;
			enqueue(&readyQueue, pcb);
		}
	} else if(interruptCode == 2){
		unlockMutex(mutex_write);
		if(mutex_write->mutexQueue->head != NULL){
			PCB * pcb = dequeue(mutex_write->mutexQueue);
			lockMutex(mutex_write, pcb);
			pcb->state = READY;
			enqueue(&readyQueue, pcb);
		}
	} else if(interruptCode == 3){
		unlockMutex(mutex_read);
		if(mutex_read->mutexQueue->head != NULL){
			PCB * pcb = dequeue(mutex_read->mutexQueue);
			lockMutex(mutex_read, pcb);
			pcb->state = READY;
			enqueue(&readyQueue, pcb);
		}
	} else if(interruptCode == 4){
		unlockMutex(m4);
		if(m4->mutexQueue->head != NULL){
			PCB * pcb = dequeue(m4->mutexQueue);
			lockMutex(m4, pcb);
			pcb->state = READY;
			enqueue(&readyQueue, pcb);
		}
	}
}


void IO_CompleteHandler(int interruptCode){
	if(interruptCode == 1){
		PCB *pcb = dequeue(displayDevice->ioQueue);
		if(pcb !=NULL){
			pcb->state = READY;
			enqueue(&readyQueue, pcb);
			printf("PID: %d - I/O completion interrupt  - Display output - back in ready queue at quantum %d of %d total.\n",
					pcb->processID, pcb->completedTime, pcb->runtime);
		}
	}else if(interruptCode == 2){
		PCB *pcb = dequeue(printDevice->ioQueue);
		if(pcb !=NULL){
			pcb->state = READY;
			enqueue(&readyQueue, pcb);
			printf("PID: %d - I/O completion interrupt  - Printer output - back in ready queue at quantum %d of %d total.\n",
					pcb->processID, pcb->completedTime, pcb->runtime);
		}
	}else if(interruptCode == 3){
		PCB *pcb = dequeue(keyInDevice->ioQueue);
		if(pcb !=NULL){
			pcb->state = READY;
			enqueue(&readyQueue, pcb);
			printf("PID: %d - I/O completion interrupt  - Keyboard input - back in ready queue at quantum %d of %d total.\n",
					pcb->processID, pcb->completedTime, pcb->runtime);
		}
	}else if(interruptCode == 4){
		PCB *pcb = dequeue(mouseInDevice->ioQueue);
		if(pcb !=NULL){
			pcb->state = READY;
			enqueue(&readyQueue, pcb);
			printf("PID: %d - I/O completion interrupt  - Mouse input - back in ready queue at quantum %d of %d total.\n",
					pcb->processID, pcb->completedTime, pcb->runtime);
		}
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

void generateDisplayReq(PCB *pcb){
	int i = rand()%4;;
	int quaterOfQuantum = pcb->runtime/4;

	pcb->displayRequest = i*quaterOfQuantum + rand()%quaterOfQuantum;

}

void generateKeyInReq(PCB *pcb){
	int i = rand()%4;;
	int quaterOfQuantum = pcb->runtime/4;

	pcb->displayRequest = i*quaterOfQuantum + rand()%quaterOfQuantum;

}

void generateMouseInReq(PCB *pcb){
	int i = rand()%4;;
	int quaterOfQuantum = pcb->runtime/4;

	pcb->mouseInRequest = i*quaterOfQuantum + rand()%quaterOfQuantum;

}

void generatePrintReq(PCB *pcb){
	int i = rand()%4;;
	int quaterOfQuantum = pcb->runtime/4;

	pcb->printRequest = i*quaterOfQuantum + rand()%quaterOfQuantum;

}

void generateM1Req(PCB *pcb){
	int i = rand()%4;;
	int quaterOfQuantum = pcb->runtime/4;

	pcb->m1Lock = i*quaterOfQuantum + rand()%quaterOfQuantum;

}

void generateM2Req(PCB *pcb){
	int i = rand()%4;;
	int quaterOfQuantum = pcb->runtime/4;

	pcb->m2Lock = i*quaterOfQuantum + rand()%quaterOfQuantum;

}

void generateM3Req(PCB *pcb){
	int i =  rand()%4;
	int quaterOfQuantum = pcb->runtime/4;

	pcb->m3Lock = i*quaterOfQuantum + rand()%quaterOfQuantum;

}

void generateM4Req(PCB *pcb){
	int i = rand()%4;;
	int quaterOfQuantum = pcb->runtime/4;

	pcb->m4Lock = i*quaterOfQuantum + rand()%quaterOfQuantum;

}


