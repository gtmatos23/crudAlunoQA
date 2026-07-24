# Objetivo: elaborar a especificação de casos de teste funcionais

## Papel

Atue como um Analista de Qualidade de Software Sênior, com experiência em:

* planejamento e análise de testes;
* testes funcionais de aplicações web;
* testes funcionais de APIs REST;
* projeto de testes baseado em risco;
* técnicas de caixa-preta;
* rastreabilidade entre requisitos e testes;
* revisão de requisitos e regras de negócio;
* documentação técnica para equipes de QA.

Adote profundidade e rigor compatíveis com uma disciplina de pós-graduação em Quality Assurance.

## Contexto

Analise integralmente o repositório atualmente aberto, correspondente ao sistema:

`academia-alunos-web`

O sistema implementa o caso de uso **Gerir alunos de uma academia**, contendo:

* backend Java com Spring Boot;
* API REST;
* persistência com Spring Data JPA;
* banco H2 em memória;
* interface gráfica em HTML, CSS e JavaScript;
* operações de cadastro, consulta, atualização e exclusão;
* regras de validação;
* pesquisa e filtros;
* tratamento padronizado de erros;
* elementos de usabilidade baseados nas heurísticas de Nielsen.

O repositório e seu código-fonte devem ser considerados as principais fontes de evidência. O README ajuda na compreensão, mas não deve ser utilizado como única fonte.

## Resultado esperado

Crie um único documento Markdown:

`Docs/CASOS_DE_TESTE_FUNCIONAIS.md`

Caso a pasta `Docs` não exista, crie-a.

Não crie nenhum outro artefato de teste nesta etapa.

## Escopo

A análise deve contemplar os comportamentos funcionais observáveis nas seguintes perspectivas:

* interface gráfica;
* integração entre interface e API;
* API REST;
* regras de negócio;
* validações de entrada;
* persistência no banco H2;
* mensagens de sucesso e erro;
* códigos de resposta HTTP;
* alterações de estado;
* comportamentos padrão;
* tratamento de exceções;
* fluxos positivos;
* fluxos negativos;
* valores-limite;
* cancelamentos;
* recuperação após erros.

## Fora do escopo

Não deverão ser avaliados ou documentados nesta etapa:

* desempenho;
* carga;
* estresse;
* escalabilidade;
* disponibilidade;
* segurança ofensiva;
* testes de invasão;
* análise de vulnerabilidades;
* automação de testes;
* testes unitários;
* cobertura de código;
* análise estática;
* mutação de testes;
* implantação em produção;
* compatibilidade com navegadores antigos;
* conformidade completa com normas de acessibilidade;
* avaliação estética subjetiva da interface.

Comportamentos funcionais relacionados à acessibilidade, como navegação por teclado, foco, fechamento de diálogos e apresentação de mensagens, podem ser incluídos quando estiverem implementados ou especificados no repositório.

## Restrições

Nesta atividade:

* não escreva código de teste;
* não crie scripts automatizados;
* não adicione dependências;
* não modifique o código-fonte;
* não modifique o `pom.xml`;
* não corrija defeitos encontrados;
* não altere o README;
* não execute uma campanha formal de testes;
* não classifique casos como aprovado ou reprovado;
* não produza relatório de execução;
* não produza relatório de cobertura;
* não invente requisitos inexistentes.

Se for necessário executar a aplicação para compreender um fluxo, faça apenas uma exploração controlada. Não trate essa exploração como execução formal dos casos de teste.

## Processo de análise

Antes de escrever os casos de teste, execute as seguintes atividades.

### 1. Inspeção do repositório

Analise:

* estrutura de diretórios;
* `README.md`;
* `pom.xml`;
* configurações da aplicação;
* entidade `Aluno`;
* enumeração dos planos;
* DTOs;
* Controller;
* Service;
* Repository;
* validações Jakarta;
* tratamento global de exceções;
* endpoints;
* códigos HTTP;
* página HTML;
* arquivos CSS;
* arquivos JavaScript;
* chamadas realizadas com Fetch API;
* diálogos;
* formulários;
* pesquisa;
* filtros;
* mensagens;
* estados de carregamento;
* regras de persistência.

### 2. Identificação dos requisitos

Extraia e organize:

* requisitos funcionais;
* regras de negócio;
* validações;
* valores padrão;
* restrições;
* estados possíveis;
* respostas da API;
* mensagens apresentadas ao usuário;
* dependências entre funcionalidades.

Crie identificadores para os requisitos encontrados, por exemplo:

* `RF001`: Cadastrar aluno;
* `RF002`: Listar alunos;
* `RN001`: CPF deve ser único;
* `RN002`: E-mail deve ser único;
* `VAL001`: Nome deve possuir entre 3 e 100 caracteres;
* `UI001`: Exibir confirmação antes da exclusão;
* `API001`: Retornar 201 após cadastro bem-sucedido.

Os identificadores devem ser usados na matriz de rastreabilidade.

### 3. Análise de risco

Priorize os testes considerando:

* impacto de uma falha;
* probabilidade de ocorrência;
* criticidade da regra;
* frequência esperada de uso;
* possibilidade de perda ou corrupção de dados;
* complexidade do fluxo;
* quantidade de validações;
* dependência entre interface, API e banco.

Utilize as prioridades:

* Crítica;
* Alta;
* Média;
* Baixa.

Casos relacionados ao CRUD principal, integridade dos dados, duplicidade, exclusão e validações obrigatórias devem receber prioridade compatível com seu risco.

### 4. Técnicas de projeto de testes

Utilize conscientemente as seguintes técnicas:

* particionamento de equivalência;
* análise de valor-limite;
* tabela de decisão;
* transição de estados;
* teste baseado em caso de uso;
* teste de fluxo alternativo;
* teste negativo;
* teste de contrato da API;
* combinação de condições;
* error guessing fundamentado.

Para limites numéricos ou de tamanho, considere:

* imediatamente abaixo do mínimo;
* valor mínimo;
* valor nominal válido;
* valor máximo;
* imediatamente acima do máximo.

Evite explosão combinatória. Utilize combinações representativas, justificadas pela técnica aplicada e pelo risco.

## Cobertura funcional mínima

A especificação deve contemplar, quando confirmado pelo repositório:

### Inicialização e navegação

* carregamento da página inicial;
* carregamento dos recursos estáticos;
* comunicação inicial com a API;
* indicador de carregamento;
* estado inicial com banco vazio;
* tratamento de falha ao carregar os alunos.

### Cadastro de aluno

* cadastro com todos os dados válidos;
* cadastro sem campos opcionais;
* aplicação de valores padrão;
* validação de campos obrigatórios;
* limites do nome;
* caracteres em branco no nome;
* CPF com quantidade válida e inválida de números;
* CPF contendo letras ou símbolos;
* CPF duplicado;
* formatação e remoção da máscara do CPF;
* e-mail válido;
* e-mail inválido;
* e-mail duplicado;
* telefone ausente;
* telefone com 10 números;
* telefone com 11 números;
* telefone inválido;
* data de nascimento válida;
* data de nascimento atual;
* data de nascimento futura;
* plano não selecionado;
* cada plano permitido;
* plano inexistente enviado diretamente à API;
* data de matrícula válida;
* data de matrícula omitida;
* data de matrícula futura;
* situação omitida;
* prevenção de múltiplos envios;
* mensagens de sucesso e erro.

### Listagem

* nenhum aluno cadastrado;
* um aluno cadastrado;
* vários alunos cadastrados;
* ordenação crescente pelo ID;
* apresentação dos campos;
* formatação do CPF;
* identificação textual de aluno ativo ou inativo;
* atualização dos cartões de resumo;
* atualização da tabela após operações de CRUD.

### Consulta por ID

* aluno existente;
* ID inexistente;
* ID inválido;
* resposta e estrutura do JSON;
* código HTTP correspondente.

### Atualização

* abertura do formulário preenchido;
* alteração válida;
* manutenção do identificador;
* atualização de cada campo editável;
* alteração da situação;
* manutenção do próprio CPF;
* manutenção do próprio e-mail;
* tentativa de usar CPF de outro aluno;
* tentativa de usar e-mail de outro aluno;
* validações aplicadas novamente;
* atualização de aluno inexistente;
* cancelamento da edição;
* fechamento do diálogo sem salvar;
* atualização visual após sucesso.

### Exclusão

* abertura da confirmação;
* apresentação do nome correto;
* cancelamento;
* fechamento do diálogo sem excluir;
* confirmação da exclusão;
* remoção física do registro;
* atualização da tabela;
* atualização dos cartões;
* exclusão de ID inexistente;
* mensagem de sucesso;
* tratamento de falha.

### Pesquisa e filtros

* pesquisa por nome completo;
* pesquisa por parte do nome;
* pesquisa por CPF;
* pesquisa por e-mail;
* pesquisa sem resultados;
* pesquisa com letras maiúsculas e minúsculas;
* limpeza da pesquisa;
* filtro de ativos;
* filtro de inativos;
* combinação de pesquisa e filtro;
* contador de resultados;
* retorno ao estado sem filtros.

### Interface e interação

* abertura e fechamento dos diálogos;
* botão Cancelar;
* tecla Esc;
* foco inicial;
* retorno do foco;
* botões desabilitados durante processamento;
* mensagens em região acessível;
* campos preservados após erro;
* erros apresentados próximos aos campos;
* estado vazio;
* estado sem resultado de pesquisa;
* diálogo de ajuda;
* navegação básica por teclado.

### API REST

* métodos HTTP corretos;
* URLs;
* cabeçalhos;
* `Content-Type`;
* corpo das requisições;
* corpo das respostas;
* códigos `200`, `201`, `204`, `400`, `404` e `409`;
* estrutura padronizada de erros;
* erros de validação por campo;
* ausência de corpo no `204`;
* consistência entre resposta e persistência.

### Persistência H2

* armazenamento durante a execução;
* recuperação dos dados cadastrados;
* atualização persistida;
* exclusão física;
* reinicialização do banco após encerramento da aplicação, quando esse comportamento estiver configurado.

## Estrutura obrigatória do documento

O arquivo `Docs/CASOS_DE_TESTE_FUNCIONAIS.md` deve conter as seguintes seções.

### 1. Identificação

Inclua:

* sistema;
* versão do documento;
* data;
* branch analisada;
* hash do commit analisado;
* responsável pela especificação;
* tipo de teste;
* status do documento.

Não invente o nome de uma pessoa. Utilize “A definir” caso não esteja informado.

### 2. Objetivo do documento

Explique o propósito da especificação.

### 3. Fontes de evidência

Liste os arquivos e componentes efetivamente analisados.

### 4. Escopo

Descreva o que foi incluído.

### 5. Fora do escopo

Descreva as exclusões.

### 6. Estratégia de projeto

Explique resumidamente:

* abordagem baseada em risco;
* técnicas utilizadas;
* critérios de prioridade;
* organização dos casos.

### 7. Premissas e dependências

Registre:

* ambiente necessário;
* estado do banco;
* dependências da aplicação;
* dados necessários;
* restrições identificadas.

### 8. Catálogo de dados de teste

Crie uma tabela com dados reutilizáveis.

Exemplo:

| Código | Descrição                    | Valor                   |
| ------ | ---------------------------- | ----------------------- |
| DT001  | CPF válido não cadastrado    | `12345678901`           |
| DT002  | E-mail válido não cadastrado | `ana.souza@example.com` |
| DT003  | Nome com dois caracteres     | `An`                    |

Os CPFs podem ser sintaticamente válidos e fictícios. Não utilize dados pessoais reais.

### 9. Casos de teste

Organize os casos por funcionalidade:

* Inicialização;
* Cadastro;
* Listagem;
* Consulta;
* Atualização;
* Exclusão;
* Pesquisa e filtros;
* Interface;
* API;
* Persistência.

Utilize numeração única e sequencial:

* `CT001`;
* `CT002`;
* `CT003`.

Não reinicie a numeração em cada seção.

### 10. Matriz de rastreabilidade

Relacione requisitos, regras e casos:

| Requisito ou regra | Descrição | Casos de teste | Prioridade |
| ------------------ | --------- | -------------- | ---------- |

Todo requisito funcional ou regra identificada deve possuir pelo menos um caso relacionado ou uma justificativa explícita para a ausência.

### 11. Resumo da cobertura

Apresente:

* quantidade total de casos;
* quantidade por funcionalidade;
* quantidade por prioridade;
* quantidade de casos positivos;
* quantidade de casos negativos;
* quantidade de casos de limite;
* requisitos sem cobertura;
* casos bloqueados por ausência de definição.

Não apresente percentual de cobertura de código.

### 12. Lacunas, ambiguidades e riscos

Registre:

* comportamentos não definidos;
* divergências entre README e código;
* inconsistências entre frontend e backend;
* regras ambíguas;
* mensagens sem definição;
* cenários tecnicamente difíceis de reproduzir;
* necessidade de massa ou recurso específico;
* possíveis defeitos percebidos durante a análise.

Não classifique uma suspeita como defeito confirmado sem execução e evidência suficiente. Utilize expressões como:

* “possível inconsistência”;
* “comportamento que requer esclarecimento”;
* “risco identificado”;
* “requisito não definido”.

## Modelo obrigatório de caso de teste

Utilize o seguinte formato tradicional para todos os casos:

```markdown
### CT001 - Cadastrar aluno com todos os dados válidos

| Campo | Informação |
|---|---|
| Funcionalidade | Cadastro de aluno |
| Prioridade | Crítica |
| Tipo | Positivo |
| Nível | Sistema |
| Canal | Interface gráfica |
| Técnica de teste | Particionamento de equivalência |
| Requisitos cobertos | RF001, RN001, RN002 |
| Evidência no repositório | `caminho/do/arquivo: componente ou regra` |

#### Objetivo

Verificar se um aluno é cadastrado quando todos os dados obrigatórios e opcionais são informados com valores válidos.

#### Pré-condições

- A aplicação está em execução.
- A interface está acessível.
- A API está disponível.
- O banco não possui aluno com o CPF ou e-mail utilizados.
- O formulário de cadastro está aberto.

#### Dados de teste

| Campo | Valor | Justificativa |
|---|---|---|
| Nome completo | Ana Souza Ferreira | Partição válida |
| CPF | 12345678901 | Onze caracteres numéricos |
| E-mail | ana.souza@example.com | Formato válido |
| Telefone | 31999998888 | Limite válido de onze números |
| Data de nascimento | 20/05/1995 | Data anterior à atual |
| Plano | Mensal | Valor permitido |
| Data de matrícula | Data atual | Limite válido |
| Situação | Ativo | Estado válido |

#### Passos e resultados esperados

| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Acessar a página inicial do sistema. | A página é apresentada e a lista de alunos é carregada. |
| 2 | Selecionar a opção “Novo aluno”. | O formulário de cadastro é aberto com o título “Cadastrar aluno”. |
| 3 | Preencher os campos com os dados definidos para o teste. | Os dados são aceitos e não são apresentadas mensagens de validação. |
| 4 | Selecionar “Salvar aluno”. | O botão é temporariamente desabilitado e apresenta o estado “Salvando...”. |
| 5 | Aguardar a conclusão da operação. | O aluno é persistido, o formulário é fechado e uma mensagem de sucesso é apresentada. |
| 6 | Consultar a tabela de alunos. | O novo aluno é apresentado com os dados e a situação corretos. |

#### Pós-condições

- O aluno permanece cadastrado no banco durante a execução da aplicação.
- O registro pode ser utilizado por casos de consulta, atualização ou exclusão.

#### Observações

Nenhuma.
```

## Regras para escrever os casos

Cada caso deve:

* verificar um objetivo principal;
* possuir título específico;
* possuir prioridade;
* identificar se é positivo, negativo, limite, exceção ou transição;
* informar a técnica aplicada;
* apontar os requisitos cobertos;
* indicar a evidência encontrada no repositório;
* apresentar pré-condições verificáveis;
* utilizar dados explícitos;
* possuir ações objetivas;
* possuir resultado esperado em cada passo;
* apresentar pós-condições;
* ser executável manualmente;
* ser independente sempre que possível;
* evitar dependência oculta de outro caso;
* utilizar linguagem técnica, clara e impessoal.

Não utilize resultados vagos como:

* “O sistema deve funcionar corretamente.”
* “O cadastro deve dar certo.”
* “Uma mensagem adequada deve aparecer.”
* “O sistema deve tratar o erro.”

Informe exatamente:

* qual estado deve ser apresentado;
* qual mensagem deve aparecer;
* qual código HTTP deve ser retornado;
* qual dado deve ser persistido;
* qual alteração deve ser observada;
* qual operação não deve ocorrer.

## Regras específicas para testes de API

Nos casos de API, registre:

* método HTTP;
* endpoint;
* cabeçalhos;
* corpo da requisição;
* código esperado;
* corpo esperado;
* efeito esperado no banco;
* ausência de efeitos em caso de erro.

Utilize como canal:

`API REST`

Não exija observações internas que não possam ser verificadas por resposta ou consulta posterior.

## Tratamento de ambiguidades

Quando código, interface e documentação divergirem:

1. Não escolha silenciosamente uma interpretação.
2. Registre as evidências encontradas.
3. Identifique o impacto sobre os testes.
4. Especifique o caso com status “Bloqueado por definição”, quando necessário.
5. Inclua uma pergunta objetiva na seção de lacunas.
6. Não altere o sistema.

## Revisão final obrigatória

Antes de concluir:

1. Verifique se a numeração dos casos é única e sequencial.
2. Confirme que todas as tabelas Markdown estão válidas.
3. Confirme que todos os casos possuem resultado esperado por passo.
4. Confirme que os limites foram avaliados.
5. Confirme que os fluxos negativos foram contemplados.
6. Confirme que frontend, API e persistência possuem cobertura funcional.
7. Confirme que a matriz de rastreabilidade está completa.
8. Remova casos duplicados ou sem propósito distinto.
9. Confirme que nenhum teste foi automatizado.
10. Confirme que somente o arquivo solicitado foi criado ou modificado.

## Entrega

Ao finalizar:

* salve o documento em `Docs/CASOS_DE_TESTE_FUNCIONAIS.md`;
* informe a quantidade total de casos especificados;
* informe a distribuição por prioridade;
* informe as principais funcionalidades cobertas;
* informe as lacunas encontradas;
* confirme que nenhum código de produção foi alterado;
* confirme que nenhum teste automatizado foi criado.

Não apresente apenas uma amostra. Produza a especificação completa dos casos de teste funcionais do sistema.
