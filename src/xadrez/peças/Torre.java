package xadrez.peças;

import tabuleiroDeXadrez.Posicao;
import tabuleiroDeXadrez.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaDeXadrez;

public class Torre extends PeçaDeXadrez {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		// acima
		p.setValores(posicao.getLinha()-1, posicao.getColuna());
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;						//enquanto a posicao 'p' existir e não tiver uma peça é uma posicao TRUE	
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePeçaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true; 						// enquanto a posicao 'p' existir e não tiver uma peça é uma posicao TRUE
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePeçaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true; 						// enquanto a posicao 'p' existir e não tiver uma peça é uma posicao TRUE														
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePeçaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// abaixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;						//enquanto a posicao 'p' existir e não tiver uma peça é uma posicao TRUE
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePeçaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		
		return mat;
	}

	
	
	

	
	
}
