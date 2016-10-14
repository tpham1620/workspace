/*
 * CustomerList.c
 *
 *  Created on: Feb 15, 2015
 *      Author: Tan Pham
 *      TCSS333 Winter 2015
 *
 ******************************************************************************************************
 *
 *NOTE: Please make sure to put all the files in the project folder to have it work correctly.
 *
 */


#include <stdio.h>
#include <string.h>

/*
 * define macros.
 */
#define MAX_NAME 30			//Max length of name
#define MAX_ITEM 10			//Max number of items per person
#define MAX_CUSTOMER 20		//Max number of customers per customer list


/*
 * define and declare structures
 */
typedef struct customer Customer;
typedef struct item Item;

struct item{						//items structure
	char itemName[MAX_NAME];
	int count;
	double price;
	double value;
};
struct customer {					//customer structure
	char customerName[MAX_NAME];
	Item items[MAX_ITEM];
	int itemCount;
	double total;
};

/*
 * preprocessor.
 */
void readFile(Customer *, char *, int *);
void output(Customer *, char *, int);
void swapCustomer(Customer *, Customer *);
void swapItem(Item *, Item *);
void sortCustomer(Customer *, int);
void sortItem(Item *, int);

/*
 * main function
 */
int main(void){

	setvbuf(stdout, NULL, _IONBF, 0);

	/*
	 * name of in and out files
	 */
	char infileName[] = "hw4input.txt";
	char outfileTime[] = "hw4time.txt";
	char outfileMoney[] = "hw4money.txt";

	Customer list[MAX_CUSTOMER] = {{"", {{"",0,0,0}}, 0,0}};	//the list of customers
	int customerCount = 0;										//keep track of how many customers are in the list

	readFile(list, infileName, &customerCount);					//read in the input file and store data to list

	output(list, outfileTime,customerCount);					//keep everything and output to the time file

	sortCustomer(list, customerCount);							//sort the list according to assignment

	output(list, outfileMoney, customerCount);					//output the list after sorting to money file


	return 0;
//end of main
}

/*
 * swap the item that store in the address it1 and it2
 */
void swapItem(Item *it1, Item *it2){
	Item temp = *it1;
	*it1 = *it2;
	*it2 = temp;
}

/*
 * swap the customer that store in the address c1 and c2
 */
void swapCustomer(Customer *c1, Customer *c2){
	Customer temp = *c1;
	*c1 = *c2;
	*c2 = temp;
}

/*
 * sort the customer base on the total order value
 */
void sortCustomer(Customer *list, int count){
	int i;
	while(count){
		for(i=0; i < count + 1; i++){
			sortItem(list[i].items,list[i].itemCount);						//sort each customer while sort the list.
			if(list[i].total<list[i+1].total) swapCustomer(&list[i],&list[i+1]);
		}
		count--;
	}
}

/*
 * sort the items of a customer base on the value of items
 */
void sortItem(Item *items, int count){
	int i;
	while(count){
		for(i = 0; i < count; i++){
			if(items[i].value < items[i+1].value) swapItem(&items[i],&items[i+1]);
		}
		count--;
	}
}

/*
 * output the list to file.
 */
void output(Customer *list, char *outTime, int count){
	FILE *file = fopen(outTime,"w");

	int i, j;
	for(i = 0; i < count; i++){
		fprintf(file, "%s, ", list[i].customerName);
		fprintf(file,"Total Order = $%.2lf\n\r",list[i].total);
		fprintf(file,"\n\r");
		for(j=0; j < list[i].itemCount; j++){
			fprintf(file, "%s %d $%.2lf, Item Value = $%.2lf",
					list[i].items[j].itemName, list[i].items[j].count, list[i].items[j].price, list[i].items[j].value);
			fprintf(file,"\n\r");
			fprintf(file,"\n\r");	//its kinda weird but i need this second line separator to get the expected output

		}
		fprintf(file, "\n\r");
	}
}



/*
 * read the input file data, and store into the list of customer.
 */
void readFile(Customer *list, char *fileName, int *count){
	FILE *file = fopen(fileName, "r");
	char cusName[MAX_NAME], itName[MAX_NAME], ch = NULL;
	int itCount;
	double price;
	double value;
	while(*count < MAX_CUSTOMER &&
			fscanf(file,"%s %d %s %c %lf",cusName, &itCount, itName, &ch, &price)==5){
		int i = 0;
		int found = 0;
		value = itCount*price;

		for(; i < *count +1; i++){	//if the customer already exists, just add the new items
			if(strcmp(list[i].customerName, cusName) == 0 && list[i].itemCount<10){
				strcpy(list[i].items[list[i].itemCount].itemName, itName);
				list[i].items[list[i].itemCount].count = itCount;
				list[i].items[list[i].itemCount].price = price;
				list[i].items[list[i].itemCount].value = value;
				list[i].itemCount++;
				list[i].total += value;
				found = 1;
				break;
			}
		}
		if(found == 0){		//if the customer doesnot eixst, add new customer and items
			strcpy(list[i-1].customerName, cusName);
			strcpy(list[i-1].items[list[i-1].itemCount].itemName, itName);
			list[i-1].items[list[i-1].itemCount].count = itCount;
			list[i-1].items[list[i-1].itemCount].price = price;
			list[i-1].items[list[i-1].itemCount].value = value;
			list[i-1].total += value;
			list[i-1].itemCount++;
			(*count)++;
		}
	}
}
