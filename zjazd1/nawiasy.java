import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// Nie dokończone
public class nawiasy {
    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
        
        boolean obcy = false;

        while (obcy = true) {
            
        System.out.println("Podaj ciąg nawiasów:\n");
        String text = reader.readLine();
        
        

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '(' ||
                text.charAt(i) == ')' ||
                text.charAt(i) == '[' ||
                text.charAt(i) == ']' ||
                text.charAt(i) == '{' ||
                text.charAt(i) == '}' ) {
                System.out.println(text.charAt(i));
                obcy = false;
                }
                else obcy = true;
                
        } 

    }
}
}
