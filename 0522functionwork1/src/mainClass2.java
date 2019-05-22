
public class mainClass2 {

	public static void main(String[] args) {
		
		
		int[] original = {1,2,3,4,5,6,7,8,9};

		System.out.println(java.util.Arrays.toString(original));

		int[] result = shuffle(original);

		System.out.println(java.util.Arrays.toString(result));

	}
	
	static int[] shuffle(int arr[]) {
		
		
		boolean swit[] = new boolean[arr.length];
		
		int r, w;
		
		for (int i = 0; i < swit.length; i++) {
			swit[i] = false;
		}
		
		w = 0;
		
		while(w < arr.length) {
			int a = 0;
			r = (int)(Math.random()* arr.length);
			if(swit[r] == false) {
				swit[r] = true;
				a = arr[r];
				arr[w] = a;
				w++;
				
			}
		}
		
		return arr;
		
		
	}

}
