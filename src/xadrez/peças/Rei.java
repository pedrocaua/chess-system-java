package xadrez.peças;

import tabuleiroDeXadrez.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaDeXadrez;

public class Rei extends PeçaDeXadrez {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	
}
