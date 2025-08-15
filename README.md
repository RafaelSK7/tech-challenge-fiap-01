# tech-challenge-fiap-01
Um gerenciador de usuários de um restaurante.

#Subindo a aplicação

Se estiver usando o Windows, instale o wsl for windows digitando o comando
"wsl --install" na sua prompt de comando.
importante: O comando acima irá requerer o nome de uma distribuição do Linux,
neste trabalho utilizamos a distribuição Ubuntu. Caso deseje outras distribuições
utilize o comando "wsl --list --online" para verificar todas as distribuições
disponíveis.

Após instalado, faça o download e instalação do Docker Desktop em:
"https://docs.docker.com/desktop/setup/install/windows-install/"

Com ambos os softwares instalados, navegue pelo prompt de comando até o diretório
raiz do projeto, onde há um arquivo chamado "Dockerfile" e execute o comando a seguir:

"docker build -t techchallenge/restaurantmanager:1.0 ."

Esse comando fará o download das imagens da jdk / jre descritos no Dockerfile,
assim como as demais dependencias que serão necesárias para a construção do projeto.
Importante: Esse processo pode demorar um tempo devido ao tempo de download das
imagens.

Agora, com tudo baixado e todas as layers executadas, é hora de efetetivar a subida
do projeto, execute o comando a seguir para conseguir testa-lo no navegador, no
postman, ou em qualquer outra ferramenta de mesma finalidade (curl, Inmsonia, Bruno).

"docker run -p 8080:8080 techchallenge/restaurantmanager:1.0 ."

Agora é só testar, divirta-se.



