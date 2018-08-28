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
    			if (!i.getPassword().equals(password)) throw new Exception("�������");
    			break;
    		}
    	}
    	
    	if (!found) throw new Exception("�û���������!");
    	else return null;
    }

    static public Object checkUsername(String username) throws Exception {
    	ArrayList<User> users = Service.getUsersList();
    	
    	if (username.length() > 12) throw new Exception("�û���������������12λ����");
    	if (username.length() == 0) throw new Exception("�û�������Ϊ�գ�");
    	for (User i : users) {
    		if (i.getUsername().equals(username)) throw new Exception("�û�����ռ���ˣ�");
    	}
    	return null;
    }

    static public Object checkPassword(String password) throws Exception {
    	if (password.length() > 12) throw new Exception("���������������12λ����");
    	if (password.length() == 0) throw new Exception("���벻��Ϊ�գ�");
    	return null;
    }
    
    static public Object checkName(String name) throws Exception {
    	if (name.length() > 8) throw new Exception("���ֹ�����������8�ֽڣ���");
    	if (name.length() == 0) throw new Exception("���ֲ���Ϊ�գ�");
    	return null;
    }
}