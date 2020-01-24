package lesson2;

import java.util.*;

public class Main {

    private static final int SUBSTRINGS = 4, NUMBERS_IN_SUBSTRING = 4;

    private static class CountSubstringsException extends RuntimeException {
        CountSubstringsException(String message) {
            super("Ошибка в количестве подстрок: " + message);
        }
    }

    private static class CountNumbersInSubStringException extends RuntimeException {
        CountNumbersInSubStringException(String message) {
            super("Ошибка в количестве цифр подстроки: " + message);
        }
    }

    private static class NotNumberException extends RuntimeException {
        NotNumberException(String message) {
            super("Одна из ячеек соджержит не число: " + message);
        }
    }

    private static String[][] stringConversionToMatrix(String s) {
        String[] temp = s.split("\n");
        if (temp.length != SUBSTRINGS)
            throw new CountSubstringsException("\n" + Arrays.toString(temp) + "\nВ поданной строке всего подстрок: " + temp.length);
        String[][] matrix = new String[SUBSTRINGS][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = temp[i].split(" ");
            if (matrix[i].length != NUMBERS_IN_SUBSTRING)
                throw new CountNumbersInSubStringException("\n" + Arrays.toString(matrix[i]) + "\nЦифр в полученной подстроке: " + matrix[i].length);
        }
        return matrix;
    }

    private static float calculateMatrix(String[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                try {
                    sum += Integer.parseInt(matrix[i][j]);
                } catch (NumberFormatException e) {
                    throw new NotNumberException(matrix[i][j]);
                }
            }
        }
        return sum / 2f;
    }

    public static void main(String[] args) {

        String str = "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0",
                strErr1 = "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0\n5 8 4 7\n28 8 13 7\n2 80 11 6",
                strErr2 = "10 3 1 2\n2 3 2 2",
                strErr3 = "7 10 3 1 2 9 56 43\n2 3 2 2\n5 6 7 1\n300 3 1 0",
                strErr4 = "3 1\n3 2\n6 7\n1 0",
                strErr5 = "10 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 Kate 0";

        try {
            String[][] matrix = stringConversionToMatrix(str);
//            String[][] matrix = stringConversionToMatrix(strErr1);
//            String[][] matrix = stringConversionToMatrix(strErr2);
//            String[][] matrix = stringConversionToMatrix(strErr3);
//            String[][] matrix = stringConversionToMatrix(strErr4);
//            String[][] matrix = stringConversionToMatrix(strErr5);
            System.out.println(Arrays.deepToString(matrix));
            System.out.println(calculateMatrix(matrix));
        } catch (NotNumberException e) {
            System.out.println("Ошибка при подсчете: \n" + e.getMessage());
        } catch (CountSubstringsException | CountNumbersInSubStringException e) {
            System.out.println(e.getMessage());
        }

    }
}
