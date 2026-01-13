# My Little Xamuel

## Trabalho realizado por:
- Luís Gomes - 31473
- David Guimarães - 31460
- Pedro Cunha - 31462

## Introdução
Neste projeto criamos um jogo baseado na aplicação “Pou”, onde o jogador tem a capacidade de cuidar de um ser de estimação. O jogo está dividido em divisões e cada uma delas tem uma característica  específica que permite ao jogador interagir com o seu animal.


## Modelo de Dados e Tecnologias Utilizadas
Este projeto utiliza a linguagem de programação Kotlin e o sistema Jetpack Compose, ele segue o modelo de programação MVVM( Model-View-ViewModel).
O login e autenticação do utilizador é efetuado através da FireBase auth e as suas informações relativas ao jogo são todas armazenadas numa database através do FireBase FireStore.


# Estrutura de Dados
O programa está estruturada nos seguintes ficheiros:

## Model
### PouEstado
Este é um ficheiro fundamental para o funcionamento do jogo sendo o responsável por declarar as variáveis respectivas ao pou, como por exemplo a higiene, o dinheiro, o nome e o estado, se ele está a dormir ou com fome.


## ViewModel
### PouViewModel
Este funciona como o cérebro da aplicação, este é o ficheiro que gere o estado do pou contendo mais especificamente:
Os timers que são responsáveis por fazer as barras de estado (barra de fome, barra de energia, barra de felicidade e barra de higiene) descerem;
As funções que mudam de sprite de acordo com o estado;
As funções que aumentam a barra de estado, por exemplo quando ele come algo
A lógica de inventário, da loja verificando por exemplo se o jogador tem dinheiro para comprar um certo item
Definição que roupa ou acessório deve estar equipado


### LoginViewModel
Esta classe é responsável pela lógica de login e armazenamento de dados do utilizador na base de dados da Firebase. Ela recebe e regista as informações de login do jogador assim como todas as informações relativas ao estado do seu animal como os itens que possui, roupas equipadas e estatísticas de saúde e mais.


## View 
### MainLayout
Responsável por definir o visual principal do jogo definindo tudo de maneira a que a criação de uma nova zona seja rápida. Contendo o código para as barras que contém a loja e o armário por exemplo, ligando se diretamente ao viewmodel.
Ela foi criada pois inicialmente cada classe desenhava individualmente os botões de cada janela, então decidimos criar um ficheiro geral para os botões e desenhos globais e evitar grandes repetições no código.


### BanhoScreen
É responsável por referenciar o sprite usado para o fundo da divisão , criando também a lógica do sabonete que começa por determinar a posição do sabonete fazendo este atualizar quando é pegado pelo utilizador de maneira a acompanhar o dedo no ecrã, quando o utilizador larga o sabonete ele volta à posição original (Offset.Zero). Também verifica se o sabonete está na mesma posição que o pou e se estiver ele chama a função tomarBanho().


### ClosetScreen
Esta é responsável por gerir o ecrã do armário que é onde o jogador personaliza o seu animal.
Este vai buscar o inventário que está no PouViewModel e separar os itens dependendo se são roupa ou acessório.
No centro do ecrã ele põe uma box que vai buscar o sprite atual do pou e a roupa/acessorio que estiver selecionada no momento e se o jogador clicar numa roupa este equipa instantaneamente a roupa selecionada.


### CozinhaScreen
Ficheiro que gere o ecrã da cozinha, fazendo o mesmo que o BanhoScreen.kt na parte da renderização do fundo porém este muda o botão do closet para o botão do frigorífico, este no meio apresenta o primeiro item relacionado a comida no inventário, de maneira a que se arrastarmos a comida o pou come essa comida e ganha pontos de “fome” fazendo essa comida desaparecer e sendo substituído pela próxima. Sendo impossível dar lhe comida quando este estiver alimentado ao máximo.


### DisplayScreen
Este ecrã é responsável pelo ecrã que apresenta os outros jogadores que deram login no jogo, indo à Firebase buscar os nomes dos jogadores. Usando uma LazyColumn para listar os players economizando memória. Mostra também uma miniatura que inclui equipadas as vestimentas.


### FridgeScreen
Fridgescreen.kt é bastante semelhante ao ClosetScreen.kt mostrando o inventário da comida, possibilitando que se use as comidas a partir deste ecrã clicando nelas.

### HomeScreen
Na HomeScreen é referenciado o sprite usado para o fundo da divisão “Sala”, neste ficheiro a lógica de um objeto diferente aos demais, ele é responsável pela criação de uma bola que o jogador pode atirar para qualquer canto da ecrã fazendo ricochete em cada borda. Com isso conseguimos aumentar a felicidade do nosso animal.

### QuartoScreen
É responsável por referenciar o sprite usado para o fundo da divisão , criando também a lógica do sabonete que começa por determinar a posição do sabonete fazendo este atualizar quando é pegado pelo utilizador de maneira a acompanhar o dedo no ecrã, quando o utilizador larga o sabonete ele volta à posição original (Offset.Zero). Também verifica se o sabonete está na mesma posição que o pou e se estiver ele chama a função tomarBanho().
 

### ShopScreen
Responsável pela criação do layout da loja e por filtrar se um item é comida, roupa ou acessório. Este verifica também se o item já está no inventário do utilizador ou não e chama o PouViewModel.kt. Usa também LazyColumn para mostrar os itens

### StatusBar
Este é responsável pelo design das barras dos valores relativos ao animal, foi criada num ficheiro separada para evitar repetições no código e para permitir uma costumização masi fácil.

### NavGraph
Este ficheiro é o responsável pela navegação do projeto e em que página é que o jogador começa.

### MainActivity
A main activity apenas configura o conteúdo(setContent) e chama o NavGraph.kt que decide qual é o ecrã inicial.

## Lista de funcionalidade da aplicação
Para a aplicação funcionar como um simulador de cuidador de pet, ela utiliza funcionalidades como um sistema de necessidades em tempo real.
Este sistema funciona à base de barras de status que representam as necessidades de fome, energia, felicidade e higiene que diminuem com o passar do tempo.

A aplicação também se aproveita de interações dinâmicas com o animal para o cuidado dele. 
Para a alimentação do ser o utilizador pode arrastar alimentos que tenha no inventário a partir da cozinha para o animal.
A limpeza do animal acontece na casa de banho e precisa que o utilizador esfregue o sabão no personagem para o limpar.
A diversão do ser acontece na sala quando o utilizador brinca com a bola.
E por fim o utilizador pode colocar o animal a dormir no quarto para recuperar energia.

Na aplicação existe uma loja onde o utilizador pode usar as moedas do jogo para comprar comida, roupas e acessórios. Também tem a funcionalidade de guarda-roupa onde o utilizador pode personalizar o ser com as roupas e acessórios comprados

No início do jogo é pedido para o utilizador fazer login com o intuito de guardar o aspeto do seu animal e poder mostrar aos outros jogadores.

## Desenhos, esquemas e protótipos da aplicação
Os sprites dos ícones, dos itens e do pet foram desenhados em pixel art no piskel, assim como os sprites das roupas e acessórios. Os cenários foram gerados por inteligência artificial. Aqui está um exemplo de cada um.


<img width="177" height="152" alt="camisola3" src="https://github.com/user-attachments/assets/ffe6c90a-8944-4560-98df-ab9401b60743" />
<img width="500" height="500" alt="item_maca" src="https://github.com/user-attachments/assets/fe7e0068-422d-4715-84d8-016a8a34c24e" />
<img width="832" height="1248" alt="cenario1" src="https://github.com/user-attachments/assets/c0fda569-e078-4bdc-9210-b5bc81e0527b" />

## Dificuldades
Tivemos dificuldades nas seguintes vertentes:
Na implementação da firebase tivemos alguns problemas na maneira como estávamos a tentar guardar os dados, inicialmente pretendíamos apenas guardar o nome do utilizador e o ID das roupas para mostrar um display com o animal de todos os jogadores mas no fim acabamos por armazenar o estado todo. Após isso tivemos um problema onde o jogo dava crash ou não mostrava os animais dos outros. Para resolver certificamos que cada variável possuia um valor predefinido e verificamos se elas estavam a ser lidas corretamente e com alguns ajuste na tela de display conseguimos corrigir esse problema.


Na implementação da lógica da bola.
Na questão da bola, de forma mais detalhada as dificuldades que tivemos foram por conta do compose por natureza ser estático, ou seja o estado tinha que estar sempre a mudar, para resolver isto calculamos o vetor do movimento em vez de fazer com que a bola mudasse constantemente de posição, permitindo para um movimento mais fluido e simples. Também nos foi difícil fazer a inércia da bola, para o fazermos aplicámos uma corotina que é uma tarefa que pode ser pausada sem bloquear o funcionamento do resto do código.
Quando o utilizador solta a bola (onDragEnd), lançamos essa corotina que continua a atualizar a posição da bola, diminuindo a velocidade gradualmente (simulando o atrito) até ela parar.

## Conclusão
Neste trabalho desenvolvemos a nossa interpretação de um jogo para cuidar de um pet. Com isto consolidamos conhecimentos em relação à Firebase e Kotlin assim como conseguimos entender melhor o modelo  de programação MVVM. Embora estejamos contentes com o resultado final achamos que ainda existem muitas melhorias que podemos fazer como a implementação de sons e minijogos para tornar o game loop ainda mais divertido.








