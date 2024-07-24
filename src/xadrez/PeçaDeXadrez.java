package xadrez;

import tabuleiroDeXadrez.Peça;
import tabuleiroDeXadrez.Posicao;
import tabuleiroDeXadrez.Tabuleiro;

public abstract class PeçaDeXadrez extends Peça {

	private Cor cor;

	public PeçaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {				//não a set, pos a cor só pode ser acessada e não alterada
		return cor;
	}
	
	protected boolean existePeçaAdversaria(Posicao posicao) {
		PeçaDeXadrez p = (PeçaDeXadrez)getTabuleiro().peça(posicao);
		return p != null && p.getCor() != cor;
	}


	
	

	
	
}
