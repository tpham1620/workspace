/**
 * Maze class for maze generator project
 * @author Tan Pham
 * Created: 03/10/2015
 * **************************************************************************************************************************
 * This class randomly generate a maze with input sizes nXm and along with a unique solution for the maze.
 * 
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class Maze {

	/**
	 * A private class to create nodes for the maze
	 * @author Tan Pham
	 *
	 */
	private class Node{
		boolean visited;
		ArrayList<Node> adj;		//adjacency list

		Node(){
			visited = false;
			adj = new ArrayList<Node>();
		}

		
		void addAdj(Node n){
			adj.add(n);
			n.adj.add(this);
		}
		
		void removeAdj(Node n){
			adj.remove(adj.indexOf(n));
			n.adj.remove(n.adj.indexOf(this));

		}
		
		/*
		 * there is no needs for function of the edges. 
		 * The edges are imagined existing between a node and it's adjacencies.
		 */
		boolean hasEdgeWith(Node n){
			if(adj.contains(n)) return true;
			return false;
		}
	}


	int n, m;
	boolean debug;
	String output = "";
	
	Node nodes[][]; //The graph represent the maze.
	
	Stack<Node> solution = new Stack<Node>();  //store the solution where it was found.

	/*
	 * Constructor.
	 * Construct a maze with sizes nXm
	 * Print the process step by step if debug is on.
	 */
	Maze(int n, int m, boolean debug){
		this.n = n;
		this.m = m;
		this.debug = debug;
		
		nodes = new Node[n][m];
		
		/*
		 * Connect each node to the left, right, above and below node if exist.
		 * This is done by adding adjacency nodes.
		 */
		for(int i = 0; i < n; i++){
			for(int j = 0; j < m; j++){
				nodes[i][j] = new Node();
				if(j>0){
					nodes[i][j-1].addAdj(nodes[i][j]);
				}
				if(i>0){
					nodes[i-1][j].addAdj(nodes[i][j]);
				}
			}
		}

		/*
		 * generate a random maze
		 */
		createMaze();
		
		
		this.debug = false;			//Disable the debug before print the final maze with solution.
		output += toString();
		
		
		System.out.println(output);	//Print the maze and/or debug to the console.
	}

	/**
	 * createMaze method
	 * This method randomly create a maze by depth-first searching algorithm 
	 */
	public void createMaze(){
		Stack<Node> tracker = new Stack<Node>();	//a tracker to keep track of the path.
		
		tracker.push(nodes[0][0]);					//add first position (the door) to the tracker.
		nodes[0][0].visited = true;
		
		if(debug) output += toString() + "\n";		//if debug on, add the first stage of the maze to the output.

		Random rand = new Random();

		/*
		 * every time a node have no adjacent or all the adjacent has been visited, that node will be pop out of the tracker.
		 * if the first node (the door) is pop, this mean all the node has been visited, terminate the loop.
		 */
		while(!tracker.isEmpty()){
			int adjSize = tracker.peek().adj.size();
			
			//if a node has no adjacent, pop it out.
			if(adjSize == 0){
				tracker.pop();
			}else{//else randomly get one of it's adjacencies.
				int r = rand.nextInt(adjSize);
				Node temp = tracker.peek().adj.get(r);
				int visitAll = 1;
				//if the selected node has already been visited, get the next one.
				while(temp.visited&&visitAll<adjSize+1){
					r++;
					temp = tracker.peek().adj.get(r%adjSize);
					visitAll++;
				}
				//if found an adjacent has not been visited, set it visited, remove the edge (wall), then add to the tracker.
				if(visitAll<adjSize+1){
					temp.visited = true;
					tracker.peek().removeAdj(temp);
					tracker.push(temp);
					
					//if the exit is reached, the tracker contents the solution. add it to the solution keeper.
					if(temp == nodes[n-1][m-1]){
						solution.addAll(tracker);
					}

					if(debug) output += toString() + "\n";	//if debug on, add this step to output.
				}else{
					tracker.pop();//if all the adjacent of this node has been visited, pop it out of the tracker.
				}	
			}
		}
	}

	/**
	 * toString method.
	 */
	public String toString(){
		StringBuilder toReturn = new StringBuilder();

		/*
		 * this double char maze content the out side walls, the nodes, and the edges.
		 */
		char maze[][] = new char[2*n+1][2*m+1];

		for(int i = 0; i < 2*n +1; i++){
			for(int j = 0; j < 2*m +1; j++){
				maze[i][j] = 'X';		//set everything to X
				
				/*
				 * all the (odd, odd) positions is the nodes.
				 * if a node is visited and debug on, reset it to 'V'
				 * if a node is visited and debug off and it is on the solution path, reset it to '+'
				 * otherwise, reset it to ' '
				 */
				if((i*j)%2 == 1){ 
					if(nodes[(i-1)/2][(j-1)/2].visited){
						if(debug){
							maze[i][j] = 'V';
						}else if(!solution.isEmpty()&&solution.contains(nodes[(i-1)/2][(j-1)/2])){
							maze[i][j] = '+';
						}else maze[i][j] = ' ';
					}else maze[i][j] = ' ';
					
					/*
					 * if there is an edge between a node and it's previous one, reset the edge to ' '
					 */
					if((i-1)/2 > 0 && !nodes[(i-1)/2][(j-1)/2].hasEdgeWith(nodes[(i-1)/2-1][(j-1)/2])){
						maze[i-1][j] = ' ';
					}
					if((j-1)/2>0 && !nodes[(i-1)/2][(j-1)/2].hasEdgeWith(nodes[(i-1)/2][(j-1)/2-1])){
						maze[i][j-1] = ' ';
					}
				}
				//break the door and the exit walls
				if((i==0&&j==1)||(i==2*n&&j==2*m-1)) maze[i][j] = ' ';
			}
		}
		
		/*
		 * finally, print the maze to a string.
		 */
		for(int i = 0; i < 2*n +1; i++){
			for(int j = 0; j < 2*m +1; j++){
				toReturn.append(maze[i][j]);
			}
			toReturn.append("\n");
		}
		return toReturn.toString();
	}
}
