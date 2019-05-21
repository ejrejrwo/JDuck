package work2;

import java.util.Scanner;

public class mainClass {

	public static void main(String[] args) {
		// 구구단 출력
		// 2중 for
		// 1 * 1 = 1 ~~~

		// 입력 : 3
		// 3 * 1 = 3 ~~~

		int i, j, x;

		for (i = 2; i < 10; i++) {

			System.out.println();

			for (j = 1; j < 10; j++) {
				x = i * j;
				System.out.print(i + " x " + j + " = " + x + "\t");
			}
			System.out.println();
		}

		Scanner sc = new Scanner(System.in);

		System.out.print("숫자 입력: ");
		int inNum = sc.nextInt();

		for (i = 1; i < 10; i++) {

			int a = inNum * i;
			System.out.print(inNum + " x " + i + " = " + a + "\t");
			
		}

	}

}
