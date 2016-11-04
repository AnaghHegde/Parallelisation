import java.util.*;

class MatrixMulDivideParallel{
	static float i=0;
	static float j=0;
	static float k=0;
	public static void main(String args[]){
		Scanner in=new Scanner(System.in);
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
			arr = calc(M1,M2);
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println(totalTime);
	}	

     public static float[][] calc(float[][] a, float[][] b){
        int len = a.length;
        float[][] c = new float[len][len];

        if (len == 1) {
            c[0][0] = a[0][0] * b[0][0];
        } else {
            //int len/2 = a.length / 2;
            float[][] smalla11 = new float[len/2][len/2];//a
            float[][] smalla12 = new float[len/2][len/2];//b
            float[][] smalla21 = new float[len/2][len/2];//c
            float[][] smalla22 = new float[len/2][len/2];//d
            float[][] smallb11 = new float[len/2][len/2];//e
            float[][] smallb12 = new float[len/2][len/2];//f
            float[][] smallb21 = new float[len/2][len/2];//g
            float[][] smallb22 = new float[len/2][len/2];//h
            //answer matrix c
            float[][] smallc11 = new float[len/2][len/2];            
            float[][] smallc12 = new float[len/2][len/2];
            float[][] smallc21 = new float[len/2][len/2];
            float[][] smallc22 = new float[len/2][len/2];
            for(int i=0;i<len/2;i++)
               for(int j=0;j<len/2;j++){
               	  smalla11[i][j]=a[i][j];
                  smallb11[i][j]=b[i][j];
               }
            for(int i=0;i<len/2;i++)
               for(int j=len/2;j<len;j++){  
               	  smalla12[i][j-len/2]=a[i][j];
                  smallb12[i][j-len/2]=b[i][j];
               }   
            for(int i=len/2;i<len;i++)
               for(int j=0;j<len/2;j++){
                  smalla21[i-len/2][j]=a[i][j];
                  smallb21[i-len/2][j]=b[i][j];
               }
            for(int i=len/2;i<len;i++)
               for(int j=len/2;j<len;j++){  
                  smalla22[i-len/2][j-len/2]=a[i][j];
                  smallb22[i-len/2][j-len/2]=b[i][j];
               }
	   //threading starts here
	    MyThread one = new MyThread(smalla11,smallb11);
            MyThread two = new MyThread(smalla12,smallb21);
            MyThread three = new MyThread(smalla11,smallb12);
            MyThread four = new MyThread(smalla12,smallb22);
            MyThread five = new MyThread(smalla21,smallb11);
            MyThread six = new MyThread(smalla22,smallb21);
            MyThread seven = new MyThread(smalla21,smallb12);
            MyThread eight = new MyThread(smalla22,smallb22);

            try {
                one.t.join();
                two.t.join();
                three.t.join();
                four.t.join();
                five.t.join();
                six.t.join();
                seven.t.join();
                eight.t.join();
            }
            catch (Exception x)
            {
                System.out.println(x);
            }
            smallc11=add(one.ans,two.ans);
            smallc12=add(three.ans,four.ans);
            smallc21=add(five.ans,six.ans);
            smallc22=add(seven.ans,eight.ans);
            
            for(int i=0;i<len/2;i++)
               for(int j=0;j<len/2;j++)
                 c[i][j]=smallc11[i][j];   
            for(int i=0;i<len/2;i++)
               for(int j=len/2;j<len;j++)
                 c[i][j]=smallc12[i][j-len/2];      
            for(int i=len/2;i<len;i++)
               for(int j=0;j<len/2;j++)
                 c[i][j]=smallc21[i-len/2][j];
            for(int i=len/2;i<len;i++)
               for(int j=len/2;j<len;j++)
                  c[i][j]=smallc22[i-len/2][j-len/2];
        }
       return c;
    }
    
    public static float[][] add(float[][] a,float[][] b){
        int size=a.length;  
        float[][] c=new float[size][size];
	for(int i=0;i<size;i++)
        	for(int j=0;j<size;j++)
            		c[i][j]=a[i][j]+b[i][j];
      return c;
    }
}

class MyThread implements Runnable{
    float[][] matrix1;
    float[][] matrix2;
    float[][] ans;
    Thread t;
    public MyThread(float[][] matrix1,float[][] matrix2)
    {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        t = new Thread(this);
        t.start();
    }
    @Override
    public void run()
    {
        ans = calc(matrix1,matrix2);
    }
    
    public static float[][] calc(float[][] a, float[][] b){
        int len = a.length;
        float[][] c = new float[len][len];

        if (len == 1) {
            c[0][0] = a[0][0] * b[0][0];
        } else {
            //int len/2 = a.length / 2;
            float[][] smalla11 = new float[len/2][len/2];//a
            float[][] smalla12 = new float[len/2][len/2];//b
            float[][] smalla21 = new float[len/2][len/2];//c
            float[][] smalla22 = new float[len/2][len/2];//d
            float[][] smallb11 = new float[len/2][len/2];//e
            float[][] smallb12 = new float[len/2][len/2];//f
            float[][] smallb21 = new float[len/2][len/2];//g
            float[][] smallb22 = new float[len/2][len/2];//h
            //answer matrix c
            float[][] smallc11 = new float[len/2][len/2];            
            float[][] smallc12 = new float[len/2][len/2];
            float[][] smallc21 = new float[len/2][len/2];
            float[][] smallc22 = new float[len/2][len/2];
            for(int i=0;i<len/2;i++)
               for(int j=0;j<len/2;j++){
               	  smalla11[i][j]=a[i][j];
                  smallb11[i][j]=b[i][j];
               }
            for(int i=0;i<len/2;i++)
               for(int j=len/2;j<len;j++){  
               	  smalla12[i][j-len/2]=a[i][j];
                  smallb12[i][j-len/2]=b[i][j];
               }   
            for(int i=len/2;i<len;i++)
               for(int j=0;j<len/2;j++){
                  smalla21[i-len/2][j]=a[i][j];
                  smallb21[i-len/2][j]=b[i][j];
               }
            for(int i=len/2;i<len;i++)
               for(int j=len/2;j<len;j++){  
                  smalla22[i-len/2][j-len/2]=a[i][j];
                  smallb22[i-len/2][j-len/2]=b[i][j];
               }
	   
	   smallc11=add(calc(smalla11,smallb11),calc(smalla12,smallb21));
            smallc12=add(calc(smalla11,smallb12),calc(smalla12,smallb22));
            smallc21=add(calc(smalla21,smallb11),calc(smalla22,smallb21));
            smallc22=add(calc(smalla21,smallb12),calc(smalla22,smallb22));
	   
            for(int i=0;i<len/2;i++)
               for(int j=0;j<len/2;j++)
                 c[i][j]=smallc11[i][j];   
            for(int i=0;i<len/2;i++)
               for(int j=len/2;j<len;j++)
                 c[i][j]=smallc12[i][j-len/2];      
            for(int i=len/2;i<len;i++)
               for(int j=0;j<len/2;j++)
                 c[i][j]=smallc21[i-len/2][j];
            for(int i=len/2;i<len;i++)
               for(int j=len/2;j<len;j++)
                  c[i][j]=smallc22[i-len/2][j-len/2];
        }
       return c;
    }
    
    public static float[][] add(float[][] a,float[][] b){
        int size=a.length;  
        float[][] c=new float[size][size];
	for(int i=0;i<size;i++)
        	for(int j=0;j<size;j++)
            		c[i][j]=a[i][j]+b[i][j];
      return c;
    }
}
