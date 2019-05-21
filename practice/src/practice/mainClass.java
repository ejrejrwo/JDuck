package practice;

import java.util.Scanner;

public class mainClass {

	public static void main(String[] args) {
		
		//중복인거 찾아내기
		
		Scanner sc = new Scanner(System.in);
		int s,  input;
		
		System.out.print("배열 수: ");
		s = sc.nextInt();
		int[] num =  new int[s];
		int a = 0;
		int i = 0;
		while(true) {
			
			
			System.out.print(a + "값: ");
			input = sc.nextInt();
			num[a] = input;
			
			if(a < s) {
				a++;
				
				for (int j = 0; j < a-1; j++) {
					if(num[j] == input) {
						System.out.println("중복입니다.");
					}
					
				}
				
			} else {
				break;
			}
			
		}
		System.out.println("끝");
	
		
		
		

	}

}
