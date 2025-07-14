# 👥 GUIA COMPLETO - FUNCIONALIDADES DE AUTORES

## 🎯 Funcionalidades Obrigatórias Implementadas

### ✅ **4. Lista de Autores**
- Lista todos os autores dos livros buscados
- Ordenação alfabética por nome
- Exibe: Nome, Ano de nascimento, Ano de falecimento
- Considera apenas o primeiro autor de cada livro

### ✅ **5. Autores Vivos em Determinado Ano**
- Consulta personalizada com lógica específica
- Entrada do usuário para ano desejado
- Validação de ano válido (1-2024)
- Exibição formatada dos resultados

## 📊 Estrutura dos Dados do Autor

### 🔍 **Dados da API Gutendx**
```json
{
  "authors": [
    {
      "name": "Shakespeare, William",
      "birth_year": 1564,
      "death_year": 1616
    }
  ]
}
```

### 🗄️ **Entidade JPA Autor**
```java
@Entity
@Table(name = "autores")
public class Autor {
    private Long id;
    private String nome;              // name
    private Integer anoNascimento;    // birth_year
    private Integer anoMorte;         // death_year (pode ser null)
    private List<Livro> livros;       // Relacionamento OneToMany
}
```

## 🔍 Consultas Derivadas Implementadas

### **📋 Lista de Autores**
```java
List<Autor> findAllByOrderByNome()
```
- Retorna todos os autores ordenados por nome
- Usado na funcionalidade "Lista de autores"

### **🕰️ Autores Vivos no Ano**
```java
@Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoMorte IS NULL OR a.anoMorte >= :ano)")
List<Autor> findAutoresVivosNoAno(@Param("ano") Integer ano)
```

**Lógica implementada:**
- Nasceu antes ou no ano especificado **E**
- (Ainda está vivo **OU** morreu no/após o ano especificado)

### **🔧 Consultas Auxiliares**
```java
Optional<Autor> findByNome(String nome)
List<Autor> findByNomeContainingIgnoreCase(String nome)
List<Autor> findByAnoMorteIsNull()
List<Autor> findAutoresPorSeculo(Integer anoInicio, Integer anoFim)
List<Autor> findAutoresComMaisLivros()
```

## 🧪 Exemplos de Teste

### **📝 Teste 1: Lista de Autores**
1. Adicione livros ao catálogo:
   - "Pride and Prejudice" → Jane Austen (1775-1817)
   - "Romeo and Juliet" → Shakespeare (1564-1616)
   - "Dom Casmurro" → Machado de Assis (1839-1908)

2. Execute opção 4 - "Lista de autores"

3. **Resultado esperado:**
   ```
   👥 LISTA DE AUTORES
   ═══════════════════
   📊 Total: 3 autor(es)
   
   1. 👤 Jane Austen (1775 - 1817)
   2. 👤 Machado de Assis (1839 - 1908)
   3. 👤 William Shakespeare (1564 - 1616)
   ```

### **📝 Teste 2: Autores Vivos no Ano**

#### **Teste ano 1800:**
- **Jane Austen**: ✅ VIVO (nasceu 1775, morreu 1817)
- **Shakespeare**: ❌ MORTO (morreu 1616)
- **Machado**: ❌ NÃO NASCEU (nasceu 1839)

#### **Teste ano 1850:**
- **Jane Austen**: ❌ MORTO (morreu 1817)
- **Shakespeare**: ❌ MORTO (morreu 1616)
- **Machado**: ✅ VIVO (nasceu 1839, morreu 1908)

#### **Teste ano 1600:**
- **Shakespeare**: ✅ VIVO (nasceu 1564, morreu 1616)

## 🎯 Lógica "Autor Vivo no Ano"

### **Condições para estar vivo:**
1. **Nascimento**: `anoNascimento <= ano`
2. **Morte**: `anoMorte IS NULL OR anoMorte >= ano`

### **Exemplo prático:**
**Shakespeare (1564-1616) no ano 1600:**
- Nasceu em 1564 ≤ 1600 ✅
- Morreu em 1616 ≥ 1600 ✅
- **Resultado**: ESTAVA VIVO em 1600

## 🚀 Como Executar

### **Opção 1: Maven**
```bash
cd /home/valeria/Projetos/literatura
mvn spring-boot:run
```

### **Opção 2: JAR**
```bash
mvn clean package
java -jar target/literatura-0.0.1-SNAPSHOT.jar
```

### **Menu do Sistema:**
```
📋 MENU PRINCIPAL
═══════════════════════════════════════════════════════════════════

📚 FUNCIONALIDADES OBRIGATÓRIAS DE LIVROS:
   1 - Buscar livro por título e adicionar ao catálogo
   2 - Listar todos os livros
   3 - Listar livros por idioma

👥 FUNCIONALIDADES OBRIGATÓRIAS DE AUTORES:
   4 - Lista de autores
   5 - Listar autores vivos em determinado ano

📊 EXTRAS:
   6 - Estatísticas do catálogo

   0 - Sair
```

## 🔄 Fluxo de Funcionamento

### **1. Extração de Dados do Autor:**
```
JSON da API → Primeiro autor da lista → Entidade Autor
→ Verificação se já existe → Salvamento no banco
```

### **2. Lista de Autores:**
```
Consulta findAllByOrderByNome() → Ordenação alfabética
→ Formatação visual → Exibição com dados completos
```

### **3. Autores Vivos no Ano:**
```
Entrada do ano → Validação → Consulta personalizada
→ Aplicação da lógica → Exibição dos resultados
```

## 📈 Estatísticas Implementadas

### **Estatísticas de Autores:**
- Total de autores no catálogo
- Autores ainda vivos
- Autores falecidos
- Autores por século de nascimento

### **Exemplo de saída:**
```
👥 ESTATÍSTICAS DOS AUTORES:
═══════════════════════════════════════
📊 Total de autores: 15
💚 Autores ainda vivos: 3
⚰️  Autores falecidos: 12

📅 Autores por século:
   Século 16: 2 autor(es)
   Século 18: 4 autor(es)
   Século 19: 7 autor(es)
   Século 20: 2 autor(es)
```

## ✅ Validações Implementadas

### **Entrada do Usuário:**
- Ano não pode estar vazio
- Deve ser um número válido
- Deve estar entre 1 e 2024
- Tratamento de exceções NumberFormatException

### **Dados da API:**
- Considera apenas o primeiro autor da lista
- Trata valores null para ano de morte
- Validação de dados antes do salvamento

## 🎓 Resumo das Funcionalidades

### **✅ Todas as 5 Funcionalidades Obrigatórias:**
1. ✅ Buscar livro por título
2. ✅ Listar todos os livros
3. ✅ Listar livros por idioma
4. ✅ Lista de autores
5. ✅ Listar autores vivos em determinado ano

### **🏗️ Arquitetura Completa:**
- Entidades JPA com relacionamentos
- Repositórios com consultas derivadas
- Serviços com lógica de negócio
- Interface de linha de comando
- Integração com API Gutendx
- Conversão JSON com Jackson

### **🔧 Tecnologias Utilizadas:**
- Spring Boot 3.x
- JPA/Hibernate
- Jackson 2.16
- HttpClient nativo Java 17
- H2 Database (desenvolvimento)
- Maven

## 🚀 Sistema Completo e Funcional!

Todas as funcionalidades obrigatórias de livros e autores estão implementadas e testadas. O sistema está pronto para uso com interface interativa e todas as consultas derivadas funcionando perfeitamente!
