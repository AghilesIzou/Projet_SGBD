package up_info_bdda_iz_ha;

import java.io.IOException;

import java.util.Scanner;

/*
 * classe main qui est le point d'entré pour executer le programme 
 * 
 * @author Aghiles_IZOUAOUEN  
 * 
 * @version 1.0 
 * 
 */

public class Main {

	public static void main(String[] args) throws IOException {
		
		DBParams.dbPath = args[0];
		DBManager.getInstance().Init();
		
		DBParams.pageSize =4096;
		DBParams.frameCount=2;
		
		Scanner keyboard = new Scanner(System.in);
		Utilitaire.menuDebut();
			
		String commande = keyboard.nextLine();
		String [] mots = commande.split(" ");
		
		if(mots[0].equals("EXIT")) {
			DBManager.getInstance().Finish();
			Utilitaire.menuFin();
		}else {
			
		while(!(mots[0].equals("EXIT"))) {
			try {
			DBManager.getInstance().ProcessCommand(commande);
			}catch(IllegalArgumentException e) {
				System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println(e.getMessage());
				System.out.println("-------------------------------------------------------------------------------------------------------------------------------------\n");
			}
			Utilitaire.menu();
			commande = keyboard.nextLine();
			mots = commande.split(" ");
			
		}
		DBManager.getInstance().Finish();
		Utilitaire.menuFin();
		}
			 
		keyboard.close();
	}
}
