package correcter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import static correcter.Main.BinaryToHex;

public class Encoder {
    // encoded.txt file
    private ArrayList<StringBuilder> arrayListHex;
    private ArrayList<StringBuilder> arrayListBin;


    public Encoder(ArrayList<StringBuilder> arrayListBinSend) {
        encode(arrayListBinSend);
    }

    public ArrayList<StringBuilder> getArrayListHex() {
        return arrayListHex;
    }

    public ArrayList<StringBuilder> getArrayListBin() {
        return arrayListBin;
    }

    private void encode(ArrayList<StringBuilder> arrayListBinSend) {
        String fileName = "encoded.txt";

        try (OutputStream outputStream = new FileOutputStream(fileName, false)) {

            arrayListHex = new ArrayList<>();
            arrayListBin = new ArrayList<>();

            StringBuilder allBits = new StringBuilder();

            for (StringBuilder builder : arrayListBinSend) {
                // first byte
                allBits.append('0').append('0');
                allBits.append(builder.charAt(0));
                allBits.append('0');
                allBits.append(builder.substring(1, 4));
                allBits.append('0');

                // second byte
                allBits.append('0').append('0');
                allBits.append(builder.charAt(4));
                allBits.append('0');
                allBits.append(builder.substring(5, 8));
                allBits.append('0');
            }




            for (int i = 0; i < allBits.length(); i += 8) {
                arrayListBin.add(new StringBuilder(allBits.substring(i, i + 8)));
                doCoding(arrayListBin.get(arrayListBin.size() - 1));


                arrayListHex.add(BinaryToHex(arrayListBin.get(arrayListBin.size() - 1)));

                outputStream.write((char) Integer.parseInt(String.valueOf(arrayListHex.get(arrayListHex.size() - 1)), 16));

            }
        } catch (IOException e) {
            System.out.println("Encoder: something went wrong");
        }
    }

    private void doCoding(StringBuilder builder) {

        int start;
        int parityBit;


        // i is powers of two
        for (int i = 0; i <= 2; i++) {

            start = (int) Math.pow(2, i) - 1;

            parityBit = findParityBit(i, start, builder);

            builder.setCharAt(start, String.valueOf(parityBit).charAt(0));
        }
    }

    public static int findParityBit(int i, int start, StringBuilder builder) {
        int parityBit = 0;
        int add = (int) Math.pow(2, i + 1);

        for (int j = start; j < builder.length(); j += add) {
            for (int k = j; k < j + Math.pow(2, i); k++) {
                if (k < builder.length()) {
                    parityBit ^= Integer.parseInt(String.valueOf(builder.charAt(k)));
                }
            }
        }

        return parityBit;
    }
}
