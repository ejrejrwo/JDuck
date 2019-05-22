
public class mainClass6 {

	public static void main(String[] args) {
		
		String strNumber1 = "123.4567";
		String strNumber2 = "456";
		mesg(strNumber1);
		mesg(strNumber2);
//		System.out.println(mesg(strNumber1));
//		System.out.println(mesg(strNumber2));
	}
	
	static void mesg(String strNumber) { // void 출력 까지 
		
		boolean c1 = true;
		for (int i = 0; i < strNumber.length(); i++) {
			char a = strNumber.charAt(i);
			int b = (int)a;
			if( b == 46 ) {
				c1 = false;
				break;
			}
			
		}
		
//		if (c1 = false) {
//			System.out.println("실수입니다.");
//		}else {
//			System.out.println("정수입니다.");
//		}
		
		System.out.println(c1);
		
		
	}
	

}
