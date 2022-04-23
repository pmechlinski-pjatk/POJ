class choinka {
    public static void main(String[] args) {
        int count = 4; // Parametr wysokości choinky
        int i = 0;
        String x = "*";
        String w = " ";
        for (; i < count-1; i++) {
        x = w + x;
        
        }
        for (i=0 ; i < count; i++) {
            System.out.println(x);
            x += "**"; 
            x = x.substring(1, x.length());
        }
        
    }
}
