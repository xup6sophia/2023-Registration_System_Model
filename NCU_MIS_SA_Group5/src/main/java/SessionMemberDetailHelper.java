import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONObject;

public class SessionMemberDetailHelper
{
	private static SessionMemberDetailHelper SMDH;
	private Connection conn = null;
	private PreparedStatement pres = null;
	
	private SessionMemberDetailHelper()
	{
	}
	
	//Instantiate an EventHelper if there's none.
	public static SessionMemberDetailHelper getHelper()
	{
		if( SMDH == null )
			SMDH = new SessionMemberDetailHelper();
			
		return SMDH;
	}
	
	public JSONObject getAllDataByMemberID( String member_id )
	{
		Event e = null;
		EventSessions es = null;
		
        JSONArray jsa = new JSONArray();
        
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        /** 紀錄SQL總行數 */
        int row = 0;
        
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try
		{
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `group5_final`.`sessionmemberdetail` INNER JOIN group5_final.eventsessions USING (eventsessions_id) INNER JOIN group5_final.event USING (event_id) WHERE member_id = ?";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString( 1, member_id );
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next())
            {
                /** 每執行一次迴圈表示有一筆資料 */  
                row += 1;
            	
            	int event_id = rs.getInt("event_id");
            	String title = rs.getString("event_title");
            	int eventtype_id = rs.getInt("eventtype_id");
            	String introduction = rs.getString("event_introduction");
            	String place = rs.getString("event_place");
            	Timestamp start_date = rs.getTimestamp("event_start_date");
            	Timestamp end_date = rs.getTimestamp("event_end_date");
            	String notification = rs.getString("event_notification");
            	String imagePath = rs.getString("event_image");
            	byte isOutDated = rs.getByte("event_isOutDated");
            	byte isCanceled = rs.getByte("event_isCanceled");
            	Timestamp modified = rs.getTimestamp("event_modified");
            	Timestamp created = rs.getTimestamp("event_created");
            	
            	int eventsessions_id = rs.getInt("eventsessions_id");
                String session_title = rs.getString("eventsessions_title");
                int limitnum = rs.getInt("eventsessions_limitnum");
                Timestamp session_start_date = rs.getTimestamp("eventsessions_start_date");
                Timestamp session_end_date = rs.getTimestamp("eventsessions_end_date");
                byte session_isCanceled = rs.getByte("eventsessions_isCanceled");
                Timestamp session_modified = rs.getTimestamp("eventsessions_modified");
                Timestamp session_created = rs.getTimestamp("eventsessions_created");
                
                int sessionmemberdetail_id = rs.getInt("sessionmemberdetail_id");
                int applystatus_id = rs.getInt("applystatus_id");
                
                
                e = new Event( event_id , title , eventtype_id , introduction , place , start_date , end_date , notification , imagePath , isOutDated , isCanceled , modified , created);
                es = new EventSessions( eventsessions_id , event_id , session_title , limitnum , session_start_date , session_end_date , session_isCanceled , session_modified , session_created );
            	
            	JSONObject result = new JSONObject();
            	result.put("event_info", e.getEventData());
            	result.put("eventsessions_info" , es.getEventSessionsData() );
            	result.put("sessionmemberdetail_id", sessionmemberdetail_id);
            	result.put("applystatus_id", applystatus_id);
            	
                jsa.put( result );
            }
		}
		catch( SQLException sqlex )
		{
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", sqlex.getErrorCode(), sqlex.getSQLState(), sqlex.getMessage());
		}
		catch( Exception ex )
		{
            /** 若錯誤則印出錯誤訊息 */
            ex.printStackTrace();
		}
		finally
		{
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
		}
		
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

	public JSONObject createSessionMemberDetail( SessionMemberDetail smd )
	{
		 /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        /** 紀錄SQL總行數 */
        int row = 0;
        
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        int sessionmemberdetail_id = -1;
        
        try
		{
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO group5_final.sessionmemberdetail(`member_id` , `eventsessions_id` , `sessionmemberdetail_modified` , `sessionmemberdetail_created` , `applystatus_id`)"
            		+ " VALUES( ? , ? , ? , ? , ? )";
            
            pres = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            pres.setInt( 1, smd.getMemberID() );
            pres.setInt(2, smd.getEventSessionsID());
            pres.setTimestamp(3, smd.getModified());
            pres.setTimestamp(4, smd.getCreated());
            pres.setInt(5, smd.getApplyStatusID());
            
            pres.executeUpdate();
            
            exexcute_sql = pres.toString();
	        System.out.println(exexcute_sql);
	         
	        rs = pres.getGeneratedKeys();
	        
	        if( rs.next() )
	        	sessionmemberdetail_id = rs.getInt(1);
		}
        catch( SQLException sqlex )
		{
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", sqlex.getErrorCode(), sqlex.getSQLState(), sqlex.getMessage());
		}
		catch( Exception ex )
		{
            /** 若錯誤則印出錯誤訊息 */
            ex.printStackTrace();
		}
		finally
		{
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
		}
        
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("sessionmemberdetail_id",sessionmemberdetail_id );

        return response;
	}

	public JSONObject deleteBySessionMemberDetailID( String sessionmemberdetail_id )
	{
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
		
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        
        /** 紀錄SQL總行數 */
        int row = 0;
		
		try
		{
			conn = DBMgr.getConnection();
			
			String sql = "DELETE FROM group5_final.sessionmemberdetail WHERE sessionmemberdetail_id = ?";
			
			pres = conn.prepareStatement(sql);
			pres.setString( 1 , sessionmemberdetail_id );
			
            /** 執行新增之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
		}
		catch( SQLException e )
		{
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
            DBMgr.close(pres, conn);
		}
		
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
		
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
	}

	public JSONObject deleteByEventID( String event_id )
	{
		/** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
		
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        
        /** 紀錄SQL總行數 */
        int row = 0;
        
		try
		{
			conn = DBMgr.getConnection();
			
			String sql = "DELETE FROM group5_final.sessionmemberdetail WHERE sessionmemberdetail_id IN ( SELECT sessionmemberdetail_id FROM `group5_final`.`sessionmemberdetail` INNER JOIN group5_final.eventsessions USING (eventsessions_id) INNER JOIN group5_final.event USING (event_id) WHERE event_id = ?)";
			pres = conn.prepareStatement(sql);
			pres.setString( 1 , event_id );
				
	        /** 執行新增之SQL指令並記錄影響之行數 */
	        row = pres.executeUpdate();
	            
	        /** 紀錄真實執行的SQL指令，並印出 **/
	        exexcute_sql = pres.toString();
	        System.out.println(exexcute_sql);
		}
		catch( SQLException e )
		{
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
            DBMgr.close(pres, conn);
		}
		
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
		
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
	}
	
	public boolean checkDuplicate(SessionMemberDetail smd) {
		/** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
		int row = -1;
		/** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
		ResultSet rs = null;

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			/** SQL指令 */
			String sql = "SELECT count(*) FROM `group5_final`.`sessionmemberdetail` WHERE `member_id` = ? AND `eventsessions_id` = ? ";

			/** 取得所需之參數 */
			int member_id = smd.getMemberID();
			int eventsessions_id = smd.getEventSessionsID();

			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
			pres.setInt(1, member_id);
			pres.setInt(2, eventsessions_id);
			
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

	public JSONObject deleteByEventSessionsID( String eventsessions_id )
	{
		/** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
		
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        
        /** 紀錄SQL總行數 */
        int row = 0;
        
		try
		{
			conn = DBMgr.getConnection();
			
			String sql = "DELETE FROM group5_final.sessionmemberdetail WHERE eventsessions_id = ?";
			pres = conn.prepareStatement(sql);
			pres.setString( 1 , eventsessions_id );
				
	        /** 執行新增之SQL指令並記錄影響之行數 */
	        row = pres.executeUpdate();
	            
	        /** 紀錄真實執行的SQL指令，並印出 **/
	        exexcute_sql = pres.toString();
	        System.out.println(exexcute_sql);
		}
		catch( SQLException e )
		{
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
            DBMgr.close(pres, conn);
		}
		
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
		
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
	}
}
