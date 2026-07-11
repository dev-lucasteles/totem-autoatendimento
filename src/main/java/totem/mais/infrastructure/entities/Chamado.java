package totem.mais.infrastructure.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "chamados_ti")
public class Chamado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFuncionario;
    private String setor;
    private String problema;

    // O Spring/Hibernate exige um construtor vazio
    public Chamado() {}

    public Chamado(String nomeFuncionario, String setor, String problema) {
        this.nomeFuncionario = nomeFuncionario;
        this.setor = setor;
        this.problema = problema;
    }

    // Getters
    public Long getId() { return id; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public String getSetor() { return setor; }
    public String getProblema() { return problema; }
}