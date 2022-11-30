import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String strMatrix = "{1,2;3,4}";
        strMatrix = (strMatrix.replace("{", "")).replace("}", ""); // 中カッコの除去
        String[] row = strMatrix.split(";"); // すべての行の読み込み
        int rowLength = row.length; // 行の長さチェック
        int columnLength = row[0].split(",").length; // 一行目で列の長さチェック
        double[][] matrix = new double[rowLength][columnLength]; //行列を準備
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                matrix[i][j] = Double.parseDouble(row[i].split(",")[j]); //行列を double[][]形式に変換 }
            }
        }

        double[] result = new double[columnLength];
        for (int i = 0; i < rowLength; i++) {
            double total = matrix[0][i] + matrix[1][i];
            result[i] = total;
        }
        System.out.println("result = " + Arrays.toString(result));
        System.out.println("determinant = " + (result[0] - result[1]));
    }
}