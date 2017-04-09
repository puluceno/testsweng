Teste para Itaú

Aplicação

A aplicação utiliza java 8 e faz extensivo uso de lambda functions e streams. Outras tecnologias utilizadas são:

 		* Sparkjava - Um pequeno, rápido e prático framework para a criação de aplicações web e APIs Rest, que inclusive conta com o servidor Jetty embutido.
		
		* Biblioteca twitter4j, que facilita bastante a interação com a api do Twitter.
		
		* Biblioteca TinyLog, a biblioteca de logs mais rápida existente em java, que muito pouca gente conhece. Muito fácil de ser utilizada, porém limitada.
		
		* JUnit, para a criação e execução de testes unitários.

Container
O container ao ser inicializado, executa um script via entrypoint que:

		* Inicia o banco de dados - MongoDB;
		
		* Importa os dados utilizados para a atividade #3;
		
		* Inicia a aplicação java.

Utilização
Partindo do princípio que o Docker está instalado na sua máquina, é necessário executar os seguintes comandos:

1- Entrar na pasta do projeto.

2- Realizar o build do container através do comando: docker build -t itau docker/itau

3- Inicializar o container através do comando: docker run -it itau

4- Para simplificar, recomenda-se o uso do curl para testar a api:

		* Busca os top 5 usuários com mais followers: curl ip:4567/top5
		* Busca o total para as postagens em Português, para cada uma das #tag solicitadas: curl ip:4567/tagtotal
		* Busca o total de postagens, agrupadas por hora do dia (utilizando java stream): curl ip:4567/hourly
		* Busca o total de postagens, agrupadas por hora do dia (utilizando a função de agregação do MongoDB): curl ip:4567/hourly2
		* Busca os ultimos 100 tweets para uma determinada tag: curl ip:4567/tag?%23floripa
