# Sistema Bancário em Java (MVC + JSON + Gson)

Este é um sistema bancário desenvolvido em **Java**, utilizando o padrão de arquitetura **MVC** (Model-View-Controller), persistência de dados em **arquivos JSON** com **Gson** e uso de `RuntimeTypeAdapterFactory` para manipulação de classes abstratas e herança.

---

## Funcionalidades Principais

### Perfis de usuário

* **Administrador (Adm)**

    * Lista todos os clientes e gerentes
    * Pode cadastrar novos gerentes
    * Visualiza o total de dinheiro depositado no banco

* **Gerente**

    * Lista clientes
    * Cadastra clientes
    * Reverte transações dos clientes

* **Cliente**

    * Realiza saques e depósitos
    * Consulta saldo
    * Consulta histórico de transações

---

## Estrutura do Projeto

```
banco/
├── src/
│   ├── controller/
│   │   ├── AdmController.java
│   │   ├── AutenticacaoController.java
│   │   ├── ClienteController.java
│   │   ├── GerenteController.java
│   │   ├── SistemaController.java
│   │   └── TransacaoController.java
│   ├── model/
│   │   ├── Conta.java
│   │   ├── Transacao.java
│   │   ├── Transacionavel.java
│   │   └── usuario/
│   │       ├── Usuario.java (classe abstrata)
│   │       ├── Adm.java
│   │       ├── Cliente.java
│   │       └── Gerente.java
│   ├── util/
│   │   ├── excecoes/
│   │   ├── InputHelper.java
│   │   ├── Log.java
│   │   ├── Persistencia.java
│   │   └── RuntimeTypeAdapterFactory.java
│   └── view/
│       ├── AdmView.java
│       ├── ClienteView.java
│       ├── GerenteView.java
│       └── LoginView.java
├── dados/
│   ├── usuarios.json
│   ├── contas.json
│   └── transacoes.json
└── Main.java
```

---

## Tecnologias Utilizadas

* **Java 11+**
* **Gson** para serialização e desserialização de objetos JSON
* **RuntimeTypeAdapterFactory** para tratar polimorfismo (classes filhas de `Usuario`)
* Estrutura MVC (Model, View, Controller)

---

## Fluxo do Sistema

1. A aplicação inicia pela `Main.java`, que invoca o `SistemaController`.
2. O `SistemaController` chama a `LoginView` para solicitar as credenciais.
3. A autenticação é feita por meio do `AutenticacaoController`, que carrega os dados de `usuarios.json` usando o `Persistencia.java`.
4. Se for a primeira execução e não houver usuários cadastrados, o sistema cria automaticamente um administrador padrão:

    * **Login**: `adm`
    * **Senha**: `adm`
5. Dependendo do tipo de usuário autenticado (`Adm`, `Gerente`, `Cliente`), o sistema redireciona para a respectiva View.

---

## Comunicação entre Camadas (MVC)

A arquitetura MVC organiza o código em três camadas bem definidas, cada uma com uma responsabilidade:

* **Model (modelo de dados):**

    * Contém as entidades principais: `Usuario` (e subclasses), `Conta`, `Transacao`
    * Representa os dados e regras de negócio (ex: cálculo de saldo, verificação de senha, etc)

* **Controller (lógica de controle):**

    * Intermedia entre a View e o Model
    * Exemplos: `AdmController`, `GerenteController`, `ClienteController`
    * Manipula dados vindos da View e os aplica ao Model: cadastro, saques, depósitos, reversões de transações

* **View (interface com o usuário):**

    * É o ponto de entrada/saída com o usuário final (via terminal)
    * Exibe menus, coleta entradas com `InputHelper`, e invoca métodos nos Controllers

### Exemplo de integração

1. Usuário acessa `LoginView`, informa email e senha
2. `LoginView` chama `AutenticacaoController.login(email, senha)`
3. O controller valida os dados com base na lista carregada pela `Persistencia`
4. Após login:

    * Se for `Adm`, redireciona para `AdmView`, que interage com `AdmController`
    * `AdmView` pode listar gerentes chamando `admController.listarGerentes()`
    * O controller busca os dados dos gerentes e a View exibe ao usuário

---

## Métodos das Camadas de Controle

Abaixo estão os principais métodos utilizados nas classes `Controller`, acompanhados de uma breve descrição e os parâmetros esperados:

### AdmController.java

* `cadastrarGerente(String nome, String cpf, String email, String senha)`: Cria e adiciona um novo gerente à lista de usuários.
* `cadastrarCliente(String nome, String cpf, String email, String senha)`: Cria e adiciona um novo cliente à lista geral de usuários e clientes.
* `excluirGerentePorCPF(String cpf)`: Remove da lista o gerente com o CPF informado.
* `listarGerentes()`: Retorna a lista atual de gerentes cadastrados no sistema.
* `excluirClientePorCPF(String cpf)`: Remove da lista o cliente com o CPF informado.
* `listarClientes(String cpfGerente)`: Lista todos os clientes vinculados a um gerente específico, filtrando por CPF do gerente.
* `listarTodosClientes()`: Retorna todos os clientes cadastrados, independente do gerente.
* `calcularTotalDinheiroBanco()`: Retorna a soma de todos os saldos das contas registradas no sistema. Soma o saldo de todas as contas e retorna o valor total disponível no banco.

### AutenticacaoController.java

* `autenticar(String email, String senha)`: Verifica as credenciais informadas e retorna o objeto `Usuario` correspondente se os dados forem válidos.

### ClienteController.java

* `depositar(double valor)`: Realiza um depósito na conta do cliente.
* `sacar(double valor, String senha)`: Realiza um saque; se valor > R\$1000, exige confirmação da senha.
* `consultarSaldo()`: Retorna o saldo atual do cliente.
* `listarTransacoes()`: Retorna a lista de transações realizadas pelo cliente.
* `sacar`
* `consultarSaldo`
* `listarTransacoes`

### GerenteController.java

* `cadastrarCliente(String nome, String cpf, String email, String senha)`: Cadastra um novo cliente.
* `listarClientes()`: Retorna a lista de clientes cadastrados pelo gerente.
* `reverterTransacao(String cpf, int idTransacao)`: Reverte uma transação de um cliente específico, identificada pelo ID.
* `listarClientes`
* `reverterTransacao`

### SistemaController.java

* `iniciar()`: Inicia o sistema e gerencia o fluxo de autenticação e redirecionamento para a view adequada.
* `carregarDados()`: Carrega os dados salvos em arquivos JSON para listas em memória.
* `salvarDados()`: Persiste os dados atuais da memória nos arquivos JSON correspondentes.
* `carregarDados`
* `salvarDados`

### TransacaoController.java

* `buscarTransacaoPorId(List<Transacao> lista, int id)`: Procura e retorna uma transação específica em uma lista, com base no ID.
* `reverterTransacaoGlobal(Cliente cliente, int id)`: Reverte uma transação do cliente globalmente (usado por gerentes).
* `reverterTransacaoGlobal`

---

## Persistência de Dados

A persistência é feita via arquivos `.json`, localizados na pasta `dados/`.

* `usuarios.json`: todos os usuários (Adm, Gerente, Cliente)
* `contas.json`: dados das contas dos clientes
* `transacoes.json`: histórico de operações

Para resolver problemas de herança (como a classe abstrata `Usuario`), foi utilizado o `RuntimeTypeAdapterFactory`, permitindo que o Gson serialize e deserialize os tipos corretos (Adm, Gerente, Cliente).

---

## Como Executar

1. **Abrir no IntelliJ ou Eclipse** (certifique-se de que a biblioteca Gson esteja adicionada)
2. \*\*Executar a classe \*\*\`\`
3. O sistema criará automaticamente o administrador padrão (`adm` / `adm`) caso não haja usuários cadastrados
4. Após login, o usuário é direcionado para seu respectivo menu

---

## Interface

O sistema implementa a interface `Transacionavel`, que define métodos obrigatórios como `sacar()` e `depositar()`. Essa interface é utilizada pela classe `Cliente`, garantindo que todas as operações financeiras essenciais estejam implementadas de forma padronizada.

## Relacionamento entre Entidades

O projeto apresenta relacionamento entre as entidades, como por exemplo, o vínculo entre clientes e gerentes. Cada `Cliente` possui uma referência ao `Gerente` responsável por seu cadastro, representando uma associação direta entre os objetos.

## Log

A classe `Log.java` registra eventos importantes do sistema, como erros e operações críticas. As informações são armazenadas em arquivos `.txt`, permitindo o rastreamento de falhas e a análise do comportamento do sistema.

## Uso do ChatGPT

O ChatGPT foi utilizado como ferramenta de apoio durante o desenvolvimento do projeto, fornecendo orientação inicial sobre estruturação do sistema, sugestões de boas práticas de programação orientada a objetos, auxílio no processo de debug, correções pontuais e especialmente na escolha e implementação da persistência de dados via JSON, com recomendação do uso da biblioteca Gson.

## Superclasse

O projeto conta com uma superclasse abstrata chamada `Usuario`, que serve como base para as classes `Adm`, `Gerente` e `Cliente`. Essa superclasse centraliza atributos comuns como `nome`, `cpf`, `email` e `senha`, além de métodos compartilhados, como `login()`. Ela também define o método abstrato `exibirMenu()`, que é sobrescrito por cada subclasse com a lógica específica para o tipo de usuário. Isso promove reaproveitamento de código, organização hierárquica e facilita a aplicação de polimorfismo.

## Princípios de Programação Orientada a Objetos

### Herança

O sistema utiliza herança por meio da classe abstrata `Usuario`, que é estendida pelas classes `Adm`, `Gerente` e `Cliente`. Cada uma dessas subclasses herda atributos como nome, CPF, e-mail e senha, além de implementar comportamentos específicos como `exibirMenu()`.

### Polimorfismo

O polimorfismo é aplicado quando as views interagem com objetos do tipo `Usuario`, mas o comportamento real depende da subclasse instanciada. Por exemplo, ao autenticar um usuário, o sistema retorna um `Usuario`, mas redireciona dinamicamente para `AdmView`, `GerenteView` ou `ClienteView` conforme o tipo real. O uso do `RuntimeTypeAdapterFactory` também permite que o Gson leia/escreva corretamente objetos de subclasses de `Usuario`, mantendo o polimorfismo durante a persistência.

### Encapsulamento

As classes do modelo, como `Conta`, `Transacao` e `Usuario`, utilizam modificadores `private` para proteger seus atributos e disponibilizam `getters` e `setters` públicos para acesso controlado. Isso garante que os dados sejam acessados e modificados apenas de forma segura e padronizada.

---

## Requisitos

* Java 11 ou superior
* IDE (IntelliJ, Eclipse ou VS Code com extensões Java)
* Biblioteca Gson adicionada no classpath (ex: `gson-2.10.1.jar`)
