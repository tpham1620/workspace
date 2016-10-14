Tan Pham - TCSS 435 winter 2016 - Programming Assignment #2.
Please read this file before grading.
*******************************************************************************************
To run the program, open a command prompt and navigate to the directory contain the unzip files.
In the command prompt type: java -jar pentago.jar 
Then follow the prompt.

In case you cannot run with the runnable jar file. 
Please create a java project and copy all the source code files into the
default package folder. Then run the project and follow the output prompt.

Thanks!
******************************************************************************************

Come in the zip file, I have attached the three output files.
1. output-minmax vs. alphabeta at depth 1
2. output-minmax vs. alphabeta at depth 2
3. output-human vs. alphabeta at depth 2

The output files contain most of the requirement for submission, including:
- player info
- player token
- player move
- configuration of board
- number of nodes enpanded for each move.
output-minmax vs. alphabeta at depth 1: contains nodes expanded for both algorithm at depth level 1 look ahead
output-minmax vs. alphabeta at depth 2: contains nodes expanded for both algorithm at depth level 2 look ahead

The missing infomation is time and space completexity of the algorithms which are state at the end of this file. 

minimax algorithm:
	time complexity: O((n^2)^d)
	space complexity: O(d)

alphabeta pruning:
	time complexity: O((n^2)^(d/2))
	space complexity: O(d)

where n is the size of the board, d is the depth level of look ahead.