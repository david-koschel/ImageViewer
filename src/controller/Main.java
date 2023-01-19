package controller;

import persistence.FileImageLoader;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        String imageFolderPath = args.length > 0 ? args[0] : "testImages";
        new MainFrame(new FileImageLoader(new File(imageFolderPath)));
    }
}
