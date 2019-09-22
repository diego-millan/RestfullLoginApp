Desafio Java Concrete Solutions
====================
API RESTful criada com três módulos.
------------------------------------
### Como testar a aplicação

Para melhor utilização da API, utilizar a aplicação Postman (a versão plugin do navegador chrome está deprecated)
Link para download e instalação do Postman, caso necessário:
https://www.getpostman.com/downloads/

Conforme solicitado, o andamento da aplicação foi comitado gradualmente neste repositório do Bitbucket, porém o bitbucket não está mais dando a possibilidade de compartilhar o link do repositório publicamente, por isso, foi adicionada também uma instância da aplicação no Github:
https://github.com/diego-millan/RestfullLoginApp

---
## Informações sobre a aplicação:

### Módulo de Cadastro:
Para enviar dados de cadastro de um novo usuário, utilizar a seguinte configuração no PostMan:
URL: http://localhost:8080/login
Request type: POST
Body (Exemplo): 
```json
	{
        "name": "João da Silva",
        "email": "joao@silva.org",
        "password": "hunter2",
        "phones": [
            {
                "number": "987654321",
                "ddd": "21"
            }
        ]
    }
```

Ao cadastrar, caso seja persistido com sucesso, retorna as informações da classe UserInfoDTO.
Informações enviadas + dados de criação e acesso do usuário + token criado.
- Senha: A senha criada é encriptada utilizando o BCryptPasswordEncoder, no método createAuthenticationToken(), no momento da criação do usuário no UserController.
- Criação do Token: O Token é criado no AuthenticationController, que por sua vez chama o CustomTokenService e cria utilizando o algorítmo de assinatura SignatureAlgorithm.HS256, a chave secreta pode ser encontrada no application.properties.

---

### Autenticação de Usuário:
Para autenticar um usuário existente no banco, utiliza-se a seguinte configuração no PostMan:
URL: http://localhost:8080/auth
Request type: POST
Body (Exemplo): 
```json
{
	"email":"joao@silva.org",
	"password":"hunter2"
}
```
Caso o email não esteja cadastrado no sistema, deve retornar o seguinte JSON:
"message": "Usuário e/ou senha inválidos."
E o status da mensagem deve ser:
400 - Bad Request

Caso o email foi encontrado, mas a senha sem encriptação não corresponder:
"message": "Usuário e/ou senha inválidos."
E o status da mensagem deve ser:
401 - Unauthorized

Ao efetuar o login com sucesso, um novo token é gerado.

---

### Consulta de usuário:
Para consulta de um usuário pelo id do mesmo, utiliza-se a seguinte configuração no PostMan:
URL: http://localhost:8080/login?id={ID_USUARIO_CADASTRADO}
Request type: GET
Header:
Key - Authorization
Value - Token válido (Recuperado tanto no resultado da criação quanto no resultado do login com sucesso).

Caso o token esteja válido e o id corresponder a um válido na tabela, retornará os mesmos dados do cadastro.
Caso o id não tenha sido inserido ou não existir, retorna a mensagem: "message": "Usuário e/ou senha inválidos."
Caso o id exista mas o token não é válido, a mensagem retornada é: "message": "Não autorizado."

### Sobre os arquivos do projeto:
- LoginApplication : Classe de inicialização do SpringApplication
- data.sql : Criando 2 registros no banco sempre que a aplicação inicia, para facilitar alguns testes.
- application.properties : Configurações do datasource, jpa, h2 e jwt podem ser encontradas lá.
- Banco de dados : Foi utilizado o H2, para acessar o console modificar o arquivo SecurityConfig e permitir acesso a URL /h2-console
- UserInfoDTO : DTO padrão para exibição de todas as informações do usuário
- TokenDTO : DTO de exibição do Token, o type está marcado como *Bearer*.
- InvalidFormDTO : DTO para exibir problemas no ValidationHandler, no momento da inserção de usuário caso algum campo esteja fora da anotação exigida.
- TokenAuthorizationFilter : Utilizado pelo securityConfig, faz o filtro interno para validar se a requisição está autenticada.