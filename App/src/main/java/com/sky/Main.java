package com.sky;

public class Main {

    public static void main(String[] args) {
        System.out.println("-------App started-----");

        new PlayOnDisplay();

        System.out.println("-------Main finished-----");
    }

    public static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}