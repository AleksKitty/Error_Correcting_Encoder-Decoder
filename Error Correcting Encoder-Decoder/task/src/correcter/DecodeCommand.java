package correcter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static correcter.Encoder.findParityBit;
import static correcter.Main.BinaryToHex;
import static correcter.SendCommand.readBytes;


public class DecodeCommand {

    private ArrayList<StringBuilder> arrayListHex;
    private ArrayList<StringBuilder> arrayListBin;
    private ArrayList<StringBuilder> arrayListBinOutput;
    private StringBuilder textView;

    public DecodeCommand() throws IOException {
        readAndCorrect();
        removeParityBits();
    }

    public ArrayList<StringBuilder> getArrayListHex() {
        return arrayListHex;
    }

    public ArrayList<StringBuilder> getArrayListBinOutput() {
        return arrayListBinOutput;
    }

    public String getTextView() {
        return String.valueOf(textView);
    }

    private void readAndCorrect() throws IOException {
        String fileReceived = "received.txt";

        byte[] inf = readBytes(fileReceived);

        arrayListBin = new ArrayList<>();
        arrayListHex = new ArrayList<>();

        textView = new StringBuilder();

        for (byte b : inf) {
            arrayListBin.add(new StringBuilder(String.format("%8s", Integer.toBinaryString((char)b & 0xFF)).replace(' ', '0')));

            doUnCoding(arrayListBin.get(arrayListBin.size() - 1));
        }
    }

    private void doUnCoding(StringBuilder builder) {

        int start;
        int parityBit;

        StringBuilder indexesBinary = new StringBuilder();

        // i is powers of two
        for (int i = 0; i <= 2; i++) {
            start = (int) Math.pow(2, i) - 1;

            parityBit = findParityBit(i, start, builder);

            indexesBinary.append(parityBit);
        }


        char correctBit;

        if (!String.valueOf(indexesBinary).equals("000")) {
            int index = Integer.parseInt(String.valueOf(indexesBinary.charAt(2)) + indexesBinary.charAt(1) + indexesBinary.charAt(0) , 2) - 1;

            correctBit = (char) (Character.getNumericValue(builder.charAt(index)) ^ 1 + '0');

            builder.setCharAt(index, correctBit);

        } else {
            // don't use last one
            builder.setCharAt(7, '0');
        }
    }

    private void removeParityBits() {
        arrayListBinOutput = new ArrayList<>();

        StringBuilder tmp = new StringBuilder();
        for (StringBuilder builder : arrayListBin) {

            tmp.append(builder.charAt(2));
            tmp.append(builder.charAt(4));
            tmp.append(builder.charAt(5));
            tmp.append(builder.charAt(6));

            if (tmp.length() == 8) {
                arrayListBinOutput.add(tmp);

                arrayListHex.add(BinaryToHex(arrayListBinOutput.get(arrayListBinOutput.size() - 1)));

                textView.append((char) Integer.parseInt(String.valueOf(arrayListHex.get(arrayListHex.size() - 1)), 16));

                tmp = new StringBuilder();
            }
        }

        String fileName = "decoded.txt";

        try (PrintWriter printWriter = new PrintWriter(new File(fileName))) {
            printWriter.print(textView);
        } catch (IOException e) {
            System.out.printf("An exception occurs in encode(): %s", e.getMessage());
        }
    }
}