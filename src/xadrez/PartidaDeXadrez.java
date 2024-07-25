package xadrez;

import java.util.ArrayList;
import java.util.List;

import tabuleiroDeXadrez.Peça;
import tabuleiroDeXadrez.Posicao;
import tabuleiroDeXadrez.Tabuleiro;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class PartidaDeXadrez {			//classe onde fica as regras do jogo de xadrez

	private int vez;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	
	private List<Peça> peçasNoTabuleiro = new ArrayList<>();
	private List<Peça> peçasCapturadas = new ArrayList<>();
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);		//dimensão do tabuleiro
		vez = 1;
		jogadorAtual = Cor.BRANCO;
		iniciarPartida();
	}
	
	public int getVez() {
		return vez;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
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
	
	public boolean[][] possiveisMovimentos(PosicaoDeXadrez posicaoInicial){
		Posicao posicao = posicaoInicial.posicionar();
		validarPosicaoInicial(posicao);
		return tabuleiro.peça(posicao).possiveisMovimentos(); 
	}
	
	public PeçaDeXadrez executarMovimentoDeXadrez(PosicaoDeXadrez posicaoInicial, PosicaoDeXadrez posicaoDestino) {
		Posicao inicial = posicaoInicial.posicionar();
		Posicao destino = posicaoDestino.posicionar();
		validarPosicaoInicial(inicial);
		validarPosicaoDestino(inicial, destino);
		Peça peçaCapturada = façaMover(inicial, destino);
		proximoVez();
		return (PeçaDeXadrez)peçaCapturada;
	}
	
	private Peça façaMover(Posicao inicial, Posicao destino) {		//tira da origem e põe no destino
		Peça p = tabuleiro.removerPeça(inicial);
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.LugarDaPeça(p, destino);
		
		if (peçaCapturada != null) {
			peçasNoTabuleiro.remove(peçaCapturada);
			peçasCapturadas.add(peçaCapturada);
		}
		
		return peçaCapturada;
	}
	
	private void validarPosicaoInicial(Posicao posicao) {
		if (!tabuleiro.temUmaPeça(posicao)) {
			throw new ExcecaoDoXadrez("Não existe peça na posição de origem");
		}
		if (jogadorAtual !=  ((PeçaDeXadrez)tabuleiro.peça(posicao)).getCor()) {
			throw new ExcecaoDoXadrez("A peça escolhida não é sua");
		}
		if (!tabuleiro.peça(posicao).existeAlgumMovimentoPossivel()) {
			throw new ExcecaoDoXadrez("Não existe movimentos possiveis para peça escolhida");
		}
	}
	
	private void validarPosicaoDestino(Posicao inicial, Posicao destino) {
		if (!tabuleiro.peça(inicial).possiveisMovimentos(destino)) {
			throw new ExcecaoDoXadrez("A peça escolhida não pode se mover para a posição de destino");
		}
	}
	
	private void proximoVez() {
		vez++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private void coloqueNovaPeça(char coluna, int linha, PeçaDeXadrez peça) {
		 tabuleiro.LugarDaPeça(peça, new PosicaoDeXadrez(coluna, linha).posicionar());
		 peçasNoTabuleiro.add(peça);
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
