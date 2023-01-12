
import java.sql.*;
import java.time.LocalDateTime;
import org.json.*;
import java.security.NoSuchAlgorithmException;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class MemberHelper<br>
 * MemberHelper類別（class）主要管理所有與Member相關與資料庫之方法（method）
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class MemberHelper {

	/**
	 * 實例化（Instantiates）一個新的（new）MemberHelper物件<br>
	 * 採用Singleton不需要透過new
	 */
	private MemberHelper() {

	}

	/** 靜態變數，儲存MemberHelper物件 */
	private static MemberHelper mh;

	/** 儲存JDBC資料庫連線 */
	private Connection conn = null;

	/** 儲存JDBC預準備之SQL指令 */
	private PreparedStatement pres = null;

	/**
	 * 靜態方法<br>
	 * 實作Singleton（單例模式），僅允許建立一個MemberHelper物件
	 *
	 * @return the helper 回傳MemberHelper物件
	 */
	public static MemberHelper getHelper() {
		/** Singleton檢查是否已經有MemberHelper物件，若無則new一個，若有則直接回傳 */
		if (mh == null)
			mh = new MemberHelper();

		return mh;
	}

	/**
	 * 透過會員編號（ID）刪除會員
	 *
	 * @param id 會員編號
	 * @return the JSONObject 回傳SQL執行結果
	 */
	public JSONObject deleteByID(int member_id) {
		/** 記錄實際執行之SQL指令 */
		String exexcute_sql = "";
		/** 紀錄程式開始執行時間 */
		long start_time = System.nanoTime();
		/** 紀錄SQL總行數 */
		int row = 0;
		/** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
		ResultSet rs = null;

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();

			/** SQL指令 */
			String sql = "UPDATE `group5_final`.`member` SET `member_isDeleted` = 1 WHERE `member_id` = ? LIMIT 1";

			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
			pres.setInt(1, member_id);
			/** 執行刪除之SQL指令並記錄影響之行數 */
			row = pres.executeUpdate();

			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);

		} catch (SQLException e) {
			/** 印出JDBC SQL指令錯誤 **/
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			/** 若錯誤則印出錯誤訊息 */
			e.printStackTrace();
		} finally {
			/** 關閉連線並釋放所有資料庫相關之資源 **/
			DBMgr.close(rs, pres, conn);
		}

		/** 紀錄程式結束執行時間 */
		long end_time = System.nanoTime();
		/** 紀錄程式執行時間 */
		long duration = (end_time - start_time);

		/** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
		JSONObject response = new JSONObject();
		response.put("sql", exexcute_sql);
		response.put("row", row);
		response.put("time", duration);

		return response;
	}
	public JSONObject getByID(String member_id) {
		/** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
		Member m = null;
		/** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
		JSONArray jsa = new JSONArray();
		/** 記錄實際執行之SQL指令 */
		String exexcute_sql = "";
		/** 紀錄程式開始執行時間 */
		long start_time = System.nanoTime();
		/** 紀錄SQL總行數 */
		int row = 0;
		/** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
		ResultSet rs = null;

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			/** SQL指令 */
			String sql = "SELECT * FROM group5_final.member INNER JOIN membercredential ON member.member_id = membercredential.member_id WHERE member.member_id = ? LIMIT 1";
			System.out.println(sql);
			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
			pres.setString(1, member_id);
			/** 執行查詢之SQL指令並記錄其回傳之資料 */
			rs = pres.executeQuery();
			rs.next();
			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);

			/** 將 ResultSet 之資料取出 */
			int id = rs.getInt("member_id");
			String name = rs.getString("member_name");
			String email = rs.getString("member_email");
			Timestamp created =rs.getTimestamp("member_created");
			Timestamp usermodified= rs.getTimestamp("member_usermodified");
			Timestamp administratormodified = rs.getTimestamp("member_administratormodified");
			byte isDeleted = rs.getByte("member_isDeleted");
			System.out.println(member_id);
			
			m = new Member(id, name, email, created, usermodified,administratormodified,isDeleted);
			/** 取出該名會員之資料並封裝至 JSONsonArray 內 */
			jsa.put(m.getData());
			
			// int is_deleted = rs.getInt("isDeleted");
			
			

		} catch (SQLException e) {
			/** 印出JDBC SQL指令錯誤 **/
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			/** 若錯誤則印出錯誤訊息 */
			e.printStackTrace();
		} finally {
			/** 關閉連線並釋放所有資料庫相關之資源 **/
			DBMgr.close(rs, pres, conn);
		}

		/** 紀錄程式結束執行時間 */
		long end_time = System.nanoTime();
		/** 紀錄程式執行時間 */
		long duration = (end_time - start_time);

		/** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
		JSONObject response = new JSONObject();
		response.put("sql", exexcute_sql);
		response.put("row", row);
		response.put("time", duration);
		response.put("data", jsa);

		return response;
	}
	public JSONObject getByEmail(String member_account,String originalpassword) {
		/** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
		Member m = null;
		/** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
		JSONArray jsa = new JSONArray();
		/** 記錄實際執行之SQL指令 */
		String exexcute_sql = "";
		/** 紀錄程式開始執行時間 */
		long start_time = System.nanoTime();
		/** 紀錄SQL總行數 */
		int row = 0;
		/** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
		ResultSet rs = null;

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			/** SQL指令 */
			String sql = "SELECT * FROM group5_final.member INNER JOIN membercredential ON member.member_id = membercredential.member_id WHERE member_email = ? LIMIT 1";
			System.out.println(sql);
			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
			pres.setString(1, member_account);
			/** 執行查詢之SQL指令並記錄其回傳之資料 */
			rs = pres.executeQuery();
			rs.next();
			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);

			/** 將 ResultSet 之資料取出 */
			int member_id = rs.getInt("member_id");
			String member_name = rs.getString("member_name");
			String member_email = rs.getString("member_email");
			String member_hashedpassword = rs.getString("member_hashedpassword");
			int member_isDeleted = rs.getInt("member_isDeleted");
			System.out.println(member_id);
			
			boolean matched = BCrypt.checkpw(originalpassword, member_hashedpassword);
			System.out.println(matched);
			String checkpw="false";
			
			if(matched==true) {
				checkpw="true";
			}else {
				checkpw="false";
			}
			
			// int is_deleted = rs.getInt("isDeleted");
			System.out.println(member_email);
			/** 將每一筆會員資料產生一名新Member物件 */
			m = new Member(member_id, member_name, member_email, member_hashedpassword, member_isDeleted, checkpw);
			/** 取出該名會員之資料並封裝至 JSONsonArray 內 */
			jsa.put(m.getLoginData());
			

		} catch (SQLException e) {
			/** 印出JDBC SQL指令錯誤 **/
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			/** 若錯誤則印出錯誤訊息 */
			e.printStackTrace();
		} finally {
			/** 關閉連線並釋放所有資料庫相關之資源 **/
			DBMgr.close(rs, pres, conn);
		}

		/** 紀錄程式結束執行時間 */
		long end_time = System.nanoTime();
		/** 紀錄程式執行時間 */
		long duration = (end_time - start_time);

		/** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
		JSONObject response = new JSONObject();
		response.put("sql", exexcute_sql);
		response.put("row", row);
		response.put("time", duration);
		response.put("data", jsa);

		return response;
	}

	/**
	 * 取回所有會員資料
	 *
	 * @return the JSONObject 回傳SQL執行結果與自資料庫取回之所有資料
	 */
	public JSONObject getAll() {
		/** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
		Member m = null;
		/** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
		JSONArray jsa = new JSONArray();
		/** 記錄實際執行之SQL指令 */
		String exexcute_sql = "";
		/** 紀錄程式開始執行時間 */
		long start_time = System.nanoTime();
		/** 紀錄SQL總行數 */
		int row = 0;
		/** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
		ResultSet rs = null;

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			/** SQL指令 */
			String sql = "SELECT * FROM `group5_final`.`member`";

			/** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
			pres = conn.prepareStatement(sql);
			/** 執行查詢之SQL指令並記錄其回傳之資料 */
			rs = pres.executeQuery();

			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);

			/** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
			while (rs.next()) {
				/** 每執行一次迴圈表示有一筆資料 */
				row += 1;

				/** 將 ResultSet 之資料取出 */
				int user_id = rs.getInt("member_id");
				String name = rs.getString("member_name");
				String email = rs.getString("member_email");
				Timestamp  created= Timestamp.valueOf(rs.getString("member_created"));
				Timestamp usermodified= Timestamp.valueOf(rs.getString("member_usermodified"));
				Timestamp administratormodified = Timestamp.valueOf(rs.getString("member_administratormodified"));
				int isDeleted = rs.getInt("member_isDeleted");
				m = new Member(user_id, name, email, created, usermodified,administratormodified,isDeleted);
				/** 將每一筆會員資料產生一名新Member物件 */
				// m = new Member(member_id, name, email, password, usermodified,
				// administratormodified, created, is_deleted);
				/** 取出該名會員之資料並封裝至 JSONsonArray 內 */
				jsa.put(m.getData());
				
			}

		} catch (SQLException e) {
			/** 印出JDBC SQL指令錯誤 **/
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			/** 若錯誤則印出錯誤訊息 */
			e.printStackTrace();
		} finally {
			/** 關閉連線並釋放所有資料庫相關之資源 **/
			DBMgr.close(rs, pres, conn);
		}

		/** 紀錄程式結束執行時間 */
		long end_time = System.nanoTime();
		/** 紀錄程式執行時間 */
		long duration = (end_time - start_time);

		/** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
		JSONObject response = new JSONObject();
		response.put("sql", exexcute_sql);
		response.put("row", row);
		response.put("time", duration);
		response.put("data", jsa);

		return response;
			/** 每執行一次迴圈表示有一筆資料 */
	}

	/**
	 * 建立該名會員至資料庫
	 *
	 * @param m 一名會員之Member物件
	 * @return the JSON object 回傳SQL指令執行之結果
	 */
	public JSONObject create(Member m) {
		/** 記錄實際執行之SQL指令 */
		String exexcute_sql = "";
		/** 紀錄程式開始執行時間 */
		long start_time = System.nanoTime();
		/** 紀錄SQL總行數 */
		int row = 0;
		int member_id;

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			/** SQL指令 */
			String sql = "INSERT INTO `group5_final`.`member`(`member_name`, `member_email`, `member_usermodified`, `member_administratormodified`, `member_created`, `member_isDeleted`) VALUES(?, ?, ?, ?, ?, ?);";

			/** 取得所需之參數 */
			String member_name = m.getName();
			String member_email = m.getEmail();
			String member_password = m.getMemberhashedpassword();

			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pres.setString(1, member_name);
			pres.setString(2, member_email);

			pres.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			pres.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			pres.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
			
			pres.setInt(6, 0);
			
			/** 執行新增之SQL指令並記錄影響之行數 */
			row = pres.executeUpdate();

			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);
			
			ResultSet rs = pres.getGeneratedKeys();
			
			if (rs.next()) {
				member_id = rs.getInt(1);
				System.out.println("memberid:"+member_id);
				try {
					/** 取得資料庫之連線 */
					conn = DBMgr.getConnection();
					/** SQL指令 */
					String sql2 = "INSERT INTO `group5_final`.`membercredential`(`member_id`, `member_hashedpassword`) VALUES(?, ?);";

					/** 將參數回填至SQL指令當中 */
					pres = conn.prepareStatement(sql2);
					pres.setInt(1, member_id);
					String generatedSecuredPasswordHash = BCrypt.hashpw(member_password, BCrypt.gensalt(12));
					pres.setString(2, generatedSecuredPasswordHash);
					
					/** 執行新增之SQL指令並記錄影響之行數 */
					row += pres.executeUpdate();

					/** 紀錄真實執行的SQL指令，並印出 **/
					exexcute_sql += pres.toString();
					System.out.println(exexcute_sql);

				} catch (SQLException e) {
					/** 印出JDBC SQL指令錯誤 **/
					System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
				} catch (Exception e) {
					/** 若錯誤則印出錯誤訊息 */
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			/** 印出JDBC SQL指令錯誤 **/
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			/** 若錯誤則印出錯誤訊息 */
			e.printStackTrace();
		} finally {
			/** 關閉連線並釋放所有資料庫相關之資源 **/
			DBMgr.close(pres, conn);
		}

		/** 紀錄程式結束執行時間 */
		long end_time = System.nanoTime();
		/** 紀錄程式執行時間 */
		long duration = (end_time - start_time);

		/** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
		JSONObject response = new JSONObject();
		response.put("sql", exexcute_sql);
		response.put("time", duration);
		response.put("row", row);

		return response;
	}

	/**
	 * 更新一名會員之會員資料
	 *
	 * @param m 一名會員之Member物件
	 * @return the JSONObject 回傳SQL指令執行結果與執行之資料
	 */
	public JSONObject updateByID(Member m) {
		/** 紀錄回傳之資料 */
		JSONArray jsa = new JSONArray();
		/** 記錄實際執行之SQL指令 */
		String exexcute_sql = "";
		/** 紀錄程式開始執行時間 */
		long start_time = System.nanoTime();
		/** 紀錄SQL總行數 */
		int row = 0;

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			/** SQL指令 */
			String sql = "UPDATE group5_final.member, group5_final.membercredential SET member_name = ?, member_email = ?, member_hashedpassword = ?,member_administratormodified = ? WHERE member.member_id = membercredential.member_id AND member.member_id = ?";

			/** 取得所需之參數 */
			int id =m.getID();
			String name = m.getName();
			System.out.println(name);
			String email = m.getEmail();
			String password = m.getMemberhashedpassword();
			String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
			
			
			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
			pres.setString(1, name);
			pres.setString(2,email);
			
			pres.setString(3, generatedSecuredPasswordHash);
			pres.setTimestamp(4,Timestamp.valueOf(LocalDateTime.now()));
			pres.setInt(5, id);
			
			/** 執行更新之SQL指令並記錄影響之行數 */
			row = pres.executeUpdate();

			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);

		} catch (SQLException e) {
			/** 印出JDBC SQL指令錯誤 **/
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			/** 若錯誤則印出錯誤訊息 */
			e.printStackTrace();
		} finally {
			/** 關閉連線並釋放所有資料庫相關之資源 **/
			DBMgr.close(pres, conn);
		}

		/** 紀錄程式結束執行時間 */
		long end_time = System.nanoTime();
		/** 紀錄程式執行時間 */
		long duration = (end_time - start_time);

		/** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
		JSONObject response = new JSONObject();
		response.put("sql", exexcute_sql);
		response.put("row", row);
		response.put("time", duration);
		response.put("data", jsa);

		return response;
	}

	/**
	 * 更新會員更新資料之分鐘數
	 *
	 * @param m 一名會員之Member物件
	 */

	/**
	 * 更新會員之會員組別
	 *
	 * @param m      一名會員之Member物件
	 * @param status 會員組別之字串（String）
	 */
	public void updateStatus(Member m, String status) {
		/** 記錄實際執行之SQL指令 */
		String exexcute_sql = "";

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			/** SQL指令 */
			String sql = "Update `group5_final`.`member` SET `member_isDleted` = ? WHERE `member_id` = ?";
			/** 取得會員編號 */
			int id = m.getID();

			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
			pres.setString(1, status);
			pres.setInt(2, id);
			/** 執行更新之SQL指令 */
			pres.executeUpdate();

			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);
		} catch (SQLException e) {
			/** 印出JDBC SQL指令錯誤 **/
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			/** 若錯誤則印出錯誤訊息 */
			e.printStackTrace();
		} finally {
			/** 關閉連線並釋放所有資料庫相關之資源 **/
			DBMgr.close(pres, conn);
		}
	}

	public boolean checkDuplicate(Member m) {
		/** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
		int row = -1;
		/** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
		ResultSet rs = null;

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			/** SQL指令 */
			String sql = "SELECT count(*) FROM `group5_final`.`member` WHERE `member_email` = ?";

			/** 取得所需之參數 */
			String email = m.getEmail();

			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
			pres.setString(1, email);
			/** 執行查詢之SQL指令並記錄其回傳之資料 */
			rs = pres.executeQuery();

			/** 讓指標移往最後一列，取得目前有幾行在資料庫內 */
			rs.next();
			row = rs.getInt("count(*)");
			System.out.print(row);

		} catch (SQLException e) {
			/** 印出JDBC SQL指令錯誤 **/
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			/** 若錯誤則印出錯誤訊息 */
			e.printStackTrace();
		} finally {
			/** 關閉連線並釋放所有資料庫相關之資源 **/
			DBMgr.close(rs, pres, conn);
		}

		/**
		 * 判斷是否已經有一筆該學號之資料 若無一筆則回傳False，否則回傳True
		 */
		return (row == 0) ? false : true;
	}

}
