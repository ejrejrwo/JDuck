
public class mainClass4 {

	public static void main(String[] args) {
		
		int[] data = { 3, 2, 9, 4, 7 };
		System.out.println(java.util.Arrays.toString(data));
		System.out.println("최대값:" + max(data));

	}
	
	static int max(int arr[]) {
		int size = arr.length;
        int max; 
        int temp;
        
        if(arr.length == 0 || arr == null) {
        	return -99999;
        }
        else {
        for(int i=0; i<size-1; i++){ 
            max = i;
            for(int j=i+1; j<size; j++){
                if(arr[max] < arr[j]){
                	max = j;
                }
            }
            temp = arr[max];
            arr[max] = arr[i];
            arr[i] = temp;
        }
        
        return arr[0];
        }
	}

}
