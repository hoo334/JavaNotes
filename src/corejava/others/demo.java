package corejava.others;

public class demo {
    public static void main(String[] args){
       // printArrays(7);
        printTower(11);
    }

   static void printArrays(int n){
        for(int i=1;i<=n;++i){
            for(int j=1;j<=n;++j){
                System.out.printf("%3d",n*(i-1)+j);
            }
            System.out.println();
        }
    }

   static void printTower(int n){
        if(n%2==0)
            return;
        int mid = n/2+1;
        for(int i=1;i<=n;++i){
            for(int j=1;j<=Math.abs(mid-i);++j)
                System.out.print(' ');
            for(int j=1;j<=2*(mid-Math.abs(mid-i))-1;j++)
                System.out.print('*');
            System.out.println();
        }
    }
}
