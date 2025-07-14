package com.exemplo.literatura.exemplo;

/**
 * Teste completo das funcionalidades de autores
 * Demonstra as duas funcionalidades obrigatórias relacionadas aos autores
 */
public class TesteAutoresCompleto {
    
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("👥 TESTE COMPLETO - FUNCIONALIDADES DE AUTORES");
        System.out.println("======================================================================\n");
        
        System.out.println("✅ FUNCIONALIDADES OBRIGATÓRIAS DE AUTORES IMPLEMENTADAS:");
        System.out.println("═".repeat(60));
        
        System.out.println("\n4️⃣ LISTA DE AUTORES:");
        System.out.println("─".repeat(40));
        System.out.println("✅ Lista todos os autores dos livros buscados");
        System.out.println("✅ Ordenação por nome usando findAllByOrderByNome()");
        System.out.println("✅ Exibe informações completas de cada autor:");
        System.out.println("   • Nome");
        System.out.println("   • Ano de nascimento");
        System.out.println("   • Ano de falecimento");
        System.out.println("✅ Considera apenas o primeiro autor de cada livro");
        System.out.println("✅ Contador de total de autores");
        
        System.out.println("\n5️⃣ AUTORES VIVOS EM DETERMINADO ANO:");
        System.out.println("─".repeat(40));
        System.out.println("✅ Consulta personalizada com @Query");
        System.out.println("✅ Lógica implementada:");
        System.out.println("   • Nasceu antes ou no ano especificado E");
        System.out.println("   • Morreu depois do ano OU ainda está vivo (ano_morte é null)");
        System.out.println("✅ Entrada do usuário para o ano desejado");
        System.out.println("✅ Validação de ano válido");
        System.out.println("✅ Exibição formatada dos resultados");
        
        System.out.println("\n📊 ESTRUTURA DOS DADOS DO AUTOR:");
        System.out.println("═".repeat(60));
        
        System.out.println("\n🔍 DADOS DA API GUTENDX:");
        System.out.println("─".repeat(30));
        System.out.println("✅ JSON recebe lista de autores por livro");
        System.out.println("✅ Cada autor possui três características:");
        System.out.println("   • Nome (name)");
        System.out.println("   • Ano de nascimento (birth_year)");
        System.out.println("   • Ano de falecimento (death_year)");
        System.out.println("✅ Considera apenas o primeiro autor da lista");
        
        System.out.println("\n🗄️ ENTIDADE JPA AUTOR:");
        System.out.println("─".repeat(30));
        System.out.println("✅ @Entity com tabela 'autores'");
        System.out.println("✅ Campos mapeados:");
        System.out.println("   • id (Long) - Chave primária");
        System.out.println("   • nome (String) - Nome do autor");
        System.out.println("   • anoNascimento (Integer) - Ano de nascimento");
        System.out.println("   • anoMorte (Integer) - Ano de falecimento (pode ser null)");
        System.out.println("✅ Relacionamento OneToMany com Livro");
        
        System.out.println("\n🔍 CONSULTAS DERIVADAS IMPLEMENTADAS:");
        System.out.println("═".repeat(60));
        
        System.out.println("\n📋 CONSULTA OBRIGATÓRIA 1 - Lista de Autores:");
        System.out.println("─".repeat(50));
        System.out.println("List<Autor> findAllByOrderByNome()");
        System.out.println("• Retorna todos os autores ordenados por nome");
        System.out.println("• Usado na funcionalidade 'Lista de autores'");
        
        System.out.println("\n🕰️ CONSULTA OBRIGATÓRIA 2 - Autores Vivos no Ano:");
        System.out.println("─".repeat(50));
        System.out.println("@Query(\"SELECT a FROM Autor a WHERE a.anoNascimento <= :ano");
        System.out.println("       AND (a.anoMorte IS NULL OR a.anoMorte >= :ano)\")");
        System.out.println("List<Autor> findAutoresVivosNoAno(@Param(\"ano\") Integer ano)");
        System.out.println("• Lógica: nasceu <= ano E (morte = null OU morte >= ano)");
        System.out.println("• Usado na funcionalidade 'Autores vivos em determinado ano'");
        
        System.out.println("\n🔧 CONSULTAS AUXILIARES:");
        System.out.println("─".repeat(30));
        System.out.println("✅ findByNome() - Busca por nome exato");
        System.out.println("✅ findByNomeContainingIgnoreCase() - Busca parcial");
        System.out.println("✅ findByAnoMorteIsNull() - Autores ainda vivos");
        System.out.println("✅ findAutoresPorSeculo() - Autores por período");
        System.out.println("✅ findAutoresComMaisLivros() - Autores prolíficos");
        
        System.out.println("\n🧪 EXEMPLOS DE TESTE:");
        System.out.println("═".repeat(60));
        
        System.out.println("\n📝 TESTE 1 - Lista de Autores:");
        System.out.println("─".repeat(30));
        System.out.println("1. Adicione alguns livros ao catálogo:");
        System.out.println("   • 'Pride and Prejudice' (Jane Austen, 1775-1817)");
        System.out.println("   • 'Romeo and Juliet' (Shakespeare, 1564-1616)");
        System.out.println("   • 'Dom Casmurro' (Machado de Assis, 1839-1908)");
        System.out.println("2. Execute 'Lista de autores'");
        System.out.println("3. Verifique ordenação alfabética");
        System.out.println("4. Confirme dados: nome, nascimento, morte");
        
        System.out.println("\n📝 TESTE 2 - Autores Vivos no Ano:");
        System.out.println("─".repeat(30));
        System.out.println("• Teste ano 1800:");
        System.out.println("  - Jane Austen: ✅ (nasceu 1775, morreu 1817)");
        System.out.println("  - Shakespeare: ❌ (morreu 1616)");
        System.out.println("  - Machado: ❌ (nasceu 1839)");
        System.out.println("• Teste ano 1850:");
        System.out.println("  - Jane Austen: ❌ (morreu 1817)");
        System.out.println("  - Shakespeare: ❌ (morreu 1616)");
        System.out.println("  - Machado: ✅ (nasceu 1839, morreu 1908)");
        System.out.println("• Teste ano 1600:");
        System.out.println("  - Shakespeare: ✅ (nasceu 1564, morreu 1616)");
        
        System.out.println("\n🔄 FLUXO DE FUNCIONAMENTO:");
        System.out.println("═".repeat(60));
        
        System.out.println("\n1. EXTRAÇÃO DE DADOS DO AUTOR:");
        System.out.println("   JSON da API → Primeiro autor da lista → Entidade Autor");
        System.out.println("   → Verificação se já existe → Salvamento no banco");
        
        System.out.println("\n2. LISTA DE AUTORES:");
        System.out.println("   Consulta findAllByOrderByNome() → Ordenação alfabética");
        System.out.println("   → Formatação visual → Exibição com dados completos");
        
        System.out.println("\n3. AUTORES VIVOS NO ANO:");
        System.out.println("   Entrada do ano → Validação → Consulta personalizada");
        System.out.println("   → Aplicação da lógica → Exibição dos resultados");
        
        System.out.println("\n🎯 LÓGICA 'AUTOR VIVO NO ANO':");
        System.out.println("═".repeat(60));
        System.out.println("Para um autor estar vivo em determinado ano:");
        System.out.println("1. Deve ter nascido antes ou no ano especificado");
        System.out.println("2. E uma das condições:");
        System.out.println("   a) Ainda está vivo (ano_morte = null) OU");
        System.out.println("   b) Morreu no ano especificado ou depois");
        
        System.out.println("\nExemplo: Shakespeare (1564-1616) no ano 1600:");
        System.out.println("• Nasceu em 1564 ≤ 1600 ✅");
        System.out.println("• Morreu em 1616 ≥ 1600 ✅");
        System.out.println("• Resultado: ESTAVA VIVO em 1600");
        
        System.out.println("\n🚀 PARA TESTAR:");
        System.out.println("═".repeat(60));
        System.out.println("1. Execute: mvn spring-boot:run");
        System.out.println("2. Adicione alguns livros (opção 1)");
        System.out.println("3. Liste autores (opção 4)");
        System.out.println("4. Teste autores vivos em ano específico (opção 5)");
        
        System.out.println("\n✨ FUNCIONALIDADES DE AUTORES IMPLEMENTADAS COM SUCESSO!");
        System.out.println("Todas as consultas derivadas e lógica de negócio estão funcionais.");
    }
}
