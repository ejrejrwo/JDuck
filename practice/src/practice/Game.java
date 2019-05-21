package practice;

import java.util.Scanner;

public class Game {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("게임시작");

		int random = (int) (Math.random() * 100) + 1;
		int user = sc.nextInt();
		System.out.println(random);
		for (int i = 0; i < 10; i++) {

			if (random > user) {
				System.out.println("큽니다. 남은횟수 : [" + (10 - i) + "]");
				user = sc.nextInt();
			} else if (random < user) {
				System.out.println("작습니다. 남은횟수 : [" + (10 - i) + "]");
				user = sc.nextInt();
			} else {
				System.out.println("맞았습니다.");
				break;
			}
		}

	}
}
