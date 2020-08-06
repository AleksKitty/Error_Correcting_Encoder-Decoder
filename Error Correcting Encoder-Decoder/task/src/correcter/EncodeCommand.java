package correcter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EncodeCommand {

    // send.txt file
    private ArrayList<StringBuilder> arrayListHex;
    private ArrayList<StringBuilder> arrayListBin;
    private String textSend;


    public EncodeCommand() {
        readFromFile();
    }

    public ArrayList<StringBuilder> getArrayListHex() {
        return arrayListHex;
    }

    public ArrayList<StringBuilder> getArrayListBin() { return arrayListBin; }

    public String getTextSend() {
        return textSend;
    }


    private void readFromFile() {
        File fileSend = new File("send.txt");

        try (Scanner scanner = new Scanner(fileSend)){

            char[] symbols = scanner.nextLine().toCharArray();
            textSend = String.valueOf(symbols);

            arrayListHex = new ArrayList<>();
            arrayListBin = new ArrayList<>();


            for (char letter : symbols) {

                StringBuilder hex = new StringBuilder(String.format("%2H", letter).toUpperCase());
                if (hex.length() % 2 != 0) {
                    hex.insert(0, '0');
                }

                arrayListHex.add(hex);

                arrayListBin.add(new StringBuilder(String.format("%8s", Integer.toBinaryString(letter & 0xFF)).replace(' ', '0')));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
