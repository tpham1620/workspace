/**
 * Author: Tan Pham, Indiana Davis
 * Last modified: 10/29/2015
 */
#ifndef READY_QUEUE_H
#define READY_QUEUE_H

#define PRIORITY_CLASSES 64


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
    int timestamp;
    int starved;
    StateType state;
} PCB;

typedef struct fifo_queue {
    PCB *head;
    PCB *tail;
} FifoQueue;

typedef struct priority_queue {
    FifoQueue queues[PRIORITY_CLASSES];
} PriorityQueue;


PCB *PriorityQueue_dequeue(PriorityQueue *queue);
PCB * PriorityQueue_dequeueProcess(PriorityQueue *queue, int processID);

void PriorityQueue_enqueue(PriorityQueue *queue, PCB *pcb);

PCB *PriorityQueue_peekProcess(PriorityQueue *queue, int processID);
PCB *PriorityQueue_peek(PriorityQueue *queue);


#endif // READY_QUEUE_H
