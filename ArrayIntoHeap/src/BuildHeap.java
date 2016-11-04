import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
	private int[] data;
	private List<Swap> swaps;

	private FastScanner in;
	private PrintWriter out;

	public static void main(String[] args) throws IOException {
		new BuildHeap().solve();
	}

	private void readData() throws IOException {
		int n = in.nextInt();
		data = new int[n];
		for (int i = 0; i < n; ++i) {
			data[i] = in.nextInt();
		}
	}

	private void writeResponse() {
		out.println(swaps.size());
		for (Swap swap : swaps) {
			out.println(swap.index1 + " " + swap.index2);
		}
	}

	private void generateSwaps() {
		swaps = new ArrayList<Swap>();
		// The following naive implementation just sorts 
		// the given sequence using selection sort algorithm
		// and saves the resulting sequence of swaps.
		// This turns the given array into a heap, 
		// but in the worst case gives a quadratic number of swaps.
		//
		// TODO: replace by a more efficient implementation

		for(int i = data.length/2;i>=0;--i){
			shiftDown(i);
		}
	}

	private void shiftDown(int i){
		int smallChild = 0;
		if(2*i+2<=data.length-1){
			smallChild = (data[2*i+1]<data[2*i+2]) ? 2*i+1 : 2*i+2;
			swap(i,smallChild); 
			shiftDown(smallChild);
		}else if(2*i+1 == data.length-1){
			swap(i,2*i+1);
			shiftDown(2*i+1);
		}
	}
	private void swap(int i, int j){
		if (data[i] > data[j]) {
			swaps.add(new Swap(i, j));
			int tmp = data[i];
			data[i] = data[j];
			data[j] = tmp;
		}
	}
	public void solve() throws IOException {
		in = new FastScanner();
		out = new PrintWriter(new BufferedOutputStream(System.out));
		readData();
		generateSwaps();
		writeResponse();
		out.close();
	}

	static class Swap {
		int index1;
		int index2;

		public Swap(int index1, int index2) {
			this.index1 = index1;
			this.index2 = index2;
		}
	}

	static class FastScanner {
		private BufferedReader reader;
		private StringTokenizer tokenizer;

		public FastScanner() {
			reader = new BufferedReader(new InputStreamReader(System.in));
			tokenizer = null;
		}

		public String next() throws IOException {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				tokenizer = new StringTokenizer(reader.readLine());
			}
			return tokenizer.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}
}
