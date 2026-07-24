# CASOS_DE_TESTE_FUNCIONAIS

## 1. Identificação

- Sistema: academia-alunos-web
- Versão do documento: 1.0
- Data: 2026-07-24
- Branch analisada: main
- Hash do commit analisado: 25dfa5d
- Responsável pela especificação: A definir
- Tipo de teste: Funcional Manual
- Status do documento: Rascunho

## 2. Objetivo do documento

Documentar os casos de teste funcionais para o sistema de gestão de alunos da academia, cobrindo a interface gráfica, a integração com a API REST, as regras de negócio, as validações de entrada, a persistência em H2 e as mensagens de sucesso e erro observadas no repositório.

## 3. Fontes de evidência

- `src/main/java/br/com/academia/alunos/entity/Aluno.java`
- `src/main/java/br/com/academia/alunos/entity/PlanoAcademia.java`
- `src/main/java/br/com/academia/alunos/dto/AlunoRequest.java`
- `src/main/java/br/com/academia/alunos/dto/AlunoResponse.java`
- `src/main/java/br/com/academia/alunos/controller/AlunoController.java`
- `src/main/java/br/com/academia/alunos/service/AlunoService.java`
- `src/main/java/br/com/academia/alunos/repository/AlunoRepository.java`
- `src/main/java/br/com/academia/alunos/exception/GlobalExceptionHandler.java`
- `src/main/resources/static/index.html`
- `src/main/resources/static/js/alunos.js`
- `src/main/resources/application.properties`
- `README.md`

## 4. Escopo

Inclui:

- interface gráfica de cadastro, edição, exclusão, pesquisa e filtros;
- integração entre o frontend e a API REST em `/api/alunos`;
- operações CRUD sobre alunos;
- regras de validação de campos obrigatórios, formato e limites;
- tratamento de duplicidade de CPF e e-mail;
- retorno de códigos HTTP 200, 201, 204, 400, 404 e 409;
- persistência em banco H2 em memória durante a execução;
- mensagens de erro e sucesso exibidas ao usuário;
- estados de carregamento, vazio, erro e sem resultados.

## 5. Fora do escopo

Exclui:

- desempenho, carga, estresse ou escalabilidade;
- segurança ofensiva, invasão ou análise de vulnerabilidade;
- automação de testes ou código de teste automatizado;
- testes unitários, análise estática, cobertura de código ou mutação;
- implantação em produção;
- compatibilidade total com navegadores antigos;
- avaliação estética subjetiva da interface.

## 6. Estratégia de projeto

A estratégia é baseada em risco, priorizando os fluxos CRUD, a integridade dos dados e as validações que afetam o registro de alunos. Foram usadas as seguintes técnicas:

- particionamento de equivalência para CPF, e-mail, telefone e situação;
- análise de valor-limite para nome, CPF, data de nascimento e data de matrícula;
- teste negativo para formatos inválidos, campos obrigatórios ausentes e duplicidade;
- teste de contrato de API para verificar códigos HTTP, cabeçalhos e corpo JSON;
- transição de estado para cadastrar, editar, excluir e recarregar a lista;
- combinação de pesquisa e filtro para validar interações do frontend.

Critérios de prioridade:

- Crítica: CRUD principal, duplicidade, exclusão e integridade de dados.
- Alta: validações obrigatórias e mensagens de erro.
- Média: filtros, pesquisa e estados de lista.
- Baixa: ajuda, foco e comportamento assistivo.

## 7. Premissas e dependências

- A aplicação deve estar em execução e servir o HTML em um navegador.
- A API deve estar disponível em `/api/alunos` no mesmo host.
- O banco H2 é em memória e reinicializa ao encerrar a aplicação (`create-drop`).
- O campo `telefone` é opcional; CPF, e-mail, nome completo, data de nascimento e plano são obrigatórios.
- O frontend faz validação, mas o backend também valida e retorna mensagens padronizadas.
- O CPF é enviado sem máscara e o frontend formata apenas para exibição.
- O e-mail é normalizado em minúsculas para evitar duplicidade por caixa.

## 8. Catálogo de dados de teste

| Código | Descrição | Valor |
| ------ | --------- | ----- |
| DT001 | CPF válido não cadastrado | `12345678901` |
| DT002 | CPF válido diferente | `98765432100` |
| DT003 | E-mail válido não cadastrado | `ana.souza@example.com` |
| DT004 | E-mail válido diferente | `bruno.costa@example.com` |
| DT005 | Nome válido de comprimento mínimo | `Ana Silva` |
| DT006 | Nome com 2 caracteres | `An` |
| DT007 | Nome com 101 caracteres | `A` repetido 101 vezes |
| DT008 | Telefone válido com 10 dígitos | `1133224455` |
| DT009 | Telefone válido com 11 dígitos | `11998877665` |
| DT010 | CPF com letras | `12345abc901` |
| DT011 | CPF com 10 dígitos | `1234567890` |
| DT012 | E-mail inválido | `usuario@` |
| DT013 | Data nascimento anterior | data anterior à data atual |
| DT014 | Data nascimento igual hoje | data atual |
| DT015 | Data matrícula futura | data após a data atual |
| DT016 | Plano Mensal | `MENSAL` |
| DT017 | Plano Trimestral | `TRIMESTRAL` |
| DT018 | Plano Anual | `ANUAL` |
| DT019 | Situação ativa | `true` |
| DT020 | Situação inativa | `false` |
| DT021 | CPF formatado para exibição | `123.456.789-01` |
| DT022 | Telefone com máscara | `(11) 99999-8888` |

## 9. Casos de teste

### CT001 - Carregar página inicial e listar alunos

| Campo | Informação |
|---|---|
| Funcionalidade | Inicialização |
| Prioridade | Crítica |
| Tipo | Positivo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Caso de uso |
| Requisitos cobertos | RF002, API002 |
| Evidência no repositório | `src/main/resources/static/js/alunos.js: carregarAlunos()` |

#### Objetivo
Verificar que a página inicial carrega e que a listagem de alunos é solicitada à API.

#### Pré-condições
- A aplicação está em execução.
- O navegador acessa a página principal.

#### Dados de teste
Nenhum.

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Acessar a página principal. | O cabeçalho, o botão "Novo aluno" e o painel de resumo são exibidos. |
| 2 | Observar o estado de listagem. | O texto "Carregando alunos..." aparece e a tabela é substituída após a resposta. |
| 3 | Verificar a requisição. | O frontend invoca GET `/api/alunos` e exibe o retorno no DOM. |

#### Pós-condições
- A lista de alunos é exibida ou o estado de erro/vazio é apresentado.

#### Observações
Nenhuma.

### CT002 - Exibir estado inicial quando não há alunos cadastrados

| Campo | Informação |
|---|---|
| Funcionalidade | Listagem |
| Prioridade | Alta |
| Tipo | Positivo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Transição de estado |
| Requisitos cobertos | RF002, UI004 |
| Evidência no repositório | `src/main/resources/static/js/alunos.js: mostrarEstadoVazio()` |

#### Objetivo
Verificar que o sistema mostra mensagem e ação de cadastro quando a lista retorna vazia.

#### Pré-condições
- O banco está vazio.
- A aplicação está em execução.

#### Dados de teste
Nenhum.

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Acessar a página principal. | Exibe "Nenhum aluno cadastrado" e botão "Cadastrar primeiro aluno". |
| 2 | Observar o contador. | O contador indica "0 resultados". |

#### Pós-condições
- O estado vazio permanece até novo cadastro.

#### Observações
Nenhuma.

### CT003 - Exibir erro de carregamento quando a API falha

| Campo | Informação |
|---|---|
| Funcionalidade | Inicialização |
| Prioridade | Alta |
| Tipo | Negativo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Error guessing |
| Requisitos cobertos | API002, UI004 |
| Evidência no repositório | `src/main/resources/static/js/alunos.js: mostrarEstadoErro()` |

#### Objetivo
Verificar que o frontend mostra erro e permite re-tentativa quando GET `/api/alunos` falha.

#### Pré-condições
- A API retorna erro ou não está disponível.
- A aplicação está em execução.

#### Dados de teste
Nenhum.

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Acessar a página com a API indisponível. | Exibe "Não foi possível carregar os alunos". |
| 2 | Verificar a ação apresentada. | O botão "Tentar novamente" é exibido. |
| 3 | Clicar em "Tentar novamente". | O frontend envia novamente GET `/api/alunos`. |

#### Pós-condições
- Existe possibilidade de recuperação do estado.

#### Observações
Nenhuma.

### CT004 - Cadastrar aluno com todos os dados válidos

| Campo | Informação |
|---|---|
| Funcionalidade | Cadastro |
| Prioridade | Crítica |
| Tipo | Positivo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Particionamento de equivalência |
| Requisitos cobertos | RF001, RN001, RN002, RN003, RN004, RN005, RN006, RN007, RN008, RN009, RN010, API001 |
| Evidência no repositório | `src/main/resources/static/js/alunos.js: salvarAluno()`, `src/main/java/br/com/academia/alunos/service/AlunoService.java: cadastrar()` |

#### Objetivo
Verificar cadastro de aluno com campos obrigatórios e opcionais válidos.

#### Pré-condições
- A aplicação está em execução.
- Nenhum aluno com CPF `12345678901` ou e-mail `ana.souza@example.com` existe.
- O formulário de cadastro está aberto.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| Nome completo | `Ana Souza Ferreira` | Partição válida de comprimento aceitável |
| CPF | `12345678901` | 11 dígitos numéricos |
| E-mail | `ana.souza@example.com` | Formato válido |
| Telefone | `31999998888` | 11 dígitos válidos |
| Data de nascimento | data anterior à atual | Deve ser passada |
| Plano | `MENSAL` | Valor do enum válido |
| Data de matrícula | data atual | Valor presente permitido |
| Situação | ativo | Checkbox marcado |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Selecionar "Novo aluno". | O diálogo de cadastro é exibido. |
| 2 | Preencher todos os campos válidos. | Não há mensagens de erro de validação. |
| 3 | Clicar em "Salvar aluno". | O botão muda para "Salvando..." e fica desabilitado. |
| 4 | Confirmar a resposta. | A API retorna HTTP 201 e o diálogo fecha. |
| 5 | Verificar a tabela. | O aluno aparece com CPF formatado e situação "Ativo". |
| 6 | Verificar os indicadores. | Os números de total e ativos são atualizados. |

#### Pós-condições
- O aluno foi persistido no banco em memória.
- O registro está disponível para consulta, edição e exclusão.

#### Observações
Nenhuma.

### CT005 - Cadastrar aluno omitindo campos opcionais

| Campo | Informação |
|---|---|
| Funcionalidade | Cadastro |
| Prioridade | Alta |
| Tipo | Positivo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Análise de valor-limite |
| Requisitos cobertos | RF001, RN001, RN002, RN007, RN008, RN009, RN010 |
| Evidência no repositório | `src/main/java/br/com/academia/alunos/service/AlunoService.java: cadastrar()` |

#### Objetivo
Verificar que campos opcionais podem ser omitidos e que valores padrão são aplicados.

#### Pré-condições
- A aplicação está em execução.
- Nenhum aluno com CPF `98765432100` ou e-mail `bruno.costa@example.com` existe.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| Nome completo | `Bruno Costa` | Valor válido |
| CPF | `98765432100` | 11 dígitos válidos |
| E-mail | `bruno.costa@example.com` | Formato válido |
| Telefone | vazio | Campo opcional omitido |
| Data de nascimento | data anterior à atual | Valor válido |
| Plano | `ANUAL` | Valor válido |
| Data de matrícula | vazio | Deve aplicar data atual |
| Situação | omitida | Deve aplicar ativo = true |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Selecionar "Novo aluno". | O formulário é exibido com valores padrão. |
| 2 | Preencher apenas os campos obrigatórios. | O formulário aceita os dados. |
| 3 | Clicar em "Salvar aluno". | A API retorna HTTP 201 e o diálogo fecha. |
| 4 | Verificar o registro. | O telefone não é exibido e a situação é "Ativo". |

#### Pós-condições
- O aluno é persistido com `ativo=true` e data de matrícula igual ao dia atual.

#### Observações
Nenhuma.

### CT006 - Validar nome com menos de 3 caracteres

| Campo | Informação |
|---|---|
| Funcionalidade | Cadastro |
| Prioridade | Alta |
| Tipo | Negativo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Valor-limite |
| Requisitos cobertos | RN003, API006 |
| Evidência no repositório | `src/main/java/br/com/academia/alunos/dto/AlunoRequest.java: @Size`, `src/main/resources/static/js/alunos.js: validarFormulario()` |

#### Objetivo
Verificar que nome com menos de 3 caracteres não é aceito.

#### Pré-condições
- A aplicação está em execução.
- O formulário está aberto.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| Nome completo | `An` | Abaixo do limite mínimo |
| CPF | `12345678901` | Valor válido |
| E-mail | `teste.a@example.com` | Valor válido |
| Data de nascimento | data anterior | Valor válido |
| Plano | `MENSAL` | Valor válido |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Preencher nome com `An` e demais campos válidos. | O frontend mostra erro "O nome completo deve ter entre 3 e 100 caracteres.". |
| 2 | Tentar salvar. | O envio é bloqueado e o erro é apontado no campo. |

#### Pós-condições
- Nenhum aluno é cadastrado.

#### Observações
Os erros são exibidos próximos aos campos.

### CT007 - Validar CPF com número de dígitos inválido

| Campo | Informação |
|---|---|
| Funcionalidade | Cadastro |
| Prioridade | Crítica |
| Tipo | Negativo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Valor-limite |
| Requisitos cobertos | RN004, API006 |
| Evidência no repositório | `src/main/java/br/com/academia/alunos/dto/AlunoRequest.java: @Pattern`, `src/main/resources/static/js/alunos.js: validarFormulario()` |

#### Objetivo
Verificar que CPF com menos de 11 dígitos é rejeitado.

#### Pré-condições
- A aplicação está em execução.
- O formulário está aberto.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| CPF | `1234567890` | 10 dígitos |
| Nome completo | `Carlos Lima` | Valor válido |
| E-mail | `carlos.lima@example.com` | Valor válido |
| Data de nascimento | data anterior | Valor válido |
| Plano | `MENSAL` | Valor válido |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Inserir CPF com 10 dígitos. | O frontend mostra erro "O CPF deve conter exatamente 11 números.". |
| 2 | Clicar em salvar. | O envio é bloqueado e o campo recebe foco. |

#### Pós-condições
- Nenhum registro é criado.

#### Observações
A API também valida o formato do CPF.

### CT008 - Validar e-mail inválido

| Campo | Informação |
|---|---|
| Funcionalidade | Cadastro |
| Prioridade | Alta |
| Tipo | Negativo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Particionamento de equivalência |
| Requisitos cobertos | RN005, API006 |
| Evidência no repositório | `src/main/java/br/com/academia/alunos/dto/AlunoRequest.java: @Email`, `src/main/resources/static/js/alunos.js: validarFormulario()` |

#### Objetivo
Verificar que e-mail inválido é rejeitado com mensagem específica.

#### Pré-condições
- A aplicação está em execução.
- O formulário está aberto.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| E-mail | `usuario@` | Formato inválido |
| Nome completo | `Eduardo Silva` | Valor válido |
| CPF | `12345678901` | Valor válido |
| Data de nascimento | data anterior | Valor válido |
| Plano | `TRIMESTRAL` | Valor válido |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Preencher o e-mail inválido. | O campo é marcado como inválido e exibe "Informe um e-mail válido.". |
| 2 | Clicar em salvar. | O envio é bloqueado. |

#### Pós-condições
- Nenhum aluno é cadastrado.

#### Observações
A mensagem é exibida pelo navegador e pelo código JavaScript.

### CT009 - Cadastrar aluno com CPF duplicado

| Campo | Informação |
|---|---|
| Funcionalidade | Cadastro |
| Prioridade | Crítica |
| Tipo | Negativo |
| Nível | Sistema |
| Canal | Interface gráfica / API REST |
| Técnica de teste | Particionamento de equivalência |
| Requisitos cobertos | RN001, API007 |
| Evidência no repositório | `src/main/java/br/com/academia/alunos/service/AlunoService.java: verificarDuplicidadeNoCadastro()`, `src/main/java/br/com/academia/alunos/exception/GlobalExceptionHandler.java: tratarRecursoDuplicado()` |

#### Objetivo
Verificar que o cadastro é rejeitado quando o CPF já existe.

#### Pré-condições
- Um aluno com CPF `12345678901` já está cadastrado.
- A aplicação está em execução.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| CPF | `12345678901` | CPF já cadastrado |
| Nome completo | `Fernanda Reis` | Valor válido |
| E-mail | `fernanda.reis@example.com` | Valor válido e diferente |
| Data de nascimento | data anterior | Valor válido |
| Plano | `MENSAL` | Valor válido |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Tentar cadastrar novo aluno com CPF já existente. | A API retorna HTTP 409 e mostra mensagem de conflito. |
| 2 | Verificar mensagem no frontend. | Exibe "Já existe um aluno com o CPF informado.". |

#### Pós-condições
- Nenhum novo aluno é criado.

#### Observações
A duplicidade é detectada no serviço e complementada pela proteção do banco.

### CT010 - Cadastrar aluno com e-mail duplicado (case-insensitive)

| Campo | Informação |
|---|---|
| Funcionalidade | Cadastro |
| Prioridade | Crítica |
| Tipo | Negativo |
| Nível | Sistema |
| Canal | Interface gráfica / API REST |
| Técnica de teste | Particionamento de equivalência |
| Requisitos cobertos | RN002, API007 |
| Evidência no repositório | `src/main/java/br/com/academia/alunos/repository/AlunoRepository.java: existsByEmailIgnoreCase()`, `src/main/java/br/com/academia/alunos/service/AlunoService.java: normalizarEmail()` |

#### Objetivo
Verificar que e-mails duplicados são rejeitados sem diferenciar caixa alta e baixa.

#### Pré-condições
- Um aluno com e-mail `fernanda.reis@example.com` já está cadastrado.
- A aplicação está em execução.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| E-mail | `FERNANDA.REIS@EXAMPLE.COM` | Mesmo e-mail em caixa diferente |
| Nome completo | `Fernanda Reis` | Valor válido |
| CPF | `23456789012` | Valor válido diferente |
| Data de nascimento | data anterior | Valor válido |
| Plano | `ANUAL` | Valor válido |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Tentar cadastrar com e-mail já existente em caixa diferente. | A API retorna HTTP 409 e mensagem de conflito. |
| 2 | Verificar mensagem no frontend. | Exibe "Já existe um aluno com o e-mail informado.". |

#### Pós-condições
- Nenhum aluno é criado.

#### Observações
A normalização para minúsculas ocorre antes da verificação.

### CT011 - Atualizar aluno existente com campos válidos

| Campo | Informação |
|---|---|
| Funcionalidade | Atualização |
| Prioridade | Crítica |
| Tipo | Positivo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Caso de uso |
| Requisitos cobertos | RF004, RN003, RN005, RN007, RN008, RN009 |
| Evidência no repositório | `src/main/java/br/com/academia/alunos/service/AlunoService.java: atualizar()` |

#### Objetivo
Verificar que um aluno existente pode ser editado e salvo com novos dados válidos.

#### Pré-condições
- Um aluno existente foi cadastrado.
- A aplicação está em execução.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| Nome completo | `Ana Souza Oliveira` | Alteração válida |
| CPF | `12345678901` | Mesmo CPF do aluno |
| E-mail | `ana.oliveira@example.com` | Alteração válida |
| Telefone | `11999998888` | Alteração válida |
| Data de nascimento | mesma data anterior | Valor consistente |
| Plano | `TRIMESTRAL` | Alteração válida |
| Data de matrícula | mesma data atual | Mantém válido |
| Situação | inativo | Alteração do checkbox |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Clicar em "Editar" para um aluno existente. | O formulário é preenchido com os dados atuais. |
| 2 | Alterar campos válidos. | Os novos valores são aceitos. |
| 3 | Clicar em "Salvar aluno". | A API retorna HTTP 200 e o diálogo fecha. |
| 4 | Verificar a tabela. | O registro exibe as novas informações e situação atualizada. |

#### Pós-condições
- O aluno atualizado permanece no banco.

#### Observações
Nenhuma.

### CT012 - Atualizar aluno mantendo o mesmo CPF e e-mail

| Campo | Informação |
|---|---|
| Funcionalidade | Atualização |
| Prioridade | Alta |
| Tipo | Positivo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Particionamento de equivalência |
| Requisitos cobertos | RN001, RN002 |
| Evidência no repositório | `src/main/java/br/com/academia/alunos/service/AlunoService.java: verificarDuplicidadeNaAtualizacao()` |

#### Objetivo
Verificar que a atualização aceita o mesmo CPF e e-mail do próprio aluno sem conflito.

#### Pré-condições
- Um aluno existente foi cadastrado.
- A aplicação está em execução.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| CPF | mesmo CPF existente | Deve ser aceito |
| E-mail | mesmo e-mail existente | Deve ser aceito |
| Nome completo | `Ana S. Ferreira` | Alteração válida |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Editar um aluno existente. | O formulário abre com valores atuais. |
| 2 | Alterar apenas o nome. | Campo CPF e e-mail permanecem iguais. |
| 3 | Salvar. | A API retorna HTTP 200 sem conflito. |
| 4 | Verificar a tabela. | O aluno é atualizado corretamente. |

#### Pós-condições
- O registro no banco mantém os mesmos identificadores únicos.

#### Observações
Nenhuma.

### CT013 - Atualizar aluno com CPF de outro aluno

| Campo | Informação |
|---|---|
| Funcionalidade | Atualização |
| Prioridade | Crítica |
| Tipo | Negativo |
| Nível | Sistema |
| Canal | Interface gráfica / API REST |
| Técnica de teste | Particionamento de equivalência |
| Requisitos cobertos | RN001, API007 |
| Evidência no repositório | `src/main/java/br/com/academia/alunos/service/AlunoService.java: verificarDuplicidadeNaAtualizacao()` |

#### Objetivo
Verificar que atualizar um aluno usando CPF de outro aluno resulta em conflito.

#### Pré-condições
- Dois alunos distintos estão cadastrados.
- A aplicação está em execução.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| CPF | CPF de outro aluno | Deve disparar conflito |
| Nome completo | `Aluno de Teste` | Valor válido |
| E-mail | mantido diferente | Para isolar CPF |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Editar o segundo aluno. | O formulário abre. |
| 2 | Substituir o CPF pelo CPF do primeiro aluno. | Campo aceita a edição. |
| 3 | Salvar. | A API retorna HTTP 409 e exibe mensagem de conflito. |

#### Pós-condições
- O aluno não é atualizado.

#### Observações
A verificação ignora o próprio ID e compara com outros registros.

### CT014 - Excluir aluno com confirmação explícita

| Campo | Informação |
|---|---|
| Funcionalidade | Exclusão |
| Prioridade | Crítica |
| Tipo | Positivo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Caso de uso |
| Requisitos cobertos | RF005, UI001, API005 |
| Evidência no repositório | `src/main/resources/static/js/alunos.js: abrirExclusao()`, `src/main/java/br/com/academia/alunos/controller/AlunoController.java: excluir()` |

#### Objetivo
Verificar exclusão física de aluno após confirmação no diálogo.

#### Pré-condições
- Um aluno existente está cadastrado.
- A aplicação está em execução.

#### Dados de teste
Nenhum.

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Clicar em "Excluir" para um aluno existente. | Exibe diálogo com o nome correto do aluno. |
| 2 | Confirmar exclusão. | A API recebe DELETE `/api/alunos/{id}`. |
| 3 | Verificar o resultado. | O diálogo fecha e a lista é recarregada sem o aluno. |
| 4 | Verificar indicadores. | O total e o total ativo/inativo são atualizados. |

#### Pós-condições
- O aluno é removido do banco em memória.

#### Observações
Nenhuma.

### CT015 - Cancelar exclusão no diálogo

| Campo | Informação |
|---|---|
| Funcionalidade | Exclusão |
| Prioridade | Alta |
| Tipo | Negativo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Fluxo alternativo |
| Requisitos cobertos | UI001 |
| Evidência no repositório | `src/main/resources/static/js/alunos.js: abrirExclusao()` |

#### Objetivo
Verificar que cancelar ou fechar o diálogo não exclui o aluno.

#### Pré-condições
- Um aluno existe e a aplicação está em execução.

#### Dados de teste
Nenhum.

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Abrir diálogo de exclusão. | O nome do aluno aparece no texto. |
| 2 | Fechar sem confirmar (botão fechar, Esc ou cancelar). | O diálogo fecha sem chamada DELETE. |
| 3 | Verificar a tabela. | O aluno permanece na lista. |

#### Pós-condições
- O registro permanece no banco.

#### Observações
Nenhuma.

### CT016 - Pesquisar e filtrar alunos combinados

| Campo | Informação |
|---|---|
| Funcionalidade | Pesquisa e filtros |
| Prioridade | Média |
| Tipo | Positivo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Combinação de condições |
| Requisitos cobertos | RF006, UI005 |
| Evidência no repositório | `src/main/resources/static/js/alunos.js: renderizarListagem()` |

#### Objetivo
Verificar pesquisa por nome/CPF/e-mail e filtro de situação em conjunto.

#### Pré-condições
- Há ao menos três alunos cadastrados com combinações de situação e nomes.
- A aplicação está em execução.

#### Dados de teste
Nenhum.

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Digitar parte do nome no campo de pesquisa. | A lista se filtra enquanto digita. |
| 2 | Selecionar filtro "Ativos". | Aparecem apenas alunos ativos cujo nome/CPF/e-mail correspondem. |
| 3 | Limpar pesquisa e filtros. | A lista retorna ao estado sem filtros e o contador mostra o total. |

#### Pós-condições
- O estado de pesquisa/filtro é restaurado corretamente.

#### Observações
Pesquisa normaliza caixa e pontuação.

### CT017 - Consultar aluno existente por ID via API REST

| Campo | Informação |
|---|---|
| Funcionalidade | Consulta por ID |
| Prioridade | Alta |
| Tipo | Positivo |
| Nível | API |
| Canal | API REST |
| Técnica de teste | Contrato de API |
| Requisitos cobertos | RF003, API003 |
| Evidência no repositório | `src/main/java/br/com/academia/alunos/controller/AlunoController.java: buscarPorId()` |

#### Objetivo
Verificar que GET `/api/alunos/{id}` retorna 200 com JSON correto para aluno existente.

#### Pré-condições
- Um aluno existe no banco.
- A aplicação está em execução.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| ID | ID válido existente | Identificador do registro |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Enviar GET `/api/alunos/{id}` com ID existente. | Retorna 200 e corpo JSON com campos do aluno. |
| 2 | Verificar campos de resposta. | Inclui `id`, `nomeCompleto`, `cpf`, `email`, `dataNascimento`, `plano`, `dataMatricula`, `ativo`. |

#### Pós-condições
- O recurso permanece no banco.

#### Observações
Nenhuma.

### CT018 - Consultar aluno inexistente por ID via API REST

| Campo | Informação |
|---|---|
| Funcionalidade | Consulta por ID |
| Prioridade | Alta |
| Tipo | Negativo |
| Nível | API |
| Canal | API REST |
| Técnica de teste | Contrato de API |
| Requisitos cobertos | RF003, API003, API008 |
| Evidência no repositório | `src/main/java/br/com/academia/alunos/exception/GlobalExceptionHandler.java: tratarAlunoNaoEncontrado()` |

#### Objetivo
Verificar que GET `/api/alunos/{id}` retorna 404 para ID não cadastrado.

#### Pré-condições
- Um ID inexistente é conhecido.
- A aplicação está em execução.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| ID | valor inexistente | Deve gerar 404 |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Enviar GET `/api/alunos/{id}` com ID inexistente. | Retorna HTTP 404. |
| 2 | Verificar o corpo de erro. | Contém `status`, `erro`, `mensagem` e `path`. |

#### Pós-condições
- O banco não é alterado.

#### Observações
Nenhuma.

### CT019 - API DELETE retorna 204 e corpo vazio

| Campo | Informação |
|---|---|
| Funcionalidade | Exclusão |
| Prioridade | Alta |
| Tipo | Positivo |
| Nível | API |
| Canal | API REST |
| Técnica de teste | Contrato de API |
| Requisitos cobertos | RF005, API005 |
| Evidência no repositório | `src/main/java/br/com/academia/alunos/controller/AlunoController.java: excluir()` |

#### Objetivo
Verificar que DELETE `/api/alunos/{id}` retorna 204 vazio em exclusão bem-sucedida.

#### Pré-condições
- Um aluno existente está cadastrado.
- A aplicação está em execução.

#### Dados de teste
| Campo | Valor | Justificativa |
|---|---|---|
| ID | ID válido existente | Registro para exclusão |

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Enviar DELETE `/api/alunos/{id}`. | Retorna HTTP 204. |
| 2 | Verificar o corpo. | Ausência de corpo. |
| 3 | Consultar GET `/api/alunos/{id}`. | Retorna 404. |

#### Pós-condições
- O aluno é removido do banco.

#### Observações
Nenhuma.

### CT020 - Persistência em H2 e reinicialização do banco

| Campo | Informação |
|---|---|
| Funcionalidade | Persistência |
| Prioridade | Média |
| Tipo | Positivo |
| Nível | Sistema |
| Canal | API REST |
| Técnica de teste | Estado |
| Requisitos cobertos | RF001, RF002, RF004, RF005 |
| Evidência no repositório | `src/main/resources/application.properties: spring.jpa.hibernate.ddl-auto=create-drop` |

#### Objetivo
Verificar que os dados são persistidos durante a execução e que o banco é reinicializado ao reiniciar a aplicação.

#### Pré-condições
- A aplicação está em execução.
- Um aluno foi cadastrado.

#### Dados de teste
Nenhum.

#### Passos e resultados esperados
| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Cadastrar um aluno. | A API retorna HTTP 201 e o aluno é listado. |
| 2 | Reiniciar a aplicação. | O banco H2 reinicia e os dados anteriores desaparecem. |
| 3 | Acessar GET `/api/alunos`. | A lista é vazia novamente. |

#### Pós-condições
- O banco em memória é reiniciado ao fechar a aplicação.

#### Observações
A configuração `create-drop` em `application.properties` define esse comportamento.

## 10. Matriz de rastreabilidade

| Requisito ou regra | Descrição | Casos de teste | Prioridade |
| ------------------ | --------- | -------------- | ---------- |
| RF001 | Cadastrar aluno | CT004, CT005 | Crítica |
| RF002 | Listar alunos | CT001, CT002, CT003 | Alta |
| RF003 | Consultar aluno por ID | CT017, CT018 | Alta |
| RF004 | Atualizar aluno | CT011, CT012, CT013 | Crítica |
| RF005 | Excluir aluno | CT014, CT015 | Crítica |
| RF006 | Pesquisar e filtrar alunos | CT016 | Média |
| RN001 | CPF único | CT009, CT012 | Crítica |
| RN002 | E-mail único (case-insensitive) | CT010, CT012 | Crítica |
| RN003 | Nome 3-100 caracteres | CT006 | Alta |
| RN004 | CPF com exatamente 11 números | CT007 | Crítica |
| RN005 | E-mail válido | CT008 | Alta |
| RN006 | Telefone opcional 10 ou 11 dígitos | CT004, CT005 | Média |
| RN007 | Data de nascimento anterior | CT004, CT005 | Alta |
| RN008 | Plano obrigatório | CT004, CT005 | Alta |
| RN009 | Data de matrícula até hoje | CT004, CT005 | Média |
| RN010 | Situação ativa padrão | CT005 | Média |
| UI001 | Confirmação antes de exclusão | CT014, CT015 | Crítica |
| API001 | POST retorna 201 | CT004, CT005 | Crítica |
| API002 | GET /api/alunos retorna 200 | CT001 | Alta |
| API003 | GET /api/alunos/{id} retorna 200/404 | CT017, CT018 | Alta |
| API004 | PUT /api/alunos/{id} retorna 200 | CT011, CT012 | Alta |
| API005 | DELETE retorna 204 | CT015 | Alta |
| API006 | Erro 400 para validação | CT006, CT007, CT008 | Alta |
| API007 | Erro 409 para duplicidade | CT009, CT010, CT013 | Crítica |
| API008 | Erro 404 para recurso não encontrado | CT018 | Alta |

## 11. Resumo da cobertura

- Total de casos: 20
- Por funcionalidade:
  - Inicialização: 3
  - Cadastro: 9
  - Atualização: 4
  - Exclusão: 2
  - Pesquisa e filtros: 1
  - API: 3
  - Persistência: 1
- Por prioridade:
  - Crítica: 8
  - Alta: 8
  - Média: 4
- Casos positivos: 10
- Casos negativos: 9
- Casos de limite: 5
- Requisitos sem cobertura: nenhum identificado dentro do escopo analisado.
- Casos bloqueados por ausência de definição: nenhum.

## 12. Lacunas, ambiguidades e riscos

- A validação de CPF é sintática apenas; não há verificação de dígito ou validade real.
- A pesquisa e filtros ocorrem somente no frontend a partir da lista já carregada; não há endpoint de backend para busca paginada.
- O frontend aplica máscara no CPF e telefone apenas para exibição, o que requer atenção ao manter o contrato de envio sem pontuação.
- O comportamento do botão de ajuda é registrado, mas não há casos específicos de conteúdo da ajuda ou navegação por teclado além do foco inicial.
- O tratamento de `HttpMessageNotReadableException` indica possíveis erros de payload JSON, mas não há caso específico de JSON mal formado no documento.
- A reinicialização do banco H2 foi inferida pela configuração `spring.jpa.hibernate.ddl-auto=create-drop` e deve ser validada em ambiente real.

