
public class mainClass1 {

	public static void main(String[] args) {
		int[] arr = { 10, 20, 30, 40, 50 };
		int sum = 0;
		
		for (int i = 0; i < arr.length; i++) {
			sum = arr[i] + sum;
			
		}
		
		System.out.println("sum = " + sum);
	}

}
