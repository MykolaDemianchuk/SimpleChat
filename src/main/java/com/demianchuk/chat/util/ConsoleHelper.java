package com.demianchuk.chat.util;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleHelper {

    private static final InputStream inputStream;
    private static final PrintStream printStream;
    private static final Scanner scanner;

    static {
        inputStream = System.in;
        printStream = System.out;
        scanner = new Scanner(inputStream);
    }

    private ConsoleHelper() {
    }

    public static String read() {
        return scanner.nextLine();
    }

    public static void write(String s) {
        printStream.println(s);
    }
}
