package lesson07.online;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Окно настроек */
public class Settings extends JFrame {

    /** Константы, задающие ширину и высоту окна */
    public static final int WIN_WIDTH = 350;
    public static final int WIN_HEIGHT = 270;

    /** Константы, задающие максимальные и минимальные значения для бегунков */
    private static int MIN_WIN_LENGTH = 3;
    private static int MIN_FIELD_LENGTH = 3;
    private static int MAX_FIELD_LENGTH = 10;

    /** Конатснты с текстом для окна настроек */
    private static String FIELD_SIZE_TEXT_PREFIX = "Размер поля: ";
    private static String WIN_LENGTH_TEXT_PREFIX = "Выигрышная длина: ";

    /** Ссылка на родительское окно MainWindow */
    private MainWindow mainWindow;

    /** Радиокнопки для выбора режима игры */
    private JRadioButton humanVsAI;
    private JRadioButton humanVsHuman;

    /** Бегунки для указания длины/ширины поля, а также длины выигрышной позиции */
    private JSlider sliderWinLen;
    private JSlider sliderFieldSize;

    /**
     * Инициализирует экземпляр класса
     * @param mainWindow Ссылка на родительское окно MainWindow
     */
    Settings(MainWindow mainWindow){
        // Инициализируем поле mainWindow аргументом из компьютера
        this.mainWindow = mainWindow;
        setSize(WIN_WIDTH, WIN_HEIGHT);

        // Делаем так, чтобы данное окно открылось по центру родительского
        Rectangle gameWindowBounds = mainWindow.getBounds();
        int posX = (int) gameWindowBounds.getCenterX() - WIN_WIDTH / 2;
        int posY = (int) gameWindowBounds.getCenterY() - WIN_HEIGHT / 2;
        setLocation(posX, posY);

        // Устанавливаем другие свойства окна
        setResizable(false);
        setTitle("Настройки новой игры");
        setLayout(new GridLayout(10, 1));

        // Добавляем на форму элементы для выбора режима и задания размеров поля с помощью отдельных методов
        addGameModeSettings();
        addFieldSizeControl();

        // Создаём кнопку "Начать новую игру", устанавливаем ей обработчик нажатия и добавляем на форму
        JButton btnStartGame = new JButton("Начать новую игру");
        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStartClick();
            }
        });
        add(btnStartGame);
    }

    /**
     * Обработчик нажатия кнопки "Начать новую игру"
     */
    private void btnStartClick() {
        // Создаём переменную gameMode и записываем в неё тот или иной режим игры в зависимости от выбранной радиокнопки
        int gameMode;
        if (humanVsAI.isSelected()){
            gameMode = GameMap.GAME_MODE_HUMAN_VS_AI;
        } else if (humanVsHuman.isSelected()){
            gameMode = GameMap.GAME_MODE_HUMAN_VS_HUMAN;
        } else {
            throw new RuntimeException("Неизвестный режим игры");
        }

        // Получаем текущие значения из бегунков и помещаем их в переменные fieldSize и winLength
        int fieldSize = sliderFieldSize.getValue();
        int winLength = sliderWinLen.getValue();

        // Обращаемся к главному окну и вызываем там метод startNewGame() для начала новой игры, после чего скрываем текущее окно
        mainWindow.startNewGame(gameMode, fieldSize, fieldSize, winLength);
        setVisible(false);
    }

    /**
     * Добавляет на форму элементы для настройки режима игры
     */
    private void addGameModeSettings(){
        // Добавляем надпись, радиокнопки, объединяем их в радиогруппу и добавляем
        add(new JLabel("Выберите режим игры"));
        humanVsAI = new JRadioButton("Human VS AI", true);
        humanVsHuman = new JRadioButton("Human VS Human");

        ButtonGroup gameMode = new ButtonGroup();
        gameMode.add(humanVsAI);;
        gameMode.add(humanVsHuman);;

        add(humanVsAI);
        add(humanVsHuman);
    }

    /**
     * Добавляет на форму элементы для настройки размеров поля и длины выигрышной позиции
     */
    private void addFieldSizeControl(){
        // Создаём надписи для отображения текущих значений параметров
        JLabel lbFieldSize = new JLabel(FIELD_SIZE_TEXT_PREFIX + MIN_FIELD_LENGTH);
        JLabel lbWinLength = new JLabel(WIN_LENGTH_TEXT_PREFIX + MIN_WIN_LENGTH);

        // Создаём слайдеры и устанавливаем им обработчики изменения значения.
        sliderFieldSize = new JSlider(MIN_FIELD_LENGTH, MAX_FIELD_LENGTH, MIN_FIELD_LENGTH);
        sliderFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Получаем текущее значение первого слайдера
                int currentValue = sliderFieldSize.getValue();

                // Меняем текст надписи
                lbFieldSize.setText(FIELD_SIZE_TEXT_PREFIX + currentValue);

                // Текущее значение длины поля устанавливаем в качестве максимального значения второго слайдера
                sliderWinLen.setMaximum(currentValue);
            }
        });

        sliderWinLen = new JSlider(MIN_WIN_LENGTH, MAX_FIELD_LENGTH, MIN_FIELD_LENGTH);
        sliderWinLen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lbWinLength.setText(WIN_LENGTH_TEXT_PREFIX + sliderWinLen.getValue());
            }
        });

        // Добавляем надписи и слайдеры для двух параметров
        add(new JLabel("Выберите размер поля"));
        add(lbFieldSize);
        add(sliderFieldSize);

        add(new JLabel("Выберите выигрышную позицию"));
        add(lbWinLength);
        add(sliderWinLen);
    }
}
