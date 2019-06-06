
public class MissionTrip {
    private int count;
    private int[][] paths;
    private int[][] status;

    public MissionTrip() {
        count = 0;
    }


    public int resolve(int[][] paths) {
        this.paths = paths;
        int n = paths.length;
        int m = paths[0].length;
        status = new int[n][m];
        calculate(0, 0);
        return count;
    }

    private void calculate(int i, int j) {
        status[i][j] = 1;
        if (i >= 1 && status[i - 1][j] != 1 && paths[i - 1][j] == 0) {
            calculate(i - 1, j);
        }
        if (i < paths.length - 1 && status[i + 1][j] != 1 && paths[i + 1][j] == 0) {
            calculate(i + 1, j);
        }
        if (j >= 1 && status[i][j - 1] != 1 && paths[i][j - 1] == 0) {
            calculate(i, j - 1);
        }
        if (j < paths[0].length - 1 && status[i][j + 1] != 1 && paths[i][j + 1] == 0) {
            calculate(i, j + 1);
        }
        status[i][j] = 0;
        if (i == paths.length - 1 && j == paths[0].length - 1) count++;
    }

    public static void main(String[] args) {
        long a=System.currentTimeMillis();

        MissionTrip missionTrip = new MissionTrip();
        int[][] map = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        System.out.println(missionTrip.resolve(map));
        System.out.println((System.currentTimeMillis()-a)/1000f+ "秒");
    }


}