package lesson04;

import java.util.Random;

/** Животное */
public abstract class Animal {
    static final int SWIM_FAIL = 0;
    static final int SWIM_OK = 1;
    static final int SWIM_NONE = -1;

    /** Наименование животного */
    private String type;

    /** Наименование животного */
    private String name;

    /** Ограничение по бегу */
    private float runLimit;

    /** Ограничение по плаванию */
    private float swimLimit;

    /** Ограничение по прыжку */
    private float jumpLimit;

    private final Random random = new Random();

    /**
     * Инициализирует экземпляр класса
     * @param type Вид животного
     * @param name Имя животного
     * @param runLimit  Ограничение по бегу
     * @param swimLimit Ограничение по плаванию
     * @param jumpLimit Ограничение по прыжку
     */
    public Animal(String type, String name, float runLimit, float swimLimit, float jumpLimit) {
        float jumpDiff = random.nextFloat() * jumpLimit - (jumpLimit / 2);
        float runDiff = random.nextFloat() * runLimit - (runLimit / 2);
        float swimDiff = random.nextFloat() * swimLimit - (swimLimit / 2);

        this.type = type;
        this.name = name;
        this.runLimit = runLimit + runDiff;
        this.swimLimit = swimLimit + swimDiff;
        this.jumpLimit = jumpLimit + jumpDiff;
    }

    /**
     * Бежать
     * @param distance Расстояние
     * @return Истина в случае успеха, иначе ложь
     */
    public boolean run(float distance) {
        return distance <= runLimit;
    }

    /**
     * Плыть
     * @param distance Расстояние
     * @return Истина в случае успеха, иначе ложь
     */
    public int swim(float distance) {
        return distance <= swimLimit ? SWIM_OK : SWIM_FAIL;
    }

    /**
     * Перепрыгивать препятствие
     * @param height Высота препятствия
     * @return Истина в случае успеха, иначе ложь
     */
    public boolean jump(float height){
        return height <= jumpLimit;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public float getRunLimit() {
        return runLimit;
    }

    public float getSwimLimit() {
        return swimLimit;
    }

    public float getJumpLimit() {
        return jumpLimit;
    }
}
