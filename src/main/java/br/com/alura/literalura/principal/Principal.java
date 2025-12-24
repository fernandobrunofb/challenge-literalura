package br.com.alura.literalura.principal;

import br.com.alura.literalura.service.ConsumoApi;

import java.util.Scanner;

public class Principal {

    private static final String ENDERECO_GUTENDEX = "https://gutendex.com/books/?search=";

    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();

    public void exibeMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("""
                    \n===== LITERALURA =====
                    1 - Buscar livro por título
                    2 - Listar livros registrados
                    3 - Listar autores
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros por idioma
                    0 - Sair
                    """);

            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;

                case 2:
                    System.out.println("Listar livros registrados");
                    break;

                case 3:
                    System.out.println("Listar autores");
                    break;

                case 4:
                    System.out.println("Listar autores vivos em determinado ano");
                    break;

                case 5:
                    System.out.println("Listar livros por idioma");
                    break;

                case 0:
                    System.out.println("Encerrando aplicação...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();

        String endereco = ENDERECO_GUTENDEX + titulo.replace(" ", "%20");

        String json = consumoApi.obterDados(endereco);

        System.out.println(json);
    }

    private void listarLivros() {
        System.out.println("Funcionalidade ainda não implementada.");
    }

    private void listarAutores() {
        System.out.println("Funcionalidade ainda não implementada.");
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Funcionalidade ainda não implementada.");
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Funcionalidade ainda não implementada.");
    }
}
