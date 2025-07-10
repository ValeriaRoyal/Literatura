package com.exemplo.literatura.exemplo;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/**
 * Exemplo prático demonstrando o uso das classes HttpClient, HttpRequest e HttpResponse
 * com simulação de diferentes cenários e explicações detalhadas.
 */
public class HttpClientExemploLocal {
    
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("🚀 EXEMPLO PRÁTICO: HttpClient, HttpRequest e HttpResponse");
        System.out.println("======================================================================\n");
        
        // 1. Demonstrar criação e configuração do HttpClient
        demonstrarHttpClient();
        
        // 2. Demonstrar construção do HttpRequest
        demonstrarHttpRequest();
        
        // 3. Demonstrar diferentes tipos de requisições
        demonstrarTiposRequisicoes();
        
        // 4. Demonstrar tratamento de HttpResponse
        demonstrarHttpResponse();
        
        // 5. Demonstrar requisições assíncronas
        demonstrarRequisicaoAssincrona();
        
        System.out.println("\n🎯 RESUMO FINAL");
        System.out.println("================");
        System.out.println("✅ HttpClient: Cliente HTTP reutilizável com configurações globais");
        System.out.println("✅ HttpRequest: Representa uma requisição HTTP específica");
        System.out.println("✅ HttpResponse: Contém a resposta do servidor com dados e metadados");
        System.out.println("✅ Suporte nativo para requisições síncronas e assíncronas");
        System.out.println("✅ Configurações flexíveis de timeout, headers e redirecionamentos");
    }
    
    private static void demonstrarHttpClient() {
        System.out.println("🔧 1. CRIANDO E CONFIGURANDO O HttpClient");
        System.out.println("----------------------------------------");
        
        // HttpClient básico
        HttpClient clienteBasico = HttpClient.newHttpClient();
        System.out.println("✅ HttpClient básico criado (configurações padrão)");
        
        // HttpClient customizado
        HttpClient clienteCustomizado = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .version(HttpClient.Version.HTTP_2)
            .build();
        
        System.out.println("✅ HttpClient customizado criado com:");
        System.out.println("   • Timeout de conexão: 10 segundos");
        System.out.println("   • Redirecionamentos: Automático");
        System.out.println("   • Versão HTTP: 2.0 preferencial");
        System.out.println();
    }
    
    private static void demonstrarHttpRequest() {
        System.out.println("🔧 2. CONSTRUINDO HttpRequest");
        System.out.println("-----------------------------");
        
        try {
            // GET request simples
            HttpRequest requestGet = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/get"))
                .GET()
                .build();
            System.out.println("✅ GET Request criado para: " + requestGet.uri());
            
            // POST request com dados
            HttpRequest requestPost = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/post"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(30))
                .POST(HttpRequest.BodyPublishers.ofString("{\"nome\":\"João\",\"idade\":30}"))
                .build();
            
            System.out.println("✅ POST Request criado com:");
            System.out.println("   • Content-Type: application/json");
            System.out.println("   • Timeout: 30 segundos");
            System.out.println("   • Body: JSON com dados do usuário");
            
            // PUT request
            HttpRequest requestPut = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/put"))
                .header("Authorization", "Bearer token123")
                .PUT(HttpRequest.BodyPublishers.ofString("dados atualizados"))
                .build();
            System.out.println("✅ PUT Request criado com Authorization header");
            
            // DELETE request
            HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/delete"))
                .DELETE()
                .build();
            System.out.println("✅ DELETE Request criado");
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao criar requests: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void demonstrarTiposRequisicoes() {
        System.out.println("🔧 3. TIPOS DE BODY PUBLISHERS");
        System.out.println("------------------------------");
        
        // Diferentes tipos de body
        System.out.println("📝 Tipos de dados que podem ser enviados:");
        System.out.println("   • String: HttpRequest.BodyPublishers.ofString(\"texto\")");
        System.out.println("   • Bytes: HttpRequest.BodyPublishers.ofByteArray(bytes)");
        System.out.println("   • Arquivo: HttpRequest.BodyPublishers.ofFile(path)");
        System.out.println("   • Stream: HttpRequest.BodyPublishers.ofInputStream(supplier)");
        System.out.println("   • Vazio: HttpRequest.BodyPublishers.noBody()");
        System.out.println();
    }
    
    private static void demonstrarHttpResponse() {
        System.out.println("🔧 4. TRABALHANDO COM HttpResponse");
        System.out.println("----------------------------------");
        
        System.out.println("📊 Informações disponíveis no HttpResponse:");
        System.out.println("   • statusCode(): Código de status HTTP (200, 404, 500, etc.)");
        System.out.println("   • headers(): Mapa com todos os headers da resposta");
        System.out.println("   • body(): Corpo da resposta (String, bytes, etc.)");
        System.out.println("   • uri(): URI da requisição");
        System.out.println("   • version(): Versão HTTP utilizada");
        System.out.println("   • request(): Requisição original");
        
        System.out.println("\n📋 Body Handlers mais comuns:");
        System.out.println("   • BodyHandlers.ofString(): Resposta como String");
        System.out.println("   • BodyHandlers.ofByteArray(): Resposta como array de bytes");
        System.out.println("   • BodyHandlers.ofFile(path): Salva resposta em arquivo");
        System.out.println("   • BodyHandlers.discarding(): Descarta o corpo da resposta");
        System.out.println();
    }
    
    private static void demonstrarRequisicaoAssincrona() {
        System.out.println("🔧 5. REQUISIÇÕES ASSÍNCRONAS");
        System.out.println("-----------------------------");
        
        HttpClient client = HttpClient.newHttpClient();
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/delay/1"))
                .GET()
                .build();
            
            System.out.println("🚀 Enviando requisição assíncrona...");
            
            // Requisição assíncrona
            CompletableFuture<HttpResponse<String>> futureResponse = 
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            
            System.out.println("⏳ Requisição enviada, continuando outras tarefas...");
            System.out.println("💼 Fazendo outras operações enquanto aguarda...");
            
            // Simular outras operações
            for (int i = 1; i <= 3; i++) {
                System.out.println("   📋 Operação " + i + " executada");
                Thread.sleep(300);
            }
            
            // Aguardar resultado (com timeout)
            try {
                HttpResponse<String> response = futureResponse.get();
                System.out.println("✅ Resposta recebida assincronamente!");
                System.out.println("   Status: " + response.statusCode());
            } catch (Exception e) {
                System.out.println("❌ Timeout ou erro na requisição assíncrona");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro na demonstração assíncrona: " + e.getMessage());
        }
        System.out.println();
    }
}
