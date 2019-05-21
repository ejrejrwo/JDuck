
public class mainClass {

	public static void main(String[] args) {
		// 1 ~ 10 J Q K *3
		// == 52
		// 52을 셔플
		// 0 ~ 51 
		// 스페이드, 다이아, 하트, 클로버
		// 0 ~ 12 13 ~ 25
		
/*
	랜덤 숫자 -> 카드 숫자 -> 그림
	13 			1		다이아
	51			13(K)	클로버
				:
 
 */
		
		
		
		//2중		
		int arr[][] = new int[4][13];
		
		//shuffle
		int r_num[] = new int[52];
		
		
		boolean swit[] = new boolean[52];		
		int r, w;
		
		for (int i = 0; i < swit.length; i++) {
			swit[i] = false;		
		}
		int result;
		w = 0;
		while(w < arr[0].length) {
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[0].length; j++) {
					result = arr[i][j] = w+1;
					System.out.println("["+i+"]"+"["+j+"]"+result);
					w++;
				}
			}
		}
		
		w = 0;
		while(w < r_num.length) {
			result = (int)(Math.random() * 52); // 0 ~ 9
			if(swit[result] == false) {
				swit[result] = true;
				
				r_num[w] = result + 1;		// 1 ~ 10
				for (int i = 0; i < arr.length; i++) {
					for (int j = 0; j < arr[0].length; j++) {
						if(r_num[w] == arr[i][j] && i == 0) {
							System.out.println(r_num[w]+"	카드종류: 스페이드" + "	카드숫자:"+(j+1));
						}else if(r_num[w] == arr[i][j] && i == 1) {
							System.out.println(r_num[w]+"	카드종류: 다이아" + "	카드숫자:"+(j+1));
						}else if(r_num[w] == arr[i][j] && i == 2) {
							System.out.println(r_num[w]+"	카드종류: 하트" + "	카드숫자:"+(j+1));
						}else if(r_num[w] == arr[i][j] && i == 3) {
							System.out.println(r_num[w]+"	카드종류: 클로버" + "	카드숫자:"+(j+1));
						}
					}
				}
				w++;
			}
		}
		
		

		
//		w = 0;
//		while(w < r_num.length) {
//			result = (int)(Math.random() * 52); // 0 ~ 9
//			if(swit[result] == false) {
//				swit[result] = true;
//				
//				r_num[w] = result + 1;		// 1 ~ 10
//				if(r_num[w]/13 == 0) {
//					System.out.println(r_num[w]+"     스페이드"+"		"+r_num[w]%13);
//				}else if(r_num[w]/13 == 1) {
//					System.out.println(r_num[w]+"     다이아"+"		"+r_num[w]%13);
//				}else if(r_num[w]/13 == 2) {
//					System.out.println(r_num[w]+"     하트"+"			"+r_num[w]%13);
//				}else if(r_num[w]/13 == 3) {
//					System.out.println(r_num[w]+"     클로버"+"		"+r_num[w]%13);
//				}
//				w++;
//				
//				
//			}
//		}
		


		

	}

}
