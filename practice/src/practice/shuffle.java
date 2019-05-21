package practice;

public class shuffle {

	public static void main(String[] args) {
		
		int r_num[] = new int[3];
		int u_num[] = new int[3];
		
		boolean swit[] = new boolean[10];		// 00000 00000
		int r, w;
		
		for (int i = 0; i < swit.length; i++) {
			swit[i] = false;		// 00000 000000
		}
		
//		00000 00000
//		random number -> 3
//		00010 00000
//		random number -> 5
//		00010 10000
		
		w = 0;
		
		swit[0] = true;
		swit[1] = true;
		swit[2] = true;
		swit[3] = true;
		swit[4] = true;
		
//		swit[35] = true; // 제외 숫자
		
		while(w < u_num.length) {
			r = (int)(Math.random() * 10); // 0 ~ 9
			if(swit[r] == false) {
				swit[r] = true;
				
				r_num[w] = r + 1;		// 1 ~ 10
				w++;
			}
		}
		
		for (int i = 0; i < r_num.length; i++) {
			System.out.println("r_num[" + i + "]" + " = " + r_num[i]);
		}

	}

}

