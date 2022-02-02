package databasesquared.services;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Random;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class LoginRegister {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private DatabaseConnectionService dbService = null;
	public LoginRegister(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return 0: if successful, -1 if error or fail
	 */
	public int login(String username, String password) {
		try {
			PreparedStatement stmt = this.dbService.getConnection().prepareStatement("Select Salt, PasswordHash \n From [Reviewer] \n where Username = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				byte[] salt = rs.getBytes(1);
				String dbHash = rs.getString(2);
				String hash = hashPassword(salt,password);
				if(hash.compareTo(dbHash) == 0) {
					return 0;
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return 0: if successful, -1 if error
	 */
	public int register(String username, String password) {
		//TODO: Task 6
		byte[] salt = this.getNewSalt();
		String hash = this.hashPassword(salt, password);
		try {
			CallableStatement stmt = this.dbService.getConnection().prepareCall("{? = call registerReviewer(?,?,?)}");
			stmt.registerOutParameter(1,Types.INTEGER);
			stmt.setString(2, username);
			stmt.setString(3, hash);
			stmt.setBytes(4, salt);
			stmt.execute();
			if(stmt.getInt(1) == 0) {
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}
}
