package xadrez;

import tabuleiroDeXadrez.Posicao;
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
	
	private void iniciarPartida() {
		tabuleiro.LugarDaPeça(new Torre(tabuleiro, Cor.BRANCO), new Posicao(2, 1));
		tabuleiro.LugarDaPeça(new Rei(tabuleiro, Cor.PRETO), new Posicao(0, 4));
		tabuleiro.LugarDaPeça(new Rei(tabuleiro, Cor.BRANCO), new Posicao(7, 4));
	}
	
	
}
