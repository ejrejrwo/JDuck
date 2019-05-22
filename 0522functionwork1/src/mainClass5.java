
public class mainClass5 {

	public static void main(String[] args) {
		String str = "123";

		System.out.println(str+"는 숫자입니까? "+isNumber(str));

		str = "1234o1";

		System.out.println(str+"는 숫자입니까? "+isNumber(str));

	}
	
	
	static boolean isNumber(String str) {
		
		
		boolean b1 = true;
		for (int i = 0; i < str.length(); i++) {
			char a = str.charAt(i);
			int b = (int)a;
			
			if(b < 48 || b > 57) {
				return false;
			} 
			
		}
		
		return b1;
		
	}

}


