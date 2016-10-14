/*
 *  File name: image.c
 *
 *  Created on: Jan 19, 2015
 *      Author: Tan Pham
 *      TCSS333 Winter 2015
 *
 ******************************************************************************************************
 * This program will read an image in bitmap format, then create two copy of it.
 * One copy will have double height and width
 * The other copy will have height and width reduced by half.
 ******************************************************************************************************
 *
 *NOTE: Please make sure to put the image in the project folder to have it work correctly.
 *
 */


#include <stdio.h>


int main(void) {

	FILE *infile = fopen("test1.bmp","rb");
	FILE *d_outfile = fopen("double.bmp","wb");
	FILE *r_outfile = fopen("reduce.bmp","wb");

	int r,c,k;

	char hdField1[2];
	int fileSize, dSize, rSize;
	char hdField2[12];
	int wSize, dwSize, rwSize, hSize, dhSize, rhSize;
	char hdField3[8];
	int iSize, diSize, riSize;
	char restOfHdr[16];


	/*
	 * Read in the file.
	 */
	fread(hdField1, sizeof(char), 2, infile);
	fread(&fileSize, sizeof(int), 1, infile);
	fread(hdField2, sizeof(char), 12, infile);
	fread(&wSize, sizeof(int), 1, infile);
	fread(&hSize, sizeof(int), 1, infile);
	fread(hdField3, sizeof(char), 8, infile);
	fread(&iSize, sizeof(int), 1, infile);
	fread(restOfHdr, sizeof(char), 16, infile);

	char pixels[hSize][wSize*3];
	char dPixels[hSize*2][wSize*2*3];
	char rPixels[hSize/2][3*wSize/2];

	//Read this last after having the value of height and width.
	fread(pixels, sizeof(char), hSize * wSize * 3, infile);


	/*
	 * Double the height and width of the image.
	 */
	for (r = 0; r < hSize; r++) {
		for(c = 0; c < wSize ; c++) {
			for (k = 0; k < 3; k++){
				dPixels[2*r][(2*3*c+k)]=pixels[r][3*c+k];
				dPixels[2*r+1][2*3*c+k]=pixels[r][3*c+k];
				dPixels[2*r][2*3*c+k+3]=pixels[r][3*c+k];
				dPixels[2*r+1][2*3*c+k+3]=pixels[r][3*c+k];
			}
		}
	}
	dSize = iSize*4 + 54;
	dwSize = wSize*2;
	dhSize = hSize*2;
	diSize = iSize*4;
	/*
	 * Write out the double file.
	 */
	fwrite(hdField1, sizeof(char), 2, d_outfile);
	fwrite(&dSize, sizeof(int), 1, d_outfile);
	fwrite(hdField2, sizeof(char), 12, d_outfile);
	fwrite(&dwSize, sizeof(int), 1, d_outfile);
	fwrite(&dhSize, sizeof(int), 1, d_outfile);
	fwrite(hdField3, sizeof(char), 8, d_outfile);
	fwrite(&diSize, sizeof(int), 1, d_outfile);
	fwrite(restOfHdr, sizeof(char), 16, d_outfile);
	fwrite(dPixels, sizeof(char), dhSize * dwSize * 3, d_outfile);


	/*
	 * Reduce the height and width of the image by half.
	 */
	for (r = 0; r < hSize; r+=2) {
		for(c = 0; c < wSize ; c+=2) {
			for (k = 0; k < 3; k++){
				rPixels[r/2][(3*c/2+k)]=pixels[r][3*c+k];
			}
		}
	}
	rSize = iSize/4 + 54;
	rwSize = wSize/2;
	rhSize = hSize/2;
	riSize = iSize/4;
	/*
	 * Write out the reduce file.
	 */
	fwrite(hdField1, sizeof(char), 2, r_outfile);
	fwrite(&rSize, sizeof(int), 1, r_outfile);
	fwrite(hdField2, sizeof(char), 12, r_outfile);
	fwrite(&rwSize, sizeof(int), 1, r_outfile);
	fwrite(&rhSize, sizeof(int), 1, r_outfile);
	fwrite(hdField3, sizeof(char), 8, r_outfile);
	fwrite(&riSize, sizeof(int), 1, r_outfile);
	fwrite(restOfHdr, sizeof(char), 16, r_outfile);
	fwrite(rPixels, sizeof(char), rhSize * rwSize * 3, r_outfile);

	/*
	 * Close the files.
	 */
	fclose(infile);
	fclose(d_outfile);
	fclose(r_outfile);
	return 0;

}
