--INSTRUCTIONS:
--Create a database named "MeltingPotLocal" with username "melt" and password "pot" (w/o quotes).
--Run this SQL file in Netbeans with "MeltingPotLocal" selected in the connection field.
--Run this file again whenever a team member makes changes to the database structure.
--Edit this file to add new tables to the database.

--Drop all previously created tables here.
DROP TABLE MELT.Users;
DROP TABLE MELT.Conversations;
DROP TABLE MELT.Messages;
DROP TABLE MELT.Reports;

--User Table
CREATE TABLE MELT.Users(
    USERNAME                VARCHAR(25),
    PASSWORD                VARCHAR(50),
    NAME                    VARCHAR(50),
    AGE                     INTEGER,
    GENDER                  VARCHAR(10),
    CITY                    VARCHAR(25),
    STATE                   VARCHAR(15),
    RELIGION                VARCHAR(20),
    RACE                    VARCHAR(20),
    POLITICS                VARCHAR(20),
    BIO                     VARCHAR(250),
    EMAIL                   VARCHAR(35),
    BANNED                  BOOLEAN,
    FRIENDREQUEST           VARCHAR(250),
    FRIENDLIST              VARCHAR(250)
);

--Sample User accounts (to be used for testing).
INSERT INTO MELT.Users VALUES ('test', '123', 'tester', 99, 'Other', 'Anchorage', 'Alaska', 'Other', 'Other', 'Other', 'I am a test account.', 'test@ilstu.edu', 'False', '', '');
INSERT INTO MELT.Users VALUES ('pdkaufm', '123', 'Perry', 26, 'Male', 'Normal', 'Illinois', 'Athiest', 'White', 'Other', 'This is a bio...', 'pdkaufm@ilstu.edu', 'False', '', '');
INSERT INTO MELT.Users VALUES ('ajamen', '123', 'Andrew', 23, 'Male', 'Normal', 'Illinois', 'None', 'White', 'Liberal', 'Skiier', 'ajamen@ilstu.edu', 'False', '', '');
INSERT INTO MELT.Users VALUES ('akol', '123', 'Amanda', 23, 'Female', 'LITH', 'Illinois', 'None', 'White', 'Liberal', 'Dogs', 'akol@ilstu.edu', 'False', '', '');

--Conversation Table
CREATE TABLE MELT.Conversations(
    CONV_ID                  INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    USERNAME_A               VARCHAR(25),
    USERNAME_B               VARCHAR(25)
);

--Message Table
CREATE TABLE MELT.Messages(
    MESSAGE_ID              INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    CONV_ID                 INTEGER,
    SENDER                  VARCHAR(25),
    RECEIVER                VARCHAR(25),
    DATETIME                TIMESTAMP,
    CONTENT                 VARCHAR(280)
);

CREATE TABLE MELT.Reports(
    REPORT_ID               INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    REPORTER                VARCHAR(25),
    OFFENDER                VARCHAR(25),
    MESSAGE                 VARCHAR(500)
);

