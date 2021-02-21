package up_info_bdda_iz_ha;

import java.io.IOException;
import java.util.Scanner;

public class TestFileManager {

	public static void main(String[] args) throws IOException {
		
		DBParams.dbPath = "/home/rilles/Bureau/dossiers_bureau/Projet_BDDA_IZOUAOUEN_HAMMOUDI/DB";
		DBManager.getInstance().Init();
	//	DBParams.dbPath = args[0];
		
		
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
			
			DBManager.getInstance().ProcessCommand(commande);
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
