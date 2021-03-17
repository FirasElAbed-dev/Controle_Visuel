package com.grokonez.jwtauthentication.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Table(name = "CHEQUE_TBL")
@Entity

public class Cheque implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String numChq;
	private Date dateTrait;
	private String ribTireur;
	private String ribBenif;
	private String ncpTireur;
	private String codeBanqBenif;
	@Column(name = "recto", columnDefinition = "LONGBLOB")
	@Lob
	private byte[] recto;
	@Column(name = "verso", columnDefinition = "LONGBLOB")
	@Lob
	private byte[] verso;
	@Column(name = "signature", columnDefinition = "LONGBLOB")
	@Lob
	private byte[] signature;
	
	private long montant;
	private boolean flagTrait;
	private String motif1;
	private String motif2;
	private String motif3;
	private String motif4;
	private boolean flagReservation;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumChq() {
		return numChq;
	}
	public void setNumChq(String numChq) {
		this.numChq = numChq;
	}
	public Date getDateTrait() {
		return dateTrait;
	}
	public void setDateTrait(Date dateTrait) {
		this.dateTrait = dateTrait;
	}
	public String getRibTireur() {
		return ribTireur;
	}
	public void setRibTireur(String ribTireur) {
		this.ribTireur = ribTireur;
	}
	public String getRibBenif() {
		return ribBenif;
	}
	public void setRibBenif(String ribBenif) {
		this.ribBenif = ribBenif;
	}
	public String getCodeBanqBenif() {
		return codeBanqBenif;
	}
	public void setCodeBanqBenif(String codeBanqBenif) {
		this.codeBanqBenif = codeBanqBenif;
	}
	public byte[] getRecto() {
		return recto;
	}
	public void setRecto(byte[] recto) {
		this.recto = recto;
	}
	public byte[] getVerso() {
		return verso;
	}
	public void setVerso(byte[] verso) {
		this.verso = verso;
	}
	public byte[] getSignature() {
		return signature;
	}
	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
	public long getMontant() {
		return montant;
	}
	public void setMontant(long montant) {
		this.montant = montant;
	}
	public boolean isFlagTrait() {
		return flagTrait;
	}
	public void setFlagTrait(boolean flagTrait) {
		this.flagTrait = flagTrait;
	}
	public String getMotif1() {
		return motif1;
	}
	public void setMotif1(String motif1) {
		this.motif1 = motif1;
	}
	public String getMotif2() {
		return motif2;
	}
	public void setMotif2(String motif2) {
		this.motif2 = motif2;
	}
	public String getMotif3() {
		return motif3;
	}
	public void setMotif3(String motif3) {
		this.motif3 = motif3;
	}
	public String getMotif4() {
		return motif4;
	}
	public void setMotif4(String motif4) {
		this.motif4 = motif4;
	}
	public boolean isFlagReservation() {
		return flagReservation;
	}
	public void setFlagReservation(boolean flagReservation) {
		this.flagReservation = flagReservation;
	}
	
	
	public String getNcpTireur() {
		return ncpTireur;
	}
	public void setNcpTireur(String ncpTireur) {
		this.ncpTireur = ncpTireur;
	}
	@Override
	public String toString() {
		return "Cheque [id=" + id + ", numChq=" + numChq + ", dateTrait=" + dateTrait + ", ribTireur=" + ribTireur
				+ ", ribBenif=" + ribBenif + ", ncpTireur=" + ncpTireur + ", codeBanqBenif=" + codeBanqBenif
				+ ", recto=" + Arrays.toString(recto) + ", verso=" + Arrays.toString(verso) + ", signature="
				+ Arrays.toString(signature) + ", montant=" + montant + ", flagTrait=" + flagTrait + ", motif1="
				+ motif1 + ", motif2=" + motif2 + ", motif3=" + motif3 + ", motif4=" + motif4 + ", flagReservation="
				+ flagReservation + "]";
	}
	



	

	
}
