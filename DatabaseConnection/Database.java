package DatabaseConnection;
//***********************************************************************
//***********************************************************************
//***********************************************************************
//THE ENCRYPTION KEY NEEDS TO BE STORED IN THE DB.PROPERTIES!!!!!!!!!!!!!
//***********************************************************************
//***********************************************************************
//***********************************************************************
//***********************************************************************
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*; 
import java.util.*; 
public class Database {
	private Connection conn; 
	private String key; 
	public Database() throws SQLException, IOException {
		Properties prop = new Properties(); 
		FileInputStream fis  = new FileInputStream("DatabaseConnection/db.properties"); 
		prop.load(fis);
		this.key = prop.getProperty("key"); 
		String url = prop.getProperty("url"); 
		String user = prop.getProperty("user"); 
		String pass = prop.getProperty("password");
		
		try {
			this.conn = DriverManager.getConnection(url, user, pass); 
			
		} catch (Exception e) {
			throw e; 
		}
		
	}
	
	public void saveAccount(User userToAdd) {
		//we need to now build the DML
		String dml = String.format("insert into USER values('%s',aes_encrypt('%s','%s'))", 
								   userToAdd.getUsername(), 
								   userToAdd.getPassword(), 
								   this.key); 
		try {
			this.executeDML(dml);
		} catch (Exception e) {
			
		}
	}
	
	public boolean verifyUserExistsForCreateUser(User user) {
		String query = String.format("select * from USER where username='%s'", 
									 user.getUsername()); 
		if (this.query(query).size() == 0) {
			this.saveAccount(user);
			return true; 
		}
		else {
			return false; 
		}
		
	}
	
	public boolean verifyUserExistsForLogin(User user) {
		String query = String.format("select * from USER where (username='%s' and aes_decrypt(password, '%s')='%s')", 
				user.getUsername(), 
				this.key,
				user.getPassword()); 
		if (this.query(query).size() != 0) {
			return true; 
		}
		else {
			return false;
		}
	}
	
	public ArrayList<String> query(String query){
		ArrayList<String> toReturn = new ArrayList<String>(); 
		try {
			Statement stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData(); 
			while(rs.next()) {
				String stringToBuildArr[] = new String[rsmd.getColumnCount()]; 
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					stringToBuildArr[i-1] = rs.getString(i); 
				}
				toReturn.add(String.join(", ", stringToBuildArr)); 
			
			}
			return toReturn; 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; 
		} 
		
	}
	
	public void executeDML(String dml) throws SQLException{
		
		Statement statement = this.conn.createStatement(); 
		statement.execute(dml); 
	}
}
