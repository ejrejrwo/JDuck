
public class mainClass7 {

	static void encryption(String str) {
		char[] abcCode =

			{
			'`','~','!','@','#','$','%','^','&','*',

			'(',')','-','_','+','=','|','[',']','{',

			'}',';',':',',','.','/'};

			// 0 1 2 3 4 5 6 7 8 9
		
			char[] numCode = {'q','w','e','r','t','y','u','i','o','p'};
			
			String result = "";
			
			for (int i = 0; i < str.length(); i++) {
				char ch1=str.charAt(i);
				int ch2 = (int)ch1;
				if(ch2 >= 97 && ch2 <= 122) {
					 ch2 = ch2 - 97;
					 result = result + abcCode[ch2];
				}else  {
					ch2 = ch2 - 48;
					 result = result + numCode[ch2];
				}
				
			}
		
			System.out.println(result);
		
	}
	
	static void Decryption(String str) {
		char[] abcCode =

			{
			'`','~','!','@','#','$','%','^','&','*',

			'(',')','-','_','+','=','|','[',']','{',

			'}',';',':',',','.','/'};

			// 0 1 2 3 4 5 6 7 8 9
		
			char[] numCode = {'q','w','e','r','t','y','u','i','o','p'};
			
			String result = "";
			
			for (int i = 0; i < str.length(); i++) {
				
				char ch1=str.charAt(i);
				
				int ch2 = (int)ch1;
				
				if(ch2 >= 97 && ch2 <= 122) {
					 ch2 = ch2 - 97;
					 result = result + abcCode[ch2];
				}
				else  {
					ch2 = ch2 - 48;
					 result = result + numCode[ch2];
				}
				
			}
		
			System.out.println(result);
	}
	
	
	public static void main(String[] args) {
		// abcdefghijklmnopqrstuvwxyz
		String a = "abc123";
		
		char[] abcCode =

		{
		'`','~','!','@','#','$','%','^','&','*',

		'(',')','-','_','+','=','|','[',']','{',

		'}',';',':',',','.','/'};

		// 0 1 2 3 4 5 6 7 8 9

		char[] numCode = {'q','w','e','r','t','y','u','i','o','p'};
		
		encryption(a);
	}
	
	

}
