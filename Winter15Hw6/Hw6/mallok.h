/*
 * mallok.h
 *
 *  Created on: Mar 3, 2015
 *      Author: Tan Pham
 *  File header for mallok.c source file
 */

#include "orderedList.h"

#ifndef MALLOK_H_
#define MALLOK_H_

void create_pool(int size);
void *my_malloc(int size);
void my_free(void *block);

#endif /* MALLOK_H_ */
