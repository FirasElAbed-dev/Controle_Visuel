package com.grokonez.jwtauthentication.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Table(name = "REPERTOIR_TBL")
@Entity


public class Repertoire  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String repIn;
	private String repOut;
	private String repArchiv;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRepIn() {
		return repIn;
	}
	public void setRepIn(String repIn) {
		this.repIn = repIn;
	}
	public String getRepOut() {
		return repOut;
	}
	public void setRepOut(String repOut) {
		this.repOut = repOut;
	}
	public String getRepArchiv() {
		return repArchiv;
	}
	public void setRepArchiv(String repArchiv) {
		this.repArchiv = repArchiv;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Repertoire() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Repertoire(Long id, String repIn, String repOut, String repArchiv) {
		super();
		this.id = id;
		this.repIn = repIn;
		this.repOut = repOut;
		this.repArchiv = repArchiv;
	}
	@Override
	public String toString() {
		return "Repertoire [id=" + id + ", repIn=" + repIn + ", repOut=" + repOut + ", repArchiv=" + repArchiv + "]";
	}

	
	
	
}
