package util;

import java.util.Scanner;

public class InputHelper {
    public static Scanner scan = new Scanner(System.in);

    public static String lerString(String texto) {
        System.out.print(texto);
        return scan.nextLine();
    }

    public static int lerInt(String texto) {
        while (true) {
            try {
                System.out.print(texto);
                return Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido");
            }
        }
    }

    public static double lerDouble(String texto){
        while(true){
            try{
                System.out.print(texto);
                return Double.parseDouble(scan.nextLine().replace(",", "."));
                
            } catch (NumberFormatException e){
                System.out.println("Digite um número decimal válido.");
            }
        }
    }
}
