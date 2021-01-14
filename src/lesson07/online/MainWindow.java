package lesson07.online;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Главное окно программы, на котором будет отображаться игровое поле, а также кнопки */
public class MainWindow extends JFrame {

    /** Константы, задающие ширину и высоту окна, а также позицию его левого верхнего угла по осям X и Y */
    public static final int WIN_WIDTH = 500;
    public static final int WIN_HEIGHT = 555;
    public static final int WIN_POSX = 650;
    public static final int WIN_POSY = 250;

    /** Окно настроек */
    Settings settingsWindow;

    /** Панель, на которой располагается игровое поле */
    GameMap gameMap;

    /** Конструктор класса */
    public MainWindow()  {
        // Задаём дефолтную операцию при закрытии окна - выход из программы. По умолчанию - ничего не делать.
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Задаём размеры окна
        setSize(WIN_WIDTH, WIN_HEIGHT);

        // Задаём стартовую позицию окна
        setLocation(WIN_POSX, WIN_POSY);

        // Устанавливаем заголовок
        setTitle("Игра \"Крестики-нолики\"");

        // Делаем окно недоступным для изменения размера
        setResizable(false);

        // Инициализируем окно настроек Settings и панель с игровым полем GameMap
        settingsWindow = new Settings(this);
        gameMap = new GameMap();

        // Инициализируем кнопки и устанавливаем им обработчики нажатия
        JButton btnStartGame = new JButton("Новая игра");
        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsWindow.setVisible(true);
            }
        });

        JButton btnExit = new JButton("Выход из игры");
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Создаём панель с компоновщиком GridLayout
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridLayout(1, 2));

        // На эту панель добавляем кнопки
        panelBottom.add(btnStartGame);
        panelBottom.add(btnExit);

        // Саму эту панель добавляем в стандартный компоновщик окна BorderLayout, на южную сторону
        add(panelBottom, BorderLayout.SOUTH);

        // Добавляем в окно панель с игровым полем (по умолчанию она добавляется в центр)
        add(gameMap);

        // Показываем окно пользователю
        setVisible(true);
    }

    /**
     * Начинает новую игру
     * @param mode Режим (человек против компьютера или человек против человека)
     * @param fieldSizeX Ширина игрового поля
     * @param fieldSizeY Высота игрового поля
     * @param winLength Длина выигрышной позиции
     */
    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLength){
        // Вызываем аналогичный метод у класса GameMap
        gameMap.startNewGame(mode, fieldSizeX, fieldSizeY, winLength);
    }
}
