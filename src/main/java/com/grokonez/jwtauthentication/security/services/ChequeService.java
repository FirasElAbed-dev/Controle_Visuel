package com.grokonez.jwtauthentication.security.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.attijari.demo.exception.ResourceNotFoundException;
import com.attijari.demo.metier.DiskFileExplorer;
import com.grokonez.jwtauthentication.model.Cheque;
import com.grokonez.jwtauthentication.model.ParamCnxDB;
import com.grokonez.jwtauthentication.repository.IChequeRepository;

@Service

@Transactional
public class ChequeService {
	@Autowired
	private IChequeRepository chequeRepository;
	private static Set<String> listImageCheq = new HashSet<String>();
	
	private static String user_cnx;
	private static String pswd_cnx;
	private static String host_cnx;
	private static String output_path;

	public ChequeService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public  void chargerParamFromJson() {
		String userDirectory = new File("").getAbsolutePath();
		JSONParser jsonP = new JSONParser();		
			JSONObject jsonO;
			try {
				jsonO = (JSONObject) jsonP.parse(new FileReader(userDirectory + "/target/param1.json"));
				host_cnx = (String) jsonO.get("host_cnx");
				user_cnx = (String) jsonO.get("user_cnx");
				pswd_cnx = (String) jsonO.get("pswd_cnx");
				output_path = (String) jsonO.get("output_path");
				System.out.println("chargement param de cnx ,host_cnx: " + host_cnx + " , user_cnx: " + user_cnx + " , pswd_cnx: "	+ pswd_cnx+" ,output_path : "+output_path);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}

	public  boolean checkDBcnx() {
		boolean access = false;
		Connection conn2 = null;
		System.out.println("checkDBcnx ,host_cnx: " + host_cnx + " , user_cnx: " + user_cnx + " , pswd_cnx: " + pswd_cnx);

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			// use : with sid or / with service name
			TimeZone timeZone = TimeZone.getTimeZone(" Africa/Tunis");
			TimeZone.setDefault(timeZone);

			conn2 = DriverManager.getConnection(host_cnx, user_cnx, pswd_cnx);

			if (conn2 != null) {
				access = true;
				System.out.println("test access db ok ");
			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Exception occurred connecting to database:checkDBcnx {}" + ex.getMessage());
		} catch (SQLException ex) {
			System.out.println("Exception occurred connecting to database:checkDBcnx {}" + ex.getMessage());
		} finally {
			try {
				if (conn2 != null && !conn2.isClosed()) {
					conn2.close();
					System.out.println("test cnx db closed ");
				}
			} catch (SQLException ex) {
				System.out.println("Exception occurred closing cnx  to database:checkDBcnx {}" + ex.getMessage());
			}
		}
		return access;
	}

	public  Connection startDBcnx() {
		Connection conn2 = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			// use : with sid or / with service name
			TimeZone timeZone = TimeZone.getTimeZone(" Africa/Tunis");
			TimeZone.setDefault(timeZone);
			conn2 = DriverManager.getConnection(host_cnx, user_cnx, pswd_cnx);
			if (conn2 != null) {
				System.out.println("access db ok ");

			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Exception occurred connecting to database: {}" + ex.getMessage());
		} catch (SQLException ex) {
			System.out.println("Exception occurred connecting to database: {}" + ex.getMessage());
		}
		return conn2;
	}

	public  void closecnx(Connection conn2) {
		try {
			if (conn2 != null && !conn2.isClosed()) {
				conn2.close();
				System.out.println("cnx db closed ");
			}
		} catch (SQLException ex) {
			System.out.println("Exception occurred closing cnx  to database: {}" + ex.getMessage());
		}
	}

	
	
	public  Blob getSignature(Connection cnx, String ncp,String age) {
		 
		Connection conn = cnx;
		Blob blobSignat = null;
		String ncpAge=ncp+age;
		Map<String, Blob> couple_Signt_NcpAge = new HashMap<String, Blob> ();
		
		if (conn != null) {
			// "select p.par_valeur from parametrage p where p.par_code=\'FICHIER_COND\'";
			 String getSignatureSQL ="select IMAGE from bksig_blob  where chemin =(select IDENT_SIG from bksig_compte where age =\'00201\' and ncp =\'0063450704\')";
			try {
				Statement signatureSTMT = conn.createStatement();
				ResultSet signatureResult = signatureSTMT.executeQuery(getSignatureSQL);
				while (signatureResult.next()) {
					blobSignat = signatureResult.getBlob(1) ;
					couple_Signt_NcpAge.put(ncpAge, blobSignat);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Exception occurred getting fileName from  db : {}" + e.getMessage());
			}
		}
		return blobSignat;
	}
	
	
	

	
	
	// extraire les données de chaque fichier data 
	public  ArrayList manageDataFile(String pathFichData) {
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
					String Cm7NumAgenc = CMC7.substring(12, 15);
					String Cm7NumCOMPT = CMC7.substring(15, 25);

					String Cm7cleRib = CMC7.substring(25, 27);
					mapCheq.put("NmbrCheq", NmbrCheq);
					mapCheq.put("Cm7numCHEQ", Cm7numCHEQ);
					mapCheq.put("Cm7NumAgenc", Cm7NumAgenc);
					mapCheq.put("Cm7NumCOMPT", Cm7NumCOMPT);

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

	public  Set<String> getAllPictFromDirectory(String pathDirectory) {

			File dir = new File(pathDirectory);
			File[] liste = dir.listFiles();
			for (File item : liste) {
				if (item.isFile()) {
					if (item.getName().endsWith(".JPEG")&& item.getName().startsWith("IMAG_CHQ_")) {
						listImageCheq.add(item.getAbsolutePath());
						System.out.println(item.getName());	
					}				

				} else if (item.isDirectory()) {
					getAllPictFromDirectory(item.getAbsolutePath());
				}
			}
			
			
			return listImageCheq;
		}
	
	
	

	
	public  ArrayList parcouriMap(ArrayList<HashMap> listChequesData,Set<String> listImageCheq) {
		
		ArrayList<String> listimagtrouver = new ArrayList<String>();

		//start traitement cnx 
		chargerParamFromJson();
		
		if (checkDBcnx()) {   
			Connection c = startDBcnx();
	
		
			
		

		for (Map<String, String> myMap : listChequesData) {
			// Afficher le contenu du MAP
			Set listKeys = myMap.keySet(); // Obtenir la liste des clés
			Iterator iterateur = listKeys.iterator();
			// Parcourir les clés et afficher les entrées de chaque clé;
			while (iterateur.hasNext()) {
				Object key = iterateur.next();
				// System.out.println (key+"=>"+myMap.get(key));
			}
			// Afficher une entrée spécifique	
			//listNumCheq.add(myMap.get("Cm7numCHEQ"));
			
			if (!(findPictures( listImageCheq,  myMap.get("Cm7numCHEQ")).isEmpty())) {

				for (String string : findPictures( listImageCheq,  myMap.get("Cm7numCHEQ"))) {
					// a ce niveau j'ai pour un fichier data tout les données de chaque fichier 
					// ansique chemin path du fichier image 
					System.out.println("test"+myMap.get("Cm7numCHEQ")+" : "+ string);
					listimagtrouver.add(string);
					
					
					Cheque cheq = new Cheque();
					File fileR = null ;
			    	File fileV = null;
			    	byte[] bFileR = null ;
					byte[] bFileV = null ;
					
					if (string.endsWith("R.JPEG")) {
				    	 fileR = new File(string);
						 bFileR = new byte[(int) fileR.length()];

					}else if (string.endsWith("V.JPEG")) {
				    	 fileV = new File(string);
						 bFileV = new byte[(int) fileV.length()];

					}

					 
					
		    		cheq.setNumChq(myMap.get("Cm7numCHEQ"));
		    		cheq.setNcpTireur(myMap.get("Cm7NumCOMPT"));
		    		//mapCheq.put("Cm7NumAgenc", Cm7NumAgenc);
		    		 
		    		//(assuming you have a ResultSet named RS)
		    		 Blob blob = getSignature( c, myMap.get("Cm7NumCOMPT"),myMap.get("Cm7NumAgenc") ) ;
	 
		    		// traitement du blob signature de la base amplitude to appDB 
		    		 int blobLength;
					try {
						blobLength = (int) blob.length();
						byte[] blobAsBytes = blob.getBytes(1, blobLength);

			    		 //release the blob and free up memory. (since JDBC 4.0)
			    		cheq.setSignature(blobAsBytes);
			    		blob.free();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
		    		 
		    		
		    		try {
						FileInputStream fileInputStreamR = new FileInputStream(fileR);
						FileInputStream fileInputStreamV = new FileInputStream(fileV);

						// convert file into array of bytes
						fileInputStreamR.read(bFileR);
						fileInputStreamV.read(bFileV);

						fileInputStreamR.close();
						fileInputStreamV.close();


					} catch (Exception e) {
						e.printStackTrace();
					}	
		    		cheq.setRecto(bFileR);
		    		cheq.setVerso(bFileV);

		    		chequeRepository.save(cheq);
					
					
					
					
					
				}
			}				
			}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		closecnx(c);

		}
		
		
		return listimagtrouver;
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	public  List<String> findPictures(Set<String> listImageCheq2, String numCheq) {
		List<String> rectoVersoCheq = new ArrayList<String>();
		String numeroCheq = numCheq;

		for (String image : listImageCheq2) {
			if (image.contains(numeroCheq)) {
				rectoVersoCheq.add(image);
			}
		}
		return rectoVersoCheq;
	}	
	
	

	public  Set<String> getAllDataFile(String pathDirectory) {
		File dir = new File(pathDirectory);
		File[] liste = dir.listFiles();
		  Set<String> listDataFile = new HashSet<String>();

		for (File item : liste) {
			if (item.isFile()) {
				if (item.getName().endsWith(".DATA")) {
					listDataFile.add(item.getAbsolutePath());
					System.out.println(item.getName());	
				}				

			} 
		}
	return listDataFile;
	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	public Set<String> getImages(String repImages) {

		return getAllPictFromDirectory(repImages);

	}
	
	public  ArrayList getChequesData() {
		getAllPictFromDirectory("//bank-sud.tn/shared_doc/FirasControleVisuelTst");
		ArrayList<HashMap> k = manageDataFile("D://Donnees1003202115195704000.DATA");

		Set<String> dataFiles =getAllDataFile("//DELTA07/Partage_Sof/test prod/j-10032021/DATA");
//		for (String fichierData : dataFiles) {
//			ArrayList<HashMap> k = manageDataFile(fichierData);
//			parcouriMap(k, listImageCheq);
//		}
		
		return	parcouriMap(k, listImageCheq);

	}
	
	
	public Cheque addCheque(Cheque f) {
		return chequeRepository.save(f);
	}


	public Cheque getCheque(Long id) {
		return chequeRepository.findOneById(id);
	}

//	public List<Cheque> findBynomComplet(String nomComplet) {
//		return ChequeRepository.findBynomComplet(nomComplet);
//	}
//	public boolean existsChequeBynomComplet(String nomComplet) {
//		return ChequeRepository.existsChequeBynomComplet(nomComplet);
//	}
//	
//	public Cheque updateCheque( Long ChequeId,  Cheque ChequeRequest) {
//		return ChequeRepository.findById(ChequeId).map(Cheque -> {
//			Cheque.setFileName(ChequeRequest.getFileName());
//			Cheque.setApplicationSource(ChequeRequest.getApplicationSource());
//			Cheque.setStatus(ChequeRequest.getStatus());
//			Cheque.setNbrErreur(ChequeRequest.getNbrErreur());
//			Cheque.setDateTrait(ChequeRequest.getDateTrait());
//			Cheque.setNomComplet(ChequeRequest.getNomComplet());
//			Cheque.setDateRapportTrait(ChequeRequest.getDateRapportTrait());
//
//			return ChequeRepository.save(Cheque);
//		}).orElseThrow(() -> new ResourceNotFoundException("ChequeId " + ChequeId + " not found"));
//	}
//	public String getErrorDetails( String nomCheque,  String date,) {
//		
//		return null;
//		
//	}

}
