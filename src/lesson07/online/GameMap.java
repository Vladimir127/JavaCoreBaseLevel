package lesson07.online;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/** Панель, на котором будет отображаться игровое поле и проходить игра */
public class GameMap extends JPanel {
    // Константы для обозначения режимов игры
    public static final int GAME_MODE_HUMAN_VS_AI = 0;
    public static final int GAME_MODE_HUMAN_VS_HUMAN = 1;

    // Константы для обозначения знаков в клетках
    private static final int DOT_EMPTY = 0;
    private static final int DOT_HUMAN = 1;
    private static final int DOT_AI = 2;

    // Константы для обозначения результата игры: ничья, победил человек или компьютер
    private static final int STATE_DRAW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;
    private static final int STATE_WIN_PLAYER_1 = 3;
    private static final int STATE_WIN_PLAYER_2 = 4;

    /** Флаг завершения игры */
    private boolean isGameOver;
    private boolean initializedMap;

    /** Состояние завершенной игры - принимает значение одной из констант, объявленных выше */
    private int stateGameOver;

    /** Режим игры - человек против компьютера или против другого человека */
    private int mode;

    public static final Random RANDOM = new Random();

    // Поля, хранящие в себе значения параметров, заданные пользователем
    private int fieldSizeX;
    private int fieldSizeY;
    private int winLength;
    private int[][] field;

    private int cellWidth;
    private int cellHeight;

    private final Color GAME_MAP_COLOR = new Color(23, 73, 62);
    private final Color LINE_COLOR = Color.WHITE;
    private final Color DOT_HUMAN_COLOR = Color.RED;
    private final Color DOT_AI_COLOR = Color.ORANGE;

    private int playerNumber = 1;

    /**
     * Инициализирует экземпляр класса
     */
    GameMap(){
        setBackground(GAME_MAP_COLOR);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                update(e);
            }
        });
        initializedMap = false;
    }

    /**
     * Начинает новую игру
     * @param mode Режим (человек против компьютера или человек против человека)
     * @param fieldSizeX Ширина игрового поля
     * @param fieldSizeY Высота игрового поля
     * @param winLength Длина выигрышной позиции
     */
    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLength){
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winLength = winLength;
        field = new int[fieldSizeX][fieldSizeY];

        this.mode = mode;

        isGameOver = false;
        initializedMap = true;
        repaint();
    }

    /**
     * Задаёт результат игры
     * @param gameOverState Результат игры (одна из трёх констант, объявленных выше)
     */
    private void setGameOver(int gameOverState) {
        stateGameOver = gameOverState;
        isGameOver = true;
        repaint();
    }

    // Данный метод вызывается компонентами Swing. Запрещается вызывать его вручную.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    /**
     * Реагирует на щелчок мышью по игровому полю и обновляет его состояние
     * @param e Событие щелчка мыши
     */
    private void update(MouseEvent e) {
        // Если игра ещё не началась, ничего не делаем. Это предотвращает исключение деления на ноль
        if (!initializedMap) return;

        // Если игра уже окончена, перестаём реагировать на щелчки,
        // иначе после победы пользователь сможет продолжать ставить крестики
        if (isGameOver) return;

        // Определяем индексы ячейки, разделив координаты курсора на количество ячеек
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;

        // Проверяем, является ли эта ячейка валидной и пустой
        if (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) {
            return;
        }

        // Ставим нужный знак в соответствующий элемент массива
        if (mode == GAME_MODE_HUMAN_VS_AI) {

            // Если человек против компьютера, ставим знак человека
            field[cellY][cellX] = DOT_HUMAN;

        } else if (mode == GAME_MODE_HUMAN_VS_HUMAN) {
            // Если человек против человека, то будем считать знак человека
            // знаком первого игрока, а знак компьютера - знаком второго игрока
            if (playerNumber == 1){
                field[cellY][cellX] = DOT_HUMAN;
            } else {
                field[cellY][cellX] = DOT_AI;
            }
        }

        // Проверяем, не выиграл ли человек, и если выиграл, объявляем конец игры
        if (checkWin(DOT_HUMAN)) {

            if (mode == GAME_MODE_HUMAN_VS_AI) {
                setGameOver(STATE_WIN_HUMAN);
            } else if (mode == GAME_MODE_HUMAN_VS_HUMAN) {
                setGameOver(STATE_WIN_PLAYER_1);
            }
            return;
        }

        // Проверяем, не переполнена ли карта, и если переполнена, объявляем конец игры
        if (isFullMap()) {
            setGameOver(STATE_DRAW);
            return;
        }

        // Если режим игры - человек против компьютера, делаем ход компьютера
        if (mode == GAME_MODE_HUMAN_VS_AI){
            aiTurn();
        }

        // Иначе, если режим игры - человек против человека,
        else if (mode == GAME_MODE_HUMAN_VS_HUMAN){
            // Меняем номер игрока
            if (playerNumber == 1){
                playerNumber = 2;
            } else {
                playerNumber = 1;
            }
        }

        // Перерисовываем игровое поле
        repaint();

        // Проверяем, не выиграл ли компьютер, и если выиграл, объявляем конец игры
        if (checkWin(DOT_AI)){
            if (mode == GAME_MODE_HUMAN_VS_AI) {
                setGameOver(STATE_WIN_AI);
            } else if (mode == GAME_MODE_HUMAN_VS_HUMAN) {
                setGameOver(STATE_WIN_PLAYER_2);
            }
            return;
        }
        if (isFullMap()) {
            setGameOver(STATE_DRAW);
            return;
        }

    }

    /**
     * Дополнительный метод для отрисовки нужных нам компонентов на элементе Graphics
     * @param g Объект Graphics
     */
    private void render(Graphics g){
        if (!initializedMap)
            return;

        // Получаем ширину и высоту панели, на которой будет расчерчено игровое поле
        int width = getWidth();
        int height = getHeight();

        // Получаем ширину и высоту одной ячейки
        cellWidth = width / fieldSizeX;
        cellHeight = height / fieldSizeY;

        // Устанавливаем цвет кисти для рисования линий
        g.setColor(LINE_COLOR);

        // Рисуем линии по горизонтали
        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, width, y);
        }

        // Рисуем линии по вертикали
        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, height);
        }

        // Рисуем крестики и нолики
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {

                // Если текущая ячейка пустая, ничего не рисуем
                if (isEmptyCell(x, y)) {
                    continue;
                }

                // Приводим объект Graphics к типу Graphics2D и далее будем рисовать на нём
                // (это нужно, чтобы у нас была возможность задать дополнительные настройки линий).
                Graphics2D g2 = (Graphics2D) g;

                Stroke stroke = new BasicStroke(7, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

                int padding = 10;

                // Если соответствующая ячейка массива содержит знак человека,
                // рисуем красный крестик
                if (field[y][x] == DOT_HUMAN) {
                    g2.setColor(DOT_HUMAN_COLOR);
                    g2.setStroke(stroke);
                    g2.drawLine(x * cellWidth + padding,
                            y * cellHeight + padding,
                            (x + 1) * cellWidth - padding,
                            (y + 1) * cellHeight - padding);

                    g2.drawLine(x * cellWidth + padding,
                            (y + 1) * cellHeight - padding,
                            (x + 1) * cellWidth - padding,
                            y * cellHeight + padding);
                }

                // Если ячейка содержит знак компьютера, рисуем жёлтый нолик
                else if (field[y][x] == DOT_AI) {
                    g2.setColor(DOT_AI_COLOR);
                    g2.setStroke(stroke);
                    g2.drawOval(x * cellWidth + padding,
                            y * cellHeight + padding,
                            cellWidth - padding * 2,
                            cellHeight - padding * 2);
                }

                // Если что-то пошло не так, и программа не смогла отрисовать ни крестик, ни нолик,
                // выбрасываем исключение
                else {
                    throw new RuntimeException("Can't paint cellX " + x + " cellY " + y);
                }

            }
        }

        // Если игра закончилась, выводим сообщение о результате игры
        if (isGameOver) {
            showMessageGameOver(g);
        }
    }

    /**
     * Отрисовывает сообщение о результате игры
     * @param g Объект Graphics
     */
    private void showMessageGameOver(Graphics g) {
        // Устанавливаем для фонового прямоугольника серый цвет и отрисовываем его
        g.setColor(Color.GRAY);

        // Вычисляем координату Y плашки с подсказкой,
        // чтобы по вертикали она была выровнена по центру
        int height = getHeight();
        int y = (height - 70) / 2;

        g.fillRect(0, y, getWidth(), 70);

        // Устанавливаем для текста цвет и шрифт.
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 45));

        // В зависимости от результата игры выводим соответствующее сообщение
        switch (stateGameOver) {
            case STATE_DRAW:
                g.drawString("Ничья", 180, y + 50);
                break;
            case STATE_WIN_HUMAN:
                g.drawString("Победил человек", 50, y + 50);
                break;
            case STATE_WIN_AI:
                g.drawString("Победил ИИ", 100, y + 50);
                break;
            case STATE_WIN_PLAYER_1:
                g.drawString("Победил игрок №1", 30, y + 50);
                break;
            case STATE_WIN_PLAYER_2:
                g.drawString("Победил игрок №2", 30, y + 50);
                break;
            default:
                throw new RuntimeException("Unexpected game over state: " + stateGameOver);
        }
    }

    /**
     * Выполняет ход компьютера
     */
    public void aiTurn() {
        // Проверяем, выиграет ли игрок на следующем ходу, с помощью метода turnAIWinCell().
        // Этот метод проверяет, не сложилась ли такая ситуация, что на следующем ходу может
        // выиграть игрок. Если такая ситуация сложилась, он блокирует этот ход, ставя свой
        // знак в нужную клетку, и возвращает true. Поэтому если метод turnAIWinCell() вернул
        // true, то в данном методе нам уже не нужно делать ход.
        if (turnAIWinCell()) {
            return;
        }

        // Аналогичным образом проверяем, выиграет ли комп на следующем ходу.
        if (turnHumanWinCell()) {
            return;
        }

        // Если таких предвыигрышных ситуаций не сложилось, ставим знак в случайную клетку.
        int x;
        int y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));
        field[y][x] = DOT_AI;
    }

    /**
     * Проверяет, может ли компьютер одержать победу в игре одним следующим ходом
     * @return Истина, если такая ситуация возможна, и такой ход сделан
     */
    private boolean turnAIWinCell() {
        // Перебираем все клетки поля подряд
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                // Если клетка пустая, ставим в неё знак компьютера.
                if (isEmptyCell(j, i)) {
                    field[i][j] = DOT_AI;

                    // Если мы выиграли, вернём истину, оставив нолик в выигрышной позиции
                    if (checkWin(DOT_AI)) {
                        return true;
                    }

                    // Если нет - вернём обратно пустоту в клетку и пойдём дальше
                    field[i][j] = DOT_EMPTY;
                }
            }
        }
        return false;
    }

    /**
     * Проверяет, может ли человек одержать победу в игре одним следующим ходом
     * @return Истина, если такая ситуация возможна, и сделан ход, блокирующий победу
     */
    private boolean turnHumanWinCell() {
        // Перебираем все клетки поля подряд
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {

                // Если клетка пустая, ставим в неё знак человека и проверяем,
                // не выиграет ли человек при таком раскладе
                if (isEmptyCell(j, i)) {
                    field[i][j] = DOT_HUMAN;
                    if (checkWin(DOT_HUMAN)) {

                        // Если игрок победит, поставим на то место знак компьютера,
                        // чтобы заблокировать этот ход, и возвращаем true
                        field[i][j] = DOT_AI;
                        return true;
                    }

                    // В противном случае вернуть на место пустоту
                    field[i][j] = DOT_EMPTY;
                }
            }
        }
        return false;
    }

    /**
     * Проверка на победу
     * @param c Знак того игрока (компьютера или человека), которого мы будем проверять на победу
     * @return Истина, если данный игрок победил, иначе - ложь
     */
    private boolean checkWin(int c) {
        // Перебираем всё поле
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {

                // Из каждой клетки строим линию по четырём направлениям:
                // вверх, вниз и по двум диагоналям
                // и проверяем каждую линию с помощью метода checkLine()
                if (checkLine(i, j, 1, 0, winLength, c)) {
                    return true;    // проверим линию по х
                }
                if (checkLine(i, j, 1, 1, winLength, c)) {
                    return true;    // проверим по диагонали х у
                }
                if (checkLine(i, j, 0, 1, winLength, c)) {
                    return true;    // проверим линию по у
                }
                if (checkLine(i, j, 1, -1, winLength, c)) {
                    return true;    // проверим по диагонали х -у
                }
            }
        }
        return false;
    }

    /**
     * Выполняет проверку линии, проведённой из данной клетки в данном направлении, на победную комбинацию
     * @param x Координата X данной клетки
     * @param y Координата Y данной клетки
     * @param vx Координата X единичного вектора для указания направления
     * @param vy Координата Y единичного вектора для указания направления
     * @param len Длина выигрышной позиции
     * @param c Знак того игрока (компьютера или человека), которого мы будем проверять на победу
     * @return Истина, если на этой линии есть выигрышная комбинация, иначе - ложь
     */
    private boolean checkLine(int x, int y, int vx, int vy, int len, int c) {
        // В зависимости от направления вычисляем координаты конца линии
        final int farX = x + (len - 1) * vx;
        final int farY = y + (len - 1) * vy;

        // Проверим, не выйдет ли проверяемая линия за пределы поля,
        // проверив её конец с помощью метода isValidCell
        if (!isValidCell(farX, farY)) {
            return false;
        }

        // Ползём по проверяемой линии и проверяем, одинаковые ли символы в ячейках.
        // Если найдётся символ, который отличается от нужного, возвращаем false. Иначе - возвращаем true.
        for (int i = 0; i < len; i++) {
            if (field[y + i * vy][x + i * vx] != c) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверяет, переполнено ли игровое поле
     * @return Истина, если в игровом поле нет свободных ячеек, иначе - ложь
     */
    public boolean isFullMap() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Проверяет ячейку на валидность
     * @param x Координата X
     * @param y Координата Y
     * @return Истина, если ячейка находится в рамках игрового поля, иначе - ложь
     */
    public boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Проверяет ячейку на пустоту
     * @param x Координата X
     * @param y Координата Y
     * @return Истина, если ячейка пустая, иначе - ложь
     */
    public boolean isEmptyCell(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }
}
