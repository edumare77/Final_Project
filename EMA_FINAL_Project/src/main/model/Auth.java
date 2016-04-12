package main.model;

import java.util.Date;

public class Auth {
	
		private Long nhsNo;
		private String userName;
		private String password;
		private UserGroups userGroup;
		private Date createdDate;
		private Date modifiedDate;
		
		public Auth() {
			userGroup = new UserGroups();
		}
		
		public Long getNhsNo() {
			return nhsNo;
		}
		public String getUserName() {
			return userName;
		}
		public String getPassword() {
			return password;
		}
		
		public Date getCreatedDate() {
			return createdDate;
		}
		public Date getModifiedDate() {
			return modifiedDate;
		}
		public void setNhsNo(Long nhsNo) {
			this.nhsNo = nhsNo;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public void setModifiedDate(Date modifiedDate) {
			this.modifiedDate = modifiedDate;
		}
		

		public UserGroups getUserGroup() {
			return userGroup;
		}

		public void setUserGroup(UserGroups userGroup) {
			this.userGroup = userGroup;
		}

		
}
