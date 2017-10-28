import java.io.BufferedReader;  import java.io.FileReader;  import java.io.IOException;
import java.util.ArrayList;     import java.util.List;

public class Lexer {

    static List tokens = new ArrayList<>();

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

        // To find strings
        List keyWords = new ArrayList<>();
        keyWords.add("out"); keyWords.add("if"); keyWords.add("def"); keyWords.add("for");
        String builder = "";
        boolean stringState = false;
        String string = "";

        for (int i = 0; i < opened_file.length(); i++) {
            // Our reader which can identify the character at any time.
            char chr = opened_file.charAt(i);
            if (chr == '"') {// If stringState is true...
                if (stringState) {
                    // Deleting string character at beginning of token index
                    String string1 = new StringBuilder(string).deleteCharAt(0).toString();
                    ArrayList<String> string_token = new ArrayList<>();
                    string_token.add("String: " + string1);
                    tokens.add(string_token);
                    string = "";
                    string_token = null;
                    stringState = false;
                } else {
                    stringState = true;
                }
            }

            // Build up our string
            if (stringState) {
                string += chr;
            } else {
                patternMatcher("Parenthesis(Open)", "(", chr); patternMatcher("Parenthesis(Close)", ")", chr);
                patternMatcher("ScopeDef(Open)", "{", chr);    patternMatcher("ScopeDef(Close)", "}", chr);
                patternMatcher("Operator", "+-/*^%", chr);     patternMatcher("Separator", ",", chr);
                patternMatcher("Statement End", ";", chr);     patternMatcher("Separator", ",", chr);
                patternMatcher("Comparison", "<>", chr);       patternMatcher("Boolean Logic", "|&!", chr);
            }

            // tokens.add(tempArrayList);
        }

        // To find keywords

        return tokens;

    }

    public static boolean patternMatcher(String valueName, String pattern, char item) {
        ArrayList<String> tempArrayList = new ArrayList<>();
        if (pattern.contains(Character.toString(item))) {
            tempArrayList.add(valueName + ": " + Character.toString(item));
            tokens.add(tempArrayList);
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[]args) throws Exception{
        System.out.println(tokeniser(openFile("test.lang")));
    }

}