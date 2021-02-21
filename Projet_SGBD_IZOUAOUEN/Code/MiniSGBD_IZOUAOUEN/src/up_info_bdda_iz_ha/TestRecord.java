package up_info_bdda_iz_ha;

import java.io.IOException;
import java.nio.ByteBuffer;

public class TestRecord {
	  
		
	public static void main(String[] args) throws IOException {
		
		System.out.println("\t----------------------------------------------------\n");
		System.out.println("\t\t*********** Test de la classe Record *************\n\n");
		
		System.out.println("Sérialisation et désirialisation du DBInfo contenant les relations de notre BDD\n");
		
		
		DBParams.dbPath = "/home/rilles/Bureau/dossiers_bureau/Projet_BDDA_IZOUAOUEN_HAMMOUDI/DB";
		DBManager.getInstance().Init();
		String st1 = "CREATEREL Student Nom:string6 Prenom:string9 age:int Id:int";
		String st2 = "CREATEREL Acteur Nom:string10 Prenom:String10 Id:int";
		String st3 = "CREATEREL Note noteCc:double noteExam:double";
		DBManager.getInstance().ProcessCommand(st1);
		DBManager.getInstance().ProcessCommand(st2);
		DBManager.getInstance().ProcessCommand(st3);
		System.out.println("\n\nÉtape 1 : sérialisation");
		DBManager.getInstance().Finish(); 
		
		System.out.println("\n\nÉtape 2 : désiaralisation\n");
		
		System.out.println("Affichage du nom de chaque relation et de son nombre de colonne du DBINFO désérialisé.");
		
		
		for (RelationInfo relinfo : DBInfo.getINSTANCE().listeDeRelationsInfo) {
			
			System.out.println(relinfo.getNomDeLaRelation()+", "+relinfo.getNbColonnes());
		}
		
		System.out.println("\n\n****** Test sur les records ******\n\n");
		Record tuple = new Record(DBInfo.getINSTANCE().listeDeRelationsInfo.get(0));
		ByteBuffer buff = ByteBuffer.allocate(4096);
		 
		 tuple.setValues("aghiles");
		 tuple.setValues("izouaouen");
		 tuple.setValues("23");
		 tuple.setValues("51707068");
		 System.out.println("\n\n****** utilisation de la méthde writeToBuffer ******\n\n");
		 tuple.writeToBuffer(buff, 0);
		 
		 System.out.println("\n\n****** utilisation de la méthde readFromBuffer ******\n\n");
		 Record tupleLecture = new Record(DBInfo.getINSTANCE().listeDeRelationsInfo.get(0));
		 Record tupleLecture1 = new Record(DBInfo.getINSTANCE().listeDeRelationsInfo.get(1));
		 
		
		tupleLecture.readFromBuffer(buff, 0);
		
		for (String string : tupleLecture.getValues()) {
			System.out.print(string+", ");
		}
		System.out.println("\n");
		tupleLecture1.readFromBuffer(buff, 0);
		
		for (String string : tupleLecture1.getValues()) {
			System.out.print(string+", ");
		}
		 
		
		
		

	}
	
	

}
