package up_info_bdda_iz_ha;

import java.io.Serializable;

/*
 * Class ColInfo qui contient le nom et le type d'une colonne
 * 
 * 
 * @author Aghiles_IZOUAOUEN  
 * 
 * @version 1.0 
 * 
 *
 */

public class ColInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nomDeLaColonne;
	private String typeDeLaColonne;
	
	public ColInfo(String nomDeLaColonne, String typeDeLaColonne) {
		this.nomDeLaColonne = nomDeLaColonne;
		this.typeDeLaColonne= typeDeLaColonne;
	}

	public String getNomDeLaColonne() {
		return nomDeLaColonne;
	}

	public void setNomDeLaColonne(String nomDeLaColonne) {
		this.nomDeLaColonne = nomDeLaColonne;
	}

	public String getTypeDeLaColonne() {
		return typeDeLaColonne;
	}

	public void setTypeDeLaColonne(String typeDeLaColonne) {
		this.typeDeLaColonne = typeDeLaColonne;
	}

}
