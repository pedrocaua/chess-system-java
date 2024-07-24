package xadrez;

import tabuleiroDeXadrez.Peça;
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
	
	public PeçaDeXadrez executarMovimentoDeXadrez(PosicaoDeXadrez posicaoInicial, PosicaoDeXadrez posicaoDestino) {
		Posicao inicial = posicaoInicial.posicionar();
		Posicao destino = posicaoDestino.posicionar();
		validarPosicaoInicial(inicial);
		Peça peçaCapturada = façaMover(inicial, destino);
		return (PeçaDeXadrez)peçaCapturada;
	}
	
	private Peça façaMover(Posicao inicial, Posicao destino) {
		Peça p = tabuleiro.removerPeça(inicial);
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.LugarDaPeça(p, destino);
		return peçaCapturada;
	}
	
	private void validarPosicaoInicial(Posicao posicao) {
		if (!tabuleiro.temUmaPeça(posicao)) {
			throw new ExcecaoDoXadrez("Não existe peça na posição de origem");
		}
		if (!tabuleiro.peça(posicao).existeAlgumMovimentoPossivel()) {
			throw new ExcecaoDoXadrez("Não existe movimentos possiveis para peça escolhida");
		}
	}
	
	private void coloqueNovaPeça(char coluna, int linha, PeçaDeXadrez peça) {
		 tabuleiro.LugarDaPeça(peça, new PosicaoDeXadrez(coluna, linha).posicionar());
	}
	
	
	
	private void iniciarPartida() {
		coloqueNovaPeça('c', 1, new Torre(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('c', 2, new Torre(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('d', 2, new Torre(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('e', 2, new Torre(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('e', 1, new Torre(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('d', 1, new Rei(tabuleiro, Cor.BRANCO));

		coloqueNovaPeça('c', 7, new Torre(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('c', 8, new Torre(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('d', 7, new Torre(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('e', 7, new Torre(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('e', 8, new Torre(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('d', 8, new Rei(tabuleiro, Cor.PRETO));
	}
	
	
}
