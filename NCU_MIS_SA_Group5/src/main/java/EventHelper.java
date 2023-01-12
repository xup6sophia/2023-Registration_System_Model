import java.sql.*;
import java.util.*;
import org.json.*;
//import DBMgr

public class EventHelper
{
	//Only one EventHelper could exist at a time.
	private static EventHelper EH;
	private Connection conn = null;
	private PreparedStatement pres = null;
	
	private EventSessionsHelper ESH = EventSessionsHelper.getHelper();
	
	private EventHelper()
	{
	}
	
	//Instantiate an EventHelper if there's none.
	public static EventHelper getHelper()
	{
		if( EH == null )
			EH = new EventHelper();
			
		return EH;
	}
	
	public JSONObject getAllEvent()
	{
        Event e = null;
        JSONArray jsa = new JSONArray();
        
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        
        /** 紀錄SQL總行數 */
        int row = 0;
        
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
		
		try
		{
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM group5_final.event INNER JOIN group5_final.eventtype USING (eventtype_id)";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
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
                
                /** 將 ResultSet 之資料取出 */
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
            	String eventtype_description = rs.getString("eventtype_description");
            	
                e = new Event( event_id , title , eventtype_id , introduction , place , start_date , end_date , notification , imagePath , isOutDated , isCanceled , modified , created);
                JSONObject data = e.getAllData();
                data.put("eventtype_description",eventtype_description );
                jsa.put( data );
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
	
	public JSONObject getByTypeID( String typeID )
	{
		Event e = null;
        JSONArray jsa = new JSONArray();
        
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        
        /** 紀錄SQL總行數 */
        int row = 0;
        
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
		
		try
		{
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `group5_final`.`event` INNER JOIN group5_final.eventtype WHERE event.eventtype_id = eventtype.eventtype_id AND `event`.`eventtype_id` = ?";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString( 1, typeID );
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
                
                /** 將 ResultSet 之資料取出 */
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
            	String eventtype_description = rs.getString("eventtype_description");
            	
                e = new Event( event_id , title , eventtype_id , introduction , place , start_date , end_date , notification , imagePath , isOutDated , isCanceled , modified , created);
                JSONObject data = e.getAllData();
                data.put("eventtype_description",eventtype_description );
                jsa.put( data );
                System.out.println(jsa);
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
	
	public JSONObject getByEventID( String eventID )
	{
		Event e = null;
        JSONArray jsa = new JSONArray();
        
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        
        /** 紀錄SQL總行數 */
        int row = 0;
        
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
		
		try
		{
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `group5_final`.`event` INNER JOIN group5_final.eventtype WHERE event.eventtype_id = eventtype.eventtype_id AND `event`.`event_id` = ?";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString( 1, eventID );
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
                
                /** 將 ResultSet 之資料取出 */
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
            	String eventtype_description = rs.getString("eventtype_description");
            	
                e = new Event( event_id , title , eventtype_id , introduction , place , start_date , end_date , notification , imagePath , isOutDated , isCanceled , modified , created);
                JSONObject data = e.getAllData();
                data.put("eventtype_description",eventtype_description );
                jsa.put( data );
                System.out.println(jsa);
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
	
	public JSONObject createEvent( Event ev )
	{
        /** 記錄實際執行之SQL指令 */
        int event_id = -1;
        JSONArray sessionIDArray = new JSONArray();
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO `group5_final`.`event`(`event_title`, `eventtype_id`, `event_introduction`, `event_place`, `event_start_date`, `event_end_date`, `event_notification` , `event_image` , `event_isOutDated` , `event_isCanceled` , `event_modified` , `event_created`)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            /** 取得所需之參數 */
            String title = ev.getTitle();
            int eventtype_id = ev.getEventTypeID();
            String introduction = ev.getIntroduction();
            String place = ev.getPlace();
            Timestamp start_date = ev.getStartDate();
            Timestamp end_date = ev.getEndDate();
            String notification = ev.getNotification();
            String imagePath = ev.getImagePath();
            byte isOutDated = ev.checkOutDated();
            byte isCanceled = ev.checkCanceled();
            Timestamp modified = ev.getModifiedDate();
            Timestamp created = ev.getCreatedDate();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pres.setString(1, title);
            pres.setInt(2, eventtype_id);
            pres.setString(3, introduction);
            pres.setString(4, place);
            pres.setTimestamp(5, start_date);
            pres.setTimestamp(6, end_date);
            pres.setString(7, notification);
            pres.setString(8, imagePath);
            pres.setByte(9, isOutDated);
            pres.setByte(10, isCanceled);
            pres.setTimestamp(11, modified);
            pres.setTimestamp(12, created);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            String exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            //取得插入數值所產生的主鍵
            ResultSet rs = pres.getGeneratedKeys();

            //呼叫event session helper來插入eventsession的數值
            if (rs.next())
            {
                event_id = rs.getInt(1);
                ArrayList<EventSessions> sessionList = ev.getEventSessions();
                sessionIDArray  = ESH.createByList( event_id , sessionList );
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

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("event_id"  , event_id);
        response.put("eventsessions_id", sessionIDArray);

        return response;
    }
	
	public int updateEvent(Event ev)
	{	
		int row = 0;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "UPDATE `group5_final`.`event` SET `event_title` = ? , `eventtype_id` = ? , `event_introduction` = ? , `event_place` = ? , `event_start_date` = ? , `event_end_date` = ? , `event_notification` = ? , `event_image` = ? , `event_isOutDated` = ? , `event_isCanceled` = ? , `event_modified` = ? WHERE `event_id` = ?";
            
            /** 取得所需之參數 */
            String title = ev.getTitle();
            int eventtype_id = ev.getEventTypeID();
            String introduction = ev.getIntroduction();
            String place = ev.getPlace();
            Timestamp start_date = ev.getStartDate();
            Timestamp end_date = ev.getEndDate();
            String notification = ev.getNotification();
            String imagePath = ev.getImagePath();
            byte isOutDated = ev.checkOutDated();
            byte isCanceled = ev.checkCanceled();
            Timestamp modified = ev.getModifiedDate();
            int event_id = ev.getEventID();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, title);
            pres.setInt(2, eventtype_id);
            pres.setString(3, introduction);
            pres.setString(4, place);
            pres.setTimestamp(5, start_date);
            pres.setTimestamp(6, end_date);
            pres.setString(7, notification);
            pres.setString(8, imagePath);
            pres.setByte(9, isOutDated);
            pres.setByte(10, isCanceled);
            pres.setTimestamp(11, modified);
            pres.setInt(12 , event_id);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            String exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
;        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }
		
		return row;
	}
	
	public JSONObject deleteByEventID( String eventID )
	{
		int row = 0;
		
		String execute_sql = "";
		
		long start_time = System.nanoTime();
		
		JSONObject deleted_eventsessions = ESH.deleteByEventID( eventID );
		
		try
		{
			conn = DBMgr.getConnection();
			
			String sql = "DELETE FROM group5_final.event WHERE event_id = ?";
			
			pres = conn.prepareStatement(sql);
			pres.setString( 1 , eventID );
			
			row = pres.executeUpdate();
			
            execute_sql = pres.toString();
            System.out.println(execute_sql);
            
            
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
            DBMgr.close(pres, conn);
		}
		
		long duration = start_time - System.nanoTime();
		
		JSONObject resp = new JSONObject();
		resp.put( "sql" , execute_sql );
		resp.put( "row" , row );
		resp.put( "time" , duration );
		resp.put( "deleted_eventsessions" , deleted_eventsessions );
		
		return resp;
	}
}