# Totem de Suporte

## Tecnologias Utilizadas
* **JavaFX:** Construção da interface gráfica interativa de autoatendimento.
* **Spring Boot:** Gerenciamento da injeção de dependências e inicialização da aplicação.
* **Spring Data JPA:** Abstração da camada de acesso a dados e mapeamento objeto-relacional.
* **MySQL:** Banco de dados relacional configurado para salvar o histórico de chamados.
* **Java Print API:** Integração direta com impressoras térmicas/bobinas para emissão dos tickets.

## Funcionalidades
* **Interface Amigável:** Tela intuitiva contendo logotipo da empresa (\totem\src\main\resources\img\logo.png) e formulário claro.
* **Validação de Regras de Negócio:** Bloqueio de chamados sem nome, sem setor definido ou com descrição de problema muito curta (mínimo de 10 caracteres).
* **Geração de Ticket Impresso:** Assim que o chamado é salvo, o sistema envia automaticamente um ticket formatado sem margens para a impressora padrão do sistema operacional.
* **Pop-ups:** Feedback imediato ao usuário em caso de sucesso ou erro na validação dos campos.
* **Persistência de Dados:** Todos os chamados são armazenados automaticamente na tabela `chamados_ti` no banco de dados.

## Configuração do Banco de Dados
A aplicação está configurada para conectar a uma instância local do MySQL e criar o banco automaticamente. 
Certifique-se de que o seu serviço MySQL esteja rodando e valide as credenciais no arquivo `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/totem_mais?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
```

## Estrutura do projeto

```
totem-autoatendimento/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    └── main/
        ├── java/
        │   └── totem/
        │       └── mais/
        │           ├── TotemApplication.java
        │           ├── business/
        │           │   └── ChamadoBusiness.java
        │           ├── controller/
        │           │   ├── AlertaUtil.java
        │           │   ├── ChamadoController.java
        │           │   ├── ImpressoraUtil.java
        │           │   └── TotemMAIS.java
        │           └── infrastructure/
        │               ├── entities/
        │               │   └── Chamado.java
        │               └── repositories/
        │                   └── ChamadoRepository.java
        │
        └── resources/
            ├── application.properties
            ├── css/
            │   └── style.css
            └── img/
                └── logo.png
```
