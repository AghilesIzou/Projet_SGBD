package up_info_bdda_iz_ha;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * classe DBmanager qui est le «point d’entrée» de la logique spécifique SGBD 
 * 
 * @author Aghiles_IZOUAOUEN 
 * 
 * @version 1.0 
 * 
 */

public class DBManager {
	
	 private DBManager() {}
    
    private static class Holder{
    	public static DBManager dbManager= new DBManager();
    }

    public static DBManager getInstance() {
        return Holder.dbManager;
    }
    
    /*
   	 * methode init  du DBmanager qui fait appel a la methode init du DBinfo qui est  nécessaire pour l’initialisation d’une instance
   	 * 
   	 * 
   	 */
    
    public void Init() {
    	DBInfo.getINSTANCE().Init();
    	FileManager.getINSTANCE().Init();
    }
    
    /*
	 * methode finish qui fait appel a la methode finish du DBinfo 
	 * et FlushAll() pour réinitialiser le bufferpool
	 * 
	 */
    public void Finish() throws IOException  {
    	
    	DBInfo.getINSTANCE().Finish();
    	BufferManager.getInstance().FlushAll();
    }
    
    /*
   	 * methode ProcessCommand cette méthode permet de parser la commande et en fonction du premier 
   	 * élement de celle-ci, cette méthode fera appel à la méthode qui est associé au premier élément. 
   	 * 
   	 * @param commande, nom de la commande 
   	 * 
   	 */
    
    public void ProcessCommand(String commande) throws IOException, IllegalArgumentException {
    	
    	String mot[] = commande.split(" ");
    	
    	
    	switch(mot[0]) {
    	
    	case "CREATEREL":
    		
    		CreateRelation(mot);
    		break;
    		
    	case "RESET":
    		
    		Reset();
    		break;
    		
    	case "INSERT":
    		
    		Insert(mot);
    		break;
    		
    	case "BATCHINSERT" :
    		
    		BatchInsert(mot[2], mot[5]);
    		break;
    	
    	case "SELECTALL" :	
    		SelectAll(mot[2]);
    		break;
    		
    	case "SELECTS" :
    		
    		SelectS(mot[2], mot[4]);
    		break;
    	
    	case "SELECTC" :
    		
    		SelectC(commande);
    		break;
    		
    	case "UPDATE" :
    		
    		Update(mot[1], mot[3], mot[5]);
    		break;
    	
    	case "HELP" :
    		
    		Utilitaire.menuHelp();
    		break;
    	
    	default :
    		
    		throw new IllegalArgumentException("********** Erreur ! la commande "+mot[0]+ " n'existe pas ! **********");
    	}
    	
    }
    
    
    /*
     * methode pour créer une relation 
     * @param tableau de chaine de caractére 
     * @throws IOException
     */
    public void CreateRelation(String [] mot) throws IOException {
    	
    	List<ColInfo> listeNomTypeColonne = new ArrayList<ColInfo> ();
		for(int i =2; i< mot.length;i++) {
			String [] nomType = mot[i].split(":");
			ColInfo infoColonne = new ColInfo(nomType[0],nomType[1]);
			listeNomTypeColonne.add(infoColonne);
		}
		
		String nomRelation = mot[1];
		int nombreColonnes = mot.length-2;
		
    	int recordSize=0;
    	
    	String typeString;
    	
    	for (ColInfo colInfo : listeNomTypeColonne) {
    		
    		
			switch(colInfo.getTypeDeLaColonne().toLowerCase()) {
			
    		
    			case "int" : case "float" : recordSize+=4;
    			break;
    			
    			default : 
    				
    				typeString = colInfo.getTypeDeLaColonne().toLowerCase();
    				recordSize += 2* Integer.parseInt(typeString.substring(6));
			}
		}
    	
    	int slotCount = (DBParams.pageSize/(recordSize+1));
    	int fileIdx = DBInfo.getINSTANCE().getCompteur();
    	
    	RelationInfo relationInfo = new RelationInfo(nomRelation, nombreColonnes, listeNomTypeColonne, fileIdx, recordSize, slotCount );
    	DBInfo.getINSTANCE().AddRelation(relationInfo);
    	FileManager.getINSTANCE().CreateRelationFile(relationInfo);
    	
    	System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.println("\nVous avez créé la relation "+ nomRelation+" avec succés ! ");
    	System.out.println("Nombres de colonnes : "+nombreColonnes);
    	for(int i=0; i<listeNomTypeColonne.size();i++) {
    		System.out.println("Nom de la colonne "+(i+1)+" : "+listeNomTypeColonne.get(i).getNomDeLaColonne()+", de type : "+listeNomTypeColonne.get(i).getTypeDeLaColonne()+".");
    	}
    	System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------");
    	
    	
    }
    
    /*
     * methode pour la commande RESET qui remet l'application dans un état qui correspond à un premier lancement 
     *
     * @throws IOException
    */
    
    public void Reset() throws IOException {
    	
    	BufferManager.getInstance().reset();
    	DBInfo.getINSTANCE().reset();
    	FileManager.getINSTANCE().reset();
    	
    	File directory = new File(DBParams.dbPath);
    	
    	 if (directory.exists()) {
    		 
             if (directory.isDirectory()) {
                 
                 File[] fichiers = directory.listFiles();
                 
                 for (File file : fichiers) {
                     file.delete();
                 }
                 
             }
             
    	 }

    	 System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------");
    	 System.out.println("\t\t\t\t********* La base de données a été supprimée avec succès ! *********");
    	 System.out.println("-------------------------------------------------------------------------------------------------------------------------------------\n");
    	
    }
    
    /*
     * methode poue la commande INSERT qui permet l’insertion d’un record dans une relation
     * @param tableau de string
     * @throws IOException 
     */
    public void  Insert(String[] mot) throws IOException {
    	
    	String nomRelation = mot[2];
		List<String> values = new ArrayList<>();
		String valueswithoutParentheses = mot[4].substring(1, mot[4].length()-1);
		
		String tabValues [] = valueswithoutParentheses.split(",");
		for (String value : tabValues) {
			values.add(value);
		}
    	
    	Record record = new Record();
    	record.setListeValues(values);
    	FileManager.getINSTANCE().InsertRecordInRelation(record, nomRelation);
    	
    	
     }
    
    /*
     * methode pour la commande BATCHINSERT qui permet de récupérer et d'inserer  tous les records d'un fichier dans une relation
     * @param nomRelation le nom de la relation 
     * @param nomFichier le nom du fichier 
     * @throws IOException
     */
    
    public void BatchInsert(String nomRelation, String nomFichier) throws IOException {
    	
    	FileReader fileReader = new FileReader(DBParams.dbPath + "/../" +nomFichier);
		BufferedReader bufferdReader = new BufferedReader(fileReader);
		String ligne=null;
		
		while((ligne = bufferdReader.readLine()) != null){
			List<String> values = new ArrayList<String>();
			Record record = new Record();
			String[] valuesRecord = ligne.split(",");
			for (String value : valuesRecord) {
				values.add(value);
			}
			record.setListeValues(values);
			FileManager.getINSTANCE().InsertRecordInRelation(record, nomRelation);
		}

		bufferdReader.close();
    	
    }
    
    /*
     * methode de la commande SelectAll qui sert à afficher tous les records d’une relation 
     *  @param nomRelation le nom de la relation
     * @throws IOException
     */
    
    public void SelectAll(String nomRelation) throws IOException {
    	
    	ArrayList<Record> allRecords = FileManager.getINSTANCE().SelectAllFromRelation(nomRelation);
    	int compteurRecords=0;
    	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
    	for (int i = 0; i < allRecords.size() ; i++) {
    		
    		ArrayList<String> valuesRecord= (ArrayList<String>) allRecords.get(i).getValues();
    		StringBuilder builder = new StringBuilder();
    		
    		for (int j = 0 ; j<valuesRecord.size(); j++) {
				builder.append(valuesRecord.get(j));
				if(j!=valuesRecord.size()-1) {
					builder.append(" ; ");
				}else {
					builder.append(".");
				}
				
			}
    		compteurRecords++;
    		System.out.println(builder.toString()+"\n");
    		
			
		}
    	System.out.println("Total records="+compteurRecords+".");
    	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
    	
    }
   
    /*
     * methode de la commande SELECTS qui sert à afficher tous les records d’une relation qui ont une valeur donnée sur une colonne donnée
     * @param nomRelation le nom de la relation
     * @param nomValeurColonne nom et valeur de la colonne de la condition 
     * @throws IOException
     */
    
    public void SelectS(String nomRelation, String nomValeurColonne) throws IOException {
    	
    	String[] separer = nomValeurColonne.split("=");
    	String nomColonne= separer[0];
    	String valeurColonne = separer[1];
    	ArrayList<Record> allRecords = FileManager.getINSTANCE().SelectAllFromRelation(nomRelation);
    	RelationInfo relInfo = FileManager.getINSTANCE().getRel(nomRelation);
    	
    	int positionDansRelation=0;
    	
    	
    	for (int i = 0; i <relInfo.getNbColonnes() ; i++) {
			
    		if(relInfo.getListeNomTypeColonnes().get(i).getNomDeLaColonne().equals(nomColonne)) {
    			positionDansRelation=i;
			}
		}
    	
    	int compteurRecords=0;
    	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
    	for (int i = 0; i < allRecords.size() ; i++) {
    		
    		ArrayList<String> valuesRecord= (ArrayList<String>) allRecords.get(i).getValues();
    		if(valuesRecord.get(positionDansRelation).equals(valeurColonne)) {
    			
    			StringBuilder builder = new StringBuilder();
        		
        		for (int j = 0 ; j<valuesRecord.size(); j++) {
    				builder.append(valuesRecord.get(j));
    				if(j!=valuesRecord.size()-1) {
    					builder.append(" ; ");
    				}else {
    					builder.append(".");
    				}
    				
    			}
        		compteurRecords++;
        		System.out.println(builder.toString()+"\n");
        		
    			
    			
    		}
    		
			
		}
    	System.out.println("Total records="+compteurRecords+".");
    	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
    	
    }
    
    /*
     * methode pour la commande SELECTC qui  doit afficher tous les records d’une relation qui respectent un ensemble de critères d’égalité ou inégalité
     * @param commande la commande 
     * @throws IOException
     */
    
    public void SelectC(String commande) throws IOException {
    	
    	
    	String [] mot = commande.split(" ");
		String nomRelation = mot[2];
		ArrayList<Record> allRecords = FileManager.getINSTANCE().SelectAllFromRelation(nomRelation);
	
		RelationInfo relInfo = FileManager.getINSTANCE().getRel(nomRelation);
		ArrayList<ColInfo> types =  (ArrayList<ColInfo>) relInfo.getListeNomTypeColonnes();
		
		ArrayList<Boolean> conditionsSatisfaites = new ArrayList<>();
		ArrayList<Record> recordsCheckCondition = new ArrayList<>();
		
		String [] SperationAvecWhere = commande.split("WHERE");
	
		String chaineApresWhere = SperationAvecWhere[1].substring(1);
		
		String [] criteres = new String[1];
		
		if(chaineApresWhere.contains("AND")) {
			criteres = chaineApresWhere.split(" AND ");
			
		}else {
			criteres[0] =  chaineApresWhere;
			
		}
		
		String operateur = null;
		String typeColonne = null;
		int positionValue = 0;
		int compteurRecords=0;
		
    	for(int i =0;i<allRecords.size();i++) {
    		
    		Record record = allRecords.get(i);
    		ArrayList<String> valuesRecords = (ArrayList<String>) record.getValues();
    		
    		for(int j=0; j<criteres.length; j++) {
    		
    			StringBuilder sb = new StringBuilder();
        		Pattern pattern = Pattern.compile("[<>=]+");   
        	    Matcher matcher = pattern.matcher(criteres[j]) ;  
        		 while (matcher.find()) {  
        			 sb.append(matcher.group());
        			 operateur = sb.toString();
        		}
    			
    			String[] colonneValeur = criteres[j].split(operateur);
    			String nomColonne = colonneValeur[0];
    			String valeur = colonneValeur[1];
    			
    			for ( int k=0; k<types.size();k++) {
    				
    				
    				if(types.get(k).getNomDeLaColonne().equals(nomColonne)) {
    					typeColonne = types.get(k).getTypeDeLaColonne();
    					positionValue=k;
    					break;
    				}
    				
    			}
    			
    			if(typeColonne.equals("int")) {
    				
    				switch(operateur) {
    					
    					case ">" :
    						if(Integer.parseInt(valuesRecords.get(positionValue))>Integer.parseInt(valeur)) {
    							conditionsSatisfaites.add(true);
    						}else {
    							conditionsSatisfaites.add(false);
    						}
    						break;
    				
    					case "<" :
    						
    						if(Integer.parseInt(valuesRecords.get(positionValue))<Integer.parseInt(valeur)) {
    							conditionsSatisfaites.add(true);
    						}else {
    							conditionsSatisfaites.add(false);
    						}
    						break;
    						
    					case ">=" :
    						
    						if(Integer.parseInt(valuesRecords.get(positionValue))>=Integer.parseInt(valeur)) {
    							conditionsSatisfaites.add(true);
    						}else {
    							conditionsSatisfaites.add(false);
    						}
    						break;	
    						
    					case "<=" :
    						
    						if(Integer.parseInt(valuesRecords.get(positionValue))<=Integer.parseInt(valeur)) {
    							conditionsSatisfaites.add(true);
    						}else {
    							conditionsSatisfaites.add(false);
    						}
    						break;	
    						
    					case "=" :
    						
    						if(Integer.parseInt(valuesRecords.get(positionValue))==Integer.parseInt(valeur)) {
    							conditionsSatisfaites.add(true);
    						}else {
    							conditionsSatisfaites.add(false);
    						}
    						break;	
    				
    				}
    			
    			
    				
    			}else if(typeColonne.equals("float")) {
    				
    				
    				
    				switch(operateur) {
					
					case ">" :
						if(Float.parseFloat(valuesRecords.get(positionValue))>Float.parseFloat(valeur)) {
							conditionsSatisfaites.add(true);
						}else {
							conditionsSatisfaites.add(false);
						}
						break;
				
					case "<" :
						
						if(Float.parseFloat(valuesRecords.get(positionValue))<Float.parseFloat(valeur)) {
							conditionsSatisfaites.add(true);
						}else {
							conditionsSatisfaites.add(false);
						}
						break;
						
					case ">=" :
						
						if(Float.parseFloat(valuesRecords.get(positionValue))>=Float.parseFloat(valeur)) {
							conditionsSatisfaites.add(true);
						}else {
							conditionsSatisfaites.add(false);
						}
						break;
						
					case "<=" :
						
						if(Float.parseFloat(valuesRecords.get(positionValue))<=Float.parseFloat(valeur)) {
							conditionsSatisfaites.add(true);
						}else {
							conditionsSatisfaites.add(false);
						}
						break;
						
					case "=" :
						
						if(Float.parseFloat(valuesRecords.get(positionValue))==Float.parseFloat(valeur)) {
							conditionsSatisfaites.add(true);
						}else {
							conditionsSatisfaites.add(false);
						}
						break;
				
				}
    				
    				
    			}else {
    				
    				switch(operateur) {
					
					case ">" :
						if(valuesRecords.get(positionValue).compareTo(valeur)>0) {
							conditionsSatisfaites.add(true);
						}else {
							conditionsSatisfaites.add(false);
						}
						break;
				
					case "<" :
						
						if(valuesRecords.get(positionValue).compareTo(valeur)<0) {
							conditionsSatisfaites.add(true);
						}else {
							conditionsSatisfaites.add(false);
						}
						break;
						
					case ">=" :
						
						if(valuesRecords.get(positionValue).compareTo(valeur)>=0) {
							conditionsSatisfaites.add(true);
						}else {
							conditionsSatisfaites.add(false);
						}
						break;
						
					case "<=" :
						
						if(valuesRecords.get(positionValue).compareTo(valeur) <=0){
							conditionsSatisfaites.add(true);
						}else {
							conditionsSatisfaites.add(false);
						}
						break;
						
					case "=" :
						
						if(valuesRecords.get(positionValue).equals(valeur)){
	    					conditionsSatisfaites.add(true);
	    				}else {
							conditionsSatisfaites.add(false);
						}
						break;
				
				  }
    				
    		  }
    			
    		}
    		
    		if(!(conditionsSatisfaites.contains(false))) {
    			recordsCheckCondition.add(record);
    		}
    		
    		
    		conditionsSatisfaites.clear();
    		
    	}
    	
    	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
    
    	for (int i = 0; i < recordsCheckCondition.size() ; i++) {
    		
    		ArrayList<String> valuesRecord= (ArrayList<String>) recordsCheckCondition.get(i).getValues();
    		
    			StringBuilder builder = new StringBuilder();
        		
        		for (int j = 0 ; j<valuesRecord.size(); j++) {
    				builder.append(valuesRecord.get(j));
    				if(j!=valuesRecord.size()-1) {
    					builder.append(" ; ");
    				}else {
    					builder.append(".");
    				}
    				
    			}
        		compteurRecords++;
        		System.out.println(builder.toString()+"\n");
        		
    	}
    	System.out.println("Total records="+compteurRecords+".");
    	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
    	
    	
    }

    /*
     * methode pour la commande UPDATE pour modifier un record en modifinat la valeur  d'une colonne 
     * @param nomRelation le nom de la relation 
     * @param colonneA le nom de la colonne à mettre à jour
     * @param colonneB le nom de la colonne dont le champs sur cette colonne doit remplir une condition  
     * @throws IOException
     */
    
    public void  Update(String nomRelation, String colonneA, String colonneB) throws IOException {
	
		RelationInfo relInfo = FileManager.getINSTANCE().getRel(nomRelation);
    	String[] separerA = colonneA.split("=");
    	String nomColonneA= separerA[0];
    	String valeurColonneA = separerA[1];
    	int positionDeADansRelation=0;
    	
    	
    	String[] separerB = colonneB.split("=");
    	String nomColonneB= separerB[0];
    	String valeurColonneB = separerB[1];
    	int positionDeBDansRelation=0;
    	
    	int updatedRecords=0;
    	
    	
    	for (int i = 0; i <relInfo.getNbColonnes() ; i++) {
			
    		if(relInfo.getListeNomTypeColonnes().get(i).getNomDeLaColonne().equals(nomColonneB)) {
    			positionDeBDansRelation=i;
			}
    		
    		if(relInfo.getListeNomTypeColonnes().get(i).getNomDeLaColonne().equals(nomColonneA)) {
    			positionDeADansRelation=i;
			}
		}
    	
    	int fileIdx = relInfo.getFileIdx();
    	
    	
    	PageId pageIdHeaderPage = new PageId(fileIdx,0);
    	ByteBuffer headerPage = BufferManager.getInstance().GetPage(pageIdHeaderPage);
    	headerPage.position(0);
    	
    	int nbDataPage = headerPage.getInt(0);
    	
    	for(int i=1; i<=nbDataPage; i++) {
			
			PageId dataPage = new PageId(fileIdx,i);
			ByteBuffer bufferDataPage = BufferManager.getInstance().GetPage(dataPage);
			ArrayList<Record> recordsInDataPage = FileManager.getINSTANCE().getHeapFiles().get(fileIdx).getRecordsInDataPage(dataPage);
			
			for (int j=0; j<recordsInDataPage.size();j++) {
				
				if(recordsInDataPage.get(j).getValues().get(positionDeBDansRelation).equals(valeurColonneB)) {
		    			
					recordsInDataPage.get(j).setValues(positionDeADansRelation, valeurColonneA);
					recordsInDataPage.get(j).writeToBuffer(bufferDataPage, relInfo.getSlotCount() + j * relInfo.getRecordSize());
		    		updatedRecords++;
		    	}
			}
		
			BufferManager.getInstance().FreePage(dataPage, 1);    	
		
    	}

    	BufferManager.getInstance().FreePage(pageIdHeaderPage, 0);
    	
    	System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.println("Total updated records="+updatedRecords+".");
    	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");

    }
}	