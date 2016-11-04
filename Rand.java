import java.util.*;

class Rand{
	public static void main(String args[]){
		Scanner in=new Scanner(System.in);
		int n=in.nextInt();
		float[][] M1=new float[n][n];
		float[][] M2=new float[n][n];
		Random rand=new Random();
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				int no = rand.nextInt(n - 1)+0;
				float randno1 = rand.nextFloat()+1.00f * no;
				M1[i][j]=randno1;
				float randno2 =rand.nextFloat()+10.00f * no;
				M2[i][j]=randno2;
		}
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++)
				System.out.print(M1[i][j]+" ");
			System.out.println();
		}
		
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++)
				System.out.print(M2[i][j]+" ");
			System.out.println();
		}			
		
	}
}
