package com.revature.beans;

import javax.persistence.*;

@Entity
@Table(name="REV_BLOG_EVIDENCE")
public class Evidence {
	
		@Id
		@Column(name="REV_BLOG_EVIDENCE_ID")
	private int evidenceId;
		@Column(name="REV_BLOG_EVIDENCE_ADDRESS", unique=true, nullable=false)
	private String evidenceAddress;
	
	/**
	 * 	Constructors
	 */
	public Evidence() {
		super();
	}
	public Evidence(int evidenceId, String evidenceAddress) {
		super();
		this.evidenceId = evidenceId;
		this.evidenceAddress = evidenceAddress;
	}
	
	/**
	 * 	Getters & Setters
	 */
	public int getEvidenceId() {
		return evidenceId;
	}
	public void setEvidenceId(int evidenceId) {
		this.evidenceId = evidenceId;
	}
	public String getEvidenceAddress() {
		return evidenceAddress;
	}
	public void setEvidenceAddress(String evidenceAddress) {
		this.evidenceAddress = evidenceAddress;
	}
	
	

}
