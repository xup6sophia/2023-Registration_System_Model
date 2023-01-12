import java.sql.*;
import java.util.*;
import org.json.*;

public class EventSessionsHelper
{
	private static EventSessionsHelper ESH;
	private Connection conn = null;
	private PreparedStatement pres = null;
	private SessionMemberDetailHelper SMDH = SessionMemberDetailHelper.getHelper();
	
	private EventSessionsHelper()
	{
	}
	
	//Instantiate an EventHelper if there's none.
	public static EventSessionsHelper getHelper()
	{
		if( ESH == null )
			ESH = new EventSessionsHelper();
			
		return ESH;
	}
	
	public JSONArray createByList( int event_id , ArrayList<EventSessions> esl )
	{
		JSONArray jsa = new JSONArray();
		 
		try
		{
			conn = DBMgr.getConnection();
			String sql = "INSERT INTO `group5_final`.`eventsessions`(`event_id` , `eventsessions_title` , `eventsessions_limitnum` , `eventsessions_start_date` , `eventsessions_end_date` , `eventsessions_isCanceled` , `eventsessions_modified` , `eventsessions_created`)"
					 + " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			 
			for( int i = 0 ; i < esl.size() ; i++ )
			{
				EventSessions es = esl.get(i);
				 
				String title = es.getTitle();
				int limitnum = es.getLimitNum();
				Timestamp start_date = es.getStartDate();
				Timestamp end_date = es.getEndDate();
				byte isCanceled = es.checkCanceled();
				Timestamp modified = es.getModifiedDate();
				Timestamp created = es.getCreatedDate();
				 
				pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		        pres.setInt(1, event_id);
		        pres.setString(2, title);
		        pres.setInt(3, limitnum);
		        pres.setTimestamp(4, start_date);
		        pres.setTimestamp(5, end_date);
		        pres.setByte(6, isCanceled);
		        pres.setTimestamp(7, modified);
		        pres.setTimestamp(8, created);
		        
		        pres.executeUpdate();
		         
		        String exexcute_sql = pres.toString();
		        System.out.println(exexcute_sql);
		         
		        ResultSet rs = pres.getGeneratedKeys();
		         
		        if( rs.next() )
		        {
		        	int eventsessions_id = rs.getInt(1);
		        	jsa.put(eventsessions_id);
		        }
			}
		}
		catch( SQLException sqlex )
		{
			System.err.format("SQL State: %s\n%s\n%s", sqlex.getErrorCode(), sqlex.getSQLState(), sqlex.getMessage());
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
		finally
		{
			DBMgr.close(pres, conn);
		}
		 
		return jsa;
	}
	
	public ArrayList<EventSessions> getEventSessionByEventID( int event_id )
	{
		ArrayList<EventSessions> result = new ArrayList<EventSessions>();
		
		/** 記錄實際執行之SQL指令 */
        ResultSet rs = null;
        EventSessions es;
        
        try
        {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `group5_final`.`eventsessions` WHERE `eventsessions`.`event_id` = ?";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, event_id);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            rs = pres.executeQuery();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            String exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            while(rs.next())
            {
                /** 將 ResultSet 之資料取出 */
                int eventsessions_id = rs.getInt("eventsessions_id");
                String title = rs.getString("eventsessions_title");
                int limitnum = rs.getInt("eventsessions_limitnum");
                Timestamp start_date = rs.getTimestamp("eventsessions_start_date");
                Timestamp end_date = rs.getTimestamp("eventsessions_end_date");
                byte isCanceled = rs.getByte("eventsessions_isCanceled");
                Timestamp modified = rs.getTimestamp("eventsessions_modified");
                Timestamp created = rs.getTimestamp("eventsessions_created");
                
                /** 將每一筆會員資料產生一名新Member物件 */
                es = new EventSessions( eventsessions_id , event_id , title , limitnum , start_date , end_date , isCanceled , modified , created );
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                result.add(es);
            }
        }
        catch (SQLException e)
        {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
        catch (Exception e)
        {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        }
        finally
        {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }
        
        return result;
	}
	
	//未完成
	public int updateSessions( ArrayList<EventSessions> esl )
	{
		int row = 0;
		
		try
		 {
			 conn = DBMgr.getConnection();
			 
			 String sql = "UPDATE `group5_final`.`eventsessions` SET eventsessions_title = ? , eventsessions_limitnum = ? , eventsessions_start_date = ? , eventsessions_end_date = ? , eventsessions_isCanceled = ? , eventsessions_modified = ? WHERE eventsessions_id = ?";
			 
			 for( int i = 0 ; i < esl.size() ; i++ )
			 {
				 EventSessions es = esl.get(i);
				 
				 String title = es.getTitle();
				 int limitnum = es.getLimitNum();
				 Timestamp start_date = es.getStartDate();
				 Timestamp end_date = es.getEndDate();
				 byte isCanceled = es.checkCanceled();
				 Timestamp modified = es.getModifiedDate();
				 int eventsessions_id = es.getEventSessionID();
				 
				 pres = conn.prepareStatement(sql);
		         pres.setString(1, title);
		         pres.setInt(2, limitnum);
		         pres.setTimestamp(3, start_date);
		         pres.setTimestamp(4, end_date);
		         pres.setByte(5, isCanceled);
		         pres.setTimestamp(6, modified);
		         pres.setInt(7, eventsessions_id);
		         
		         row = pres.executeUpdate();
		         
		         String exexcute_sql = pres.toString();
		         System.out.println(exexcute_sql);
			 }
		 }
		 catch( SQLException sqlex )
		 {
			 System.err.format("SQL State: %s\n%s\n%s", sqlex.getErrorCode(), sqlex.getSQLState(), sqlex.getMessage());
		 }
		 catch( Exception ex )
		 {
			 ex.printStackTrace();
		 }
		 finally
		 {
			 DBMgr.close(pres, conn);
		 }
		 
		 return row;
	}

	public JSONObject deleteByEventID( String eventID )
	{
		int row = 0;
		
		String execute_sql = "";
		
		long start_time = System.nanoTime(); 
		
		JSONObject deleted_sessionmemberdetail = SMDH.deleteByEventID( eventID );
		
		try
		{
			conn = DBMgr.getConnection();
			
			String sql = "DELETE FROM group5_final.eventsessions WHERE event_id = ?";
			
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
		resp.put( "deleted_sessionmemberdetail" , deleted_sessionmemberdetail );
		
		return resp;
	}

	public JSONObject deleteByEventSessionsID( String eventsessionsID )
	{
		int row = 0;
		
		String execute_sql = "";
		
		long start_time = System.nanoTime(); 
		
		JSONObject deleted_sessionmemberdetail = SMDH.deleteByEventSessionsID( eventsessionsID );
		
		try
		{
			conn = DBMgr.getConnection();
			
			String sql = "DELETE FROM group5_final.eventsessions WHERE eventsessions_id = ?";
			
			pres = conn.prepareStatement(sql);
			pres.setString( 1 , eventsessionsID );
			
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
		resp.put( "deleted_sessionmemberdetail" , deleted_sessionmemberdetail );
		
		return resp;
	}
}
