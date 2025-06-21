package org.example.challengeliteralura.principal;

import org.example.challengeliteralura.dto.AutorDTO;
import org.example.challengeliteralura.dto.GutenbergApiResponseDTO;
import org.example.challengeliteralura.dto.LivroDTO;
import org.example.challengeliteralura.model.Autor;
import org.example.challengeliteralura.model.Livro;
import org.example.challengeliteralura.repository.AutorRepository;
import org.example.challengeliteralura.repository.LivroRepository;
import org.example.challengeliteralura.service.ConsumoAPI;
import org.example.challengeliteralura.service.ConverterDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final ConsumoAPI consumo = new ConsumoAPI();
    private final ConverterDados conversor = new ConverterDados();

    public Principal(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            String menu =
                    """
                    -------------
                    Escolha uma das opções
                    1 - buscar livro pelo título
                    2 - buscar livros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos em um determinado ano
                    5 - listar livros em um determinado idioma
                    6 - listar os dez livros mais baixados
                    7 - buscar autores por nome
                    
                    0 - sair
                    """;

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> buscarLivrosRegistrados();
                case 3 -> buscarAutoresRegistrados();
                case 4 -> buscarAutoresVivosEmDeterminadoAno();
                case 5 -> buscarLivrosPorIdioma();
                case 6 -> buscarTop10LivrosMaisBaixados();
                case 7 -> buscarAutoresPorNome();

                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        GutenbergApiResponseDTO dadosLivro = obterDadosLivro();

        if (dadosLivro.count() == 0) {
            System.out.println("\nNenhum livro com esse título foi encontrado.");
            return;
        }

        for (LivroDTO livroDTO : dadosLivro.results()) {
            if (livroRepository.findByTitulo(livroDTO.titulo()).isPresent()) {
                System.out.println("Livro '" + livroDTO.titulo() + "' já existe no banco. Pulando.");
                continue;
            }

            Autor autor;
            if (livroDTO.autores() != null && !livroDTO.autores().isEmpty()) {
                AutorDTO autorDTO = livroDTO.autores().get(0);

                Optional<Autor> autorExistente = autorRepository.findByNomeIgnoreCase(autorDTO.nome());
                if (autorExistente.isPresent()) {
                    System.out.println("Autor '" + autorDTO.nome() + "' encontrado no banco.");
                    autor = autorExistente.get();
                } else {
                    autor = new Autor(autorDTO);
                    autorRepository.save(autor);
                    System.out.println("Novo autor '" + autorDTO.nome() + "' salvo.");
                }
            } else {
                System.out.println("Livro sem autor principal no DTO: " + livroDTO.titulo());
                continue;
            }

            Livro livro = new Livro(livroDTO, autor);
            autor.addLivro(livro);
            livroRepository.save(livro);
            exibirDadosLivro(livro);
            break;
        }
    }

    private void buscarLivrosRegistrados() {
        List<Livro> livrosRegistrados = livroRepository.findAll();

        for (Livro livro : livrosRegistrados) {
            exibirDadosLivro(livro);
        }
    }

    private void buscarAutoresRegistrados() {
        List<Autor> autoresRegistrados = autorRepository.buscarAutoresRegistrados();

        autoresRegistrados.forEach(this::exibirDadosAutor);
    }

    private void buscarAutoresVivosEmDeterminadoAno() {
        System.out.print("Digite o ano que deseja buscar: ");
        int ano = scanner.nextInt();
        List<Autor> autoresVivosEmDeterminadoAno = autorRepository.findAutoresVivosNoAnoJPQL(ano);

        autoresVivosEmDeterminadoAno.forEach(this::exibirDadosAutor);
    }

    private void buscarLivrosPorIdioma() {
        String menu =
                """
                Digite o idioma que você deseja buscar:
                pt - português
                es - espanhol
                en - inglês
                fr - francês
                """;

        System.out.println(menu);
        String idioma = scanner.nextLine();
        List<Livro> livrosPorIdioma = livroRepository.findAllByIdiomaContainingIgnoreCase(idioma);

        if (!livrosPorIdioma.isEmpty()) {
            livrosPorIdioma.forEach(this::exibirDadosLivro);
        } else {
            System.out.println("Não existem livros nesse idioma no banco de dados.");
        }
    }

    private void buscarTop10LivrosMaisBaixados() {
        List<Livro> top10LivrosMaisBaixados = livroRepository.buscarTop10LivrosMaisBaixados();

        top10LivrosMaisBaixados.forEach(this::exibirDadosLivro);
    }

    private void buscarAutoresPorNome() {
        System.out.print("Digite o nome do autor que deseja buscar: ");
        String nomeAutor = scanner.nextLine();

        Optional<Autor> autoresBuscados = autorRepository.findByNomeIgnoreCase(nomeAutor);
        if (autoresBuscados.isPresent()) {
            autoresBuscados.stream()
                    .toList()
                    .forEach(this::exibirDadosAutor);
        } else {
            System.out.println("Autor não encontrado no banco de dados.");
        }
    }

    private GutenbergApiResponseDTO obterDadosLivro() {
        System.out.print("Digite o nome do livro que você deseja pesquisar: ");
        String nomeLivro = scanner.nextLine().replace(" ", "+");
        String json = consumo.obterDados(nomeLivro);
        return conversor.obterDados(json, GutenbergApiResponseDTO.class);
    }

    private void exibirDadosAutor(Autor autor) {
        System.out.println("Nome: " + autor.getNome());
        System.out.println("Ano de nascimento: " + autor.getAnoNascimento());
        System.out.println("Ano de falecimento: " + autor.getAnoFalecimento());

        System.out.print("Livros: [");
        autor.getLivros().forEach(l -> System.out.print(l.getTitulo()));
        System.out.println("]\n");
    }

    private void exibirDadosLivro(Livro livro) {
        System.out.println("---------- LIVRO ----------");
        System.out.println(livro);
        System.out.println("---------------------------\n");
    }
}
