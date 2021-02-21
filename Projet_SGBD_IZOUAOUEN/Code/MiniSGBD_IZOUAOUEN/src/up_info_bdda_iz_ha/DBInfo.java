package up_info_bdda_iz_ha;

import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/*
 * Class DBInfo correspond à la base de données contient les informations de schéma pour l’ensemble de la base de données
 * 
 * 
 * 
 * @author Aghiles_IZOUAOUEN  
 * 
 * @version 1.0 
 * 
 *
 */

public class DBInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * un compteur de relations
	 */
	
	private  int compteur;
	
	/*
	 * une liste de RelationInfo
	 */
	
	List <RelationInfo> listeDeRelationsInfo ;
	
	private DBInfo() {
		listeDeRelationsInfo= new ArrayList<RelationInfo>();
		
	}
	private static class Holder{
		public static  DBInfo dbInfo = new DBInfo();
	}

	public static DBInfo getINSTANCE() {
		return Holder.dbInfo;
	}
	
	/*
	 * methode init du DBinfo nécessaire pour la récupération de l'ensemble des schémas de relation stcoké dans le fichier 
	 * 
	 *  Catalog.def
	 * 
	 * 
	 */
	public void Init() {
		
		File ficher = new File(DBParams.dbPath+"/Catalog.def");
		if (ficher.exists()) {
			FileInputStream fileInPut;
			ObjectInputStream in;
			
			try {
				fileInPut = new FileInputStream(ficher);
				in = new ObjectInputStream(fileInPut);
				Holder.dbInfo = (DBInfo) in.readObject();
			

				this.listeDeRelationsInfo = Holder.dbInfo.listeDeRelationsInfo;
				this.compteur = Holder.dbInfo.compteur;
				in.close();
				fileInPut.close();
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				System.out.println("Erreur de type FileNotFoundException");
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.out.println("Erreur de type IOException");
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
				System.out.println("Erreur de type ClassNotFoundException");
				
			}
		}
		
	}
	
	/*
	 * methode Finish du DBinfo qui s’occupe de sauvgarder l'instance actuel du DBinfo dans le fichier
	 * Catalog.def
	 */
	
	public void Finish()  {
		
		File fichier =new File(DBParams.dbPath+"/Catalog.def");
		
		try {
			FileOutputStream fileOutPut = new FileOutputStream(fichier);
			ObjectOutputStream out = new ObjectOutputStream(fileOutPut);
			out.writeObject(getINSTANCE());
			out.close();
			fileOutPut.close();
		} catch (FileNotFoundException e) {
			
			System.out.println("Erreur lors de la tentative d'ouverture  du fichier fileOutPut ou du fichier out");
		
		}catch (IOException e) {
			System.out.println("Erreur lors de la tentative de sérialisation du DBInfo");
		}
		
		
	}
	
	public void AddRelation(RelationInfo relation) {
		listeDeRelationsInfo.add(relation);
		this.compteur++;
	}

	public  int getCompteur() {
		return compteur;
	}

	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}
	
	public void reset() {
		listeDeRelationsInfo.clear();
		this.setCompteur(0);
		
	}

}
