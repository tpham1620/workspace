/**
 * Genome Class
 * @author Tan Pham
 * Created 01/26/2015
 * ***************************************************************************************************************************************************************
 * 
 */

import java.util.Random;


public class Genome {

	private Character[] list = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ','’' };
	public String genome;
	private double mutationRate;

	private Random rand = new Random();

	/**
	 * Constructor with a mutation rate.
	 * @param mutationRate
	 */
	Genome(double mutationRate){
		genome = "A";
		this.mutationRate = mutationRate;
	}

	/**
	 * Copy constructor.
	 * @param gene
	 */
	Genome(Genome gene){
		this.genome = gene.genome;
		this.mutationRate = gene.mutationRate;
	}

	/**
	 * Mutation method.
	 */
	public void mutate(){

		//with mutationRate chance add a randomly selected character.
		if(rand.nextDouble()<=mutationRate){
			genome = randAdd(genome);	//Call random add method.
		}

		//with mutationRate chance delete a randomly selected character.
		if(rand.nextDouble()<=mutationRate){
			if(genome.length()>2) genome = randDel(genome);	//Call random delete method.
		}

		//with mutationRate chance replace a randomly selected character.
		if(rand.nextDouble()<=mutationRate){
			if(genome.length()>1) genome = randRep(genome);	//Call random replace method.
		}
	}

	/**
	 * Crossover method.
	 * @param other
	 */
	public void crossover(Genome other){
		String newStr = "";
		int index = 0;

		while(index < genome.length()){
			if(rand.nextBoolean()){
				newStr += genome.charAt(index);
			}else{
				if(index < other.genome.length()){
					newStr += other.genome.charAt(index);
				}else break;
			}		
			index++;
		}
		genome = newStr;
	}


	/**
	 * Calculate the fitness of the current string to the target.
	 * @param target
	 * @return
	 */
	public int fitness(String target){
		int fitness = Math.abs(genome.length() - target.length() );
		int length;
		if (genome.length()>target.length()) {
			length = genome.length();
		}else{
			length = target.length();
		}
		for(int i = 0; i < length;i++){
			if (i >= genome.length()||i >= target.length()){
				fitness += 1;
			}else if(genome.charAt(i) != target.charAt(i)){
				fitness += 1;
			}
		}
		return fitness;
	}


	public String toString(){
		return genome;
	}
	/**
	 * Randomly select a char from the list.
	 * @return the random selected char from the list.
	 */
	private Character randGetChar(){
		return list[rand.nextInt(28)];
	}

	/**
	 * Add a random selected character to a random position of a string.
	 * @param str - original string
	 * @return new string
	 */
	private String randAdd(String str){
		String newStr, tempStr;
		tempStr = str.substring(0, rand.nextInt(str.length()));
		str = str.substring(tempStr.length());
		newStr = tempStr + randGetChar() + str;
		return newStr;
	}

	/**
	 * Delete a random selected character from a random position of a string.
	 * @param str - original string
	 * @return new string
	 */
	private String randDel(String str){
		String newStr, tempStr;
		tempStr = str.substring(0, rand.nextInt(str.length()));
		str = str.substring(tempStr.length()+1);
		newStr = tempStr + str;
		return newStr;
	}

	/**
	 * Replace a random selected character at a random position of a string.
	 * @param str - original string
	 * @return new string
	 */
	private String randRep(String str){
		String newStr, tempStr;
		tempStr = str.substring(0, rand.nextInt(str.length()));
		str = str.substring(tempStr.length()+1);
		newStr = tempStr + randGetChar() + str;
		return newStr;
	}
}
