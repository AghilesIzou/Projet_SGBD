package up_info_bdda_iz_ha;

/*
 * Class qui définit l'identifiant de la page 
 * 
 * 
 * 
 * @author Aghiles_IZOUAOUEN   
 * 
 * @version 1.0 
 * 
 *
 */

public class PageId {
	
	/*
	 * un entier correspondant à l'indice d'un fichier 
	 */
	
	private int FileIdx;
	
	/*
	 * un entier correspondant à l'indice d'une page
	 */
	private int PageIdx;
	
	public PageId(int FileIdx, int PageIdx) {
		this.FileIdx=FileIdx;
		this.PageIdx =PageIdx;
		
	}

	public int getFileIdx() {
		return FileIdx;
	}

	public void setFileIdx(int FileIdx) {
		this.FileIdx = FileIdx;
	}

	public int getPageIdx() {
		return PageIdx;
	}

	public void setPageIdx(int PageIdx) {
		this.PageIdx = PageIdx;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageId autre = (PageId) obj;
		if (FileIdx != autre.FileIdx)
			return false;
		if (PageIdx != autre.PageIdx)
			return false;
		return true;
	}
}
