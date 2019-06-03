
CREATE TABLE ROLE (
role_id INT AUTO_INCREMENT NOT NULL,
name VARCHAR(32) UNIQUE NOT NULL,
PRIMARY KEY (ROLE_ID)
);
INSERT INTO "PUBLIC"."ROLE" VALUES
(1, 'USER'),
(2, 'ADMIN');  

CREATE TABLE  APP_USER (
user_id INT AUTO_INCREMENT NOT NULL,
birthday DATE NOT NULL,
email VARCHAR(46) UNIQUE NOT NULL,
firstName VARCHAR(32) NOT NULL,
lastName VARCHAR(32) NOT NULL,
login VARCHAR(30) UNIQUE NOT NULL,
password VARCHAR(64) NOT NULL,
id_role INT NOT NULL,
PRIMARY KEY (user_id),
FOREIGN KEY (id_role) REFERENCES ROLE (role_id) ON DELETE CASCADE
ON UPDATE CASCADE
);



INSERT INTO "PUBLIC"."APP_USER" VALUES
(1,  '2000-10-10', 'user1@mail', 'user1212', 'user', 'user1', 'user1', 1),
(2,  '2000-10-10', 'admin1@mail', 'user', 'user1', 'Admin1', 'user1', 2);
   
         
   
 
    

        