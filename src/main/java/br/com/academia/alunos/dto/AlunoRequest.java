package br.com.academia.alunos.dto;

import br.com.academia.alunos.entity.PlanoAcademia;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Dados aceitos nas operações de cadastro e atualização.
 *
 * <p>O DTO impede que detalhes internos da entidade sejam expostos diretamente
 * e concentra as validações estruturais executadas antes do serviço.</p>
 */
public class AlunoRequest {

    @NotBlank(message = "O nome completo é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome completo deve ter entre 3 e 100 caracteres.")
    private String nomeCompleto;

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 números.")
    private String cpf;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 254, message = "O e-mail deve ter no máximo 254 caracteres.")
    private String email;

    @Pattern(regexp = "^$|\\d{10,11}", message = "O telefone deve conter 10 ou 11 números.")
    private String telefone;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser anterior à data atual.")
    private LocalDate dataNascimento;

    @NotNull(message = "O plano é obrigatório.")
    private PlanoAcademia plano;

    @PastOrPresent(message = "A data de matrícula não pode estar no futuro.")
    private LocalDate dataMatricula;

    /** Boolean aceita nulo para que o serviço aplique o padrão de novo aluno ativo. */
    private Boolean ativo;

    /**
     * Construtor vazio usado pelo Jackson ao converter JSON em objeto Java.
     */
    public AlunoRequest() {
    }

    /** @return nome recebido para validação */
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    /** @param nomeCompleto valor preenchido no JSON */
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    /** @return CPF recebido sem máscara */
    public String getCpf() {
        return cpf;
    }

    /** @param cpf valor preenchido no JSON */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /** @return e-mail recebido */
    public String getEmail() {
        return email;
    }

    /** @param email valor preenchido no JSON */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return telefone recebido ou {@code null} */
    public String getTelefone() {
        return telefone;
    }

    /** @param telefone valor opcional preenchido no JSON */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /** @return data de nascimento recebida */
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    /** @param dataNascimento valor convertido do formato ISO */
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /** @return plano recebido */
    public PlanoAcademia getPlano() {
        return plano;
    }

    /** @param plano opção convertida do texto JSON */
    public void setPlano(PlanoAcademia plano) {
        this.plano = plano;
    }

    /** @return data de matrícula recebida ou {@code null} */
    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    /** @param dataMatricula valor opcional convertido do formato ISO */
    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    /** @return situação recebida ou {@code null} quando omitida */
    public Boolean getAtivo() {
        return ativo;
    }

    /** @param ativo situação opcional preenchida no JSON */
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
