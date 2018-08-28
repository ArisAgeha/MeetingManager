import java.util.ArrayList;
import java.util.Scanner;

class UI{
	static private Scanner scanner = new Scanner(System.in);
	
	static public void showLoginMenu() throws Exception {
		println("与其感慨路难行，不如马上出发。",
				"[L] 登录",
				"[R] 注册"
				);
		while(true) {
			String choice = readLine().toUpperCase();
			switch (choice) {
				case "L": showLogin(); return;
				case "R": showRegister(); return;
				default: println("输错了！再来！");
			}
		}
	}
	
	static private void showLogin() throws Exception {
		String username;
		String password;
		
		while(true) {
			try {
				println("用户名是啥？");
				username = readLine();
				println("密码是啥？");
				password = readLine();
				
				User.verifyUserInfo(username, password);
				break;
			} catch(Exception e) {
				println(e);
			}
			println("输入回车键重新输入，其他键滚回主菜单。");
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
				println("输入账号啦！");
				username = readLine();	
				
				User.checkUsername(username);
				break;
			} catch(Exception e) {
				println(e);
			}
			
			println("按回车键继续，按其他键滚回主菜单");
			String leave = readLine().toUpperCase();
			if (leave.length() != 0) {
				showLoginMenu();
				return;
			}
		}
		
		while(true) {
			try {
				println("输入密码啦！");
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
				println("输入名字啦！");
				name = readLine();
				
				User.checkName(name);
				break;
			} catch (Exception e) {
				println(e);
			}
			readLine();
		}
		
		Service.regis(username, password, name);
		println("搞定！要跳转了！！");
		readLine();
		showLoginMenu();
		return;
	}
	
	static private void showMainMenu() throws Exception {
		println("",
				"[C] 创建会议",
				"[R] 查看会议",
				"[O] 注销"
				);
		
		while(true) {
			String choice = readLine().toUpperCase();
			switch(choice) {
				case "C": showCreateMeeting(); return;
				case "R": showMeetings("", "1970-01-01 00:00", "2050-01-01 00:00", new ArrayList<User>()); return;
				case "O": showLogout(); return;
				default: println("输错了！再来！");
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
				println("改个会议名字……");
				title = readLine();
				
				Meeting.checkTitle(title, null);
				break;
			} catch(Exception e) {
				println(e);
			}
		}
		
		while(true) {
			try {
				println("什么时候开始？(YYYY-MM-DD HH-mm)");
				beginDate = readLine();
				println("什么时候结束？(YYYY-MM-DD HH-mm)");
				endDate = readLine();
				
				Meeting.verifyDate(beginDate, endDate);
				break;
			} catch (Exception e) {
				println(e);
			}
		}
		
		while(true) {
			try {
				println("挑些人…");
				participators = showUserList();
				
				Meeting.verifyUserDate(beginDate, endDate, participators, null);
				break;
			} catch(Exception e) {
				println(e);
			}
		}

		Service.createMeeting(title, beginDate, endDate, participators);
		println("搞定！要回去了！");
		readLine();
		showMainMenu();
		return;
	}
	
	static private void showMeetings(String searchTitle, String searchBeginDate, String searchEndDate, ArrayList<User> searchParticipators) throws Exception {
		ArrayList<Meeting> printMeetingsList = new ArrayList<Meeting>();
		printMeetingsList = Service.getMeetings(searchTitle, searchBeginDate, searchEndDate, searchParticipators);
		
		if (printMeetingsList.size() == 0) {
			println("",
					"没找到！",
					"[S] 改变搜索条件",
					"[任意键] 回到主菜单"
					);
			String choice = readLine().toUpperCase();
			switch(choice) {
				case "S": showSearchMenu(searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				default: showMainMenu(); return;
			}
		}
		
		for (int i = 0; i < printMeetingsList.size(); i++) {
			println("[" + i + "] 标题：" + printMeetingsList.get(i).getTitle() + " 开始时间：" + printMeetingsList.get(i).getBeginDate());
		}
		println("",
				"选择一个操作",
				"[R] 查看会议具体信息",
				"[S] 搜索会议",
				"[M] 回到主菜单"
				);
		while(true) {
		String choice = readLine().toUpperCase();
			switch(choice) {
				case "R": showMeetingDetail(printMeetingsList, searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				case "S": showSearchMenu(searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				case "M": showMainMenu(); return;
				default: println("输错啦！再来！");
			}
		}
	}
	
	static private void showSearchMenu(String searchTitle, String searchBeginDate, String searchEndDate, ArrayList<User> searchParticipators) throws Exception {
		while(true) {
			println("",
					"[T] 设置搜索标题",
					"[D] 设置时间范围",
					"[P] 设置参会人",
					"[S] 开始搜索"
					);
		
			String choice = readLine().toUpperCase();
			switch(choice) {
				case "T": {
					println("输入个名儿：");
					searchTitle = readLine();
					
					println("搞定！下一个！");
					readLine();
					break;
				}
				case "D": {
					while(true) {
						try {
							println("输入开始时间：");
							searchBeginDate = readLine();
							println("输入结束时间");
							searchEndDate = readLine();
							Meeting.verifyDate(searchBeginDate, searchEndDate);
							break;
						} catch(Exception e) {
							println(e);
						}
					}
					println("搞定！下一个！");
					readLine();
					break;
				}
				case "P" : {
					searchParticipators = showUserList();
					println("搞定！下一个！");
					readLine();
					break;
				}
				
				case "S": {
					showMeetings(searchTitle, searchBeginDate, searchEndDate, searchParticipators);
					return;
				}
				
				default: println("哎呀手滑了？");
			}
		}
	}
	
	static private void showMeetingDetail(ArrayList<Meeting> printMeetingList, String searchTitle, String searchBeginDate, String searchEndDate, ArrayList<User> searchParticipators) throws Exception {
		println("请选择会议编号");
		Meeting meetingSelected;
		while(true) {
			int choice = readInt();
			if (choice >= 0 && choice < printMeetingList.size()) {
				meetingSelected = printMeetingList.get(choice);
				break;
			} else {
				println("没这编号，再来…");
			}
		}
		
		String participatorsList = "";
		for (User i : meetingSelected.getParticipators()) {
			participatorsList += i.getName() + ";";
		}
			
		println("",
				"标题：" + meetingSelected.getTitle(),
				"开始日期：" + meetingSelected.getBeginDate(),
				"结束日期：" + meetingSelected.getEndDate(),
				"参加人：" + participatorsList,
				"host: " + meetingSelected.getHost().getName()
				);
		
		println("",
				"请选择一个操作",
				"[E] 修改此会议",
				"[R] 返回会议列表",
				"[M] 返回主菜单"
				);
		
		while(true) {
			String choice = readLine().toUpperCase();
			switch(choice) {
				case "E": showEditMeeting(meetingSelected, searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				case "R": showMeetings(searchTitle, searchBeginDate, searchEndDate, searchParticipators); return;
				case "M": showMainMenu(); return;
				default: println("输入有误！再来！");
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
						"[T] 修改标题",
						"[D] 修改日期",
						"[P] 修改参会人",
						"[R] 保存并结束编辑"
						);
		
				String choice = readLine().toUpperCase();
				switch(choice) {
				
					case "T": {
						while(true) {
							try {
								println("改个好听儿的名字:");
								title = readLine();					
								Meeting.checkTitle(title, meetingSelected);
								break;
								
							} catch(Exception e) {
								println(e);
							}
						}
						println("搞定！下一个！");
						break;
					}
					
					case "D": {
						while(true) {
							try {
								println("什么时候开始？");
								beginDate = readLine();
								println("什么时候结束？");
								endDate = readLine();
								Meeting.verifyDate(beginDate, endDate);
								Meeting.verifyUserDate(beginDate, endDate, participators, meetingSelected);
								break;
							} catch(Exception e) {
								println(e);
							}
						}
						println("搞定！下一个！");
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
						println("搞定！下一个！");
						break;
					}
					
					case "R":{
						Service.editMeeting(meetingSelected, title, beginDate, endDate, participators);
						println("搞定！");
						readLine();
						showMainMenu();
						return;
					}
				}
			}
		} else {
			if (inMeeting) {
				println("你没有权限修改会议信息",
						"[Y] 退出会议",
						"[任意键] 返回会议列表"
						);
				String choice = readLine().toUpperCase();
				switch(choice) {
					case "Y": {
						Service.exitMeeting(meetingSelected); 
						println("成功鸽掉！");
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
				println("你没有权限修改会议信息",
						"[任意键] 返回会议列表"
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
			println("请输入用户编号，输入-1表示结束");
			int choice = readInt();
			if (choice == -1) break;	
			else if (choice >= 0 && choice < users.size()) {
				if (users.get(choice).includes(result)){
					println("这货已经在列表里了…");
				} else {
					result.add(users.get(choice));
					println("添加成功！");
				}
			} else {
				println("编号错误！去确认一蛤！");
			}
		}
		return result;
	}
	
	static private void showLogout() throws Exception {
		Service.logout();
		println("走好不送！");
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
				System.out.println("ふざけんなですわ！只能输入数字！");
				scanner.nextLine();
			}
		}
		scanner.nextLine();
		return result;
	}
}