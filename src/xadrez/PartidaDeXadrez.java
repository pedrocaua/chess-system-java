package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiroDeXadrez.Peça;
import tabuleiroDeXadrez.Posicao;
import tabuleiroDeXadrez.Tabuleiro;
import xadrez.peças.Bispo;
import xadrez.peças.Cavalo;
import xadrez.peças.Peao;
import xadrez.peças.Rainha;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class PartidaDeXadrez { // classe onde fica as regras do jogo de xadrez

	private int vez;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PeçaDeXadrez enPassantVulneravel;
	private PeçaDeXadrez promover;

	private List<Peça> peçasNoTabuleiro = new ArrayList<>();
	private List<Peça> peçasCapturadas = new ArrayList<>();

	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8); // dimensão do tabuleiro
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

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PeçaDeXadrez getEnPassantVulneravel() {
		return enPassantVulneravel;
	}

	public PeçaDeXadrez getPromover() {
		return promover;
	}

	public PeçaDeXadrez[][] getPeças() {
		PeçaDeXadrez[][] mat = new PeçaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PeçaDeXadrez) tabuleiro.peça(i, j);
			}
		}
		return mat;
	}

	public boolean[][] possiveisMovimentos(PosicaoDeXadrez posicaoInicial) {
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

		if (testeCheck(jogadorAtual)) {
			desfazerMovimento(inicial, destino, peçaCapturada);
			throw new ExcecaoDoXadrez("Você não pode se colocar em Check");
		}

		PeçaDeXadrez peçaMovida = (PeçaDeXadrez) tabuleiro.peça(destino);

		// # Movimento especial PROMOÇÃO
		promover = null;
		if (peçaMovida instanceof Peao) {
			if (peçaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0
					|| (peçaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7)) {
				promover = (PeçaDeXadrez) tabuleiro.peça(destino);
				promover = substituirPeçaPromovida("Q");
			}
		}

		check = (testeCheck(oponente(jogadorAtual))) ? true : false;

		if (testeCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoVez();
		}

		// Movimento especial EN PASSANT
		if (peçaMovida instanceof Peao
				&& (destino.getLinha() == inicial.getLinha() - 2 || destino.getLinha() == inicial.getLinha() + 2)) {
			enPassantVulneravel = peçaMovida;
		} else {
			enPassantVulneravel = null;
		}

		return (PeçaDeXadrez) peçaCapturada;
	}
	
	public PeçaDeXadrez substituirPeçaPromovida(String type) {
		if (promover == null) {
			throw new IllegalStateException("Não a peça para ser promovida");
		}
		if (!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")) {
			throw new InvalidParameterException("Tipo invalido para promoção");
		}
		
		Posicao pos = promover.getPosicaoDeXadrez().posicionar();
		Peça p = tabuleiro.removerPeça(pos);
		peçasNoTabuleiro.remove(p);
		
		PeçaDeXadrez peçaNova = peçaNova(type, promover.getCor());
		tabuleiro.LugarDaPeça(peçaNova, pos);
		peçasNoTabuleiro.add(peçaNova);
		
		return peçaNova;
		
	}
	
	private PeçaDeXadrez peçaNova(String type, Cor cor) {
		if (type.equals("B")) return new Bispo(tabuleiro, cor);
		if (type.equals("C")) return new Bispo(tabuleiro, cor);
		if (type.equals("Q")) return new Bispo(tabuleiro, cor);
		return new Bispo(tabuleiro, cor);
		
	}
	

	private Peça façaMover(Posicao inicial, Posicao destino) { // tira da origem e põe no destino
		PeçaDeXadrez p = (PeçaDeXadrez) tabuleiro.removerPeça(inicial);
		p.aumentarContagemMovimentos();
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.LugarDaPeça(p, destino);

		if (peçaCapturada != null) {
			peçasNoTabuleiro.remove(peçaCapturada);
			peçasCapturadas.add(peçaCapturada);
		}

		// # Movimento especial ROQUE ao lado do rei
		if (p instanceof Rei && destino.getColuna() == inicial.getColuna() + 2) {
			Posicao inicialT = new Posicao(inicial.getLinha(), inicial.getColuna() + 3);
			Posicao destinoT = new Posicao(inicial.getLinha(), inicial.getColuna() + 1);
			PeçaDeXadrez torre = (PeçaDeXadrez) tabuleiro.removerPeça(inicialT);
			tabuleiro.LugarDaPeça(torre, destinoT);
			torre.aumentarContagemMovimentos();
		}

		// # Movimento especial ROQUE ao lado da rainha
		if (p instanceof Rei && destino.getColuna() == inicial.getColuna() - 2) {
			Posicao inicialT = new Posicao(inicial.getLinha(), inicial.getColuna() - 4);
			Posicao destinoT = new Posicao(inicial.getLinha(), inicial.getColuna() - 1);
			PeçaDeXadrez torre = (PeçaDeXadrez) tabuleiro.removerPeça(inicialT);
			tabuleiro.LugarDaPeça(torre, destinoT);
			torre.aumentarContagemMovimentos();
		}

		// # Movimento especial EN PASSANT
		if (p instanceof Peao) {
			if (inicial.getColuna() != destino.getColuna() && peçaCapturada == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				peçaCapturada = tabuleiro.removerPeça(posicaoPeao);
				peçasCapturadas.add(peçaCapturada);
				peçasNoTabuleiro.remove(peçaCapturada);
			}
		}

		return peçaCapturada;
	}

	private void desfazerMovimento(Posicao inicial, Posicao destino, Peça peçaCapturada) {
		PeçaDeXadrez p = (PeçaDeXadrez) tabuleiro.removerPeça(destino);
		p.diminuirContagemMovimentos();
		tabuleiro.LugarDaPeça(p, destino);

		if (peçaCapturada != null) {
			tabuleiro.LugarDaPeça(peçaCapturada, destino);
			peçasCapturadas.remove(peçaCapturada);
			peçasNoTabuleiro.add(peçaCapturada);
		}

		// # Movimento especial ROQUE ao lado do rei
		if (p instanceof Rei && destino.getColuna() == inicial.getColuna() + 2) {
			Posicao inicialT = new Posicao(inicial.getLinha(), inicial.getColuna() + 3);
			Posicao destinoT = new Posicao(inicial.getLinha(), inicial.getColuna() + 1);
			PeçaDeXadrez torre = (PeçaDeXadrez) tabuleiro.removerPeça(destinoT);
			tabuleiro.LugarDaPeça(torre, inicialT);
			torre.diminuirContagemMovimentos();
		}

		// # Movimento especial ROQUE ao lado da rainha
		if (p instanceof Rei && destino.getColuna() == inicial.getColuna() - 2) {
			Posicao inicialT = new Posicao(inicial.getLinha(), inicial.getColuna() - 4);
			Posicao destinoT = new Posicao(inicial.getLinha(), inicial.getColuna() - 1);
			PeçaDeXadrez torre = (PeçaDeXadrez) tabuleiro.removerPeça(destinoT);
			tabuleiro.LugarDaPeça(torre, inicialT);
			torre.diminuirContagemMovimentos();
		}

		// # Movimento especial EN PASSANT
		if (p instanceof Peao) {
			if (inicial.getColuna() != destino.getColuna() && peçaCapturada == enPassantVulneravel) {
				PeçaDeXadrez peao = (PeçaDeXadrez) tabuleiro.removerPeça(destino);
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(3, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.LugarDaPeça(peao, posicaoPeao);
			}
		}

	}

	private void validarPosicaoInicial(Posicao posicao) {
		if (!tabuleiro.temUmaPeça(posicao)) {
			throw new ExcecaoDoXadrez("Não existe peça na posição de origem");
		}
		if (jogadorAtual != ((PeçaDeXadrez) tabuleiro.peça(posicao)).getCor()) {
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

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PeçaDeXadrez rei(Cor cor) {
		List<Peça> list = peçasNoTabuleiro.stream().filter(x -> ((PeçaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peça p : list) {
			if (p instanceof Rei) {
				return (PeçaDeXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe o rei " + cor + " no tabuleiro");
	}

	private boolean testeCheck(Cor cor) {
		Posicao posicaoDoRei = rei(cor).getPosicaoDeXadrez().posicionar();
		List<Peça> peçasOponentes = peçasNoTabuleiro.stream().filter(x -> ((PeçaDeXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peça p : peçasOponentes) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeCheckMate(Cor cor) {
		if (!testeCheck(cor)) {
			return false;
		}
		List<Peça> list = peçasNoTabuleiro.stream().filter(x -> ((PeçaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peça p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao inicial = ((PeçaDeXadrez) p).getPosicaoDeXadrez().posicionar();
						Posicao destino = new Posicao(i, j);
						Peça peçaCapturada = façaMover(inicial, destino);
						boolean testeCheck = testeCheck(cor);
						desfazerMovimento(inicial, destino, peçaCapturada);
						if (!testeCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;

	}

	private void coloqueNovaPeça(char coluna, int linha, PeçaDeXadrez peça) {
		tabuleiro.LugarDaPeça(peça, new PosicaoDeXadrez(coluna, linha).posicionar());
		peçasNoTabuleiro.add(peça);
	}

	private void iniciarPartida() {
		coloqueNovaPeça('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		coloqueNovaPeça('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		coloqueNovaPeça('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		coloqueNovaPeça('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		coloqueNovaPeça('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		coloqueNovaPeça('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		coloqueNovaPeça('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		coloqueNovaPeça('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		coloqueNovaPeça('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		coloqueNovaPeça('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		coloqueNovaPeça('a', 8, new Torre(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		coloqueNovaPeça('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('h', 8, new Torre(tabuleiro, Cor.PRETO));
		coloqueNovaPeça('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		coloqueNovaPeça('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		coloqueNovaPeça('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		coloqueNovaPeça('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		coloqueNovaPeça('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		coloqueNovaPeça('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		coloqueNovaPeça('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		coloqueNovaPeça('h', 7, new Peao(tabuleiro, Cor.PRETO, this));

	}

}
