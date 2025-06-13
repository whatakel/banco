package util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private static final String CAMINHO_ARQUIVO = "log.txt";

    public static void registrar(String mensagem){
        try (FileWriter fw = new FileWriter(CAMINHO_ARQUIVO, true)){
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            fw.write("[" + timestamp + "]" + mensagem + "\n");
        }catch (IOException e){
            System.err.println("Erro ao escrever no log: " + e.getMessage());
        }
    }
}

 