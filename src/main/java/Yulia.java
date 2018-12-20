public class Yulia {
    public static void main(String... args){
        int sum = 0;
        for (int i=1; i <= 6; i++) {
            sum += fun(i);
        }
        System.out.println(sum);
    }

    public static int fun(int s){
        if (s == 0) { return 0; }

        return s > 2 ? fun (s-1) + fun (s-2) : 1;
    }
}
