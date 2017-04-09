#Teste para Ita�

##Aplica��o
A aplica��o utiliza java 8 e faz extensivo uso de lambda functions e streams. Outras tecnologias utilizadas s�o:
* Sparkjava - Um pequeno, r�pido e pr�tico framework para a cria��o de aplica��es web e APIs Rest, que inclusive conta com o servidor Jetty embutido.
* Biblioteca twitter4j, que facilita bastante a intera��o com a api do Twitter.
* Biblioteca TinyLog, a biblioteca de logs mais r�pida existente em java, que muito pouca gente conhece. Muito f�cil de ser utilizada, por�m limitada.
* JUnit, para a cria��o e execu��o de testes unit�rios.

##Container
O container ao ser inicializado, executa um script via entrypoint que:
* Inicia o banco de dados - MongoDB;
* Importa os dados utilizados para a atividade #3;
* Inicia a aplica��o java.

##Utiliza��o
Partindo do princ�pio que o Docker est� instalado na sua m�quina, � necess�rio executar os seguintes comandos:
1- Entrar na pasta do projeto.
2- Realizar o build do container atrav�s do comando: docker build -t itau docker/itau
3- Inicializar o container atrav�s do comando: docker run -it itau
4- Para simplificar, recomenda-se o uso do curl para testar a api:
	* Busca os top 5 usu�rios com mais followers: curl ip:4567/top5
	* Busca o total para as postagens em Portugu�s, para cada uma das #tag solicitadas: curl ip:4567/tagtotal
	* Busca o total de postagens, agrupadas por hora do dia (utilizando java stream): curl ip:4567/hourly
	* Busca o total de postagens, agrupadas por hora do dia (utilizando a fun��o de agrega��o do MongoDB): curl ip:4567/hourly2
	* Busca os ultimos 100 tweets para uma determinada tag: curl ip:4567/tag?%23floripa