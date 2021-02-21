package up_info_bdda_iz_ha;

import java.io.IOException;
import java.util.ArrayList;

public class TestHeapFile {

	public static void main(String[] args) throws IOException {
		
		
		System.out.println("\t----------------------------------------------------\n");
		System.out.println("\t\t*********** Test de la classe HeapFile *************\n\n");
		
		DBParams.dbPath = "/home/rilles/Bureau/dossiers_bureau/Projet_BDDA_IZOUAOUEN_HAMMOUDI/DB";
		DBParams.pageSize =4096;
		DBParams.frameCount=2;
		String commande = "CREATEREL Compagnie Nom:string6 adresse:string8 idEntreprise:string8";
		DBManager.getInstance().ProcessCommand(commande);
		RelationInfo relInfo = DBInfo.getINSTANCE().listeDeRelationsInfo.get(0);
	//	HeapFile heapFile = FileManager.getINSTANCE().getHeapFiles().get(0);
		HeapFile heapFile = new HeapFile(relInfo);
		
		PageId page =null;
		try {
			 page = heapFile.addDataPage();
			
			if(page!=null) {
				System.out.println("Page ajouté avec succés");
				System.out.println("PageIdx : "+page.getPageIdx()+" FileId : "+page.getFileIdx());
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
  
		try {
			PageId pageAvecEspace = heapFile.getFreeDataPageId();
			System.out.println("La page ayant de l'espace dans le fichier N° "+pageAvecEspace.getFileIdx()+" est la page "+pageAvecEspace.getPageIdx());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		Record tuple = new Record(relInfo);
		
		 
		 tuple.setValues("ASLAir");
		 tuple.setValues("Paris-fr");
		 tuple.setValues("fr75019P");
		 
		
		 
		 
		
	 try {
		heapFile.writeRecordToDataPage(tuple, page);
		 System.out.println("\nAffichage des records ajoutés dans cette DataPage  : \n");
		ArrayList<Record> tuples = heapFile.getRecordsInDataPage(page);
		for (Record record : tuples) {
			ArrayList<String> valeurs = (ArrayList<String>) record.getValues();
			for (String val : valeurs) {
				System.out.print(val+"\t");
				
			}
			System.out.println("\n");
		}
	} catch (IOException e) {
		
		e.printStackTrace();
	}
		
	 heapFile.InsertRecord(tuple);
	 
	Record tuple2 = new Record(relInfo);
		
	 
	tuple2.setValues("Air-Fr");
	tuple2.setValues("Paris-fr");
	tuple2.setValues("fr75019P");
	 
	 try {
		
		heapFile.InsertRecord(tuple2);
		
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	 
	 System.out.println("\nAffichage de tous les records  : \n");
	 try {
		ArrayList<Record> allTuples = heapFile.GetAllRecords();
		
		for (Record record : allTuples) {
			ArrayList<String> valeurs2 = (ArrayList<String>) record.getValues();
			for (String val : valeurs2) {
				System.out.print(val+"\t");
				
			}
			System.out.println("\n");
		}
	} catch (IOException e) {
		
		e.printStackTrace();
	}
		
		
	
	 
	
	}

}
