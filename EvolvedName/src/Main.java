/**
 * Main Class
 * @author Tan Pham
 * Created 01/26/2015
 * ***************************************************************************************************************************************************************
 * 
 */


public class Main {

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();

		Population pop = new Population(100,0.05);

		pop.target = "CHRISTOPHER PAUL MARRIOTT";


		int generations = 0;
		while(pop.mostFit.fitness(pop.target)>0){
			pop.day();
			System.out.println(pop.mostFitString());
			generations++;			
		}

		System.out.println("Generations: " + generations);		
		long endTime = System.currentTimeMillis();
		long totalTime = endTime-startTime;
		System.out.println("Runtime: " + totalTime + " milliseconds");

	}

}
