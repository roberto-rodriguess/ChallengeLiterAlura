package org.example.challengeliteralura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.example.challengeliteralura.dto.LivroDTO;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    //@ElementCollection(fetch = FetchType.EAGER) // Para List<String> (idiomas)
    //@CollectionTable(name = "livro_idiomas", joinColumns = @JoinColumn(name = "livro_id"))
    //@Column(name = "idioma")
    private String idioma;

    private Integer quantidadeDownloads;

    public Livro() {}

    public Livro(LivroDTO dadosLivro, Autor autor) {
        this.titulo = dadosLivro.titulo();
        this.autor = autor;
        this.idioma = dadosLivro.idiomas().toString();
        this.quantidadeDownloads = dadosLivro.quantidadeDownloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getQuantidadeDownloads() {
        return quantidadeDownloads;
    }

    public void setQuantidadeDownloads(Integer quantidadeDownloads) {
        this.quantidadeDownloads = quantidadeDownloads;
    }

    @Override
    public String toString() {
        return String.format("Titulo: %s%nAutor: %s%nIdioma: %s%nNúmero de downloads: %d",
                titulo, autor.getNome(), idioma, quantidadeDownloads);
    }
}
