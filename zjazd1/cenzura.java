import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class cenzura {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));

        System.out.println("Podaj ciąg nr 1:\n");
        String text = reader.readLine();
        System.out.println("Podaj ciąg nr 2:\n");
        String censored = reader.readLine();
        char star = '*';

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if ( c == star ) {
                text = text.substring(0, i) + censored.charAt(j) + text.substring(i + 1);
                j++;
            }
        }
        System.out.println(text);
    }
}

