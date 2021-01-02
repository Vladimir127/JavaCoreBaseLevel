package lesson06.homework;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        String fileName1 = "src/lesson06/homework/files/file1.txt";
        String fileName2 = "src/lesson06/homework/files/file2.txt";
        String fileName3 = "src/lesson06/homework/files/file3.txt";

        System.out.println("Чтение файла " + fileName1 + ". Содержимое файла: ");
        String str1 = readFromFile(fileName1);
        System.out.println(str1);
        System.out.println();

        System.out.println("Чтение файла " + fileName2 + ". Содержимое файла: ");
        String str2 = readFromFile(fileName2);
        System.out.println(str2);
        System.out.println();

        System.out.println("Склеиваем полученные строки и записываем их в файл " + fileName3 + ". Содержимое файла: ");
        String str3 = str1 + str2;
        writeToFile(fileName3, str3);
        System.out.println(readFromFile(fileName3));
        System.out.println();

        String searchWord = "язык";
        System.out.println("Поиск слова \"" + searchWord + "\" в файле " + fileName3);
        boolean found = search(searchWord, fileName3);
        System.out.println("Слово " + (found ? "найдено" : "не найдено"));
        System.out.println();

        searchWord = "Kotlin";
        System.out.println("Поиск слова \"" + searchWord + "\" в файле " + fileName3);
        found = search(searchWord, fileName3);
        System.out.println("Слово " + (found ? "найдено" : "не найдено"));
        System.out.println();

        searchWord = "кофе";
        String folderName = "src/lesson06/homework/files";
        System.out.println("Поиск слова \"" + searchWord + "\" в папке " + folderName);
        found = searchInFolder(searchWord, folderName);
        System.out.println("Слово " + (found ? "найдено" : "не найдено"));
        System.out.println();

        searchWord = "Яблоко";
        System.out.println("Поиск слова \"" + searchWord + "\" в папке " + folderName);
        found = searchInFolder(searchWord, folderName);
        System.out.println("Слово " + (found ? "найдено" : "не найдено"));
        System.out.println();
    }

    /**
     * Считывает текстовую строку из файла
     * @param fileName Имя файла
     * @return Текст, считанный из файла
     */
    private static String readFromFile(String fileName){
        // Инициализируем объект StringBuilder для формирования строки
        StringBuilder stringBuilder = new StringBuilder();

        try {
            // Создаём объект FileInputStream для чтения из файла
            FileInputStream fis = new FileInputStream(fileName);

            // Объект InputStreamReader добавлен для чтения кириллицы
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);

            // Текущий байт
            int outbox;

            // Пока текущий байт, считываемый из файла, не равен -1,
            // преобразуем его в символ и добавляем к строке
            while ((outbox = reader.read()) != -1){
                stringBuilder.append((char) outbox);
            }

            // Закрываем поток
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Преобразуем StringBuilder в строку и возвращаем её
        return stringBuilder.toString();
    }

    /**
     * Записывает текстовую строку в файл
     * @param fileName Имя файла
     * @param text Текст, который необходимо записать в файл
     */
    private static void writeToFile(String fileName, String text){
        try {
            // Создаём объект FileOutputStream для записи в файл
            FileOutputStream fos = new FileOutputStream(fileName);

            // Преобразуем текст в массив байтов и записываем в файл
            byte[] str = text.getBytes();
            fos.write(str);

            // Закрываем поток
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверяет, присутствует ли указанное пользователем слово в файле
     * @param searchWord Слово для поиска
     * @param fileName Имя файла
     * @return Истина, если данное слово присутствует в файле, иначе - ложь
     */
    private static boolean search(String searchWord, String fileName){
        // Считываем текст из файла и помещаем в строку
        String text = readFromFile(fileName);

        // Разбиваем строку на массив слов, используя в качестве разделителя пробел
        String[] words = text.split(" ");

        // Перебираем массив слов
        for (String word : words){
            // Сравниваем текущее слово со словом для поиска без
            // учёта регистра, и если слова совпадают, возвращаем истину
            if (word.equalsIgnoreCase(searchWord)){
                return true;
            }
        }

        // Если совпадений не найдено, возвращаем ложь
        return false;
    }

    /**
     * Проверяет, есть ли указанное слово в папке
     * @param searchWord Слово для поиска
     * @param folderName Имя папки
     * @return Истина, если данное слово присутствует в папке, иначе - ложь
     */
    private static boolean searchInFolder(String searchWord, String folderName){
        // Создаём объект File, соответствующий указанной папке
        File folder = new File(folderName);

        // Получаем массив файлов из этой папки, используя фильтр по имени
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                // Условие соответствия - имя должно заканчиваться на ".txt"
                return name.endsWith(".txt");
            }
        });

        // Если массив файлов не равен null, перебираем их
        if (files != null) {
            for (File file : files) {
                // Ищем слово в каждом файле, используя определённый выше
                // метод search. Если результат положительный, возвращаем истину
                // метод search. Если результат положительный, возвращаем истину
                if (search(searchWord, file.getAbsolutePath())) {
                    return true;
                }
            }
        }

        // Если совпадений не найдено, возвращаем ложь
        return false;
    }
}
