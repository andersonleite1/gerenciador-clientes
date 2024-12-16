# Gerenciador de Clientes

## Descrição do Projeto

Gerenciador de Clientes é uma aplicação web desenvolvida para gerenciar um cadastro de clientes, permitindo realizar operações básicas de CRUD (Create, Read, Update, Delete) e integrar-se com o serviço ViaCEP para consulta de endereços via CEP. A aplicação foi construída utilizando JavaServer Faces (JSF) para a interface do usuário, Java Persistence API (JPA) para persistência dos dados, e banco de dados MySQL "dockerizado" para armazenamento dos dados.

## Estrutura do Projeto
### Diretórios e Arquivos

```
src/main
├── java/dev/andersonleite/erp
│   ├── controller
│   │   └── ClienteBean.java
│   ├── dto
│   │   └── EnderecoDTO.java
│   ├── model
│   │   └── Cliente.java
│   ├── repository
│   │   └── ClienteRepository.java
│   ├── service
│   │   ├── ClienteService.java
│   │   └── ViaCepService.java
│   └── util
│       ├── EntityManagerProducer.java
│       ├── FacesContextProducer.java
│       ├── FacesMessages.java
│       ├── Transacional.java
│       └── TransacionalInterceptor.java
├── resources
│   ├── dev/andersonleite/erp
│   │   └── Messages.properties
│   ├── META-INF
│   │   ├── beans.xml
│   │   ├── persistence.xml
│   │   └── ValidationMessages.properties
│   └── webapp
│       ├── META-INF
│       │   └── context.xml
│       ├── resources/assets
│       │   ├── estilo.css
│       │   └── logo.png
│       ├── WEB-INF
│       │    ├── template
│       │   │   └── Layout.xhtml
│       │   ├── faces-config.xml
│       │   └── web.xml
│       └── gestao-clientes.xhtml

docker-compose.yaml
```

### Estrutura do Projeto

A estrutura do projeto segue uma organização modular, com separação de responsabilidades entre controle, modelo, serviço e utilitários:

- **Controller**: Contém os managed beans que controlam o fluxo de dados entre a UI e a camada de negócio.
- **DTO**: Objetos de Transferência de Dados usados para comunicar com serviços externos, como o ViaCEP.
- **Model**: Entidades JPA que representam os dados do sistema.
- **Repository**: Classes que lidam com a persistência dos dados.
- **Service**: Contém a lógica de negócio da aplicação.
- **Util**: Utilitários para a aplicação, como produtores de EntityManager e FacesContext, mensagens de Faces, etc.

### Arquivos de Configuração Importantes

- **beans.xml**: Configuração de CDI.
- **persistence.xml**: Configuração da persistência JPA.
- **context.xml**: Configuração do contexto do Tomcat.
- **faces-config.xml**: Configuração do JSF.
- **ValidationMessages.properties:**: Mensagens de validação customizadas.
- **Messages.properties**: Mensagens da aplicação.

## Dependências e Plugins
### Dependências
- **weld-servlet**: Implementação de CDI.
- **javax.faces**: Implementação do JavaServer Faces.
- **primefaces**: Biblioteca de componentes JSF.
- **servlet-api**: API de Servlet.
- **hibernate-validator**: Validador de dados baseado no Bean Validation.
- **hibernate-core**: ORM Hibernate.
- **mysql-connector-java**: Conector JDBC para MySQL.
- **jaxb-api**: API para JAXB.
- **poi**: Biblioteca para manipulação de documentos do Microsoft Office.
- **poi-ooxml**: Biblioteca para manipulação de documentos do Microsoft Office no formato OOXML.
- **log4j-core**: Biblioteca de logging.
- **log4j-api**: API de logging.
- **httpclient**: Biblioteca para fazer requisições HTTP.
- **gson**: Biblioteca para manipulação de JSON.

### Plugins
- **Maven Compiler Plugin**: Para compilar o projeto utilizando Java 17.

## Requisitos para Rodar a Aplicação
Antes de rodar a aplicação, certifique-se de ter os seguintes requisitos instalados no seu ambiente:

1. **Java Development Kit (JDK) 17**: A aplicação foi desenvolvida utilizando Java 17, portanto, é necessário ter o JDK 17 instalado.
2. **Apache Maven 3.6.0 ou superior**: Utilizado para gerenciar as dependências e construir o projeto.
3. **Docker**: Utilizado para subir o banco de dados MySQL através de um container.
4. **Servidor de aplicação compatível com Java EE 8 ou superior**: Eu utilizei o Apache Tomcat 9, mas você pode utilizar outros, como WildFly, etc.

## Build e Execução
Para construir e executar o projeto, siga os passos abaixo:

1. Clonar o repositório:
```
git clone https://github.com/andersonleite1/gerenciador-clientes && cd gerenciador-clientes
```

2. Construir o projeto:
```
mvn clean install
```

3. Subir o banco de dados MySQL com Docker: Certifique-se de ter o Docker instalado e em execução. No diretório do projeto, execute:
```
docker-compose up -d
```

4. Executar a aplicação: Utilize seu servidor de aplicação preferido (ex: Tomcat, WildFly). Se estiver utilizando o Tomcat, copie o arquivo WAR gerado (target/gerenciador-clientes.war) para o diretório webapps do Tomcat e inicie o servidor.


5. Acessar a aplicação: Acesse a aplicação no navegador utilizando a URL: http://localhost:8080/gerenciador-clientes


## Considerações Finais
Este projeto foi desenvolvido com o objetivo de demonstrar habilidades em desenvolvimento Java EE, utilização de Docker, e boas práticas em arquitetura MVC. É um exemplo de uma aplicação completa, abrangendo desde a camada de persistência até a interface do usuário.

## Contato
- **Nome**: Anderson Leite
- **Email**: andersonleitedev@gmail.com
- **LinkedIn**: linkedin.com/in/andersonleitedev
