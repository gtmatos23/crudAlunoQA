"use strict";

/*
 * Estado da interface
 * O navegador mantém uma cópia da lista recebida pela API. Pesquisa e filtro
 * trabalham sobre essa cópia, evitando requisições a cada tecla digitada.
 */
const API_ALUNOS = "/api/alunos";
const estado = {
    alunos: [],
    alunoEmEdicao: null,
    alunoParaExcluir: null,
    carregando: true
};

const elementos = {};

/*
 * DOMContentLoaded espera o HTML ser interpretado. Depois disso, as referências
 * são guardadas uma vez e os eventos conectam as intenções do usuário às ações.
 */
document.addEventListener("DOMContentLoaded", () => {
    mapearElementos();
    configurarEventos();
    limitarDatas();
    carregarAlunos();
});

/**
 * Centraliza seletores para que as demais funções expressem a intenção, e não
 * repitam detalhes da estrutura do documento.
 */
function mapearElementos() {
    elementos.totalAlunos = document.querySelector("#total-alunos");
    elementos.totalAtivos = document.querySelector("#total-ativos");
    elementos.totalInativos = document.querySelector("#total-inativos");
    elementos.textoAtualizacao = document.querySelector("#texto-atualizacao");
    elementos.pesquisa = document.querySelector("#pesquisa-alunos");
    elementos.filtro = document.querySelector("#filtro-situacao");
    elementos.contador = document.querySelector("#contador-resultados");
    elementos.estadoListagem = document.querySelector("#estado-listagem");
    elementos.tabelaContainer = document.querySelector("#tabela-container");
    elementos.corpoTabela = document.querySelector("#corpo-tabela");
    elementos.botaoNovo = document.querySelector("#botao-novo-aluno");
    elementos.botaoAjuda = document.querySelector("#botao-ajuda");
    elementos.dialogoAluno = document.querySelector("#dialogo-aluno");
    elementos.dialogoExclusao = document.querySelector("#dialogo-exclusao");
    elementos.dialogoAjuda = document.querySelector("#dialogo-ajuda");
    elementos.formulario = document.querySelector("#formulario-aluno");
    elementos.tituloFormulario = document.querySelector("#titulo-dialogo-aluno");
    elementos.botaoSalvar = document.querySelector("#botao-salvar");
    elementos.botaoExcluir = document.querySelector("#botao-confirmar-exclusao");
    elementos.mensagemExclusao = document.querySelector("#mensagem-exclusao");
    elementos.notificacoes = document.querySelector("#notificacoes");
    elementos.cpf = document.querySelector("#cpf");
    elementos.telefone = document.querySelector("#telefone");
    elementos.dataNascimento = document.querySelector("#data-nascimento");
    elementos.dataMatricula = document.querySelector("#data-matricula");
    elementos.ativo = document.querySelector("#ativo");
    elementos.textoSituacao = document.querySelector("#texto-situacao-formulario");
}

/**
 * Registra eventos de forma declarativa. A delegação de clique permite que
 * botões criados depois, como os das linhas e estados vazios, funcionem também.
 */
function configurarEventos() {
    elementos.botaoNovo.addEventListener("click", abrirCadastro);
    elementos.botaoAjuda.addEventListener("click", () => abrirDialogo(elementos.dialogoAjuda));
    elementos.pesquisa.addEventListener("input", renderizarListagem);
    elementos.filtro.addEventListener("change", renderizarListagem);
    elementos.formulario.addEventListener("submit", salvarAluno);
    elementos.botaoExcluir.addEventListener("click", excluirAluno);
    elementos.cpf.addEventListener("input", aplicarMascaraCpf);
    elementos.telefone.addEventListener("input", aplicarMascaraTelefone);
    elementos.ativo.addEventListener("change", atualizarTextoSituacao);

    document.addEventListener("click", tratarCliqueDelegado);

    // O evento close é disparado inclusive quando o dialog é fechado com Esc.
    [elementos.dialogoAluno, elementos.dialogoExclusao, elementos.dialogoAjuda]
        .forEach((dialogo) => dialogo.addEventListener("close", devolverFoco));
}

/**
 * Define o maior valor aceito pelos campos de data. Essa prevenção imediata
 * complementa, mas não substitui, a validação feita novamente no servidor.
 */
function limitarDatas() {
    const hoje = dataLocalIso();
    elementos.dataNascimento.max = hoje;
    elementos.dataMatricula.max = hoje;
}

/**
 * Usa Fetch API para buscar os dados no mesmo endereço da aplicação.
 *
 * async indica que a função executa operações assíncronas. await pausa somente
 * esta função até a resposta chegar, sem bloquear a página nem o teclado.
 */
async function carregarAlunos(mostrarCarregamento = true) {
    if (mostrarCarregamento) {
        estado.carregando = true;
        mostrarEstadoCarregando();
    }

    try {
        estado.alunos = await requisitarJson(API_ALUNOS);
        estado.carregando = false;
        atualizarResumo();
        renderizarListagem();
        elementos.textoAtualizacao.textContent = "Atualizado agora";
    } catch (erro) {
        estado.carregando = false;
        mostrarEstadoErro();
        notificar("Não foi possível carregar os alunos.", "erro", true);
    }
}

/**
 * Calcula os três indicadores sempre a partir da lista mais recente da API.
 */
function atualizarResumo() {
    const ativos = estado.alunos.filter((aluno) => aluno.ativo).length;
    elementos.totalAlunos.textContent = estado.alunos.length;
    elementos.totalAtivos.textContent = ativos;
    elementos.totalInativos.textContent = estado.alunos.length - ativos;
}

/**
 * Aplica pesquisa e situação em conjunto e decide entre tabela e estado vazio.
 */
function renderizarListagem() {
    if (estado.carregando) {
        return;
    }

    const termo = normalizarBusca(elementos.pesquisa.value);
    const filtro = elementos.filtro.value;
    const filtrados = estado.alunos.filter((aluno) => {
        const correspondeSituacao =
            filtro === "todos"
            || (filtro === "ativos" && aluno.ativo)
            || (filtro === "inativos" && !aluno.ativo);

        const camposPesquisaveis = [
            aluno.nomeCompleto,
            aluno.cpf,
            aluno.email
        ].map(normalizarBusca);

        return correspondeSituacao
            && (!termo || camposPesquisaveis.some((campo) => campo.includes(termo)));
    });

    atualizarContador(filtrados.length);

    if (estado.alunos.length === 0) {
        mostrarEstadoVazio();
        return;
    }

    if (filtrados.length === 0) {
        mostrarEstadoSemResultados();
        return;
    }

    elementos.estadoListagem.hidden = true;
    elementos.tabelaContainer.hidden = false;
    elementos.corpoTabela.replaceChildren(...filtrados.map(criarLinhaAluno));
}

/**
 * Cria cada célula com textContent, evitando interpretar dados do aluno como HTML.
 */
function criarLinhaAluno(aluno) {
    const linha = document.createElement("tr");
    linha.append(
        criarCelula(aluno.id, "ID"),
        criarCelula(aluno.nomeCompleto, "Nome", "celula-nome"),
        criarCelula(formatarCpf(aluno.cpf), "CPF"),
        criarCelula(aluno.email, "E-mail", "celula-email"),
        criarCelula(formatarPlano(aluno.plano), "Plano"),
        criarCelulaSituacao(aluno.ativo),
        criarCelulaAcoes(aluno)
    );
    return linha;
}

/**
 * Cria uma célula responsiva; data-label vira o rótulo no formato de cartão.
 */
function criarCelula(conteudo, rotulo, classe = "") {
    const celula = document.createElement("td");
    celula.dataset.label = rotulo;
    celula.textContent = conteudo ?? "—";
    if (classe) {
        celula.className = classe;
    }
    return celula;
}

/**
 * Situação inclui palavra e marcador, atendendo quem não distingue as cores.
 */
function criarCelulaSituacao(ativo) {
    const celula = criarCelula("", "Situação");
    const etiqueta = document.createElement("span");
    etiqueta.className = ativo ? "etiqueta" : "etiqueta etiqueta--inativo";
    etiqueta.textContent = ativo ? "Ativo" : "Inativo";
    celula.append(etiqueta);
    return celula;
}

/**
 * Ações usam texto visível e conservam contexto no aria-label.
 */
function criarCelulaAcoes(aluno) {
    const celula = criarCelula("", "Ações");
    const grupo = document.createElement("div");
    grupo.className = "acoes";

    const editar = document.createElement("button");
    editar.type = "button";
    editar.className = "botao--texto";
    editar.textContent = "Editar";
    editar.dataset.acao = "editar";
    editar.dataset.id = aluno.id;
    editar.setAttribute("aria-label", `Editar ${aluno.nomeCompleto}`);

    const excluir = document.createElement("button");
    excluir.type = "button";
    excluir.className = "botao--texto botao--texto-perigo";
    excluir.textContent = "Excluir";
    excluir.dataset.acao = "excluir";
    excluir.dataset.id = aluno.id;
    excluir.setAttribute("aria-label", `Excluir ${aluno.nomeCompleto}`);

    grupo.append(editar, excluir);
    celula.append(grupo);
    return celula;
}

/**
 * O contador torna visível o efeito dos filtros e informa também o total.
 */
function atualizarContador(quantidade) {
    const total = estado.alunos.length;
    elementos.contador.textContent = quantidade === total
        ? `${quantidade} ${quantidade === 1 ? "resultado" : "resultados"}`
        : `${quantidade} de ${total} resultados`;
}

/**
 * Exibe o retorno de carregamento antes que a API responda.
 */
function mostrarEstadoCarregando() {
    elementos.tabelaContainer.hidden = true;
    elementos.estadoListagem.hidden = false;
    elementos.estadoListagem.className = "estado-listagem";
    elementos.estadoListagem.replaceChildren();

    const carregador = document.createElement("span");
    carregador.className = "carregador";
    carregador.setAttribute("aria-hidden", "true");

    const texto = document.createElement("div");
    const titulo = document.createElement("strong");
    titulo.textContent = "Carregando alunos...";
    const descricao = document.createElement("p");
    descricao.textContent = "Aguarde enquanto buscamos os dados mais recentes.";
    texto.append(titulo, descricao);
    elementos.estadoListagem.append(carregador, texto);
}

/**
 * Oferece recuperação explícita quando a consulta falha.
 */
function mostrarEstadoErro() {
    mostrarEstadoComAcao(
        "Não foi possível carregar os alunos",
        "Confira se a aplicação está disponível e tente novamente.",
        "Tentar novamente",
        "tentar-novamente",
        true
    );
}

/**
 * Diferencia a ausência real de registros da ausência causada por um filtro.
 */
function mostrarEstadoVazio() {
    mostrarEstadoComAcao(
        "Nenhum aluno cadastrado",
        "Cadastre o primeiro aluno para começar a organizar as matrículas.",
        "Cadastrar primeiro aluno",
        "novo-aluno"
    );
}

/**
 * Mantém a pessoa no controle ao permitir limpar pesquisa e filtro de uma vez.
 */
function mostrarEstadoSemResultados() {
    mostrarEstadoComAcao(
        "Nenhum aluno encontrado",
        "Altere a pesquisa ou a situação selecionada para ver outros resultados.",
        "Limpar pesquisa e filtros",
        "limpar-filtros"
    );
}

/**
 * Monta estados vazios consistentes e com uma próxima ação clara.
 */
function mostrarEstadoComAcao(tituloTexto, descricaoTexto, botaoTexto, acao, erro = false) {
    elementos.tabelaContainer.hidden = true;
    elementos.estadoListagem.hidden = false;
    elementos.estadoListagem.className =
        `estado-listagem estado-listagem--central${erro ? " estado-listagem--erro" : ""}`;
    elementos.estadoListagem.replaceChildren();

    const titulo = document.createElement("strong");
    titulo.textContent = tituloTexto;
    const descricao = document.createElement("p");
    descricao.textContent = descricaoTexto;
    const botao = document.createElement("button");
    botao.type = "button";
    botao.className = erro ? "botao botao--perigo" : "botao botao--primario";
    botao.textContent = botaoTexto;
    botao.dataset.acao = acao;

    elementos.estadoListagem.append(titulo, descricao, botao);
}

/**
 * Interpreta cliques em controles dinâmicos a partir de data-acao.
 */
function tratarCliqueDelegado(evento) {
    const fechar = evento.target.closest("[data-fechar-dialogo]");
    if (fechar) {
        document.querySelector(`#${fechar.dataset.fecharDialogo}`).close();
        return;
    }

    const botao = evento.target.closest("[data-acao]");
    if (!botao) {
        return;
    }

    const aluno = estado.alunos.find((item) => item.id === Number(botao.dataset.id));
    switch (botao.dataset.acao) {
        case "editar":
            if (aluno) abrirEdicao(aluno);
            break;
        case "excluir":
            if (aluno) abrirExclusao(aluno);
            break;
        case "novo-aluno":
            abrirCadastro();
            break;
        case "limpar-filtros":
            limparFiltros();
            break;
        case "tentar-novamente":
            carregarAlunos();
            break;
        default:
            break;
    }
}

/**
 * Prepara o formulário com padrões seguros para um cadastro.
 */
function abrirCadastro() {
    estado.alunoEmEdicao = null;
    elementos.formulario.reset();
    limparErrosFormulario();
    elementos.tituloFormulario.textContent = "Cadastrar aluno";
    elementos.dataMatricula.value = dataLocalIso();
    elementos.ativo.checked = true;
    atualizarTextoSituacao();
    abrirDialogo(elementos.dialogoAluno, document.querySelector("#nome-completo"));
}

/**
 * Preenche todos os valores atuais, favorecendo reconhecimento em vez de memória.
 */
function abrirEdicao(aluno) {
    estado.alunoEmEdicao = aluno;
    elementos.formulario.reset();
    limparErrosFormulario();
    elementos.tituloFormulario.textContent = "Editar aluno";
    elementos.formulario.elements.nomeCompleto.value = aluno.nomeCompleto;
    elementos.formulario.elements.cpf.value = formatarCpf(aluno.cpf);
    elementos.formulario.elements.email.value = aluno.email;
    elementos.formulario.elements.telefone.value = formatarTelefone(aluno.telefone);
    elementos.formulario.elements.dataNascimento.value = aluno.dataNascimento;
    elementos.formulario.elements.plano.value = aluno.plano;
    elementos.formulario.elements.dataMatricula.value = aluno.dataMatricula;
    elementos.formulario.elements.ativo.checked = Boolean(aluno.ativo);
    atualizarTextoSituacao();
    abrirDialogo(elementos.dialogoAluno, document.querySelector("#nome-completo"));
}

/**
 * Mostra o nome antes da ação destrutiva e não executa nada automaticamente.
 */
function abrirExclusao(aluno) {
    estado.alunoParaExcluir = aluno;
    elementos.mensagemExclusao.textContent =
        `Tem certeza de que deseja excluir o aluno ${aluno.nomeCompleto}?`;
    abrirDialogo(elementos.dialogoExclusao, elementos.botaoExcluir);
}

/**
 * Guarda o elemento de origem para devolver o foco ao fechar o modal.
 */
function abrirDialogo(dialogo, focoInicial) {
    dialogo.elementoRetornoFoco = document.activeElement;
    dialogo.showModal();
    window.setTimeout(() => (focoInicial ?? dialogo.querySelector("button"))?.focus(), 0);
}

/**
 * Restaura a posição do teclado após cancelar, salvar ou confirmar.
 */
function devolverFoco(evento) {
    const origem = evento.currentTarget.elementoRetornoFoco;
    if (origem instanceof HTMLElement && document.contains(origem)) {
        origem.focus();
    }
}

/**
 * Valida no navegador, remove máscaras e envia somente um cadastro por vez.
 */
async function salvarAluno(evento) {
    evento.preventDefault();
    limparErrosFormulario();

    const dados = obterDadosFormulario();
    if (!validarFormulario(dados)) {
        notificar("Corrija os campos destacados para continuar.", "erro", true);
        focarPrimeiroErro();
        return;
    }

    const editando = estado.alunoEmEdicao !== null;
    const url = editando ? `${API_ALUNOS}/${estado.alunoEmEdicao.id}` : API_ALUNOS;
    const metodo = editando ? "PUT" : "POST";
    alterarEstadoBotao(elementos.botaoSalvar, true, "Salvando...");

    try {
        await requisitarJson(url, {
            method: metodo,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dados)
        });
        elementos.dialogoAluno.close();
        notificar(editando
            ? "Aluno atualizado com sucesso."
            : "Aluno cadastrado com sucesso.");
        await carregarAlunos(false);
    } catch (erro) {
        tratarErroFormulario(erro);
    } finally {
        alterarEstadoBotao(elementos.botaoSalvar, false, "Salvar aluno");
    }
}

/**
 * Constrói o contrato JSON e remove pontuação das máscaras de CPF e telefone.
 */
function obterDadosFormulario() {
    const formulario = elementos.formulario.elements;
    const telefone = somenteNumeros(formulario.telefone.value);
    return {
        nomeCompleto: formulario.nomeCompleto.value.trim(),
        cpf: somenteNumeros(formulario.cpf.value),
        email: formulario.email.value.trim(),
        telefone: telefone || "",
        dataNascimento: formulario.dataNascimento.value || null,
        plano: formulario.plano.value || null,
        dataMatricula: formulario.dataMatricula.value || null,
        ativo: formulario.ativo.checked
    };
}

/**
 * Mensagens específicas explicam o problema e como corrigi-lo.
 */
function validarFormulario(dados) {
    let valido = true;

    if (dados.nomeCompleto.length < 3 || dados.nomeCompleto.length > 100) {
        definirErro("nomeCompleto", "O nome completo deve ter entre 3 e 100 caracteres.");
        valido = false;
    }
    if (dados.cpf.length !== 11) {
        definirErro("cpf", "O CPF deve conter exatamente 11 números.");
        valido = false;
    }
    if (!dados.email || !elementos.formulario.elements.email.validity.valid) {
        definirErro("email", "Informe um e-mail válido.");
        valido = false;
    }
    if (dados.telefone && ![10, 11].includes(dados.telefone.length)) {
        definirErro("telefone", "O telefone deve conter 10 ou 11 números.");
        valido = false;
    }
    if (!dados.dataNascimento) {
        definirErro("dataNascimento", "A data de nascimento é obrigatória.");
        valido = false;
    } else if (dados.dataNascimento >= dataLocalIso()) {
        definirErro("dataNascimento", "A data de nascimento deve ser anterior à data atual.");
        valido = false;
    }
    if (!dados.plano) {
        definirErro("plano", "Selecione um plano.");
        valido = false;
    }
    if (dados.dataMatricula && dados.dataMatricula > dataLocalIso()) {
        definirErro("dataMatricula", "A data de matrícula não pode estar no futuro.");
        valido = false;
    }

    return valido;
}

/**
 * Exibe erros devolvidos pelo backend sob o mesmo campo já conhecido no HTML.
 */
function tratarErroFormulario(erro) {
    if (erro.dados?.errosValidacao) {
        Object.entries(erro.dados.errosValidacao)
            .forEach(([campo, mensagem]) => definirErro(campo, mensagem));
        focarPrimeiroErro();
    }
    notificar(
        erro.dados?.mensagem || "Não foi possível salvar o aluno. Tente novamente.",
        "erro",
        true
    );
}

/**
 * Conclui a exclusão somente após a confirmação explícita no dialog.
 */
async function excluirAluno() {
    if (!estado.alunoParaExcluir) {
        return;
    }

    alterarEstadoBotao(elementos.botaoExcluir, true, "Excluindo...");
    try {
        await requisitarJson(`${API_ALUNOS}/${estado.alunoParaExcluir.id}`, {
            method: "DELETE"
        });
        elementos.dialogoExclusao.close();
        estado.alunoParaExcluir = null;
        notificar("Aluno excluído com sucesso.");
        await carregarAlunos(false);
    } catch (erro) {
        notificar(
            erro.dados?.mensagem || "Não foi possível excluir o aluno. Tente novamente.",
            "erro",
            true
        );
    } finally {
        alterarEstadoBotao(elementos.botaoExcluir, false, "Excluir aluno");
    }
}

/**
 * Encapsula Fetch API e preserva o corpo de erro enviado pelo servidor.
 */
async function requisitarJson(url, opcoes = {}) {
    let resposta;
    try {
        resposta = await fetch(url, opcoes);
    } catch (causa) {
        const erroRede = new Error("Não foi possível acessar o servidor.");
        erroRede.cause = causa;
        throw erroRede;
    }

    if (resposta.status === 204) {
        return null;
    }

    const tipo = resposta.headers.get("content-type") || "";
    const dados = tipo.includes("application/json") ? await resposta.json() : null;
    if (!resposta.ok) {
        const erro = new Error(dados?.mensagem || "A operação não pôde ser concluída.");
        erro.status = resposta.status;
        erro.dados = dados;
        throw erro;
    }
    return dados;
}

/**
 * Apresenta retornos numa região aria-live. Sucessos desaparecem; falhas que
 * exigem correção permanecem e todas podem ser fechadas manualmente.
 */
function notificar(mensagem, tipo = "sucesso", persistente = false) {
    const notificacao = document.createElement("div");
    notificacao.className = `notificacao${tipo === "erro" ? " notificacao--erro" : ""}`;
    notificacao.setAttribute("role", tipo === "erro" ? "alert" : "status");

    const simbolo = document.createElement("span");
    simbolo.setAttribute("aria-hidden", "true");
    simbolo.textContent = tipo === "erro" ? "!" : "✓";

    const texto = document.createElement("span");
    texto.className = "notificacao__texto";
    texto.textContent = mensagem;

    const fechar = document.createElement("button");
    fechar.type = "button";
    fechar.className = "notificacao__fechar";
    fechar.setAttribute("aria-label", "Fechar mensagem");
    fechar.textContent = "×";
    fechar.addEventListener("click", () => notificacao.remove());

    notificacao.append(simbolo, texto, fechar);
    elementos.notificacoes.append(notificacao);

    if (!persistente) {
        window.setTimeout(() => notificacao.remove(), 5000);
    }
}

/**
 * Liga uma mensagem de erro ao input por aria-describedby e aria-invalid.
 */
function definirErro(nomeCampo, mensagem) {
    const campo = elementos.formulario.elements[nomeCampo];
    const erro = document.querySelector(`#erro-${nomeCampo}`);
    if (campo) {
        campo.setAttribute("aria-invalid", "true");
    }
    if (erro) {
        erro.textContent = mensagem;
    }
}

/**
 * Remove apenas mensagens, preservando os valores digitados pelo usuário.
 */
function limparErrosFormulario() {
    elementos.formulario.querySelectorAll("[aria-invalid]")
        .forEach((campo) => campo.removeAttribute("aria-invalid"));
    elementos.formulario.querySelectorAll(".erro-campo")
        .forEach((erro) => { erro.textContent = ""; });
}

/**
 * Leva o teclado diretamente ao primeiro ponto que precisa de correção.
 */
function focarPrimeiroErro() {
    elementos.formulario.querySelector('[aria-invalid="true"]')?.focus();
}

/**
 * Evita múltiplos envios e deixa o andamento da operação visível.
 */
function alterarEstadoBotao(botao, processando, texto) {
    botao.disabled = processando;
    botao.textContent = texto;
}

/**
 * Limpa os dois controles e devolve o foco à pesquisa.
 */
function limparFiltros() {
    elementos.pesquisa.value = "";
    elementos.filtro.value = "todos";
    renderizarListagem();
    elementos.pesquisa.focus();
}

/**
 * A situação sempre é comunicada por texto, além da aparência do controle.
 */
function atualizarTextoSituacao() {
    elementos.textoSituacao.textContent = elementos.ativo.checked
        ? "Aluno ativo"
        : "Aluno inativo";
}

/**
 * Mantém somente algarismos para o contrato da API e para comparações.
 */
function somenteNumeros(valor = "") {
    return String(valor).replace(/\D/g, "");
}

/**
 * Máscara progressiva do CPF: facilita digitação sem mudar o valor enviado.
 */
function aplicarMascaraCpf(evento) {
    evento.target.value = formatarCpf(somenteNumeros(evento.target.value).slice(0, 11));
}

/**
 * Máscara progressiva aceita tanto telefone fixo quanto celular.
 */
function aplicarMascaraTelefone(evento) {
    evento.target.value = formatarTelefone(
        somenteNumeros(evento.target.value).slice(0, 11)
    );
}

/**
 * Formata CPF somente para apresentação.
 */
function formatarCpf(cpf = "") {
    const numeros = somenteNumeros(cpf);
    return numeros
        .replace(/^(\d{3})(\d)/, "$1.$2")
        .replace(/^(\d{3})\.(\d{3})(\d)/, "$1.$2.$3")
        .replace(/\.(\d{3})(\d)/, ".$1-$2");
}

/**
 * Formata 10 números como fixo e 11 como celular.
 */
function formatarTelefone(telefone = "") {
    const numeros = somenteNumeros(telefone);
    if (!numeros) return "";
    if (numeros.length <= 10) {
        return numeros
            .replace(/^(\d{2})(\d)/, "($1) $2")
            .replace(/(\d{4})(\d)/, "$1-$2");
    }
    return numeros
        .replace(/^(\d{2})(\d)/, "($1) $2")
        .replace(/(\d{5})(\d)/, "$1-$2");
}

/**
 * Traduz valores técnicos do enum para a linguagem cotidiana da academia.
 */
function formatarPlano(plano) {
    return {
        MENSAL: "Mensal",
        TRIMESTRAL: "Trimestral",
        ANUAL: "Anual"
    }[plano] || plano;
}

/**
 * Normaliza caixa, acentos e pontuação para ampliar os resultados da pesquisa.
 */
function normalizarBusca(valor = "") {
    return String(valor)
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .replace(/[.\-()]/g, "")
        .toLocaleLowerCase("pt-BR")
        .trim();
}

/**
 * Produz yyyy-MM-dd no fuso local, sem o deslocamento que Date.toISOString cria.
 */
function dataLocalIso() {
    const hoje = new Date();
    const ano = hoje.getFullYear();
    const mes = String(hoje.getMonth() + 1).padStart(2, "0");
    const dia = String(hoje.getDate()).padStart(2, "0");
    return `${ano}-${mes}-${dia}`;
}
