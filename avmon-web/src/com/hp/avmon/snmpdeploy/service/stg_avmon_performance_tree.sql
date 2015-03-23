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
     FROM td_avmon_mo_info
     order by 4