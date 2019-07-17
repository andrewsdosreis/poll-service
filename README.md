# Poll-Service

Uma solução REST backend para gerenciar Sessões de Votação de Associados

## Como rodar a aplicação

.\gradlew bootrun

Pré-requisitos: Java 8

Ao executar a aplicação localmente, a mesma irá:
- Fazer build da aplicação com o gradle;
- Conectar com MySQL que está disponível na nuvem
- Conectar com RabbitMQ que está disponível na nuvem

Para acessar local: http://localhost:8080

A aplicação também está publicada na nuvem, pode ser executada diretamente.

## Endereços:

- Poll-Service:
	- https://poll-service.herokuapp.com/

- RabbitMQ:
	- https://buck.rmq.cloudamqp.com/
	- user: xgpesumw
	- pass: 4IOdCr2OUjb6d6asNxERxMHVhUX22MBj

## Como usar o serviço

Acessar a documentação Swagger do serviço

- Pautas (topic-controller)
	- Criar uma Pauta
		- POST /topic
			- Informar o nome e uma descrição para a Pauta
			- Retorna as informações da Pauta cadastrada

	- Listar todas as Pautas cadastradas
		- GET /topic
			- Retorna as informações da todas as Pautas cadastradas
			
	- Encontra uma Pauta pelo seu ID
		- GET /topic/{id}
			- Retorna as informações de uma Pauta pelo seu ID


- Sessões de Votação (poll-controller)
	- Criar uma Sessão de Votação
		- POST /poll
			- Informar o ID da Pauta e quantos minutos a votação deve permanecer aberta
			- Retorna as informações da Sessão de Votação cadastrada
	
	- Listar todas as Sessões de Votação cadastradas
		- GET /poll
			- Retorna as informações da todas as Sessões de Votação cadastradas
			
	- Encontra uma Pauta pelo seu ID
		- GET /poll/{id}
			- Retorna as informações de uma Sessão de Votação pelo seu ID
			
	- Busca o resultado de uma Sessão de Votação
		- GET /poll/{id}/result
			- Retorna o resultado de uma Sessão de Votação pelo seu ID (Pemitido apenas quando a Sessão de Votação encerrar)

- Votos (vote-controller)
	- Votar em uma Sessão de Votação
		- POST /vote
			- Informar O ID da Votação, CPF do usuário e a opção de voto (Sim/Não)
			- Retorna as informações do Voto cadastrado

- Usuários (user-controller)
	- Listar todas os Usuários cadastrados
		- GET /topic
			- Retorna as informações da todos os Usuários cadastradas
			
	- Encontra uma Usuário pelo seu ID
		- GET /user/{id}
			- Retorna as informações de uma Usuário pelo seu ID


## Stack utilizada:

- Java e Spring Boot, por ser a stack que mais domino e pela facilidade da injeção de dependências


- Banco de dados relacional, MySQL


- Mensageria com RabbitMQ, pela familiaridade e pela simplicidade para subir e configurar na nuvem.
  Também levei em conta nesta escolha o fato de que a mensagem com o resultado da Votação é algo simples, que um MessageBroker como o rabbitMQ atende plenamente.
  Caso seja necessário enviar a mensagem para outros sistemas, podemos utilizar o poder de coreografia do rabbitMQ adicionando mais filas ao EXCHANGE que recebe a escrita da mensagem.


- A camada de negócio conta com testes de unidade que são executados a cada PUSH/PR na branch master do github


- O código foi escrito em inglês, seguindo a linha abaixo:
	- Topic -> Pauta
	- Poll	-> Sessão de Votação
	- Vote	-> Voto
	- User	-> Usuários/associados
  
  Por mais que o código fique mais elegante, tenho minhas dúvidas quanto a desenvolver totalmente em inglês. Sempre penso sobre o DDD, de Eric Evans, e o que mais gosto de todo o livro: a Ubiquitous Language.
  Será que, ao desenvolver totalmente em inglês, não estamos nos afastando dos termos tão conhecidos pelo nosso negócio?
  Em soluções menores, como esta, não vejo problemas. Porém, com um ambiente mais complexo, poderiamos gerar alguns ruídos de comunicação. Acredito que, como tudo, temos pontos positivos e negativos.


- Montei um pipeline de publicação que funciona da seguinte forma:
	1. Realizo um push/PR na branch master do repositório do GitHub
	2. Configurei o Travis CI para executar os testes e realizar build da aplicação
	3. Configurei o Heroku para, ao término da execução do build do Travis, realizar o deploy da aplicação no ambiente cloud


## Versionamento:

- Optei por adicionar na URI o versionamento para as API:
![Imagem com o versionamento das API](https://raw.githubusercontent.com/andrewsdosreis/poll-service/master/api_version.png)

## Melhorias:

- O log está sendo gravado numa pasta /logs na raiz da aplicação. Uma melhoria seria implementar um FileBeats para coletar o texto e enviar para um ambiente usando a tecnologia ELK

- Gostaria de ter implementado testes de performance para o endpoint que realiza o voto do associado, visto que como descrito seria um ponto de gargalo da aplicação.
  Algumas melhorias que poderiam ser aplicadas para garantir uma boa performance, como  implementar cache usando o REDIS: 
  Podemos implementar cache REDIS nas informações slowchange, como por exemplo a chamada REST para saber se o usuário está apto para votar, ou a validação de que um usuário JÁ REALIZOU seu voto em uma Sessão de Votação.
