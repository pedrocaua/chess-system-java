package xadrez;

import tabuleiroDeXadrez.Tabuleiro;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class PartidaDeXadrez {			//classe onde fica as regras do jogo de xadrez

	private Tabuleiro tabuleiro;
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);		//dimensão do tabuleiro
		iniciarPartida();
	}
	
	public PeçaDeXadrez[][] getPeças(){
		PeçaDeXadrez[][] mat = new PeçaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PeçaDeXadrez) tabuleiro.peça(i, j);
			}	
		}
		return mat;
	}
	
	private void coloqueNovaPeça(char coluna, int linha, PeçaDeXadrez peça) {
		 tabuleiro.LugarDaPeça(peça, new PosicaoDeXadrez(coluna, linha).posicionar());
	}
	
	
	
	private void iniciarPartida() {
		coloqueNovaPeça('b', 6, new Torre(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('e', 8, new Rei(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('e', 1, new Rei(tabuleiro, Cor.BRANCO));
	}
	
	
}
