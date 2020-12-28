package lesson04;

/** Птица */
public class Bird extends Animal {
    /**
     * Инициализирует экземпляр класса
     * @param name Имя животного
     * @param runLimit  Ограничение по бегу
     * @param swimLimit Ограничение по плаванию
     * @param jumpLimit Ограничение по прыжку
     */
    public Bird(String name, int runLimit, int swimLimit, int jumpLimit) {
        super(name, runLimit, swimLimit, jumpLimit);
    }
}
