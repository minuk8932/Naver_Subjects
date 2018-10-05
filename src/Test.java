import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Bracket 정답 검증 클래스
 * @author minchoba
 *
 */
public class Test {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		String[] res = new String[N];
		for(int i = 0; i < N; i++) {
			res[i] = br.readLine();
		}
		
		String newLine = br.readLine();
		
		boolean isCorrect = true;
		
		for(int i = 0; i < N; i++) {
			if(!res[i].equals(br.readLine())) {
				isCorrect = false;
				System.out.println(res[i]);
				break;
			}
		}
		
		System.out.println(isCorrect);
	}
}
