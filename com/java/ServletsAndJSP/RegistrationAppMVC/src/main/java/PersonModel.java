import java.sql.*;

public class PersonModel {
	private int piId;
	private String piName;
	private String piEmail;
	private String piPassword;
	private String piCity;
	
	private Connection connection = null;
	private PreparedStatement pStmt = null;
	
	/**
	 * @return the piId
	 */
	public int getPiId() {
		return piId;
	}
	/**
	 * @param piId the piId to set
	 */
	public void setPiId(int piId) {
		this.piId = piId;
	}
	/**
	 * @return the piName
	 */
	public String getPiName() {
		return piName;
	}
	/**
	 * @param piName the piName to set
	 */
	public void setPiName(String piName) {
		this.piName = piName;
	}
	/**
	 * @return the piEmail
	 */
	public String getPiEmail() {
		return piEmail;
	}
	/**
	 * @param piEmail the piEmail to set
	 */
	public void setPiEmail(String piEmail) {
		this.piEmail = piEmail;
	}
	/**
	 * @return the piPassword
	 */
	public String getPiPassword() {
		return piPassword;
	}
	/**
	 * @param piPassword the piPassword to set
	 */
	public void setPiPassword(String piPassword) {
		this.piPassword = piPassword;
	}
	/**
	 * @return the piCity
	 */
	public String getPiCity() {
		return piCity;
	}
	/**
	 * @param piCity the piCity to set
	 */
	public void setPiCity(String piCity) {
		this.piCity = piCity;
	}
	
	
	public int register() {
		int row = 0;
		try {
			connection = JdbcUtil.getConnection();
			
			String sql = "INSERT INTO personal_info(pi_name, pi_email, pi_password, pi_city) VALUES (?, ?, ?, ?)";
			
			pStmt = connection.prepareStatement(sql);
			pStmt.setString(1, piName);
			pStmt.setString(2, piEmail);
			pStmt.setString(3, piPassword);
			pStmt.setString(4, piCity);
			
			row = pStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				JdbcUtil.closeConnection(connection, pStmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return row;
	}
}
