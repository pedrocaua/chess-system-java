package application;

import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PeçaDeXadrez;
import xadrez.PosicaoDeXadrez;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		while (true) {
			UI.printTabuleiro(partidaDeXadrez.getPeças());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoDeXadrez inicial = UI.lerPosicaoDeXadrez(sc);
			
			System.out.println();
			System.out.print("Destino: ");
			PosicaoDeXadrez destino = UI.lerPosicaoDeXadrez(sc);
			
			PeçaDeXadrez peçaCapturada = partidaDeXadrez.executarMovimentoDeXadrez(inicial, destino);
			
		}
		
		
		
		
	}

}
