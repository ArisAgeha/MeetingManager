import java.util.ArrayList;
import java.util.Scanner;

class UI{
	static private Scanner scanner = new Scanner(System.in);
	
	static public void showLoginMenu() throws Exception {
		println("嚥凪湖信揃佃佩��音泌瀧貧竃窟。",
				"[L] 鞠村",
				"[R] 廣過"
				);
		while(true) {
			String choice = readLine().toUpperCase();
			switch (choice) {
				case "L": showLogin(); return;
				case "R": showRegister(); return;
				default: println("補危阻�《拈苅�");
			}
		}
	}
	
	static private void showLogin() throws Exception {
		String username;
		String password;
		
		while(true) {
			try {
				println("喘薩兆頁俵��");
				username = readLine();
				println("畜鷹頁俵��");
				password = readLine();
				
				User.verifyUserInfo(username, password);
				break;
			} catch(Exception e) {
				println(e);
			}
			println("補秘指概囚嶷仟補秘��凪麿囚獄指麼暇汽。");
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
				println("補秘嬲催晴��");
				username = readLine();	
				
				User.checkUsername(username);
				break;
			} catch(Exception e) {
				println(e);
			}
			
			println("梓指概囚写偬��梓凪麿囚獄指麼暇汽");
			String leave = readLine().toUpperCase();
			if (leave.length() != 0) {
				showLoginMenu();
				return;
			}
		}
		
		while(true) {
			try {
				println("補秘畜鷹晴��");
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
				println("補秘兆忖晴��");
				name = readLine();
				
				User.checkName(name);
				break;
			} catch (Exception e) {
				println(e);
			}
			readLine();
		}
		
		Service.regis(username, password, name);
		println("吾協�〈�柳廬阻�。�");
		readLine();
		showLoginMenu();
		return;
	}
	
	static private void showMainMenu() throws Exception {
		println("",
				"[C] 幹秀氏咏",
				"[R] 臥心氏咏",
				"[O] 廣��"
				);
		
		while(true) {
			String choice = readLine().toUpperCase();
			switch(choice) {
				case "C": showCreateMeeting(); return;
				case "R": showMeetings("", "1970-01-01 00:00", "2050-01-01 00:00", new ArrayList<User>()); return;
				case "O": showLogout(); return;
				default: println("補危阻�《拈苅�");
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
				println("個倖氏咏兆忖´´");
				title = readLine();
				
				Meeting.checkTitle(title, null);
				break;
			} catch(Exception e) {
				println(e);
			}
		}
		
		while(true) {
			try {
				println("焚担扮昨蝕兵��(YYYY-MM-DD HH-mm)");
				beginDate = readLine();
				println("焚担扮昨潤崩��(YYYY-MM-DD HH-mm)");
				endDate = readLine();
				
				Meeting.verifyDate(beginDate, endDate);
				break;
			} catch (Exception e) {
				println(e);
			}
		}
		
		while(true) {
			try {
				println("薬乂繁´");
				participators = showUserList();
				
				Meeting.verifyUserDate(beginDate, endDate, participators, null);
				break;
			} catch(Exception e) {
				println(e);
			}
		}

		Service.createMeeting(title, beginDate, endDate, participators);
		println("吾協�〈�指肇阻��");
		readLine();
		showMainMenu();
		return;
	}
	
	static private void showMeetings(String searchTitle, String searchBeginDate, String searchEndDate, ArrayList<User> searchParticipators) throws Exception {
		ArrayList<Meeting> printMeetingsList = new ArrayList<Meeting>();
		printMeetingsList = Service.getMeetings(searchTitle, searchBeginDate, searchEndDate, searchParticipators);
		
		if (printMeetingsList.size() == 0) {
			println("",
					"短孀欺��",
					"[S] 個延朴沫訳周",
					"[販吭囚] 指欺麼暇汽"
					);
			String choice = readLine().toUpperCase();
			switch(choice) {
				case "S": showSearchMenu(searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				default: showMainMenu(); return;
			}
		}
		
		for (int i = 0; i < printMeetingsList.size(); i++) {
			println("[" + i + "] 炎籾��" + printMeetingsList.get(i).getTitle() + " 蝕兵扮寂��" + printMeetingsList.get(i).getBeginDate());
		}
		println("",
				"僉夲匯倖荷恬",
				"[R] 臥心氏咏醤悶佚連",
				"[S] 朴沫氏咏",
				"[M] 指欺麼暇汽"
				);
		while(true) {
		String choice = readLine().toUpperCase();
			switch(choice) {
				case "R": showMeetingDetail(printMeetingsList, searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				case "S": showSearchMenu(searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				case "M": showMainMenu(); return;
				default: println("補危晴�《拈苅�");
			}
		}
	}
	
	static private void showSearchMenu(String searchTitle, String searchBeginDate, String searchEndDate, ArrayList<User> searchParticipators) throws Exception {
		while(true) {
			println("",
					"[T] 譜崔朴沫炎籾",
					"[D] 譜崔扮寂袈律",
					"[P] 譜崔歌氏繁",
					"[S] 蝕兵朴沫"
					);
		
			String choice = readLine().toUpperCase();
			switch(choice) {
				case "T": {
					println("補秘倖兆隅��");
					searchTitle = readLine();
					
					println("吾協�］岱燦���");
					readLine();
					break;
				}
				case "D": {
					while(true) {
						try {
							println("補秘蝕兵扮寂��");
							searchBeginDate = readLine();
							println("補秘潤崩扮寂");
							searchEndDate = readLine();
							Meeting.verifyDate(searchBeginDate, searchEndDate);
							break;
						} catch(Exception e) {
							println(e);
						}
					}
					println("吾協�］岱燦���");
					readLine();
					break;
				}
				case "P" : {
					searchParticipators = showUserList();
					println("吾協�］岱燦���");
					readLine();
					break;
				}
				
				case "S": {
					showMeetings(searchTitle, searchBeginDate, searchEndDate, searchParticipators);
					return;
				}
				
				default: println("哀兔返錆阻��");
			}
		}
	}
	
	static private void showMeetingDetail(ArrayList<Meeting> printMeetingList, String searchTitle, String searchBeginDate, String searchEndDate, ArrayList<User> searchParticipators) throws Exception {
		println("萩僉夲氏咏園催");
		Meeting meetingSelected;
		while(true) {
			int choice = readInt();
			if (choice >= 0 && choice < printMeetingList.size()) {
				meetingSelected = printMeetingList.get(choice);
				break;
			} else {
				println("短宸園催��壅栖´");
			}
		}
		
		String participatorsList = "";
		for (User i : meetingSelected.getParticipators()) {
			participatorsList += i.getName() + ";";
		}
			
		println("",
				"炎籾��" + meetingSelected.getTitle(),
				"蝕兵晩豚��" + meetingSelected.getBeginDate(),
				"潤崩晩豚��" + meetingSelected.getEndDate(),
				"歌紗繁��" + participatorsList,
				"host: " + meetingSelected.getHost().getName()
				);
		
		println("",
				"萩僉夲匯倖荷恬",
				"[E] 俐個緩氏咏",
				"[R] 卦指氏咏双燕",
				"[M] 卦指麼暇汽"
				);
		
		while(true) {
			String choice = readLine().toUpperCase();
			switch(choice) {
				case "E": showEditMeeting(meetingSelected, searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				case "R": showMeetings(searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				case "M": showMainMenu(); return;
				default: println("補秘嗤列�《拈苅�");
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
						"[T] 俐個炎籾",
						"[D] 俐個晩豚",
						"[P] 俐個歌氏繁",
						"[R] 隠贋旺潤崩園辞"
						);
		
				String choice = readLine().toUpperCase();
				switch(choice) {
				
					case "T": {
						while(true) {
							try {
								println("個倖挫油隅議兆忖:");
								title = readLine();					
								Meeting.checkTitle(title, meetingSelected);
								break;
								
							} catch(Exception e) {
								println(e);
							}
						}
						println("吾協�］岱燦���");
						break;
					}
					
					case "D": {
						while(true) {
							try {
								println("焚担扮昨蝕兵��");
								beginDate = readLine();
								println("焚担扮昨潤崩��");
								endDate = readLine();
								Meeting.verifyDate(beginDate, endDate);
								Meeting.verifyUserDate(beginDate, endDate, participators, meetingSelected);
								break;
							} catch(Exception e) {
								println(e);
							}
						}
						println("吾協�］岱燦���");
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
						println("吾協�］岱燦���");
						break;
					}
					
					case "R":{
						Service.editMeeting(meetingSelected, title, beginDate, endDate, participators);
						println("吾協��");
						readLine();
						showMainMenu();
						return;
					}
				}
			}
		} else {
			if (inMeeting) {
				println("低短嗤幡�湮涕鳥痃蚩渡�",
						"[Y] 曜竃氏咏",
						"[販吭囚] 卦指氏咏双燕"
						);
				String choice = readLine().toUpperCase();
				switch(choice) {
					case "Y": {
						Service.exitMeeting(meetingSelected); 
						println("撹孔碁渠��");
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
				println("低短嗤幡�湮涕鳥痃蚩渡�",
						"[販吭囚] 卦指氏咏双燕"
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
			println("萩補秘喘薩園催��補秘-1燕幣潤崩");
			int choice = readInt();
			if (choice == -1) break;	
			else if (choice >= 0 && choice < users.size()) {
				if (users.get(choice).includes(result)){
					println("宸歯厮将壓双燕戦阻´");
				} else {
					result.add(users.get(choice));
					println("耶紗撹孔��");
				}
			} else {
				println("園催危列�“ト携碗燦鬘�");
			}
		}
		return result;
	}
	
	static private void showLogout() throws Exception {
		Service.logout();
		println("恠挫音僕��");
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
				System.out.println("ふざけんなですわ�「残槓簇詈�忖��");
				scanner.nextLine();
			}
		}
		scanner.nextLine();
		return result;
	}
}