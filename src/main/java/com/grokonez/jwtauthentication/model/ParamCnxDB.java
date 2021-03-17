package com.grokonez.jwtauthentication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "paramCnxDB_TBL")
@Entity

public class ParamCnxDB {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String url;
	private int port;
	private String sid;
	private String user;
	private String pswd;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ParamCnxDB() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ParamCnxDB(Long id, String url, int port, String sid, String user, String pswd) {
		super();
		this.id = id;
		this.url = url;
		this.port = port;
		this.sid = sid;
		this.user = user;
		this.pswd = pswd;
	}
	@Override
	public String toString() {
		return "ParamCnxDB [id=" + id + ", url=" + url + ", port=" + port + ", sid=" + sid + ", user=" + user
				+ ", pswd=" + pswd + "]";
	}

}
