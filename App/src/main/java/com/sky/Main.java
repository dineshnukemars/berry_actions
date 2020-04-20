package com.sky;

import com.sky.tm1638.TMBoard;

public class Main {

    public static void main(String[] args) {
        System.out.println("-------app started-----");
        TMBoard board = new TMBoard();
        board.sendSomeData();
        board.clear();
        System.out.println("-------app finished-----");
    }
}
