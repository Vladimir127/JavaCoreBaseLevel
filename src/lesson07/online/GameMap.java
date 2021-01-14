package lesson07.online;

import javax.swing.*;
import java.awt.*;

/** Паенль, на котором будет отображаться игровое поле и проходить игра */
public class GameMap extends JPanel {
    // Константы для обозначения режимов игры
    public static final int GAME_MODE_HUMAN_VS_AI = 0;
    public static final int GAME_MODE_HUMAN_VS_HUMAN = 1;

    // Константы для задания цвета и толщины линии ячейки
    public static final int BORDER_WIDTH = 1;
    public static final Color BORDER_COLOR = Color.BLACK;

    /**
     * Инициализирует экземпляр класса
     */
    GameMap(){
        // По умолчанию рисуем сетку размером 3 на 3
        displayGrid(3, 3);
    }

    /**
     * Начинает новую игру
     * @param mode Режим (человек против компьютера или человек против человека)
     * @param fieldSizeX Ширина игрового поля
     * @param fieldSizeY Высота игрового поля
     * @param winLength Длина выигрышной позиции
     */
    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLength){
        System.out.println("mode = " + mode +
                " fieldSizeX = " + fieldSizeX +
                " fieldSizeY = " + fieldSizeY +
                " winLength = " + winLength);

        displayGrid(fieldSizeX, fieldSizeY);
    }

    /**
     * Формирует на данной панели таблицу с нужным числом строк и столбцов, разграниченных линиями
     * @param fieldSizeX Число столбцов (размер по оси X)
     * @param fieldSizeY Число строк (размер по оси Y)
     */
    void displayGrid(int fieldSizeX, int fieldSizeY){
        // Сначала удаляем с данной панели всё содержимое
        this.removeAll();

        // Создаём табличный контейнер с нужным количеством строк и столбцов
        // и устанавливаем этот контейнер в качестве макета панели
        GridLayout gridLayout = new GridLayout(fieldSizeX, fieldSizeY);
        setLayout(gridLayout);

        // Чтобы отобразить линии сетки, добавим в каждую ячейку таблицы панель
        // и установим ей границы нужного цвета и толщины. Однако если задать для
        // каждой панели все четыре границы, будет некрасиво, поэтому каждой панели
        // устанавливаем только некоторые границы, в зависимости от её положения.
        // Обходим таблицу по строкам и столбцам.
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                JPanel panel = new JPanel();

                if (i == 0){
                    if (j == 0) {
                        // Верхний левый угол - добавляем все границы ячейки
                        panel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
                    } else {
                        // Верхний край - добавляем все линии, кроме левой
                        panel.setBorder(BorderFactory.createMatteBorder(
                                BORDER_WIDTH,
                                0,
                                BORDER_WIDTH,
                                BORDER_WIDTH,
                                BORDER_COLOR));
                    }
                } else {
                    if (j == 0) {
                        // Левый край - добавляем все линии, кроме верхней
                        panel.setBorder(BorderFactory.createMatteBorder(
                                0,
                                BORDER_WIDTH,
                                BORDER_WIDTH,
                                BORDER_WIDTH,
                                BORDER_COLOR));
                    } else {
                        // Ни левый, ни верхний край - добавляем панелям только правую и нижнюю границу
                        panel.setBorder(BorderFactory.createMatteBorder(
                                0,
                                0,
                                BORDER_WIDTH,
                                BORDER_WIDTH,
                                BORDER_COLOR));
                    }
                }

                // Добавляем панель в ячейку таблицы
                this.add(panel);
            }
        }

        // Обновляем панель, чтобы изменения отобразились на экране
        this.revalidate();
        this.repaint();
    }
}
