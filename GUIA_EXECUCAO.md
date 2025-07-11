# 🚀 GUIA DE EXECUÇÃO - SISTEMA DE LITERATURA

## 📋 Como Executar o Sistema

### Opção 1: Usando Maven (Recomendado)
```bash
cd /home/valeria/Projetos/literatura
mvn spring-boot:run
```

### Opção 2: Compilando e Executando JAR
```bash
mvn clean package
java -jar target/literatura-0.0.1-SNAPSHOT.jar
```

## 🧪 Testes Recomendados

### 1. Testes de Validação de Entrada
- Digite números inválidos (-1, 99, abc)
- Deixe campos obrigatórios vazios
- Digite apenas ENTER em campos de texto

### 2. Testes de Busca
- **Busca por Título**: "Pride and Prejudice", "Dom Casmurro"
- **Busca por Autor**: "Shakespeare", "Jane Austen", "Machado de Assis"
- **Busca por Idioma**: "pt", "en", "fr"
- **Busca por Assunto**: "Fiction", "Poetry", "Philosophy"

### 3. Testes de Funcionalidades Avançadas
- Livros mais populares (teste com 1-20)
- Autores por século (teste séculos 18, 19, 20)
- Análise detalhada de livro específico
- Comparação entre dois autores
- Busca avançada com múltiplos critérios

### 4. Testes de Tratamento de Erros
- Busque por termos inexistentes
- Teste conectividade (desconecte internet temporariamente)
- Digite caracteres especiais nos campos

## 📊 Funcionalidades Implementadas

### ✅ Interface de Linha de Comando
- [x] CommandLineRunner implementado
- [x] Menu interativo com 14 opções
- [x] Scanner para entrada do usuário
- [x] Laço de repetição contínuo
- [x] Validação robusta de entrada
- [x] Interface visual com bordas ASCII

### ✅ Funcionalidades de Busca
- [x] Buscar livro por título
- [x] Buscar livros por autor
- [x] Buscar livros por idioma
- [x] Buscar livros por assunto

### ✅ Análises e Estatísticas
- [x] Livros mais populares
- [x] Autores por século
- [x] Estatísticas por idioma
- [x] Livros de domínio público

### ✅ Ferramentas Avançadas
- [x] Análise detalhada de livro
- [x] Comparar autores
- [x] Explorar formatos disponíveis
- [x] Busca avançada (múltiplos critérios)

### ✅ Tratamento de Erros
- [x] Validação de entrada numérica
- [x] Verificação de campos obrigatórios
- [x] Tratamento de exceções de rede
- [x] Mensagens de erro informativas
- [x] Recuperação automática de erros

## 🎯 Exemplos de Uso

### Exemplo 1: Busca Simples
```
Opção: 1
Título: "Alice in Wonderland"
Resultado: Lista de livros com esse título
```

### Exemplo 2: Busca por Autor
```
Opção: 2
Autor: "Shakespeare"
Resultado: Obras de Shakespeare com detalhes dos autores
```

### Exemplo 3: Análise Detalhada
```
Opção: 9
Título: "Romeo and Juliet"
Resultado: Análise completa com formatos, autor, downloads
```

### Exemplo 4: Busca Avançada
```
Opção: 12
Título: "Pride"
Autor: "Austen"
Idioma: "en"
Min. Downloads: 1000
Resultado: Livros que atendem todos os critérios
```

## 🔧 Estrutura do Sistema

```
LiteraturaApplication (main)
├── CommandLineRunner.run()
├── MenuInterativoService.exibirMenu()
├── Loop principal com Scanner
├── Validação de entrada
├── Processamento da opção
├── Chamada do método específico
├── Exibição dos resultados
└── Retorno ao menu principal
```

## 📈 Fluxo de Dados

```
Entrada do Usuário → Validação → API Gutendx → JSON Response
                                      ↓
Conversão Jackson → DTOs → Processamento → Formatação → Exibição
```

## 🛡️ Tratamento de Erros Implementado

1. **Entrada Inválida**: Números fora do range, texto em campos numéricos
2. **Campos Vazios**: Validação de campos obrigatórios
3. **Erros de Rede**: Timeout, conexão perdida, API indisponível
4. **Dados Inexistentes**: Busca sem resultados, livros não encontrados
5. **Exceções Gerais**: Try-catch abrangente com mensagens informativas

## 🎨 Interface Visual

- Bordas ASCII decorativas
- Ícones emoji para melhor UX
- Formatação de tabelas e listas
- Cores e símbolos para status
- Limpeza de tela entre operações
- Pausas para leitura dos resultados

## 📝 Logs e Debugging

O sistema exibe informações detalhadas sobre:
- Requisições à API
- Conversões JSON
- Resultados encontrados
- Erros e exceções
- Estatísticas de uso

## 🚀 Sistema Pronto!

O sistema está completamente funcional e pronto para uso. Todas as funcionalidades foram implementadas conforme solicitado no desafio, incluindo:

- ✅ CommandLineRunner
- ✅ Menu interativo
- ✅ Scanner para entrada
- ✅ Laço de repetição
- ✅ Validação robusta
- ✅ Tratamento de erros
- ✅ Métodos modulares
- ✅ Interface visual
- ✅ Testes abrangentes
