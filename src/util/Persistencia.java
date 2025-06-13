package util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import model.Conta;
import model.Transacao;
import model.usuario.*;

public class Persistencia {
    private static final String USUARIOS_ARQ = new File("dados/usuarios.json").getAbsolutePath();
    private static final String CONTAS_ARQ = new File("dados/contas.json").getAbsolutePath();
    private static final String TRANSACOES_ARQ = new File("dados/transacoes.json").getAbsolutePath();

    private static final Gson gson = construirGsonComHierarquiaUsuario();

    private static Gson construirGsonComHierarquiaUsuario() {
        RuntimeTypeAdapterFactory<Usuario> adaptador = RuntimeTypeAdapterFactory
                .of(Usuario.class, "tipo")
                .registerSubtype(Adm.class, "Adm")
                .registerSubtype(Cliente.class, "Cliente")
                .registerSubtype(Gerente.class, "Gerente");

        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(adaptador)
                .registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter out, LocalDateTime value) throws IOException {
                        out.value(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    }

                    @Override
                    public LocalDateTime read(JsonReader in) throws IOException {
                        return LocalDateTime.parse(in.nextString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    }
                })
                .create();
    }

    public static int obterMaiorIdTransacao(List<Cliente> clientes) {
        int maior = 0;
        for (Cliente cliente : clientes) {
            for (Transacao t : cliente.getConta().getTransacoes()) {
                if (t.getId() > maior) {
                    maior = t.getId();
                }
            }
        }
        return maior;
    }


    private static void garantirDiretorio(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        File dir = arquivo.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
//            System.out.println("DEBUG: Pasta criada em " + dir.getAbsolutePath());
        }
    }

    public static List<Usuario> carregarUsuarios() {
        File arquivo = new File(USUARIOS_ARQ);
        if (!arquivo.exists() || arquivo.length() == 0) {
            List<Usuario> usuarios = new ArrayList<>();
            usuarios.add(new Adm("Administrador", "00000000000", "adm", "adm"));
            salvarUsuarios(usuarios); // salva já serializado corretamente
            return usuarios;
        }
        try (Reader reader = new FileReader(USUARIOS_ARQ)) {
            Type tipoLista = new TypeToken<List<Usuario>>() {
            }.getType();
            return gson.fromJson(reader, tipoLista);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void salvarUsuarios(List<Usuario> usuarios) {
        garantirDiretorio(USUARIOS_ARQ);
//        System.out.println("DEBUG: Salvando " + usuarios.size() + " usuários em " + USUARIOS_ARQ);
//        for (Usuario u : usuarios) {
//            System.out.println("-> " + u.getClass().getSimpleName() + ": " + u.getEmail());
//        }

        try (Writer writer = new FileWriter(USUARIOS_ARQ)) {
            gson.toJson(usuarios, writer);
            writer.flush(); // força escrita no disco
//            System.out.println("DEBUG: Usuários salvos com sucesso.");
        } catch (Exception e) {
            System.err.println("ERRO AO SALVAR USUÁRIOS:");
            e.printStackTrace();
        }
    }

    //contas
    public static void salvarContas(List<Cliente> clientes) {
        garantirDiretorio(CONTAS_ARQ);
        List<Conta> contas = new ArrayList<>();
        for (Cliente c : clientes) {
            contas.add(c.getConta());
        }

        try (Writer writer = new FileWriter(CONTAS_ARQ)) {
            gson.toJson(contas, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Conta> carregarContas() {
        File arquivo = new File(CONTAS_ARQ);
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(CONTAS_ARQ)) {
            Type tipoLista = new TypeToken<List<Conta>>() {
            }.getType();
            return gson.fromJson(reader, tipoLista);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    //transacoes
    public static void salvarTransacoes(List<Cliente> clientes) {
        garantirDiretorio(TRANSACOES_ARQ);
        List<Transacao> todas = new ArrayList<>();
        for (Cliente c : clientes) {
            todas.addAll(c.getConta().getTransacoes());
        }

        try (Writer writer = new FileWriter(TRANSACOES_ARQ)) {
            gson.toJson(todas, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //filtrar
    public static List<Cliente> filtrarClientes(List<Usuario> usuarios) {
        List<Cliente> lista = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u instanceof Cliente c) lista.add(c);
        }
        return lista;
    }

    public static List<Gerente> filtrarGerentes(List<Usuario> usuarios) {
        List<Gerente> lista = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u instanceof Gerente g && !(u instanceof Adm)) lista.add(g);
        }
        return lista;
    }
}
