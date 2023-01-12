import java.io.Console;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class sessionmemberdetail
 */

@WebServlet("/api/sessionmemberdetail.do")
public class SessionMemberDetailController extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public SessionMemberDetailController()
    {
        super();
    }

	private SessionMemberDetailHelper SMDH = SessionMemberDetailHelper.getHelper();
       
	//查看已報名的活動
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		JsonReader jsr = new JsonReader(request);
		String member_id = jsr.getParameter("member_id");
		
		JSONObject queryResult = SMDH.getAllDataByMemberID(member_id);
		
		JSONObject resp = new JSONObject();
		resp.put( "status" , 200 );
		resp.put( "message" , "報名活動取得成功!" );
		resp.put( "response" , queryResult );
		
		jsr.response( resp, response );
	}

	//報名活動
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("有到這");
		JsonReader jsr = new JsonReader(request);
		JSONObject jso = jsr.getObject();
		
		int member_id = jso.getInt("member_id");
		int eventsessions_id = jso.getInt("eventsessions_id");
		int applystatus_id = jso.getInt("applystatus_id");
		
		SessionMemberDetail smd = new SessionMemberDetail( member_id , eventsessions_id , applystatus_id );
		
		if (!SMDH.checkDuplicate(smd)) {
			JSONObject result = SMDH.createSessionMemberDetail(smd);
		
			JSONObject resp = new JSONObject();
			resp.put( "status" , 200 );
			resp.put( "message" , "報名成功!" );
			resp.put( "response" , result );
		
			jsr.response( resp, response );
			System.out.println(resp);
		}else {
			/** 以字串組出JSON格式之資料 */
			String resp = "{\"status\": \'400\', \"message\": \'報名活動失敗，已報名此活動！\', \'response\': \'\'}";
			/** 透過JsonReader物件回傳到前端（以字串方式） */
			jsr.response(resp, response);
			System.out.println(resp);
		}
	}
	
	//取消報名活動
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		JsonReader jsr = new JsonReader(request);
		JSONObject jso = jsr.getObject();
		String sessionmemberdetail_id = jso.getString("sessionmemberdetail_id");
		
		JSONObject result = SMDH.deleteBySessionMemberDetailID( sessionmemberdetail_id );
		
		JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "報名活動刪除成功!");
        resp.put("response", result );
        
        jsr.response(resp, response);
	}
}
