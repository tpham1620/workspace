/*
 * File name: Trans.c
 *
 *  Created on: Jan 15, 2015
 *      Author: Tan Pham
 *      TCSS333 Winter 2015
 *
 ******************************************************************************************************
 * This program converts user input between 3 different representations.
 * Offer the user these 3 options for input and output below.
 * alphabetic  - a/A
 * hexadecimal - h/H
 * binary      - b/B
 * ****************************************************************************************************
 *
 * NOTE: Once the program is running, it will not stop until you press Ctrl+c.
 *
 */

#include <stdio.h>

/*
 * Build-in power function to calculate the exponential.
 */
int power(int base, unsigned int exp) {
	int i, result = 1;
	for (i = 0; i < exp; i++)
		result *= base;
	return result;
}

/*
 * Convert a char to hex.
 */
void toHex(char c){
	printf("%d",c/16);
	switch(c%16){
	case 0: printf("0");
	break;
	case 1: printf("1");
	break;
	case 2: printf("2");
	break;
	case 3: printf("3");
	break;
	case 4: printf("4");
	break;
	case 5: printf("5");
	break;
	case 6: printf("6");
	break;
	case 7: printf("7");
	break;
	case 8: printf("8");
	break;
	case 9: printf("9");
	break;
	case 10: printf("A");
	break;
	case 11: printf("B");
	break;
	case 12: printf("C");
	break;
	case 13: printf("D");
	break;
	case 14: printf("E");
	break;
	case 15: printf("F");
	break;
	}
}

/*
 * Convert a char to binary.
 */
void toBin(char c){
	int i;
	for (i = 7; i >=0; i--) {
		printf("%d",(c >> i) & 1);
	}
}

/*
 * Translate a string in alpha to binary.
 */
void transAtoB(){
	char strIn;

	while(1){
		scanf("%c",&strIn);
		if(strIn=='$') break;
		if(strIn!=10&&strIn!=13){
			toBin(strIn);
		}
	}
}

/*
 * Translate a string in alpha to hex.
 */
void transAtoH(){
	char strIn;

	while(1){
		scanf("%c",&strIn);
		if(strIn=='$') break;
		if(strIn!=10&&strIn!=13){
			toHex(strIn);
		}
	}
}

/*
 * Translate a string in binary to alpha.
 */
void transBtoA(){
	int i, strOut;
	char strIn;

	while(1){
		if(strIn!=10||strIn!=13){
			strOut=0;
			for(i=7;i>=0;i--){

				scanf(" %c",&strIn);
				if(strIn=='$')break;
				if(strIn=='1') strOut+=power(2,i);
			}
			if(strIn=='$')break;
			printf("%c",strOut);
		}
	}
}

/*
 * Translate a string in binary to hex.
 */
void transBtoH(){
	int i, strOut;
	char strIn;

	while(1){
		if(strIn!=10||strIn!=13){
			strOut=0;
			for(i=7;i>=0;i--){
				scanf(" %c",&strIn);
				if(strIn=='$')break;
				if(strIn=='1') strOut+=power(2,i);
			}
			if(strIn=='$')break;
			toHex(strOut);
		}
	}

}

/*
 * Translate a string in hex to alpha.
 */
void transHtoA(){
	int i, strOut;
	char strIn;

	while(1){

		if(strIn!=10||strIn!=13){
			strOut=0;
			for(i=1;i>=0;i--){
				scanf(" %c",&strIn);
				if(strIn=='$')break;
				if(i==1){
					switch(strIn){
					case '0': strOut=0*16;break;
					case '1': strOut=1*16;break;
					case '2': strOut=2*16;break;
					case '3': strOut=3*16;break;
					case '4': strOut=4*16;break;
					case '5': strOut=5*16;break;
					case '6': strOut=6*16;break;
					case '7': strOut=7*16;break;
					default: break;
					}
				}else if (i==0){
					switch(strIn){
					case '0': strOut+=0;break;
					case '1': strOut+=1;break;
					case '2': strOut+=2;break;
					case '3': strOut+=3;break;
					case '4': strOut+=4;break;
					case '5': strOut+=5;break;
					case '6': strOut+=6;break;
					case '7': strOut+=7;break;
					case '8': strOut+=8;break;
					case '9': strOut+=9;break;
					case 'A': strOut+=10;break;
					case 'B': strOut+=11;break;
					case 'C': strOut+=12;break;
					case 'D': strOut+=13;break;
					case 'E': strOut+=14;break;
					case 'F': strOut+=15;break;
					default: break;
					}
				}
			}
			if(strIn=='$')break;
			printf("%c",strOut);
		}

	}
}

/*
 * Translate a string in hex to binary.
 */
void transHtoB(){
	int i, strOut;
	char strIn;

	while(1){
		if(strIn!=10||strIn!=13){
			strOut=0;
			for(i=1;i>=0;i--){
				scanf(" %c",&strIn);
				if(strIn=='$')break;
				if(i==1){
					switch(strIn){
					case '0': strOut=0*16;break;
					case '1': strOut=1*16;break;
					case '2': strOut=2*16;break;
					case '3': strOut=3*16;break;
					case '4': strOut=4*16;break;
					case '5': strOut=5*16;break;
					case '6': strOut=6*16;break;
					case '7': strOut=7*16;break;
					default: break;
					}
				}else if (i==0){
					switch(strIn){
					case '0': strOut+=0;break;
					case '1': strOut+=1;break;
					case '2': strOut+=2;break;
					case '3': strOut+=3;break;
					case '4': strOut+=4;break;
					case '5': strOut+=5;break;
					case '6': strOut+=6;break;
					case '7': strOut+=7;break;
					case '8': strOut+=8;break;
					case '9': strOut+=9;break;
					case 'A': strOut+=10;break;
					case 'B': strOut+=11;break;
					case 'C': strOut+=12;break;
					case 'D': strOut+=13;break;
					case 'E': strOut+=14;break;
					case 'F': strOut+=15;break;
					default: break;
					}
				}
			}
			if(strIn=='$')break;
			toBin(strOut);
		}

	}
}

/**
 * If the input and output are the same, print out the same input string.
 */
void keepTheSame(){
	char strIn;
	while(1){
		scanf("%c",&strIn);
		if(strIn=='$') break;
		if(strIn!=10||strIn!=13)printf("%c",strIn);
	}
}

int main(void){

	setvbuf(stdout, NULL, _IONBF, 0);
	char input, output;
	while(1){
		printf("Choose Input (A,H,B): ");
		scanf(" %c", &input);
		printf("Choose Output (A,H,B): ");
		scanf(" %c", &output);

		if((input=='a'||input=='A')&&(output=='b'||output=='B')){
			printf("Enter your alpha input: \n");
			transAtoB();
			printf("\n");
		}else if((input=='a'||input=='A')&&(output=='h'||output=='H')){
			printf("Enter your alpha input: \n");
			transAtoH();
			printf("\n");
		}else if((input=='h'||input=='H')&&(output=='b'||output=='B')){
			printf("Enter your hex input: \n");
			transHtoB();
			printf("\n");
		}else if((input=='h'||input=='H')&&(output=='a'||output=='A')){
			printf("Enter your hex input: \n");
			transHtoA();
			printf("\n");
		}else if((input=='b'||input=='B')&&(output=='a'||output=='A')){
			printf("Enter your binary input: \n");
			transBtoA();
			printf("\n");
		}else if((input=='b'||input=='B')&&(output=='h'||output=='H')){
			printf("Enter your binary input: \n");
			transBtoH();
			printf("\n");
		}else if((input=='a'||input=='A')&&(output=='a'||output=='A')){
			printf("Enter your alpha input: ");
			keepTheSame();
			printf("\n");
		}else if((input=='h'||input=='H')&&(output=='h'||output=='H')){
			printf("Enter your hex input: ");
			keepTheSame();
			printf("\n");
		}else if((input=='b'||input=='B')&&(output=='b'||output=='B')){
			printf("Enter your binary input: ");
			keepTheSame();
			printf("\n");
		}
	}
	return 0;
}
