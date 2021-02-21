package up_info_bdda_iz_ha;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class DiskManagerTests {
	
	public void  CreerFichier(int fileIdx) throws IOException {
		
		RandomAccessFile file = new RandomAccessFile(DBParams.dbPath+ "/Data_"+fileIdx+".rf", "rw");
		System.out.println("le fichier  a été crée avec succés");
		file.close();
		
	}
	
	public PageId AjouterPage(int fileIdx) throws IOException {
		
		RandomAccessFile raf = new RandomAccessFile(DBParams.dbPath+ "/Data_"+fileIdx+".rf","rw");
		
		long longueurFichier = raf.length();
		System.out.println("longueur du fichier : "+longueurFichier+" nombre de pages : "+(longueurFichier/4096));
		int idPage = (int)(longueurFichier/DBParams.pageSize);
		raf.seek((long)longueurFichier);
		for (int i=0; i<DBParams.pageSize; i++){
			raf.writeByte(0);
			
		}
		raf.close();
		
		return new PageId(fileIdx,idPage);
		
		
			
	}
	 
	public void LirePage(PageId pageId, ByteBuffer buff ) throws IOException {
		
		RandomAccessFile raf = new RandomAccessFile(DBParams.dbPath+"/Data_"+pageId.getFileIdx()+".rf", "rw");
		raf.seek(pageId.getPageIdx()* DBParams.pageSize);
		raf.read(buff.array());
		raf.close();
		
	}
	
	public void EcrirePage(PageId pageId, ByteBuffer buff ) throws IOException {
		
		RandomAccessFile raf = new RandomAccessFile(DBParams.dbPath+"/Data_"+pageId.getFileIdx()+".rf", "rw");
		raf.seek(pageId.getPageIdx()* DBParams.pageSize);
		raf.write(buff.array());
		raf.close();
		
	}
	

	public static void main(String[] args) {
		
		DBParams.dbPath = "/home/rilles/Bureau/dossiers_bureau/Projet_BDDA_IZOUAOUEN_HAMMOUDI/DB";
		DBParams.pageSize=4096;
		
		System.out.println("\t----------------------------------------------------\n");
		System.out.println("\t\t*********** Test couche DiskManager *************\n\n");
		
		DiskManagerTests ds = new DiskManagerTests();
		ByteBuffer buff = ByteBuffer.allocate(DBParams.pageSize);
		ByteBuffer buff2 = ByteBuffer.allocate(DBParams.pageSize);
		try {
			System.out.println("création d'un File dans le dossier DB :");
			ds.CreerFichier(1);
			System.out.println("Ajout de deux pages dans le File créé.");
			System.out.println("Affichage de la longeur de ce fichier et du nombre de pages contenu avant chaque ajout de page\n");
			
			PageId pg1 = ds.AjouterPage(1);
			PageId pg2 = ds.AjouterPage(1);
			PageId pg3 = ds.AjouterPage(1);
			System.out.println("PageId de pg1 :  "+pg1.getPageIdx()+", se trouvant dans le FILE N° : "+pg1.getFileIdx());
			System.out.println("PageId de pg2 :  "+pg2.getPageIdx()+", se trouvant dans le FILE N° : "+pg2.getFileIdx());
			System.out.println("PageId de pg3 :  "+pg3.getPageIdx()+", se trouvant dans le FILE N° : "+pg3.getFileIdx());
			
			System.out.println("\nécrire une page sur le disque : ");
			
			String str = "Aghiles Izouaouen, Hammoudi Yanis, Projet BDDA";
			System.out.println("\nle contenu de la page à écrire est  : "+str);
			for (int j = 0; j < str.length(); j++) {
				buff.putChar(str.charAt(j));
			}
			ds.EcrirePage(pg1, buff);
			
			System.out.println("\nlire le contenu de la page de cette dernière page depuis le disque puis afficher son contenu  : \n");
			ds.LirePage(pg1, buff2);
			
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j <str.length(); j++) {
				
				sb.append(String.valueOf(buff2.getChar()));
			}
			
			System.out.println(sb.toString());
			
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
