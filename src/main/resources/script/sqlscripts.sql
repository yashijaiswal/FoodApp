set schema 'foodapp';

select * from account;
select * from userauth;
select * from users;
select * from zones;
alter table account add column roles varchar(50);
alter table account alter column roles set default 'USER';
update account set roles = 'USER' where accid in (14, 65);

create table restaurantauth(
restaurantname varchar (100) unique not null primary key,
restauranttoken varchar(500)
);

alter table userauth add column accid integer references account(accid);
drop table restaurantauth;
create table userauth(
userauthid integer primary key,
accid integer references account(accid),
username varchar(100) unique not null,
usertoken  varchar(500)
);

delete from account where accid = 72;