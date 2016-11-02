/**
 * This program is a brackets tester for a text editor for programmers. 
 * The program will take a text file input and evaluate for misuse of brackets.
 * The text file should consist of big and small Latin letters, digits, punctuation
 * marks and brackets from the set []{}().
 * If the code in the file uses brackets correctly, the program will output "Success!".
 * Otherwise, the program will output the index of the first misuse bracket.
 * 
 * @author Tan Pham
 * @version 2016.11.1
 */
public class Checker {

	private static String input = "(";
	
	public static void main(String[] args) {
		int code = check(input);
		if(code != 0){
			System.out.println(code);
		}else{
			System.out.println("Success!");
		}

	}

	public static int check(String str){
		Stack stack = new Stack(); //The stack to keep track of balance brackets.
		int i = 0;
		for(i = 0; i < str.length(); i++){
			Character c = str.charAt(i);
			if(c == '(' || c == '{' || c == '[' || c == ')' || c == '}' || c == ']'){
				if(c == '(' || c == '{' || c == '['){
					stack.push(c);
				}else{
					if(stack.size()==0) return i;
					Character top = stack.pop();
					if((top == '(' & c != ')')|| (top == '{' & c != '}')|| (top == '[' & c != ']')) return i;
				}
			}
		}
		if(stack.size()!=0) return i;
		return -1;
	}

}
