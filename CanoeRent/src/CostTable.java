/**
 * Cost table object
 * @author Tan Pham
 */
import java.util.Random;


public class CostTable {

	public int table[][];
	private Random rd = new Random();
	
	public CostTable(int n){
		table = new int[n][n];
		for(int i = 0; i<n; i++){
			for(int j = 0; j<n; j++){
				if(j<=i){
					table[i][j] = 0;
				}else{
					table[i][j] = table[i][j-1] + rd.nextInt(5);
				}
			}
		}
	}
	public String toString(){
		String str = "";
		for(int i = 0; i<table.length; i++){
			for(int j = 0; j<table.length; j++){
				str += table[i][j] + ",";
			}
			str += "\n";
		}
		return str;
	}
	
}
