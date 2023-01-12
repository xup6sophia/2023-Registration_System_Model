

import org.json.*;

import java.sql.Timestamp;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Administrator
 * Member類別（class）具有會員所需要之屬性與方法，並且儲存與會員相關之商業判斷邏輯<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */


public class Administrator {
	private int administrator_id;
	private String name;
	private String email;
	private String hashedpassword;
	private String modified;
	private String created;
	private int isRoot;
	private int isDeleted;
	private String checkpw;
	
	private AdministratorHelper adh = AdministratorHelper.getHelper();
	
	/**
     * 實例化（Instantiates）一個新的（new）Manager物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改會員資料時，產生一名新的會員
     *
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param name 會員姓名
     */ 
    public Administrator(int administrator_id, String name, String email, int isRoot, String hashedpassword) {
    	this.administrator_id=administrator_id;
    	this.name = name;
    	this.email = email;
    	this.isRoot = isRoot;
    	this.hashedpassword = hashedpassword;
    }
	
    public Administrator(int administrator_id, String name, String email,  String hashedpassword) {
    	this.administrator_id=administrator_id;
    	this.name = name;
    	this.email = email;
    	
    	this.hashedpassword = hashedpassword;
    }
    
    public Administrator(int administrator_id, String name, String email, String hashedpassword,String checkpw) {
    	this.administrator_id=administrator_id;
    	this.name = name;
    	this.email = email;
    	this.hashedpassword = hashedpassword;
    	this.checkpw = checkpw;
    }
    
    public Administrator(int administrator_id, String name, String email) {
    	this.administrator_id=administrator_id;
    	this.name = name;
    	this.email = email;
    }
	
    //新增administrator的建構子
    public Administrator( String name, String email, String hashedpassword) {
    
    	this.name = name;
    	this.email = email;
    	this.hashedpassword = hashedpassword;
  
    }
    
    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("administrator_id", getAdministratorID());
        jso.put("name", getAdministratorName());
        jso.put("email", getAdministratorEmail());
        jso.put("isRoot", getAdministratorIsRoot());
        jso.put("hashedpassword", getAdministratorHashedpassword());
        return jso;
    }
    public JSONObject getLoginData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("administrator_id", getAdministratorID());
        jso.put("name", getAdministratorName());
        jso.put("email", getAdministratorEmail());
        
        jso.put("hashedpassword", getAdministratorHashedpassword());
        jso.put("checkpw", getCheckpw());
        return jso;
    }
    /**Get 方法**/

	public int getAdministratorID() {
		return this.administrator_id;
	}

	public String getAdministratorName() {
		return this.name;
	}

	public String getAdministratorEmail() {
		return this.email;
	}


	public String getAdministratorHashedpassword() {
		return this.hashedpassword;
	}
	
	public String getAdministratorModified() {
		return this.modified;
	}
	public String getAdministratorCreated() {
		return this.created;
	}
	
	public int getAdministratorIsRoot() {
		return this.isRoot;
	}
	
	public int getAdministratorIsDeleted() {
		return this.isDeleted;
	}
	
	public String getCheckpw(){
		return this.checkpw ;
	}
	
}
