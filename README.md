# Lumière d'Or
## Descrição do problema
A loja “Lumière d'Or”, que vende produtos como alianças, correntes, relógios, pulseiras, óculos e brincos, é reconhecida por oferecer peças de alto padrão com design sofisticado e acabamento refinado. Atualmente atuando exclusivamente em uma loja física, a empresa deseja expandir sua rede de clientes alcançando novos públicos por meio de canais digitais. Para isso, planeja investir na criação de uma loja virtual, visando aumentar sua visibilidade, fortalecer a marca e proporcionar uma experiência de compra prática e elegante aos consumidores em todo o país.

## Descrição da solução
Para isso, foi resolvido criar um sistema web que permitiria a ela expandir suas atuações para o resto do Brasil. O sistema terá dois tipos de usuários, o cliente que irá poder acessar o site, ver os produtos e realizar pedidos, e a equipe da loja que poderá adicionar e remover produtos.

## Tecnologias Utilizadas

### Front-End 
- **Node** - v20.5.1 ou 20.X
- **React** -  v18.3.1
- **Vite** - v5.1.0
- **MUI Components** - Biblioteca de Componentes

### Back-End
- **PostgreSQL** - vX.X.X
- **Spring Boot Java** - v3.3.1

### IDE
- VSCode

## Estrutura do Diretório

```
Lumiere-dOr/
|-- Documentacao/           # Pasta contendo todas as documentações
|   |-- PadroesAdotados/    # Pasta contendo regras de verificação
|   |-- Requisitos/         # Diagrama de Casos de Uso e Documento de Requisitos
|-- CodigoFonte/            # Código fonte do projeto
|   |-- front/
|   |   |-- public/         # Imagens e arquivos estáticos
|   |   |-- src/
|   |   |   |-- assets/     # Ícones, fontes, imagens internas, etc.
|   |   |   |-- components/ # Componentes React reutilizáveis
|   |   |-- README.md       # Explicações sobre o front-end
|   |-- back/
|   |   |-- src/
|   |   |   |-- main/       # Classes da API e todas as funcionalidades
|   |   |   |-- test/       # Códigos de teste da API
|   |   |-- pom.xml         # Dependências 
|-- README.md               # Visão geral sobre o projeto
|-- CONTRIBUTING.md         # Regras e padrões do uso do git

```

## Instalação

### Pré-requisitos

Antes de começar, certifique-se de ter instalado no seu ambiente:

- [Node.js](https://nodejs.org/) (recomendado v16 ou superior)  
- [Yarn](https://yarnpkg.com/) ou npm (gerenciador de pacotes para Node.js)  
- [Java JDK 17+](https://adoptium.net/) (ou versão compatível com seu projeto Spring)  
- [Maven](https://maven.apache.org/) (para gerenciar dependências e build do backend)

### Front-End (React com Vite)

1. Navegue até a pasta do front-end:

    ```bash
    cd CodigoFonte/front
    ```

2. Instale as dependências:

    ```bash
    npm install
    ```

3. Rode a aplicação em modo de desenvolvimento na porta 5173:

    ```bash
    npm run dev
    ```

O front-end estará disponível em [http://localhost:5173](http://localhost:5173).

### Back-End (Java Spring)

1. Navegue até a pasta do back-end:

    ```bash
    cd CodigoFonte/back
    ```

2. Compile e rode a aplicação Spring com Maven:

    No Linux/macOS:

    ```bash
    ./mvnw spring-boot:run
    ```

    No Windows:

    ```bash
    mvnw.cmd spring-boot:run
    ```

3. A API estará disponível em [http://localhost:8080](http://localhost:8080).
