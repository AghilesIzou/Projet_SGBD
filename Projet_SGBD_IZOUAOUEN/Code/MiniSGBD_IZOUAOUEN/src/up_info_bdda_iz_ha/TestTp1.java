package up_info_bdda_iz_ha;

import java.io.IOException;
import java.util.Scanner;

public class TestTp1 {
	
	public static void main(String[] args) throws IOException {
		
		DBParams.dbPath = "/home/rilles/Bureau/dossiers_bureau/Projet_BDDA_IZOUAOUEN_HAMMOUDI/DB";
		DBManager.getInstance().Init();
	
		
		
		DBParams.pageSize =4096;
		DBParams.frameCount=2;
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("\t----------------------------------------------------\n");
		System.out.println("\t\t*********** MINI-SGBD *************\n\n");
		System.out.println("\t\t*********** Bienvenue *************\n");
		
		System.out.println("Que souhaitez vous faire ?\n");
		System.out.println("1) CREATEREL\n");
		System.out.println("********** La relation doit respecter le format suivant **********");
		System.out.println("\nCREATEREL NomRelation NomCol[1]:TypeColl[1] NomCol[2]:TypeCol[2]....\n");
		System.out.println("2) EXIT");
			
		String commande = keyboard.nextLine();
		String [] mots = commande.split(" ");
		
		if(mots[0].equals("EXIT")) {
			DBManager.getInstance().Finish();
			System.out.println("\t\t************** Au revoir **************");
			System.out.println("\n\n\t-----------------------------------------------------------\n");
		}else {
			
		while(!(mots[0].equals("EXIT"))) {
			
			DBManager.getInstance().ProcessCommand(commande);
			
			System.out.println("Que souhaitez vous faire ? ");
			commande = keyboard.nextLine();
			mots = commande.split(" ");
			
		}
		DBManager.getInstance().Finish();
		System.out.println("\t\t************** Au revoir **************");
		System.out.println("\n\n\t----------------------------------------------------------\n");
		}
			 
		keyboard.close();
		
	
		
		
		
		
	}

}
