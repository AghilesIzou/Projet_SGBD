package up_info_bdda_iz_ha;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/*
 * classe RelationInfo qui contient les informations de schéma d’une relation
 * 
 * @author Aghiles_IZOUAOUEN 
 * 
 * @version 1.0 
 * 
 */

public class RelationInfo implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * le nom de la relation 
	 */
	
	private String nomDeLaRelation;
	
	/*
	 * le nombre de colonnes
	 */
	private int nbColonnes;
	
	/*
	 * liste qui contient les colInfo de la relation c'est à dire
	 * le nom et le type d'une colonne 
	 */
	private List<ColInfo> listeNomTypeColonnes;
	/*
	 * un entier correspondant à l'indice d'un fichier 
	 */
	
	private int fileIdx;
	
	/*
	 * représente la taille d’un record
	 */
	private int recordSize;
	
	/*
	 * représente le nombre de cases/slots dans la relation
	 */
	private int slotCount;
	
	public RelationInfo(String nomDeLaRelation,int nbColonnes, List<ColInfo> listeNomTypeColonnes, int fileIdx, int recordSize, int slotCount) {
		
		this.nomDeLaRelation = nomDeLaRelation;
		this.nbColonnes=nbColonnes;
		this.listeNomTypeColonnes =listeNomTypeColonnes;
		this.fileIdx=fileIdx;
		this.recordSize=recordSize;
		this.slotCount=slotCount;
	}
	
	public int getFileIdx() {
		return fileIdx;
	}

	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public int getSlotCount() {
		return slotCount;
	}

	public void setSlotCount(int slotCount) {
		this.slotCount = slotCount;
	}

	public RelationInfo(String nomDeLaRelation,int nbColonnes) {
		
		this.nomDeLaRelation = nomDeLaRelation;
		this.nbColonnes=nbColonnes;
		listeNomTypeColonnes = new ArrayList<ColInfo> ();
	}

	public String getNomDeLaRelation() {
		return nomDeLaRelation;
	}

	public void setNomDeLaRelation(String nomDeLaRelation) {
		this.nomDeLaRelation = nomDeLaRelation;
	}

	public int getNbColonnes() {
		return nbColonnes;
	}

	public void setNbColonnes(int nbColonnes) {
		this.nbColonnes = nbColonnes;
	}

	public List<ColInfo> getListeNomTypeColonnes() {
		return listeNomTypeColonnes;
	}

	public void setListeNomTypeColonnes(List<ColInfo> listeNomTypeColonnes) {
		this.listeNomTypeColonnes = listeNomTypeColonnes;
	}
	
}
