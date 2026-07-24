package br.com.academia.alunos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * Representa o registro persistido de um aluno da academia.
 *
 * <p>A classe mantém apenas o formato de armazenamento. As validações de
 * entrada ficam no DTO e as regras de negócio permanecem no serviço.</p>
 */
@Entity
@Table(name = "alunos")
public class Aluno {

    /** Chave primária gerada automaticamente pelo banco. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** O tamanho da coluna reforça no banco o mesmo limite usado pela API. */
    @Column(nullable = false, length = 100)
    private String nomeCompleto;

    /** A unicidade também é protegida no banco contra condições de corrida. */
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(length = 11)
    private String telefone;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    /** STRING mantém no banco nomes legíveis como MENSAL, em vez de números. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PlanoAcademia plano;

    @Column(nullable = false)
    private LocalDate dataMatricula;

    @Column(nullable = false)
    private Boolean ativo;

    /**
     * Construtor vazio exigido pela especificação JPA.
     */
    public Aluno() {
    }

    /**
     * Cria um aluno com todos os dados de domínio, exceto o ID automático.
     *
     * @param nomeCompleto nome do aluno
     * @param cpf CPF contendo somente números
     * @param email e-mail do aluno
     * @param telefone telefone opcional contendo somente números
     * @param dataNascimento data de nascimento
     * @param plano plano contratado
     * @param dataMatricula data da matrícula
     * @param ativo situação atual
     */
    public Aluno(String nomeCompleto, String cpf, String email, String telefone,
                 LocalDate dataNascimento, PlanoAcademia plano,
                 LocalDate dataMatricula, Boolean ativo) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.plano = plano;
        this.dataMatricula = dataMatricula;
        this.ativo = ativo;
    }

    /** @return identificador gerado pelo banco */
    public Long getId() {
        return id;
    }

    /** @param id identificador usado pela JPA ao materializar a entidade */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return nome completo persistido */
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    /** @param nomeCompleto novo nome completo */
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    /** @return CPF armazenado sem máscara */
    public String getCpf() {
        return cpf;
    }

    /** @param cpf CPF normalizado pelo serviço */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /** @return e-mail normalizado */
    public String getEmail() {
        return email;
    }

    /** @param email e-mail normalizado pelo serviço */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return telefone sem máscara ou {@code null} */
    public String getTelefone() {
        return telefone;
    }

    /** @param telefone telefone opcional normalizado */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /** @return data de nascimento */
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    /** @param dataNascimento data de nascimento validada */
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /** @return plano contratado */
    public PlanoAcademia getPlano() {
        return plano;
    }

    /** @param plano plano selecionado */
    public void setPlano(PlanoAcademia plano) {
        this.plano = plano;
    }

    /** @return data em que a matrícula foi registrada */
    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    /** @param dataMatricula data de matrícula válida */
    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    /** @return {@code true} quando a matrícula está ativa */
    public Boolean getAtivo() {
        return ativo;
    }

    /** @param ativo situação atual da matrícula */
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
