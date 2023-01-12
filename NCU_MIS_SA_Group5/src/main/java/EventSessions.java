import java.sql.*;
import org.json.*;

public class EventSessions
{
	private int eventsessions_id;
	
	private int event_id;
	
	private String title;
	
	private int limitnum;
	
	private Timestamp start_date;
	
	private Timestamp end_date;
	
	private byte isCanceled;
	
	private Timestamp modified;
	
	private Timestamp created;
	
	// 查詢活動時使用
	public EventSessions( int eventsessions_id , int event_id , String title , int limitnum , Timestamp start_date , Timestamp end_date , byte isCanceled , Timestamp modified , Timestamp created )
	{
		this.eventsessions_id = eventsessions_id;
		this.event_id = event_id;
		this.title = title;
		this.limitnum = limitnum;
		this.start_date = start_date;
		this.end_date = end_date;
		this.isCanceled = isCanceled;
		this.modified = modified;
		this.created = created;
	}
	
	// 新增活動時使用
	public EventSessions( String title , int limitnum , Timestamp start_date , Timestamp end_date , byte isCanceled , Timestamp modified , Timestamp created )
	{
		this.title = title;
		this.limitnum = limitnum;
		this.start_date = start_date;
		this.end_date = end_date;
		this.isCanceled = isCanceled;
		this.modified = modified;
		this.created = created;
	}
	
	//更新活動時使用 - 未完成
	public EventSessions( int eventsessions_id , String title , int limitnum , Timestamp start_date , Timestamp end_date , byte isCanceled , Timestamp modified)
	{
		this.eventsessions_id = eventsessions_id;
		this.title = title;
		this.limitnum = limitnum;
		this.start_date = start_date;
		this.end_date = end_date;
		this.isCanceled = isCanceled;
		this.modified = modified;
	}
	
	public void setSessionID( int evetsessions_id )
	{ this.eventsessions_id = evetsessions_id ; }
	
	public int getEventSessionID()
	{ return this.eventsessions_id; }
	
	public int getEventID()
	{ return this.event_id; }
	
	public String getTitle()
	{ return this.title; }
	
	public int getLimitNum()
	{ return this.limitnum; }
	
	public Timestamp getStartDate()
	{ return this.start_date; }
	
	public Timestamp getEndDate()
	{ return this.end_date; }
	
	public byte checkCanceled()
	{ return this.isCanceled; }
	
	public Timestamp getModifiedDate()
	{ return this.modified; }
	
	public Timestamp getCreatedDate()
	{ return this.created; }
	
	public JSONObject getEventSessionsData()
	{
		JSONObject jso = new JSONObject();
		
		jso.put("eventsessions_id" , getEventSessionID());
		jso.put("event_id" , getEventID());
		jso.put("eventsessions_title" , getTitle());
		jso.put("eventsessions_limitnum" , getLimitNum() );
		jso.put("eventsessions_start_date" , getStartDate());
		jso.put("eventsessions_end_date" , getEndDate());
		jso.put("eventsessions_isCanceled" , checkCanceled());
		jso.put("eventsessions_modified" , getModifiedDate());
		jso.put("eventsessions_created" , getCreatedDate());
		
		return jso;
	}
}
