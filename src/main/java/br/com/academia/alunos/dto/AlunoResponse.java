package br.com.academia.alunos.dto;

import br.com.academia.alunos.entity.PlanoAcademia;

import java.time.LocalDate;

/**
 * Representação devolvida pela API para a interface e outros consumidores.
 */
public class AlunoResponse {

    private Long id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private PlanoAcademia plano;
    private LocalDate dataMatricula;
    private Boolean ativo;

    /**
     * Construtor vazio preserva compatibilidade com ferramentas de serialização.
     */
    public AlunoResponse() {
    }

    /**
     * Monta a resposta completa de um aluno.
     *
     * @param id identificador
     * @param nomeCompleto nome completo
     * @param cpf CPF sem máscara
     * @param email e-mail
     * @param telefone telefone sem máscara
     * @param dataNascimento data de nascimento
     * @param plano plano contratado
     * @param dataMatricula data de matrícula
     * @param ativo situação atual
     */
    public AlunoResponse(Long id, String nomeCompleto, String cpf, String email,
                         String telefone, LocalDate dataNascimento,
                         PlanoAcademia plano, LocalDate dataMatricula,
                         Boolean ativo) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.plano = plano;
        this.dataMatricula = dataMatricula;
        this.ativo = ativo;
    }

    /** @return identificador enviado no JSON */
    public Long getId() {
        return id;
    }

    /** @param id identificador a serializar */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return nome completo enviado no JSON */
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    /** @param nomeCompleto nome a serializar */
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    /** @return CPF sem máscara enviado no JSON */
    public String getCpf() {
        return cpf;
    }

    /** @param cpf CPF a serializar */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /** @return e-mail enviado no JSON */
    public String getEmail() {
        return email;
    }

    /** @param email e-mail a serializar */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return telefone sem máscara ou {@code null} */
    public String getTelefone() {
        return telefone;
    }

    /** @param telefone telefone opcional a serializar */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /** @return data de nascimento no formato ISO da API */
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    /** @param dataNascimento data a serializar */
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /** @return plano enviado no JSON */
    public PlanoAcademia getPlano() {
        return plano;
    }

    /** @param plano plano a serializar */
    public void setPlano(PlanoAcademia plano) {
        this.plano = plano;
    }

    /** @return data de matrícula no formato ISO da API */
    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    /** @param dataMatricula data a serializar */
    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    /** @return situação enviada no JSON */
    public Boolean getAtivo() {
        return ativo;
    }

    /** @param ativo situação a serializar */
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
