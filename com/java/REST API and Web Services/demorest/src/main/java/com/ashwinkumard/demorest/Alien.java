package com.ashwinkumard.demorest;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Alien {
	private int id;
	private String name;
	private int points;
	
	
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
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	@Override
	public String toString() {
		return "Alien [id=" + id + ", name=" + name + ", points=" + points + "]";
	}
	
	
}
