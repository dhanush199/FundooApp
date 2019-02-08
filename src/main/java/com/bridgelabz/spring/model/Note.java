package com.bridgelabz.spring.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="Note")
public class Note implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToMany(fetch=FetchType.EAGER,targetEntity= Label.class,cascade = CascadeType.ALL)
	@JoinTable(name = "Note_Label", joinColumns = { @JoinColumn(name = "noteId") }, inverseJoinColumns = { @JoinColumn(name = "labelId") })

	List<Label> labelList;
	private int labelId;
	@GeneratedValue
	@Id
	@Column(name="id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="title")
	private String title;

	@Column(name="discription")
	private String discription;

	@Column(name="isPinned")
	private boolean isPinned;

	@Column(name="inTrash")
	private boolean inTrash;

	@Column(name="updateTime")
	@UpdateTimestamp
	private Timestamp updateTime;

	@Column(name="createdTime")
	@UpdateTimestamp
	private Timestamp createdTime;

	@ManyToOne
	@JoinColumn(name = "user_Id")
	private User user_Id;

	public int getLabelId() {
		return labelId;
	}

	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}

	public List<Label> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<Label> labelList) {
		this.labelList = labelList;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	public boolean isInTrash() {
		return inTrash;
	}

	public void setInTrash(boolean inTrash) {
		this.inTrash = inTrash;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}


	public User getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(User user_Id) {
		this.user_Id = user_Id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", title=" + title + ", discription=" + discription + ", inTrash=" + inTrash
				+ ", isPinned=" + isPinned + ", updateTime="+updateTime +",createdTime=" +createdTime +"]";
	}

}