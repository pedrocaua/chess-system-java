package xadrez;

import tabuleiroDeXadrez.Posicao;

public class PosicaoDeXadrez {

	private char coluna;
	private int linha;
	
	public PosicaoDeXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new ExcecaoDoXadrez("Erro instanciando ExcecaoDoXadrez. Valores validos são a1 até h8");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}
	
	protected Posicao posicionar() {
		return new Posicao(8- linha, coluna - 'a');
	}
	
	protected static PosicaoDeXadrez daPosicao(Posicao posicao) {
		return new  PosicaoDeXadrez((char)('a' + posicao.getColuna()), 8 - posicao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha; 
	}
	
	
	
}
