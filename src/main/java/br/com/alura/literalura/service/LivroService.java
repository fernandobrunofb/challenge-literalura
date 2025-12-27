package br.com.alura.literalura.service;

import br.com.alura.literalura.dto.DadosGutendexDto;
import br.com.alura.literalura.dto.DadosLivroDto;
import br.com.alura.literalura.dto.DadosAutorDto;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    @Transactional
    public void salvarLivros(DadosGutendexDto dadosGutendex) {
        if (dadosGutendex.results().isEmpty()) {
            System.out.println("Nenhum livro encontrado na busca.");
            return;
        }

        for (DadosLivroDto dtoLivro : dadosGutendex.results()) {

            if (livroRepository.findByTituloIgnoreCase(dtoLivro.title()).isPresent()) {
                System.out.println("Livro já cadastrado: " + dtoLivro.title());
                continue;
            }


            if (dtoLivro.authors().isEmpty()) {
                System.out.println("Livro sem autor, pulando: " + dtoLivro.title());
                continue;
            }


            DadosAutorDto dtoAutor = dtoLivro.authors().get(0);
            Autor autor = buscarOuCriarAutor(dtoAutor);


            String idioma = dtoLivro.languages().isEmpty() ? "desconhecido" : dtoLivro.languages().get(0);


            Livro livro = new Livro(
                    dtoLivro.title(),
                    dtoLivro.downloadCount() != null ? dtoLivro.downloadCount() : 0,
                    idioma,
                    autor
            );

            livroRepository.save(livro);
            System.out.println("✓ Livro salvo: " + livro.getTitulo());
        }
    }


    private Autor buscarOuCriarAutor(DadosAutorDto dtoAutor) {
        return autorRepository.findByNomeIgnoreCase(dtoAutor.name())
                .orElseGet(() -> {
                    Autor novoAutor = new Autor(
                            dtoAutor.name(),
                            dtoAutor.anoNascimento(),
                            dtoAutor.anoFalecimento()
                    );
                    return autorRepository.save(novoAutor);
                });
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return buscarTodos();
        }
        return livroRepository.findByPalavraNoTitulo(titulo.trim());
    }

    public List<Livro> buscarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> buscarPorIdioma(String idioma) {
        return livroRepository.findByIdioma(idioma);
    }

    public Long contarLivrosPorIdioma(String idioma) {
        return livroRepository.countByIdioma(idioma);
    }

    public List<String> listarIdiomasDisponiveis() {
        return livroRepository.findDistinctIdiomas();
    }
}