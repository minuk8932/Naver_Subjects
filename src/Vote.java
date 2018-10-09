import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 * 	@author minchoba
 * 
 * 	실행시 EOF 감지를 하지 못합니다.
 * 	따라서 입력 데이터를 복사해 넣으시고 'Enter'를 한번 더 입력해 주십시오.
 * 
 */
public class Vote {
	private static final char NEW_LINE = '\n';	// 개행 문자 상수
	private static final String EMPTY = "";		// 공백 문자열 상수
	
	public static void main(String[] args) throws Exception{
		// 버퍼를 통한 값 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		HashMap<String, Integer> hm = new HashMap<>();		// 후보자들의 이름과 받은 표수를 저장 할 해쉬맵 선언
		
		String input = EMPTY;
		while((input = br.readLine()) != null) {			// EOF의 경우 반복문 종료 (End of File)
			if (EMPTY.equals(input)) break;					// 또는 단지 빈 문자열 하나가 들어온 경우에도 반복문 종료. (EOF로 끝나지 않을 상황 대비)
			
			if(hm.containsKey(input)) {						// 해쉬 맵에 해당 이름이 존재하는 경우
				hm.put(input, hm.get(input) + 1);			// 그 이름, 이름의 득표수+1을 해쉬맵에 다시 put
			}
			else {
				hm.put(input, 1);							// 해쉬맵에 없는 경우 이름, 득표수: 1 저장
			}
		}
		br.close();					// 입력이 끝남, 버퍼 닫음
		
		int max = 0;
		
		for(Entry<String, Integer> e: hm.entrySet()) {
			if(e.getValue() > max) max = e.getValue();		// 최고 득표수를 max에 뽑아냄
		}
		
		int idx = 0;
		PriorityQueue<MaxPeople> pq = new PriorityQueue<>();	// ArrayList -> Priority 수정
												// 어차피 결과 출력시 뽑아내어도 될 값이고, 리스트 내부 정렬도 필요하기 때문에, 이 모두를 만족 할 수 있는 우선순위 큐 사용
		
		for(Entry<String, Integer> e: hm.entrySet()) {
			if(e.getValue() == max){						// 동률이 있을 경우를 대비하여 리스트에 최고 득표수를 갖는 사람의 이름을 모두 저장
				pq.offer(new MaxPeople(e.getKey()));
			}
		}
		
		StringBuilder sb = new StringBuilder();
		int pqSize = pq.size();
		
		while(pqSize-- > 0) {					// 우선순위 큐에 사전순으로 담긴 이름들을 하나씩 개행 문자와 함께 버퍼에 담고
			sb.append(pq.poll().name).append(NEW_LINE);
		}
		
		System.out.println(sb.toString());					// 출력 예제에 따라 결과 값 한번에 출력
	}
	
	/**
	 * 최대 득표자 이름 저장 이너 클래스
	 * @author minchoba
	 *
	 */
	private static class MaxPeople implements Comparable<MaxPeople>{
		String name;
		
		public MaxPeople(String name) {
			this.name = name;
		}

		@Override
		public int compareTo(MaxPeople mp) {				// 여러 이름들이 존재할 경우, 대소문자 구문없이 사전순으로 정렬함
			return this.name.compareToIgnoreCase(mp.name);
		}
	}
}
