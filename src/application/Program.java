package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExcecaoDoXadrez;
import xadrez.PartidaDeXadrez;
import xadrez.PeçaDeXadrez;
import xadrez.PosicaoDeXadrez;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		List<PeçaDeXadrez> capturadas = new ArrayList<>();
		
		while (!partidaDeXadrez.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printPartida(partidaDeXadrez, capturadas);
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
				
				if (peçaCapturada != null) {
					capturadas.add(peçaCapturada);
				}
				
				if (partidaDeXadrez.getPromover() != null) {
					System.out.print("Entrar com a preça promovida (B/C/T/Q): ");
					String type = sc.nextLine().toUpperCase();
					while (!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")) {
						System.out.print("Valor inválido! Entrar com a preça promovida (B/C/T/Q): ");
						type = sc.nextLine().toUpperCase();
					}
					partidaDeXadrez.substituirPeçaPromovida(type);
				}
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
		UI.clearScreen();
		UI.printPartida(partidaDeXadrez, capturadas);	
	}

}
