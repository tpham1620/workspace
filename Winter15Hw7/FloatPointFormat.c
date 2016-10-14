/*
 * Float.c
 *
 *  Created on: Mar 11, 2015
 *      Author: Tan Pham
 * *************************************************************************************************************************************88
 * This program will ask for a float number, then display the 32 bit binary representation of that number.
 * And show step by step how to rebuild the input from the 32 bits binary number.
 */


#include <stdio.h>
#include <string.h>
#include <stdlib.h>

struct f{
	unsigned int sign: 1;
	unsigned int exp: 8;
	unsigned int frac: 23;
};

typedef struct f Float;

int getExp(float input, float* frac);
unsigned int fracBins(float frac);
void print32bits(Float fb);
float buildFrac(unsigned int);
void appExp(float, int);
int power(int,int);

int main(void){
	setvbuf(stdout, NULL, _IONBF, 0);

	Float fbins;

	float input, fraction;
	int exp;

	printf("Enter a float number: ");
	scanf("%f", &input);

	printf("\nyour float was read: %f", input);

	/*
	 * store the sign and get absolute value of input
	 */
	if(input<0){
		fbins.sign = 1;
		input = 0 - input;
	}else fbins.sign = 0;

	/*
	 * get the exponent and create a fraction part
	 */
	exp = getExp(input, &fraction);

	fbins.exp = 127 + exp;  					//store the bias of exponent

	fbins.frac = fracBins(fraction);			//convert the fraction part to unsigned integer (binary)

	print32bits(fbins);							//print the output to 32 bits format

	float bldFrac =	buildFrac(fbins.frac);		//rebuild the fraction part from the binary.

	if(fbins.sign == 1) bldFrac = 0-bldFrac;	//add the sign of the original input.
	printf("\nApplying the sign:\nfraction = %f\n", bldFrac);

	appExp(bldFrac, exp);						//apply the exponent to the fraction.

	return 0;
}

/**
 * convert the fraction to a unsigned int (binary)
 */
unsigned int fracBins(float frac){
	int i = 1;
	unsigned int fbin = 0;

	frac = frac-1;
	while(frac != 0 && i < 24){
		float p = 1.0f/power(2,i);
		if(frac >= p){
			fbin |= (1<<(23-i));
			frac = frac - p;
		}
		i++;
	}
	return fbin;

}

/*
 * shift the integer part to get the exponent by either multiplication or division by 2
 * until the integer part become 1.
 * the final number will become the fraction part for the single point format.
 */
int getExp(float input, float* frac){
	int exp = 0;
	if((int)input == 1){
		*frac = input;
		return 0;
	}
	if((int)input > 1){
		while((int)input > 1){
			input = input/2;
			*frac = input;
			exp++;
		}
	}else{
		while((int)input < 1){
			input = input*2;
			*frac = input;
			exp--;
		}
	}
	return exp;
}

/*
 * print out the 32 bit format
 */
void print32bits(Float fb){
	int i;
	char expStr[9] = "00000000";
	char fracStr[24] = "00000000000000000000000";


	for(i = 0; i < 9; i++){
		if(fb.exp&(1<<(7-i))) expStr[i] = '1';
	}
	for(i = 0; i < 24; i++){
		if(fb.frac&(1<<(22-i))) fracStr[i] = '1';
	}

	printf("\nyour float in 32 bits: %d%s%s", fb.sign, expStr, fracStr);
	printf("\nsign: %d", fb.sign);
	printf("\nexponent binary: %s", expStr);
	printf("\nfraction binary: %s\n", fracStr);
}

/*
 * rebuild the fraction part from the binary and print the steps
 */
float buildFrac(unsigned int frac){
	float b = 1.0f;
	int i;
	printf("\ncreating the fraction:\n");
	printf("fraction = %f (the implicit 1)\n", b);
	for(i = 1; i < 24; i++){
		float temp = 1.0f/power(2,i);
		if(frac&(1<<(23-i))){
			b += temp;
			printf("fraction = %f, after adding %f\n", b, temp);
		}else{
			printf("fraction = %f, after skipping %f\n", b, temp);
		}
	}
	return b;
}

/*
 * apply the exponent and re-create the original input.
 * print out steps
 */
void appExp(float frac, int exp){
	printf("\nApplying the exponent:\n unbiased exponent = %d\n", exp);
	int i, sign = 0;
	if(exp<0){
		sign = 1;
		exp = 0-exp;
	}
	for(i = 0; i < exp; i++){
		switch(sign){
		case 0:{
			frac = frac*2.0f;
			printf("times 2 = %f\n", frac);
			break;
		}
		case 1:{
			frac = frac/2.0f;
			printf("divided 2 = %f\n", frac);
			break;
		}
		default: break;
		}
	}
	printf("\nFinal Answer: %f", frac);
}

/*
 * build in power function
 */
int power(int b, int e){
	if (e == 0) return 1;
	if (e == 1) return b;

	int i = 0, toReturn = b;
	for(i = 1; i < e; i++){
		toReturn = toReturn*b;
	}

	return toReturn;
}


