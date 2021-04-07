package com.java.topic;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilesTest {



    
    public static void main(String[] args) {
        boolean exists = Files.exists(Paths.get("/Users/lina"));
        System.out.println(exists);

        try {
            Files.createFile(Paths.get("/Users/lina/files.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.createDirectory(Paths.get("/Users/lina/files_dir"));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            Files.delete(Paths.get("/Users/lina/files.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Files.delete(Paths.get("/Users/lina/files_dir"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            Files.copy(Paths.get("/Users/lina/files.txt"), Paths.get("/Users/lina/copy.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.move(Paths.get("/Users/lina/files.txt"), Paths.get("/Users/lina/move.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            long size = Files.size(Paths.get("/Users/lina/files_dir"));
            System.out.println(size);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.write(Paths.get("/Users/lina/files.txt"), "hello".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
