package br.com.academia.alunos.exception;

/**
 * Indica conflito com um valor que deve ser único, como CPF ou e-mail.
 */
public class RecursoDuplicadoException extends RuntimeException {

    /**
     * Cria a exceção com a mensagem que orientará a correção.
     *
     * @param mensagem explicação do dado duplicado
     */
    public RecursoDuplicadoException(String mensagem) {
        super(mensagem);
    }
}
