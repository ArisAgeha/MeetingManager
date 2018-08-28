import java.util.ArrayList;

class User {
    private String username;
    private String password;
    private String name;

    User(String username, String password, String name) {
    	this.username = username;
    	this.password = password;
    	this.name = name;
    }

    public String getUsername() {
    	return this.username;
    }
    
    public String getPassword() {
    	return this.password;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public boolean includes(ArrayList<User> judgeList) {
    	for (User i : judgeList) {
    		if (i.getUsername() == this.username) return true;
    	}
    	
    	return false;
    }
    
    static public Object verifyUserInfo(String username, String password) throws Exception {
    	ArrayList<User> users = Service.getUsersList();
    	boolean found = false;
    	for (User i : users) {
    		if (i.getUsername().equals(username)) {
    			found = true;
    			if (!i.getPassword().equals(password)) throw new Exception("密码错误！");
    			break;
    		}
    	}
    	
    	if (!found) throw new Exception("用户名不存在!");
    	else return null;
    }

    static public Object checkUsername(String username) throws Exception {
    	ArrayList<User> users = Service.getUsersList();
    	
    	if (username.length() > 12) throw new Exception("用户名过长（不超过12位）！");
    	if (username.length() == 0) throw new Exception("用户名不能为空！");
    	for (User i : users) {
    		if (i.getUsername().equals(username)) throw new Exception("用户名被占用了！");
    	}
    	return null;
    }

    static public Object checkPassword(String password) throws Exception {
    	if (password.length() > 12) throw new Exception("密码过长（不超过12位）！");
    	if (password.length() == 0) throw new Exception("密码不能为空！");
    	return null;
    }
    
    static public Object checkName(String name) throws Exception {
    	if (name.length() > 8) throw new Exception("名字过长（不超过8字节）！");
    	if (name.length() == 0) throw new Exception("名字不能为空！");
    	return null;
    }
}