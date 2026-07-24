package br.com.academia.alunos.entity;

/**
 * Planos que podem ser escolhidos durante a matrícula do aluno.
 *
 * <p>Uma enumeração restringe o domínio a valores conhecidos e evita textos
 * inconsistentes no banco de dados.</p>
 */
public enum PlanoAcademia {
    MENSAL,
    TRIMESTRAL,
    ANUAL
}
