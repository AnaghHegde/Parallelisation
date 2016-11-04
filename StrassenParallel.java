import java.util.*;
public class StrassenParallel{

    Random r, r1;
    public static float [][] strassen(float [][] a, float [][] b){
		int n = a.length;
		float [][] result = new float[n][n];

		if((n%2 != 0 ) && (n !=1)){
			float[][] a1, b1, c1;
			int n1 = n+1;
			a1 = new float[n1][n1];
			b1 = new float[n1][n1];
			c1 = new float[n1][n1];

			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++){
					a1[i][j] =a[i][j];
					b1[i][j] =b[i][j];
				}
			c1 = strassen(a1, b1);
			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++)
					result[i][j] =c1[i][j];
			return result;
		}

		if(n == 1){
			result[0][0] = a[0][0] * b[0][0];
		}
		else{
			float [][] A11 = new float[n/2][n/2];
			float [][] A12 = new float[n/2][n/2];
			float [][] A21 = new float[n/2][n/2];
			float [][] A22 = new float[n/2][n/2];

			float [][] B11 = new float[n/2][n/2];
			float [][] B12 = new float[n/2][n/2];
			float [][] B21 = new float[n/2][n/2];
			float [][] B22 = new float[n/2][n/2];

			divide(a, A11, 0 , 0);
			divide(a, A12, 0 , n/2);
			divide(a, A21, n/2, 0);
			divide(a, A22, n/2, n/2);

			divide(b, B11, 0 , 0);
			divide(b, B12, 0 , n/2);
			divide(b, B21, n/2, 0);
			divide(b, B22, n/2, n/2);

			//Threading goes here
			MyThread one =new MyThread(add(A11, A22), add(B11, B22));
			MyThread two = new MyThread(add(A21, A22), B11);
			MyThread three = new MyThread(A11, sub(B12, B22));
			MyThread four = new MyThread(A22, sub(B21, B11));
			MyThread five = new MyThread(add(A11, A12), B22);
			MyThread six = new MyThread(sub(A21, A11), add(B11, B12));
			MyThread seven = new MyThread(sub(A12, A22), add(B21, B22)); 
			
			try{
				one.t.join();
				two.t.join();
				three.t.join();
				four.t.join();
				five.t.join();
				six.t.join();
				seven.t.join();
				
			}
			
			catch(Exception e){
				System.out.println("Oh! its an error");
			}			
		
		}
		return result;
	}

	public static float [][] add(float [][] A, float [][] B){
		int n = A.length;

		float [][] result = new float[n][n];

		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
			result[i][j] = A[i][j] + B[i][j];

		return result;
	}

	public static float [][] sub(float [][] A, float [][] B){
		int n = A.length;

		float [][] result = new float[n][n];

		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
			result[i][j] = A[i][j] - B[i][j];

		return result;
	}

	public static void divide(float[][] p1, float[][] c1, int iB, int jB){
		for(int i1 = 0, i2=iB; i1<c1.length; i1++, i2++)
			for(int j1 = 0, j2=jB; j1<c1.length; j1++, j2++)
			{
				c1[i1][j1] = p1[i2][j2];
			}
	}

	public static void copy(float[][] c1, float[][] p1, int iB, int jB){
		for(int i1 = 0, i2=iB; i1<c1.length; i1++, i2++)
			for(int j1 = 0, j2=jB; j1<c1.length; j1++, j2++)
			{
				p1[i2][j2] = c1[i1][j1];
			}
	}


    public static void main(String[] args) {
        Random rand = new Random(10000);
        Scanner in=new Scanner(System.in);
        //int t=in.nextInt();
	//while(t > 0){
		int n=in.nextInt();
		float[][] M1 = new float[n][n];
		float[][] M2 = new float[n][n];
		float[][] arr=new float[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				//float randno1 = rand.nextFloat()*n*1.00;
				M1[i][j]=in.nextFloat();
				//float randno2 =rand.nextFloat()*n*1.00;
			
				}
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				//float randno1 = rand.nextFloat()*n*1.00;
				M2[i][j]=in.nextFloat();
				//float randno2 =rand.nextFloat()*n*1.00;
			
				}		
		long startTime = System.currentTimeMillis();       
		arr = strassen(M1,M2);
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	        
    }

}

//thread class starts from here
class MyThread implements Runnable{
	float[][] M1;
	float[][] M2;
	float[][] ans;
	Thread t;
	public MyThread(float[][] M1,float[][] M2){
		this.M1=M1;
		this.M2=M2;
		t=new Thread(this);
		t.start();
	}
	@Override
	public void run(){
		ans=strassen(M1,M2);
	}
	
	public static float [][] strassen(float [][] a, float [][] b){
		int n = a.length;
		float [][] result = new float[n][n];

		if((n%2 != 0 ) && (n !=1)){
			float[][] a1, b1, c1;
			int n1 = n+1;
			a1 = new float[n1][n1];
			b1 = new float[n1][n1];
			c1 = new float[n1][n1];

			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++){
					a1[i][j] =a[i][j];
					b1[i][j] =b[i][j];
				}
			c1 = strassen(a1, b1);
			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++)
					result[i][j] =c1[i][j];
			return result;
		}

		if(n == 1){
			result[0][0] = a[0][0] * b[0][0];
		}
		else{
			float [][] A11 = new float[n/2][n/2];
			float [][] A12 = new float[n/2][n/2];
			float [][] A21 = new float[n/2][n/2];
			float [][] A22 = new float[n/2][n/2];

			float [][] B11 = new float[n/2][n/2];
			float [][] B12 = new float[n/2][n/2];
			float [][] B21 = new float[n/2][n/2];
			float [][] B22 = new float[n/2][n/2];

			divide(a, A11, 0 , 0);
			divide(a, A12, 0 , n/2);
			divide(a, A21, n/2, 0);
			divide(a, A22, n/2, n/2);

			divide(b, B11, 0 , 0);
			divide(b, B12, 0 , n/2);
			divide(b, B21, n/2, 0);
			divide(b, B22, n/2, n/2);
			
			float [][] P1 = strassen(add(A11, A22), add(B11, B22));
			float [][] P2 = strassen(add(A21, A22), B11);
			float [][] P3 = strassen(A11, sub(B12, B22));
			float [][] P4 = strassen(A22, sub(B21, B11));
			float [][] P5 = strassen(add(A11, A12), B22);
			float [][] P6 = strassen(sub(A21, A11), add(B11, B12));
			float [][] P7 = strassen(sub(A12, A22), add(B21, B22));
		
			float [][] C11 = add(sub(add(P1, P4), P5), P7);
			float [][] C12 = add(P3, P5);
			float [][] C21 = add(P2, P4);
			float [][] C22 = add(sub(add(P1, P3), P2), P6);

			copy(C11, result, 0 , 0);
			copy(C12, result, 0 , n/2);
			copy(C21, result, n/2, 0);
			copy(C22, result, n/2, n/2);
		}
		return result;
	}


	public static float [][] add(float [][] A, float [][] B){
		int n = A.length;

		float [][] result = new float[n][n];

		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
			result[i][j] = A[i][j] + B[i][j];

		return result;
	}

	public static float [][] sub(float [][] A, float [][] B){
		int n = A.length;

		float [][] result = new float[n][n];

		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
			result[i][j] = A[i][j] - B[i][j];

		return result;
	}

	public static void divide(float[][] p1, float[][] c1, int iB, int jB){
		for(int i1 = 0, i2=iB; i1<c1.length; i1++, i2++)
			for(int j1 = 0, j2=jB; j1<c1.length; j1++, j2++)
			{
				c1[i1][j1] = p1[i2][j2];
			}
	}

	public static void copy(float[][] c1, float[][] p1, int iB, int jB){
		for(int i1 = 0, i2=iB; i1<c1.length; i1++, i2++)
			for(int j1 = 0, j2=jB; j1<c1.length; j1++, j2++)
			{
				p1[i2][j2] = c1[i1][j1];
			}
	}

	
}
