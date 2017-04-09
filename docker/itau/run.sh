#!/bin/bash

printf "Gerando o build do container...\n"
sudo docker build -t itau . 
printf "Build gerado com sucesso!\n\n"

printf "Inicializando o container...\n\n"
sudo docker run -d itau
/bin/sleep 10
printf "Container inicializado com sucesso!\n\n"

printf "Buscando os top 5 usuários com mais followers:\n\n"
curl 172.17.0.2:4567/top5

printf "\n\n\nBuscando o total para as postagens em Português, para cada uma das #tag solicitadas:\n\n"
curl 172.17.0.2:4567/tagtotal

printf "\n\n\nBuscando o total de postagens, agrupadas por hora do dia (utilizando java stream):\n\n"
curl 172.17.0.2:4567/hourly

printf "\n\n\nBuscando o total de postagens, agrupadas por hora do dia (utilizando a função de agregação do MongoDB):\n\n"
curl 172.17.0.2:4567/hourly2

printf "\n\nFim da demonstração.\n\n"

