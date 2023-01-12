
import org.json.*;

import java.sql.Timestamp;

import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Member
 * Member類別（class）具有會員所需要之屬性與方法，並且儲存與會員相關之商業判斷邏輯<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Member {
    
    /** id，會員編號 */
    private int member_id;
    
    /** name，會員姓名 */
    private String member_name;
    
    /** email，會員電子郵件信箱 */
    private String member_email;
    
    /** password，會員密碼 */
    private String member_hashedpassword;
    
    /** user_moodified，user更新時間的分鐘數 */
    private  Timestamp member_usermodified;
    
    /** administrator_moodified，administrator更新時間的分鐘數 */
    private Timestamp member_administratormodified;
    
    /** created，user建立時間的分鐘數 */
    private Timestamp member_created;
    
    /** is_deleted，會員是否刪除 */
    private int member_isDeleted;
    
    private String checkpw;
    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper mh =  MemberHelper.getHelper();
    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於login
     *
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param name 會員姓名
     */ 
    
    public Member(int member_id, String member_hashedpassword) {
    	this.member_id = member_id;
    	this.member_hashedpassword = member_hashedpassword;
    }
    public Member(int member_id, String member_name, String member_email) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_email = member_email;
        
    }
    public Member(int member_id, String member_name, String member_email,String member_hashedpassword) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_email = member_email;
        this.member_hashedpassword = member_hashedpassword;
        
    }
    
    public Member(int member_id, String member_name, String member_email, String member_hashedpassword,int member_isDeleted,String checkpw) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_email = member_email;
        this.member_hashedpassword = member_hashedpassword;
        this.member_isDeleted=member_isDeleted;
        this.checkpw= checkpw;
    }
    
    

    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立會員資料時
     * 
     * @param id 會員編號
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param name 會員姓名
     */
    public Member(String member_name, String member_email, String member_hashedpassword) {
        //this.member_id = member_id;
        this.member_email = member_email;
        this.member_hashedpassword = member_hashedpassword;
        this.member_name = member_name;
        this.member_isDeleted = 0;
    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新會員資料時
     * 
     * @param id 會員編號
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param name 會員姓名
     */
    
    

    
    public Member(int member_id,String member_name,String member_email,Timestamp member_created,Timestamp member_usermodified,Timestamp administratormodified, int member_isDeleted) {
    	this.member_id = member_id;
    	this.member_name = member_name;
    	this.member_email=member_email;
    	this.member_created = member_created;
    	this.member_usermodified = member_usermodified;
    	this.member_administratormodified= administratormodified;
    	this.member_isDeleted=member_isDeleted;
    	
    }
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢會員資料時，將每一筆資料新增為一個會員物件
     *
     * @param id 會員編號
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param name 會員姓名
     * @param login_times 更新時間的分鐘數
     * @param status the 會員之組別
     */
    public Member(int member_id,String member_name,String member_email, String member_hashedpassword,int member_isDeleted) {
    	this.member_id = member_id;
    	this.member_name = member_name;
    	
    	this.member_email=member_email;
    	this.member_hashedpassword = member_hashedpassword;
    	this.member_isDeleted=member_isDeleted;
    	
    }


    
    
    /**
     * 更新會員資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("member_id", getID());
        jso.put("member_name", getName());
        jso.put("member_email", getEmail());
        jso.put("member_created", getCreated());
        jso.put("member_usermodified", getUserModified());
        jso.put("member_administratormodified", getAdministratorModified());
        jso.put("member_hashedpassword", getMemberhashedpassword());
		jso.put("member_isDeleted",getIsDeleted());

        return jso;
    }
    
    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getLoginData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("member_id", getID());
        jso.put("member_name", getName());
        jso.put("member_email", getEmail());
        jso.put("member_hashedpassword", getMemberhashedpassword());
        jso.put("member_isDeleted", getIsDeleted());
        jso.put("checkpw", getCheckpw());
        
        return jso;
    }
    
       /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public int getID() {
        return this.member_id;
    }

    /**
     * 取得會員之電子郵件信箱
     *
     * @return the email 回傳會員電子郵件信箱
     */
    public String getEmail() {
        return this.member_email;
    }
    
    /**
     * 取得會員之姓名
     *
     * @return the name 回傳會員姓名
     */
    public String getName() {
        return this.member_name;
    }

    /**
     * 取得會員之密碼
     *
     * @return the password 回傳會員密碼
     */
    public String getMemberhashedpassword() {
        return this.member_hashedpassword;
    }
    
    /**
     * 取得更新資料時間之分鐘數
     *
     * @return the login times 回傳更新資料時間之分鐘數
     */
    public Timestamp getUserModified() {
        return this.member_usermodified;
    }
    public Timestamp getAdministratorModified() {
        return this.member_administratormodified;
    }
    
    public Timestamp getCreated() {
    	return this.member_created;
    }
    
    public int getIsDeleted() {
        return this.member_isDeleted;
    }
    public String getCheckpw() {
        return this.checkpw;
    }
    
   
}