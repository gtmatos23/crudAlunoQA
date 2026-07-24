package br.com.academia.alunos.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Formato único das respostas de erro produzidas pela API.
 *
 * <p>A estrutura previsível facilita o diagnóstico na interface: erros gerais
 * ficam em {@code mensagem} e validações específicas ficam em {@code errosValidacao}.</p>
 */
public class ErroResposta {

    private LocalDateTime dataHora;
    private int codigoHttp;
    private String erro;
    private String mensagem;
    private String caminho;
    private Map<String, String> errosValidacao = new LinkedHashMap<>();

    /**
     * Construtor vazio usado pelo mecanismo de serialização JSON.
     */
    public ErroResposta() {
    }

    /**
     * Inicializa os dados comuns a qualquer tipo de erro.
     *
     * @param dataHora momento em que o problema ocorreu
     * @param codigoHttp código HTTP numérico
     * @param erro nome resumido da categoria HTTP
     * @param mensagem explicação amigável
     * @param caminho endereço solicitado
     */
    public ErroResposta(LocalDateTime dataHora, int codigoHttp, String erro,
                        String mensagem, String caminho) {
        this.dataHora = dataHora;
        this.codigoHttp = codigoHttp;
        this.erro = erro;
        this.mensagem = mensagem;
        this.caminho = caminho;
    }

    /** @return momento do erro */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /** @param dataHora momento a serializar */
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    /** @return código HTTP numérico */
    public int getCodigoHttp() {
        return codigoHttp;
    }

    /** @param codigoHttp código a serializar */
    public void setCodigoHttp(int codigoHttp) {
        this.codigoHttp = codigoHttp;
    }

    /** @return categoria resumida do erro */
    public String getErro() {
        return erro;
    }

    /** @param erro categoria a serializar */
    public void setErro(String erro) {
        this.erro = erro;
    }

    /** @return explicação amigável */
    public String getMensagem() {
        return mensagem;
    }

    /** @param mensagem explicação a serializar */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /** @return caminho HTTP solicitado */
    public String getCaminho() {
        return caminho;
    }

    /** @param caminho caminho a serializar */
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    /** @return mensagens de validação organizadas por campo */
    public Map<String, String> getErrosValidacao() {
        return errosValidacao;
    }

    /** @param errosValidacao mapa de problemas a serializar */
    public void setErrosValidacao(Map<String, String> errosValidacao) {
        this.errosValidacao = errosValidacao;
    }
}
