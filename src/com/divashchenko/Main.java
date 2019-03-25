package com.divashchenko;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

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
        String srcHome = "/usr/java/jdk1.8.0_202-amd64/src.zip";
        String srcNewPlace = "/home/shergan/IdeaProjects/WorkWithFiles/src.zip";
        String placeToExtractSrc = "/home/shergan/IdeaProjects/WorkWithFiles/srcJava";

        File homeDir = new File(System.getProperty("user.dir"));

        if (homeDir.exists() && homeDir.isDirectory()) {
            File[] files = homeDir.listFiles();
            showFiles(files);
        }
        System.out.println();

        try {
            Files.copy(Paths.get(srcHome), Paths.get(srcNewPlace), StandardCopyOption.REPLACE_EXISTING);
            ZipFile zipFile = new ZipFile(srcNewPlace);
            zipFile.extractAll(placeToExtractSrc);
        } catch (IOException | ZipException e) {
            e.printStackTrace();
        }

        homeDir = new File(placeToExtractSrc);

        if (homeDir.exists() && homeDir.isDirectory()) {
            File[] files = homeDir.listFiles();
            findJavaFiles(files);
        }

        System.out.println();
        System.out.println(".java = " + countJavaFile);
        System.out.println("@FunctionalInterface = " + countJavaAnnotations);
    }

    private static void findJavaFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                findJavaFiles(file.listFiles());
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

    private static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                showFiles(file.listFiles());
            } else {
                System.out.println(file.getName());
            }
        }
    }
}
