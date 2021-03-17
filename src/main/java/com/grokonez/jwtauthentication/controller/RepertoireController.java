//package com.grokonez.jwtauthentication.controller;
//
//import java.io.File;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Optional;
//
//import javax.annotation.security.PermitAll;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.attijari.demo.metier.Parser;
//
//import com.attijari.demo.exception.ResourceNotFoundException;
//import com.grokonez.jwtauthentication.model.Fichier;
//import com.grokonez.jwtauthentication.model.ParamCnxDB;
//import com.grokonez.jwtauthentication.model.Repertoire;
//import com.grokonez.jwtauthentication.repository.IClientRepository;
//import com.grokonez.jwtauthentication.repository.IParamCnxRepository;
//import com.grokonez.jwtauthentication.repository.IRepertoireRepository;
//import com.grokonez.jwtauthentication.security.services.ChampService;
//import com.grokonez.jwtauthentication.security.services.ClientService;
//import com.grokonez.jwtauthentication.security.services.FichierService;
//import com.grokonez.jwtauthentication.security.services.LineService;
//import com.grokonez.jwtauthentication.security.services.MatriceService;
//import com.grokonez.jwtauthentication.security.services.ParamCnxService;
////import com.attijari.demo.metier.DemoData;
//import com.grokonez.jwtauthentication.security.services.RepertoireService;
//
//@RestController
//
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//public class RepertoireController {
//	@Autowired
//	private IRepertoireRepository repertoireRepository;
//	@Autowired
//	private RepertoireService repertoireService;
//
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
//	private void fetchChild(File file) {
//		System.out.println(file.getAbsolutePath());
//
//		if (file.isDirectory()) {
//
//			File[] children = file.listFiles();
//
//			for (File child : children) {
//				// Récursive (Recursive)
//				this.fetchChild(child);
//			}
//		}
//
//	}
//
//	@GetMapping("/repertoires/repertoires")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public List<Repertoire> getAllDirectories() {
//		return repertoireRepository.findAll();
//	}
//
//	@GetMapping("/repertoires/exist")
//	@ResponseBody
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public Boolean checkDir(@RequestParam String path) {
//		Boolean existDir = false;
//		File f1 = new File(path);
//		if (f1.exists()) {
//			existDir = true;
//		}
//
//		return existDir;
//
//	}
//
//	@PutMapping("/repertoires/{repertoireId}")
//	@PreAuthorize("hasRole('ADMIN')")
//	public Repertoire updateRepertoire(@PathVariable Long repertoireId, @RequestBody Repertoire repertoireRequest) {
//		return repertoireRepository.findById(repertoireId).map(repertoire -> {
//			repertoire.setRepIn(repertoireRequest.getRepIn());
//			repertoire.setRepOut(repertoireRequest.getRepOut());
//			repertoire.setRepArchiv(repertoireRequest.getRepArchiv());
//
//			return repertoireRepository.save(repertoire);
//		}).orElseThrow(() -> new ResourceNotFoundException("RepertoireId " + repertoireId + " not found"));
////
//	}
//
//	@PutMapping("/repertoires/parser")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public List<Fichier> gererFichier() throws ParseException {
//
//		List<JSONObject> files = new ArrayList<JSONObject>();
//
//		Repertoire r = null;
//		// recuperer repertoire de la base de donnée
//		List<Repertoire> repertoires = repertoireRepository.findAll();
//
//		if (repertoires != null) {
//			r = repertoires.get(0);
//			// System.out.println("r : " + r.toString());
//
//		}
//
//		// lister les fichiers de ce repertoire par date et nom
//		List<String> listeFichier = repertoireService.listerFichierRepPARSING(r.getRepIn());
//		
//		
//		
//		
//		List<String> listeFichierNotWebank= new ArrayList<String>();
//		for (String fich : listeFichier) {
//			//bkemacli_01999_20201019.txt
//			String[] separated = fich.split("\\_");
//			 if (!separated[1].endsWith("999")) {
//				 listeFichierNotWebank.add(fich);
//			 }
//			
//		}
//		
//		
//		for (String string : listeFichierNotWebank) {
//			JSONObject file = new JSONObject();
//			file.put("path", string);
//			files.add(file);
//		}
//
//		return repertoireService.gererFichiers(files);
//	}
//	@PutMapping("/repertoires/parserWebank")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public List<Fichier> gererFichierWebank() throws ParseException {
//
//		List<JSONObject> files = new ArrayList<JSONObject>();
//
//		Repertoire r = null;
//		// recuperer repertoire de la base de donnée
//		List<Repertoire> repertoires = repertoireRepository.findAll();
//
//		if (repertoires != null) {
//			r = repertoires.get(0);
//			// System.out.println("r : " + r.toString());
//
//		}
//
//		// lister les fichiers de ce repertoire par date et nom
//		List<String> listeFichier = repertoireService.listerFichierRepPARSING(r.getRepIn());
//		List<String> listeFichierWebank= new ArrayList<String>();
//		for (String fich : listeFichier) {
//			//bkemacli_01999_20201019.txt
//			String[] separated = fich.split("\\_");
//			 if (separated[1].endsWith("999")) {
//				 listeFichierWebank.add(fich);
//			 }
//			
//		}
//		for (String string : listeFichierWebank) {
//			JSONObject file = new JSONObject();
//			file.put("path", string);
//			files.add(file);
//		}
//
//		return repertoireService.gererFichiers(files);
//	}
//
//	@GetMapping("/repertoires/getFileList")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public List<JSONObject> getFileListfromDirect() {
//		List<JSONObject> files = new ArrayList<JSONObject>();
//
//		Repertoire r = null;
//		// recuperer repertoire de la base de donnée
//		List<Repertoire> repertoires = repertoireRepository.findAll();
//
//		if (repertoires != null) {
//			r = repertoires.get(0);
//			// System.out.println("r : " + r.toString());
//
//		}
//
//		// lister les fichiers de ce repertoire par date et nom
//		List<String> listeFichier = Parser.listerFichierRep(r.getRepIn());
//		for (String string : listeFichier) {
//			JSONObject file = new JSONObject();
//			file.put("path", string);
//			files.add(file);
//		}
//		return files;
//	}
//
//	@GetMapping("/repertoires/getOutputFiles")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public List<JSONObject> getOutputFiles() {
//		List<JSONObject> files = new ArrayList<JSONObject>();
//
//		Repertoire r = null;
//		// recuperer repertoire de la base de donnée
//		List<Repertoire> repertoires = repertoireRepository.findAll();
//
//		if (repertoires != null) {
//			r = repertoires.get(0);
//			// System.out.println("r : " + r.toString());
//
//		}
//
//		// lister les fichiers de ce repertoire par date et nom
//		List<String> listeFichier = Parser.listerFichierRepOut(r.getRepOut());
//		for (String string : listeFichier) {
//			JSONObject file = new JSONObject();
//			file.put("path", string);
//			files.add(file);
//		}
//		return files;
//	}
//
//	@GetMapping("/repertoires/getRejectedFileList")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public JSONArray validerCltsFichRep() {
//		List<JSONObject> files = new ArrayList<JSONObject>();
//
//		Repertoire r = null;
//		// recuperer repertoire de la base de donnée
//		List<Repertoire> repertoires = repertoireRepository.findAll();
//
//		if (repertoires != null) {
//			r = repertoires.get(0);
//			// System.out.println("r : " + r.toString());
//
//		}
//
//		// lister les fichiers de ce repertoire par date et nom
//		List<String> listeFichier = Parser.listerFichierRep(r.getRepIn());
//		JSONArray rapportRepertoire = new JSONArray();
//
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
//			for (String string : listeFichier) {
//				JSONArray rapportFichier = new JSONArray();
//				JSONObject rapportLigne = new JSONObject();
//
//				boolean statusFichier = true;
//				int nmbrErr = 0;
//				StringBuilder erreurList = new StringBuilder();
//				StringBuilder cheminFichier = new StringBuilder();
//				cheminFichier.append(r.getRepIn());
//				cheminFichier.append(string);
//				String FILENAME = cheminFichier.toString();
//				// clients fichier en cour
//				JSONArray clientsMap = RepertoireService.listerCreerClient(FILENAME);
//				JSONArray comptesMap = RepertoireService.listerCreerCompte(FILENAME);
//				//System.out.println("test boucle pour bkacp" + ":" + comptesMap.toJSONString());
//
//				if (!string.contains("bkcli")) {
//
//					if (string.contains("bkicom") || string.contains("bkacp") || string.contains("bkemacom")
//							|| string.contains("bktelcom")) {
//						// verif exitence compte
//
//						for (Object object : comptesMap) {
//							JSONObject obj = (JSONObject) object;
//							boolean exist = false;
//							obj.get("ncp").toString();
//							// verif exitence compte dans amplitude
//							String compteAmpl = paramCnxService.testerCOMPTEamplitude(obj.get("ncp").toString(), conn2);
//							// verif existence compte dans db
//							boolean existCompte = clientRepository.existsClientByCompteCli(obj.get("ncp").toString());
//							//System.out.println("boolean test " + existCompte + "exist dans amplitude :" + compteAmpl + ":");
//
//							// String
//							// compteDb=clientRepository.findByTidNid(obj.get("ncp").toString()).getCompteCli();
//
//							// test des comptes
//							if ((compteAmpl == null || compteAmpl.isEmpty()) && (existCompte == false)) {
//								rapportLigne.put("nomfichier", string);
//
//								statusFichier = false;
//								erreurList.append(" : ");
//								erreurList.append(obj.get("ncp").toString());
//								erreurList.append(" : ");
//								nmbrErr++;
//								//System.out.println("test erreur list" + erreurList + ":" + nmbrErr);
//
//								rapportLigne.put("msg", "erreur compte inexistant" + erreurList);
//								rapportLigne.put("nbrErr", nmbrErr);
//
//							}
//							/*
//							 * if (statusFichier==false) { rapportLigne.put("msg",
//							 * "erreur compte inexistant"+erreurList); rapportLigne.put("nbrErr", nmbrErr);
//							 * 
//							 * }
//							 */
//							// rapportFichier.add(rapportLigne);
//
//						}
//
//					} else {
//						// verif existence client
//						for (Object object : clientsMap) {
//							// JSONObject rapportLigne = new JSONObject();
//
//							JSONObject obj = (JSONObject) object;
//							boolean exist = false;
//							obj.get("tidNid").toString();
//							// verif exitence client dans amplitude
//							String cltAmpl = paramCnxService.testerCLIamplitude(obj.get("tidNid").toString(), conn2);
//							// verif existence compte dans db
//							boolean existClient = clientRepository.existsClientByTidNid(obj.get("tidNid").toString());
//							//System.out.println("boolean test " + existClient + "exist dans amplitude :" + cltAmpl + ":");
//							// Long
//							// cltDb=clientRepository.findByTidNid(obj.get("tidNid").toString()).getId();
//
//							// test des clients
//							if ((cltAmpl == null || cltAmpl.isEmpty()) && (existClient == false)) {
//								rapportLigne.put("nomfichier", string);
//
//								statusFichier = false;
//								erreurList.append(" : ");
//								erreurList.append(obj.get("tidNid").toString());
//								erreurList.append(" : ");
//								nmbrErr++;
//								//System.out.println("test erreur list" + erreurList + ":" + nmbrErr);
//								rapportLigne.put("msg", "erreur client inexistant" + erreurList);
//								rapportLigne.put("nbrErr", nmbrErr);
//							}
//							
//						}
//						/*
//						 * if (statusFichier==false) { rapportLigne.put("msg",
//						 * "erreur client inexistant"+erreurList); rapportLigne.put("nbrErr", nmbrErr);
//						 * 
//						 * }
//						 */
//					}
//
//				} else {// fichier bkcli
//						// test si client exist dans amplitude
//						// sinon ajouter un client
//				}
//				if (!rapportLigne.isEmpty()) {
//					rapportFichier.add(rapportLigne);
//
//				}
//				if (!rapportFichier.isEmpty()) {
//					rapportRepertoire.add(rapportFichier);
//
//				}
//			}
//
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
//		return rapportRepertoire;
//
//	}
//
//	@GetMapping("/repertoires/getRejectedFile")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public JSONObject validerCltFichier() {
//
//		return repertoireService.validerCltsComptParFich("bkacp_00843_20200724.txt");
//		//String path ="D:/rep/repArch/bkacp_00843_20200928.txt";
//		//return repertoireService.testerFichVide(path) ;
//
//	}
//
//}
