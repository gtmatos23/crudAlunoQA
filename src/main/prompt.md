# Prompt — Especificação de casos de teste funcionais

## Objetivo

Atue como Analista de Qualidade de Software Sênior e elabore uma especificação manual completa de casos de teste funcionais para o sistema do repositório atualmente aberto.

O resultado final deve ser rastreável, executável por pessoas e fundamentado exclusivamente nas evidências encontradas no repositório. Não presuma requisitos que não estejam documentados ou implementados.

Crie somente:

`Docs/CASOS_DE_TESTE_FUNCIONAIS.md`

Não crie, renomeie ou exclua qualquer outro arquivo. Não altere código-fonte, configuração, manifesto de dependências, documentação existente ou recursos da aplicação.

## Escopo da atividade

### Incluído

Analise comportamentos funcionais observáveis nas seguintes perspectivas:

- interface gráfica;
- integração entre interface e API;
- API REST;
- regras de negócio;
- validações de entrada;
- persistência no banco H2;
- mensagens de sucesso e erro;
- códigos HTTP;
- alterações de estado;
- estados vazios e estados sem resultados;
- carregamento e processamento;
- fluxos positivos;
- fluxos negativos;
- limites;
- cancelamentos;
- transições de estado;
- recuperação após erros;
- comportamentos de acessibilidade implementados ou especificados no repositório.

### Excluído

Não inclua ou avalie:

- desempenho, carga, estresse, escalabilidade ou disponibilidade;
- segurança ofensiva, invasão ou vulnerabilidades;
- automação de testes;
- testes unitários;
- cobertura de código;
- análise estática;
- mutação de testes;
- implantação em produção;
- compatibilidade com navegadores antigos;
- conformidade completa com normas de acessibilidade;
- julgamento subjetivo de estética;
- correção de defeitos;
- execução formal da suíte;
- aprovação ou reprovação de casos.

A exploração controlada da aplicação é permitida apenas para compreender fluxos que não estejam claros no código. Não transforme essa exploração em campanha de testes ou em relatório de execução.

## Fontes de evidência

A análise deve ter o repositório como fonte principal e considerar a seguinte hierarquia:

1. código-fonte implementado;
2. configuração e persistência;
3. interface e recursos estáticos;
4. README e documentação existente.

O README não substitui a análise do código.

Antes de especificar testes, inspecione quando existirem:

- estrutura de diretórios;
- `README.md`;
- `pom.xml`;
- `application.properties`;
- classe principal;
- entidade `Aluno`;
- enumeração dos planos;
- DTOs;
- controller;
- service;
- repository;
- validações Jakarta;
- tratamento global de exceções;
- endpoints e códigos HTTP;
- página HTML;
- CSS;
- JavaScript;
- chamadas Fetch;
- formulários e diálogos;
- mensagens;
- estados de carregamento;
- pesquisa e filtros;
- regras de persistência.

Cada requisito, regra ou caso documentado deve apontar a evidência que o sustenta, preferencialmente no formato:

`src/main/java/br/com/academia/alunos/Aluno.java: campo plano`

Informe o caminho do arquivo e o componente, método, campo, constante, endpoint ou regra observada. Não cite linhas inventadas. Se não houver evidência suficiente, registre a lacuna em vez de criar um requisito.

## Processo obrigatório

Execute as etapas abaixo nesta ordem.

### 1. Identificar o estado do repositório

Antes da análise, determine:

- branch analisada;
- hash do commit analisado;
- versão do projeto;
- arquivos efetivamente examinados;
- estado do banco configurado;
- forma de inicialização da aplicação.

Se algum valor não puder ser obtido com segurança, use `Não identificado no repositório` ou `A definir`. Não invente pessoa responsável, versão de documento ou data.

### 2. Catalogar requisitos e regras

Extraia comportamentos explícitos e organize-os em categorias.

Use identificadores estáveis:

- `RFxxx`: requisito funcional;
- `RNxxx`: regra de negócio;
- `VALxxx`: validação;
- `UIxxx`: comportamento da interface;
- `APIxxx`: contrato da API;
- `PERxxx`: comportamento de persistência.

Não reutilize o mesmo identificador para conceitos diferentes. Para cada identificador, registre:

- descrição objetiva;
- evidência no repositório;
- impacto no teste;
- status:
  - `Confirmado`;
  - `Premissa`;
  - `Bloqueado por definição`;
  - `Risco identificado`.

Use `Premissa` apenas para uma interpretação plausível necessária à especificação. Registre-a explicitamente em “Premissas e dependências”. Use `Bloqueado por definição` quando uma divergência impedir um resultado esperado confiável.

### 3. Identificar divergências

Quando código, interface e documentação divergir:

- não escolha silenciosamente uma interpretação;
- registre os fatos observados separadamente;
- identifique o impacto sobre a especificação;
- escolha uma premissa quando houver base suficiente e declare-a;
- bloqueie o caso quando não houver como definir o resultado esperado;
- apresente uma pergunta objetiva na seção de lacunas.

Não descreva uma divergência como defeito confirmado sem execução e evidência suficiente.

### 4. Aplicar análise de risco

Priorize os casos pelas combinações de:

- impacto da falha;
- probabilidade de ocorrência;
- criticidade da regra;
- frequência de uso;
- risco de perda ou corrupção de dados;
- complexidade do fluxo;
- quantidade de validações;
- dependência entre interface, API e banco.

Use exclusivamente estas prioridades:

- `Crítica`: integridade, CRUD principal, unicidade, exclusão ou regra que pode impedir o uso básico;
- `Alta`: validações, persistência, contrato da API ou fluxo frequente com impacto relevante;
- `Média`: comportamentos auxiliares e fluxos alternativos;
- `Baixa`: cenários periféricos de baixo impacto.

### 5. Aplicar técnicas de projeto de testes

Use conscientemente, conforme a situação:

- particionamento de equivalência;
- análise de valor-limite;
- tabela de decisão;
- transição de estados;
- caso de uso;
- fluxo alternativo;
- teste negativo;
- contrato da API;
- combinação representativa de condições;
- error guessing fundamentado em implementações existentes.

Para limites, considere o mínimo, o máximo e os valores imediatamente adjacentes quando aplicável. Não produza explosão combinatória. Justifique combinações pouco óbvias pela técnica ou pelo risco.

## Cobertura funcional mínima

Confirme pelo repositório antes de omitir um cenário. Inclua cobertura representativa para:

### Inicialização

- carregamento da página;
- carga dos recursos estáticos;
- comunicação inicial com a API;
- indicador de carregamento;
- banco vazio;
- falha ao carregar registros.

### Cadastro

- dados válidos;
- campos opcionais;
- valores padrão;
- campos obrigatórios;
- limites do nome;
- CPF válido e inválido;
- máscaras e normalização;
- e-mail válido e inválido;
- telefone válido, ausente ou inválido;
- data de nascimento válida, atual ou futura;
- planos disponíveis e plano inválido por API;
- data de matrícula;
- situação;
- duplicidades;
- prevenção de múltiplos envios;
- sucesso e erro.

### Listagem

- nenhum, um ou vários registros;
- ordenação;
- apresentação dos campos;
- formatação;
- cartões de resumo;
- atualização após CRUD.

### Consulta

- ID existente;
- ID inexistente;
- ID inválido;
- resposta, JSON e código HTTP.

### Atualização

- abertura com dados preenchidos;
- alterações válidas;
- manutenção do identificador;
- situação;
- manutenção dos próprios CPF e e-mail;
- duplicidade causada por dados de outro aluno;
- revalidação;
- aluno inexistente;
- cancelamento;
- atualização visual.

### Exclusão

- confirmação;
- nome exibido;
- cancelamento;
- confirmação;
- remoção física;
- atualização da tabela e dos cartões;
- ID inexistente;
- falha.

### Pesquisa e filtros

- nome completo e parcial;
- CPF;
- e-mail;
- sem resultados;
- maiúsculas e minúsculas;
- limpeza;
- ativos e inativos;
- combinação de pesquisa e filtro;
- contador de resultados;
- retorno ao estado sem filtros.

### Interface

- abertura e fechamento de diálogos;
- Cancelar;
- Esc;
- foco inicial e retorno do foco;
- processamento e prevenção de reenvio;
- mensagens acessíveis;
- preservação de campos após erro;
- erros próximos aos campos;
- estado vazio;
- sem resultados;
- ajuda;
- navegação por teclado.

### API REST

- métodos e URLs;
- cabeçalhos;
- `Content-Type`;
- corpo de entrada e saída;
- códigos HTTP observados;
- estrutura de erro;
- validação por campo;
- ausência de corpo quando prevista;
- consistência entre resposta e persistência.

### Persistência

- armazenamento;
- recuperação;
- atualização;
- exclusão física;
- reinicialização do banco conforme configuração.

Não crie casos para comportamentos que não façam parte do sistema. Use a cobertura acima como guia, não como exigência mecânica.

## Estrutura obrigatória do documento

Crie o arquivo com as seções abaixo, nesta ordem.

### 1. Identificação

Inclua:

- sistema;
- versão do documento;
- data;
- branch analisada;
- hash do commit analisado;
- responsável pela especificação;
- tipo de teste;
- status do documento.

Use `A definir` para pessoa responsável não informada. Use `Não identificado no repositório` para dados técnicos não encontrados.

### 2. Objetivo do documento

Explique o propósito da especificação sem confundi-la com relatório de execução.

### 3. Fontes de evidência

Liste os arquivos efetivamente analisados e o que cada um confirmou.

### 4. Escopo

Descreva os comportamentos incluídos.

### 5. Fora do escopo

Descreva as exclusões desta atividade.

### 6. Estratégia de projeto

Resuma:

- abordagem baseada em risco;
- técnicas utilizadas;
- critérios de prioridade;
- organização dos casos;
- regra de independência dos casos.

### 7. Premissas e dependências

Registre:

- ambiente necessário;
- estado inicial do banco;
- dependências;
- dados necessários;
- restrições identificadas;
- premissas que apoiaram a especificação.

### 8. Catálogo de dados de teste

Use dados reutilizáveis em uma tabela:

| Código | Descrição | Valor |
|---|---|---|

Use dados fictícios e não use informações pessoais reais. Catálogo, formato e variações devem ser coerentes com a implementação observada.

### 9. Casos de teste

Organize os casos pelas funcionalidades:

- Inicialização;
- Cadastro;
- Listagem;
- Consulta;
- Atualização;
- Exclusão;
- Pesquisa e filtros;
- Interface;
- API;
- Persistência.

Use numeração única e sequencial:

`CT001`, `CT002`, `CT003`...

Não reinicie a numeração em cada categoria. Remova casos duplicados ou sem objetivo principal distinto.

### 10. Matriz de rastreabilidade

Relacione cada requisito, regra ou validação aos casos correspondentes:

| Requisito ou regra | Descrição | Casos de teste | Prioridade |
|---|---|---|---|

Todo requisito identificado deve possuir pelo menos um caso ou justificativa explícita de ausência de cobertura.

### 11. Resumo da cobertura

Apresente:

- quantidade total de casos;
- quantidade por funcionalidade;
- quantidade por prioridade;
- quantidade por tipo:
  - Positivo;
  - Negativo;
  - Limite;
  - Exceção;
  - Transição;
- requisitos sem cobertura;
- casos bloqueados por ausência de definição.

Não apresente percentual de cobertura de código.

### 12. Lacunas, ambiguidades e riscos

Registre:

- comportamento não definido;
- divergência entre README e código;
- inconsistência entre frontend e backend;
- regra ambígua;
- mensagem não definida;
- cenário difícil de reproduzir;
- necessidade de massa ou recurso específico;
- possível problema percebido, sempre com linguagem cautelosa;
- pergunta objetiva para o responsável esclarecer.

Use expressões como:

- `possível inconsistência`;
- `comportamento que requer esclarecimento`;
- `risco identificado`;
- `requisito não definido`.

Não confirme um defeito sem evidência de execução suficiente.

## Modelo obrigatório de caso de teste

Use o formato abaixo para todos os casos. O objetivo deve ser único e específico.

```markdown
### CT001 - Título específico

| Campo | Informação |
|---|---|
| Funcionalidade | Categoria principal |
| Prioridade | Crítica / Alta / Média / Baixa |
| Tipo | Positivo / Negativo / Limite / Exceção / Transição |
| Nível | Sistema / API / Persistência |
| Canal | Interface gráfica / API REST / Banco H2 |
| Técnica de teste | Nome da técnica aplicada |
| Requisitos cobertos | RF001, RN001, VAL001 |
| Evidência no repositório | `caminho/do/arquivo: componente, método ou regra` |
| Status | Especificado / Bloqueado por definição |

#### Objetivo

Descreva, em uma frase, o comportamento que será verificado.

#### Pré-condições

- Estado verificável da aplicação.
- Estado verificável da API.
- Estado verificável do banco.
- Estado verificável da interface.
- Dados existentes ou ausentes necessários ao caso.

Não use pré-condições vagas como `sistema funcionando corretamente`. Indique diretamente o estado esperado.

#### Dados de teste

| Campo | Valor | Justificativa |
|---|---|---|

Use dados completos e explícitos.

#### Passos e resultados esperados

| Id | Ação | Resultado esperado |
|---:|---|---|
| 1 | Ação objetiva e verificável. | Estado, mensagem, código HTTP, dado ou efeito persistido exatamente esperado. |

Todo passo deve ter uma expectativa verificável. Não use frases genéricas como `o sistema deve funcionar corretamente`, `deve dar certo`, `mensagem adequada` ou `erro deve ser tratado`.

#### Pós-condições

- Estado final verificável do banco, interface ou aplicação.

#### Observações

- Observações relevantes, premissas ou lacunas.
- Use `Nenhuma.` somente quando não houver nenhuma observação.
```

## Regras gerais para casos

Cada caso deve:

- verificar um objetivo principal;
- possuir título específico;
- indicar prioridade;
- indicar tipo;
- indicar técnica;
- apontar requisitos cobertos;
- indicar evidência encontrada;
- ter pré-condições verificáveis;
- usar dados explícitos;
- usar ações objetivas;
- apresentar resultado esperado em cada passo;
- apresentar pós-condições;
- ser executável manualmente;
- ser independente sempre que possível;
- evitar dependências ocultas;
- usar linguagem técnica, impessoal e clara.

Não reutilize implícitamente o efeito de outro caso. Quando um estado depende de cadastro prévio, declare-o diretamente nas pré-condições ou na preparação dos dados.

## Regras específicas para casos de API

Para cada caso de API, informe:

- método HTTP;
- endpoint completo;
- cabeçalhos;
- `Content-Type`;
- corpo da requisição, quando existir;
- código HTTP esperado;
- corpo da resposta esperado;
- ausência de corpo, quando prevista;
- efeito esperado no banco;
- ausência de efeitos em caso de erro;
- forma de consulta posterior para verificar persistência.

Use `API REST` no campo Canal. Não exija informações internas não observáveis pela resposta ou por consultas permitidas.

Exemplo de entrada de API:

```json
{
  "campo": "valor"
}
```

Nunca complete com campos, endpoints ou contratos que não existam no repositório.

## Regras de classificação

- Positivo: comportamento válido com persistência ou resposta esperada;
- Negativo: entrada inválida, duplicidade ou operação que não deve ocorrer;
- Limite: mínimo, máximo ou valor adjacente;
- Exceção: falha de aplicação ou recurso externo;
- Transição: mudança de estado;
- Bloqueado por definição: resultado não pode ser especificado por causa de ambiguidade.

Um caso pode ter um tipo principal e justificar uma combinação com técnicas ou observações. Evite múltiplos objetivos no mesmo caso.

## Autoauditoria obrigatória antes da entrega

Antes de concluir, execute esta revisão:

1. confirme que a numeração dos casos é única e sequencial;
2. confirme que todas as tabelas Markdown estão válidas;
3. confirme que todos os casos possuem resultado esperado em cada passo;
4. confirme que todas as seções obrigatórias estão presentes;
5. confirme que limites estão representados quando aplicáveis;
6. confirme que fluxos negativos estão contemplados;
7. confirme que interface, API e persistência têm cobertura;
8. confirme que a matriz relaciona todos os requisitos;
9. confirme que premissas e lacunas estão explícitas;
10. confirme que os casos não dependem de outro caso de forma oculta;
11. remova casos duplicados;
12. confirme que não foi criado código de teste ou script de automação;
13. confirme que somente o arquivo solicitado foi criado ou modificado;
14. confirme que nenhum arquivo de produção foi alterado;
15. confirme que cada afirmação técnica possui evidência ou foi declarada como premissa, risco ou lacuna.

## Entrega

Ao finalizar:

1. salve o documento em `Docs/CASOS_DE_TESTE_FUNCIONAIS.md`;
2. informe a quantidade total de casos especificados;
3. informe a distribuição por prioridade;
4. informe as principais funcionalidades cobertas;
5. informe as lacunas encontradas;
6. informe o resumo dos arquivos modificados;
7. confirme que nenhum código de produção foi alterado;
8. confirme que nenhum teste automatizado foi criado.

Não entregue apenas uma amostra. Produza uma especificação completa, coerente com as evidências reais do repositório.
