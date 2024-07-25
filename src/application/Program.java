package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ExcecaoDoXadrez;
import xadrez.PartidaDeXadrez;
import xadrez.PeçaDeXadrez;
import xadrez.PosicaoDeXadrez;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		while (true) {
			try {
				UI.clearScreen();
				UI.printPartida(partidaDeXadrez);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoDeXadrez inicial = UI.lerPosicaoDeXadrez(sc);
				
				boolean[][] possiveisMovimentos = partidaDeXadrez.possiveisMovimentos(inicial);
				UI.clearScreen();
				UI.printTabuleiro(partidaDeXadrez.getPeças(), possiveisMovimentos);		
				System.out.println();
				System.out.print("Destino: ");
				PosicaoDeXadrez destino = UI.lerPosicaoDeXadrez(sc);
				
				PeçaDeXadrez peçaCapturada = partidaDeXadrez.executarMovimentoDeXadrez(inicial, destino);
			}
			catch (ExcecaoDoXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		
		
		
		
	}

}
