--INSTRUCTIONS:
--Create a database named "MeltingPotLocal" with username "melt" and password "pot" (w/o quotes).
--Run this SQL file in Netbeans with "MeltingPotLocal" selected in the connection field.
--Run this file again whenever a team member makes changes to the database structure.

--Drops any previously created tables.
DROP TABLE MELT.Users;

--Creates a table to hold user information.
CREATE TABLE MELT.Users(
	UserName		VARCHAR(25),
	Password		VARCHAR(50),
	Name			VARCHAR(50),
	Age				INTEGER,
	Gender			VARCHAR(10),
	City			VARCHAR(25),
	State			VARCHAR(15),
	Religion		VARCHAR(20),
	Race			VARCHAR(20),
	Politics		VARCHAR(20),
	Bio				VARCHAR(250),
	Rating			DOUBLE,
	Email			VARCHAR(35),
	Messages		VARCHAR(500),
	FriendRequest	VARCHAR(250),
	FriendList		VARCHAR(250)
);

--Sample User accounts (to be used for testing).
INSERT INTO MELT.Users VALUES ('test', '123', 'tester', 99, 'Other', 'Anchorage', 'Alaska', 'Other', 'Other', 'Other', 'I am a test account.', 5.0, 'test@ilstu.edu', '', '', '');
INSERT INTO MELT.Users VALUES ('pdkaufm', '123', 'Perry', 26, 'Male', 'Normal', 'Illinois', 'Athiest', 'White', 'Other', 'This is a bio...', 5.0, 'pdkaufm@ilstu.edu', '', '', '');

--TODO: Add admin table.