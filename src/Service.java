import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

class Service {
	
	static private User currentUser = null;
    static private ArrayList<User> users = new ArrayList<User>();
    static private ArrayList<Meeting> meetings = new ArrayList<Meeting>();
    
	static public void init() {
        getUsersFromFile();
        getMeetingsFromFile();
	}
	
	static public void regis(String username, String password, String name) throws Exception {
		User.checkUsername(username);
		User.checkPassword(password);
		User.checkName(name);

		users.add(new User(username, password, name));
		saveUsersToFile();
		return;
		
	}
	
	static public void login(String username, String password) throws Exception {
		User.verifyUserInfo(username, password);
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				currentUser = users.get(i);
				return;
			}
		}
	}
	
	static public void createMeeting(String title, String beginDate, String endDate, ArrayList<User> participators) throws Exception {
		Meeting.verifyDate(beginDate, endDate);		
		Meeting.verifyUserDate(beginDate, endDate, participators, null);	
		Meeting.checkTitle(title, null);

		meetings.add(new Meeting(title, beginDate, endDate, participators, currentUser));
		saveMeetingsToFile();
		return;
	}
	
	static public ArrayList<Meeting> getMeetings(String searchTitle, String searchBeginDate, String searchEndDate, ArrayList<User> searchParticipators) throws ParseException {
		DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ArrayList<Meeting> result = new ArrayList<Meeting>();
		
		for (Meeting i : meetings) result.add(i);
		
		for (int i = 0; i < result.size(); i++) {
			if (!meetings.get(i).getTitle().contains(searchTitle)) {
				result.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < result.size(); i++) {
			long longBeginDate = timeFormat.parse(result.get(i).getBeginDate()).getTime();
			long longEndDate = timeFormat.parse(result.get(i).getEndDate()).getTime();
			long longSearchBeginDate = timeFormat.parse(searchBeginDate).getTime();
			long longSearchEndDate = timeFormat.parse(searchEndDate).getTime();
			if (longSearchEndDate < longBeginDate || longSearchBeginDate > longEndDate) {
				result.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < searchParticipators.size(); j++) {
				if (!searchParticipators.get(j).includes(result.get(i).getParticipators())) {
					result.remove(i);
					i--;
					break;
				}
			}
		}
		
		return result;
	}
	
	static public void editMeeting(Meeting meeting, String title, String beginDate, String endDate, ArrayList<User> participators) throws Exception {
		Meeting.checkTitle(title, meeting);	
		Meeting.verifyDate(beginDate, endDate);
		Meeting.verifyUserDate(beginDate, endDate, participators, meeting);
		
		meeting.setTitle(title);
		meeting.setBeginDate(beginDate);
		meeting.setEndDate(endDate);
		meeting.setParticipators(participators);
		saveMeetingsToFile();
		return;
	}

	static public void exitMeeting(Meeting meetingSelected) {
		ArrayList<User> editParticipators = new ArrayList<User>();

		for (User i : meetingSelected.getParticipators()) {
			editParticipators.add(i);
		}
		
		for (int i = 0; i < editParticipators.size(); i++) {
			if (editParticipators.get(i).getUsername().equals(currentUser.getUsername())) {
				editParticipators.remove(i);
				break;
			}
		}
		
		meetingSelected.setParticipators(editParticipators);
		saveMeetingsToFile();
	}
	
	static public boolean verifyIsHost(Meeting meetingSelected) {
		if (meetingSelected.getHost() == currentUser) return true;
		else return false;
	}
	
	static public boolean verifyInMeeting(Meeting meetingSelected) {
		if (currentUser.includes(meetingSelected.getParticipators())) return true;
		else return false;
	}
	
	static public ArrayList<User> getUsersList() {
		return users;
	}
	
	static public ArrayList<Meeting> getMeetingList() {
		return meetings;
	}
	
	static public void logout() {
		currentUser = null;
	}

    static private void getUsersFromFile() {
    	String readString = getProcessor("./storage/users.txt");
    	String[] readStringArray = readString.split(";");
		String setUsername = "";
		String setPassword = "";
		String setName = "";
		
    	for (int i = 0; i < readStringArray.length; i++) {
    		switch(i % 3) {
    			case 0: setUsername = readStringArray[i]; break;
    			case 1: setPassword = readStringArray[i]; break;
    			case 2: {
        			setName = readStringArray[i];
        			users.add(new User(setUsername, setPassword, setName));
        			break;
    			}
    		}
    	}
    }
    
    static private void getMeetingsFromFile() {
    	String readString = getProcessor("./storage/meetings.txt");
    	String[] readStringArray = readString.split(";");
    	String setTitle = "";
    	String setBeginDate = "";
    	String setEndDate = "";
    	ArrayList<User> setPaticipators = new ArrayList<>();
    	User setHost = null;
    	
    	for (int i = 0; i < readStringArray.length; i++) {	
    		switch(i % 5) {
    			case 0: setTitle = readStringArray[i]; break;
    			case 1: setBeginDate = readStringArray[i]; break;
    			case 2: setEndDate = readStringArray[i]; break;
    			case 3: {
        			setPaticipators = new ArrayList<>();
        			String[] readParticipatorsArray = readStringArray[i].split("/");
        			
        			for (int j = 0; j < readParticipatorsArray.length; j++) {
        				String setUsername = "已销户";
        				String setPassword = "已销户";
        				String setName = "已销户";				
        				
        				for (User k : users) {
        					if (k.getUsername().equals(readParticipatorsArray[j])) {
        						setUsername = k.getUsername();
        						setPassword = k.getPassword();
        						setName = k.getName();
        						break;
        					}
        				}
        				setPaticipators.add(new User(setUsername, setPassword, setName));
        			}
    			}
    			case 4: {
    				String setUsername = "已销户";
    				String setPassword = "已销户";
    				String setName = "已销户";
        			for (int k = 0; k < users.size(); k++) {
        				if (users.get(k).getUsername().equals(readStringArray[i])) {
    						setUsername = users.get(k).getUsername();
    						setPassword = users.get(k).getPassword();
    						setName = users.get(k).getName();
        					break;
        				}
        			}
        			setHost = new User(setUsername, setPassword, setName);
        			meetings.add(new Meeting(setTitle, setBeginDate, setEndDate, setPaticipators, setHost));
    			}
    		}
    	}
    }
    
    static private String getProcessor(String address) {
    	String readString = "";
    	File file = new File(address);
    	try {
	    	FileInputStream in = new FileInputStream(file);
	    	byte byt[] = new byte[10240];
	    	int len = in.read(byt);
	    	if (len == -1) len = 0;
	    	readString = new String(byt, 0, len);
	    	in.close();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return readString;
    }
    
    static private void saveUsersToFile() {
    	String writtenString = "";
    	for (User i : users) {
    		writtenString += i.getUsername() + ";"
    					   + i.getPassword() + ";"
    					   + i.getName() + ";";
    	}
    	
    	saveProcessor(writtenString, "./storage/users.txt");
    }
    
    static private void saveMeetingsToFile() {
    	String writtenString = "";
    	for (int i = 0; i < meetings.size(); i++) {
    		String participatorsUsername = "";
    		for (int k = 0; k < meetings.get(i).getParticipators().size(); k++) {
    			participatorsUsername = participatorsUsername.concat(meetings.get(i).getParticipators().get(k).getUsername() + "/");
    		} 		
    		writtenString += meetings.get(i).getTitle() + ";" 
    					   + meetings.get(i).getBeginDate() + ";"
    					   + meetings.get(i).getEndDate() + ";"
    					   + participatorsUsername + ";"
    					   + meetings.get(i).getHost().getUsername() + ";";
    	}
    	saveProcessor(writtenString, "./storage/meetings.txt");
    }
    
    static private void saveProcessor(String writtenString, String address) {
    	File file = new File(address);
    	try {
    		FileOutputStream out = new FileOutputStream(file);
    		byte buy[] = writtenString.getBytes();
    		out.write(buy);
    		out.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

}