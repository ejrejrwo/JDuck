
public class mainClass7 {

	static void encryption(String str) {
		char[] abcCode =

			{
			'`','~','!','@','#','$','%','^','&','*',

			'(',')','-','_','+','=','|','[',']','{',

			'}',';',':',',','.','/'};

			// 0 1 2 3 4 5 6 7 8 9

			char[] numCode = {'q','w','e','r','t','y','u','i','o','p'};
			
			for (int i = 0; i < str.length(); i++) {
				char ch1=str.charAt(i);
				int ch2 = (int)ch1;
				if(ch2 > 96) {
					char result = abcCode[(ch2 - 97)];
				}else  {
					char result = numCode[(ch2 - 48)];
				}
				
			}
		
			
			
		
		
	}
	
//	static void Decryption
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

	}
	
	

}
