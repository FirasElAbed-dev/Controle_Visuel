//package com.grokonez.jwtauthentication.security.services;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.attijari.demo.metier.DateComparator;
//import com.attijari.demo.metier.GestionFichier;
//import com.attijari.demo.metier.Parser;
//import com.grokonez.jwtauthentication.model.Champ;
//import com.grokonez.jwtauthentication.model.Client;
//import com.grokonez.jwtauthentication.model.Fichier;
//import com.grokonez.jwtauthentication.model.Matrice;
//import com.grokonez.jwtauthentication.model.ParamCnxDB;
//import com.grokonez.jwtauthentication.model.Repertoire;
//import com.grokonez.jwtauthentication.repository.IClientRepository;
//import com.grokonez.jwtauthentication.repository.IParamCnxRepository;
//import com.grokonez.jwtauthentication.repository.IRepertoireRepository;
//
//@Service
//
//@Transactional
//public class RepertoireService {
//	@Autowired
//	private IRepertoireRepository repertoireRepository;
//	@Autowired
//	private MatriceService matriceService;
//	@Autowired
//	private ChampService champService;
//	@Autowired
//	private LineService lineService;
//	@Autowired
//	private FichierService fichierService;
//	@Autowired
//	private ClientService clientService;
//	@Autowired
//	private IClientRepository clientRepository;
//
//	@Autowired
//	private ParamCnxService paramCnxService;
//	@Autowired
//	private IParamCnxRepository paramCnxRepository;
//
//	public RepertoireService() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public Repertoire getFichier(Long id) {
//		return repertoireRepository.findOneById(id);
//	}
//	public static LocalDate getLocalDate() {
//	    return LocalDate.now();
//	}
//	public String testerCLIamplitude(String tidnid) {
//		Connection conn2 = null;
//		String dbURL2 = null;
//		String username = null;
//		String password = null;
//		String tid = tidnid.substring(0, 3);
//		String nid = tidnid.substring(3);
//		String cli = null;
//		try {
//			Class.forName("oracle.jdbc.OracleDriver");
//			List<ParamCnxDB> params = paramCnxRepository.findAll();
//			if (!params.isEmpty()) {
//				ParamCnxDB p = params.get(0);
//
//				dbURL2 = "jdbc:oracle:thin:@" + p.getUrl() + ":" + p.getPort() + p.getSid();
//				username = p.getUser();
//				password = p.getPswd();
//			}
//			// METHOD #2
//			// URL PORT SID username password
//			conn2 = DriverManager.getConnection(dbURL2, username, password);
//			java.sql.Statement stmt = conn2.createStatement();
//			ResultSet rs = stmt.executeQuery(
//					"select CLI,TID,NID from bkcli where NID=\'" + nid + "\' and " + "TID=\'" + tid + "\'");
//			// System.out.println("select CLI,TID,NID from bkcli where NID=\'" + nid + "\'
//			// and " + "TID=\'" + tid + "\'");
//			// 00011323 :CIN01018109
//			if (conn2 != null) {
//				while (rs.next()) {
//					/*
//					 * System.out.println("cbintint sql recherche  ni : " + nid + " :tid : " + tid +
//					 * rs.getString(1).replaceAll(" ", ""));
//					 */
//					cli = rs.getString(1).replaceAll(" ", "");
//				}
//			}
//
//		} catch (ClassNotFoundException ex) {
//			ex.printStackTrace();
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		} finally {
//			try {
//
//				if (conn2 != null && !conn2.isClosed()) {
//					conn2.close();
//				}
//
//			} catch (SQLException ex) {
//				ex.printStackTrace();
//			}
//		}
//
//		return cli;
//	}
//
//	public static JSONArray listerCreerClient(String FILENAME) {
//
//		String tidNid;
//		String idAppli;
//		BufferedReader bufferedreader = null;
//		FileReader filereader = null;
//		JSONArray jsonfichier = new JSONArray();
//		try {
//			// fichier en lecture
//			filereader = new FileReader(FILENAME);
//			bufferedreader = new BufferedReader(filereader);
//
//			String strCurrentLine;
//
//			// parcourir les lignes de chaque fichier
//			while ((strCurrentLine = bufferedreader.readLine()) != null) {
//				String str = strCurrentLine.toString();
//				String[] parts = str.split("\\|");
//
//				// récuperer dernier champ parts[parts.lenght-1] tidNid
//				tidNid = parts[(parts.length) - 1];
//				// récuperer premier champ parts[0] idAppli
//				idAppli = parts[0];
//				JSONObject jsonLine = new JSONObject();
//				jsonLine.put("idAppli", idAppli);
//				jsonLine.put("tidNid", tidNid);
//				jsonfichier.add(jsonLine);
//			}
//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//
//		} finally {
//
//			try {
//
//				if (bufferedreader != null)
//					bufferedreader.close();
//
//				if (filereader != null)
//					filereader.close();
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return jsonfichier;
//
//	}
//
//	public static JSONArray listerCreerCompte(String FILENAME) {
//
//		String ncp;// tidnid
//		String idAppli;
//		String tidNid;
//		BufferedReader bufferedreader = null;
//		FileReader filereader = null;
//		JSONArray jsonfichier = new JSONArray();
//		try {
//			// fichier en lecture
//			filereader = new FileReader(FILENAME);
//			bufferedreader = new BufferedReader(filereader);
//
//			String strCurrentLine;
//			//System.out.println("test boucle pour bkacp " + FILENAME);
//
//			// bkcom bktelcom bkemacom
//			if (FILENAME.contains("bkcom") || FILENAME.contains("bktelcom") || FILENAME.contains("bkemacom")) {
//
//				while ((strCurrentLine = bufferedreader.readLine()) != null) {
//					String str = strCurrentLine.toString();
//					String[] parts = str.split("\\|");
//
//					// récuperer dernier champ parts[parts.lenght-1] tidNid
//					tidNid = parts[(parts.length) - 1];
//					ncp = parts[0];
//					// récuperer premier champ parts[0] idAppli
//					idAppli = parts[2];
//					JSONObject jsonLine = new JSONObject();
//					jsonLine.put("tidNid", tidNid);
//					jsonLine.put("idAppli", idAppli);
//					jsonLine.put("ncp", ncp);
//					jsonfichier.add(jsonLine);
//				}
//			}
//			if (FILENAME.contains("bkacp") || FILENAME.contains("bkicom")) {
//				// bkacp bkicom
//				while ((strCurrentLine = bufferedreader.readLine()) != null) {
//					String str = strCurrentLine.toString();
//					String[] parts = str.split("\\|");
//
//					// récuperer dernier champ parts[parts.lenght-1] tidNid
//					ncp = parts[2];
//					// récuperer premier champ parts[0] idAppli
//					idAppli = parts[0];
//					JSONObject jsonLine = new JSONObject();
//					jsonLine.put("idAppli", idAppli);
//					jsonLine.put("ncp", ncp);
//					jsonfichier.add(jsonLine);
//
//				}
//
//			}
//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//
//		} finally {
//
//			try {
//
//				if (bufferedreader != null)
//					bufferedreader.close();
//
//				if (filereader != null)
//					filereader.close();
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return jsonfichier;
//
//	}
//
//
//
//	public JSONObject validerCltsComptParFich(String string) {
//
//		List<JSONObject> files = new ArrayList<JSONObject>();
//		Repertoire r = repertoireRepository.findAll().get(0);
//
//		JSONArray rapportFichier = new JSONArray();
//		JSONObject rapportLigne = new JSONObject();
//		/*********************************************************************************************/
//
//		Connection conn2 = null;
//		String dbURL2 = null;
//		String username = null;
//		String password = null;
//		try {
//			Class.forName("oracle.jdbc.OracleDriver");
//			List<ParamCnxDB> params = paramCnxRepository.findAll();
//			if (!params.isEmpty()) {
//				ParamCnxDB p = params.get(0);
//				// "jdbc:mysql:///test?allowMultiQueries=true";
//				dbURL2 = "jdbc:oracle:thin:@" + p.getUrl() + ":" + p.getPort() + p.getSid();
//				username = p.getUser();
//				password = p.getPswd();
//			}
//			// METHOD #2
//			// URL PORT SID username password
//			conn2 = DriverManager.getConnection(dbURL2, username, password);
//
//			/*********************************************************************************************/
//
//			boolean statusFichier = true;
//			int nmbrErr = 0;
//			StringBuilder erreurList = new StringBuilder();
//			StringBuilder cheminFichier = new StringBuilder();
//
//			cheminFichier.append(r.getRepIn());
//			cheminFichier.append(string);
//			String FILENAME = cheminFichier.toString();
//
//			// clients fichier en cour
//			JSONArray clientsMap = RepertoireService.listerCreerClient(FILENAME);// retourner tidnid et idappli de
//																					// chaque client
//			JSONArray comptesMap = RepertoireService.listerCreerCompte(FILENAME);// retourner ncp et idappli de chaque
//																					// client
//			//System.out.println("test boucle pour bkacp" + ":" + comptesMap.toJSONString());
//
//			if (!string.contains("bkcli")) {
//				if (string.contains("bkicom") || string.contains("bkacp") || string.contains("bkemacom")
//						|| string.contains("bktelcom")) {
//					// verif exitence compte
//					for (Object object : comptesMap) {
//						JSONObject obj = (JSONObject) object;
//						boolean exist = false;
//						obj.get("ncp").toString();
//						// verif exitence compte dans amplitude
//						String compteAmpl = paramCnxService.testerCOMPTEamplitude(obj.get("ncp").toString(), conn2);
//						// verif existence compte dans db
//						boolean existCompte = clientRepository.existsClientByCompteCli(obj.get("ncp").toString());
//						//System.out.println("boolean test " + existCompte + "exist dans amplitude :" + compteAmpl + ":");
//						// String
//						// compteDb=clientRepository.findByTidNid(obj.get("ncp").toString()).getCompteCli();
//
//						// test des comptes
//						if ((compteAmpl == null || compteAmpl.isEmpty()) && (existCompte == false)) {
//							rapportLigne.put("nomfichier", string);
//							statusFichier = false;
//							erreurList.append(" : ");
//							erreurList.append(obj.get("ncp").toString());
//							erreurList.append(" : ");
//							nmbrErr++;
//							//System.out.println("test erreur list" + erreurList + ":" + nmbrErr);
//
//							rapportLigne.put("msg", "erreur compte inexistant" + erreurList);
//							rapportLigne.put("nbrErr", nmbrErr);
//						}
//						/*
//						 * if (statusFichier==false) { rapportLigne.put("msg",
//						 * "erreur compte inexistant"+erreurList); rapportLigne.put("nbrErr", nmbrErr);
//						 * 
//						 * }
//						 */
//						// rapportFichier.add(rapportLigne);
//
//					}
//
//				} else {
//					// verif existence client
//					for (Object object : clientsMap) {
//
//						JSONObject obj = (JSONObject) object;
//						boolean exist = false;
//						obj.get("tidNid").toString();
//						// verif exitence client dans amplitude
//						String cltAmpl = paramCnxService.testerCLIamplitude(obj.get("tidNid").toString(), conn2);
//						// verif existence compte dans db
//						boolean existClient = clientRepository.existsClientByTidNid(obj.get("tidNid").toString());
//					
//
//						// test des clients
//						if ((cltAmpl == null || cltAmpl.isEmpty()) && (existClient == false)) {
//							rapportLigne.put("nomfichier", string);
//							statusFichier = false;
//							erreurList.append(" : ");
//							erreurList.append(obj.get("tidNid").toString());
//							erreurList.append(" : ");
//							nmbrErr++;
//							//System.out.println("test erreur list" + erreurList + ":" + nmbrErr + string);
//							rapportLigne.put("msg", "erreur client inexistant" + erreurList);
//							rapportLigne.put("nbrErr", nmbrErr);
//						}
//
//					}
//					/*
//					 * if (statusFichier==false) { rapportLigne.put("msg",
//					 * "erreur client inexistant"+erreurList); rapportLigne.put("nbrErr", nmbrErr);
//					 * 
//					 * }
//					 */
//				}
//
//			} else {// fichier bkcli
//					// test si client exist dans amplitude
//					// sinon ajouter un client
//			}
//			rapportFichier.add(rapportLigne);
//			/*********************************************************************************************/
//			// fermer cnx amplitude
//
//		} catch (ClassNotFoundException ex) {
//			ex.printStackTrace();
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		} finally {
//			try {
//
//				if (conn2 != null && !conn2.isClosed()) {
//					conn2.close();
//				}
//
//			} catch (SQLException ex) {
//				ex.printStackTrace();
//			}
//		}
//
//		/*********************************************************************************************/
//
//		return rapportLigne;
//
//	}
//
//	
//	
//	public  List<String> listerFichierRepPARSING(String rep)  {	
//		
//		List<String> FichiersDuJour = new ArrayList<String>();
//		Set<String> dateSet = new HashSet<String>();
//		List<String> dateList = new ArrayList<String>();
//		File dir = new File(rep);
//		
//		//repertoir pour les fichiers vide de taille 0
//		String pathRepVid=rep+"repFichiersVide";
//		File file = new File(pathRepVid);
//        if (!file.exists()) {
//            if (file.mkdir()) {
//                System.out.println("Directory pathRepVid is created!");
//            } else {
//                System.out.println("Failed to create directory pathRepVid !");
//            }
//        }
//		
//		
//		
//		File[] directoryListing = dir.listFiles();
//
//		if (directoryListing != null) {
//			for (File child : directoryListing) {
//
//				if (child.getName().startsWith("bk") && child.getName().endsWith(".txt")&&(!child.getName().contains("bkrecap"))) {
//					System.out.println(child.getName());
//					//tester si fichier vide le depalcer 
//					 //File tmpFile = new File(child.getName());
//					 if(child.length() == 0) {//deplacer le fichier
//						 System.out.println("vide:"+child.getName());
//						 /*****************************************************************/
//							/******************list des fich avec vide db *******************/
//						 Fichier fichVid=  GestionFichier.creerFichRejOrVideDB(rep+child.getName(), rep, "O");
//						 System.out.println("test affichage fichVid"+fichVid);
//						 boolean existDB = false;
//						 try {
//							  existDB =fichierService.existsFichierBynomComplet(fichVid.getNomComplet());
//
//						} catch (NullPointerException e) {
//								System.out.println("My exception"+e);						}
//						
//						    if (existDB) {
//								//si fichier deja traite 
//								Long fichierId=fichierService.findBynomComplet(fichVid.getNomComplet()).get(0).getId();
//								fichierService.updateFichier(fichierId, fichVid);
//							}else {
//
//								 System.out.println("test affichage fichVid"+fichVid);
//								 
//								 
//								 
//								fichierService.addFichier(fichVid);
//
//							}
//						    
//						    /*****************************************************************/
//						    boolean resultat = child.renameTo(new File(pathRepVid, child.getName()));
//						    
//						 }else {
//					FichiersDuJour.add(child.getName());
//					String[] tabDate = child.getName().split("\\_");
//					String dateString = tabDate[2].substring(0, 8);
//					dateSet.add(dateString);
//					 }
//				} // Do something with child
//
//			}
//			for (String filename : FichiersDuJour) {
//			}
//		} else {
//			/*
//			 * // Handle the case where dir is not really a directory. // Checking
//			 * dir.isDirectory() above would not be sufficient // to avoid race conditions
//			 * with another process that deletes // directories.
//			 */
//		}
//		for (String date : dateSet) {
//			String d = date.subSequence(0, 4) + "-" + date.subSequence(4, 6) + "-" + date.subSequence(6, 8);
//			dateList.add(d);
//		}
//		Collections.sort(dateList, new DateComparator("dd-MM-yyyy"));
//		List<String> finalListe = Parser.listerParDate(FichiersDuJour, dateList);
//		return finalListe;
//	}
//	
//
//	
//	
//	public List<Fichier> gererFichiers(List<JSONObject> f) throws ParseException {
//		System.out.println("recherche recap"+f);
//		
//		// optimisation 1 effectu la cnx a la base amplitude une seul fois pour test
//		List<Fichier> finalList = new ArrayList<>();
//		List<String> fichiers = new ArrayList<String>();
//
//		// 1 cnx amplitude pour les test
//		/*********************************************************************************************/
//		Connection conn2 = null;
//		String dbURL2 = null;
//		String username = null;
//		String password = null;
//		try {
//			Class.forName("oracle.jdbc.OracleDriver");
//			List<ParamCnxDB> params = paramCnxRepository.findAll();
//			if (!params.isEmpty()) {
//				ParamCnxDB p = params.get(0);
//				// "jdbc:mysql:///test?allowMultiQueries=true";
//				dbURL2 = "jdbc:oracle:thin:@" + p.getUrl() + ":" + p.getPort()  + p.getSid();
//				username = p.getUser();
//				password = p.getPswd();
//			}
//			// METHOD #2
//			// URL PORT SID username password
//			conn2 = DriverManager.getConnection(dbURL2, username, password);
//
//			/*********************************************************************************************/
//			// 2 liste json -> list string path
//			List<JSONObject> files = f;
//			for (Object object : files) {
//				JSONObject obj = (JSONObject) object;
//
//				if (obj.containsKey("path")) {
//					String s = obj.get("path").toString();
//					fichiers.add(s);
//				}
//			}
//
//			// nvl liste apres validation client compte
//			List<String> fichiersAccepte = new ArrayList<String>(fichiers); 
//			List<String> fichiersErreur = new ArrayList<String>();
//
//			// 3 repertoir de traitement de la base de donnee
//			Repertoire r = repertoireRepository.findAll().get(0);
//			String rep = r.getRepIn();
//			String repOut = r.getRepOut();
//			String repArchiv = r.getRepArchiv();
//			List<JSONArray> myList = new ArrayList<JSONArray>();
//			int CLI;
//			Matrice m;
//			String FILENAME;
//
//			// parcourir repertoir
//			for (String fichierDuRep : fichiers) {
//
//				JSONObject j = validerCltsComptParFich(fichierDuRep);
//				if (!j.isEmpty()) {// seul les fichiers valide 
//					JSONObject obj = (JSONObject) j;
//					fichiersErreur.add(obj.get("nomfichier").toString());
////					obj.get("nbrErr").toString();
////					obj.get("nomfichier").toString();
////					obj.get("msg").toString();
////					obj.get("nbrErr").toString()
//					/*****************************************************************/
//					/******************list des fich avec erreurs *******************/
//					String status="R";
//					
//					Fichier rejectedFile=GestionFichier.creerFichRejOrVideDB(rep+obj.get("nomfichier").toString(), rep, status);
//					rejectedFile.setNbrErreur( Integer.parseInt(obj.get("nbrErr").toString()));
//					
//					if (fichierService.existsFichierBynomComplet(rejectedFile.getNomComplet())) {
//						//si fichier deja traite 
//						Long fichierId=fichierService.findBynomComplet(rejectedFile.getNomComplet()).get(0).getId();
//						fichierService.updateFichier(fichierId, rejectedFile);
//						 System.out.println("test affichage rejectedFile"+rejectedFile);
//
//					}else {
//						fichierService.addFichier(rejectedFile);
//						 System.out.println("test affichage rejectedFile"+rejectedFile);
//
//					}
//					
//					
//					//fichiersErreur
//					/*****************************************************************/
//				} else {
//
//					/*****************************************************************/
//					/******************list des fich accepter debut trait*************/
//		
//					/*****************************************************************/
//					System.out.println("cbint fichier de la liste " + fichierDuRep.toString());
//
//					// choisir matrice selon nom fichier
//					String[] parties = fichierDuRep.split("\\_");
//					String nomFichierEtMatrice = parties[0];
//
//					m = matriceService.getMatriceByName(nomFichierEtMatrice);
//					if (m != null) {
//						// recuperer la liste des champs du matrice
//						List<Champ> champs = champService
//								.getAllChampsByMatriceId(matriceService.getMatriceByName(nomFichierEtMatrice).getId());
//
//						// matrice existant mais sans les champs ( ou supprimer )
//
//						if (champs == null || champs.isEmpty()) {
//							// ajout d'un champ fictif
//							// remplacer : champ1 : champ1 : 50
//							System.out.println("erreur cas fictif ");
//
//							Champ champ = new Champ();
//							champ.setAction("remplacer");
//							champ.setChampName("champ1");
//							champ.setValeur("champ1");
//							champs.add(champ);
//
//						}
//
//						StringBuilder cheminFichier = new StringBuilder();
//						cheminFichier.append(rep);
//						cheminFichier.append(fichierDuRep);
//						FILENAME = cheminFichier.toString();
//						SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
//						try {
//							CLI = lineService.getLastLine().getId().intValue();
//						} catch (NullPointerException e) {
//							CLI = 1;
//						}
///************************************************************************verif ok *************************************************************/
//						// traitement des clients , dans le fichiers , dans la base , et dans amplitude
//						JSONArray clientsMap = listerCreerClient(FILENAME);
//
//						System.out.println("cbintint: la listes des clients dans le fichier: " + FILENAME + " sont : "+ clientsMap.toString());
//						List<Client> clients = clientRepository.findAll();
//
//						if (!FILENAME.contains("bkcom")) {// je doit explure les autres fichiers  compte et a voir le reste des client 
//							for (Object object : clientsMap) {// verif et creation des nouveaux clients 
//								// recuperer la liste des clients dans la base application local
//								clients = clientRepository.findAll();
//								JSONObject obj = (JSONObject) object;
//								boolean exist = false;
//								// veifier l'existance de chaque client ( par son tidnid) dans la base local
//								for (Client cli : clients) {
//									String k = cli.getTidNid();
//									if (k.equals(obj.get("tidNid").toString())) {
//										System.out.println("cbintint:  id existant " + cli.getId() + " : TidNID "+ cli.getTidNid());
//										exist = true;
//									}
//								}
//								// client non existant dans la base de donnée local
//								// verif bkcli and amplitude
//								if ((!exist)) {
//									Client clt = new Client();
//									clt.setIdAppli(obj.get("idAppli").toString());
//									clt.setTidNid(obj.get("tidNid").toString());
//									try {
//										String in = obj.get("tidNid").toString();
//										String tid = in.substring(0, 3);
//										String nid = in.substring(3);
//										String cli = null;
//										String s = paramCnxService.testerCLIamplitude(in, conn2);
//
//										System.out.println(obj.get("tidNid").toString() + " test" + ": " + "s " + s);
//										if (!(s == null)) {//corriger idCli de la base local a partir d amplitude
//											clt.setIdCli(s);
//											System.out.println(obj.get("tidNid").toString()+ " exist deja dans amplitude " + ": " + "s " + s);
//											clientService.addClient(clt);
//											System.out.println("cbintint : nv client ajouter a la base de donnée existant dans amplitude "+ clt.getTidNid() + " : " + clt.getId());
//										} else {
//											if (fichierDuRep.contains("bkcli")) {
//												clientService.addClient(clt);
//												System.out.println("cbintint : nv client ajouter a la base de donnée a partir de bkcli"	+ clt.getTidNid() + " : " + clt.getId() + " : "	+ fichierDuRep);
//											}
//										}
//
//									} catch (NullPointerException ex) {
//										System.out
//												.println(obj.get("tidNid").toString() + " n exist pas dans amplitude ");
//
//									} finally {
//										// clientService.addClient(clt);
//										// System.out.println("cbintint : nv client ajouter" + clt.getTidNid() + ":" +
//										// clt.getId());
//									}
//								}
//							}
//							
//						} else {// juste pour bkcom lors de creation d'un compte pour un client existant
//
//							JSONArray comptesMap = listerCreerCompte(FILENAME);
//							for (Object object : comptesMap) {
//								// recuperer la liste des clients dans la base application local
//								clients = clientRepository.findAll();
//								JSONObject obj = (JSONObject) object;
//								boolean exist = false;
//								// veifier l'existance de chaque client ( par son tidnid) dans la base local
//								for (Client cli : clients) {
//									String k = cli.getTidNid();
//									if (k.equals(obj.get("tidNid").toString())) {
//										System.out.println("cbintint:  id existant " + cli.getId() + " : TidNID "+ cli.getTidNid());
//										exist = true;
//										// je modifie pour lui ajouter le compte
//										Client client = clientRepository.findByTidNid(obj.get("tidNid").toString());
//										client.setIdAppli(client.getIdAppli());
//										client.setIdCli(client.getIdCli());
//										client.setTidNid(client.getTidNid());
//										client.setCompteCli(obj.get("ncp").toString());// cas multi-compte ecrase l ancien 
//										clientRepository.save(client);
//
//									}
//								}
//								// client non existant dans la base de donnée local
//								// verif bkcli and amplitude
//								if ((!exist)) {
//									Client clt = new Client();
//									clt.setIdAppli(obj.get("idAppli").toString());
//									clt.setTidNid(obj.get("tidNid").toString());
//									clt.setCompteCli(obj.get("ncp").toString());
//									try {
//										String in = obj.get("tidNid").toString();
//										String s = paramCnxService.testerCLIamplitude(in, conn2);
//										System.out.println(obj.get("tidNid").toString() + " test" + ": " + "s " + s);
//										if (!(s == null)) {
//											clt.setIdCli(s);
//											System.out.println(obj.get("tidNid").toString()+ " exist deja dans amplitude " + ": " + "s " + s);
//											clientService.addClient(clt);
//											System.out.println("cbintint : nv client ajouter a la base de donnée existant dans amplitude "+ clt.getTidNid() + " : " + clt.getId());
//										} else {
//											if (fichierDuRep.contains("bkcom")) {
//												//clientService.addClient(clt);
//												System.out.println("cbintint : nv client ajouter a la base de donnée a partir de bkcom"+ clt.getTidNid() + " : " + clt.getId() + " : "+ clt.getCompteCli() + " : " + fichierDuRep);
//											}
//										}
//
//									} catch (NullPointerException ex) {
//										System.out.println(obj.get("tidNid").toString() + " n exist pas dans amplitude ");
//
//									} finally {
//										// clientService.addClient(clt);
//										// System.out.println("cbintint : nv client ajouter" + clt.getTidNid() + ":" +
//										// clt.getId());
//									}
//								}
//							}
//						}
//
//						// exclure les fichiers du reste de traitement si
//						// ( fichier n'est pas bkcli && client n'existe pas dans amplitude && client
//						// n'existe pas dans la base cad son bkcli n'a pas passé par traiement )
//						/************************************************************************verif ok creation des clients et comptes *************************************************************/
//
//						List<Client> dbClients = clientRepository.findAll();
//
//						JSONArray Map = Parser.parser(FILENAME, m, champs, CLI, dbClients, rep, repOut, repArchiv);
//
//						Fichier fichier = new Fichier();
//						// detail de fichier et blob
//						for (Object object : Map) {
//							JSONObject obj = (JSONObject) object;
//							if (obj.containsKey("fileName")) {
//								fichier.setFileName(obj.get("fileName").toString());
//								fichier.setApplicationSource(obj.get("applicationSource").toString());
//								fichier.setNomComplet(obj.get("nomComplet").toString()); 
//
//								Date date_trait = simpleFormat.parse(obj.get("date_trait").toString());
//								fichier.setDateTrait(date_trait);
//								/**********************************************************/
//								LocalDate localDate = LocalDate.now();
//								 Date dateRapportTrait = simpleFormat.parse(localDate.toString());
//								 fichier.setDateRapportTrait(dateRapportTrait);
//
//								/**********************************************************/
//								// save file .txt into database
//								File file = new File(rep + fichierDuRep);
//								byte[] bFile = new byte[(int) file.length()];
//								try {
//									FileInputStream fileInputStream = new FileInputStream(file);
//									// convert file into array of bytes
//									fileInputStream.read(bFile);
//									fileInputStream.close();
//
//
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//								fichier.setFileblob(bFile);
//								// save file .dat into database
//								File fileDat = new File(rep + (fichierDuRep.replace(".txt", ".dat")));
//								byte[] bFileDat = new byte[(int) fileDat.length()];
//								try {
//									FileInputStream fileInputStream = new FileInputStream(fileDat);
//									// convert file into array of bytes
//									fileInputStream.read(bFileDat);
//									fileInputStream.close();
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//								fichier.setDatblob(bFileDat);
//								fichier.setStatus("O");
//
//								/*****************************************************************/
//								/******************recup fich de la db si exist agir *************/
//					
//								/*****************************************************************/
//								if (fichierService.existsFichierBynomComplet(obj.get("nomComplet").toString())) {
//									//si fichier deja traite 
//									Long fichierId=fichierService.findBynomComplet(obj.get("nomComplet").toString()).get(0).getId();
//									fichierService.updateFichier(fichierId, fichier);
//								}else {
//									fichierService.addFichier(fichier);
//
//								}
//
//								finalList.add(fichier);
//							}
//						}
//						myList.add(Map);
//						GestionFichier.deplacerFichier(FILENAME, repArchiv);
//						GestionFichier.deplacerFichier((FILENAME.replace(".txt", ".dat")), repArchiv);
//					} else {
//						System.out.println("null detecter sans matrice " + fichierDuRep);
//						// GestionFichier.CopierSupprimer(fichierDuRep);
//					}
//				}
//			}
//			/*********************************************************************************************/
//			// fermer cnx amplitude
//
//		} catch (ClassNotFoundException ex) {
//			ex.printStackTrace();
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		} finally {
//			try {
//
//				if (conn2 != null && !conn2.isClosed()) {
//					conn2.close();
//				}
//
//			} catch (SQLException ex) {
//				ex.printStackTrace();
//			}
//		}
//
//		/*********************************************************************************************/
//
//		return finalList;
//	}
//}
