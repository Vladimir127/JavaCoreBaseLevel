package lesson04;

public class Main {

    static String nameString;
    static String eventName;
    static String eventResult;

    public static void main(String[] args) {
         Cat cat = new Cat("Маша", 200, 0, 2);
         Dog dog = new Dog("Джек", 500, 10, 0.5f);
         Horse horse = new Horse("Ночка", 1500, 100, 3);
         Bird bird = new Bird("Севочка", 5, 0, 0.2f);

         Animal[] array = {cat, dog, horse, bird};
         float toRun = 300;
         float toSwim = 10;
         float toJump = 2.5f;

        for (int i = 0; i < array.length; i++) {
            nameString = array[i].getType() + " " + array[i].getName() + " может ";

            jumpMarathon(array[i], toJump);
            runMarathon(array[i], toRun);
            swimMarathon(array[i], toSwim);
        }
    }

    private static void jumpMarathon(Animal animal, float distance) {
        eventName = String.format("прыгнуть на %.2f м. Пытается прыгнуть на ", animal.getJumpLimit());
        eventResult = (animal.jump(distance)) ? " м и это получилось" : " м. и это не получилось";
        System.out.println(nameString + eventName + distance + eventResult);
    }

    private static void runMarathon(Animal animal, float distance) {
        eventName = String.format("пробежать на %.2f м. Пытается пробежать на ", animal.getRunLimit());
        eventResult = animal.run(distance) ? " м. и это получилось" : " м. и это не получилось";
        System.out.println(nameString + eventName + distance + eventResult);
    }

    private static void swimMarathon(Animal animal, float distance) {
        int swimResult = animal.swim(distance);
        eventName = String.format("проплыть на %.2f м. Попытка проплыть на ", animal.getSwimLimit());
        eventResult = (swimResult == Animal.SWIM_OK) ? " это получилось" : " это не получилось";
        if (swimResult == Animal.SWIM_NONE) {
            eventResult = " это не получилось, т.к. не умеет плавать";
        }
        System.out.println(nameString + eventName + distance + " м и" + eventResult);
    }
}
