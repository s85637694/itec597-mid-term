import java.util.Arrays;

public class KnightsTemplarBank {

    public int[] resolve(int amount, int[] options) {
        int[] result ={-1, -1};
        int[] f = new int[amount + 1];
        int[] n = new int[amount + 1];

        f[0] = 0;
        n[0] = 1;
        if (amount > 0) {
            for (int i = 0; i < options.length; i++) {
                for (int j = options[i]; j <= amount; j++) {
                    if (j - options[i] >= 0) {
                        n[j] += n[j - options[i]];
                    }
                }
            }
            result[0] = n[amount];
            for (int i = 1; i <= amount; i++) {
                int cost = amount;
                for (int j = 0; j < options.length; j++) {
                    if (i - options[j] >= 0) {
                        cost = min(f[i-options[j]] + 1, cost);
                    }
                }
                f[i] = cost;
//                System.out.println("f[" + i + "]=" + cost );
            }
            result[1] = f[amount];

        }
        if (result[0] == 0) {
            result[0] = -1;
            result[1] = -1;
        }
        return result;
    }

    private int min(int a, int b) {
        return a < b ? a : b;
    }

    public static void main(String[] args) {

        KnightsTemplarBank bank = new KnightsTemplarBank();
        //int amount = 10000;
        int amount = 6249;
        int[] options = {6, 49, 83, 8};
        System.out.println(Arrays.toString(bank.resolve(amount, options)));

    }

}