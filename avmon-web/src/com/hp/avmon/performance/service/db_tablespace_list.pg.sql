SELECT b.instance AS "dbTablespacenName",
       b.str_value AS "dbTablespacenStatus",
       c.str_value AS "dbTablespaceMegsAlloc",
       d.str_value AS "dbTablespaceMegsUsed",
       e.num_value AS "dbTablespacePctUsed"
  FROM 
  (SELECT mo_id, str_value, instance
               FROM (SELECT a.mo_id, a.instance, a.kpi_code, b.*
                       FROM tf_avmon_kpi_value_list    a,
                            tf_avmon_kpi_value_current b
                      WHERE a.value_key = b.value_key) k
              WHERE 1 = 1
                AND mo_id = '{MO_ID}'
                AND kpi_code = '2102002013') b


  LEFT JOIN (SELECT mo_id, str_value, instance
               FROM (SELECT a.mo_id, a.instance, a.kpi_code, b.*
                       FROM tf_avmon_kpi_value_list    a,
                            tf_avmon_kpi_value_current b
                      WHERE a.value_key = b.value_key) k
              WHERE 1 = 1
                AND mo_id = '{MO_ID}'
                AND kpi_code = '2102002002') c
    ON b.mo_id = c.mo_id
   AND b.instance = c.instance
  LEFT JOIN (SELECT mo_id, str_value, instance
               FROM (SELECT a.mo_id, a.instance, a.kpi_code, b.*
                       FROM tf_avmon_kpi_value_list    a,
                            tf_avmon_kpi_value_current b
                      WHERE a.value_key = b.value_key) k
              WHERE 1 = 1
                AND mo_id = '{MO_ID}'
                AND kpi_code = '2101002004') d
    ON b.mo_id = d.mo_id
   AND b.instance = d.instance
  LEFT JOIN (SELECT mo_id, num_value, instance
               FROM (SELECT a.mo_id, a.instance, a.kpi_code, b.*
                       FROM tf_avmon_kpi_value_list    a,
                            tf_avmon_kpi_value_current b
                      WHERE a.value_key = b.value_key) k
              WHERE 1 = 1
                AND mo_id = '{MO_ID}'
                AND kpi_code = '2101002006') e
    ON b.mo_id = e.mo_id
   AND b.instance = e.instance

