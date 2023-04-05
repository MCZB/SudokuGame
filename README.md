## Sudoku Game
* SudokuGUI é uma aplicação Java que permite aos usuários jogar o popular jogo de lógica Sudoku em uma interface gráfica simples e fácil de usar. A aplicação foi desenvolvida utilizando a linguagem de programação Java, o toolkit de interface gráfica Swing e a biblioteca de resolução de Sudoku (SudokuBoard).

## Tecnologias utilizadas
* Java: A linguagem de programação Java foi escolhida para desenvolver a aplicação devido à sua portabilidade e facilidade de uso para criar interfaces gráficas.

Swing: O toolkit Swing foi utilizado para criar a interface gráfica do usuário (GUI) para a aplicação. Swing é uma biblioteca Java que fornece uma ampla variedade de componentes e controles para desenvolver interfaces gráficas ricas e interativas.

SudokuBoard: A biblioteca SudokuBoard é responsável por gerar e resolver tabuleiros de Sudoku. Ela é utilizada para fornecer a lógica de jogo e verificar se o tabuleiro foi completado corretamente.

Funcionalidades
Geração de tabuleiro
O aplicativo oferece três níveis de dificuldade (fácil, médio e difícil) para gerar um novo tabuleiro de Sudoku. Os jogadores podem selecionar um nível de dificuldade no menu "New Game" para gerar um novo tabuleiro.

Seleção e preenchimento de células
Os jogadores podem clicar em uma célula vazia no tabuleiro para selecioná-la e preenchê-la com um valor usando os botões numéricos (1-9) localizados na parte superior da interface.

Verificação de conclusão
O aplicativo verifica automaticamente se o tabuleiro foi completado corretamente após cada movimento. Se o jogador preencher todas as células do tabuleiro corretamente, uma mensagem de parabéns será exibida.

Resolução automática
Os jogadores podem utilizar o botão "Solve All" para resolver o tabuleiro automaticamente. O aplicativo irá preencher todas as células vazias com os valores corretos e verificar se o tabuleiro foi completado.

Métodos principais
actionPerformed(ActionEvent e)
Este método é responsável por lidar com eventos de ação dos itens de menu e botões da interface. Ele gerencia a geração de novos tabuleiros, a resolução automática e o fechamento da aplicação.

checkForCompletion()
Este método verifica se o tabuleiro foi completado corretamente. Se todas as células do tabuleiro estiverem preenchidas com os valores corretos, uma mensagem de parabéns será exibida.

updateBoardLabels()
Este método é responsável por atualizar os rótulos do tabuleiro com os valores das células do objeto SudokuBoard. Ele é chamado sempre que uma célula é preenchida ou o tabuleiro é resolvido automaticamente.

Instruções para compilar e executar
Compile o código Java utilizando o comando a seguir no terminal:
Copy code
javac SudokuGUI.java SudokuBoard.java
Execute a aplicação utilizando o comando a seguir no terminal:
Copy code
java SudokuGUI
Isso iniciará a aplicação e exibirá a interface gráfica do usuário, permitindo que você jogue Sudoku.
