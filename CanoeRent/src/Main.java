/**
 * Canoe sequence class
 * @author Tan Pham
 *
 */
public class Main {

	public static void main(String[] args) {
		CostTable table = new CostTable(10);
		System.out.println(table.toString());
		System.out.println("Brute Force:\n");
		System.out.println(bruteForce(table.table));
		System.out.println("\nDynamic Programming\n");
		System.out.println(dynamic(table.table));
		System.out.println("\nDivide and Conquer\n");
		Post con = conquer(table.table,table.table.length);
		System.out.println("The cheapest cost: " + con.getCheapest() );
		System.out.println("The optimal sequence: " + output(con.getLastPost(),table.table.length-1));
	}
	
	public static String bruteForce(int table[][]){
		String str = "";
		
		int n = table.length;
		
		int cost = 0, cheapest = Integer.MAX_VALUE, sequence = 0;
		
		for(int i = 1; i <= Math.pow(2, n-2); i++){
			int last = 0, temp = i;

			for(int j = 1; j<n; j++){
				if((temp&1) == 1 && last == 0){
					last = j;
					cost = table[0][last];
					temp = temp>>1;
				}else if((temp&1) == 1 && last != 0){
					cost += table[last][j];
					last = j;
					temp = temp>>1;
				}else temp = temp>>1;
			}
			cost += table[last][n-1];
			if(cheapest > cost){
				cheapest = cost;
				sequence = i;
			}
		}
		
		str += "The cheapest cost: " + cheapest + "\n";
		str += "The optimal sequence: 1,";
		for(int i = 2; i<n; i++){
			if((sequence&1) == 1){
				str += i +",";
				sequence = sequence>>1;
			}else sequence = sequence>>1;
		}
		str += "" + n;
		return str;
	}

	public static String dynamic(int table[][]){
		String str = "";
		int n = table.length;
		
		int cost[] = new int[n];
		int lastPost[] = new int[n];
		cost[0] = 0;
		lastPost[0] = 0;
		
		for(int i = 1; i < n; i++){
			int min = table[0][i];
			lastPost[i] = 0;
			for(int j = 1; j < i; j++){
				if(cost[j] + table[j][i] < min){
					min = cost[j] + table[j][i];
					lastPost[i] = j;
				}
			}
			cost[i] = min;
		}
		
		str += "The cheapest cost is: " + cost[n-1] + "\n";
		str += "The optimal sequence: ";
		str += output(lastPost,n-1);
		return str;
	}
	public static String output(int p[],int n){
		String str = "";
		if(n>=1) str += output(p,p[n]);
		str += (n+1) + ",";
		return str;
	}
	
	
	
	
	public static Post conquer(int table[][], int base){

		Post min = new Post(table[0][base-1],base);
		if(base == 1){
			//min.lastPost[base-1] = 1;
			return min;
		}
		for(int i = base-1; i>0; i--){
			Post temp = conquer(table, i);
			temp.setCheapest(temp.getCheapest() + table[i-1][base-1]);
			
			if(min.getCheapest()>=temp.getCheapest()){
				min.setCheapest(temp.getCheapest());
				for(int j = 0; j<temp.getLastPost().length;j++){
					min.getLastPost()[j] = temp.getLastPost()[j];
				}
				min.getLastPost()[base-1] = i-1;
			}
		}
		
		return min;
	}
}
