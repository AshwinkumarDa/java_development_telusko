package com.ashwinkumard.demorest;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class AlienRepository {
	
	Connection connection = null;
	
	private String getValueForKeyFromOS(String key) {
		return System.getenv(key);
	}
	
	static {
		// Load and Register the Driver
		// instantiate/load Driver class.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AlienRepository() {
		
		String url = "jdbc:mysql://localhost:3306/telusko_db";
		String userName = getValueForKeyFromOS("DB_USER_TELUSKO");
		String password = getValueForKeyFromOS("DB_PASSWORD_TELUSKO");
	
		System.out.println("username: "+ userName);
		
		try {
			connection = DriverManager.getConnection(url, userName, password);
		} catch(Exception e) {
			System.out.println("Exception: " + e);
		}
	}
	
	public List<Alien> getAliens() {
		List<Alien> aliens = new ArrayList<>();
		
		String sql = "SELECT * FROM alien";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Alien a = new Alien();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setPoints(rs.getInt("points"));
				aliens.add(a);
			}
		}
		catch(Exception e) {
			System.out.println("Exception: " + e);
		}
		return aliens;
	}
	
	
	public Alien getAlien(int id) {
		String sql = "SELECT * FROM alien WHERE id = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				Alien a = new Alien();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setPoints(rs.getInt("points"));
				return a;
			}
		}
		catch(Exception e) {
			System.out.println("Exception: " + e);
		}
		return null;
	}
	
	public void create(Alien alien) {
		String sql = "INSERT INTO alien values (?, ?, ?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, alien.getId());
			ps.setString(2, alien.getName());
			ps.setInt(3, alien.getPoints());
			
			int rowsAffected = ps.executeUpdate();
			System.out.println("inserted = "+ rowsAffected + " record");
			
		}
		catch(Exception e) {
			System.out.println("Exception: " + e);
		}
	}
	
	public Alien update(Alien alien) {
		String sql = "UPDATE alien SET name = ?, points = ? WHERE id = ?";
	
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, alien.getName());
			ps.setInt(2, alien.getPoints());
			ps.setInt(3, alien.getId());
			
			int rowsAffected = ps.executeUpdate();
			System.out.println("updated = "+ rowsAffected + " record");
			
			return alien;
		}
		catch(Exception e) {
			System.out.println("Exception: " + e);
		}
		return null;
	}
	
	public boolean delete(int id) {
		String sql = "DELETE FROM alien WHERE id=?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected != 0) {
				System.out.println("deleted " + rowsAffected + " rows.");
				return true;
			}
		} catch(Exception e) {
			System.out.println("Exception: " + e);
		}
		return false;
	}
}
