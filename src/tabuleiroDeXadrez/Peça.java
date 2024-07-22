package tabuleiroDeXadrez;

public class Peça {

	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	public Peça(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
}
