import java.util.*;

public class Task4 {

    public static void main(String[] args) {
//        System.out.println(nonRepeat("abababcac"));
//        System.out.println(bruteForce(5, 3));
//        System.out.println(encode(new int[]{0, 31, 28, 10, 29}, "MKIIT"));
//        System.out.println(Arrays.toString(decode("MTUCI", "MKIIT")));
//        System.out.println(split("((())())(()(()()))"));
//        System.out.println(shortHand("vvvvaajaaaaa"));
//        System.out.println(convertToRome(52));
//        System.out.println(uniqueSubstring("12223234333"));
//        System.out.println(labirint(new int[][]{{1, 3, 1}, {1, -1, 1}, {4, 2, 1}}));
//        System.out.println(numericOrder("re6sponsibility Wit1h gr5eat power3 4comes g2reat"));
          System.out.println(fibString("ABC"));
    }

    // таск 1
    public static String nonRepeat(String input) {
        Map<Character, Integer> counts = new HashMap<>();
        fillCounts(input.toLowerCase(), counts);
        return removeExcess(input, counts, 0);
    }
    private static String removeExcess(String input, Map<Character, Integer> counts, int index) {
        if (index == input.length()) {
            return "";
        }
        char currentChar = input.charAt(index);
        char lowerChar = Character.toLowerCase(currentChar);
        if (counts.get(lowerChar) <= 3) {
            return currentChar + removeExcess(input, counts, index + 1);
        } else {
            return removeExcess(input, counts, index + 1);
        }
    }
    private static void fillCounts(String input, Map<Character, Integer> counts) {
        for (char ch : input.toCharArray()) {
            counts.put(ch, counts.getOrDefault(ch, 0) + 1);
        }
    }

    // таск 2
    public static List<String> bruteForce(int n, int k) {
        List<String> result = new ArrayList<>();
        generateCombinations("", n, k, result);
        return result;
    }

    private static void generateCombinations(String prefix, int n, int k, List<String> result) {
        if (prefix.length() == n) {
            result.add(prefix);
            return;
        }
        for (int i = 0; i < k; i++) {
            if (!prefix.contains(String.valueOf(i))) {
                generateCombinations(prefix + i, n, k, result);
            }
        }
    }

    // таск 3
    public static int[] decode(String text, String key) {
        int[] result = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            result[i] = text.charAt(i) ^ key.charAt(i);
        }
        return result;
    }

    public static String encode(int[] numbers, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            result.append((char) (numbers[i] ^ key.charAt(i)));
        }
        return result.toString();
    }

    // таск 4
    public static List<String> split(String input) {
        List<String> result = new ArrayList<>();
        int balance = 0;
        StringBuilder cluster = new StringBuilder();
        for (char ch : input.toCharArray()) {
            cluster.append(ch);
            balance += (ch == '(') ? 1 : -1;
            if (balance == 0) {
                result.add(cluster.toString());
                cluster.setLength(0);
            }
        }
        return result;
    }

    // таск 5
    public static String shortHand(String input) {
        StringBuilder result = new StringBuilder();
        int count = 1;
        for (int i = 1; i <= input.length(); i++) {
            if (i < input.length() && input.charAt(i) == input.charAt(i - 1)) {
                count++;
            } else {
                result.append(input.charAt(i - 1));
                if (count > 1) {
                    result.append('*').append(count);
                }
                count = 1;
            }
        }
        return result.toString();
    }

    // таск 6
    public static String convertToRome(int num) {
        String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                result.append(romanNumerals[i]);
                num -= values[i];
            }
        }
        return result.toString();
    }

    // таск 7
    public static String uniqueSubstring(String input) {
        int[] counts = new int[10];
        int[] firstIndexes = new int[10];
        Arrays.fill(firstIndexes, -1);
        for (int i = 0; i < input.length(); i++) {
            int digit = input.charAt(i) - '0';
            counts[digit]++;
            if (firstIndexes[digit] == -1) {
                firstIndexes[digit] = i;
            }
        }
        int maxCount = 0;
        int minIndex = input.length();
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > maxCount) {
                maxCount = counts[i];
                minIndex = firstIndexes[i];
            } else if (counts[i] == maxCount && firstIndexes[i] < minIndex) {
                minIndex = firstIndexes[i];
            }
        }
        return (minIndex % 2 == 0) ? "чет" : "нечет";
    }

    // таск 8
    public static List<String> labirint(int[][] matrix) {
        int n = matrix.length;
        if (n == 0 || matrix[0].length == 0) {
            return List.of("Прохода нет");
        }
        int[][] cost = new int[n][n];
        String[][] path = new String[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.MAX_VALUE;
                path[i][j] = "";
            }
        }
        cost[n - 1][n - 1] = matrix[n - 1][n - 1];
        path[n - 1][n - 1] = Integer.toString(matrix[n - 1][n - 1]);
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] < 0) {
                    continue;
                }
                if (i > 0 && cost[i][j] + matrix[i - 1][j] < cost[i - 1][j]) {
                    cost[i - 1][j] = cost[i][j] + matrix[i - 1][j];
                    path[i - 1][j] = path[i][j] + "-" + matrix[i - 1][j];
                }
                if (j > 0 && cost[i][j] + matrix[i][j - 1] < cost[i][j - 1]) {
                    cost[i][j - 1] = cost[i][j] + matrix[i][j - 1];
                    path[i][j - 1] = path[i][j] + "-" + matrix[i][j - 1];
                }
            }
        }
        if (cost[0][0] == Integer.MAX_VALUE) {
            return List.of("Прохода нет");
        }
        return List.of(path[0][0], Integer.toString(cost[0][0]));
    }

    // таск 9
    public static String numericOrder(String input) {
        String[] words = input.split(" ");
        String[] result = new String[words.length];
        for (String word : words) {
            int pos = word.replaceAll("\\D+", "").isEmpty() ? 0 : Integer.parseInt(word.replaceAll("\\D+", "")) - 1;
            result[pos] = word.replaceAll("\\d+", "");
        }
        return String.join(" ", result);
    }

    // таск 10
    public static boolean fibString(String input) {
        int[] counts = new int[256];
        for (char ch : input.toCharArray()) {
            counts[ch]++;
        }
        List<Integer> frequencies = new ArrayList<>();
        for (int count : counts) {
            if (count > 0) frequencies.add(count);
        }
        Collections.sort(frequencies);
        for (int i = 2; i < frequencies.size(); i++) {
            if (frequencies.get(i) != frequencies.get(i - 1) + frequencies.get(i - 2)) {
                return false;
            }
        }
        return true;
    }
}
