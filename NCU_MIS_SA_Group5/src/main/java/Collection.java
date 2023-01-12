public class Collection
{
	private int collection_id;
	
	private int event_id;
	
	private int member_id;
	
	//新增時使用
	public Collection( int event_id , int member_id )
	{
		this.event_id = event_id;
		this.member_id = member_id;
	}
	
	public void setCollectionID( int collection_id )
	{ this.collection_id = collection_id; }
	
	public int getCollectionID()
	{ return this.collection_id; }
	
	public int getEventID()
	{ return this.event_id; }
	
	public int getMemberID()
	{ return this.member_id; }
}
