package lesson03;

import java.util.Random;
import java.util.Scanner;

public class XOGame {

    /** Знак, который ставит игрок-человек */
    public static final char HUMAN_DOT = 'X';

    /** Знак, который ставит игрок-компьютер */
    public static final char PC_DOT = 'O';

    /** Знак пустой клетки */
    public static final char EMPTY_DOT = '_';

    /** Сканер для считывания символов с консли */
    public static final Scanner SCANNER = new Scanner(System.in);

    /** Объект Random для генерации случайных чисел */
    public static final Random RANDOM = new Random();

    /** Игровое поле */
    public static char[][] map;

    /** Ширина игрового поля */
    public static int mapSizeX;

    /** Высота игрового поля */
    public static int mapSizeY;

    /** Длина выигрышной комбинации */
    public static int winLength;

    /** Инициализирует игровое поле */
    public static void initMap(){
        mapSizeX = 5;
        mapSizeY = 5;
        winLength = 3;
        map = new char[mapSizeY][mapSizeX];

        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                map[y][x] = EMPTY_DOT;
            }
        }
    }

    /** Выводит на консоль игровое поле с крестиками и ноликами */
    public static void printMap(){
        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                System.out.print(map[y][x] + "|");
            }
            System.out.println();
        }
        System.out.println();
    }

    /** Выполняет ход пользователя: запрашивает у него координаты клетки, проверяет кооректность ввода и ставит в эту клетку знак */
    public static void humanTurn(){
        int x;
        int y;

        do {
            System.out.println("Введите свои координаты: ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isValidCell(y,x) || !isEmptyCell(y, x));

        map[y][x] = HUMAN_DOT;
    }

    /** Выполняет ход компьютера. Случайным образом генерирует координаты клетки и ставит в эту клетку знак */
    public static void aiTurn(){
        int x;
        int y;

        // Перед тем как сделать ход случайным образом, компьютер проверяет два условия:
        // 1. Компьютеру остался один ход до победы
        // 2. Игроку остался один ход до победы
        // Если метод checkIsAboutToWin() найдёт такую ситуацию, он сделает этот ход и вернёт true.
        // Если же ни для компьютера, ни для человека такой ситуации не найдено,
        // только тогда нужно делать ход случайным образом.
        if (!checkIsAboutToWin(PC_DOT) && !checkIsAboutToWin(HUMAN_DOT)){
            do {
                x = RANDOM.nextInt(mapSizeX);
                y = RANDOM.nextInt(mapSizeY);
            } while (!isEmptyCell(y, x));

            map[y][x] = PC_DOT;
        }
    }

    /**
     * Проверяет координаты клетки на валидность
     * @param y Координата Y
     * @param x Координата X
     * @return Истина, если эти координаты находятся внутри игрового поля, и ложь, если они выходят за пределы поля
     */
    public static boolean isValidCell(int y, int x){
        return x >= 0 && x < mapSizeX && y >= 0 && y < mapSizeY;
    }

    /**
     * Проверяет клетку на пустоту
     * @param y Координата Y
     * @param x Координата X
     * @return Истина, если клетка пустая, иначе ложь
     */
    public static boolean isEmptyCell(int y, int x){
        return map[y][x] == EMPTY_DOT;
    }

    /**
     * Проверяет, не победила ли одна из сторон
     * @param inboxChar Знак игрока или компьютера
     * @return Истина, если одна из сторон победила, иначе ложь
     */
    public static boolean checkWin(char inboxChar){
        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                if (checkWinForCell(y, x, inboxChar)){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Проверяет, не начинается ли из этой клетки победная комбинация (по горизонтали, вертикали или диагонали)
     * @param y Координата Y
     * @param x Координата X
     * @return Истина, если из этой клетки начинается победная комбинация, иначе ложь
     */
    private static boolean checkWinForCell(int y, int x, char inboxChar) {
        /** Длина ряда крестиков или ноликов */
        int length = 0;

        // Двигаемся по горизонтали
        // Начальная позиция - координата x, переданная в этот метод в качестве параметра
        // Конечная позиция - ширина поля
        for (int i = x; i < mapSizeX; i++) {

            // Номер строки остаётся одним и тем же (координата y).
            // Если каждый перебираемый символ равен нужному символу, увеличиваем длину ряда.
            // Иначе (если наткнулись на другой символ), прерываем цикл
            if (map[y][i] == inboxChar){
                length++;
            } else {
                break;
            }

            // После каждой итерации проверяем, достигла ли длина ряда значения, достаточного для победы.
            // Если достигла, возвращаем true.
            if (length == winLength){
                return true;
            }
        }

        // Двигаемся по вертикали
        length = 0;
        for (int i = y; i < mapSizeY; i++) {
            if (map[i][x] == inboxChar){
                length++;
            } else {
                break;
            }

            if (length == winLength){
                return true;
            }
        }

        // Двигаемся по главной диагонали (вправо и вниз). В этом случае увеличиваем значения обоих итераторов на 1
        length = 0;
        for (int i = y, j = x; i < mapSizeY && j < mapSizeX; i++, j++) {
            if (map[i][j] == inboxChar){
                length++;
            } else {
                break;
            }

            if (length == winLength){
                return true;
            }
        }

        // Двигаемся по второстепенной диагонали (влево и вниз). В этом случае итератор i, который идёт по строкам, увеличивается,
        // а итератор j, который идёт по столбцам, уменьшается
        length = 0;
        for (int i = y, j = mapSizeX - 1; i < mapSizeY && j >= 0; i++, j--) {
            if (map[i][j] == inboxChar){
                length++;
            } else {
                break;
            }

            if (length == winLength){
                return true;
            }
        }

        return false;
    }

    /**
     * Проверяет, не возникла ли такая ситуация, когда до победы одной из сторон остался один ход.
     * Если такая ситуация возникла, ставит в пустую клетку знак компьютера -
     * нолик (чтобы победить или блокировать победу игрока)
     * @param inboxChar Знак игрока или компьютера
     * @return Истина, если программа нашла такую ситуацию и сделала ход, иначе - ложь
     */
    public static boolean checkIsAboutToWin(char inboxChar){
        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                if (checkIsAboutToWinForCell(y, x, inboxChar)){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Проверяет, не начинается ли из этой клетки комбинация, в которой остался один ход до победы.
     * Если такая комбинация найдена, программа делает этот ход, чтобы победить или блокировать победу игрока.
     * @param y Координата Y
     * @param x Координата X
     * @return Истина, если программа нашла такую комбинацию и сделала ход, иначе - ложь
     */
    public static boolean checkIsAboutToWinForCell(int y, int x, char inboxChar){
        int length = 0;

        // Двигаемся по горизонтали
        // Конечная позиция - ширина поля минус один, потому что нам
        // обязательно нужна следующая клетка, и она должна быть пустой
        for (int i = x; i < mapSizeX - 1; i++) {
            if (map[y][i] == inboxChar){
                length++;
            } else {
                break;
            }

            // После каждой итерации проверяем условия: длина ряда должна быть на единицу меньше победной длины,
            // а следующая клетка должна быть пустой
            // Если эти условия выполняются, значит, компьютеру или игроку до победы остался один ход.
            // Вне зависимости от того, чей это ход (компьютера или игрока) ставим в следующую клетку
            // знак компьютера, так как в первом случае компьютер победит, а во втором - блокирует
            // победу пользователя. После того, как ход сделан, возвращаем true.
            if (length == winLength - 1 && map[y][i + 1] == EMPTY_DOT){
                map[y][i + 1] = PC_DOT;
                return true;
            }
        }

        // Двигаемся по вертикали
        length = 0;
        for (int i = y; i < mapSizeY - 1; i++) {
            if (map[i][x] == inboxChar){
                length++;
            } else {
                break;
            }

            if (length == winLength - 1 && map[i + 1][x] == EMPTY_DOT){
                map[i + 1][x] = PC_DOT;
                return true;
            }
        }

        // Двигаемся по главной диагонали (вправо и вниз). В этом случае увеличиваем значения обоих итераторов на 1
        length = 0;
        for (int i = y, j = x; i < mapSizeY - 1 && j < mapSizeX - 1; i++, j++) {
            if (map[i][j] == inboxChar){
                length++;
            } else {
                break;
            }

            if (length == winLength - 1 && map[i + 1][j + 1] == EMPTY_DOT){
                map[i + 1][j + 1] = PC_DOT;
                return true;
            }
        }

        // Двигаемся по второстепенной диагонали (влево и вниз). В этом случае итератор i, который идёт по строкам, увеличивается,
        // а итератор j, который идёт по столбцам, уменьшается
        length = 0;
        for (int i = y, j = mapSizeX - 1; i < mapSizeY - 1 && j > 0; i++, j--) {
            if (map[i][j] == inboxChar){
                length++;
            } else {
                break;
            }

            if (length == winLength  && map[i + 1][j - 1] == EMPTY_DOT){
                map[i + 1][j - 1] = PC_DOT;
                return true;
            }
        }

        return false;
    }

    /**
     * Проверяет, не заполнилось ли игровое поле (ничья)
     * @return Истина, если игровое поле заполнилось, иначе ложь
     */
    public  static boolean isMapFull(){
        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                if (map[y][x] == EMPTY_DOT) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Инициализируем и выводим на консоль игровое поле
        initMap();
        printMap();

        // В бесконечном цикле начинаем игру
        while (true){
            // Предоставляем ход игроку и выводим на консоль игровое поле,
            // после чего выполняем проверку на победу или ничью
            humanTurn();
            printMap();
            if (checkWin(HUMAN_DOT)){
                System.out.println("Human win!!!");
                break;
            }
            if (isMapFull()){
                System.out.printf("Ничья!!!");
                break;
            }

            // Далее аналогичным образом ходит компьютер
            aiTurn();
            printMap();
            if (checkWin(PC_DOT)){
                System.out.println("Computer win!!!");
                break;
            }
            if (isMapFull()){
                System.out.printf("Ничья!!!");
                break;
            }
        }
    }
}
