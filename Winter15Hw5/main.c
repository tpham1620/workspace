/*
 * main.c
 *
 *  Created on: Feb 25, 2015
 *      Author: Tan Pham
 *
 */

#include <stdio.h>
#include <string.h>
#include "list.h"

void readFile(List,char *);
void dimWord(char *);
void output(List);

int main(void){

	List list = createList();		//create the linked list of words

	readFile(list, "LittleRegiment.txt");	//read input files and store data.
	readFile(list, "RedBadge.txt");


	getSmallCount(list);				//compare which file the word appear least
	sort(list);							//sort the list bas on the least appear of a word.

	output(list);						//print out the output

	return 0;
}

/*
 * read the input file and store data to the linked list
 */
void readFile(List ls, char *fileName){
	int i;
	FILE *file = fopen(fileName, "r");

	if(file == NULL) printf("open file fail");

	char word[MAX_WORD] = "";

	while(fscanf(file,"%s", word)==1){
		dimWord(word);
		if(ls->front == NULL){
			Node *n = createNode(word);
			if(strcmp(fileName, "LittleRegiment.txt")==0){
				n->countLit++;
			}else n->countRed++;
			add(ls,n);
		}else{

			int found = 0;
			Node *c = ls->front;
			for(i = 0; i < ls->size; i++){
				if(strcmp(c->word,word)==0){
					if(strcmp(fileName, "LittleRegiment.txt")==0){
						c->countLit++;
					}else c->countRed++;
					found = 1;
					break;
				}
				c = c->next;
			}
			if(found == 0){
				Node *node = createNode(word);
				if(strcmp(fileName, "LittleRegiment.txt")==0){
					node->countLit =1;
				}else node->countRed =1;
				add(ls,node);
			}
		}
	}
}

/*
 * scan a string and get a single word base on the following rule:
 * - all letters should be converted to lower case if necessary
 * - the word in the string may contain alphabetic characters, hyphen, and apostrophe only
 * - start at the left end of the string with the first character of this type
 * - scan to the right stop when find a character not of this type or the string ends.
 */
void dimWord(char *w){
	char word[MAX_WORD] = "";
	int j = 0;
	int k =0;
	char ch = tolower(w[j]);
	while(!(96<ch&&ch<123)&&!(ch=='-')&&!(ch=='"')){
		ch = tolower(w[++j]);
	}
	while((96<ch&&ch<123)||(ch=='-')||(ch=='"')){

		word[k++] = ch;
		ch = tolower(w[++j]);
	}
	strcpy(w,word);
}

/*
 * print out the top 50 most frequently words
 */
void output(List ls){
	int i;
	Node *conductor;
	conductor = ls->front;

	for(i=0;i<50;i++){
		printf("%d) %s, LittleRegiment: %d, RedBadge: %d\n", i+1, conductor->word,conductor->countLit,conductor->countRed);
		conductor = conductor->next;
	}
}


