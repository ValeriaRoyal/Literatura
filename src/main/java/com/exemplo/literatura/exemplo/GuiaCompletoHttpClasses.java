package com.exemplo.literatura.exemplo;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/**
 * GUIA COMPLETO: HttpClient, HttpRequest e HttpResponse
 * 
 * Este exemplo demonstra de forma prática e detalhada como usar as três classes
 * principais da API HTTP nativa do Java 11+, mostrando:
 * - Configurações e opções disponíveis
 * - Diferentes tipos de requisições
 * - Tratamento de respostas
 * - Boas práticas e padrões de uso
 */
public class GuiaCompletoHttpClasses {
    
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("📖 GUIA COMPLETO: HttpClient, HttpRequest e HttpResponse");
        System.out.println("======================================================================\n");
        
        // 1. HttpClient - O cliente HTTP reutilizável
        demonstrarHttpClient();
        
        // 2. HttpRequest - Construção de requisições
        demonstrarHttpRequest();
        
        // 3. HttpResponse - Tratamento de respostas
        demonstrarHttpResponse();
        
        // 4. Padrões de uso no projeto Literatura
        demonstrarPadroesLiteratura();
        
        // 5. Boas práticas e dicas
        demonstrarBoasPraticas();
        
        System.out.println("🎓 CONCLUSÃO");
        System.out.println("=============");
        System.out.println("As classes HttpClient, HttpRequest e HttpResponse do Java 11+ oferecem");
        System.out.println("uma API moderna, eficiente e fácil de usar para comunicação HTTP,");
        System.out.println("eliminando a necessidade de bibliotecas externas para casos básicos.");
    }
    
    private static void demonstrarHttpClient() {
        System.out.println("🔧 1. HttpClient - O Cliente HTTP");
        System.out.println("==================================");
        
        System.out.println("📋 O HttpClient é o ponto de entrada para fazer requisições HTTP.");
        System.out.println("   É reutilizável e thread-safe, devendo ser criado uma vez e reutilizado.\n");
        
        // Cliente básico
        System.out.println("🔹 Cliente Básico:");
        HttpClient clienteBasico = HttpClient.newHttpClient();
        System.out.println("   HttpClient clienteBasico = HttpClient.newHttpClient();");
        System.out.println("   • Configurações padrão do sistema");
        System.out.println("   • HTTP/1.1 e HTTP/2 suportados");
        System.out.println("   • Sem timeout específico\n");
        
        // Cliente customizado
        System.out.println("🔹 Cliente Customizado:");
        HttpClient clienteCustomizado = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .version(HttpClient.Version.HTTP_2)
            .build();
        
        System.out.println("   HttpClient cliente = HttpClient.newBuilder()");
        System.out.println("       .connectTimeout(Duration.ofSeconds(10))");
        System.out.println("       .followRedirects(HttpClient.Redirect.NORMAL)");
        System.out.println("       .version(HttpClient.Version.HTTP_2)");
        System.out.println("       .build();");
        System.out.println("   • Timeout de conexão: 10 segundos");
        System.out.println("   • Segue redirecionamentos automaticamente");
        System.out.println("   • Prefere HTTP/2 quando disponível\n");
        
        System.out.println("📊 Opções de Configuração:");
        System.out.println("   • connectTimeout(): Timeout para estabelecer conexão");
        System.out.println("   • followRedirects(): NEVER, ALWAYS, NORMAL");
        System.out.println("   • version(): HTTP_1_1, HTTP_2");
        System.out.println("   • proxy(): Configuração de proxy");
        System.out.println("   • authenticator(): Autenticação automática");
        System.out.println("   • cookieHandler(): Gerenciamento de cookies\n");
    }
    
    private static void demonstrarHttpRequest() {
        System.out.println("🔧 2. HttpRequest - Construção de Requisições");
        System.out.println("==============================================");
        
        System.out.println("📋 HttpRequest representa uma requisição HTTP específica.");
        System.out.println("   É imutável e construído usando o padrão Builder.\n");
        
        // GET Request
        System.out.println("🔹 Requisição GET:");
        System.out.println("   HttpRequest request = HttpRequest.newBuilder()");
        System.out.println("       .uri(URI.create(\"https://api.exemplo.com/livros\"))");
        System.out.println("       .header(\"Accept\", \"application/json\")");
        System.out.println("       .header(\"User-Agent\", \"Literatura-App/1.0\")");
        System.out.println("       .timeout(Duration.ofSeconds(30))");
        System.out.println("       .GET()");
        System.out.println("       .build();\n");
        
        // POST Request
        System.out.println("🔹 Requisição POST:");
        System.out.println("   String jsonBody = \"{\\\"titulo\\\":\\\"Dom Casmurro\\\"}\";");
        System.out.println("   HttpRequest request = HttpRequest.newBuilder()");
        System.out.println("       .uri(URI.create(\"https://api.exemplo.com/livros\"))");
        System.out.println("       .header(\"Content-Type\", \"application/json\")");
        System.out.println("       .POST(HttpRequest.BodyPublishers.ofString(jsonBody))");
        System.out.println("       .build();\n");
        
        System.out.println("📊 Métodos HTTP Suportados:");
        System.out.println("   • GET(): Buscar dados");
        System.out.println("   • POST(BodyPublisher): Criar recursos");
        System.out.println("   • PUT(BodyPublisher): Atualizar recursos");
        System.out.println("   • DELETE(): Remover recursos");
        System.out.println("   • method(String, BodyPublisher): Método customizado\n");
        
        System.out.println("📊 Body Publishers:");
        System.out.println("   • BodyPublishers.ofString(string): Texto/JSON");
        System.out.println("   • BodyPublishers.ofByteArray(bytes): Dados binários");
        System.out.println("   • BodyPublishers.ofFile(path): Arquivo");
        System.out.println("   • BodyPublishers.ofInputStream(supplier): Stream");
        System.out.println("   • BodyPublishers.noBody(): Sem corpo\n");
    }
    
    private static void demonstrarHttpResponse() {
        System.out.println("🔧 3. HttpResponse - Tratamento de Respostas");
        System.out.println("=============================================");
        
        System.out.println("📋 HttpResponse contém a resposta do servidor com dados e metadados.\n");
        
        System.out.println("📊 Informações Disponíveis:");
        System.out.println("   • statusCode(): Código HTTP (200, 404, 500, etc.)");
        System.out.println("   • headers(): Mapa com headers da resposta");
        System.out.println("   • body(): Corpo da resposta");
        System.out.println("   • uri(): URI da requisição original");
        System.out.println("   • version(): Versão HTTP utilizada");
        System.out.println("   • request(): Requisição original\n");
        
        System.out.println("📊 Body Handlers Principais:");
        System.out.println("   • BodyHandlers.ofString(): String (JSON, HTML, texto)");
        System.out.println("   • BodyHandlers.ofByteArray(): Array de bytes");
        System.out.println("   • BodyHandlers.ofFile(path): Salva em arquivo");
        System.out.println("   • BodyHandlers.discarding(): Descarta o corpo\n");
        
        System.out.println("🔹 Exemplo de Uso:");
        System.out.println("   HttpResponse<String> response = client.send(request,");
        System.out.println("       HttpResponse.BodyHandlers.ofString());");
        System.out.println("   ");
        System.out.println("   if (response.statusCode() == 200) {");
        System.out.println("       String json = response.body();");
        System.out.println("       // Processar JSON...");
        System.out.println("   } else {");
        System.out.println("       System.out.println(\"Erro: \" + response.statusCode());");
        System.out.println("   }\n");
    }
    
    private static void demonstrarPadroesLiteratura() {
        System.out.println("🔧 4. Padrões de Uso no Projeto Literatura");
        System.out.println("==========================================");
        
        System.out.println("📋 Como essas classes são usadas no projeto de literatura:\n");
        
        System.out.println("🔹 1. Configuração do Cliente (GutendxHttpService):");
        System.out.println("   private final HttpClient httpClient = HttpClient.newBuilder()");
        System.out.println("       .connectTimeout(Duration.ofSeconds(10))");
        System.out.println("       .followRedirects(HttpClient.Redirect.NORMAL)");
        System.out.println("       .build();\n");
        
        System.out.println("🔹 2. Busca de Livros:");
        System.out.println("   String url = BASE_URL + \"?search=\" + termo;");
        System.out.println("   HttpRequest request = HttpRequest.newBuilder()");
        System.out.println("       .uri(URI.create(url))");
        System.out.println("       .header(\"Accept\", \"application/json\")");
        System.out.println("       .GET()");
        System.out.println("       .build();\n");
        
        System.out.println("🔹 3. Processamento da Resposta:");
        System.out.println("   HttpResponse<String> response = httpClient.send(request,");
        System.out.println("       HttpResponse.BodyHandlers.ofString());");
        System.out.println("   ");
        System.out.println("   if (response.statusCode() == 200) {");
        System.out.println("       GutendxResponse gutendxResponse = objectMapper");
        System.out.println("           .readValue(response.body(), GutendxResponse.class);");
        System.out.println("       return gutendxResponse.getResults();");
        System.out.println("   }\n");
        
        System.out.println("🔹 4. Tratamento de Erros:");
        System.out.println("   try {");
        System.out.println("       // Fazer requisição...");
        System.out.println("   } catch (IOException e) {");
        System.out.println("       logger.error(\"Erro de I/O: {}\", e.getMessage());");
        System.out.println("   } catch (InterruptedException e) {");
        System.out.println("       Thread.currentThread().interrupt();");
        System.out.println("       logger.error(\"Requisição interrompida\");");
        System.out.println("   }\n");
    }
    
    private static void demonstrarBoasPraticas() {
        System.out.println("🔧 5. Boas Práticas e Dicas");
        System.out.println("============================");
        
        System.out.println("✅ BOAS PRÁTICAS:");
        System.out.println("   • Reutilize o HttpClient (é thread-safe)");
        System.out.println("   • Configure timeouts apropriados");
        System.out.println("   • Use headers adequados (Accept, User-Agent, etc.)");
        System.out.println("   • Sempre verifique o status code");
        System.out.println("   • Trate exceções IOException e InterruptedException");
        System.out.println("   • Use BodyHandlers apropriados para o tipo de resposta");
        System.out.println("   • Implemente retry logic para falhas temporárias");
        System.out.println("   • Log requisições para debugging\n");
        
        System.out.println("⚠️  CUIDADOS:");
        System.out.println("   • HttpRequest é imutável - use o builder");
        System.out.println("   • Não ignore InterruptedException");
        System.out.println("   • Configure timeouts para evitar travamentos");
        System.out.println("   • Valide URLs antes de criar URI");
        System.out.println("   • Considere pool de conexões para alta carga\n");
        
        System.out.println("🚀 VANTAGENS DA API NATIVA:");
        System.out.println("   • Sem dependências externas");
        System.out.println("   • Suporte nativo a HTTP/2");
        System.out.println("   • API moderna e fluente");
        System.out.println("   • Requisições síncronas e assíncronas");
        System.out.println("   • Integração com CompletableFuture");
        System.out.println("   • Performance otimizada\n");
    }
}
