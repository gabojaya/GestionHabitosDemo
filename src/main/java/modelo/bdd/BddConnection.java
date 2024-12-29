package modelo.bdd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BddConnection {
	
	private static Connection conn =null;
	
	private BddConnection() {
		String servidor="localhost:3306";
		String database="gestionhabitos";
		String usuario="root";
		String password="admin";
		String url = "jdbc:mysql://" + servidor + "/" + database;
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			conn = DriverManager.getConnection(url, usuario, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static Connection getConexion() {
		if(conn==null) {
			new BddConnection();
		}
		return conn;
	}
	public static void cerrar(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}
	}
	
	public static void cerrar(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void cerrar() {
		if(conn!=null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
