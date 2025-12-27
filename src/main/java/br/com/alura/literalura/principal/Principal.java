package br.com.alura.literalura.principal;

import br.com.alura.literalura.dto.DadosGutendexDto;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private static final String ENDERECO_GUTENDEX = "https://gutendex.com/books/?search=";

    private final ConsumoApi consumoApi;
    private final LivroService livroService;
    private final AutorRepository autorRepository;
    private final Scanner scanner = new Scanner(System.in);

    public Principal(ConsumoApi consumoApi, LivroService livroService, AutorRepository autorRepository) {
        this.consumoApi = consumoApi;
        this.livroService = livroService;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("""
                    
                    ╔═══════════════════════════════════╗
                    ║        LITERALURA - MENU          ║
                    ╚═══════════════════════════════════╝
                    
                    1 - Buscar livro por título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros por idioma
                    6 - Estatísticas de livros por idioma
                    0 - Sair
                    
                    Escolha uma opção: """);

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> buscarLivroPorTitulo();
                    case 2 -> listarLivros();
                    case 3 -> listarAutores();
                    case 4 -> listarAutoresVivosPorAno();
                    case 5 -> listarLivrosPorIdioma();
                    case 6 -> exibirEstatisticasPorIdioma();
                    case 0 -> System.out.println("\n Encerrando aplicação...");
                    default -> System.out.println(" Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println(" Erro: Digite apenas números!");
                scanner.nextLine();
                opcao = -1;
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.print("\n Digite o título do livro: ");
        String titulo = scanner.nextLine();

        if (titulo.trim().isEmpty()) {
            System.out.println(" Título não pode ser vazio!");
            return;
        }

        System.out.println(" Buscando no banco local...");
        List<Livro> livrosLocal = livroService.buscarPorTitulo(titulo);

        if (!livrosLocal.isEmpty()) {
            System.out.println("\n Encontrado(s) " + livrosLocal.size() + " livro(s) no banco local:\n");
            livrosLocal.forEach(System.out::println);

            System.out.print("\nDeseja buscar mais livros na API? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();

            if (!resposta.equals("s")) {
                return;
            }
        }

        System.out.println("🔍 Buscando na API Gutendex...");
        String endereco = ENDERECO_GUTENDEX + titulo.replace(" ", "%20");

        try {
            String json = consumoApi.obterDados(endereco);
            ObjectMapper mapper = new ObjectMapper();
            DadosGutendexDto dados = mapper.readValue(json, DadosGutendexDto.class);

            if (dados.results().isEmpty()) {
                System.out.println(" Nenhum livro encontrado na API.");
                return;
            }

            var resultadosFiltrados = dados.results().stream()
                    .filter(livro -> contemPalavraCompleta(livro.title(), titulo))
                    .toList();

            if (resultadosFiltrados.isEmpty()) {
                System.out.println(" Nenhum livro encontrado com a palavra '" + titulo + "' no título.");
                return;
            }

            DadosGutendexDto primeiroLivro = new DadosGutendexDto(
                    List.of(resultadosFiltrados.get(0))
            );

            livroService.salvarLivros(primeiroLivro);
            System.out.println(" Livro da API salvo com sucesso!");

        } catch (Exception e) {
            System.out.println(" Erro ao processar os dados: " + e.getMessage());
        }
    }

    private boolean contemPalavraCompleta(String titulo, String palavra) {
        if (titulo == null || palavra == null) {
            return false;
        }

        String tituloLower = titulo.toLowerCase();
        String palavraLower = palavra.toLowerCase();

        return tituloLower.equals(palavraLower) ||
                tituloLower.startsWith(palavraLower + " ") ||
                tituloLower.endsWith(" " + palavraLower) ||
                tituloLower.contains(" " + palavraLower + " ");
    }

    private void listarLivros() {
        System.out.println("\n === LIVROS REGISTRADOS ===");

        List<Livro> livros = livroService.buscarTodos();

        if (livros.isEmpty()) {
            System.out.println(" Nenhum livro registrado no banco de dados.");
            System.out.println(" Dica: Use a opção 1 para buscar livros.");
        } else {
            System.out.println("Total de livros: " + livros.size() + "\n");
            livros.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        System.out.println("\n === AUTORES REGISTRADOS ===");

        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println(" Nenhum autor registrado no banco de dados.");
        } else {
            System.out.println(" Total de autores: " + autores.size() + "\n");
            autores.forEach(a -> System.out.println("- " + a.getNome() +
                    " (Nascimento: " + a.getAnoNascimento() +
                    ", Falecimento: " + a.getAnoFalecimento() + ")"));
        }
    }

    private void listarAutoresVivosPorAno() {
        System.out.print("\n Digite o ano: ");

        try {
            int ano = scanner.nextInt();
            scanner.nextLine();

            if (ano < 0 || ano > 2025) {
                System.out.println(" Ano inválido!");
                return;
            }

            List<Autor> autores = autorRepository.findAutoresVivosNoAno(ano);

            if (autores.isEmpty()) {
                System.out.println(" Nenhum autor vivo no ano " + ano + " foi encontrado no banco.");
            } else {
                System.out.println("\n Autores vivos em " + ano + ":");
                autores.forEach(a -> System.out.println("- " + a.getNome() +
                        " (" + a.getAnoNascimento() + " - " +
                        (a.getAnoFalecimento() != null ? a.getAnoFalecimento() : "atualidade") + ")"));
            }
        } catch (InputMismatchException e) {
            System.out.println(" Erro: Digite um ano válido!");
            scanner.nextLine();
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("\n Idiomas disponíveis no banco:");
        List<String> idiomas = livroService.listarIdiomasDisponiveis();

        if (idiomas.isEmpty()) {
            System.out.println(" Nenhum idioma registrado.");
            return;
        }

        idiomas.forEach(i -> System.out.println("- " + i));

        System.out.print("\nDigite o código do idioma (ex: en, pt, es, fr): ");
        String idioma = scanner.nextLine().trim().toLowerCase();

        if (idioma.isEmpty()) {
            System.out.println(" Idioma não pode ser vazio!");
            return;
        }

        List<Livro> livros = livroService.buscarPorIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println(" Nenhum livro encontrado no idioma '" + idioma + "'");
        } else {
            System.out.println("\n Livros em " + idioma + " (" + livros.size() + " encontrado(s)):\n");
            livros.forEach(System.out::println);
        }
    }

    private void exibirEstatisticasPorIdioma() {
        System.out.println("\n === ESTATÍSTICAS POR IDIOMA ===\n");

        List<String> idiomas = livroService.listarIdiomasDisponiveis();

        if (idiomas.isEmpty()) {
            System.out.println(" Nenhum dado disponível para estatísticas.");
            return;
        }

        System.out.println("Idioma | Quantidade de Livros");
        System.out.println("-------+--------------------");

        idiomas.forEach(idioma -> {
            Long quantidade = livroService.contarLivrosPorIdioma(idioma);
            System.out.printf("%-6s | %d livro(s)%n", idioma, quantidade);
        });

        // Total geral
        long total = idiomas.stream()
                .mapToLong(livroService::contarLivrosPorIdioma)
                .sum();

        System.out.println("-------+--------------------");
        System.out.printf("TOTAL  | %d livro(s)%n", total);
    }
}