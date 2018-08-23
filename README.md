# Sample-Whatsapp
Sample Jdbc application for messaging purpose


Following is a java based sample jdbc application for messaging.
This app has following features:-
1.Login(assuming all the already registered
2. Showing which person u have talked in past .
3.U have to choose a person to talked and message will print in that page along with time .


Following tables are used :
1.   password(ID varchar(10), password varchar(20)) 
2. create table users(
    uid varchar(10) primary key, 
    name varchar(20), 
    phone varchar(10));
3.create table conversations(
     uid1 varchar(10) references users, 
     uid2 varchar(10) references users, 
     thread_id serial, 
     primary key (uid1, uid2),
     unique(thread_id),  
     check (uid1 < uid2));. 
     
     
 Credit : Silu Panda    
     
