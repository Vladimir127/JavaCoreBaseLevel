package lesson04;

public class Main {
    public static void main(String[] args) {
        System.out.println("Создаём первого сотрудника.");
        Employee employee1 = new Employee("Королёв Алексей Иванович", "директор", "8-941-325-69-78", 60000, 36);
        System.out.println("ФИО: " + employee1.getFullName() + ", должность: " + employee1.getPosition());
        System.out.println();

        System.out.println("Создаём массив сотрудников.");
        Employee[] employees = {
                employee1,
                new Employee("Петрова Зинаида Фёдоровна", "бухгалтер", "8-962-782-14-56", 35000, 64),
                new Employee("Степанов Владимир Владимирович", "программист", "8-961-168-02-27", 50000, 29),
                new Employee("Колмогоров Егор Андреевич", "тестировщик", "8-935-781-65-48", 25000, 22),
                new Employee("Алексеев Андрей Александрович", "Главный инженер", "8-967-832-47-14", 55000, 45),
        };
        System.out.println("Сотрудники старше 40 лет: ");

        for (Employee employee : employees){
            if (employee.getAge() >= 40){
                System.out.println("ФИО: " + employee.getFullName() + ", возраст: " + employee.getAge());
            }
        }

        System.out.println("Повысим зарплату сотрудникам старше 35 лет.");
        raiseSalary(employees);
        System.out.println("Повысили.");
        System.out.println();
    }

    /**
     * Повышает зарплату всем сотрудникам старше 35 лет на 10000
     * @param employees Массив сотрудников
     */
    private static void raiseSalary(Employee[] employees) {
        for (Employee employee : employees){
            if (employee.getAge() >= 40){
                employee.raiseSalary(10000);
            }
        }
    }
}
