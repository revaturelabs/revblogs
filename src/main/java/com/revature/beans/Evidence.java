package com.revature.beans;

import javax.persistence.*;

@Entity
@Table(name="PP_EVIDENCE")
public class Evidence {
	
	//----------------------------------
	// Attributes
	@Id
	@Column(name="EVIDENCE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="evidenceSequence")
	@SequenceGenerator(name="evidenceSequence",sequenceName="EVIDENCE_SEQUENCE",initialValue=1,allocationSize=1)
	private int evidenceId;
		
	@Column(name="EVIDENCE_URL", unique=true)
	private String evidenceURL;
	
	/**
	 * 	Constructors
	 */
	public Evidence() {
		super();
	}
	public Evidence( String evidenceURL) {
		super();
		this.evidenceURL = evidenceURL;
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
	public String getEvidenceURL() {
		return evidenceURL;
	}
	public void setEvidenceURL(String evidenceURL) {
		this.evidenceURL = evidenceURL;
	}
	
	

}
