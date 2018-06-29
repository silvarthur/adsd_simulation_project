# Projeto de Simulação ADSD 2018.1

## Grupo

 - Arthur Antunes Gonçalves Dias Silva - 111210050
 - Hélisson Nascimento - 116110243
 - Wendley Paulo de França - 116210451

## Descrição

Nosso projeto consiste em um robô (gerador de requisições) que envia requisições GET e POST para um servidor e depois coleta métricas em um arquivo de log. Nosso modelo contém as seguintes entidades:

 - **Robot:** envia uma requisição genérica para a fachada.
 - **Facade:** é na fachada que é decidido qual das requisições vai ser enviada ao servidor. No nosso projeto, a requisição pode ser GET ou POST.
 - **Request:** representa a requisições GET ou POST dependendo da maneira como o objeto da classe é instanciado. 
 - **Server:** tem dois papéis. O primeiro é receber as requisições e logo após encaminhar para o banco de dados. Seu segundo papel é retornar as métricas para o robô para que ele possa enviar para o arquivo de log.
 - **Database:** receber as requisções do servidor e também enviar a resposta para o mesmo.
 - **LogFile:** recebe as métricas vinda do robô para serem salvas.
 

## Modelo
![alt text](https://uploaddeimagens.com.br/images/001/485/846/full/filas.png?1530203992)

## Métricas Coletadas

Neste  <a href="https://raw.githubusercontent.com/silvarthur/adsd_simulation_project/master/sim_report"> documento</a> se encontram as métricas coletadas durante a execução da simulação.
