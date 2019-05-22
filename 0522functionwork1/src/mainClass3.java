
public class mainClass3 {

	public static void main(String[] args) {
		int num1[] = { 1, 2, 3, 4, 5 };
		getDouble(num1);
		for (int i = 0; i < num1.length; i++) {
			System.out.println("num1[" + i + "] = " + num1[i] );
		}

	}
	static int[] getDouble(int num[]) {
		for (int i = 0; i < num.length; i++) {
			num[i] = num[i]*2;
		}
		return num;
	}
	
}
