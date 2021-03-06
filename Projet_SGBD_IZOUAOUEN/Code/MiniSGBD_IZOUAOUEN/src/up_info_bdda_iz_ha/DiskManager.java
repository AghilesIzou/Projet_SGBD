package up_info_bdda_iz_ha;

import java.io.IOException;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/*
 * Class DiskManager pour la gestion de l'espace disque 
 * 
 * 
 * 
 * @author Aghiles_IZOUAOUEN  
 * 
 * @version 1.0 
 * 
 *
 */

public class DiskManager {
	
	
	private DiskManager() {}
	
	private static class Holder{
		public static DiskManager diskManager= new DiskManager();
	}
	
	public static DiskManager getInstance() {
		
		return Holder.diskManager;
	}
	
	/*
	 * methode CreateFile pour créer un fichier 
	 * @param un entier correspondant à l'indice d'un fichier 
     * @throws IOException
	 */
	public void CreateFile(int fileIdx) throws IOException {
		
		RandomAccessFile file = new RandomAccessFile(DBParams.dbPath+ "/Data_"+fileIdx+".rf", "rw");	
		file.close();
	}
	
	/*
	 * methode AddPage pour ajouter une page
	 * @param un entier correspondant à l'indice du fichier dans lequel on ajoute la page 
	 * @return une PageId
         * @throws IOException
	 */
	
	public  PageId AddPage(int fileIdx) throws IOException {
		
		RandomAccessFile raf = new RandomAccessFile(DBParams.dbPath+ "/Data_"+fileIdx+".rf","rw");
		
		long longueurFichier = raf.length();
		int idPage = (int)(longueurFichier/DBParams.pageSize);
		raf.seek((long)longueurFichier);
		
		for (int i=0; i<DBParams.pageSize; i++){
			raf.writeByte(0);
		}
		raf.close();
		
		return new PageId(fileIdx,idPage); 
		
	}
	
	/*
	 * methode ReadPage pour lire une page depuis un fichier existant
	 * @param un PageId correspondant à l'indice  d'une page + l'indice du ficher dans lequel elle se trouve
	 * @param buff un byteBuffer dans lequel on lit la page
	 * @throws IOException
	 */
	
	public  void ReadPage(PageId pageId, ByteBuffer buff ) throws IOException {
		
		RandomAccessFile raf = new RandomAccessFile(DBParams.dbPath+"/Data_"+pageId.getFileIdx()+".rf", "rw");
		raf.seek(pageId.getPageIdx()* DBParams.pageSize);
		raf.read(buff.array());
		raf.close();
	}
	
	/*
	 * methode WritePage pour ecrire sur une page
	 * @param un PageId correspondant à l'indice  d'une page + l'indice du ficher dans lequel elle se trouve
	 * @param buff un byteBuffer dans lequel on lit la page
	 * @throws IOException
	 */
	
	public  void WritePage(PageId pageId, ByteBuffer buff ) throws IOException {
		
		RandomAccessFile raf = new RandomAccessFile(DBParams.dbPath+"/Data_"+pageId.getFileIdx()+".rf", "rw");
		raf.seek(pageId.getPageIdx() * DBParams.pageSize);
		raf.write(buff.array());
		raf.close();
	}
	
	

}
