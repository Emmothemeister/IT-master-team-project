import java.sql.*;

public class JDBCUtils {
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/m_19_2503308x?allowMultiQueries=true";
	static final String USER = "m_19_2503308X";
	static final String PASS = "2503308X";
	
	// Connect to database
	public static Connection getConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database");
			return DriverManager.getConnection(DB_URL, USER, PASS);
		}catch(ClassNotFoundException e) {
			System.out.println("Could not find JDBC driver");
			e.printStackTrace();
		}catch(SQLException se) {
			System.out.println("Connection Failed");
			se.printStackTrace();
		}
		return null;
	}
	
	// clear all rounds' information once a game has completed
	public static void resetRounds() {
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = null;
		
		try {
			conn.setAutoCommit(false);
			System.out.println("clear all data from toptrumps");
			String sql = "truncate table toptrumps";
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			System.out.println(result);
			conn.commit( );
		}catch(SQLException se) {
			System.out.println("Could not delete data");
			se.printStackTrace();
		}finally {
			JDBCUtils.closeStmt(stmt);
			JDBCUtils.closeConn(conn);
		}
	}
	
	// Record information once a round has completed
	public static void updateRounds() {
		Connection conn = JDBCUtils.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			conn.setAutoCommit(false);
			System.out.println("Storing data into toptrumps");
			String sql = "insert into toptrumps(winner,draw) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			if(true) {
				pstmt.setString(1, "AI1");
				pstmt.setString(2, "false");
			}
			int result = pstmt.executeUpdate();
			System.out.println(result);
			conn.commit( );
		}catch(SQLException se) {
			System.out.println("Could not store data");
			se.printStackTrace();
		} finally {
			JDBCUtils.closePstmt(pstmt);
			JDBCUtils.closeConn(conn);
		}
	}
	
	// Record information once a game has completed
	public static void updateGames() {
		Connection conn = JDBCUtils.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			conn.setAutoCommit(false);
			System.out.println("Storing data into game record");
			String sql = "insert into gamerecord(wog,nod, nor) values(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			if(true) {
				pstmt.setString(1, "HM2");
				pstmt.setInt(2, 3);
				pstmt.setInt(3, 10);
			}
			int result = pstmt.executeUpdate();
			System.out.println(result);
			conn.commit( );
		}catch(SQLException se) {
			System.out.println("Could not store data");
			se.printStackTrace();
		} finally {
			JDBCUtils.closePstmt(pstmt);
			JDBCUtils.closeConn(conn);
		}
	}
	
	// Access to database and collect past game records
	public static void getRecord() throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		Statement stmt1 = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		Statement stmt4 = null;
		Statement stmt5 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs5 = null;
		try {
			conn.setAutoCommit(false);
			System.out.println("Trying to get past game records");
			String s1 = "select count(*) as Number_of_Games_Completed from gamerecord";
			String s2 =	"select count(*)as Number_of_Game_Won_by_AI from gamerecord";
			String s3 = "select count(*)as Number_of_Game_Won_by_Human from gamerecord";
			String s4 = "select avg(nod) as Average_Number_of_Draws from gamerecord";
			String s5 = "select avg(nor) as Max_Number_of_Rounds from gamerecord";
			stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt4 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt5 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs1 = stmt1.executeQuery(s1);
			rs2 = stmt2.executeQuery(s2);
			rs3 = stmt3.executeQuery(s3);
			rs4 = stmt4.executeQuery(s4);
			rs5 = stmt5.executeQuery(s5);
			JDBCUtils.printRs1(rs1);
			JDBCUtils.printRs2(rs2);
			JDBCUtils.printRs3(rs3);
			JDBCUtils.printRs4(rs4);
			JDBCUtils.printRs5(rs5);
			conn.commit( );
		} catch(SQLException se) {
			System.out.println("Could not get result from game record");
			se.printStackTrace();
			conn.rollback( );
		} finally {
			JDBCUtils.closeRs(rs5);
			JDBCUtils.closeRs(rs4);
			JDBCUtils.closeRs(rs3);
			JDBCUtils.closeRs(rs2);
			JDBCUtils.closeRs(rs1);
			JDBCUtils.closeStmt(stmt5);
			JDBCUtils.closeStmt(stmt4);
			JDBCUtils.closeStmt(stmt3);
			JDBCUtils.closeStmt(stmt2);
			JDBCUtils.closeStmt(stmt1);
			JDBCUtils.closeConn(conn);
		}
	}
	
	public static void closeRs(ResultSet rs) {
		try {
			if(rs!= null)rs.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	
	public static void closeStmt(Statement stmt) {
		try {
			if(stmt!= null)stmt.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	public static void closePstmt(PreparedStatement pstmt) {
		try {
			if(pstmt!= null)pstmt.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	
	public static void closeConn(Connection conn) {
		try {
			if(conn!= null)conn.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
	
	public static void printRs1(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int ngc = rs.getInt("Number_of_Games_Completed");
			System.out.println("Number_of_Games_Completed = " + ngc);
		}
	}
	
	public static void printRs2(ResultSet rs) throws SQLException {
			rs.beforeFirst();
			while(rs.next()) {
				int ngwa = rs.getInt("Number_of_Game_Won_by_AI");
				System.out.println("Number_of_Game_Won_by_AI = " + ngwa);
			}
	}
	
	public static void printRs3(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int ngwh = rs.getInt("Number_of_Game_Won_by_Human");
			System.out.println("Number_of_Game_Won_by_Human = " + ngwh);
		}
	}
	
	public static void printRs4(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int and = rs.getInt("Average_Number_of_Draws");
			System.out.println("Average_Number_of_Draws = " + and);
			
		}
	}
	
	public static void printRs5(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int mnr = rs.getInt("Max_Number_of_Rounds");
			System.out.println("Max_Number_of_Rounds = " + mnr);
		}
	}
}
