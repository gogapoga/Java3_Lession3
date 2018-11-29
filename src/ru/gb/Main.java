package ru.gb;

import java.io.*;

public class Main {

    public static void main(String[] args) {
	    // Задание №1
        String nameFile = "test.txt";
        сreateRandomFile(nameFile, 50);
        printFile(nameFile);

        // Задание №2
        for(int i = 1; i < 6; i++) сreateRandomFile("test" + i + ".txt", 100);
        addFiles("test\\d+\\.txt");

        // Задание №3
        String bigFile = "bigtext.txt";
        сreateRandomFile(bigFile, 20000000);
        readPages(bigFile);
    }
    public static void сreateRandomFile(String nameFile, int size) {
        String symbols = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder str = new StringBuilder(size);
        for(int i = 0; i < size; i++) str.append(symbols.charAt((int)(Math.random() * symbols.length())));
        byte[] buf = str.toString().getBytes();
        try {
            FileOutputStream fout = new FileOutputStream(nameFile, false);
            fout.write(buf);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printFile(String nameFile) {
        try {
            FileInputStream fin = new FileInputStream(nameFile);
            byte[] buf = new byte[fin.available()];
            fin.read(buf);
            fin.close();
            System.out.println(new String(buf) +"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void addFiles(String mask) {
        File[] listFiles = new File(".").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches(mask);
            }
        });
        if(listFiles.length > 0) {
            for(int i = 0; i < listFiles.length; i++) {
                System.out.println("Файл " + (i + 1) + ":\n");
                printFile(listFiles[i].getName());
            }
            try {
                FileOutputStream res = new FileOutputStream(listFiles[0].getName(), true);
                for (int i = 1; i < listFiles.length; i++) {
                    FileInputStream fin = new FileInputStream(listFiles[i]);
                    byte[] temp = new byte[fin.available()];
                    fin.read(temp);
                    fin.close();
                    listFiles[i].delete();
                    res.write(temp);
                }
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Файл рузультирующий:\n");
            printFile(listFiles[0].getName());
        } else System.out.println("Файлы с такой маской отсутствуют!");
    }

    public static void readPages(String nameFile) { //подразумевалось скорее всего использование класса BufferedInputStream, но мне кажется так проще
        int pageSize = 1800;
        try {
            FileInputStream fin = new FileInputStream(nameFile);
            int pages = fin.available() / pageSize;
            int lastPage = fin.available() % pageSize;
            byte[] buf = new byte[pageSize];
            for (int i = 0; i < pages; i++) {
                fin.read(buf,0,pageSize);
                System.out.println(new String(buf) +"\n");
            }
            buf = new byte[lastPage];
            fin.read(buf,0, lastPage);
            System.out.println(new String(buf) +"\n");
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
