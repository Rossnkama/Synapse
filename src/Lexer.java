import java.io.BufferedReader;  import java.io.FileReader;  import java.io.IOException;
import java.util.ArrayList;     import java.util.List;

public class Lexer {

    private static String openFile(String path_to_file) throws Exception{

        // Initialising the string that returns out output value
        String opened_file = "";

        // Initialising both the Buffered and File reader objects
        try(BufferedReader br = new BufferedReader(new FileReader(path_to_file))) {

            // Our constructor for out lines being read and buffered
            String line;

            // While the lines aren't nothing, add them to opened file
            while ((line = br.readLine()) != null) opened_file += line;

        // Exception handling
        } catch (IOException e) {

            System.out.println("File not Found");
            System.out.println(e);

        } return opened_file;

    }

    // Turning the file we read into tokens
    private static List tokeniser(String opened_file) {

        List keyWords = new ArrayList<>();
        keyWords.add("out"); keyWords.add("if"); keyWords.add("def"); keyWords.add("for");
        String builder = "";
        List tokens = new ArrayList<>();
        boolean stringState = false;
        String string = "";

        for (int i = 0; i < opened_file.length(); i++) {

            builder += opened_file.charAt(i);
            char chr = opened_file.charAt(i);

            if (chr == '"') {
                // If stringState is true...
                if (stringState) {
                    stringState = false;
                } else {
                    stringState = true;
                }
            } if (stringState) string += chr;
        }

        System.out.println(string);
        return tokens;

    }

    public static void main(String[]args) throws Exception{
        System.out.println( tokeniser(openFile("test.lang")) );
    }

}