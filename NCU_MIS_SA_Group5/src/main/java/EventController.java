import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.*;

public class EventController extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private EventHelper EH = EventHelper.getHelper();
	private EventSessionsHelper ESH = EventSessionsHelper.getHelper();
	
    public EventController()
    { super(); }

    //檢視活動(所有活動、類別活動、編號活動)
    //已完成:所有活動、類別活動、編號活動
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		JsonReader jsr = new JsonReader(request);
		String event_id = jsr.getParameter("event_id");
		String eventType_id = jsr.getParameter("eventtype_id");
		
		JSONObject resp = new JSONObject();
		JSONObject queryResult = null;
		
		if( !event_id.isEmpty() )
		{
			queryResult = EH.getByEventID(event_id);
			
			resp.put( "status" , "200" );
			resp.put( "message" , "編號活動取得成功" );
			resp.put( "response" , queryResult );
		}
		else if( !eventType_id.isEmpty() )
		{
			queryResult = EH.getByTypeID(eventType_id);
			
			resp.put( "status" , "200" );
			resp.put( "message" , "類別活動取得成功" );
			resp.put( "response" , queryResult );
		}
		else
		{
			queryResult = EH.getAllEvent();
			
			resp.put( "status" , "200" );
			resp.put( "message" , "所有活動取得成功" );
			resp.put( "response" , queryResult );
		}
		
		jsr.response( resp , response );
		System.out.println(resp);
	}

	//新增活動(上架活動)
	//已完成:上架活動
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		JsonReader jsr = new JsonReader(request);
		JSONObject jso = jsr.getObject();
		
		//reading json file
		String title = jso.getString("event_title");
		int eventtype_id = jso.getInt("eventtype_id");
		String introduction = jso.getString("event_introduction");
		String place = jso.getString("event_place");
	    Timestamp start_date = Timestamp.valueOf(LocalDateTime.parse( jso.getString("event_start_date")));
		Timestamp end_date = Timestamp.valueOf(LocalDateTime.parse( jso.getString("event_end_date") ));
		String notification = jso.getString("event_notification");
		String imagePath = jso.getString("event_image");
		JSONArray eventsessions = jso.getJSONArray("event_sessions");
		
		Event ev = new Event( title , eventtype_id , introduction , place , start_date , end_date , notification , imagePath );
		
		// add sessions to Event ev
		for( int i = 0 ; i < eventsessions.length() ; i++ )
		{
			JSONObject sessionData = eventsessions.getJSONObject(i);
			
			String sessionTitle = sessionData.getString("eventsessions_title");
			int limitnum = sessionData.getInt("eventsessions_limitnum");
			
			Timestamp session_start_date = Timestamp.valueOf(LocalDateTime.parse(sessionData.getString("eventsessions_start_date")));
			Timestamp session_end_date = Timestamp.valueOf(LocalDateTime.parse(sessionData.getString("eventsessions_end_date")));
			byte session_isCanceled = 0;
			Timestamp session_modified = Timestamp.valueOf(LocalDateTime.now());
			Timestamp session_created = Timestamp.valueOf(LocalDateTime.now());
			
			EventSessions es = new EventSessions( sessionTitle , limitnum , session_start_date , session_end_date , session_isCanceled , session_modified , session_created );
			
			ev.addEventSessions(es);
		}
		
		JSONObject result = EH.createEvent(ev);
		
		ev.setEventID( result.getInt("event_id") );
		ev.setEventSessionsID( result.getJSONArray("eventsessions_id") );
		
		/** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        
        resp.put("status", "200");
        resp.put("message", "活動新增成功！");
        resp.put("response", ev.getAllData());
        
        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}
	
	//更改活動
	//已完成:
	//待完成:更改活動
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		JsonReader jsr = new JsonReader(request);
		JSONObject jso = jsr.getObject();
		
		//reading json file
		int event_id = jso.getInt("event_id");
		String title = jso.getString("event_title");
		int eventtype_id = jso.getInt("eventtype_id");
		String introduction = jso.getString("event_introduction");
		String place = jso.getString("event_place");
	    Timestamp start_date = Timestamp.valueOf(LocalDateTime.parse( jso.getString("event_start_date")));
		Timestamp end_date = Timestamp.valueOf(LocalDateTime.parse( jso.getString("event_end_date") ));
		String notification = jso.getString("event_notification");
		String imagePath = jso.getString("event_image");
		byte isCanceled = (byte) jso.getInt("event_isCanceled");
		Timestamp modified = Timestamp.valueOf(LocalDateTime.now());
		JSONArray eventsessions = jso.getJSONArray("event_sessions");
    	
		Event ev = new Event( event_id , title , eventtype_id , introduction , place , start_date , end_date , notification , imagePath , isCanceled , modified );
		
    	ArrayList<EventSessions> create = new ArrayList<EventSessions>();
    	ArrayList<EventSessions> update = new ArrayList<EventSessions>();
    	ArrayList<EventSessions> current = ESH.getEventSessionByEventID(event_id);
    	JSONArray deleted_sessions = new JSONArray();
    	
		//判斷json array中的session是新增的、更新的還是刪除的
		for( int i = 0 ; i < eventsessions.length() ; i++ )
		{
			EventSessions es = null;
			
			JSONObject sessionData = eventsessions.getJSONObject(i);
			
			String eventsessions_id = sessionData.getString("eventsessions_id");
			String sessionTitle = sessionData.getString("eventsessions_title");
			int limitnum = sessionData.getInt("eventsessions_limitnum");
			
			Timestamp session_start_date = Timestamp.valueOf(sessionData.getString("eventsessions_start_date"));
			Timestamp session_end_date = Timestamp.valueOf(sessionData.getString("eventsessions_end_date"));
			
			byte session_isCanceled = (byte) sessionData.getInt("eventsessions_isCanceled");
			Timestamp session_modified = Timestamp.valueOf(LocalDateTime.now());
			Timestamp session_created = Timestamp.valueOf(LocalDateTime.now());
			
			if( eventsessions_id.isEmpty() )
			{
				es = new EventSessions( sessionTitle , limitnum , session_start_date , session_end_date , session_isCanceled , session_modified , session_created );
				create.add(es);
			}
			else
			{
				es = new EventSessions( Integer.parseInt(eventsessions_id) , sessionTitle , limitnum , session_start_date , session_end_date , session_isCanceled , session_modified );
				update.add(es);
			}
		}
		
		for( int i = 0 ; i < current.size() ; i++ )
		{
			boolean toDelete = true;
			int current_sessionID = current.get(i).getEventSessionID();
			
			for( int j = 0 ; j < update.size() ; j++ )
			{
				if( current_sessionID == update.get(j).getEventSessionID() )
				{
					toDelete = false;
					break;
				}
			}
			
			if( toDelete == true )
				ESH.deleteByEventSessionsID( String.valueOf(current_sessionID) );
		}
		
		int event_updated_row = EH.updateEvent(ev);
		int session_updated_row = ESH.updateSessions(update);
		JSONArray CreatedSessions = ESH.createByList( event_id , create );
		
		
		JSONObject res = new JSONObject();
		res.put("event_updated_row", event_updated_row );
		res.put("session_updated_row", session_updated_row );
		res.put("created_eventsessions", CreatedSessions);
		res.put("deleted_eventsessions", deleted_sessions);
		res.put("event_data" , EH.getByEventID(String.valueOf(event_id)) );
		
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "活動新增成功！");
        resp.put("response", res );
        
        jsr.response(resp, response);
	}
	
	//刪除活動(下架活動)
	//已完成:
	//待完成:下架活動
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		JsonReader jsr = new JsonReader( request );
		JSONObject jso = jsr.getObject();
		String event_id = jso.getString("event_id");
		
		JSONObject result = EH.deleteByEventID( event_id );
		
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "活動刪除成功！");
        resp.put("response", result );
        
        jsr.response(resp, response);
	}

}
