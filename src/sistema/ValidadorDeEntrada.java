package sistema;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ValidadorDeEntrada {
    
    public static String lerApenasTexto(Scanner sc, String mensagem) {
        String entrada;
        while (true) {
            System.out.print(mensagem);
            entrada = sc.nextLine();
            if (entrada.matches("[a-zA-Z\\s]+")) {
                return entrada;
            } else {
                System.out.println("ERRO: Entrada inválida. Por favor, digite apenas texto.");
            }
        }
    }

    public static String lerApenasNumeros(Scanner sc, String mensagem) {
        String entrada;
        while (true) {
            System.out.print(mensagem);
            entrada = sc.nextLine();
            if (entrada.matches("\\d+")) {
                return entrada;
            } else {
                System.out.println("ERRO: Entrada inválida. Por favor, digite apenas números.");
            }
        }
    }

     public static String lerStringNaoVazia(Scanner sc, String mensagem) {
        String entrada;
        while (true) {
            System.out.print(mensagem);
            entrada = sc.nextLine();
            if (!entrada.trim().isEmpty()) {
                return entrada;
            } else {
                System.out.println("ERRO: Este campo não pode ficar em branco.");
            }
        }
    }

    public static double lerDouble(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = sc.nextLine();
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Entrada inválida. Por favor, digite um número (ex: 20 ou 15.5).");
            }
        }
    }

    public static LocalDateTime lerDataHora(Scanner sc, String mensagem, DateTimeFormatter formatador) {
        LocalDateTime dataHora = null;
        while (dataHora == null) {
            System.out.print(mensagem);
            String entrada = sc.nextLine();
            try {
                dataHora = LocalDateTime.parse(entrada, formatador);
            } catch (DateTimeParseException e) {
                System.out.println("ERRO: Formato de data inválido! Por favor, use o formato " + "dd/MM/yyyy HH:mm.");
            }
        }
        return dataHora;
    }
}
