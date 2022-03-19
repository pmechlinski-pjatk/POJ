package main;

public class Suma {

    public static void main(String[] args) {
        String[] tablica = { "1", "6", "1" };
       // System.out.println(sum(tablica)); // out: 8
        System.out.println(count(tablica, 1)); // out: 2
    }
    String[] array = { "0", "6"};
    int counted = 1;

    //Nieparzysty komputer
    /** policz ile razy liczba counted występuje w tablicy jako string */
    public static int count(String[] array, int counted) {
        

        String countedString=String.valueOf(counted);
        int counter=0;

        for(int i = 0; i< array.length; i++){
            
            if (countedString.equals(array[i])) { counter++;};
        };
        System.out.println(counter);
            
        return -1;
    }
}
