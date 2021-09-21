import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) {
        int[] a = new int[12];
        float[] x = new float[16];
        double[][] ans = new double[12][16];
        for(int i = 0; i < 16; i++){
            if(i < 12){
                a[i] = i + 4;
            }
            x[i] = ((float) Math.random() * 14) - 9;
        }
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 16; j++){
                if(a[i] == 12){
                    double temp = (x[j] - 2)/14;
                    temp = tan(asin(temp));
                    temp = 0.75 * temp;
                    ans[i][j] = temp;
                }else if(a[i] == 6 || a[i] == 7 || a[i] == 10 || a[i] == 11 || a[i] == 13 || a[i] == 14){
                    double temp = pow(2 * x[j], x[j]);
                    temp = temp - 1;
                    temp = 0.75 * temp + 0.75;
                    temp = temp / pow(x[j], 1/9);
                    temp = pow(temp, 2);
                    ans[i][j] = temp;
                }else{
                    double temp = pow(x[j], x[j] + 1);
                    temp = pow(temp, 3);
                    temp = pow(E, temp);
                    temp = log10(temp);

                    double temp2 = pow(0.5 * x[j], 3);
                    temp2 = pow(3 * temp2, 3);
                    temp2 = 0.5 * Math.cbrt(temp2);
                    temp = pow(temp, temp2);
                    ans[i][j] = temp;
                }
            }
        }

        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 16; j++){
                System.out.print(String.format("%.4f", ans[i][j]) + " ");
            }
            System.out.println();
        }
    }
}
