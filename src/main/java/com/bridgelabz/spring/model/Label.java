package com.bridgelabz.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "Label")
public class Label implements Serializable {
	
	@GeneratedValue
	@Id
	@Column(name="id")
	private int labelId;
	
	public int getLabelId() {
		return labelId;
	}
	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}

	@Column(name = "labelName")
	private String labelName;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User userId;
	
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
	}

	
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	@Override
	public String toString() {
		return "Label [labelName=" + labelName + ", labelId=" + labelId + "]";
	}
	

}
