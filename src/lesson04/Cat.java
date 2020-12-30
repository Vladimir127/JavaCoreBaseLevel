package lesson04;

/** Кошка */
public class Cat extends Animal {
    /**
     * Инициализирует экземпляр класса
     * @param name Имя животного
     * @param runLimit  Ограничение по бегу
     * @param swimLimit Ограничение по плаванию
     * @param jumpLimit Ограничение по прыжку
     */
    public Cat(String name, float runLimit, float swimLimit, float jumpLimit) {
        super("Кошка", name, runLimit, swimLimit, jumpLimit);
    }
}
