package br.com.academia.alunos.repository;

import br.com.academia.alunos.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Porta de acesso aos registros de alunos.
 *
 * <p>O Spring Data interpreta os nomes dos métodos e cria as consultas
 * automaticamente, permitindo que a aula concentre a regra no serviço.</p>
 */
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    /**
     * Verifica se qualquer aluno já utiliza o CPF informado.
     *
     * @param cpf CPF sem máscara
     * @return {@code true} quando o CPF já existe
     */
    boolean existsByCpf(String cpf);

    /**
     * Verifica e-mail sem diferenciar letras maiúsculas e minúsculas.
     *
     * @param email e-mail normalizado
     * @return {@code true} quando o e-mail já existe
     */
    boolean existsByEmailIgnoreCase(String email);

    /**
     * Procura CPF duplicado durante uma edição, ignorando o próprio registro.
     *
     * @param cpf CPF sem máscara
     * @param id identificador que deve ser desconsiderado
     * @return {@code true} quando outro aluno usa o CPF
     */
    boolean existsByCpfAndIdNot(String cpf, Long id);

    /**
     * Procura e-mail duplicado durante uma edição, ignorando o próprio registro.
     *
     * @param email e-mail normalizado
     * @param id identificador que deve ser desconsiderado
     * @return {@code true} quando outro aluno usa o e-mail
     */
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    /**
     * Lista os alunos na ordem estável exigida pela interface.
     *
     * @return alunos ordenados pelo ID crescente
     */
    List<Aluno> findAllByOrderByIdAsc();
}
