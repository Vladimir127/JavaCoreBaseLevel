package lesson04;

/** Животное */
public abstract class Animal {
    /**
     * Наименование животного
     */
    private String name;

    /**
     * Ограничение по бегу
     */
    private int runLimit;

    /**
     * Ограничение по плаванию
     */
    private int swimLimit;

    /**
     * Ограничение по прыжку
     */
    private int jumpLimit;

    /**
     * Инициализирует экземпляр класса
     * @param name Имя животного
     * @param runLimit  Ограничение по бегу
     * @param swimLimit Ограничение по плаванию
     * @param jumpLimit Ограничение по прыжку
     */
    public Animal(String name, int runLimit, int swimLimit, int jumpLimit) {
        this.name = name;
        this.runLimit = runLimit;
        this.swimLimit = swimLimit;
        this.jumpLimit = jumpLimit;
    }

    /**
     * Бежать
     * @param distance Расстояние
     * @return Истина в случае успеха, иначе ложь
     */
    public boolean run(int distance) {
        return distance <= runLimit;
    }

    /**
     * Плыть
     * @param distance Расстояние
     * @return Истина в случае успеха, иначе ложь
     */
    public boolean swim(int distance) {
        return distance <= swimLimit;
    }


    /**
     * Перепрыгивать препятствие
     * @param height Высота препятствия
     * @return Истина в случае успеха, иначе ложь
     */
    public boolean jump(int height){
        return height <= jumpLimit;
    }
}
