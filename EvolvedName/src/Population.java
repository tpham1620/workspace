/**
 * Population Class
 * @author Tan Pham
 * Created 01/26/2015
 * ***************************************************************************************************************************************************************
 * 
 */


import java.util.ArrayList;
import java.util.Random;


public class Population {
	Random rand = new Random();

	public String target = "TAN NGUYEN PHAM";
	public Genome mostFit;
	int popSize = 0;
	ArrayList<Genome> population = new ArrayList<Genome>();

	/**
	 * Constructor.
	 * Construct a population with the numGenomes number of elements
	 * with the mutation rate mutationRate.
	 * @param numGenomes
	 * @param mutationRate
	 */
	Population(int numGenomes, Double mutationRate){
		mostFit = new Genome(mutationRate);
		popSize = numGenomes;
		for(int i = 0; i < popSize; i++){
			population.add(new Genome(mutationRate));
		}
	}


	/**
	 * Breeding cycle method.
	 */
	public void day(){
		
		this.sort(); //sort the population increasing from the most fitness.
		
		mostFit = population.get(0); //After sorted, the first element is the most fit.
		
		population.subList(population.size()/2, population.size()).clear(); // remove the least-fit half.

		//restore the population to the original size.
		while(population.size() < popSize){
			if(rand.nextBoolean()){
				Genome newGen = new Genome(population.get(rand.nextInt(population.size())));
				newGen.mutate();
				population.add(newGen);
			}else{
				Genome newGen1 = new Genome(population.get(rand.nextInt(population.size())));
				Genome newGen2 = new Genome(population.get(rand.nextInt(population.size())));
				newGen2.crossover(newGen1);
				newGen2.mutate();
				population.add(newGen2);
			}
		}
	}


	/**
	 * Sort the population in increasing fitness order.
	 */
	public void sort(){
		for(int i = 0; i< population.size(); i++) {
			int mindex = findMin(i);
			if (mindex != i){
				swap(population.get(i), population.get(mindex));
			}
		}
	}

	/**
	 * Find the min fitness in the population start from startIndex.
	 * @param startIndex
	 * @return the index of the min fitness element.
	 */
	private int findMin(int startIndex) {
		int minFit = population.get(startIndex).fitness(target);
		int mindex = startIndex;
		int i;
		for(i = startIndex; i < population.size(); i++) {
			if(minFit > population.get(i).fitness(target)) {
				minFit = population.get(i).fitness(target);
				mindex = i;
			}
		}
		return mindex;
	}

	/**
	 * swap the position of the genome g1 and g2 in the population.
	 * @param g1
	 * @param g2
	 */
	private void swap(Genome g1, Genome g2){
		String temp;
		temp = g1.genome;
		g1.genome = g2.genome;
		g2.genome = temp;
	}

	/**
	 * Display the whole population with fitness.
	 */
	public String toString(){
		String str = "";
		for(int i = 0; i < population.size(); i++){
			str += "(\"" + population.get(i).toString() + "\", " 
					+ population.get(i).fitness(target) + ")\n";
		}
		return str;
	}

	public String mostFitString(){
		return "(\"" + mostFit + "\", " + mostFit.fitness(target) +")";
	}

	public int size(){
		return population.size();
	}
}
