# Academia Alunos Web

## Estudo de caso: predição de defeitos e otimização de testes com Inteligência Artificial

> **Disciplina:** Fundamentos da Engenharia de Qualidade Orientada por Dados  
> **Curso:** Engenharia de Qualidade e Teste de Software  
> **Professor:** Prof. Dr. Rodrigo Fernandes dos Santos

---

## 1. Apresentação do estudo de caso

O **Academia Alunos Web** é uma aplicação didática desenvolvida para apoiar o estudo de práticas de Engenharia de Qualidade Orientada por Dados. O sistema implementa o cadastro e o gerenciamento de alunos de uma academia de musculação e atividades físicas, reunindo, em um único projeto, uma API REST em Java, persistência em banco de dados H2 e uma interface web responsiva construída com tecnologias nativas do navegador.

Embora a aplicação apresente uma estrutura funcional completa, sua versão inicial não possui testes automatizados. Essa característica é intencional: o repositório representa um sistema que precisa ser compreendido, analisado e testado a partir de suas regras de negócio, de sua arquitetura, de seus contratos de API e de seu comportamento observável.

O desafio proposto não consiste apenas em verificar se o CRUD funciona. O objetivo é investigar **como dados, evidências técnicas e ferramentas de Inteligência Artificial podem apoiar decisões de qualidade**, especialmente na identificação de riscos, na geração de casos de teste, na análise de cobertura, na rastreabilidade e na priorização da execução.

---

## 2. Situação-problema

Uma equipe de Engenharia de Qualidade recebeu uma aplicação já implementada, porém ainda sem uma estratégia formal de testes. O sistema possui regras distribuídas entre interface, DTOs, serviço, repositório, entidade e banco de dados. Além disso, determinados comportamentos dependem da interação entre diferentes camadas, como a normalização dos dados, a validação de campos, o tratamento de duplicidades e a persistência das alterações.

O tempo disponível para testes é limitado. Portanto, executar todos os cenários possíveis com a mesma prioridade não é uma decisão eficiente. Antes de testar, a equipe precisa responder a questões como:

- Quais fluxos apresentam maior probabilidade de revelar defeitos?
- Quais falhas provocariam maior impacto funcional ou operacional?
- Quais regras de negócio ainda não possuem cobertura suficiente?
- A documentação disponível representa corretamente o comportamento do código?
- Em que ordem os casos de teste devem ser executados?
- Como a Inteligência Artificial pode apoiar essa análise sem substituir o julgamento do engenheiro de qualidade?

Neste estudo de caso, o estudante assume o papel de integrante dessa equipe e deverá construir respostas fundamentadas em evidências extraídas do próprio sistema.

---

## 3. Objetivos de aprendizagem

Ao concluir a atividade, espera-se que o estudante seja capaz de:

1. compreender a arquitetura e as regras de negócio de uma aplicação web em camadas;
2. reconhecer código-fonte, requisitos, contratos de API e documentação como fontes de dados para a Engenharia de Qualidade;
3. utilizar ferramentas de Inteligência Artificial como apoio à identificação de requisitos testáveis e à geração de casos de teste;
4. analisar cobertura funcional e rastreabilidade entre regras, requisitos e casos de teste;
5. classificar cenários por risco, considerando probabilidade e impacto;
6. definir uma sequência de execução compatível com riscos e dependências de dados;
7. revisar criticamente respostas produzidas por IA, identificando inconsistências, omissões e inferências sem evidência;
8. produzir documentação de testes clara, verificável e tecnicamente fundamentada.

> [!IMPORTANT]
> A Inteligência Artificial deve ser utilizada como instrumento de apoio à análise. Toda sugestão produzida pela ferramenta precisa ser confrontada com o código-fonte, as regras de negócio e o comportamento real da aplicação.

---

## 4. Missão do estudante

O estudante deverá analisar o sistema como um **engenheiro de qualidade orientado por dados**, e não apenas como usuário da aplicação. A investigação deve relacionar cada decisão de teste a uma evidência observável.

O trabalho está organizado em quatro movimentos:

### 4.1 Compreender o sistema

- reconhecer as responsabilidades de cada camada;
- identificar os fluxos principais e alternativos;
- localizar validações, valores padrão e regras de unicidade;
- compreender os contratos de sucesso e de erro da API;
- observar como a interface transforma e envia os dados.

### 4.2 Gerar hipóteses e casos de teste com apoio de IA

- descrever o contexto da aplicação para a ferramenta;
- solicitar cenários positivos, negativos, de fronteira e de integração;
- exigir a indicação das evidências utilizadas;
- revisar cada cenário antes de incorporá-lo à documentação.

### 4.3 Analisar cobertura e risco

- relacionar requisitos, regras de negócio e casos de teste;
- identificar funcionalidades não cobertas ou sub-representadas;
- estimar probabilidade e impacto em escala definida;
- priorizar casos críticos e dependências de massa de dados.

### 4.4 Documentar e justificar as decisões

- registrar os resultados de maneira rastreável;
- separar fatos observados de hipóteses preditivas;
- indicar limitações da análise;
- propor próximos passos para execução e automação.

Um prompt estruturado foi utilizado para inspecionar o código, identificar requisitos e regras de negócio e produzir o documento [`Docs/CASOS_DE_TESTE_FUNCIONAIS.md`](Docs/CASOS_DE_TESTE_FUNCIONAIS.md), contendo casos de teste manuais e uma matriz de rastreabilidade. Esse artefato também deve ser tratado como objeto de auditoria: ter sido produzido com apoio de IA não garante que esteja completo ou correto.

---

## 5. Escopo funcional da aplicação

A aplicação permite:

- cadastrar alunos;
- listar os registros existentes;
- consultar um aluno por identificador;
- atualizar dados cadastrais;
- excluir registros;
- pesquisar por nome, CPF ou e-mail;
- filtrar alunos ativos e inativos;
- visualizar indicadores resumidos na interface;
- receber mensagens padronizadas de validação e erro.

O código contém comentários em português que explicam as responsabilidades das camadas, as anotações do Spring, a persistência com JPA, as validações, a Fetch API, os eventos da interface, os recursos de acessibilidade e as decisões relacionadas às heurísticas de Nielsen.

---

## 6. Tecnologias utilizadas

- Java 21;
- Spring Boot 3.5;
- Maven;
- Spring Web;
- Spring Data JPA;
- Jakarta Bean Validation;
- banco de dados H2 em memória;
- HTML5 semântico;
- CSS3;
- JavaScript puro;
- Fetch API.

Não são utilizados frameworks de frontend, bibliotecas de componentes, dependências por CDN, Lombok, MapStruct, Docker, autenticação ou banco de dados externo. Depois que as dependências Maven estiverem disponíveis na máquina, a aplicação pode ser executada sem acesso à internet.

---

## 7. Arquitetura da solução

A aplicação adota uma arquitetura em camadas:

```text
Navegador (HTML, CSS e JavaScript)
              │ JSON / HTTP
              ▼
Controller → Service → Repository → H2 em memória
              │
              └── DTOs, entidade e regras de negócio
```

Cada camada possui uma responsabilidade específica:

- **Controller:** recebe as requisições HTTP, valida os dados de entrada, aciona o serviço e define os códigos de resposta;
- **DTO:** representa a entrada e a saída da API sem expor diretamente a entidade persistida;
- **Service:** centraliza cadastro, consulta, atualização, exclusão, valores padrão, verificação de duplicidade e conversão entre entidade e DTO;
- **Repository:** utiliza Spring Data JPA para consultar e persistir os registros;
- **Entity:** representa a tabela `alunos` e seus respectivos tipos;
- **Exception:** transforma falhas em um contrato JSON uniforme e compreensível;
- **Static:** contém a interface web entregue pelo próprio Spring Boot.

Não existe uma interface Java separada para o serviço. A classe `AlunoService` é a implementação concreta utilizada diretamente pelo controller.

### 7.1 Perspectiva de qualidade

A separação em camadas permite investigar defeitos em diferentes níveis. Uma mesma regra pode ser aplicada pela interface, pelo DTO, pelo serviço e pelo banco de dados. Essa redundância pode aumentar a proteção do sistema, mas também pode produzir divergências entre camadas. Por esse motivo, os casos de teste não devem se limitar à interface gráfica.

---

## 8. Estrutura de diretórios

```text
academia-alunos-web/
├── .gitignore
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── br/com/academia/alunos/
        │       ├── AcademiaAlunosApplication.java
        │       ├── controller/
        │       │   └── AlunoController.java
        │       ├── dto/
        │       │   ├── AlunoRequest.java
        │       │   └── AlunoResponse.java
        │       ├── entity/
        │       │   ├── Aluno.java
        │       │   └── PlanoAcademia.java
        │       ├── exception/
        │       │   ├── AlunoNaoEncontradoException.java
        │       │   ├── ErroResposta.java
        │       │   ├── GlobalExceptionHandler.java
        │       │   └── RecursoDuplicadoException.java
        │       ├── repository/
        │       │   └── AlunoRepository.java
        │       └── service/
        │           └── AlunoService.java
        └── resources/
            ├── application.properties
            └── static/
                ├── index.html
                ├── css/
                │   └── estilos.css
                └── js/
                    └── alunos.js
```

O projeto-base não possui o diretório `src/test`. A ausência de testes é uma limitação intencional desta etapa e constitui parte do problema investigado.

---

## 9. Regras de negócio

1. O nome é obrigatório e deve possuir entre 3 e 100 caracteres.
2. O CPF é obrigatório, deve conter exatamente 11 números e deve ser único.
3. Os dígitos verificadores do CPF não são validados.
4. O e-mail é obrigatório, deve possuir formato válido e deve ser único, sem diferenciação entre letras maiúsculas e minúsculas.
5. O telefone é opcional e, quando informado, deve possuir 10 ou 11 números.
6. A data de nascimento é obrigatória e deve ser anterior à data atual.
7. O plano é obrigatório e aceita os valores `MENSAL`, `TRIMESTRAL` ou `ANUAL`.
8. A data de matrícula não pode estar no futuro. Quando omitida no cadastro, recebe a data atual.
9. Quando a situação é omitida no cadastro, o aluno é considerado ativo.
10. O ID é gerado automaticamente e não pode ser alterado.
11. A atualização verifica novamente CPF e e-mail, desconsiderando o próprio aluno na análise de duplicidade.
12. A listagem é ordenada por ID crescente.
13. A exclusão remove fisicamente o registro.
14. A consulta a um identificador inexistente produz a resposta `404 Not Found`.

As máscaras de CPF e telefone existem apenas na interface. Antes de enviar a requisição, o JavaScript remove a pontuação e transmite somente os números.

### 9.1 Questões de investigação

Ao analisar as regras, considere:

- uma validação presente na interface também existe na API?
- clientes que consomem diretamente a API recebem a mesma proteção?
- os limites inferior e superior de cada campo estão cobertos?
- as regras de unicidade permanecem válidas durante uma atualização?
- valores padrão podem ser efetivamente testados por meio da interface?

---

## 10. API REST

| Operação | Método | Endpoint | Resposta de sucesso |
| --- | --- | --- | --- |
| Cadastrar | `POST` | `/api/alunos` | `201 Created` |
| Listar | `GET` | `/api/alunos` | `200 OK` |
| Buscar por ID | `GET` | `/api/alunos/{id}` | `200 OK` |
| Atualizar | `PUT` | `/api/alunos/{id}` | `200 OK` |
| Excluir | `DELETE` | `/api/alunos/{id}` | `204 No Content` |

As falhas de validação utilizam o código `400`; aluno inexistente utiliza `404`; CPF ou e-mail duplicado utiliza `409`; e um erro interno imprevisto utiliza `500`.

Uma resposta de erro segue o formato abaixo:

```json
{
  "dataHora": "2026-07-24T14:30:00",
  "codigoHttp": 400,
  "erro": "Bad Request",
  "mensagem": "Corrija os campos destacados para continuar.",
  "caminho": "/api/alunos",
  "errosValidacao": {
    "cpf": "O CPF deve conter exatamente 11 números."
  }
}
```

> [!NOTE]
> O contrato de erro deve ser verificado como parte do comportamento funcional da API. Um cenário não está completamente testado apenas porque o código HTTP está correto; nomes de campos, conteúdo da mensagem e estrutura do corpo também precisam ser avaliados.

---

## 11. Preparação do ambiente

### 11.1 Pré-requisitos

- JDK 21 ou superior, capaz de gerar bytecode Java 21;
- Maven 3.9 ou superior.

### 11.2 Compilação

Na raiz do projeto, execute:

```bash
mvn clean package
```

O pacote executável será criado em:

```text
target/academia-alunos-web-1.0.0.jar
```

### 11.3 Execução

Durante o desenvolvimento, execute:

```bash
mvn spring-boot:run
```

Ou, depois de compilar:

```bash
java -jar target/academia-alunos-web-1.0.0.jar
```

Quando o terminal informar que a aplicação foi iniciada, acesse:

- **Interface:** [http://localhost:8080](http://localhost:8080/);
- **API:** [http://localhost:8080/api/alunos](http://localhost:8080/api/alunos);
- **Console H2:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console).

---

## 12. Utilização da interface

1. Selecione **Novo aluno** ou, quando a lista estiver vazia, **Cadastrar primeiro aluno**.
2. Preencha os campos obrigatórios, identificados pelo nome do campo e por um asterisco.
3. Selecione um plano e defina a situação da matrícula.
4. Pressione **Salvar aluno**. A lista, o contador e os três cartões serão atualizados sem recarregar a página.
5. Utilize a pesquisa para localizar nome, CPF ou e-mail e combine-a com o filtro de alunos ativos ou inativos.
6. Utilize **Editar** para carregar os valores atuais ou **Excluir** para abrir a confirmação com o nome do aluno.
7. O botão **Ajuda**, localizado no cabeçalho, apresenta uma orientação resumida sobre os fluxos disponíveis.

Os formulários podem ser utilizados integralmente pelo teclado. A tecla `Esc` fecha os diálogos, `Enter` envia o formulário quando apropriado e o foco retorna ao controle responsável pela abertura do diálogo.

---

## 13. Console do banco H2

Utilize os seguintes dados na tela de conexão:

| Campo | Valor |
| --- | --- |
| Driver Class | `org.h2.Driver` |
| JDBC URL | `jdbc:h2:mem:academiadb` |
| User Name | `sa` |
| Password | deixar em branco |

O console H2 é utilizado para fins didáticos. Os dados e a tabela são removidos quando a aplicação é encerrada e são recriados vazios na próxima inicialização.

Essa característica interfere no planejamento dos testes: cenários que dependem de massa de dados devem ser executados antes do reinício da aplicação, ou devem possuir uma estratégia própria de preparação dos registros.

---

## 14. Exemplos de requisições

### 14.1 Cadastrar um aluno

```bash
curl -i -X POST http://localhost:8080/api/alunos \
  -H 'Content-Type: application/json' \
  -d '{
    "nomeCompleto": "Ana Souza Ferreira",
    "cpf": "12345678901",
    "email": "ana.souza@example.com",
    "telefone": "31999998888",
    "dataNascimento": "1995-05-20",
    "plano": "MENSAL",
    "dataMatricula": "2025-01-15",
    "ativo": true
  }'
```

### 14.2 Listar e consultar por ID

```bash
curl -i http://localhost:8080/api/alunos
curl -i http://localhost:8080/api/alunos/1
```

### 14.3 Atualizar um aluno

```bash
curl -i -X PUT http://localhost:8080/api/alunos/1 \
  -H 'Content-Type: application/json' \
  -d '{
    "nomeCompleto": "Ana Souza Ferreira",
    "cpf": "12345678901",
    "email": "ana.souza@example.com",
    "telefone": "31988887777",
    "dataNascimento": "1995-05-20",
    "plano": "ANUAL",
    "dataMatricula": "2025-01-15",
    "ativo": true
  }'
```

### 14.4 Excluir um aluno

```bash
curl -i -X DELETE http://localhost:8080/api/alunos/1
```

Para observar a aplicação dos valores padrão pelo serviço, também é possível omitir `dataMatricula` e `ativo` durante um cadastro realizado diretamente pela API.

---

## 15. Interface e identidade visual

A interface utiliza fundo cinza muito claro, superfícies brancas, bordas discretas, sombras suaves, cantos levemente arredondados e hierarquia tipográfica acadêmica. O cabeçalho e as ações principais empregam o azul definido no projeto. Nenhum logotipo oficial é utilizado ou simulado.

### 15.1 Paleta principal

| Uso | Cor |
| --- | --- |
| Primária | `#005380` |
| Primária escura | `#003c5d` |
| Primária clara | `#74c9e3` |
| Sucesso | `#0b5e2c` |
| Perigo | `#bc1822` |
| Aviso | `#ceb608` |
| Fundo | `#f5f7f9` |
| Superfície | `#ffffff` |
| Texto | `#1f2933` |

A interface utiliza somente a pilha de fontes nativas do sistema. Em telas estreitas, cada linha da tabela é reorganizada como um cartão com rótulos, preservando os dados e as ações sem exigir rolagem horizontal.

---

## 16. Aplicação das heurísticas de Nielsen

| Heurística | Aplicação concreta no sistema |
| --- | --- |
| 1. Visibilidade do status | Carregamento, notificações, textos “Salvando...” e “Excluindo...”, contador, cartões e situação textual. |
| 2. Correspondência com o mundo real | Vocabulário relacionado a aluno, matrícula, plano, situação, cadastro, edição e exclusão; datas utilizam controles locais do navegador. |
| 3. Controle e liberdade | Cancelamento e tecla `Esc` nos diálogos, confirmação de exclusão, filtros removíveis e retorno do foco. |
| 4. Consistência e padrões | Ações equivalentes compartilham a mesma aparência; azul indica a ação principal, tom neutro cancela e vermelho exclui. |
| 5. Prevenção de erros | Validação no frontend e no backend, limites, máscaras, datas máximas, plano sem seleção válida, confirmação e bloqueio de envio duplicado. |
| 6. Reconhecimento em vez de memorização | Rótulos visíveis, lista de planos, ajuda próxima aos campos, ações textuais e dados atuais preenchidos durante a edição. |
| 7. Flexibilidade e eficiência | Pesquisa imediata, filtros, suporte ao teclado, foco automático e atualização pela Fetch API sem recarregar a página. |
| 8. Estética e design minimalista | Conteúdo principal conciso, uso equilibrado de espaços, poucas cores e ajuda apresentada em diálogo separado. |
| 9. Reconhecimento e recuperação de erros | Mensagens específicas por campo, preservação dos dados e distinção entre validação, duplicidade e indisponibilidade. |
| 10. Ajuda e documentação | Diálogo contextual e este README com fluxos, regras, API e instruções de execução. |

As heurísticas funcionam como critérios de inspeção, mas não comprovam, isoladamente, a qualidade da interface. Sua aplicação deve ser complementada por testes de usabilidade, acessibilidade e comportamento funcional.

---

## 17. Acessibilidade

A aplicação contempla os seguintes recursos:

- documento configurado em `pt-BR` e uso de HTML semântico;
- link **Pular para o conteúdo principal**;
- elementos `label` associados aos respectivos controles;
- campos obrigatórios comunicados por texto, e não apenas por símbolos;
- ajuda e mensagens de erro relacionadas por `aria-describedby`;
- notificações apresentadas em região `aria-live`;
- cabeçalhos de tabela com `scope="col"` e `caption`;
- situação representada por texto, forma e cor;
- ações com texto visível e nomes acessíveis contextualizados;
- foco visível e navegação completa pelo teclado;
- definição do foco inicial e devolução do foco nos diálogos;
- alvos de interação com tamanho confortável;
- layout fluido, compatível com zoom e telas pequenas;
- respeito à preferência `prefers-reduced-motion`.

Os recursos declarados também devem ser verificados. A presença de um atributo no código não garante, por si só, que a experiência seja acessível em diferentes navegadores e tecnologias assistivas.

---

## 18. Estratégia de análise orientada por dados

Neste estudo de caso, a expressão **orientada por dados** não se limita ao uso de grandes bases históricas. Ela se refere à utilização sistemática de evidências para fundamentar decisões de qualidade.

Entre as evidências disponíveis estão:

- quantidade e distribuição dos casos de teste;
- regras de negócio associadas a cada cenário;
- camadas atravessadas por um fluxo;
- tipos de resposta da API;
- dependências de massa de dados;
- inconsistências de rastreabilidade;
- lacunas de cobertura;
- probabilidade qualitativa de revelar um defeito;
- impacto funcional, técnico ou operacional de uma eventual falha.

Uma classificação de risco pode ser expressa por:

```text
Risco = Probabilidade × Impacto
```

Essa pontuação deve ser interpretada como **estimativa heurística para priorização**, e não como probabilidade estatística de ocorrência. Na ausência de histórico de execução, defeitos ou incidentes, o resultado representa uma hipótese sustentada por evidências estáticas e precisa ser validado em ambiente real.

---

## 19. Perguntas para reflexão e discussão

1. É possível testar completamente todas as combinações de dados e fluxos deste sistema?
2. Quais casos deveriam ser executados antes de uma liberação e por quê?
3. Um fluxo de baixa probabilidade, mas de alto impacto, pode ser adiado?
4. Quais regras estão protegidas em mais de uma camada?
5. A redundância de validações reduz riscos ou pode gerar comportamentos divergentes?
6. Quais cenários exigem pelo menos dois alunos previamente cadastrados?
7. Como o uso do banco H2 em memória interfere na ordem de execução?
8. Quais casos precisam ser executados diretamente na API para alcançar ramos que a interface não permite?
9. Como verificar se um caso de teste sugerido por IA realmente corresponde ao código?
10. Que dados históricos seriam necessários para substituir a estimativa heurística por um modelo preditivo estatístico?

---

## 20. Critérios para avaliação dos artefatos produzidos com IA

Os artefatos gerados ou revisados com apoio de Inteligência Artificial devem ser avaliados segundo os seguintes critérios:

- **correção:** o cenário corresponde ao comportamento implementado ou ao requisito declarado;
- **rastreabilidade:** a origem de cada regra, requisito ou hipótese pode ser identificada;
- **cobertura:** fluxos positivos, negativos, alternativos e de fronteira estão representados;
- **clareza:** pré-condições, passos, dados e resultados esperados não são ambíguos;
- **executabilidade:** o caso pode ser reproduzido no ambiente disponível;
- **priorização:** a classificação de risco possui justificativa verificável;
- **consistência:** identificadores, contratos e relações permanecem uniformes em todos os documentos;
- **limites da inferência:** fatos observados e hipóteses são apresentados separadamente.

> [!WARNING]
> Um documento extenso não é necessariamente um documento de qualidade. Casos duplicados, requisitos inexistentes, resultados esperados incompatíveis com o código e referências não rastreáveis devem ser tratados como falhas do processo de análise.

---

## 21. Limitações intencionais da versão inicial

- O banco de dados é temporário: todos os registros desaparecem quando o processo é encerrado.
- Não há autenticação nem autorização.
- Não existem dados iniciais.
- O projeto-base não contém testes automatizados nem dependências específicas para testes.
- A versão inicial não apresenta relatórios de execução ou de cobertura.
- Não há histórico de defeitos ou incidentes que permita calcular probabilidades estatísticas.
- Os artefatos de teste manuais produzidos posteriormente precisam ser auditados e validados.

Essas limitações não devem ser interpretadas apenas como ausências técnicas. Elas constituem parte do cenário didático e ajudam a discutir decisões reais de escopo, risco, cobertura e evolução da estratégia de qualidade.

---

## 22. Resultado acadêmico esperado

Ao final do estudo de caso, o estudante deverá ser capaz de apresentar uma estratégia de testes coerente com as evidências do sistema, demonstrando:

- compreensão das regras e da arquitetura;
- identificação dos fluxos de maior risco;
- geração e revisão crítica de casos de teste;
- análise das lacunas de cobertura;
- construção ou auditoria da matriz de rastreabilidade;
- definição justificada da ordem de execução;
- reconhecimento das contribuições e limitações da Inteligência Artificial;
- documentação clara das decisões tomadas.

O principal resultado da atividade não é a quantidade de casos gerados, mas a capacidade de **explicar por que cada cenário é necessário, qual risco ele investiga e quais evidências sustentam sua inclusão**.

---

## 23. Síntese do estudo de caso

O projeto **Academia Alunos Web** permite observar que a Engenharia de Qualidade Orientada por Dados começa antes da execução de um teste. Ela envolve compreender o produto, reunir evidências, formular hipóteses, analisar riscos, identificar lacunas e documentar decisões.

A Inteligência Artificial pode acelerar parte desse trabalho ao examinar artefatos, sugerir cenários e apoiar a organização das informações. Entretanto, a responsabilidade pela validação permanece com o profissional de qualidade.

> **A IA pode ampliar a capacidade de análise, mas somente a avaliação crítica transforma uma sugestão automática em uma decisão confiável de Engenharia de Qualidade.**
