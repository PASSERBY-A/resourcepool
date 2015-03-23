SELECT b.instance AS "dbWaitEvent",
       b.str_value as "dbWaitNumber",
       c.str_value as "dbWaitTimeouttimes",
       d.str_value as "dbWaitTimeout"
  FROM 
  (SELECT mo_id, str_value, instance
               FROM (SELECT a.mo_id, a.instance, a.kpi_code, b.*
                       FROM tf_avmon_kpi_value_list    a,
                            tf_avmon_kpi_value_current b
                      WHERE a.value_key = b.value_key) k
              WHERE 1 = 1
                AND mo_id = '{MO_ID}'
                AND kpi_code = '2102021002'
                ORDER BY create_time DESC
                ) b
  LEFT JOIN (SELECT mo_id, str_value, instance
               FROM (SELECT a.mo_id, a.instance, a.kpi_code, b.*
                       FROM tf_avmon_kpi_value_list    a,
                            tf_avmon_kpi_value_current b
                      WHERE a.value_key = b.value_key) k
              WHERE 1 = 1
                AND mo_id = '{MO_ID}'
                AND kpi_code = '2102021003'
                ORDER BY create_time DESC
                ) c
    ON b.mo_id = c.mo_id
   AND b.instance = c.instance
  LEFT JOIN (SELECT mo_id, str_value||unit as str_value, instance
               FROM (SELECT a.mo_id, a.instance, a.kpi_code, b.*,c.unit
                       FROM tf_avmon_kpi_value_list    a,
                            tf_avmon_kpi_value_current b,
                            td_avmon_kpi_info c
                      WHERE a.value_key = b.value_key and a.kpi_code=c.kpi_code) k
              WHERE 1 = 1
                AND mo_id = '{MO_ID}'
                AND kpi_code = '2102021004'
                ORDER BY create_time DESC
                ) d
    ON b.mo_id = d.mo_id
   AND b.instance = d.instance
   WHERE 1 = 1
   limit 5
 