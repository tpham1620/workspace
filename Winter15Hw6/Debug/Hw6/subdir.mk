################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../Hw6/mallok.c \
../Hw6/mallok_test.c \
../Hw6/orderedList.c 

OBJS += \
./Hw6/mallok.o \
./Hw6/mallok_test.o \
./Hw6/orderedList.o 

C_DEPS += \
./Hw6/mallok.d \
./Hw6/mallok_test.d \
./Hw6/orderedList.d 


# Each subdirectory must supply rules for building sources it contributes
Hw6/%.o: ../Hw6/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


