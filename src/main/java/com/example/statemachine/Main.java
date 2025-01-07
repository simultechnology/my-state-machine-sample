package com.example.statemachine;

public class Main {
    public static void main(String[] args) {
        // 5個のガムボールを持つ販売機を作成
        GumballMachine gumballMachine = new GumballMachine(5);
        
        System.out.println("=== 初期状態 ===");
        System.out.println("ガムボールの数: " + gumballMachine.getGumballCount());
        System.out.println("現在の状態: " + gumballMachine.getState());
        
        System.out.println("\n=== 25セント投入 ===");
        gumballMachine.insertQuarter();

        System.out.println("\n=== 25セントを取り出す ===");
        gumballMachine.ejectQuarter();

        System.out.println("\n=== もう一度25セント投入 ===");
        gumballMachine.insertQuarter();

        System.out.println("\n=== ハンドルを回す ===");
        gumballMachine.turnCrank();
        
        System.out.println("\n=== 現在の状態 ===");
        System.out.println("ガムボールの数: " + gumballMachine.getGumballCount());
        System.out.println("現在の状態: " + gumballMachine.getState());
        
        // さらにいくつかの操作を試す
        System.out.println("\n=== 25セントを投入せずにハンドルを回す ===");
        gumballMachine.turnCrank();
        
        System.out.println("\n=== 25セント投入 ===");
        gumballMachine.insertQuarter();
        
        System.out.println("\n=== もう一度25セント投入 ===");
        gumballMachine.insertQuarter();
    }
}