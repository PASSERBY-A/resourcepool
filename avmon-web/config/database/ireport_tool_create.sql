
-- Date/Time    : 2012/11/28 15:05:53
-- Author       : Li, Zhi Yun
-- RDBMS Type   : Oracle Database
-- ireport工具管理表

create table IREPORT_MGT (
  ID NUMBER(15,0) not null
  , REPORT_ID VARCHAR2(64) not null
  , REPORT_NAME VARCHAR2(256)
  , DATASOURCE_ID VARCHAR2(256) not null
  , TEMPLATE VARCHAR2(512)
  , MENU VARCHAR2(512)
  , UPDATED_DT DATE
  , constraint IREPORT_MGT_PKC primary key (ID)
) ;

create unique index IREPORT_MGT_UK
  on IREPORT_MGT(REPORT_ID);

-- ireport数据源

create table IREPORT_DATASOURCE (
  ID NUMBER(15,0) not null
  , TITLE VARCHAR2(512)
  , DRIVER VARCHAR2(256) not null
  , URL VARCHAR2(1024) not null
  , USER_NAME VARCHAR2(256) not null
  , PASSWORD VARCHAR2(32) not null
  , UPDATED_DT DATE
  , constraint IREPORT_DATASOURCE_PKC primary key (ID)
) ;
-- ireport email

create table IREPORT_EMAIL (
  ID NUMBER(15,0) not null
  , REPORT_ID VARCHAR2(64) not null
  , START_TIME DATE
  , PERIOD VARCHAR2(64)
  , EMAIL VARCHAR2(1024)
  , HOST VARCHAR2(128)
  , UPDATED_DT DATE
  , constraint IREPORT_EMAIL_PKC primary key (ID)
) ;

create unique index IREPORT_EMAIL_UK
  on IREPORT_EMAIL(REPORT_ID);

-- ireport html

create table IREPORT_HTML (
  ID NUMBER(15,0) not null
  , REPORT_ID VARCHAR2(64) not null
  , TYPE VARCHAR2(32)
  , START_TIME DATE
  , PATH VARCHAR2(512)
  , UPDATED_DT DATE
  , constraint IREPORT_HTML_PKC primary key (ID)
) ;

create unique index IREPORT_HTML_UK
  on IREPORT_HTML(REPORT_ID);
  
comment on table IREPORT_EMAIL is 'ireport email	 ireport email';
comment on column IREPORT_EMAIL.ID is '唯一序列号';
comment on column IREPORT_EMAIL.REPORT_ID is '报表编号';
comment on column IREPORT_EMAIL.START_TIME is '开始时间';
comment on column IREPORT_EMAIL.PERIOD is '周期	  分,时,日,月,周';
comment on column IREPORT_EMAIL.EMAIL is '收件人	 不同email之前已分号(;)隔开';
comment on column IREPORT_EMAIL.HOST is '邮件服务器IP';
comment on column IREPORT_EMAIL.UPDATED_DT is '更新时间';

comment on table IREPORT_HTML is 'ireport html	 ireport html';
comment on column IREPORT_HTML.ID is '唯一序列号';
comment on column IREPORT_HTML.REPORT_ID is '报表编号';
comment on column IREPORT_HTML.TYPE is '类型	 day,week,month';
comment on column IREPORT_HTML.START_TIME is '开始时间';
comment on column IREPORT_HTML.PATH is 'html文件路径	 C:/mediaReport/html/+reportId+/';
comment on column IREPORT_HTML.UPDATED_DT is '更新时间';

comment on table IREPORT_DATASOURCE is 'ireport数据源	 ireport数据源';
comment on column IREPORT_DATASOURCE.ID is '唯一序列号';
comment on column IREPORT_DATASOURCE.TITLE is '名称';
comment on column IREPORT_DATASOURCE.DRIVER is 'driver';
comment on column IREPORT_DATASOURCE.URL is 'url';
comment on column IREPORT_DATASOURCE.USER_NAME is 'user name';
comment on column IREPORT_DATASOURCE.PASSWORD is 'password';
comment on column IREPORT_DATASOURCE.UPDATED_DT is '更新时间';

comment on table IREPORT_MGT is 'ireport工具管理表	 ireport工具管理表';
comment on column IREPORT_MGT.ID is '唯一序列号';
comment on column IREPORT_MGT.REPORT_ID is '报表编号';
comment on column IREPORT_MGT.REPORT_NAME is '报表名称	 小于64个字符';
comment on column IREPORT_MGT.DATASOURCE_ID is '数据源ID';
comment on column IREPORT_MGT.TEMPLATE is '模板路径	 C:/mediaReport/template/+reportId+/';
comment on column IREPORT_MGT.MENU is '菜单';
comment on column IREPORT_MGT.UPDATED_DT is '更新时间';

create sequence IREPORT_DATASOURCE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence IREPORT_MGT_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence IREPORT_HTML_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence IREPORT_EMAIL_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create or replace trigger ireport_datasource_increase
  before insert on IREPORT_DATASOURCE
  for each row
begin
  select IREPORT_DATASOURCE_SEQ.nextval into:New.id from dual;
end;

create or replace trigger ireport_mgt_increase
  before insert on IREPORT_MGT
  for each row
begin
  select IREPORT_MGT_SEQ.nextval into:New.id from dual;
end;

create or replace trigger ireport_html_increase
  before insert on IREPORT_HTML
  for each row
begin
  select IREPORT_HTML_SEQ.nextval into:New.id from dual;
end;

create or replace trigger ireport_email_increase
  before insert on IREPORT_EMAIL
  for each row
begin
  select IREPORT_EMAIL_SEQ.nextval into:New.id from dual;
end;

