package br.com.academia.alunos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada do sistema de gestão de alunos.
 *
 * <p>A anotação {@link SpringBootApplication} combina configuração,
 * autoconfiguração e busca de componentes dentro deste pacote e de seus
 * subpacotes.</p>
 */
@SpringBootApplication
public class AcademiaAlunosApplication {

    /**
     * Inicializa o Spring e o servidor web embarcado.
     *
     * @param args argumentos opcionais recebidos pela linha de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(AcademiaAlunosApplication.class, args);
    }
}
