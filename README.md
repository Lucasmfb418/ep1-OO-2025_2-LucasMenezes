🏥 Trabalho Prático – Sistema de Gerenciamento Hospitalar

🎯 Objetivo

Implementar um Sistema de Gerenciamento Hospitalar em *Java, aplicando conceitos avançados de **Programação Orientada a Objetos (POO), com foco em *herança, polimorfismo, encapsulamento, persistência de dados e regras de negócio mais complexas.

Descrição do Projeto

Desenvolvimento de um sistema de gerenciamento hospitalar utilizando os conceitos de orientação a objetos (herança, polimorfismo e encapsulamento) e persistência de dados em arquivos.

Dados do Aluno

Nome completo: Lucas Menezes Folha Brito
Matrícula: 241012300
Curso: Engenharia de Software
Turma: FGA0158 - ORIENTAÇÃO A OBJETOS - T02 (2025.2)

Instruções para Compilação e Execução

Compilação:

Navegue até a pasta raiz do projeto pelo terminal e execute o seguinte comando para compilar todos os arquivos `.java` para uma pasta `bin`:
```bash
javac -d bin src/entidades/*.java src/servicos/*.java src/sistema/*.java src/Main.java
```

Execução:

Após a compilação, execute o programa com o seguinte comando a partir da pasta raiz:
```bash
java -cp bin Main
```

Estrutura de Pastas:

O sistema foi estruturado em três pacotes principais para garantir a separação de responsabilidades (SoC) e a alta coesão, princípios fundamentais de um bom design de software:

- **`entidades`**: Contém as classes que modelam os objetos do domínio do problema. A classe abstrata `Pessoa` serve como base para `Paciente` e `Medico`, aproveitando a **Herança**. `PacienteEspecial` estende `Paciente` para adicionar comportamentos específicos de planos de saúde. Todas as classes possuem seus atributos privados, garantindo o **Encapsulamento**.

- **`servicos`**: Contém as classes que orquestram a lógica de negócio. A classe `Hospital` atua como uma fachada (*Facade*), centralizando as operações do sistema. A classe `Relatorios` isola a lógica de geração de todos os relatórios, incluindo as estatísticas avançadas.

- **`sistema`**: Contém classes de infraestrutura. `GerenciadorDeArquivos` lida com a persistência de dados e `ValidadorDeEntrada` garante a robustez da interface com o usuário.

O **Polimorfismo** é aplicado de forma chave no método `calcularDesconto()`, que possui diferentes implementações para `Paciente` e `PacienteEspecial`, permitindo que o sistema calcule o desconto correto sem precisar saber o tipo específico do paciente.

Versão do JAVA utilizada:

**Pré-requisitos:**
* Ter o JDK 21 (ou superior) instalado e configurado no `PATH` do sistema.

Vídeo de Demonstração

[Inserir o link para o vídeo no YouTube/Drive aqui]

Prints da Execução

Menu Principal:

![](2025-10-08-16-33-17.png)

Cadastro de Médico:

![](2025-10-08-16-37-06.png)

Relatório de ?:

![](2025-10-08-16-41-25.png)

Observações (Extras ou Dificuldades)

### Funcionalidades Extras Implementadas

Além dos requisitos mínimos, o projeto inclui as seguintes melhorias para enriquecer a funcionalidade e a qualidade do código:

-   **Interface de Usuário Avançada:** O sistema foi refatorado para utilizar uma navegação com sub-menus, tornando a experiência do usuário mais organizada e intuitiva. A apresentação visual dos menus também foi aprimorada para maior clareza.

-   **Sistema Robusto de Validação de Entradas:** Foi criada uma classe dedicada (`ValidadorDeEntrada`) para garantir que todas as entradas do usuário sejam válidas (ex: datas no formato correto, números em campos numéricos). Isso impede que o programa quebre e melhora a integridade dos dados.

-   **Estatísticas Avançadas:** Conforme sugerido para pontuação extra, foi adicionado um relatório de estatísticas avançadas que calcula o tempo médio de internação e a ocupação atual do hospital por especialidade médica.

### Dificuldades Enfrentadas

-   **Adaptação ao Git e Versionamento:** Sendo esta a primeira experiência utilizando Git de forma mais aprofundada, houve uma curva de aprendizado inicial para entender o fluxo de trabalho, a criação de commits com mensagens claras e a gestão das versões do código. No entanto, a experiência foi fundamental para compreender a importância do controle de versão no desenvolvimento de software.

-   **Limitações de Hardware:** O desenvolvimento foi realizado em um computador com recursos limitados, o que por vezes resultou em lentidão durante a compilação e execução do projeto, tornando o ciclo de desenvolvimento e depuração um pouco mais demorado.

Contato

E-mail: lucasfolha418@gmail.com