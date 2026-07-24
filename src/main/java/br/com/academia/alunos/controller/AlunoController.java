package br.com.academia.alunos.controller;

import br.com.academia.alunos.dto.AlunoRequest;
import br.com.academia.alunos.dto.AlunoResponse;
import br.com.academia.alunos.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Expõe as operações REST de alunos no endereço {@code /api/alunos}.
 *
 * <p>O controller traduz HTTP para chamadas do serviço. Regras de negócio não
 * ficam nesta camada.</p>
 */
@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    /**
     * Recebe o serviço por injeção de dependência.
     *
     * @param alunoService caso de uso de gestão dos alunos
     */
    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    /**
     * Cadastra um aluno e responde 201 Created.
     *
     * <p>{@link Valid} executa as anotações de validação do DTO antes de o
     * método chamar o serviço.</p>
     *
     * @param request JSON convertido para DTO
     * @return aluno criado
     */
    @PostMapping
    public ResponseEntity<AlunoResponse> cadastrar(@Valid @RequestBody AlunoRequest request) {
        AlunoResponse alunoCriado = alunoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoCriado);
    }

    /**
     * Lista todos os alunos em ordem crescente de ID.
     *
     * @return lista e estado 200
     */
    @GetMapping
    public ResponseEntity<List<AlunoResponse>> listarTodos() {
        return ResponseEntity.ok(alunoService.listarTodos());
    }

    /**
     * Busca um aluno pelo identificador da URL.
     *
     * @param id identificador numérico
     * @return aluno e estado 200, ou erro 404 pelo tratamento global
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.buscarPorId(id));
    }

    /**
     * Atualiza o registro indicado pela URL, mantendo seu ID.
     *
     * @param id identificador do aluno
     * @param request novos dados validados
     * @return aluno atualizado e estado 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponse> atualizar(
            @PathVariable Long id, @Valid @RequestBody AlunoRequest request) {
        return ResponseEntity.ok(alunoService.atualizar(id, request));
    }

    /**
     * Exclui o registro e responde sem corpo.
     *
     * @param id identificador do aluno
     * @return estado 204 quando a exclusão termina
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        alunoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
