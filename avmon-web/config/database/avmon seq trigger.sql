create sequence AUTO_CLOSE_RULE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence DEPT_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence FILTER_RULE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence IREPORT_DATASOURCE_SEQ
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

create sequence IREPORT_HTML_SEQ
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

create sequence KPI_THRESHOLD_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence MERGE_RULE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence MODULE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence ROLE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence TRANSLATE_RULE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence UPGRADE_RULE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence USER_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence NOTIFY_RULE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence TF_AVMON_ROUTE_INS_DEV_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence TF_AVMON_ROUTE_KPI_CODE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence TF_AVMON_ROUTE_INS_KPI_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence ROUTE_INSPECTION_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 50000
increment by 1
cache 20;

create sequence AVMON_NOTIFY_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 256
increment by 1
cache 20;

create sequence ROUTE_CONFIG_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 256
increment by 1
cache 20;

create sequence KPI_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;
-------------------------------------------------------------------

create or replace trigger auto_close_rule_increase
  before insert on TD_AVMON_AUTO_CLOSE_RULE
  for each row
begin
  select auto_close_rule_seq.nextval into:New.id from dual;
end;

create or replace trigger dept_increase
  before insert on portal_depts  
  for each row
begin
  select DEPT_SEQ.nextval into:New.id from dual;
end;

create or replace trigger filter_rule_increase
  before insert on TD_AVMON_FILTER_RULE
  for each row
begin
  select filter_rule_seq.nextval into:New.id from dual;
end;

create or replace trigger ireport_datasource_increase
  before insert on IREPORT_DATASOURCE
  for each row
begin
  select IREPORT_DATASOURCE_SEQ.nextval into:New.id from dual;
end;

create or replace trigger ireport_email_increase
  before insert on IREPORT_EMAIL
  for each row
begin
  select IREPORT_EMAIL_SEQ.nextval into:New.id from dual;
end;

create or replace trigger ireport_html_increase
  before insert on IREPORT_HTML
  for each row
begin
  select IREPORT_HTML_SEQ.nextval into:New.id from dual;
end;

create or replace trigger ireport_mgt_increase
  before insert on IREPORT_MGT
  for each row
begin
  select IREPORT_MGT_SEQ.nextval into:New.id from dual;
end;

create or replace trigger kpi_threshold_increase
  before insert on TD_AVMON_KPI_THRESHOLD
  for each row
begin
  select KPI_THRESHOLD_SEQ.nextval into:New.id from dual;
end;

create or replace trigger merge_rule_increase
  before insert on TD_AVMON_MERGE_RULE
  for each row
begin
  select merge_rule_seq.nextval into:New.id from dual;
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

create or replace trigger translate_rule_increase
  before insert on TD_AVMON_TRANSLATE_RULE
  for each row
begin
  select translate_rule_seq.nextval into:New.id from dual;
end;

create or replace trigger upgrade_rule_increase
  before insert on TD_AVMON_UPGRADE_RULE
  for each row
begin
  select upgrade_rule_seq.nextval into:New.id from dual;
end;

create or replace trigger user_increase
  before insert on portal_users
  for each row
begin
  select USER_SEQ.nextval into:New.id from dual;
end;

create or replace trigger notify_rule_increase
  before insert on td_avmon_notify_rule
  for each row
begin
  select notify_rule_seq.nextval into:New.id from dual;
end;

create or replace trigger TF_AVMON_ROUTE_DEV_increase
  before insert on tf_avmon_route_inspection_dev
  for each row
begin
  select TF_AVMON_ROUTE_INS_DEV_SEQ.nextval into:New.id from dual;
end;

create or replace trigger TF_AVMON_ROUTE_KPI_increase
  before insert on tf_avmon_route_kpi_code
  for each row
begin
  select TF_AVMON_ROUTE_KPI_CODE_SEQ.nextval into:New.id from dual;
end;

create or replace trigger TF_ROUTE_KPI_hold_increase
  before insert on tf_avmon_route_inspection_kpi
  for each row
begin
  select TF_AVMON_ROUTE_INS_KPI_SEQ.nextval into:New.id from dual;
end;

create or replace trigger route_inspection_increase
  before insert on tf_avmon_route_inspection_info
  for each row
begin
  select ROUTE_INSPECTION_SEQ.nextval into:New.id from dual;
end;

create or replace trigger route_config_increase
  before insert on tf_avmon_route_config_info
  for each row
begin
  select ROUTE_CONFIG_SEQ.nextval into:New.id from dual;
end;

create or replace trigger kpi_increase
  before insert on td_avmon_kpi_info
  for each row
begin
  select KPI_SEQ.nextval into:New.id from dual;
end;

--------------------------------------

create table TF_AVMON_ROUTE_CONFIG_INFO
(
  ID                  VARCHAR2(50),
  AMP_INST_ID VARCHAR2(50),
  KPI_ID              VARCHAR2(50),
  MO_ID               VARCHAR2(50),
  VALUE               VARCHAR2(128),
  VALUE_DESC          VARCHAR2(3000),
  LAST_UPDATE_TIME    DATE,
  PATH                VARCHAR2(200),
  NUM_VALUE           NUMBER(18,4),
  STR_VALUE           VARCHAR2(128),
  DEVICE_IP           VARCHAR2(64),
  DEVICE_TYPE         VARCHAR2(64),
  KPI_TYPE            VARCHAR2(64),
  DEVICE_DESC         VARCHAR2(3000),
  KPI_THRESHOLD       VARCHAR2(64),
  KPI_STATUS          VARCHAR2(64)
);

create table PORTAL_PARAMS
(
  ID          NUMBER(19),
  PARAM_NAME  VARCHAR2(255 CHAR),
  PARAM_VALUE VARCHAR2(1000 CHAR),
  PARAM_CODE  VARCHAR2(255 CHAR),
  PARAM_OTHER VARCHAR2(1000 CHAR)
);

insert into portal_params(id,param_name,param_value,param_code)
values(1,'License注册码','0646-MH1G-HMLY-ED&H-DEDC-66EE-TLHY-MCU#-MMMM3','licenseInfo');
commit;