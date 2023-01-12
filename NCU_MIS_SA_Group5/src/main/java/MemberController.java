import java.io.Console;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import java.security.NoSuchAlgorithmException;

@WebServlet("/api/member.do")
public class MemberController extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public MemberController() {
		super();

	}

	/** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
	private MemberHelper mh = MemberHelper.getHelper();

	/**
	 * 處理Http Method請求POST方法（新增資料）
	 *
	 * @param request  Servlet請求之HttpServletRequest之Request物件（前端到後端）
	 * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { /* 透過JsonReader類別將Request之JSON格式資料解析並取回 */
		JsonReader jsr = new JsonReader(request);
		JSONObject jso = jsr.getObject();

		/** 取出經解析到JSONObject之Request參數 */

		String account = jso.getString("account");
		String name = jso.getString("name");
		String password = jso.getString("password");
		
		// 建立新會員

		/** 透過MemberHelper物件的checkDuplicate()會員學號是否有重複 */
		if (name.isEmpty()) {

			JSONObject query = mh.getByEmail(account,password);
			
			
			
			JSONObject resp = new JSONObject();
			resp.put("status", "200");
			resp.put("message", "會員資料取得成功");
			resp.put("response", query);
			//resp.put("password", matched);
			
			System.out.println(resp);

			jsr.response(resp, response);
		} else {
			Member m = new Member(name, account, password);

			if (!mh.checkDuplicate(m)) {
				/** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
				JSONObject data = mh.create(m);
				JSONObject resp = new JSONObject();

				/** 新建一個JSONObject用於將回傳之資料進行封裝 */

				resp.put("status", "200");
				resp.put("message", "成功! 註冊會員資料...");
				resp.put("response", data);
				

				/** 透過JsonReader物件回傳到前端（以JSONObject方式） */
				jsr.response(resp, response);
				System.out.println(resp);
			} else {
				/** 以字串組出JSON格式之資料 */
				String resp = "{\"status\": \'400\', \"message\": \'新增帳號失敗，此學號已註冊！\', \'response\': \'\'}";
				/** 透過JsonReader物件回傳到前端（以字串方式） */
				jsr.response(resp, response);
			}
		}

	}

	/**
	 * 處理Http Method請求GET方法（取得資料）
	 *
	 * @param request  Servlet請求之HttpServletRequest之Request物件（前端到後端）
	 * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
		JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String member_id = jsr.getParameter("member_id");
        
        /** 判斷該字串是否存在，若存在代表要取回個別管理員之資料，否則代表要取回全部資料庫內管理員之資料 */
        if (member_id.isEmpty()) {
            /** 透過MemberHelper物件之getAll()方法取回所有會員之資料，回傳之資料為JSONObject物件 */
            JSONObject query = mh.getAll();
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有管理員資料取得成功");
            resp.put("response", query);
    
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
        	
        	System.out.println("有到else");
            /** 透過MemberHelper物件的getByID()方法自資料庫取回該名會員之資料，回傳之資料為JSONObject物件 */
            JSONObject query = mh.getByID(member_id);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "管理員資料取得成功");
            resp.put("response", query);
    
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
	}

	/**
	 * 處理Http Method請求DELETE方法（刪除）
	 *
	 * @param request  Servlet請求之HttpServletRequest之Request物件（前端到後端）
	 * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */
	        int member_id = jso.getInt("member_id");
	        
	        /** 取出經解析到JSONObject之Request參數 */
	        //String user_id = jsr.getParameter("user_id");
	        System.out.println(member_id);
	        
	        /** 透過ManagerHelper物件的deleteByID()方法至資料庫刪除該名管理員，回傳之資料為JSONObject物件 */
	        JSONObject query = mh.deleteByID(member_id);
	        
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "管理員移除成功！");
	        resp.put("response", query);

	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	    }

	/**
	 * 處理Http Method請求PUT方法（更新）
	 *
	 * @param request  Servlet請求之HttpServletRequest之Request物件（前端到後端）
	 * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	public void doPut(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */
	        int member_id =jso.getInt("member_id");
	        System.out.println(member_id);
	        String email = jso.getString("email");
	        
	        String name = jso.getString("user_name");
	        System.out.println(name);
	        String password = jso.getString("password");

	        /** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
	        Member m = new Member(member_id, name, email, password);
	        
	        JSONObject data = mh.updateByID(m);
	        
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "成功! 更新會員資料...");
	        resp.put("response", data);
	        
	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	    }

	}

