package com.revature.beans;

import javax.persistence.*;

@Entity
@Table(name="PP_PROPERTIES")
public class ApplicationProperties {
	
		@Id
		@Column(name="COMPANY")
	private String company;
		@Column(name="APP")
	private String app;
		@Column(name="S3")
	private String s3;
		@Column(name="SERVER")
	private String server;
		@Column(name="JENKINS")
	private String jenkins;
		@Column(name="SONARQUBE")
	private String sonarqube;
		@Column(name="K")
	private String k;
		@Column(name="V")
	private String v;
	
	/**
	 *  Constructors
	 */
	public ApplicationProperties() {
		super();
	}
	public ApplicationProperties(String company, String app, String s3, String server, String jenkins, String sonarqube,
			String k, String v) {
		super();
		this.company = company;
		this.app = app;
		this.s3 = s3;
		this.server = server;
		this.jenkins = jenkins;
		this.sonarqube = sonarqube;
		this.k = k;
		this.v = v;
	}
	
	/**
	 * 	Getters & Setters
	 */
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getS3() {
		return s3;
	}
	public void setS3(String s3) {
		this.s3 = s3;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getJenkins() {
		return jenkins;
	}
	public void setJenkins(String jenkins) {
		this.jenkins = jenkins;
	}
	public String getSonarqube() {
		return sonarqube;
	}
	public void setSonarqube(String sonarqube) {
		this.sonarqube = sonarqube;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	
	

}
