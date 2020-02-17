import java.sql.*;

public class JDBCUtils {
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres?allowMultiQueries=true";
	static final String USER = "postgres";
	static final String PASS = "xuhengxun";
	
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
			String sql = "truncate table toptrumps restart identity";
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
				pstmt.setString(1, "HM1");
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
			conn.commit( );
			if(true) {
				pstmt.setString(1, "AI3");
				pstmt.setInt(2, 2);
				pstmt.setInt(3, 9);
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
	public static String getRecord() throws SQLException {
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
			String s5 = "select max(nor) as Max_Number_of_Rounds from gamerecord";
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
		return null;
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
	
	public static String printRs1(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int ngc = rs.getInt("Number_of_Games_Completed");
			String NGC = Integer.toString(ngc);
			System.out.println("Number_of_Games_Completed = " + NGC);
			return NGC;
		}
		return null;
	}
	
	public static String printRs2(ResultSet rs) throws SQLException {
			rs.beforeFirst();
			while(rs.next()) {
				int ngwa = rs.getInt("Number_of_Game_Won_by_AI");
				String NGWA = Integer.toString(ngwa);
				System.out.println("Number_of_Game_Won_by_AI = " + NGWA);
				return NGWA;
			}
			return null;
	}
	
	public static String printRs3(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int ngwh = rs.getInt("Number_of_Game_Won_by_Human");
			String NGWH = Integer.toString(ngwh);
			System.out.println("Number_of_Game_Won_by_Human = " + NGWH);
			return NGWH;
		}
		return null;
	}
	
	public static String printRs4(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int and = rs.getInt("Average_Number_of_Draws");
			String AND = Integer.toString(and);
			System.out.println("Average_Number_of_Draws = " + AND);
			return AND;
		}
		return null;
	}
	
	public static String printRs5(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int mnr = rs.getInt("Max_Number_of_Rounds");
			String MNR = Integer.toString(mnr);
			System.out.println("Max_Number_of_Rounds = " + MNR);
			return MNR;
		}
		return null;
	}
	
	public static String getNgc() throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String ngc = "";
		try {
			conn.setAutoCommit(false);
			System.out.println("Trying to get past game records");
			String s = "select count(*) as Number_of_Games_Completed from gamerecord";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(s);
			ngc = printRs1(rs);
			conn.commit( );
		} catch(SQLException se) {
			System.out.println("Could not get result from game record");
			se.printStackTrace();
			conn.rollback( );
		} finally {
			JDBCUtils.closeRs(rs);
			JDBCUtils.closeStmt(stmt);
			JDBCUtils.closeConn(conn);
		}
		return ngc;
	}
	
	public static String getNgwa() throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = null;
		ResultSet rs= null;
		String ngwa = "";
		try {
			conn.setAutoCommit(false);
			System.out.println("Trying to get past game records");
			String s =	"select count(*)as Number_of_Game_Won_by_AI from gamerecord where not wog = 'AI3'";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(s);
			ngwa = printRs2(rs);
			conn.commit( );
		} catch(SQLException se) {
			System.out.println("Could not get result from game record");
			se.printStackTrace();
			conn.rollback( );
		} finally {
			JDBCUtils.closeRs(rs);
			JDBCUtils.closeStmt(stmt);
			JDBCUtils.closeConn(conn);
		}
		return ngwa;
	}
	
	public static String getNgwh() throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = null;
		ResultSet rs= null;
		String ngwh = "";
		try {
			conn.setAutoCommit(false);
			System.out.println("Trying to get past game records");
			String s =	"select count(*)as Number_of_Game_Won_by_Human from gamerecord";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(s);
			ngwh = printRs3(rs);
			conn.commit( );
		} catch(SQLException se) {
			System.out.println("Could not get result from game record");
			se.printStackTrace();
			conn.rollback( );
		} finally {
			JDBCUtils.closeRs(rs);
			JDBCUtils.closeStmt(stmt);
			JDBCUtils.closeConn(conn);
		}
		return ngwh;
	}
	
	public static String getAnd() throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = null;
		ResultSet rs= null;
		String and = "";
		try {
			conn.setAutoCommit(false);
			System.out.println("Trying to get past game records");
			String s =	"select avg(nod) as Average_Number_of_Draws from gamerecord";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(s);
			and = printRs4(rs);
			conn.commit( );
		} catch(SQLException se) {
			System.out.println("Could not get result from game record");
			se.printStackTrace();
			conn.rollback( );
		} finally {
			JDBCUtils.closeRs(rs);
			JDBCUtils.closeStmt(stmt);
			JDBCUtils.closeConn(conn);
		}
		return and;
	}
	
	public static String getMnr() throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = null;
		ResultSet rs= null;
		String mnr = "";
		try {
			conn.setAutoCommit(false);
			System.out.println("Trying to get past game records");
			String s =	"select max(nor) as Max_Number_of_Rounds from gamerecord";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(s);
			mnr = printRs5(rs);
			conn.commit( );
		} catch(SQLException se) {
			System.out.println("Could not get result from game record");
			se.printStackTrace();
			conn.rollback( );
		} finally {
			JDBCUtils.closeRs(rs);
			JDBCUtils.closeStmt(stmt);
			JDBCUtils.closeConn(conn);
		}
		return mnr;
	}
}
