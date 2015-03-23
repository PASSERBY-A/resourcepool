create sequence USER_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 100
increment by 1
cache 20;

create sequence ROLE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 100
increment by 1
cache 20;

create sequence MODULE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 100
increment by 1
cache 20;

create sequence DEPT_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 100
increment by 1
cache 20;

create or replace trigger dept_increase
  before insert on portal_depts  
  for each row
begin
  select DEPT_SEQ.nextval into:New.id from dual;
end;

create or replace trigger module_increase
  before insert on portal_modules
  for each row
begin
  select MODULE_SEQ.nextval into:New.id from dual;
end;

create or replace trigger role_increase
  before insert on portal_roles
  for each row
begin
  select ROLE_SEQ.nextval into:New.id from dual;
end;

create or replace trigger user_increase
  before insert on portal_users
  for each row
begin
  select USER_SEQ.nextval into:New.id from dual;
end;
