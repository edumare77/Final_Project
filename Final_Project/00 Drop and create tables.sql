DROP TABLE IF EXISTS appo_med_pat_date;
DROP TABLE IF EXISTS dates;
DROP TABLE IF EXISTS auth;
DROP TABLE IF EXISTS user_groups;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS med_staff;


CREATE TABLE user_groups (
	USER_GROUP VARCHAR(10) NOT NULL,
	DESCRIPT TEXT NOT NULL,
	CREATED_DATE DATETIME NOT NULL,
	MODIFIED_DATE DATETIME NOT NULL,
	PRIMARY KEY (USER_GROUP)
) ENGINE=InnoDB;

CREATE TABLE dates (
	DATE_ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	APPO_DATE DATETIME NOT NULL,
	APPO_TIME TIME NOT NULL,
	PRIMARY KEY (DATE_ID)
) ENGINE=InnoDB;

CREATE TABLE med_staff (
	MED_ID BIGINT UNSIGNED DEFAULT 0 NOT NULL,
	ROOM VARCHAR(3) NOT NULL,
	MED_CLASS VARCHAR(3) NOT NULL,
	CREATED_DATE DATETIME NOT NULL,
	MODIFIED_DATE DATETIME NOT NULL,
	PRIMARY KEY (MED_ID)
) ENGINE=InnoDB;

CREATE TABLE user (
	NHS_NO BIGINT UNSIGNED DEFAULT 0 NOT NULL,
	NAME VARCHAR(45) NOT NULL,
	SURNAME VARCHAR(45) NOT NULL,
	ADDRESS VARCHAR(255) NOT NULL,
	TELEPHONE BIGINT UNSIGNED,
	MOBILEPHONE BIGINT UNSIGNED,
	GENDER VARCHAR(11),
	ETHNICITY VARCHAR(10),
	DOB DATE NOT NULL,
	MED_ID BIGINT UNSIGNED NULL,
	CREATED_DATE DATETIME NOT NULL,
	MODIFIED_DATE DATETIME NOT NULL,
	PRIMARY KEY (NHS_NO),
	CONSTRAINT fk_User_MedStaff FOREIGN KEY (MED_ID) REFERENCES med_staff(MED_ID)
) ENGINE=InnoDB;

CREATE TABLE appo_med_pat_date (
	DATE_ID BIGINT UNSIGNED NOT NULL,
	NHS_NO BIGINT UNSIGNED NOT NULL,
	MED_ID BIGINT UNSIGNED NOT NULL,
	REASON TEXT NOT NULL,
	DIAG TEXT,
	PRESC VARCHAR(255),
	CONSTRAINT fk_Appo_Dates FOREIGN KEY (DATE_ID) REFERENCES dates(DATE_ID),
	CONSTRAINT fk_Appo_User FOREIGN KEY (NHS_NO) REFERENCES user(NHS_NO),
	CONSTRAINT fk_Appo_MedStaff FOREIGN KEY (MED_ID) REFERENCES med_staff(MED_ID)
) ENGINE=InnoDB;



CREATE TABLE auth (
	USERNAME VARCHAR(20) NOT NULL,
	PASSWORD VARCHAR(40) NOT NULL,
	NHS_NO BIGINT UNSIGNED NULL,
	USER_GROUP VARCHAR(10) NOT NULL,
	CREATED_DATE DATETIME NOT NULL,
	MODIFIED_DATE DATETIME NOT NULL,
	PRIMARY KEY (USERNAME),
	CONSTRAINT fk_Auth_User FOREIGN KEY (NHS_NO) REFERENCES user(NHS_NO),
	CONSTRAINT fk_Auth_UserGroups FOREIGN KEY (USER_GROUP) REFERENCES user_groups(USER_GROUP)
) ENGINE=InnoDB;

CREATE INDEX DATE_ID ON appo_med_pat_date (DATE_ID ASC);

CREATE INDEX MED_ID ON user (MED_ID ASC);

CREATE INDEX USER_GROUP ON auth (USER_GROUP ASC);

CREATE INDEX MED_ID ON appo_med_pat_date (MED_ID ASC);

CREATE INDEX NHS_NO ON auth (NHS_NO ASC);

CREATE INDEX NHS_NO ON appo_med_pat_date (NHS_NO ASC);

insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '09:30');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '09:40');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '09:50');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '10:00');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '10:10');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '10:20');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '10:30');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '10:40');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '10:50');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '11:00');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '11:10');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '11:20');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '11:30');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '11:40');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '11:50');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '12:00');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '12:10');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '12:20');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '12:30');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '12:40');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '12:50');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '13:00');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '13:10');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '13:20');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '13:30');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '13:40');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '13:50');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '14:00');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '14:10');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '14:20');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '14:30');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '14:40');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '14:50');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '15:00');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '15:10');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '15:20');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '15:30');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '15:40');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '15:50');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '16:00');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '16:10');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '16:20');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '16:30');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '16:40');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '16:50');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '17:00');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '17:10');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '17:20');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '17:30');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '17:40');
insert into dates(APPO_DATE, APPO_TIME) 
values('2015/09/29', '17:50');
