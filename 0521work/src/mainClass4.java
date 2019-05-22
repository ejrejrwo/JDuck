
public class mainClass4 {

	public static void main(String[] args) {
		// abcdefghijklmnopqrstuvwxyz    암호화 복호화 + 해주고 비교 
		char[] abcCode =

			{
			'`','~','!','@','#','$','%','^','&','*',

			'(',')','-','_','+','=','|','[',']','{',

			'}',';',':',',','.','/'};
			
			// 0 1 2 3 4 5 6 7 8 9

			char[] numCode = {'q','w','e','r','t','y','u','i','o','p'};
			String src = "abc123";

			String result = "";

			// 문자열 src의 문자를 charAt()으로 하나씩 읽어서 변환 후 result에 저장
			for(int i=0; i < src.length();i++) {

				char ch = src.charAt(i);
				
				int num = (int)ch;	// 아스키 코드를 취득 **
				
				
				// 알파벳일 경우 -> 숫자 97 <= abc... <=122 **
				if(num >= 97 && num <= 122) {
					num = num - 97;						//배열의 [0] 98 일때 배열의[1]**
					
					result = result + abcCode[num];		//한글자씩 넣는중 **
				}
				// 알파벳이 아닐경우 -> 문자 abc
				else {
					num = num - 48;						// '0' == 0 으로 세팅하기 위해서
					
					result = result + numCode[num];
				}
			}

			System.out.println("암호src:"+src);

			System.out.println("result:"+result);

		 

		
	}	// end of main

}	// end of class
