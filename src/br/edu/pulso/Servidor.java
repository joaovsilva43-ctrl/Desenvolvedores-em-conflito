package br.edu.pulso;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Servidor {
    private static final ChamadoService SERVICE = new ChamadoService();
    private static final Path WEB = Paths.get("web").toAbsolutePath().normalize();

    public static void main(String[] args) throws IOException {
        int porta = Integer.parseInt(System.getenv().getOrDefault("PORTA", "8766"));
        HttpServer http = HttpServer.create(new InetSocketAddress("127.0.0.1", porta), 0);
        http.createContext("/api/chamados", Servidor::responderChamados);
        http.createContext("/api/resumo", Servidor::responderResumo);
        http.createContext("/", Servidor::responderArquivo);
        http.start();
        System.out.println("Painel PULSO: http://localhost:" + porta);
        System.out.println("Para encerrar: Ctrl+C");
    }

    private static void responderChamados(HttpExchange ex) throws IOException {
        if (!"GET".equals(ex.getRequestMethod())) {
            enviar(ex, 405, "application/json", "{\"erro\":\"Método não permitido\"}");
            return;
        }
        Map<String, String> parametros = lerParametros(ex.getRequestURI().getRawQuery());
        List<Chamado> chamados = SERVICE.listar(parametros);
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < chamados.size(); i++) {
            if (i > 0) json.append(",");
            json.append(chamados.get(i).toJson());
        }
        json.append("]");
        enviar(ex, 200, "application/json", json.toString());
    }

    private static void responderResumo(HttpExchange ex) throws IOException {
        if (!"GET".equals(ex.getRequestMethod())) {
            enviar(ex, 405, "application/json", "{\"erro\":\"Método não permitido\"}");
            return;
        }
        List<Chamado> chamados = SERVICE.todos();
        long abertos = chamados.stream().filter(c -> c.getStatus().equals("Aberto")).count();
        long concluidos = chamados.stream().filter(c -> c.getStatus().equals("Concluído")).count();
        String json = "{\"total\":" + chamados.size() + ",\"abertos\":" + abertos + ",\"concluidos\":" + concluidos + "}";
        enviar(ex, 200, "application/json", json);
    }

    private static Map<String, String> lerParametros(String query) {
        Map<String, String> parametros = new HashMap<>();
        if (query == null || query.isEmpty()) return parametros;
        for (String parte : query.split("&")) {
            String[] p = parte.split("=", 2);
            String chave = URLDecoder.decode(p[0], StandardCharsets.UTF_8);
            String valor = p.length > 1 ? URLDecoder.decode(p[1], StandardCharsets.UTF_8) : "";
            parametros.put(chave, valor);
        }
        return parametros;
    }

    private static void responderArquivo(HttpExchange ex) throws IOException {
        if (!"GET".equals(ex.getRequestMethod())) {
            enviar(ex, 405, "text/plain", "Método não permitido");
            return;
        }
        String uri = ex.getRequestURI().getPath();
        if ("/".equals(uri)) uri = "/index.html";
        Path arquivo = WEB.resolve(uri.substring(1)).normalize();
        if (!arquivo.startsWith(WEB) || !Files.isRegularFile(arquivo)) {
            enviar(ex, 404, "text/plain", "Arquivo não encontrado");
            return;
        }
        String tipo = uri.endsWith(".html") ? "text/html" : uri.endsWith(".css") ? "text/css" :
            uri.endsWith(".js") ? "application/javascript" : "application/octet-stream";
        byte[] dados = Files.readAllBytes(arquivo);
        ex.getResponseHeaders().set("Content-Type", tipo + "; charset=utf-8");
        ex.sendResponseHeaders(200, dados.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(dados); }
    }

    static void enviar(HttpExchange ex, int codigo, String tipo, String corpo) throws IOException {
        byte[] dados = corpo.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", tipo + "; charset=utf-8");
        ex.sendResponseHeaders(codigo, dados.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(dados); }
    }
}
