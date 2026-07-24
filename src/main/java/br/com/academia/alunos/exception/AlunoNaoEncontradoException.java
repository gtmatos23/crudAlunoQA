package br.com.academia.alunos.exception;

/**
 * Indica que uma operação solicitou um aluno que não existe.
 *
 * <p>Uma exceção específica permite ao tratamento global devolver 404 sem
 * misturar detalhes HTTP à camada de serviço.</p>
 */
public class AlunoNaoEncontradoException extends RuntimeException {

    /**
     * Cria a exceção com uma orientação compreensível para quem usa a API.
     *
     * @param id identificador que não foi encontrado
     */
    public AlunoNaoEncontradoException(Long id) {
        super("Não foi encontrado um aluno com o ID " + id + ".");
    }
}
