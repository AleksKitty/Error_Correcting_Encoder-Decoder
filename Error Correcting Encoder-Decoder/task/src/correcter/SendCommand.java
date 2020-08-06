package correcter;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;


public class SendCommand {

    public SendCommand() {
        doAll();
    }

    protected static byte[] readBytes(String inFileName) throws IOException {
        return Files.readAllBytes(Paths.get(inFileName));
    }

    private static void writeBytes(String outFileName, byte[] content) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(outFileName);
        fileOutputStream.write(content);
    }

    private static byte[] makeError(byte[] array){
        Random random = new Random();

        for(int i = 0; i < array.length; i++){
            array[i] ^= 1 << random.nextInt(8);
        }

        return array;
    }

    private void doAll() {
        String encoded = "encoded.txt";
        String received = "received.txt";

        try {
            writeBytes(received, makeError(readBytes(encoded)));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
