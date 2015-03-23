-- Create table
create table TD_AVMON_SNMP_TRAP
(
  trap_key      VARCHAR2(50) not null,
  trap_content  VARCHAR2(500),
  alarm_count   NUMBER(1),
  oid_list      VARCHAR2(500),
  alarm_title   VARCHAR2(200),
  alarm_grade   VARCHAR2(200),
  alarm_time    VARCHAR2(200),
  alarm_type    VARCHAR2(200),
  flag          NUMBER(1),
  alarm_content VARCHAR2(1000)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column TD_AVMON_SNMP_TRAP.flag
  is '初始化为0，编辑后为1';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TD_AVMON_SNMP_TRAP
  add constraint TRAPKEYPRI primary key (TRAP_KEY)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
