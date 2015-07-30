package com.viviproject.entities;

import java.io.Serializable;

public class UserInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2272234164231579797L;
	private int id;
	private String username;
	private String email;
	private int status;
	private int created_at;
	private int updated_at;
	private String avatar;
	private String education;
	private String working_start;
	private String working_position;
	private String role;
	private String personal_opinion;
	private String personal_work_opinion;
	private String personal_target;
	private String url;
	private String name;
	private String message;
	private int code;
	private String type;
			
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the created_at
	 */
	public int getCreated_at() {
		return created_at;
	}
	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(int created_at) {
		this.created_at = created_at;
	}
	/**
	 * @return the updated_at
	 */
	public int getUpdated_at() {
		return updated_at;
	}
	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(int updated_at) {
		this.updated_at = updated_at;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getWorking_start() {
		return working_start;
	}
	public void setWorking_start(String working_start) {
		this.working_start = working_start;
	}
	public String getWorking_position() {
		return working_position;
	}
	public void setWorking_position(String working_position) {
		this.working_position = working_position;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPersonal_opinion() {
		return personal_opinion;
	}
	public void setPersonal_opinion(String personal_opinion) {
		this.personal_opinion = personal_opinion;
	}
	public String getPersonal_work_opinion() {
		return personal_work_opinion;
	}
	public void setPersonal_work_opinion(String personal_work_opinion) {
		this.personal_work_opinion = personal_work_opinion;
	}
	public String getPersonal_target() {
		return personal_target;
	}
	public void setPersonal_target(String personal_target) {
		this.personal_target = personal_target;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
