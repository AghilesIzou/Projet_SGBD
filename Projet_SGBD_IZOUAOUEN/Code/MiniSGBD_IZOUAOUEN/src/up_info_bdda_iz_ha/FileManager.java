package up_info_bdda_iz_ha;

import java.io.IOException;

import java.util.ArrayList;

/*
 * Class FileManager cette classe est distiné à contenir l'ensemble des heapFiles de chaque relation
 * 
 * @author Aghiles_IZOUAOUEN  
 * 
 * @version 1.0 
 * 
 *
 */

public class FileManager {
	
	/*
	 * liste de HeapFile
	 */
	
	private ArrayList<HeapFile> heapFiles;
	
	
	private FileManager() {
		 
		heapFiles = new ArrayList<HeapFile>();
	}
	
	private static class Holder{
		public static  FileManager fileManager = new FileManager();
	}

	public static  FileManager getINSTANCE() {
		
		return Holder.fileManager;
	}
	
	/*
	 * methode qui permet de créer un heapFile pour chaque relation contenu dans le DBinfo
	 */
	
	public void Init() {
		
		for (int i = 0; i < DBInfo.getINSTANCE().listeDeRelationsInfo.size(); i++) {
			HeapFile heapFile = new HeapFile(DBInfo.getINSTANCE().listeDeRelationsInfo.get(i));
			heapFiles.add(heapFile);
		}
		
	}
	
	/*
	 * methode pour creer un heapFile pour la relation passée en paramètre 
	 * et lui creer aussi le fichier associer
	 * @param relInfo une reltionInfo 
 	* @throws IOException
	 */
	
	public void CreateRelationFile(RelationInfo relInfo) throws IOException {
		
		HeapFile heapFile = new HeapFile(relInfo);
		heapFiles.add(heapFile);
		heapFile.createNewOnDisk();
		
	}
	
	/*
	 * methode pour inserer un record dans la relation 
	 * @param record un record
	 * @param relName une chaine de caractère  qui correspond au nom de la relation
	 * @return Rid
	 * @throws IOException	 */
	
	public Rid InsertRecordInRelation (Record record, String relName) throws IOException {
		
		Rid rid =null;
		
		for (int i = 0; i <heapFiles.size() ; i++) {
			
			if(heapFiles.get(i).getRelInfo().getNomDeLaRelation().equals(relName)) {
				rid = heapFiles.get(i).InsertRecord(record);
			}
		}
		
		return rid;
	}
	
	/*
	 * methode qui retourne  tous les records de la relation
	 * @param relName une chaine de caractère  qui correspond au nom de la relation 
	 * @return ArrayList<Record> une liste de record 
	 * @throws IOException
	 */
	
	public ArrayList<Record> SelectAllFromRelation (String relName) throws IOException{
		
		ArrayList<Record> allRecords = null;
		
		for (int i = 0; i <heapFiles.size() ; i++) {
			
			if(heapFiles.get(i).getRelInfo().getNomDeLaRelation().equals(relName)) {
				allRecords = heapFiles.get(i).GetAllRecords();
			}
		}
		
		
		return allRecords;
		
	}
	
	/*
	 * methode qui retourne  une RelationInfo  qui correspond au nom de relation passé en paramètre
	 * @param relName une chaine de caractère  qui est le nom d'une relation
	 * @return RelationInfo  
     * @throws IOException
	 */
	
	public RelationInfo getRel (String relName) throws IOException{
		
		RelationInfo relInfo=null;
		
		for (int i = 0; i <heapFiles.size() ; i++) {
			
			if(heapFiles.get(i).getRelInfo().getNomDeLaRelation().equals(relName)) {
				relInfo = heapFiles.get(i).getRelInfo();
			}
		}
		
		
		return relInfo;
	}
	
	public void reset() {
		heapFiles.clear();
	}

	public ArrayList<HeapFile> getHeapFiles() {
		return heapFiles;
	}

	public void setHeapFiles(ArrayList<HeapFile> heapFiles) {
		this.heapFiles = heapFiles;
	}
	
}
