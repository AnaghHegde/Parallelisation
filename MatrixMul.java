import java.util.*;

class MatrixMul{
	public static void main(String args[]){
		Scanner in=new Scanner(System.in);
			int n=in.nextInt();
			float[][] M1 = new float[n][n];
			float[][] M2 = new float[n][n];
			float[][] ans=new float[n][n];
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++){
					M1[i][j]=in.nextFloat();			
					}
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++){
					M2[i][j]=in.nextFloat();
					}		
			long startTime = System.currentTimeMillis();  
			float sum=0;     
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					for(int k=0;k<n;k++){
						sum=sum+M1[i][k]*M2[k][j];
						}
					ans[i][j]=sum;
					sum=0;	
					}
				}	
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println(totalTime);
			
	}
}
