import java.util.ArrayList;
import java.util.Scanner;

class UI{
	static private Scanner scanner = new Scanner(System.in);
	
	static public void showLoginMenu() throws Exception {
		println("����п�·���У��������ϳ�����",
				"[L] ��¼",
				"[R] ע��"
				);
		while(true) {
			String choice = readLine().toUpperCase();
			switch (choice) {
				case "L": showLogin(); return;
				case "R": showRegister(); return;
				default: println("����ˣ�������");
			}
		}
	}
	
	static private void showLogin() throws Exception {
		String username;
		String password;
		
		while(true) {
			try {
				println("�û�����ɶ��");
				username = readLine();
				println("������ɶ��");
				password = readLine();
				
				User.verifyUserInfo(username, password);
				break;
			} catch(Exception e) {
				println(e);
			}
			println("����س����������룬�������������˵���");
			String leave = readLine().toUpperCase();
			if (leave.length() != 0) {
				showLoginMenu();
				return;
			}
			
		}

		Service.login(username, password);
		showMainMenu();
	}
	
	static private void showRegister() throws Exception {
		String username;
		String password;
		String name;

		while(true) {
			try {
				println("�����˺�����");
				username = readLine();	
				
				User.checkUsername(username);
				break;
			} catch(Exception e) {
				println(e);
			}
			
			println("���س������������������������˵�");
			String leave = readLine().toUpperCase();
			if (leave.length() != 0) {
				showLoginMenu();
				return;
			}
		}
		
		while(true) {
			try {
				println("������������");
				password = readLine();
				
				User.checkPassword(password);
				break;
			} catch(Exception e) {
				println(e);
			}
			readLine();
		}
		
		while(true) {
			try {
				println("������������");
				name = readLine();
				
				User.checkName(name);
				break;
			} catch (Exception e) {
				println(e);
			}
			readLine();
		}
		
		Service.regis(username, password, name);
		println("�㶨��Ҫ��ת�ˣ���");
		readLine();
		showLoginMenu();
		return;
	}
	
	static private void showMainMenu() throws Exception {
		println("",
				"[C] ��������",
				"[R] �鿴����",
				"[O] ע��"
				);
		
		while(true) {
			String choice = readLine().toUpperCase();
			switch(choice) {
				case "C": showCreateMeeting(); return;
				case "R": showMeetings("", "1970-01-01 00:00", "2050-01-01 00:00", new ArrayList<User>()); return;
				case "O": showLogout(); return;
				default: println("����ˣ�������");
			}
		}
	}
	
	static private void showCreateMeeting() throws Exception {
		String title;
		String beginDate;
		String endDate;
		ArrayList<User> participators = new ArrayList<User>();
		
		while(true) {
			try {
				println("�ĸ��������֡���");
				title = readLine();
				
				Meeting.checkTitle(title, null);
				break;
			} catch(Exception e) {
				println(e);
			}
		}
		
		while(true) {
			try {
				println("ʲôʱ��ʼ��(YYYY-MM-DD HH-mm)");
				beginDate = readLine();
				println("ʲôʱ�������(YYYY-MM-DD HH-mm)");
				endDate = readLine();
				
				Meeting.verifyDate(beginDate, endDate);
				break;
			} catch (Exception e) {
				println(e);
			}
		}
		
		while(true) {
			try {
				println("��Щ�ˡ�");
				participators = showUserList();
				
				Meeting.verifyUserDate(beginDate, endDate, participators, null);
				break;
			} catch(Exception e) {
				println(e);
			}
		}

		Service.createMeeting(title, beginDate, endDate, participators);
		println("�㶨��Ҫ��ȥ�ˣ�");
		readLine();
		showMainMenu();
		return;
	}
	
	static private void showMeetings(String searchTitle, String searchBeginDate, String searchEndDate, ArrayList<User> searchParticipators) throws Exception {
		ArrayList<Meeting> printMeetingsList = new ArrayList<Meeting>();
		printMeetingsList = Service.getMeetings(searchTitle, searchBeginDate, searchEndDate, searchParticipators);
		
		if (printMeetingsList.size() == 0) {
			println("",
					"û�ҵ���",
					"[S] �ı���������",
					"[�����] �ص����˵�"
					);
			String choice = readLine().toUpperCase();
			switch(choice) {
				case "S": showSearchMenu(searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				default: showMainMenu(); return;
			}
		}
		
		for (int i = 0; i < printMeetingsList.size(); i++) {
			println("[" + i + "] ���⣺" + printMeetingsList.get(i).getTitle() + " ��ʼʱ�䣺" + printMeetingsList.get(i).getBeginDate());
		}
		println("",
				"ѡ��һ������",
				"[R] �鿴���������Ϣ",
				"[S] ��������",
				"[M] �ص����˵�"
				);
		while(true) {
		String choice = readLine().toUpperCase();
			switch(choice) {
				case "R": showMeetingDetail(printMeetingsList, searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				case "S": showSearchMenu(searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				case "M": showMainMenu(); return;
				default: println("�������������");
			}
		}
	}
	
	static private void showSearchMenu(String searchTitle, String searchBeginDate, String searchEndDate, ArrayList<User> searchParticipators) throws Exception {
		while(true) {
			println("",
					"[T] ������������",
					"[D] ����ʱ�䷶Χ",
					"[P] ���òλ���",
					"[S] ��ʼ����"
					);
		
			String choice = readLine().toUpperCase();
			switch(choice) {
				case "T": {
					println("�����������");
					searchTitle = readLine();
					
					println("�㶨����һ����");
					readLine();
					break;
				}
				case "D": {
					while(true) {
						try {
							println("���뿪ʼʱ�䣺");
							searchBeginDate = readLine();
							println("�������ʱ��");
							searchEndDate = readLine();
							Meeting.verifyDate(searchBeginDate, searchEndDate);
							break;
						} catch(Exception e) {
							println(e);
						}
					}
					println("�㶨����һ����");
					readLine();
					break;
				}
				case "P" : {
					searchParticipators = showUserList();
					println("�㶨����һ����");
					readLine();
					break;
				}
				
				case "S": {
					showMeetings(searchTitle, searchBeginDate, searchEndDate, searchParticipators);
					return;
				}
				
				default: println("��ѽ�ֻ��ˣ�");
			}
		}
	}
	
	static private void showMeetingDetail(ArrayList<Meeting> printMeetingList, String searchTitle, String searchBeginDate, String searchEndDate, ArrayList<User> searchParticipators) throws Exception {
		println("��ѡ�������");
		Meeting meetingSelected;
		while(true) {
			int choice = readInt();
			if (choice >= 0 && choice < printMeetingList.size()) {
				meetingSelected = printMeetingList.get(choice);
				break;
			} else {
				println("û���ţ�������");
			}
		}
		
		String participatorsList = "";
		for (User i : meetingSelected.getParticipators()) {
			participatorsList += i.getName() + ";";
		}
			
		println("",
				"���⣺" + meetingSelected.getTitle(),
				"��ʼ���ڣ�" + meetingSelected.getBeginDate(),
				"�������ڣ�" + meetingSelected.getEndDate(),
				"�μ��ˣ�" + participatorsList,
				"host: " + meetingSelected.getHost().getName()
				);
		
		println("",
				"��ѡ��һ������",
				"[E] �޸Ĵ˻���",
				"[R] ���ػ����б�",
				"[M] �������˵�"
				);
		
		while(true) {
			String choice = readLine().toUpperCase();
			switch(choice) {
				case "E": showEditMeeting(meetingSelected, searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				case "R": showMeetings(searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				case "M": showMainMenu(); return;
				default: println("��������������");
			}
		}
	}
	
	static private void showEditMeeting(Meeting meetingSelected, String searchTitle, String searchBeginDate, String searchEndDate, ArrayList<User> searchParticipators) throws Exception {
		String title = meetingSelected.getTitle();
		String beginDate = meetingSelected.getBeginDate();
		String endDate = meetingSelected.getEndDate();
		ArrayList<User> participators = meetingSelected.getParticipators();
		
		boolean isHost = Service.verifyIsHost(meetingSelected);
		boolean inMeeting = Service.verifyInMeeting(meetingSelected);
		if (isHost) {
			while(true) {
				println("",
						"[T] �޸ı���",
						"[D] �޸�����",
						"[P] �޸Ĳλ���",
						"[R] ���沢�����༭"
						);
		
				String choice = readLine().toUpperCase();
				switch(choice) {
				
					case "T": {
						while(true) {
							try {
								println("�ĸ�������������:");
								title = readLine();					
								Meeting.checkTitle(title, meetingSelected);
								break;
								
							} catch(Exception e) {
								println(e);
							}
						}
						println("�㶨����һ����");
						break;
					}
					
					case "D": {
						while(true) {
							try {
								println("ʲôʱ��ʼ��");
								beginDate = readLine();
								println("ʲôʱ�������");
								endDate = readLine();
								Meeting.verifyDate(beginDate, endDate);
								Meeting.verifyUserDate(beginDate, endDate, participators, meetingSelected);
								break;
							} catch(Exception e) {
								println(e);
							}
						}
						println("�㶨����һ����");
						break;
					}
					
					case "P": {
						while(true) {
							try {
								participators = showUserList();
								Meeting.verifyUserDate(beginDate, endDate, participators, meetingSelected);
								break;
							} catch(Exception e) {
								println(e);
							}
						}
						println("�㶨����һ����");
						break;
					}
					
					case "R":{
						Service.editMeeting(meetingSelected, title, beginDate, endDate, participators);
						println("�㶨��");
						readLine();
						showMainMenu();
						return;
					}
				}
			}
		} else {
			if (inMeeting) {
				println("��û��Ȩ���޸Ļ�����Ϣ",
						"[Y] �˳�����",
						"[�����] ���ػ����б�"
						);
				String choice = readLine().toUpperCase();
				switch(choice) {
					case "Y": {
						Service.exitMeeting(meetingSelected); 
						println("�ɹ������");
						readLine();
						showMeetings(searchTitle, searchBeginDate, searchEndDate, searchParticipators); 
						return;
					}
					
					default: {
						showMeetings(searchTitle, searchBeginDate, searchEndDate, searchParticipators); 
						return;
					}
				}
			} else {
				println("��û��Ȩ���޸Ļ�����Ϣ",
						"[�����] ���ػ����б�"
						);
				readLine();
				showMeetings(searchTitle, searchBeginDate, searchEndDate, searchParticipators); 
				return;
			}
		}
	}
	
	static private ArrayList<User> showUserList() {
		ArrayList<User> users = Service.getUsersList();
		ArrayList<User> result = new ArrayList<User>();
		for (int i = 0; i < users.size(); i++) {
			println("[" + i + "]" + users.get(i).getName());
		}
		
		println("");
		while(true) {
			println("�������û���ţ�����-1��ʾ����");
			int choice = readInt();
			if (choice == -1) break;	
			else if (choice >= 0 && choice < users.size()) {
				if (users.get(choice).includes(result)){
					println("����Ѿ����б����ˡ�");
				} else {
					result.add(users.get(choice));
					println("��ӳɹ���");
				}
			} else {
				println("��Ŵ���ȥȷ��һ��");
			}
		}
		return result;
	}
	
	static private void showLogout() throws Exception {
		Service.logout();
		println("�ߺò��ͣ�");
		readLine();
		showLoginMenu();
	}
	
	static private void println(Object... input) {
		for (Object i : input) {
			System.out.println(i);
		}
	}
	
	static private String readLine() {
		String result = scanner.nextLine();
		return result;
	}
	
	static private int readInt() {
		int result;
		while(true) {
			try {
				result = scanner.nextInt();
				break;
			} catch (Exception e) {
				System.out.println("�դ�����ʤǤ��ֻ���������֣�");
				scanner.nextLine();
			}
		}
		scanner.nextLine();
		return result;
	}
}