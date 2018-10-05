import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * 	@author minchoba
 * 
 */
public class Brackets {
	private static int res = 0;
	private static String[] line = null;
	private static Stack<String> lifo = null;

	private static final String ONE = "1";			// 각 필요한 문자열 상수 정의
	private static final String TWO = "2";
	private static final String THREE = "3";
	
	private static final String ONE_B = "(";
	private static final String TWO_B = "{";
	private static final String THREE_B = "[";
	
	private static final String SHUT_ONE_B = ")";
	private static final String SHUT_TWO_B = "}";
	private static final String SHUT_THREE_B = "]";
	
	private static final char NEW_LINE = '\n';
	
	private static final int NOT_CORRECT = 0;		// 올바른 괄호열이 아닌 경우
	private static final int MOD = 100_000_000;		// 나머지 연산 상수

	public static void main(String[] args) throws Exception {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		최대 대략 8192 chars까지 입력이 가능하므로 스캐너 사용
		// 스캐너를 통한 입력
		Scanner sc = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
//		int t = Integer.parseInt(br.readLine());
		int t = Integer.parseInt(sc.nextLine());
		
		while(t-- > 0) {
//			char[] line = br.readLine().toCharArray();
			String input = sc.nextLine();
			int loop = input.length();
			
			line = new String[loop];
			Arrays.fill(line, "");			// 배열내에 char를 받아오기 위해 초기화
			
			for(int i = 0; i < loop; i++) {
				line[i] += input.charAt(i);		// 1자리 문자를 배열에 더해줌으로써 문자열로 바꿈
			}
			
			res = 0;
			sb.append(BracketsValues(line) ? res : NOT_CORRECT).append(NEW_LINE);	// 괄호의 값 메소드가 참이면 res의 값을 아니면 0을 버퍼에 저장
		}
		
		System.out.println(sb.toString());		// 결과 값 한번에 출력
	}

	/**
	 * 괄호의 값 연산 메소드
	 * 
	 */
	private static boolean BracketsValues(String[] brk) {
		lifo = new Stack<>();
		
		boolean isTrue = true;
		for(int i = 0; i < brk.length; i++) {
			if(!isTrue) return false;		// calculate 메소드에 의해 올바르지 않은 괄호열로 판명될 경우 거짓 반환
			
			if(brk[i].equals(ONE_B) || brk[i].equals(TWO_B) || brk[i].equals(THREE_B)) {
				lifo.push(brk[i]);		// 열린 괄호는 무조건 스택에 담아줌
			}
			else {
				if(lifo.isEmpty()) return false;		// 열린 괄호가 없는 상태에서 닫힌 괄호가 들어오면 거짓 반환
				
				if (brk[i].equals(SHUT_ONE_B)) {		// ")"가 들어온 경우	
					if (lifo.peek().equals(ONE_B)) {		// 스택의 꼭대기에 "("가 존재하면 () 이므로 pop 후 해당 배열의 값: 1을 스택에 담음
						lifo.pop();
						lifo.push(ONE);
					} 
					else if(lifo.peek().equals(SHUT_THREE_B) || lifo.peek().equals(SHUT_TWO_B)) {	// 꼭대기의 값이 "("가 아닌 다른 괄호의 경우
						return false;							// 잘못된 괄호열이므로 거짓 반환
					}
					else {
						isTrue = calculate(ONE_B, TWO_B, THREE_B, 1);	// 꼭대기의 값이 그 외(즉, 숫자)인 경우, calculate 메소드 호출
					}
				} 
				else if(brk[i].equals(SHUT_TWO_B)){			// 위와 같은 방식으로 "}" 기준으로 처리함
					if (lifo.peek().equals(TWO_B)) {
						lifo.pop();
						lifo.push(TWO);
					} 
					else if(lifo.peek().equals(SHUT_THREE_B) || lifo.peek().equals(SHUT_ONE_B)) {
						return false;
					}
					else {
						isTrue = calculate(TWO_B, THREE_B, ONE_B, 2);
					}
				}
				else if(brk[i].equals(SHUT_THREE_B)){		// 위와 같은 방식으로 "]" 기준으로 처리함
					if (lifo.peek().equals(THREE_B)) {
						lifo.pop();
						lifo.push(THREE);
					} 
					else if(lifo.peek().equals(SHUT_ONE_B) || lifo.peek().equals(SHUT_TWO_B)) {
						return false;
					}
					else {
						isTrue = calculate(THREE_B, ONE_B, TWO_B, 3);
					}
				}
			}
		}
		
		
		while (!lifo.isEmpty()) {			// 스택이 비지 않았고
			if (isBrace(lifo.peek())) {		// isBrace 메소드가 참인 경우
				return false;				// 거짓 반환
			}
			
			res = (res + Integer.parseInt(lifo.pop())) % MOD;	// 그 외 스택 내의 값을 더하고
		}
		
		return true;		// 참 반환
	}
	
	/**
	 * 결과적으로 스택 내부에 숫자가 아닌 괄호가 들어가있는지 확인하는 메소드
	 * 
	 */
	private static boolean isBrace(String tmp) {
		return	tmp.equals(ONE_B) || tmp.equals(TWO_B) || tmp.equals(THREE_B) ||
				tmp.equals(SHUT_ONE_B) || tmp.equals(SHUT_TWO_B) || tmp.equals(SHUT_THREE_B) ? true : false;
	}
	
	/**
	 * 괄호와 괄호 사이의 숫자와 st에 해당하는 괄호 값의 연산을 처리하는 메소드
	 * 
	 */
	private static boolean calculate(String st, String nd, String rd, int value) {
		int num = 0;

		while (!lifo.isEmpty()) {
			String top = lifo.peek();

			if (top.equals(nd) || top.equals(rd)) {		// 만약 맞지않은 괄호가 스택 사이에 존재면 거짓 반환
				return false;
			} 
			
			else if (top.equals(st)) {			// 맞는 괄호인 경우
				lifo.pop();
				num *= value;					// 최종적으로 더해왔던 num의 값과 괄호에 해당하는 값(value)를 곱해줌
				
				if(num > MOD) num %= MOD;		// num이 MOD 보다 큰 경우 나머지 연산
				
				lifo.push(String.valueOf(num));		// 계산된 숫자를 다시 문자열로 바꾸고 스택에 push
				break;
			}
			
			else {								// 스택 중간 숫자가 있는 경우
				if(num > MOD) num %= MOD;		// MOD 보다 큰 경우 나머지 연산을 실시하고
				num += Integer.parseInt(lifo.pop());	// num의 값을 더해줌
			}
		}
		
		return true;			// 비정상 종료가 없었기 때문에 참 반환
	}
}
