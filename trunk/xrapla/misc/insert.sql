USE xrapla;

INSERT INTO `user` (`USERNAME`,`EMAIL`,`FIRSTNAME`,`LASTNAME`,`PASSWORD`) VALUES ('annkitkat','eioqjeopwj','Annika','Meyer','123456');
INSERT INTO `user` (`USERNAME`,`EMAIL`,`FIRSTNAME`,`LASTNAME`,`PASSWORD`) VALUES ('burnerpat','woihoie','Patrick','Jackes','9876');
INSERT INTO `user` (`USERNAME`,`EMAIL`,`FIRSTNAME`,`LASTNAME`,`PASSWORD`) VALUES ('gerkus','ipejoiwe','Markus','Geissel','asdf');

INSERT INTO `docent` (`USERNAME`,`SUBJECT`,`TITLE`) VALUES ('burnerpat','Informatik','Dr. PhD');

INSERT INTO `tutor` (`USERNAME`,`SUBJECT`,`TITLE`) VALUES ('gerkus','Projektleitung','Dr. rer. nat');

INSERT INTO `course` (`ID`,`NAME`,`TUTOR_ID`) VALUES (1,'WWI11B2','gerkus');

INSERT INTO `student` (`USERNAME`,`NUMBER`,`COURSE_ID`) VALUES ('annkitkat',9566630,1);

INSERT INTO `lecture` (`ID`,`NAME`,`COURSE_ID`) VALUES (1,'Datenbanken',1);

INSERT INTO `docent_lecture` (`LECTURE_ID`,`DOCENT_ID`) VALUES ('1','burnerpat');

INSERT INTO `coursegroup` (`ID`, `NAME`, `COURSE_ID`) VALUES ('1', 'Gruppe 3', '1');

INSERT INTO `student_group` (`GROUP_ID`,`STUDENT_ID`) VALUES (1,'annkitkat');

INSERT INTO `appointment` (`TIME`,`DATE`,`ROOM`,`CATEGORY`,`DURATION`,`GROUP_ID`,`LECTURE_ID`) VALUES ('09:00:00','2013-01-01','128','default',240,1,1);
INSERT INTO `appointment` (`TIME`,`DATE`,`ROOM`,`CATEGORY`,`DURATION`,`GROUP_ID`,`LECTURE_ID`) VALUES ('12:00:00','2013-01-08','463','default',120,1,1);
INSERT INTO `appointment` (`TIME`,`DATE`,`ROOM`,`CATEGORY`,`DURATION`,`GROUP_ID`,`LECTURE_ID`) VALUES ('14:30:00','2013-01-01','839','default',75,1,1);
INSERT INTO `appointment` (`TIME`,`DATE`,`ROOM`,`CATEGORY`,`DURATION`,`GROUP_ID`,`LECTURE_ID`) VALUES ('14:30:00','2013-03-11','839','default',75,1,1);
INSERT INTO `appointment` (`TIME`,`DATE`,`ROOM`,`CATEGORY`,`DURATION`,`GROUP_ID`,`LECTURE_ID`) VALUES ('11:30:00','2013-03-12','239','default',75,1,1);
INSERT INTO `appointment` (`TIME`,`DATE`,`ROOM`,`CATEGORY`,`DURATION`,`GROUP_ID`,`LECTURE_ID`) VALUES ('09:30:00','2013-03-15','474','default',75,1,1);