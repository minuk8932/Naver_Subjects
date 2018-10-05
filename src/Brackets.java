import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Brackets {
	private static int res = 0;
	private static String[] line = null;

	private static final String ONE = "1";
	private static final String TWO = "2";
	private static final String THREE = "3";
	
	private static final String ONE_B = "(";
	private static final String TWO_B = "{";
	private static final String THREE_B = "[";
	
	private static final String SHUT_ONE_B = ")";
	private static final String SHUT_TWO_B = "}";
	private static final String SHUT_THREE_B = "]";
	
	private static final char NEW_LINE = '\n';
	
	private static final int NOT_CORRECT = 0;
	private static final int MOD = 100_000_000;

	public static void main(String[] args) throws Exception {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
//		int t = Integer.parseInt(br.readLine());
		int t = Integer.parseInt(sc.nextLine());
		
		while(t-- > 0) {
//			char[] line = br.readLine().toCharArray();
			String input = sc.nextLine();
			int loop = input.length();
			
			line = new String[loop];
			Arrays.fill(line, "");
			
			for(int i = 0; i < loop; i++) {
				line[i] += input.charAt(i);
			}
			
			res = 0;
			sb.append(BracketsValues(line) ? res : NOT_CORRECT).append(NEW_LINE);
		}
		
		System.out.println(sb.toString());
	}

	private static boolean BracketsValues(String[] brk) {
		Stack<String> lifo = new Stack<>();
		
		int isTrue = 0;
		for(int i = 0; i < brk.length; i++) {
			if(isTrue == -1) return false;
			
			if(brk[i].equals(ONE_B) || brk[i].equals(TWO_B) || brk[i].equals(THREE_B)) {
				lifo.push(brk[i]);
			}
			else {
				if(lifo.isEmpty()) return false;
				
				if (brk[i].equals(SHUT_ONE_B)) {					
					if (lifo.peek().equals(ONE_B)) {
						lifo.pop();
						lifo.push(ONE);
					} 
					else if(lifo.peek().equals(SHUT_THREE_B) || lifo.peek().equals(SHUT_TWO_B)) {
						return false;
					}
					else {
						isTrue = calculate(lifo, ONE_B, TWO_B, THREE_B, 1);
					}
				} 
				else if(brk[i].equals(SHUT_TWO_B)){
					if (lifo.peek().equals(TWO_B)) {
						lifo.pop();
						lifo.push(TWO);
					} 
					else if(lifo.peek().equals(SHUT_THREE_B) || lifo.peek().equals(SHUT_ONE_B)) {
						return false;
					}
					else {
						isTrue = calculate(lifo, TWO_B, THREE_B, ONE_B, 2);
					}
				}
				else if(brk[i].equals(SHUT_THREE_B)){
					if (lifo.peek().equals(THREE_B)) {
						lifo.pop();
						lifo.push(THREE);
					} 
					else if(lifo.peek().equals(SHUT_ONE_B) || lifo.peek().equals(SHUT_TWO_B)) {
						return false;
					}
					else {
						isTrue = calculate(lifo, THREE_B, ONE_B, TWO_B, 3);
					}
				}
			}
		}
		
		
		while (!lifo.isEmpty()) {
			if (isBrace(lifo.peek())) {
				return false;
			}
			
			res = (res + Integer.parseInt(lifo.pop())) % MOD;
		}
		
		return true;
	}
	
	private static boolean isBrace(String tmp) {
		return	tmp.equals(ONE_B) || tmp.equals(TWO_B) || tmp.equals(THREE_B) ||
				tmp.equals(SHUT_ONE_B) || tmp.equals(SHUT_TWO_B) || tmp.equals(SHUT_THREE_B) ? true : false;
	}
	
	private static int calculate(Stack<String> tmpLifo, String st, String nd, String rd, int value) {
		int num = 0;

		while (!tmpLifo.isEmpty()) {
			String top = tmpLifo.peek();

			if (top.equals(nd) || top.equals(rd)) {
				return -1;
			} 
			
			else if (top.equals(st)) {
				tmpLifo.pop();
				num *= value;
				
				if(num > MOD) num %= MOD;
				
				tmpLifo.push(String.valueOf(num));
				break;
			}
			
			else {
				if(num > MOD) num %= MOD;
				
				num += Integer.parseInt(tmpLifo.pop());
			}
		}
		
		return num;
	}
}
