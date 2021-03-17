package com.attijari.demo.metier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.metamodel.StaticMetamodel;

import org.springframework.beans.factory.annotation.Autowired;

import com.grokonez.jwtauthentication.model.Cheque;
import com.grokonez.jwtauthentication.repository.IChequeRepository;
import com.grokonez.jwtauthentication.security.services.ChequeService;

import javassist.expr.NewArray;

/**
 * Lister le contenu d'un répertoire
 */

public class DiskFileExplorer {

	private String initialpath = "";
	private Boolean recursivePath = false;
	public int filecount = 0;
	public int dircount = 0;

	private String ncp;
	private String numCheque;
	private String agence;
	private String date;
	
	private static Set<String> listImageCheq = new HashSet<String>();
	/**
	 * Constructeur
	 * 
	 * @param path      chemin du répertoire
	 * @param subFolder analyse des sous dossiers
	 */
	public DiskFileExplorer(String path, Boolean subFolder) {
		super();
		this.initialpath = path;
		this.recursivePath = subFolder;
	}

	public ArrayList<String> list() {
		return this.listDirectory2(this.initialpath);
	}
	public ArrayList<String> listDirectory2(String dir) {
		ArrayList<String> images = new ArrayList<String>();
		
		File file = new File(dir);
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory() == true) {
					//System.out.println("Dossier: " + files[i].getAbsolutePath());
					this.dircount++;
				} else {
					if (files[i].getName().startsWith("IMAG_CHQ_")&&files[i].getName().endsWith(".JPEG")) {
						System.out.println("  Fichier: " + files[i].getName());

						images.add(files[i].getAbsolutePath());

						this.filecount++;
					}
				}
				if (files[i].isDirectory() == true && this.recursivePath == true) {
					this.listDirectory2(files[i].getAbsolutePath());
				}
			}
		}
		
		return images;
	}
	public ArrayList<ArrayList<String>> listDirectory(String dir) {
		ArrayList<ArrayList<String>> listOLists = new ArrayList<ArrayList<String>>();
		ArrayList<String> singleList = new ArrayList<String>();
		File file = new File(dir);
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory() == true) {
					//System.out.println("Dossier: " + files[i].getAbsolutePath());
					this.dircount++;
				} else {
					if (files[i].getName().startsWith("IMAG_CHQ_")&&files[i].getName().endsWith(".JPEG")) {
						System.out.println("  Fichier: " + files[i].getName());

						singleList = splitString(files[i].getName());

						this.filecount++;
					}
				}
				if (files[i].isDirectory() == true && this.recursivePath == true) {
					this.listDirectory(files[i].getAbsolutePath());
				}
			}
			listOLists.add(singleList);
		}
		
		return listOLists;
	}

	
	
	
	
	
	public ArrayList<String> splitString(String fileName) {
		// IMAG_CHQ_ 0009139759 _0200591 _00038_ 20210312 _R.JPEG
		// ncp numCheque agence date
		ArrayList<String> l = new ArrayList<String>();
		String str = fileName;
		String[] arrOfStr = str.split("_");

		for (String a : arrOfStr) {
			l.add(a);
			System.out.println(a);
		}

		return l;

	}

	public Cheque chargerParam(List<String> l) {
		List<Cheque> cheques = new ArrayList<Cheque>();
		Cheque cheq = new Cheque();
		// ncp numCheque agence date
		for (String string : l) {
			cheq.setId(1L);
			cheq.setNcpTireur(l.get(2));
			cheq.setNumChq(l.get(3));
		}
		return cheq;

	}

	
	
	
	
	
	
	//2//
	public static ArrayList manageDataFile(String pathFichData) {
		ArrayList<ArrayList<String>> listOListsData = new ArrayList<ArrayList<String>>();
		ArrayList<String> listFichData = new ArrayList<String>();
		ArrayList listCheques = new ArrayList();
		// extrac info from nomfichData
		String Donnees = pathFichData.substring(0, 7);
		String JJMMAAAA = pathFichData.substring(7, 15);
		String HHMMSS = pathFichData.substring(15, 21);
		String BQ = pathFichData.substring(21, 23);
		String GGG = pathFichData.substring(23, 26);

		// start reading
		BufferedReader bufRed = null;
		FileReader filereader = null;
		BufferedReader bufferedreader = null;
		try {
			bufRed = new BufferedReader(new FileReader(pathFichData));
			String firstLine = bufRed.readLine();
			int firstlength = firstLine.length();
			// fichier en lecture
			filereader = new FileReader(pathFichData);
			bufferedreader = new BufferedReader(filereader);
			String strCurrentLine;
			// parcourir les lignes

			String CodeEnregistrementEntete = null;
			String NRemiseEntete = null;
			String RIBbenef = null;
			String NmbrCheq = null;
			String MntGlobal = null;
			String TypeRemise = null;
			String FillerEntete = null;
			while ((strCurrentLine = bufferedreader.readLine()) != null) {
				// System.out.println("line" + strCurrentLine + " : " +
				// strCurrentLine.length());
				if (strCurrentLine.length() == firstlength) {
					// 10000052040000100006166441490000000001000000002560960A
					CodeEnregistrementEntete = strCurrentLine.substring(0, 1);
					NRemiseEntete = strCurrentLine.substring(1, 8);
					RIBbenef = strCurrentLine.substring(8, 28);
					NmbrCheq = strCurrentLine.substring(28, 38);
					MntGlobal = strCurrentLine.substring(38, 53);
					TypeRemise = strCurrentLine.substring(35, 54);
					FillerEntete = strCurrentLine.substring(54, 100);
				} else {
					Map<String, String> mapCheq = new HashMap<>();

					String CodeEnregistrement = strCurrentLine.substring(0, 1);
					String NRemiseLigne = strCurrentLine.substring(1, 8);
					String CMC7 = strCurrentLine.substring(8, 35);
					String MntChequ = strCurrentLine.substring(35, 50);
					String CodeValeur = strCurrentLine.substring(50, 52);
					String DateEmission = strCurrentLine.substring(52, 60);
					String CodelieuEmission = strCurrentLine.substring(60, 61);
					String TypeChèque = strCurrentLine.substring(61, 62);
					String FillerEnteteLigne = strCurrentLine.substring(62, 170);
					// • Numéro de chèque sur 7 //RIB ://Code établissement sur 2//Code guichet sur
					// 3//Numéro de compte sur 13// Clé RIB sur 2
					String Cm7numCHEQ = CMC7.substring(0, 7);
					String Cm7codeEtabl = CMC7.substring(7, 9);
					String Cm7codeGuich = CMC7.substring(9, 12);
					String Cm7numCOMPT = CMC7.substring(12, 25);
					String Cm7cleRib = CMC7.substring(25, 27);
					mapCheq.put("NmbrCheq", NmbrCheq);
					mapCheq.put("Cm7numCHEQ", Cm7numCHEQ);
					listCheques.add(mapCheq);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedreader != null) {
					bufferedreader.close();
				}
				if (bufRed != null) {
					bufRed.close();
				}
				if (filereader != null)
					filereader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listCheques;
	}

	public static void parcouriMap(ArrayList<HashMap> listCheques,Set<String> listImageCheq2) {
		//parcourir map 
	
		for (Map<String, String> myMap : listCheques) {
			// Afficher le contenu du MAP
			Set listKeys = myMap.keySet(); // Obtenir la liste des clés
			Iterator iterateur = listKeys.iterator();
			// Parcourir les clés et afficher les entrées de chaque clé;
			while (iterateur.hasNext()) {
				Object key = iterateur.next();
				// System.out.println (key+"=>"+myMap.get(key));
			}
			// Afficher une entrée spécifique	
			//System.out.println(myMap.get("Cm7numCHEQ"));
			if (!(findPictures( listImageCheq2,  myMap.get("Cm7numCHEQ")).isEmpty())) {
				for (String string : findPictures( listImageCheq2,  myMap.get("Cm7numCHEQ"))) {
					System.out.println("test"+myMap.get("Cm7numCHEQ")+" : "+ string);

				}
			}
			
						
			}
	}

//	public static  ArrayList<String> getAllPictFromDirectory(String pathDirectory) {
//		
//		ArrayList<String> listImageCheq = new ArrayList<String>();
//		
//		File dir = new File(pathDirectory);
//		File[] liste = dir.listFiles();
//		for (File item : liste) {
//			if (item.isFile()) {
//				if (item.getName().endsWith(".JPEG")&& item.getName().startsWith("IMAG_CHQ_")) {
//					listImageCheq.add(item.getName());
//					//System.out.println(item.getName());	
//				}				
//
//			} else if (item.isDirectory()) {
//				getAllPictFromDirectory(item.getAbsolutePath());
//			}
//		}
//		
//		
//		return listImageCheq;
//	}

	public static List<String> findPictures(Set<String> listImageCheq2, String numCheq) {
		List<String> rectoVersoCheq = new ArrayList<String>();
		String numeroCheq = numCheq;

		for (String image : listImageCheq2) {
			if (image.contains(numeroCheq)) {
				rectoVersoCheq.add(image);
			}
		}
		return rectoVersoCheq;
	}	
//	TEST
public  Set<String> getAllPictFromDirectory(String pathDirectory) {
	  Set<String> listImageCheq = new HashSet<String>();

		File dir = new File(pathDirectory);
		File[] liste = dir.listFiles();
		for (File item : liste) {
			if (item.isFile()) {
				if (item.getName().endsWith(".JPEG")&& item.getName().startsWith("IMAG_CHQ_")) {
					listImageCheq.add(item.getAbsolutePath());
					//System.out.println(item.getName());	
				}				

			} else if (item.isDirectory()) {
				getAllPictFromDirectory(item.getAbsolutePath());
			}
		}
		
		
		return listImageCheq;
	}
//	public static void main(String[] args) {
//		ArrayList<HashMap> k = manageDataFile("D://Donnees1003202115195704000.DATA");
//		getAllPictFromDirectory("//bank-sud.tn/shared_doc/FirasControleVisuelTst");		
	//	parcouriMap(k,listImageCheq);
//
//		// bank-sud.tn/shared_doc/FirasControleVisuelTst
//		// IMAG_CHQ_0009139759_6348242_00038_20210312_R.JPEG
//		// ncp numCheque agence date
//	}

}