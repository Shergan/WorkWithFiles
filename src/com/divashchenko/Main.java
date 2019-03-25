package com.divashchenko;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Main {

    private static int countJavaFile = 0;
    private static int countJavaAnnotations = 0;

    public static void main(String[] args) {
        try {
            Files.copy(Paths.get("/usr/java/jdk1.8.0_202-amd64/src.zip"), Paths.get("/home/shergan/IdeaProjects/WorkWithFiles/src.zip"), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String userDir = "/home/shergan/IdeaProjects/WorkWithFiles/srcJava";
        File homeDir = new File(userDir);

        if (homeDir.exists() && homeDir.isDirectory()) {
            File[] files = homeDir.listFiles();
            showFiles(files);
        }

        System.out.println();
        System.out.println(".java = " + countJavaFile);
        System.out.println("@FunctionalInterface = " + countJavaAnnotations);
    }

    private static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                showFiles(file.listFiles());
            } else {
                if (file.getName().contains(".java")) {
                    countJavaFile++;
                    try {
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNext()) {
                            if (scanner.nextLine().contains("@FunctionalInterface")) {
                                countJavaAnnotations++;
                                System.out.println(file);
                                break;
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
