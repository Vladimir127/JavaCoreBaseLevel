package lesson04;

/** Собака */
public class Dog extends Animal {

    /**
     * Инициализирует экземпляр класса
     * @param name Имя животного
     * @param runLimit  Ограничение по бегу
     * @param swimLimit Ограничение по плаванию
     * @param jumpLimit Ограничение по прыжку
     */
    public Dog(String name, float runLimit, float swimLimit, float jumpLimit) {
        super("Собака", name, runLimit, swimLimit, jumpLimit);
    }
}
