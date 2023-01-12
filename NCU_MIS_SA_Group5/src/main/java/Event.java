import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import org.json.*;

public class Event
{
	private int event_id;
	
	private String title;
	
	private int eventtype_id;
	
	private String introduction;
	
	private String place;
	
	private Timestamp start_date;
	
	private Timestamp end_date;
	
	private String notification;
	
	private String imagePath;
	
	private byte isOutDated;
	
	private byte isCanceled;
	
	private Timestamp modified;
	
	private Timestamp created;
	
	private ArrayList<EventSessions> SessionList = new ArrayList<EventSessions>();
	
	private EventSessionsHelper ESH = EventSessionsHelper.getHelper();
	
	//查詢活動使用
	public Event( int event_id , String title , int eventtype_id , String introduction , String place , Timestamp start_date , Timestamp end_date , String notification , String imagePath , byte isOutDated , byte isCanceled , Timestamp modified , Timestamp created)
	{
		this.event_id = event_id;
		this.title = title;
		this.eventtype_id = eventtype_id;
		this.introduction = introduction;
		this.place = place;
		this.start_date = start_date;
		this.end_date = end_date;
		this.notification = notification;
		this.imagePath = imagePath;
		this.isOutDated = isOutDated;
		this.isCanceled = isCanceled;
		this.modified = modified;
		this.created = created;
		this.SessionList = ESH.getEventSessionByEventID(event_id);
	}
	
	//新增活動時使用
	public Event( String title , int eventtype_id , String introduction , String place , Timestamp start_date , Timestamp end_date , String notification , String imagePath )
	{
		this.title = title;
		this.eventtype_id = eventtype_id;
		this.introduction = introduction;
		this.place = place;
		this.start_date = start_date;
		this.end_date = end_date;
		this.notification = notification;
		this.imagePath = imagePath;
		this.modified = Timestamp.valueOf(LocalDateTime.now());
		this.created = this.modified;
		
		if( end_date.after(created) )
			this.isOutDated = 0;
		else
			this.isOutDated = 1;
		
		this.isCanceled = 0;
	}
	
	//更新活動時使用 - 未完成
	public Event( int event_id , String title , int eventtype_id , String introduction , String place , Timestamp start_date , Timestamp end_date , String notification , String imagePath , byte isCanceled , Timestamp modified )
	{
		this.event_id = event_id;
		this.title = title;
		this.eventtype_id = eventtype_id;
		this.introduction = introduction;
		this.place = place;
		this.start_date = start_date;
		this.end_date = end_date;
		this.notification = notification;
		this.imagePath = imagePath;
		
		if( end_date.after( Timestamp.valueOf(LocalDateTime.now()) ) )
			this.isOutDated = 0;
		else
			this.isOutDated = 1;
		
		this.isCanceled = isCanceled;
		this.modified = modified;
		this.SessionList = ESH.getEventSessionByEventID(event_id);
	}
	
	public void addEventSessions( EventSessions es )
	{ this.SessionList.add(es); }
	
	public void setEventID( int event_id )
	{ this.event_id = event_id; }
	
	public int getEventID()
	{ return this.event_id; }
	
	public String getTitle()
	{ return this.title; }
	
	public int getEventTypeID()
	{ return this.eventtype_id; }
	
	public String getIntroduction()
	{ return this.introduction; }
	
	public String getPlace()
	{ return this.place; }
	
	public Timestamp getStartDate()
	{ return this.start_date; }
	
	public Timestamp getEndDate()
	{ return this.end_date; }
	
	public String getNotification()
	{ return this.notification; }
	
	public String getImagePath()
	{ return this.imagePath; }
	
	public byte checkOutDated()
	{return this.isOutDated; }
	
	public byte checkCanceled()
	{ return this.isCanceled; }
	
	public Timestamp getModifiedDate()
	{ return this.modified; }
	
	public Timestamp getCreatedDate()
	{ return this.created; }
	
    public void setEventSessionsID(JSONArray data)
    {
        for(int i=0 ; i < this.SessionList.size() ; i++)
        {
            this.SessionList.get(i).setSessionID( data.getInt(i) );
        }
    }
	
	public ArrayList<EventSessions> getEventSessions()
	{ return this.SessionList; }
	
	public JSONObject getEventData()
	{
        JSONObject jso = new JSONObject();
        
        jso.put("event_id", getEventID());
        jso.put("event_title", getTitle());
        jso.put("eventtype_id", getEventTypeID());
        jso.put("event_introduction", getIntroduction());
        jso.put("event_place", getPlace());
        jso.put("event_start_date", getStartDate());
        jso.put("event_end_date", getEndDate());
        jso.put("event_notification", getNotification());
        jso.put("event_image", getImagePath());
        jso.put("event_isOutDated", checkOutDated());
        jso.put("event_isCanceled", checkCanceled());
        jso.put("event_modified", getModifiedDate());
        jso.put("event_created", getCreatedDate());

        return jso;
	}
	
	public JSONArray getSessionListData()
	{
		JSONArray jsa = new JSONArray();
		
		for( int i = 0 ; i < this.SessionList.size() ; i++ )
		{ jsa.put( this.SessionList.get(i).getEventSessionsData() ); }
		
		return jsa;
	}
	
	public JSONObject getAllData()
	{
		JSONObject jso = new JSONObject();
		
		jso.put("event_info", getEventData() );
		jso.put("eventsessions_info", getSessionListData() );
		
		return jso;
	}
}
