

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

public class AdministratorHelper {

	/**
	 * 實例化（Instantiates）一個新的（new）MemberHelper物件<br>
	 * 採用Singleton不需要透過new
	 */
	private AdministratorHelper() {

	}

	/** 靜態變數，儲存MemberHelper物件 */
	private static AdministratorHelper adh;

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
	public static AdministratorHelper getHelper() {
		/** Singleton檢查是否已經有MemberHelper物件，若無則new一個，若有則直接回傳 */
		if (adh == null){
			adh = new AdministratorHelper();
		}

		return adh;
	}

	/**
	 * 透過會員編號（ID）刪除會員
	 *
	 * @param id 會員編號
	 * @return the JSONObject 回傳SQL執行結果
	 */
	public JSONObject deleteByID(int administrator_id) {
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
			String sql = "DELETE FROM `group5_final`.`administrator` WHERE `administrator_id` = ? LIMIT 1";

			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
			pres.setInt(1, administrator_id);
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

	/**
	 * 取回所有會員資料
	 *
	 * @return the JSONObject 回傳SQL執行結果與自資料庫取回之所有資料
	 */
	public JSONObject getAll() {
		/** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
		Administrator ad = null;
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
			String sql = "SELECT * FROM `group5_final`.`administrator`";

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
				int user_id = rs.getInt("administrator_id");
				String name = rs.getString("administrator_name");
				String email = rs.getString("administrator_email");

				/** 將每一筆會員資料產生一名新administrator物件 */
				ad = new Administrator(user_id, name, email);

				/** 取出該名管理員之資料並封裝至 JSONsonArray 內 */
				jsa.put(ad.getData());
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
	}

	/**
	 * 透過會員編號（user_id）取得會員資料
	 *
	 * @param stu_id 學號
	 * @return the JSON object 回傳SQL執行結果與該會員編號之會員資料
	 */
	public JSONObject getByAdministratorID(String administrator_id) {
		/** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
		Administrator ad= null;
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
			String sql = "SELECT * FROM group5_final.administrator INNER JOIN administratorcredential ON administrator.administrator_id = administratorcredential.administrator_id WHERE administrator.administrator_id = ? LIMIT 1";

			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
			pres.setString(1, administrator_id);
			/** 執行查詢之SQL指令並記錄其回傳之資料 */
			rs = pres.executeQuery();
			
			rs.next();
	
			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);
			
			/** 將 ResultSet 之資料取出 */
		    int id = rs.getInt("administrator_id");
			String name = rs.getString("administrator_name");
			String email = rs.getString("administrator_email");
			//int isRoot = rs.getInt("administrator_isRoot");
			String hashedpassword = rs.getString("administrator_hashedpassword");
			
			/** 將每一筆會員資料產生一名新Member物件 */
		    ad = new Administrator(id, name, email, hashedpassword);
			/** 取出該名會員之資料並封裝至 JSONsonArray 內 */
			jsa.put(ad.getData());

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
		
		System.out.println(response);

		return response;
	}

	
	/**
	 * 透過帳號（email）取得會員資料
	 *
	 * @param email 帳號
	 * @return the JSON object 回傳SQL執行結果與該會員編號之會員資料
	 */
	public JSONObject getByAccount(String account,String originalpassword) {
		/** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
		Administrator ad = null;
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
			String sql = "SELECT * FROM group5_final.administrator INNER JOIN administratorcredential ON administrator.administrator_id = administratorcredential.administrator_id WHERE administrator_email = ? LIMIT 1";

			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
			pres.setString(1, account);
			/** 執行查詢之SQL指令並記錄其回傳之資料 */
			rs = pres.executeQuery();
			
			rs.next();

			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);
		        
			/** 將 ResultSet 之資料取出 */
			int id = rs.getInt("administrator_id");
			String name = rs.getString("administrator_name");
			String email = rs.getString("administrator_email");
			String hashedpassword = rs.getString("administrator_hashedpassword");

			boolean matched = BCrypt.checkpw(originalpassword, hashedpassword);
			System.out.println(matched);
			String checkpw="false";
			
			if(matched==true) {
				checkpw="true";
			}else {
				checkpw="false";
			}
			/** 將每一筆會員資料產生一名新Member物件 */
		    ad = new Administrator(id, name ,email , hashedpassword,checkpw);
			/** 取出該名會員之資料並封裝至 JSONsonArray 內 */
			jsa.put(ad.getLoginData());

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
		
		System.out.println(response);

		return response;
	}

	
	/**
	 * 更新一名會員之會員資料
	 *
	 * @param m 一名會員之Member物件
	 * @return the JSONObject 回傳SQL指令執行結果與執行之資料
	 */
	public JSONObject updateByID(Administrator ad) {
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
			String sql = "UPDATE group5_final.administrator, group5_final.administratorcredential SET administrator_name = ?, administrator_email = ?, administrator_hashedpassword = ? WHERE administrator.administrator_id = administratorcredential.administrator_id AND administrator.administrator_id = ?";
			/** 取得所需之參數 */
			int id = ad.getAdministratorID();
			String name = ad.getAdministratorName();
			String email = ad.getAdministratorEmail();
			//int isRoot = ad.getAdministratorIsRoot();
			String password = ad.getAdministratorHashedpassword();
			String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
			pres.setString(1, name);
			pres.setString(2, email);
			//pres.setInt(3, isRoot);
			pres.setString(3, generatedSecuredPasswordHash);
			pres.setInt(4, id);
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
	
	
	public JSONObject create(Administrator ad) {//建立administrator
		/** 記錄實際執行之SQL指令 */
		String exexcute_sql = "";
		/** 紀錄程式開始執行時間 */
		long start_time = System.nanoTime();
		/** 紀錄SQL總行數 */
		int row = 0;
		int administrator_id;

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			/** SQL指令 */
			String sql = "INSERT INTO `group5_final`.`administrator`(`administrator_name`, `administrator_email`,  `administrator_modified`, `administrator_created`,`administrator_isRoot` , `administrator_isDeleted`) VALUES(?, ?, ?, ?, ?, ?);";

			/** 取得所需之參數 */
			String administrator_name = ad.getAdministratorName();
			String administrator_email = ad.getAdministratorEmail();
			String administrator_password = ad.getAdministratorHashedpassword();

			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pres.setString(1, administrator_name);
			pres.setString(2, administrator_email);

			pres.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			pres.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			pres.setInt(5, 1);
			
			pres.setInt(6, 0);
			
			/** 執行新增之SQL指令並記錄影響之行數 */
			row = pres.executeUpdate();

			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);
			
			ResultSet rs = pres.getGeneratedKeys();
			
			if (rs.next()) {
				administrator_id = rs.getInt(1);
				System.out.println("administratorid:"+administrator_id);
				try {
					/** 取得資料庫之連線 */
					conn = DBMgr.getConnection();
					/** SQL指令 */
					String sql2 = "INSERT INTO `group5_final`.`administratorcredential`(`administrator_id`, `administrator_hashedpassword`) VALUES(?, ?);";

					/** 將參數回填至SQL指令當中 */
					pres = conn.prepareStatement(sql2);
					pres.setInt(1, administrator_id);
					String generatedSecuredPasswordHash = BCrypt.hashpw(administrator_password, BCrypt.gensalt(12));
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
	 * 更新會員之會員組別
	 *
	 * @param m      一名會員之Member物件
	 * @param status 會員組別之字串（String）
	 */
	
	public boolean checkDuplicate(Administrator ad) {//確認administrator有沒有重複
		/** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
		int row = -1;
		/** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
		ResultSet rs = null;

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			/** SQL指令 */
			String sql = "SELECT count(*) FROM `group5_final`.`administrator` WHERE `administrator_email` = ?";

			/** 取得所需之參數 */
			String email = ad.getAdministratorEmail();

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

