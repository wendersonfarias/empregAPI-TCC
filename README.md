# EmpregAPI: Conectando Talentos a Oportunidades

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13-blue)
![Docker](https://img.shields.io/badge/Docker-blue)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-orange)

### 🧠 Visão Geral do Projeto

No dinâmico mercado de trabalho atual, a agilidade para conectar candidatos qualificados a vagas de emprego é mais do que uma conveniência — é uma necessidade. Muitas plataformas existentes, embora funcionais, podem se tornar complexas tanto para quem procura uma oportunidade quanto para a empresa que deseja contratar. A **EmpregAPI** nasceu como uma resposta a esse desafio, concebida como um estudo de caso para o meu Trabalho de Conclusão de Curso.

O projeto é um backend robusto e inteligente que serve como o motor para uma plataforma de empregos, simplificando a jornada de recrutamento. A API foi desenhada com três perfis em mente:

- **Candidatos**: Podem criar perfis detalhados, buscar vagas de forma intuitiva e se candidatar com apenas um clique.
- **Empresas**: Têm a autonomia para publicar e gerenciar suas vagas, além de visualizar os perfis dos candidatos inscritos.
- **Administradores**: Possuem uma visão completa do sistema, garantindo a integridade e a qualidade da plataforma para todos os usuários.

O resultado é uma solução limpa, eficiente e pronta para ser a base de uma aplicação completa, demonstrando não apenas conhecimento técnico, mas uma visão de produto voltada para a experiência do usuário.

#### 🚀 **Explore a API ao vivo!**

A documentação completa e interativa da API está disponível via Swagger. Você pode visualizar todos os endpoints, testá-los em tempo real e entender como o sistema funciona sem precisar baixar nada.

**Acesse aqui:** [**EmpregAPI - Documentação Online (Swagger)**](https://empregapi-tcc.onrender.com/swagger-ui/index.html)

### ⚙️ Tecnologias Utilizadas

Para construir uma base sólida e moderna, foram escolhidas tecnologias líderes de mercado, garantindo performance, segurança e escalabilidade.

| Tecnologia | Versão | Propósito |
| :--- | :--- | :--- |
| **Java** | 21 | Linguagem de programação principal, oferecendo robustez e um ecossistema maduro. |
| **Spring Boot**| 3.3.1 | Framework principal que acelera o desenvolvimento, cuidando de configurações complexas e permitindo foco nas regras de negócio. |
| **Spring Security** | - | Implementa a camada de segurança, controlando quem pode acessar quais recursos (autenticação e autorização) através de tokens JWT. |
| **Spring Data JPA** | - | Facilita a comunicação com o banco de dados, transformando classes Java em tabelas e simplificando consultas. |
| **PostgreSQL** | 13 | Banco de dados relacional robusto e confiável, escolhido por sua performance e flexibilidade. |
| **Docker & Docker Compose**| - | Permite "empacotar" a aplicação e seu banco de dados em contêineres, garantindo que ela rode de forma idêntica em qualquer ambiente. |
| **Maven** | 3.9.x | Ferramenta de gerenciamento de dependências e automação de build, organizando as "peças" do projeto. |
| **Swagger (Springdoc)**| 2.5.0 | Gera a documentação da API de forma automática e interativa, essencial para o uso por outras equipes (como o frontend). |
| **RestAssured**| - | Utilizado nos testes de integração para simular requisições à API e validar se as respostas estão corretas. |

### 📐 Arquitetura e Estrutura

A EmpregAPI foi organizada seguindo as melhores práticas de design de software, com uma clara **separação de responsabilidades**. Isso não apenas torna o código mais limpo e fácil de manter, mas também mais simples de entender, mesmo para quem não tem familiaridade com o projeto.

A estrutura principal é baseada no padrão **Controller-Service-Repository**:

- `📁 /controller`: **A Porta de Entrada**
  - Responsável por receber as requisições HTTP (as chamadas que vêm da internet).
  - Ele não contém lógica de negócio; apenas direciona a chamada para a camada de serviço correspondente.
  - Exemplo: `VagaController.java` recebe a requisição para criar uma nova vaga.

- `📁 /service`: **O Cérebro da Aplicação**
  - Aqui é onde as regras de negócio são implementadas.
  - Validações, cálculos e orquestração de operações ocorrem nesta camada.
  - Exemplo: `VagaService.java` verifica se a empresa da vaga existe antes de salvá-la no banco de dados.

- `📁 /repository`: **A Conexão com a Memória**
  - Esta camada é responsável exclusivamente por interagir com o banco de dados (ler, escrever, atualizar, deletar dados).
  - Utiliza o Spring Data JPA para tornar essa comunicação simples e segura.
  - Exemplo: `VagaRepository.java` define métodos como `findById` para buscar uma vaga por seu ID.

- `📁 /dto` (Data Transfer Object): **Os Mensageiros**
  - São objetos que carregam os dados entre as camadas e, principalmente, entre o cliente (frontend) e a API.
  - Existem os `Request` (dados que chegam, ex: `VagaRequest.java`) e os `Response` (dados que são enviados de volta, ex: `VagaResponse.java`).

- `📁 /config`: **As Engrenagens do Sistema**
  - Contém classes de configuração geral, como as de segurança (`SecurityConfig.java`) e de documentação (`OpenApiConfig.java`).

### 🔒 Segurança com JWT (JSON Web Tokens)

A segurança é um pilar fundamental da EmpregAPI. Para proteger os dados e garantir que apenas usuários autorizados acessem os recursos corretos, a aplicação utiliza um fluxo de autenticação moderno e stateless (que não armazena estado de sessão no servidor).

1. **Autenticação**: O usuário (seja candidato ou empresa) envia seu e-mail e senha para o endpoint público `/api/auth/login`.
2. **Geração do Token**: Se as credenciais estiverem corretas, o Spring Security, através do `AuthService`, gera um token JWT. Esse token é como um crachá digital, assinado e com prazo de validade.
3. **Acesso Protegido**: Para acessar endpoints protegidos (como criar uma vaga ou ver seus dados de perfil), o usuário deve incluir esse token no cabeçalho `Authorization` de cada requisição.
4. **Validação**: Um filtro (`JwtAuthenticationFilter.java`) intercepta cada requisição, valida a assinatura e a validade do token e libera o acesso ao recurso solicitado.

Este modelo garante a segurança dos dados e diferencia as permissões com base nos perfis (`Role`) de cada usuário: `ROLE_ADMIN`, `ROLE_EMPRESA` e `ROLE_CANDIDATO`.

### 🧪 Testes

Para garantir a qualidade e a estabilidade da API, o projeto conta com uma suíte de testes de integração automatizados. Essa abordagem simula o uso real da aplicação, desde a requisição HTTP até a resposta, passando por todas as camadas da arquitetura.

- **Ferramentas**: Usamos **RestAssured** para criar requisições de forma fluida e **JUnit 5** como framework de testes.
- **Banco de Dados Isolado**: Os testes não rodam no banco de dados de desenvolvimento. Um contêiner Docker com um banco de dados PostgreSQL temporário é criado exclusivamente para a execução dos testes, conforme definido no `docker-compose-test.yaml`. Isso garante total isolamento e evita corrupção de dados.
- **Estratégia Limpa**: A configuração `spring.jpa.hibernate.ddl-auto=create-drop` faz com que o banco de dados seja criado do zero antes dos testes e totalmente apagado ao final. Assim, cada execução de teste é independente e previsível.
- **Exemplos**: Os arquivos `CandidatoControllerIT.java`, `EmpresaControllerIT.java` e `VagaControllerIT.java` contêm diversos cenários de teste, como a criação de um usuário, a tentativa de acesso com um token inválido e a validação de respostas de sucesso.

### 🐳 Rodando com Docker

A maneira mais simples e recomendada de executar o projeto localmente é utilizando o Docker, que garante um ambiente padronizado e sem a necessidade de instalar o Java ou o PostgreSQL manualmente na sua máquina.

Com o Docker e o Docker Compose instalados, basta executar o seguinte comando na raiz do projeto (`/empregapi`):

```bash
docker-compose up --build
```

- Construir a imagem Docker da aplicação Spring Boot.  
- Iniciar um contêiner para o banco de dados PostgreSQL.  
- Iniciar o contêiner da EmpregAPI, já configurado para se conectar ao banco.  
- A aplicação estará disponível em http://localhost:8080.

---

### 🔧 Variáveis de Ambiente e Perfis

Para se adaptar a diferentes ambientes (desenvolvimento, teste e produção), a EmpregAPI utiliza o conceito de **Spring Profiles**. Cada perfil possui um arquivo de configuração específico, permitindo, por exemplo, usar um banco de dados local em desenvolvimento e um banco na nuvem em produção.

- `application.properties`: Configurações globais, como a porta do servidor e o perfil ativo padrão (`dev`).
- `application-dev.properties`: Conecta ao banco de dados local (`localhost`) e habilita logs detalhados para facilitar o debug.
- `application-test.properties`: Aponta para o banco de dados de teste em Docker e define a estratégia `create-drop`.
- `application-prod.properties`: Prepara a aplicação para produção, esperando variáveis de ambiente para as credenciais do banco de dados e desabilitando logs excessivos.

O perfil pode ser ativado ao iniciar a aplicação com a variável de ambiente `SPRING_PROFILES_ACTIVE`.

---

### 📄 Como Contribuir

Este é um projeto acadêmico, mas contribuições e sugestões são sempre bem-vindas! Se você se interessou e quer colaborar, siga os passos abaixo:

**Clone o repositório:**

```bash
git clone https://github.com/wendersonfarias/empregapi-tcc.git
```

**Navegue até o diretório do projeto:**

```bash
cd empregapi-tcc/empregapi
```

**Suba o banco de dados de desenvolvimento:**

```bash
docker-compose up -d postgres
```

**Rode os testes para garantir que tudo está funcionando:**

```bash
mvn clean verify
```

**Crie uma nova branch para sua feature ou correção:**

```bash
git checkout -b minha-nova-feature
```

Após implementar suas mudanças, abra um Pull Request detalhando o que foi feito.

---

### 💬 Contato

Este projeto foi desenvolvido com grande dedicação por Wenderson Farias como requisito para a conclusão do curso de Engenharia de Computação. Ele reflete não apenas o aprendizado técnico adquirido ao longo da graduação, mas também a paixão por construir soluções que possam impactar positivamente a vida das pessoas.

Se você gostou do projeto, quer trocar uma ideia, colaborar em outras iniciativas ou até mesmo me oferecer uma oportunidade de carreira 😄, ficarei feliz em conversar!

**Email**: wendersonpfarias@gmail.com  
**LinkedIn**: https://www.linkedin.com/in/dev-wenderson-farias/
