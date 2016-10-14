/*
 *  File name: popularName.c
 *
 *  Created on: Feb 02, 2015
 *      Author: Tan Pham
 *      TCSS333 Winter 2015
 *
 ******************************************************************************************************
 * This program will read in 10 files of popular name and create a summary of the most 100th popular
 * name in each of the year of 1920, 1930, 1940, 1950, 1960, 1970, 1980, 1990, 2000, and 2010.
 ******************************************************************************************************
 *
 *NOTE: Please make sure to put all the files in the project folder to have it work correctly.
 *
 */

#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>

/*
 * define macros.
 */
#define MAX_LEN_NAME 20   //max length of name.
#define MAX_NUM_NAME 400  //max number of names in total summary.
#define NUM_YEAR 10		  //number of years

/*
 * preprocessor.
 */
void readLine(FILE *, char *);
void output(char (*names)[MAX_LEN_NAME], int (*ranks)[NUM_YEAR], FILE *file);
void readFile(char (*names)[MAX_LEN_NAME], int (*ranks)[NUM_YEAR],FILE *file, int);
void readFiles(char (*names)[MAX_LEN_NAME], int (*ranks)[NUM_YEAR],FILE *file);
void swapName(char *n1, char *n2);
void swapRanks(int *r1, int *r2);
void sort(char (*names)[MAX_LEN_NAME], int (*ranks)[NUM_YEAR]);

/*
 * main function.
 */
int main(void){

	setvbuf(stdout, NULL, _IONBF, 0);

	char names[MAX_NUM_NAME][MAX_LEN_NAME] = {""};	//2D array that holds names.
	int ranks[MAX_NUM_NAME][NUM_YEAR] = {{0}};		//2D array - correspond with array names - holds ranks of names.


	FILE *infile = NULL, *outfile = NULL;			//in and out file pointers.

	readFiles(names,ranks,infile);					//read all the files, collect datas and store.

	sort(names,ranks);								//sort data

	output(names,ranks,outfile);					//output to the console and file.

	//end of main.
	return 0;
}


/**
 * sort function.
 * pass in two pointers parameters to array names and ranks
 * pointer names points to an array content a name (a row) from the 2D array in main
 * similar pointer ranks point to an array of integer ranks.
 */
void sort(char (*names)[MAX_LEN_NAME], int (*ranks)[NUM_YEAR]){
	int i, len;
	len = MAX_NUM_NAME - 35;

	while(len){
		for(i=0; i<len;i++){
			if(strcmp(names[i],"")==0) break;		//break if pass in name equal to null
			if(strcmp(names[i],names[i+1]) > 0){	//swap names and ranks if they are not in order.
				swapName(names[i],names[i+1]);
				swapRanks(ranks[i],ranks[i+1]);
			}
		}
		len--;
	}
}

/**
 * swap function for name.
 */
void swapName(char *n1, char *n2){
	char temp[MAX_LEN_NAME] = "";
	strcpy(temp, n1);
	strcpy(n1,n2);
	strcpy(n2,temp);
}

/**
 * swap function for ranks
 */
void swapRanks(int *r1, int *r2){
	int i, temp[NUM_YEAR];

	for (i=0;i<NUM_YEAR;i++){
		*(temp+i) = *(r1 + i);
	}
	for (i=0;i<NUM_YEAR;i++){
		*(r1+i) = *(r2 + i);
	}
	for (i=0;i<NUM_YEAR;i++){
		*(r2+i) = *(temp + i);
	}
}

/**
 * this function call o loop to make 10 appropriate filenames, open them
 * and then call function to read, collect and store data of each file.
 *
 */
void readFiles(char (*names)[MAX_LEN_NAME], int (*ranks)[NUM_YEAR],FILE *file){
	int i;
	for(i=0; i < NUM_YEAR; i++){
		char fileName[20] = "names\\yob";
		char year[4] = "";
		sprintf(year, "%d", (1920 + i*NUM_YEAR));
		strcat(fileName,strcat(year,".txt"));


		file = fopen(fileName,"r");
		if(file == NULL) printf("open file fail %s", fileName);

		readFile(names,ranks,file,i);

		fclose(file);

	}
}

/**
 * this function read 100 first lines of the pass in file
 * collect data and store in the 2D array name and ranks.
 */
void readFile(char (*names)[MAX_LEN_NAME], int (*ranks)[NUM_YEAR],FILE *file,int i){
	int j,k;
	for(j = 0; j < 100; j++){
		char name[MAX_LEN_NAME] = "";

		readLine(file,name);					//read a complete line of the pass in file.
												//then find the name and store in memory.


		for(k = 0; k < MAX_NUM_NAME; k++){
			if(strcmp(names[k],"") == 0){
				strcpy(names[k],name);			//if the name does not exist, add it and its rank
				ranks[k][i] = j+1;
				break;
			}
			if(strcmp(names[k],name) == 0){
				ranks[k][i] = j+1;				//if the name already exist, add its rank
				break;
			}
		}
	}
}


/**
 * read in one line from the input file.
 * find the name and modify where the pointer point to with the found name.
 */
void readLine(FILE *file, char *name){
	char line[MAX_LEN_NAME] = {""}, ch = "", *fofLine;
	fofLine = line;

	//read in the whole line.
	while(ch != '\n'){
		ch = fgetc(file);
		*fofLine = ch;
		fofLine++;
	}
	fofLine = line;

	//find only the name and modify from the pointer
	while(*fofLine !=','){
		*name = *fofLine;
		fofLine++;
		name++;
	}
}

/**
 * create output file with name summary.csv
 * also print the ouput to console.
 */
void output(char (*names)[MAX_LEN_NAME], int (*ranks)[NUM_YEAR], FILE *file){
	file = fopen("summary.csv", "w");
	char title[][5] = {"NAME","1920","1930","1940","1950","1960","1970","1980","1990","2000","2010"};
	int i, j;
	for(i=0;i<11;i++){
		printf("%s,", title[i]);
		fprintf(file,"%s,", title[i]);
	}
	printf("\n");
	fprintf(file, "\n");
	for(j = 0; j < MAX_NUM_NAME; j++){
		printf("%s,", names[j]);
		fprintf(file,"%s,", names[j]);
		for (i = 0; i < NUM_YEAR; i++){
			if(ranks[j][i]){
				printf("%d,", ranks[j][i]);
				fprintf(file,"%d,", ranks[j][i]);
			}else {
				printf(",");
				fprintf(file, ",");
			}

		}
		printf("\n");
		fprintf(file, "\n");
	}
}

//end of program.
