package com.grokonez.jwtauthentication.security.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grokonez.jwtauthentication.model.ParamCnxDB;
import com.grokonez.jwtauthentication.repository.IParamCnxRepository;

@Service

@Transactional
public class ParamCnxService {
	@Autowired
	private IParamCnxRepository paramCnxRepository;

	public ParamCnxService() {
		super();
	}

	public List<ParamCnxDB> getAllparamCnxDB() {
		return paramCnxRepository.findAll();
	}

	public static boolean checkDBcnx(String url, int port, String sid, String user, String pswd) {
		boolean access = false;
		Connection conn2 = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String dbURL2 = "jdbc:oracle:thin:@" + url + ":" + port + sid;
			String username = user;
			String password = pswd;

			conn2 = DriverManager.getConnection(dbURL2, username, password);
			if (conn2 != null) {
				System.out.println("Connected to amplitude db");
				access = true;
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
		return access;
	}

	public String testerCLIamplitude(String tidnid, Connection c) throws SQLException {

		String tid;
		String nid;
		String cli = null;

		tid = tidnid.substring(0, 3);
		nid = tidnid.substring(3);

		Connection conn2 = c;
		java.sql.Statement stmtClient = conn2.createStatement();
		ResultSet rsClient = stmtClient
				.executeQuery("select CLI,TID,NID from bkcli where NID=\'" + nid + "\' and " + "TID=\'" + tid + "\'");

		if (conn2 != null) {
			while (rsClient.next()) {
				cli = rsClient.getString("CLI").replaceAll(" ", "");

			}
		}
		stmtClient.close();
		return cli;

		/*
		 * List<String> cliList = null; String tid; String nid; String cli = null;
		 * 
		 * for (String tidnid : tidnidList) { tid=tidnid.substring(0,3);
		 * nid=tidnid.substring(3);
		 * 
		 * Connection conn2=c; java.sql.Statement stmtClient=conn2.createStatement();
		 * ResultSet rsClient=
		 * stmtClient.executeQuery("select CLI,TID,NID from bkcli where NID=\'"
		 * +nid+"\' and "+"TID=\'"+tid+"\'" );
		 * 
		 * if (conn2 != null) { while (rsClient.next()){
		 * cli=rsClient.getString("CLI").replaceAll(" ", ""); cliList.add(cli);
		 * 
		 * } } } return cliList;
		 */
	}

	public String testerCOMPTEamplitude(String ncp, Connection c) throws SQLException {
		String nCompte = ncp;

		Connection conn2 = c;
		java.sql.Statement stmtCompte = conn2.createStatement();
		ResultSet rsCompte = stmtCompte
				.executeQuery("select NCP from bkcom where NCP=\'" + ncp + "\'");

		if (conn2 != null) {
			while (rsCompte.next()) {
				ncp = rsCompte.getString("NCP").replaceAll(" ", "");

			}
		}
		stmtCompte.close();
		return ncp;

	}

	public String OLDtesterCOMPTEamplitude(String ncp) {
		System.out.println("tester oracle db ");
		Connection conn2 = null;
		String dbURL2 = null;
		String username = null;
		String password = null;
		// String tid=tidnid.substring(0,3);
		// String nid=tidnid.substring(3);
		String nCompte = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			List<ParamCnxDB> params = getAllparamCnxDB();
			if (!params.isEmpty()) {
				ParamCnxDB p = params.get(0);

				dbURL2 = "jdbc:oracle:thin:@" + p.getUrl() + ":" + p.getPort() +  p.getSid();
				username = p.getUser();
				password = p.getPswd();
			}
			// METHOD #2
			// URL PORT SID username password
			conn2 = DriverManager.getConnection(dbURL2, username, password);
			java.sql.Statement stmt = conn2.createStatement();
			ResultSet rs = stmt.executeQuery("select NCP from bkcom where NCP=\'" + ncp + "\'");
			// 00011323 :CIN01018109
			if (conn2 != null) {
				System.out.println("Connected with connection #2");
				while (rs.next()) {
					System.out.println(
							"cbintint sql compte recherche  ncp : " + ncp + rs.getString(1).replaceAll(" ", ""));
					nCompte = rs.getString(1).replaceAll(" ", "");
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

		return nCompte;
	}

}
