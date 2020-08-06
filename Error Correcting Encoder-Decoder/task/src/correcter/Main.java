package correcter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        boolean isCommand = false;

        System.out.print("Write a mode: ");
        String command = scanner.next();
        while (!isCommand) {
            if  (command.contains("encode")) {
                encodeCommand();
                isCommand = true;

            } else if  (command.contains("send")) {
                sendCommand();
                isCommand = true;

            } else if (command.contains("decode")) {
                decodeCommand();
                isCommand = true;

            } else {
                System.out.print("Write a mode: ");
                command = scanner.next();
            }
        }
    }

    private static void encodeCommand() {
        EncodeCommand encodeCommand = new EncodeCommand(); // who sends
        output("send.txt", encodeCommand.getTextSend(), getArrayListString(encodeCommand.getArrayListHex()), getArrayListString(encodeCommand.getArrayListBin()));

        Encoder encoder = new Encoder(encodeCommand.getArrayListBin());
        output("encoded.txt", null, getArrayListString(encoder.getArrayListHex()), getArrayListString(encoder.getArrayListBin()));
    }

    private static void sendCommand() {
        new SendCommand(); // who makes mistakes
    }

    private static void decodeCommand() throws IOException {
        DecodeCommand decodeCommand = new DecodeCommand();
        output("decoded.txt", decodeCommand.getTextView(), getArrayListString(decodeCommand.getArrayListHex()), getArrayListString(decodeCommand.getArrayListBinOutput()));
    }

    private static void output(String file, String text, String hex, String bin) {
        System.out.println("\n" + file + ":");
        if (text != null) {
            System.out.println("text view: " + text);
        }
        System.out.println("hex view: " + hex);
        System.out.println("bin view: " + bin);
    }

    public static StringBuilder BinaryToHex(StringBuilder binary) {
        int decimal = Integer.parseInt(String.valueOf(binary),2);
        StringBuilder hex = new StringBuilder(Integer.toHexString(decimal).toUpperCase());
        if (hex.length() % 2 != 0) {
            hex.insert(0, '0');
        }
        return new StringBuilder(hex);
    }

    public static String getArrayListString(ArrayList<StringBuilder> arrayList) {
        return arrayList.stream().map(Object::toString).collect(Collectors.joining(" "));
    }
}
