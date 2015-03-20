CREATE TABLE STG_AVMON_PERFORMANCE_TREE
(
  VIEW_TEMPLATE  VARCHAR2(50 BYTE),
  CATAGORY       VARCHAR2(50 BYTE),
  ID             VARCHAR2(50 BYTE),
  TEXT           VARCHAR2(200 BYTE),
  PID            VARCHAR2(50 BYTE),
  ICONCLS        VARCHAR2(100 BYTE),
  LEAF           VARCHAR2(20 BYTE),
  EXPANDED       VARCHAR2(20 BYTE),
  IS_INSTANCE    NUMBER(1),
  IP             VARCHAR2(20 BYTE),
  AGENT_STATUS   NUMBER(1)
);

truncate table stg_avmon_performance_tree;

insert into stg_avmon_performance_tree
   SELECT '' AS view_template, '' AS catagory, type_id AS ID, caption AS text,
          CASE
             WHEN parent_id = 'HARDWARE'
                THEN 'root'
             ELSE parent_id
          END AS pid, icon_cls AS iconcls, 'false' AS leaf,
          'true' AS expanded, 0 AS is_instance, '' AS ip, 0 AS agent_status
     FROM td_avmon_mo_type
   UNION ALL
   SELECT '' AS view_template, '' AS catagory, mo_id AS ID, caption AS text,
          type_id AS pid, '' AS iconcls, 'true' AS leaf, 'false' AS expanded,
          1 AS is_instance, '' AS ip, 0 AS agent_status
     FROM td_avmon_mo_info;

     