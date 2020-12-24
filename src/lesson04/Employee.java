package lesson04;

/** Сотрудник */
public class Employee {
    /**
     * Количество уже существующих сотрудников - статическое поле,
     * необходимое для генерации уникального порядкового номера
     * */
    private static int countEmployees = 0;

    /** Уникальный порядковый номер сотрудника */
    private int id;

    /** ФИО */
    private String fullName;

    /** Должность */
    private String position;

    /** Телефон */
    private String phoneNumber;

    /** Зарплата */
    private int salary;

    /** Возраст */
    private int age;

    /**
     * Инициализирует экземпляр класса
     * @param fullName ФИО
     * @param position Должность
     * @param phoneNumber Телефон
     * @param salary Зарплата
     * @param age Возраст
     */
    public Employee(String fullName, String position, String phoneNumber, int salary, int age) {
        this.fullName = fullName;
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.age = age;

        // Уникальный порядковый номер представляет собой количество уже существующих сотрудников, увеличенное на 1
        this.id = ++countEmployees;
    }

    /**
     * Возвращает ФИО
     * @return ФИО
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Возвращает должность
     * @return Должность
     */
    public String getPosition() {
        return position;
    }

    /**
     * Возвращает телефон
     * @return Телефон
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Возвращает зарплату
     * @return Зарплата
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Возвращает возраст
     * @return Возраст
     */
    public int getAge() {
        return age;
    }

    /**
     * Повышает зарплату сотрудника на указанную сумму
     * @param sum Сумма повышения
     */
    public void raiseSalary(int sum) {
        salary += sum;
    }
}
