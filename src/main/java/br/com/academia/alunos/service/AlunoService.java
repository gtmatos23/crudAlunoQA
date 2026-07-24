package br.com.academia.alunos.service;

import br.com.academia.alunos.dto.AlunoRequest;
import br.com.academia.alunos.dto.AlunoResponse;
import br.com.academia.alunos.entity.Aluno;
import br.com.academia.alunos.exception.AlunoNaoEncontradoException;
import br.com.academia.alunos.exception.RecursoDuplicadoException;
import br.com.academia.alunos.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Centraliza as regras do caso de uso de gestão de alunos.
 *
 * <p>{@link Service} registra a classe no Spring. As transações garantem que
 * cada operação de escrita seja concluída por inteiro ou desfeita em caso de
 * falha.</p>
 */
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    /**
     * A injeção pelo construtor deixa a dependência explícita e imutável.
     *
     * @param alunoRepository acesso aos dados dos alunos
     */
    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    /**
     * Cadastra um novo aluno aplicando padrões e regras de unicidade.
     *
     * @param request dados validados pelo controller
     * @return aluno criado, incluindo o ID automático
     */
    @Transactional
    public AlunoResponse cadastrar(AlunoRequest request) {
        String cpf = normalizarCpf(request.getCpf());
        String email = normalizarEmail(request.getEmail());
        verificarDuplicidadeNoCadastro(cpf, email);

        // Regras de padrão: matrícula hoje e situação ativa quando omitidas.
        LocalDate dataMatricula = request.getDataMatricula() != null
                ? request.getDataMatricula()
                : LocalDate.now();
        Boolean ativo = request.getAtivo() != null ? request.getAtivo() : Boolean.TRUE;

        Aluno aluno = new Aluno(
                request.getNomeCompleto().trim(),
                cpf,
                email,
                normalizarTelefone(request.getTelefone()),
                request.getDataNascimento(),
                request.getPlano(),
                dataMatricula,
                ativo
        );

        return converterParaResponse(alunoRepository.save(aluno));
    }

    /**
     * Lista os alunos por ID crescente para produzir uma ordem previsível.
     *
     * @return lista de respostas, possivelmente vazia
     */
    @Transactional(readOnly = true)
    public List<AlunoResponse> listarTodos() {
        return alunoRepository.findAllByOrderByIdAsc()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    /**
     * Busca um aluno específico ou informa claramente que ele não existe.
     *
     * @param id identificador procurado
     * @return aluno encontrado
     */
    @Transactional(readOnly = true)
    public AlunoResponse buscarPorId(Long id) {
        return converterParaResponse(buscarEntidade(id));
    }

    /**
     * Atualiza os dados sem permitir a troca do ID.
     *
     * <p>A entidade é carregada pelo ID da URL e somente os campos editáveis
     * são copiados. Valores nulos opcionais preservam matrícula e situação
     * anteriores durante a edição.</p>
     *
     * @param id identificador imutável recebido pela URL
     * @param request novos dados do aluno
     * @return aluno atualizado
     */
    @Transactional
    public AlunoResponse atualizar(Long id, AlunoRequest request) {
        Aluno aluno = buscarEntidade(id);
        String cpf = normalizarCpf(request.getCpf());
        String email = normalizarEmail(request.getEmail());
        verificarDuplicidadeNaAtualizacao(id, cpf, email);

        aluno.setNomeCompleto(request.getNomeCompleto().trim());
        aluno.setCpf(cpf);
        aluno.setEmail(email);
        aluno.setTelefone(normalizarTelefone(request.getTelefone()));
        aluno.setDataNascimento(request.getDataNascimento());
        aluno.setPlano(request.getPlano());

        if (request.getDataMatricula() != null) {
            aluno.setDataMatricula(request.getDataMatricula());
        }
        if (request.getAtivo() != null) {
            aluno.setAtivo(request.getAtivo());
        }

        return converterParaResponse(alunoRepository.save(aluno));
    }

    /**
     * Exclui fisicamente um aluno existente.
     *
     * @param id identificador do registro a remover
     */
    @Transactional
    public void excluir(Long id) {
        Aluno aluno = buscarEntidade(id);
        alunoRepository.delete(aluno);
    }

    /**
     * Busca a entidade para reutilizar a mesma regra de 404.
     */
    private Aluno buscarEntidade(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException(id));
    }

    /**
     * Impede o cadastro de valores únicos já utilizados.
     */
    private void verificarDuplicidadeNoCadastro(String cpf, String email) {
        if (alunoRepository.existsByCpf(cpf)) {
            throw new RecursoDuplicadoException("Já existe um aluno com o CPF informado.");
        }
        if (alunoRepository.existsByEmailIgnoreCase(email)) {
            throw new RecursoDuplicadoException("Já existe um aluno com o e-mail informado.");
        }
    }

    /**
     * Na edição, desconsidera o próprio aluno ao verificar CPF e e-mail.
     */
    private void verificarDuplicidadeNaAtualizacao(Long id, String cpf, String email) {
        if (alunoRepository.existsByCpfAndIdNot(cpf, id)) {
            throw new RecursoDuplicadoException("Já existe um aluno com o CPF informado.");
        }
        if (alunoRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new RecursoDuplicadoException("Já existe um aluno com o e-mail informado.");
        }
    }

    /**
     * Mantém o CPF no formato persistido pelo contrato da API.
     */
    private String normalizarCpf(String cpf) {
        return cpf == null ? null : cpf.trim();
    }

    /**
     * E-mails são normalizados para evitar duplicidade causada apenas por caixa.
     */
    private String normalizarEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    /**
     * Converte texto vazio de telefone em nulo, simplificando a ausência do dado.
     */
    private String normalizarTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            return null;
        }
        return telefone.trim();
    }

    /**
     * Isola a conversão para que a entidade JPA nunca seja devolvida diretamente.
     */
    private AlunoResponse converterParaResponse(Aluno aluno) {
        return new AlunoResponse(
                aluno.getId(),
                aluno.getNomeCompleto(),
                aluno.getCpf(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getDataNascimento(),
                aluno.getPlano(),
                aluno.getDataMatricula(),
                aluno.getAtivo()
        );
    }
}
