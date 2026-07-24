package br.com.academia.alunos.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Converte exceções em respostas JSON consistentes para toda a API.
 *
 * <p>{@link RestControllerAdvice} intercepta falhas lançadas pelos controllers.
 * Assim, cada endpoint permanece pequeno e o usuário recebe mensagens úteis em
 * vez de detalhes técnicos do servidor.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Responde 404 quando o identificador solicitado não existe.
     *
     * @param excecao falha gerada pelo serviço
     * @param request requisição atual, usada para informar o caminho
     * @return corpo de erro e estado HTTP 404
     */
    @ExceptionHandler(AlunoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> tratarAlunoNaoEncontrado(
            AlunoNaoEncontradoException excecao, HttpServletRequest request) {
        return criarResposta(HttpStatus.NOT_FOUND, excecao.getMessage(), request);
    }

    /**
     * Responde 409 quando CPF ou e-mail já pertence a outro aluno.
     *
     * @param excecao descrição do conflito
     * @param request requisição atual
     * @return corpo de erro e estado HTTP 409
     */
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ErroResposta> tratarRecursoDuplicado(
            RecursoDuplicadoException excecao, HttpServletRequest request) {
        return criarResposta(HttpStatus.CONFLICT, excecao.getMessage(), request);
    }

    /**
     * Organiza todas as mensagens de Bean Validation pelo nome do campo.
     *
     * @param excecao resultado da validação automática do DTO
     * @param request requisição atual
     * @return corpo detalhado e estado HTTP 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarValidacao(
            MethodArgumentNotValidException excecao, HttpServletRequest request) {
        ErroResposta erro = criarErro(
                HttpStatus.BAD_REQUEST,
                "Corrija os campos destacados para continuar.",
                request
        );

        Map<String, String> campos = new LinkedHashMap<>();
        excecao.getBindingResult().getFieldErrors().forEach(fieldError ->
                campos.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage())
        );
        erro.setErrosValidacao(campos);
        return ResponseEntity.badRequest().body(erro);
    }

    /**
     * Trata JSON inválido, datas fora do formato ISO e opções de plano desconhecidas.
     *
     * @param excecao falha de leitura do JSON
     * @param request requisição atual
     * @return orientação amigável com estado HTTP 400
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> tratarJsonInvalido(
            HttpMessageNotReadableException excecao, HttpServletRequest request) {
        return criarResposta(
                HttpStatus.BAD_REQUEST,
                "Não foi possível interpretar os dados. Confira datas e opções informadas.",
                request
        );
    }

    /**
     * Protege a resposta quando a restrição única do banco detecta um conflito.
     *
     * <p>A checagem principal acontece antes no serviço. Esta captura é uma
     * proteção complementar para duas gravações simultâneas.</p>
     *
     * @param excecao falha de integridade do banco
     * @param request requisição atual
     * @return mensagem de conflito com estado HTTP 409
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResposta> tratarIntegridade(
            DataIntegrityViolationException excecao, HttpServletRequest request) {
        return criarResposta(
                HttpStatus.CONFLICT,
                "Já existe um aluno com o CPF ou e-mail informado.",
                request
        );
    }

    /**
     * Impede que uma falha imprevista exponha informações internas da aplicação.
     *
     * @param excecao erro não tratado de forma mais específica
     * @param request requisição atual
     * @return mensagem segura com estado HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> tratarErroInterno(
            Exception excecao, HttpServletRequest request) {
        return criarResposta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um problema interno. Tente novamente em alguns instantes.",
                request
        );
    }

    /**
     * Constrói a entidade HTTP evitando repetição entre os tratamentos.
     */
    private ResponseEntity<ErroResposta> criarResposta(
            HttpStatus status, String mensagem, HttpServletRequest request) {
        return ResponseEntity.status(status).body(criarErro(status, mensagem, request));
    }

    /**
     * Monta os metadados comuns exigidos no contrato de erro.
     */
    private ErroResposta criarErro(
            HttpStatus status, String mensagem, HttpServletRequest request) {
        return new ErroResposta(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                request.getRequestURI()
        );
    }
}
