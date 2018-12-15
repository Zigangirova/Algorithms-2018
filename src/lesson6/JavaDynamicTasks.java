package lesson6;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * <p>
     * Трудоемкость = O(n * m)n - длина 1 строки, m - длина 2 строки
     * Ресурсоемкость = O(n * m)
     */


    public static String longestCommonSubSequence(String first, String second) {
        String res = "";
        int fir = first.length();
        int sec = second.length();
        int[][] max = new int[fir + 1][sec + 1];
        for (int i = 1; i < fir; i++) {
            for (int j = 1; j < sec; j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    max[i + 1][j + 1] = 1 + max[i][j];
                } else max[i + 1][j + 1] = Math.max(max[i + 1][j], max[i][j + 1]);
            }
        }
        int i = fir;
        int j = sec;
        while (i > 0 && j > 0) if (first.charAt(i - 1) == second.charAt(j - 1)) {
            res = first.charAt(i - 1) + res;
            i--;
            j--;
        } else if (max[i][j] == max[i - 1][j]) {
            i--;
        } else j--;
        return res;
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     * Трудоемкость = O(n^2)
     * Ресурсоемкость = O(n)
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {

        int sizeL = list.size();
        if (sizeL == 0) return list;
        int[] r = new int[sizeL];
        int[] max = new int[sizeL];
        int p = 0;
        int l = max[0];
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < sizeL; i++) {
            max[i] = 1;
            r[i] = -1;
            for (int k = 0; k < i; k++) {
                if (list.get(k) < list.get(i) && max[k] + 1 > max[i]) {
                    max[i] = max[k] + 1;
                    r[i] = k;
                }
            }
        }

        for (int i = 0; i < max.length; i++) {
            if (max[i] > l) {
                p = i;
                l = max[i];
            }
        }

        while (p != -1) {
            result.add(list.get(p));
            p = r[p];
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
