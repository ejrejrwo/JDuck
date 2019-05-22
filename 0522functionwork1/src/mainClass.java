
public class mainClass {

	
	
	public static void main(String[] args) {
		
		System.out.println(getDistance(1,1,2,2));
		
	}
	

	
	static double getDistance(int x, int y, int x1, int y1) {
		
		
		int a1 = (int)Math.pow(x1-x, 2);
		int b1 = (int)Math.pow(y1-y, 2);
		double c1 = Math.sqrt(a1+b1);
		
		return c1;
		
	}

}
