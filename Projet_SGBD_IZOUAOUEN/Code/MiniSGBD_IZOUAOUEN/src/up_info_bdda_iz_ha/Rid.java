package up_info_bdda_iz_ha;

public class Rid {
	
	/*
	 * classe Rid qui correspond au Record Id d’un enregistrement
	 * 
	 * @author Aghiles_IZOUAOUEN 
	 * 
	 * @version 1.0 
	 * 
	 */
	
	
	/*
	 * un PageId qui indique la page à laquelle appartient le record
	 */
	private PageId pageId;
	
	/*
	 * @param un entier qui est l’indice de la case où le record est stocké
	 */
	private int slotIdx;
	
	public  Rid(PageId pageId, int slotIdx) {
		this.pageId=pageId;
		this.slotIdx=slotIdx;
	
	}
	
	public Rid() {
		this(null,0);
	}

	public PageId getPageIdRecordId() {
		return pageId;
	}

	public void setPageIdRecordId(PageId pageId) {
		this.pageId = pageId;
	}

	public int getSlotIdx() {
		return slotIdx;
	}

	public void setSlotIdx(int slotIdx) {
		this.slotIdx = slotIdx;
	}

}
