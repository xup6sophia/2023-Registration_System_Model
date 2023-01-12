import java.sql.*;
import java.time.LocalDateTime;

import org.json.JSONObject;

public class SessionMemberDetail
{
	private int sessionmemberdetail_id;
	private int member_id;
	private int eventsessions_id;
	private Timestamp modified;
	private Timestamp created;
	private int applystatus_id;
	
	//查詢時使用
	public SessionMemberDetail( int sessionmemberdetail_id , int member_id , int eventsessions_id , Timestamp modified , Timestamp created , int applystatus_id )
	{
		this.sessionmemberdetail_id = sessionmemberdetail_id;
		this.member_id = member_id;
		this.eventsessions_id = eventsessions_id;
		this.modified = modified;
		this.created = created;
		this.applystatus_id = applystatus_id;
	}
	
	//新增時使用
	public SessionMemberDetail( int member_id , int eventsessions_id , int applystatus_id )
	{
		this.member_id = member_id;
		this.eventsessions_id = eventsessions_id;
		this.modified = Timestamp.valueOf(LocalDateTime.now());
		this.created = this.modified;
		this.applystatus_id = applystatus_id;
	}
	
	public int getSessionMemberDetailID()
	{ return this.sessionmemberdetail_id; }
	
	public int getMemberID()
	{ return this.member_id; }
	
	public int getEventSessionsID()
	{ return this.eventsessions_id; }
	
	public Timestamp getModified()
	{ return this.modified; }
	
	public Timestamp getCreated()
	{ return this.created; }
	
	public int getApplyStatusID()
	{ return this.applystatus_id; }
	
	public JSONObject getSessionMemberDetailData()
	{
		JSONObject jso = new JSONObject();
		
		jso.put("sessionmemberdetail_id", this.sessionmemberdetail_id);
		jso.put("member_id", this.member_id);
		jso.put("eventsessions_id", this.eventsessions_id);
		jso.put("sessionmemberdetail_modified", this.modified);
		jso.put("sessionmemberdetail_created", this.created);
		jso.put("applystatus_id", this.applystatus_id);
		
		return jso;
	}
}
