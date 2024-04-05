# Trabalho POO em Java
Trabalho para a disciplina de Programação Orientada a Objetos na faculdade, usando a linguagem Java e banco de dados MySQL

# Alguns passos para a instalação

1. Arquivo JAR para conectar o MySQL está dentro do diretório "Arquivo MYSQL", caso for preciso adicionar novamente;

2. O arquivo do banco de dados exportado está dentro do diretório "Banco de Dados";
	2.1. Fazer o import do arquivo "trabalho2.sql" dentro do Workbench;
	2.2. Fazer as alterações de conexão com o banco dentro da classe "ConnectionFactory";
		2.2.1. Alterar o usuário e senha;

3. O programa está no diretorio do "Trabalho2";

4. Os arquivos java para a Biblioteca que gera o relatorio estão na pasta "jfreeChart", caso for preciso adicionar novamente;
	4.1. Quando adicionar a pasta jfreeChart e ainda estiver dando erro, adicione os arquivos dentro 1 por 1;
	4.2. Assim que rodarmos o GeraRelatorio ela irá nos enviar o caminho para onde os arquivos pdf foram, copie o caminho
	     e cole ele na url do explorador de arquivos;
