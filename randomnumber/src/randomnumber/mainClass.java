package randomnumber;

import java.util.Scanner;

public class mainClass {

	public static void main(String[] args) {
		/*
		 * random(1~100) user input 65 - 50너무 작습니다. up down 10번의 기회
		 * 
		 * random -> input -> finding(메세지) -> 결과
		 * 
		 */
		int ranNum, userInput;
		Scanner sc = new Scanner(System.in);
		// 1.랜덤숫자
		double random = Math.random();
		ranNum = (int) (random * 100) + 1;
		System.out.println(ranNum);

		// 2.숫자 입력
		System.out.print("숫자를 입력해주세요: ");
		userInput = sc.nextInt();

		// 3.크기 비교 , 10번 기회 끝
		for (int i = 0; i < 10; i++) {
			// 다른숫자 입력시 오류
			if (userInput <= 0 || userInput > 100) {
				i--;
				System.out.println("정확한 숫자를 입력해 주세요: " + "남은기회: " + (9 - i));
			} else if (ranNum < userInput) {
				System.out.println("작음습니다. " + " 남은기회: " + (9 - i));

			} else if (ranNum > userInput) {
				System.out.println("큽니다." + " 남은기회: " + (9 - i));

			} else if (ranNum == userInput) {
				System.out.println("정답입니다.");
				break;
			}
			for (int j = 0;; j++) {
				System.out.print("숫자를 입력해주세요: ");
				userInput = sc.nextInt();
				break;
			}

		}
		System.out.println("끝");

	}

}
