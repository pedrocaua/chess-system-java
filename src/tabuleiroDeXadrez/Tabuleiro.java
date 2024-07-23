package tabuleiroDeXadrez;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peça[][] peças;
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {				//metodo defensivo para evitar que linhas e colunas seja maior que 1
			throw new ExcecaoDoTabuleiro("Erro criando tabuleiro: É necessário que haja pelo menos 1 linha e 1 coluna");
		}
		this.linhas = linhas;							
		this.colunas = colunas;
		peças = new Peça[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peça peça(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna)) {
			throw new ExcecaoDoTabuleiro("Posicao não está no tabuleiro");
		}
		return peças[linha][coluna];
	}
	
	public Peça peça(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new ExcecaoDoTabuleiro("Posicao não está no tabuleiro");
		}
		return peças[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void LugarDaPeça(Peça peça, Posicao posicao) {
		if (temUmaPeça(posicao)) {
			throw new ExcecaoDoTabuleiro("Já tem uma peça nessa posição " + posicao);
		}
		peças[posicao.getLinha()][posicao.getColuna()] = peça;
		peça.posicao = posicao;
	}
	
	public Peça removerPeça(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new ExcecaoDoTabuleiro("Posição não esta no tabuleiro");
		}
		if (peça(posicao) == null) {
			return null;
		}
		Peça aux = peça(posicao);
		aux.posicao = null;
		peças[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean temUmaPeça(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new ExcecaoDoTabuleiro("Posicao não está no tabuleiro");
		}
		return peça(posicao) != null;
	}
	
		
}
