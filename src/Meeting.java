import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

class Meeting {
    private String title;
    private String beginDate;
    private String endDate;
    private ArrayList<User> participators;
    private User host;

    Meeting(String title, String beginDate, String endDate, ArrayList<User> participators, User host) {
    	this.title = title;
    	this.beginDate = beginDate;
    	this.endDate = endDate;
    	this.participators = participators;
    	this.host = host;
    }

    public String getTitle() {
    	return this.title;
    }
    
    public String getBeginDate() {
    	return this.beginDate;
    }
    
    public String getEndDate() {
    	return this.endDate;
    }
    
    public ArrayList<User> getParticipators() {
    	return this.participators;
    }
    
    public User getHost() {
    	return this.host;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public void setBeginDate(String beginDate) {
    	this.beginDate = beginDate;
    }
    
    public void setEndDate(String endDate) {
    	this.endDate = endDate;
    }
    
    public void setParticipators(ArrayList<User> participators) {
    	this.participators = participators;
    }

    static public Object checkTitle(String title, Meeting meetingSelected) throws Exception {
    	if (meetingSelected != null) {
    		if (meetingSelected.getTitle().equals(title)) return null;
    	}
    	if (title.length() > 30) throw new Exception("不要那么长的啦，12位就够了…");
    	if (title.length() == 0) throw new Exception("标题不能为空！");
    	
    	ArrayList<Meeting> meetings = Service.getMeetingList();

    	for (Meeting i : meetings) {
    		if (i.getTitle().equals(title)) throw new Exception("开个会儿都要抢着开啊…别人已经用了这标题了！");
    	}
    	return null;
    }

    @SuppressWarnings("unused")
	static public Object verifyDate(String beginDate, String endDate) throws Exception {
    	DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	try {
			long longBeginDate = timeFormat.parse(beginDate).getTime();
			long longEndDate = timeFormat.parse(endDate).getTime();
    	} catch (Exception e) {
    		throw new Exception("时间格式错误！");
    	}
    	return null;
    }

    static public Object verifyUserDate(String beginDate, String endDate, ArrayList<User> participators, Meeting meetingSelected) throws Exception {
    	DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	ArrayList<Meeting> meetings = Service.getMeetingList();
    	ArrayList<Meeting> meetingsDeal = new ArrayList<Meeting>();
    	ArrayList<User> result = new ArrayList<User>();
    	
    	for (Meeting i : meetings) {
    		if (meetingSelected != null) {
    			if (!meetingSelected.getTitle().equals(i.getTitle())) {
    				meetingsDeal.add(i);
    			}
    		} else meetingsDeal.add(i);
    	}

    	for (int i = 0; i < meetingsDeal.size(); i++) {
    		long longBeginDate = timeFormat.parse(beginDate).getTime();
    		long longEndDate = timeFormat.parse(endDate).getTime();
    		long meetingBeginDate = timeFormat.parse(meetingsDeal.get(i).getBeginDate()).getTime();
    		long meetingEndDate = timeFormat.parse(meetingsDeal.get(i).getEndDate()).getTime();
    		
    		if (longBeginDate > meetingEndDate || longEndDate < meetingBeginDate) {
    			meetingsDeal.remove(i);
    			i--;
    		}
    		
    	} 	
	
    	for (Meeting i : meetingsDeal) {
    		for (User j : participators) {
    			if (j.includes(i.getParticipators())){
    				result.add(j);
    			}
    		}
    	}

    	if (result.size() == 0) return null;
    	else {
    		String showException = "";
    		for (User i : result) {
    			showException = showException + "用户" + i.getName() + "已经参加了同时间的其他会议！\n";
    		}
    		throw new Exception(showException);
    	}
    }



}