package lesson06.online;

import java.io.*;

public class MainClass {
    public static void main(String[] args) {
        /*try {
            FileOutputStream fos = new FileOutputStream("test.txt");

            byte[] str = "Hello world!".getBytes();
            fos.write(str);

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            FileInputStream fis = new FileInputStream("test.txt");

            int outbox;
            while ((outbox = fis.read()) != -1){
                System.out.print((char) outbox);
            }

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
