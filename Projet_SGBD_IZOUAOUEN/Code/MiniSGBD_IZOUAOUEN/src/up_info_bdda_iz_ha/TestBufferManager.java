package up_info_bdda_iz_ha;

import java.io.IOException;
import java.nio.ByteBuffer;

public class TestBufferManager {

	public static void main(String[] args) {
		
		
		DBParams.dbPath = "/home/rilles/Bureau/dossiers_bureau/Projet_BDDA_IZOUAOUEN_HAMMOUDI/DB";
		DBParams.frameCount=2;
		DBParams.pageSize=4096;
		
		
		try {
			
			DiskManager.getInstance().CreateFile(2);
			System.out.println("\nAjout de 5 pages dans un fichier sur le disque.\n");
			PageId pg = DiskManager.getInstance().AddPage(2);
			PageId pg2 = DiskManager.getInstance().AddPage(2);
			PageId pg3 = DiskManager.getInstance().AddPage(2);
		//	PageId pg4 = DiskManager.getInstance().AddPage(2);
		//	PageId pg5 = DiskManager.getInstance().AddPage(2);
			
			//Erreur lorsque l'on essaye d'avoir les 5 pages en même temps étant donné que le bufferPool ne dispose que de deux frame
			
			ByteBuffer buff = BufferManager.getInstance().GetPage(pg);
			ByteBuffer buff2 =  BufferManager.getInstance().GetPage(pg2);
		//	ByteBuffer buff3 =  BufferManager.getInstance().GetPage(pg3);
		//	ByteBuffer buff4 = 	BufferManager.getInstance().GetPage(pg4);
		//	ByteBuffer buff5 = BufferManager.getInstance().GetPage(pg5);
			
			System.out.println("\nModification du contenu de la page 1 puis libération de celle-ci\n");
			
			String str1 = "Aghiles Izouaouen, Hammoudi Yanis, Projet BDDA";
			buff.position(0);
			for (int j = 0; j < str1.length(); j++) {
				buff.putChar(str1.charAt(j));
			}
			
			BufferManager.getInstance().FreePage(pg, 1);
			
			System.out.println("Lecture du contenu de la page 1 après libération avec dirty = 1 : ");
			
			ByteBuffer buffLecture = BufferManager.getInstance().GetPage(pg);
			
			StringBuilder sb = new StringBuilder();
			buffLecture.position(0);
			
			for (int j = 0; j <str1.length(); j++) {
				
				sb.append(String.valueOf(buffLecture.getChar()));
			}
			
			System.out.println("\n"+sb);
			
			String str2 = "Acteur, Tom, Cruise, Mission-impossible-7";
			buff.position(0);
			for (int j = 0; j < str2.length(); j++) {
				buff2.putChar(str2.charAt(j));
			}
			
			BufferManager.getInstance().FreePage(pg2, 1);
			
			ByteBuffer buffLecture2 = BufferManager.getInstance().GetPage(pg2);
			
			System.out.println("\nLecture du contenu de la page 2 avant libération avec flag dirty = 0 ");
			
			buffLecture2.position(0);
			StringBuilder sb2 = new StringBuilder();
			for (int j = 0; j < str2.length(); j++) {
				sb2.append(String.valueOf(buffLecture2.getChar()));
			}
			System.out.println("\n"+sb2);
			
			BufferManager.getInstance().FreePage(pg2, 0);
			
			ByteBuffer buffLecture3 = BufferManager.getInstance().GetPage(pg2);
			
			System.out.println("\nLecture du contenu de la page 2 après libération avec flag dirty = 0 ");
			
			buffLecture3.position(0);
			StringBuilder sb3 = new StringBuilder();
			for (int j = 0; j < str2.length(); j++) {
				sb3.append(String.valueOf(buffLecture3.getChar()));
			}
			System.out.println("\n"+sb3);
			
			BufferManager.getInstance().FreePage(pg2, 0);
			
			ByteBuffer bf3 = BufferManager.getInstance().GetPage(pg3);
			
			BufferManager.getInstance().FreePage(pg3, 0);
			
			//page vide 
			bf3.position(0);
			StringBuilder sbb = new StringBuilder();
			for (int j = 0; j < 30; j++) {
				sbb.append(String.valueOf(bf3.getChar()));
			}
			
			System.out.println("\n"+sbb);
			
		
			
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
		

	

}
