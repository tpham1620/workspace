/*
 * FifoQueue.h
 *
 *  Created on: Nov 18, 2015
 *      Author: Tan Pham, Indiana
 */

#ifndef FIFOQUEUE_C_
#define FIFOQUEUE_C_


typedef enum {
    NEW,
    READY,
    RUNNING,
    WAITING,
    HALTED,
    PROCESSED
} StateType;


typedef struct pcb {
    struct pcb *next;
    int processID;
    int priority;
    int tempPriority;
    int runtime;
    int completedTime;
    int timestamp;
    int starved;
    StateType state;

    /**
     * arrays of events
     */
    int keyInRequest;
    int	mouseInRequest;
    int displayRequest;
    int printRequest;
    int m1Lock;
    int m2Lock;
    int m3Lock;
    int m4Lock;

    /**
     * hold the time of the next mutex will be unlocked after got locked.
     */
    int m1time;
    int m2time;
    int m3time;
    int m4time;

} PCB;


typedef struct fifo_queue {
    PCB *head;
    PCB *tail;
    int size;
} FifoQueue;

typedef struct iodevice {
	FifoQueue *ioQueue;
	int timeinnerval;
} IO;

typedef struct mutexdevice {
	FifoQueue *mutexQueue;
	int lock;
	int ownerID;
} MUTEX;

int lockMutex(MUTEX *m, PCB *owner);
void unlockMutex(MUTEX *m);

void ioComplete(IO *device);

PCB *dequeue(FifoQueue *queue);
PCB *dequeueProcess(FifoQueue *queue, int processID);
void enqueue(FifoQueue *queue, PCB *pcb);
PCB *peekProcess(FifoQueue *queue, int processID);
PCB *peek(FifoQueue *queue);

#endif /* FIFOQUEUE_C_ */
