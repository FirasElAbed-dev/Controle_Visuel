package com.grokonez.jwtauthentication.security.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grokonez.jwtauthentication.model.ParamCnxDB;
import com.grokonez.jwtauthentication.repository.IParamCnxRepository;

@Service
@Transactional
public class WebTelegramService {
	@Autowired
	private ParamCnxService paramCnxService;
	@Autowired
	private IParamCnxRepository paramCnxRepository;
	String dbURL2 = null;
	String username = null;
	String password = null;

	static String user_cnx="monitoring";
	static String pswd_cnx="monetctl04";
	static String host_cnx="jdbc:oracle:thin:@172.28.2.235:1521:attibank";
	
	
	public static String correctionUTF8(String s) {
		
		
		String ligne=s;
		
        HashMap<String,String> obj = new HashMap<String,String>();
		obj.put("°"  ,     " " );

       


		obj.put(","  ,     "," );
		obj.put("."  ,     "." );
//		obj.put('"'  ,     '"' );
		obj.put("/"  ,     "/" );
		obj.put("-"  ,     "-" );
		obj.put("'"  ,     "'" );
		obj.put("ç"  ,     "c" );
		obj.put("é"  ,     "e" );
		obj.put("è"  ,     "e" );
		obj.put("ê"  ,     "e" );
		obj.put("ë"  ,     "e" );
		obj.put("È"  ,     "E" );
		obj.put("Ë"  ,     "E" );
		obj.put("Ê"  ,     "E" );
		obj.put("à"  ,     "a" );
		obj.put("â"  ,     "a" );
		obj.put("ä"  ,     "a" );
		obj.put("ã"  ,     "a" );
		obj.put("ã"  ,     "a" );
		obj.put("å"  ,     "a" );
		obj.put("À"  ,     "A" );
		obj.put("Â"  ,     "A" );
		obj.put("Ä"  ,     "A" );
		obj.put("Ã"  ,     "A" );
		obj.put("Å"  ,     "A" );
		obj.put("ù"  ,     "u" );
		obj.put("ü"  ,     "u" );
		obj.put("û"  ,     "u" );
		obj.put("Ù"  ,     "U" );
		obj.put("Ü"  ,     "U" );
		obj.put("Û"  ,     "U" );
		obj.put("ô"  ,     "o" );
		obj.put("ö"  ,     "o" );
		obj.put("õ"  ,     "o" );
		obj.put("ò"  ,     "o" );
		obj.put("Ô"  ,     "O" );
		obj.put("Ö"  ,     "O" );
		obj.put("Ò"  ,     "O" );
		obj.put("Õ"  ,     "O" );
		obj.put("Ó"  ,     "O" );
		obj.put("ì"  ,     "i" );
		obj.put("ï"  ,     "i" );
		obj.put("î"  ,     "i" );
		obj.put("í"  ,     "i" );
		obj.put("Î"  ,     "I" );
		obj.put("Ì"  ,     "I" );
		obj.put("Ï"  ,     "I" );
		obj.put("Í"  ,     "I" );
		obj.put("ñ"  ,     "n" );
		obj.put("Ñ"  ,     "N" );
		obj.put("%"  ,     " " );
		obj.put("@"  ,     " " );
		obj.put("="  ,     " " );
		obj.put(">"  ,     " " );
		obj.put("<"  ,     " " );
		obj.put("#"  ,     " " );
		obj.put("\\("  ,     " " );
		obj.put("\\)"  ,     " " );
		obj.put("\\+"  ,     " " );
		obj.put("\\*"  ,     " " );
		obj.put("§"  ,     " " );
		obj.put("["  ,     " " );
		obj.put("]"  ,     " " );
		obj.put("{"  ,     " " );
		obj.put("}"  ,     " " );
		obj.put(";"  ,     " " );
		obj.put("\\?"  ,     " " );
		obj.put("!"  ,     " " );
		obj.put("''" ,     " " );
		obj.put("!"  ,     " " );
		obj.put("&"  ,     " " );
		obj.put("^"  ,     " " );
		obj.put(":"  ,     " " );
		obj.put("°"  ,     " " );
		obj.put("~"  ,     " " );
		obj.put("_"  ,     " " );
		obj.put("|"  ,     " " );
		//obj.put("?"  ,     " " );
		obj.put("$"  ,     " " );
		obj.put("£"  ,     " " );
		obj.put("`"  ,     " " );
		obj.put("\\" ,     " " );
		//obj.put("\\?"  ,     " " );
		obj.put("Þ"  ,     " " );
		obj.put("þ"  ,     " " );
		//obj.put("\\?"  ,     " " );
		obj.put("º"  ,     " " );
		
		for (Map.Entry mapentry : obj.entrySet()) {            
            if (ligne.contains(mapentry.getKey().toString())) {
            	System.out.println("je remplace tout:"+mapentry.getKey().toString()+":par un :" +mapentry.getValue().toString());
            	 String x=mapentry.getKey().toString();
                 String y=mapentry.getValue().toString();
            	ligne=ligne.replaceAll(x, y);
        		System.out.println("apres : "+x+" : "+y+" : "+ligne);

			}
         }
		return ligne;

		}
	public String getDate() {
		boolean access = false;
		Connection conn2 = null;
		String MNT2 = null;
		try {
			List<ParamCnxDB> params = paramCnxRepository.findAll();
			if (!params.isEmpty()) {
				ParamCnxDB p = params.get(0);

				dbURL2 = "jdbc:oracle:thin:@" + p.getUrl() + ":" + p.getPort() + p.getSid();
				username = p.getUser();
				password = p.getPswd();
			}
			Class.forName("oracle.jdbc.OracleDriver");
			TimeZone timeZone = TimeZone.getTimeZone(" Africa/Tunis");
			TimeZone.setDefault(timeZone);
			conn2 = DriverManager.getConnection(host_cnx, user_cnx, pswd_cnx);
			if (conn2 != null) {
				System.out.println("Connected to amplitude db");
				access = true;
				java.sql.Statement stmtClient = conn2.createStatement();
				ResultSet rsClient = stmtClient
						.executeQuery("select MNT2 from bknom where ctab=\'001\' and cacc=\'99999\'");
				while (rsClient.next()) {
					MNT2 = rsClient.getString("MNT2").replaceAll(" ", "");
					if (MNT2.length() < 8) {
						MNT2 = "0" + MNT2;
					}
				}
			}

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn2 != null && !conn2.isClosed()) {
					conn2.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return MNT2;
	}

	public String appendWebTelegram() throws IOException {
	int nmbrFich = 0;
	String result;
	int nmbrLine=0;
	int nombreParFich=0;
		// parcourir repertoire

		String repIn = "//172.28.14.99/Shared_doc/rep_travail_app/dep-webabt/TestV11/In/";
		//String repOut = repIn + "out/";
		String repOut = "//172.28.14.99/Shared_doc/rep_travail_app/dep-webabt/TestV11/Out/";

		File directory = new File(repOut);
		if (!directory.exists()) {
			directory.mkdir();
			// If you require it to make the entire directory path including parents,
			// use directory.mkdirs(); here instead.
		}
		String dbDate = getDate();// 11112020
		if (dbDate.length() < 8) {// 1122020->01122020
			dbDate = "0" + dbDate;
		}
		
		System.out.println("date " + dbDate.subSequence(0, 4) + dbDate.subSequence(6, 8));
		//dbDate="01122020";
		String extensionIn = ".PRV";
		String extensionOut = ".env";
		String dateJJMMAA = "" + dbDate.subSequence(0, 4) + dbDate.subSequence(6, 8);
		//dateJJMMAA="011220";
		String dateJJ = dbDate.subSequence(0, 2).toString();// 111120->01
		String dateMM = dbDate.subSequence(2, 4).toString();// 111120->12
		String dateAAAA = dbDate.subSequence(4, 8).toString();// 111120->20
		String dateAAAAMMJJ = dateAAAA + dateMM + dateJJ;

		// WEBPOST*301120.PRV

		List<String> FichiersDuJour = new ArrayList<String>();
		File dir = new File(repIn);
		File[] directoryListing = dir.listFiles();
		BufferedReader bufferedreader = null;
		BufferedReader bufRed = null;
		FileReader filereader = null;
		
		// lister les fichiers
		if (directoryListing != null) {
			for (File child : directoryListing) {
				//if (child.getName().startsWith("WEBPOST" + "00") && child.getName().endsWith(dateJJMMAA + ".PRV")) {
				
				if (child.getName().startsWith("WEBPOST") && child.getName().endsWith(dateJJMMAA + ".PRV")) {

					FichiersDuJour.add(child.getName());
				}

			}

		}
		

		for (String file : FichiersDuJour) {
			nombreParFich=0;
			try {
				nmbrFich=nmbrFich+1;
				
				bufRed = new BufferedReader(new FileReader(repIn + file));
				String firstLine = bufRed.readLine();
				int firstlength = firstLine.length();

				System.out.println("file " + file + " : " + firstlength);
				// fichier en lecture
				filereader = new FileReader(repIn + file);
				bufferedreader = new BufferedReader(filereader);
				// fichier en ecriture
				//String destFile = repOut + "bs_wt" + dateAAAAMMJJ + extensionOut;
				String destFile = repOut + "bs_wt" + dateAAAAMMJJ + ".txt";

				File dest = new File(destFile);

				FileWriter fw = new FileWriter(dest, true);
				String strCurrentLine;

				while ((strCurrentLine = bufferedreader.readLine()) != null) {
					
					System.out.println("line" + strCurrentLine + " : " + strCurrentLine.length());
					if (strCurrentLine.length() > firstlength) {
						nombreParFich=nombreParFich+1;
						nmbrLine=nmbrLine+1;
						
						
						
						
						fw.write(strCurrentLine.substring(160));
						System.out.println("...");
						fw.write("\n");
						System.out.println("..");
						fw.flush();
						System.out.println(".");

					}

				}
				fw.close();
			} catch (IOException e) {

				e.printStackTrace();

			} finally {
System.out.println("i m here ");
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
System.out.println("fichier :"+file+"   nbr ligne "+nombreParFich );
		}
		
		
			String destFileResult = repOut + "Resultat"+"bs_wt" + dateAAAAMMJJ + extensionOut;
			
			
			File destResult = new File(destFileResult);
			FileWriter fwResult = new FileWriter(destResult);
			
//			FileOutputStream fileStream = new FileOutputStream(new File(destFileResult));
//			OutputStreamWriter writer = new OutputStreamWriter(fileStream, "ANSI");
			result=" fichiers :"+nmbrFich+" , lignes: "+nmbrLine;
			fwResult.write(result);
			fwResult.write("\n");
			fwResult.flush();
			fwResult.close();
//			
//			writer.write(result);
//			writer.write("\n");
//			writer.flush();
//			writer.close();
		
		
		return result;

	}
	

}
