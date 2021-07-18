package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessageToConsole(String message) {
        System.out.println(message);
    }

    public static String readStringFromConsole() throws IOException {
        return reader.readLine();
    }

    public static int readIntFromConsole() throws IOException {
        return Integer.parseInt(readStringFromConsole().trim());
    }
}
