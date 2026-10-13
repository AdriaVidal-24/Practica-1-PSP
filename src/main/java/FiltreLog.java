

import java.util.Scanner;

public class FiltreLog {
    public static void main(String[] args) {
        String objectiu = "ERROR";
        if(args.length > 0 && args[0] != null && !args[0].isEmpty()) {
            objectiu = args[0];
        }

        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextLine()) {
            System.err.println("Error: Text buit");
            System.exit(1);
        }
        
        int comptador = 0;
        while (sc.hasNextLine()) {
            String linia = sc.nextLine();
            if (linia.isEmpty()) continue;

            int pos = 0;
            while ((pos = linia.indexOf(objectiu, pos)) != -1) {
                comptador++;
                pos += objectiu.length();
            }
        }

        System.out.println(comptador);
        System.exit(0);
        sc.close();
    }
}
