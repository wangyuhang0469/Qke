package com.example.wang.qke.classes;

public class News {
     private String subject;
     private String summary;
     private String cover;
     private String changed;
	public News() {
		super();
		// TODO Auto-generated constructor stub
	}
	public News(String subject, String summary, String cover, String changed) {
		super();
		this.subject = subject;
		this.summary = summary;
		this.cover = cover;
		this.changed = changed;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getChanged() {
		return changed;
	}
	public void setChanged(String changed) {
		this.changed = changed;
	}
	@Override
	public String toString() {
		return "News [subject=" + subject + ", summary=" + summary + ", cover="
				+ cover + ", changed=" + changed + "]";
	}
     
}
