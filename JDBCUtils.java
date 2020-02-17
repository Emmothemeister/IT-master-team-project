import java.sql.*; //Import JBDC class library

public class JDBCUtils {
//	static final String JDBC_DRIVER = "org.postgresql.Driver";
//	static final String DB_URL = "jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/m_19_2503308x?allowMultiQueries=true";
//	static final String USER = "m_19_2503308X";
//	static final String PASS = "2503308X";
	
//	//local host
//	static final String JDBC_DRIVER = "org.postgresql.Driver";
//	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres?allowMultiQueries=true";
//	static final String USER = "postgres";
//	static final String PASS = "********";//Enter your password here
	
	//AWS
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://52.24.215.108/404NotFound?allowMultiQueries=true";
	static final String USER = "404NotFound";
	static final String PASS = "404NotFound";//Enter your password here
		
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
	
	public static void createTables() { //create tables
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			String SQL = "CREATE TABLE PLAYER("
					+ "PID INT NOT NULL,"
					+ "PNAME VARCHAR(3) NOT NULL,"
					+ "REPW INT DEFAULT 0,"
					+ "CONSTRAINT PPK PRIMARY KEY (PID)"
					+ ");"
					+ "CREATE TABLE ONEGAME("
					+ "RID INT NOT NULL,"
					+ "WID INT,"
					+ "CONSTRAINT OGPK PRIMARY KEY (RID),"
					+ "CONSTRAINT OGFK FOREIGN KEY (WID) REFERENCES PLAYER(PID)"
					+ "ON UPDATE CASCADE ON DELETE CASCADE"
					+ ");"
					+ "CREATE TABLE ALLGAMES("
					+ "GID INT NOT NULL,"
					+ "WID INT,"
					+ "FROUND INT NOT NULL,"
					+ "NDRAW INT,"
					+ "CONSTRAINT AGPK PRIMARY KEY (GID),"
					+ "CONSTRAINT AGFK FOREIGN KEY (WID) REFERENCES PLAYER(PID)"
					+ "ON UPDATE CASCADE ON DELETE CASCADE"
					+ ");";
			stmt.executeUpdate(SQL);
		}catch (SQLException se) {
			se.printStackTrace();
			return;
		}
		try {
			stmt.close();
			conn.close();
		}catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	// clear all rounds' information once a game has completed
	public static void resetONEGAME() {
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = null;
		
		try {
			conn.setAutoCommit(false);
			System.out.println("clear all data from ONEGAME");
			String sql = "truncate table ONEGAME";
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
	
	public static void insertPLAYER() {//5PLAYERS
		Connection conn = JDBCUtils.getConnection();
//		PreparedStatement pstmt = null;
		Statement stmt = null;
		
		try {
			conn.setAutoCommit(false);
			System.out.println("Storing data into PLAYER");
//			String sql = "insert into PLAYER(PID,PNAME) values(?,?)";
//			pstmt = conn.prepareStatement(sql);
			stmt = conn.createStatement();
				String sql = "insert into PLAYER VALUES(0,'YOU');"
						+ "insert into PLAYER VALUES(1,'AI1');"
						+ "insert into PLAYER VALUES(2,'AI2');"
						+ "insert into PLAYER VALUES(3,'AI3');"
						+ "insert into PLAYER VALUES(4,'AI4');";
//				pstmt.setInt(1, a);
//				pstmt.setString(2, b);
//			int result = pstmt.executeUpdate();
			int result = stmt.executeUpdate(sql);
			System.out.println(result);
			conn.commit( );
		}catch(SQLException se) {
			System.out.println("Could not store data");
			se.printStackTrace();
		} finally {
			JDBCUtils.closeStmt(stmt);
			JDBCUtils.closeConn(conn);
		}
	}
	// Record information once a game has completed
	//modified
	public static void updateONEGAME(int a, int b) { //int b when null
		Connection conn = JDBCUtils.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			conn.setAutoCommit(false);
			System.out.println("Storing data into ONEGAME");
			String sql = "insert into ONEGAME(RID,WID) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			if(true) {
				pstmt.setInt(1, a);//modified
				pstmt.setInt(2, b);//modified
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
	
	public static void updateONEGAMEdraw(int a) { //int b when null
		Connection conn = JDBCUtils.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			conn.setAutoCommit(false);
			System.out.println("Storing data into ONEGAME");
			String sql = "insert into ONEGAME(RID,WID) values(?,null)";
			pstmt = conn.prepareStatement(sql);
			if(true) {
				pstmt.setInt(1, a);//modified
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
	
	public static void updateALLGAMES(int a, int b, int c, int d) {
		Connection conn = JDBCUtils.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			conn.setAutoCommit(false);
			System.out.println("Storing data into ALLGAMES");
			String sql = "insert into ALLGAMES(GID,WID,FROUND,NDRAW) values(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			if(true) {
				pstmt.setInt(1, a);//modified
				pstmt.setInt(2, b);//modified
				pstmt.setInt(3, c);//modified
				pstmt.setInt(4, d);//modified
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
	
	public static void getONEGAMERecord(){
		Connection conn = JDBCUtils.getConnection();
		Statement stmt1 = null;
		ResultSet gameResult = null;
		try {
			conn.setAutoCommit(false);
			String s1 = "select P.PID, P.PNAME,count(*) as Rounds_Each_Player_Win "
					+ "from PLAYER AS P,ONEGAME AS O "
					+ "WHERE P.PID = O.WID "
					+ "GROUP BY P.PID;";
			stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			gameResult = stmt1.executeQuery(s1);
			JDBCUtils.printGameResult(gameResult);
		} catch(SQLException se) {
			System.out.println("Could not get result from game record");
			se.printStackTrace();
		} finally {
			JDBCUtils.closeRs(gameResult);
			JDBCUtils.closeStmt(stmt1);
			JDBCUtils.closeConn(conn);
			System.out.println("players who were not shown on this table got 0 score");
		}
	}
	
	//Access to database and collect past game records
	public static void getStatistics(){
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
			System.out.println("Game Statistics:");
			String s1 = "select count(*) as Number_of_Games from ALLGAMES";
			String s2 = "select count(*)as Number_of_Game_Won_by_Human from ALLGAMES WHERE WID = 0";
			String s3 = "select count(*)as Number_of_Game_Won_by_AI from ALLGAMES WHERE not WID = 0";		
			String s4 = "select avg(NDRAW) as Average_Number_of_Draws from ALLGAMES";
			String s5 = "select MAX(FROUND) as Longest_Game from ALLGAMES";
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
			System.out.println("Could not get game statistics");
			se.printStackTrace();
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
	//One Game Result
	public static void printGameResult(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int ngc = rs.getInt("Rounds_Each_Player_Win");
			String pname = rs.getString("PNAME");
			System.out.println(pname + " : " + ngc);
		}
	}
	
	public static String printRs1(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int NOG = rs.getInt("Number_of_Games");
			String nog = Integer.toString(NOG);
			System.out.println("Number of Games: " + NOG);
			rturn nog;
		}
		return null;
    	}
	
	public static String printRs2(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int NOGWBH = rs.getInt("Number_of_Game_Won_by_Human");
			String nogwbh = Integer.toString(NOGWBH);
			System.out.println("Number of Human Wins: " + NOGWBH);
			return nogwbh;
		}
		return null;
  	}
	
	public static String printRs3(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int NOGWBA = rs.getInt("Number_of_Game_Won_by_AI");
			String nogwba = Integer.toString(NOGWBA);
			System.out.println("Number of AI Wins: " + NOGWBA);
			return nogwba;
		}
		return null;
	}
	
	public static void printRs4(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int ANOD = rs.getInt("Average_Number_of_Draws");
			System.out.println("Average number of Draws: " + ANOD);
			
		}
	}
	
	public static String printRs5(ResultSet rs) throws SQLException {
		rs.beforeFirst();
		while(rs.next()) {
			int LG = rs.getInt("Longest_Game");
			String lg = Integer.toString(LG);
			System.out.println("Longest Game: " + LG);
			return lg;
		}
		return NULL;
	}
	
	public static String getNg() throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String ng = "";
		try {
			conn.setAutoCommit(false);
			System.out.println("Trying to get past game records");
			String s = "select count(*) as Number_of_Games from ALLGAMES";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(s);
			ng = printRs1(rs);
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
		return ng;
	}
	
	public static String getNgwh() throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = null;
		ResultSet rs= null;
		String ngwh = "";
		try {
			conn.setAutoCommit(false);
			System.out.println("Trying to get past game records");
			String s = "sselect count(*)as Number_of_Game_Won_by_Human from ALLGAMES WHERE WID = 0";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(s);
			ngwh = printRs2(rs);
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
	
	public static String getNgwa() throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = null;
		ResultSet rs= null;
		String ngwa = "";
		try {
			conn.setAutoCommit(false);
			System.out.println("Trying to get past game records");
			String s = "select count(*)as Number_of_Game_Won_by_AI from ALLGAMES WHERE not WID = 0";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(s);
			ngwa = printRs3(rs);
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
	
	public static String getAnd() throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = null;
		ResultSet rs= null;
		String and = "";
		try {
			conn.setAutoCommit(false);
			System.out.println("Trying to get past game records");
			String s = "select avg(NDRAW) as Average_Number_of_Draws from ALLGAMES";
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
			String s = "select MAX(FROUND) as Longest_Game from ALLGAME";
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
