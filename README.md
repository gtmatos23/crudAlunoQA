# Academia Alunos Web

Aplicação web completa para gerir alunos de uma academia de musculação e
atividades físicas. O mesmo projeto reúne uma API REST em Java, persistência em
banco H2 na memória e uma interface responsiva construída com recursos nativos
do navegador.

## 1. Objetivo didático

O projeto foi escrito para aulas introdutórias de desenvolvimento e para
demonstrar como ferramentas de Inteligência Artificial podem analisar um
sistema antes de produzir artefatos de teste em uma etapa futura.

Por isso, o código contém comentários em português que explicam as
responsabilidades das camadas, as anotações do Spring, a persistência JPA, as
validações, a Fetch API, os eventos da interface, a acessibilidade e as decisões
relacionadas às heurísticas de Nielsen.

> Nesta etapa não existem testes automatizados, planos, casos, scripts,
> relatórios de teste ou relatórios de cobertura.

## 2. Tecnologias

- Java 21;
- Spring Boot 3.5;
- Maven;
- Spring Web;
- Spring Data JPA;
- Jakarta Bean Validation;
- banco H2 em memória;
- HTML5 semântico;
- CSS3;
- JavaScript puro e Fetch API.

Não são utilizados frameworks de frontend, bibliotecas de componentes,
dependências por CDN, Lombok, MapStruct, Docker, autenticação ou banco externo.
A aplicação funciona sem acesso à internet depois que as dependências Maven
estiverem disponíveis na máquina.

## 3. Arquitetura

A aplicação adota uma organização em camadas:

```text
Navegador (HTML, CSS e JavaScript)
              │ JSON / HTTP
              ▼
Controller → Service → Repository → H2 em memória
              │
              └── DTOs, entidade e regras de negócio
```

- **Controller:** recebe HTTP, valida o corpo, chama o serviço e define os
  códigos de sucesso.
- **DTO:** representa a entrada e a saída da API sem expor diretamente a
  entidade persistida.
- **Service:** centraliza cadastro, busca, atualização, exclusão, valores
  padrão, duplicidade e conversão entre entidade e DTO.
- **Repository:** usa Spring Data JPA para consultar e gravar os registros.
- **Entity:** descreve a tabela `alunos` e seus tipos.
- **Exception:** transforma falhas em um contrato JSON uniforme e amigável.
- **Static:** contém toda a interface entregue pelo próprio Spring Boot.

Não existe uma interface Java separada para o serviço: `AlunoService` é a
implementação concreta usada diretamente pelo controller.

## 4. Estrutura de diretórios

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

O projeto não possui diretório `src/test`.

## 5. Regras de negócio

1. O nome é obrigatório e deve ter entre 3 e 100 caracteres.
2. O CPF é obrigatório, deve conter exatamente 11 números e é único.
3. Os dígitos verificadores do CPF não são validados.
4. O e-mail é obrigatório, deve ter formato válido e é único sem diferenciar
   maiúsculas de minúsculas.
5. O telefone é opcional; quando informado, possui 10 ou 11 números.
6. A data de nascimento é obrigatória e anterior à data atual.
7. O plano é obrigatório e aceita `MENSAL`, `TRIMESTRAL` ou `ANUAL`.
8. A data de matrícula não pode estar no futuro; quando omitida no cadastro,
   recebe a data atual.
9. Quando a situação é omitida no cadastro, o aluno é considerado ativo.
10. O ID é automático e não pode ser alterado.
11. A atualização verifica novamente CPF e e-mail, ignorando o próprio aluno.
12. A listagem é ordenada por ID crescente.
13. A exclusão remove fisicamente o registro.
14. Um identificador inexistente produz `404 Not Found`.

As máscaras de CPF e telefone existem apenas na interface. Antes da requisição,
o JavaScript remove pontuação e envia somente números.

## 6. API REST

| Operação | Método | Endpoint | Resposta de sucesso |
|---|---|---|---|
| Cadastrar | `POST` | `/api/alunos` | `201 Created` |
| Listar | `GET` | `/api/alunos` | `200 OK` |
| Buscar | `GET` | `/api/alunos/{id}` | `200 OK` |
| Atualizar | `PUT` | `/api/alunos/{id}` | `200 OK` |
| Excluir | `DELETE` | `/api/alunos/{id}` | `204 No Content` |

Falhas de validação usam `400`, aluno inexistente usa `404`, CPF ou e-mail
duplicado usa `409` e erro interno imprevisto usa `500`.

Uma resposta de erro segue este formato:

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

## 7. Como compilar

Pré-requisitos:

- JDK 21 ou superior capaz de gerar bytecode Java 21;
- Maven 3.9 ou superior.

Na raiz do projeto, execute:

```bash
mvn clean package
```

O pacote executável será criado em:

```text
target/academia-alunos-web-1.0.0.jar
```

## 8. Como executar

Durante o desenvolvimento:

```bash
mvn spring-boot:run
```

Ou depois de compilar:

```bash
java -jar target/academia-alunos-web-1.0.0.jar
```

Quando o terminal informar que a aplicação foi iniciada, abra:

- interface: [http://localhost:8080](http://localhost:8080);
- API: [http://localhost:8080/api/alunos](http://localhost:8080/api/alunos);
- console H2: [http://localhost:8080/h2-console](http://localhost:8080/h2-console).

## 9. Como utilizar a interface

1. Selecione **Novo aluno** ou, na lista vazia, **Cadastrar primeiro aluno**.
2. Preencha os campos obrigatórios, identificados por nome e asterisco.
3. Selecione um plano e escolha a situação da matrícula.
4. Pressione **Salvar aluno**. A lista, o contador e os três cartões são
   atualizados sem recarregar a página.
5. Use a pesquisa para encontrar nome, CPF ou e-mail e combine-a com o filtro
   de alunos ativos ou inativos.
6. Use **Editar** para abrir os valores atuais ou **Excluir** para abrir a
   confirmação com o nome do aluno.
7. O botão **Ajuda** no cabeçalho apresenta uma orientação curta dos fluxos.

Os formulários podem ser usados inteiramente pelo teclado. `Esc` fecha os
diálogos, `Enter` envia o formulário quando apropriado e o foco volta ao
controle que abriu o diálogo.

## 10. Console do H2

Use os seguintes dados na tela de conexão:

| Campo | Valor |
|---|---|
| Driver Class | `org.h2.Driver` |
| JDBC URL | `jdbc:h2:mem:academiadb` |
| User Name | `sa` |
| Password | deixe vazio |

O console é uma ferramenta didática. Os dados e a tabela são removidos quando
a aplicação é encerrada e recriados vazios na próxima inicialização.

## 11. Exemplos de JSON e curl

### Cadastrar

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

### Listar e buscar

```bash
curl -i http://localhost:8080/api/alunos
curl -i http://localhost:8080/api/alunos/1
```

### Atualizar

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

### Excluir

```bash
curl -i -X DELETE http://localhost:8080/api/alunos/1
```

Para observar os valores padrão, também é possível omitir `dataMatricula` e
`ativo` durante um cadastro.

## 12. Interface e identidade visual

A interface usa fundo cinza muito claro, superfícies brancas, bordas discretas,
sombras suaves, cantos levemente arredondados e uma hierarquia tipográfica
acadêmica. O cabeçalho e as ações principais empregam o azul informado no
briefing. Nenhum logotipo oficial é utilizado ou simulado.

Paleta principal:

| Uso | Cor |
|---|---|
| Primária | `#005380` |
| Primária escura | `#003c5d` |
| Primária clara | `#74c9e3` |
| Sucesso | `#0b5e2c` |
| Perigo | `#bc1822` |
| Aviso | `#ceb608` |
| Fundo | `#f5f7f9` |
| Superfície | `#ffffff` |
| Texto | `#1f2933` |

A fonte usa somente a pilha nativa do sistema. Em telas estreitas, cada linha
da tabela se reorganiza como um cartão com rótulos, mantendo todos os dados e
ações sem exigir rolagem horizontal.

## 13. Dez heurísticas de Nielsen

| Heurística | Aplicação concreta |
|---|---|
| 1. Visibilidade do status | Carregamento, notificações, textos “Salvando...” e “Excluindo...”, contador, cartões e situação textual. |
| 2. Mundo real | Vocabulário de aluno, matrícula, plano, ativo, cadastrar, editar e excluir; datas da interface usam os controles locais do navegador. |
| 3. Controle e liberdade | Cancelamento e `Esc` nos diálogos, confirmação de exclusão, filtros removíveis e retorno do foco. |
| 4. Consistência e padrões | Ações equivalentes compartilham aparência; azul indica ação principal, neutro cancela e vermelho exclui. |
| 5. Prevenção de erros | Validação no frontend e backend, limites, máscaras, datas máximas, plano sem seleção válida, confirmação e bloqueio de envio duplo. |
| 6. Reconhecimento | Rótulos visíveis, lista de planos, ajuda próxima dos campos, ações textuais e dados atuais preenchidos na edição. |
| 7. Eficiência | Pesquisa imediata, filtro, teclado, foco automático e atualização pela Fetch API sem recarregar a página. |
| 8. Estética minimalista | Conteúdo principal conciso, espaço em branco, poucas cores e ajuda separada em diálogo. |
| 9. Recuperação de erros | Mensagens específicas por campo, dados preservados, distinção de validação, duplicidade e indisponibilidade, além de tentativa novamente. |
| 10. Ajuda | Diálogo contextual e este README com fluxos, regras, API e execução. |

## 14. Acessibilidade

- documento em `pt-BR` e HTML semântico;
- link “Pular para o conteúdo principal”;
- `label` associado a cada controle;
- campos obrigatórios comunicados por texto e não apenas por símbolo;
- ajuda e erros ligados por `aria-describedby`;
- notificações em região `aria-live`;
- cabeçalhos de tabela com `scope="col"` e `caption`;
- situação comunicada por texto, forma e cor;
- ações com texto visível e nomes acessíveis contextualizados;
- foco visível e navegação completa pelo teclado;
- foco inicial e devolução de foco nos diálogos;
- alvos de interação com tamanho confortável;
- layout fluido, compatível com zoom e telas pequenas;
- respeito à preferência `prefers-reduced-motion`.

## 15. Limitações intencionais desta etapa

- O banco é temporário: todos os registros desaparecem ao encerrar o processo.
- Não há autenticação nem autorização.
- Não há dados iniciais.
- Não existem testes automatizados, dependências de teste, plano de teste,
  scripts, relatórios ou cobertura. Esses artefatos pertencem a uma etapa
  posterior.
