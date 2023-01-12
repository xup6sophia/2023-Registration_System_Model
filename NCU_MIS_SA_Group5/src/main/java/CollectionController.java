import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.*;

@WebServlet("/api/collection.do")
public class CollectionController extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private CollectionHelper CH = CollectionHelper.getHelper();
       
    public CollectionController()
    {
    	super();
    }
    
    //加入收藏
  	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  	{
  		System.out.print("有到Post");
  		JsonReader jsr = new JsonReader(request);
  		JSONObject jso = jsr.getObject();
  		
  		int event_id = jso.getInt("event_id");
  		int member_id = jso.getInt("member_id");
  		
  		Collection c = new Collection( event_id , member_id );
  		
  		JSONObject result = CH.createCollection( c );
  		c.setCollectionID( result.getInt("collection_id") );
  		
  		JSONObject resp = new JSONObject();
  		
          resp.put("status", "200");
          resp.put("message", "活動收藏成功!");
          resp.put("response", result );
          
          jsr.response(resp, response);
  	}
    
    //取得收藏
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		JsonReader jsr = new JsonReader(request);
		String member_id = jsr.getParameter("member_id");
		
		JSONObject queryResult = CH.getAllDataByMemberID(member_id);
		
		JSONObject resp = new JSONObject();
		resp.put( "status" , 200 );
		resp.put( "message" , "收藏取得成功!" );
		resp.put( "response" , queryResult );
		
		jsr.response( resp, response );
	}

	

	//取消收藏
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		JsonReader jsr = new JsonReader(request);
		JSONObject jso = jsr.getObject();
		
		String collection_id = jso.getString("collection_id");
		System.out.printf("CollectionID:%s",collection_id);
		JSONObject result = CH.deleteByCollectionID(collection_id);
		
		JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "收藏刪除成功!");
        resp.put("response", result );
        
        jsr.response(resp, response);
	}
}
