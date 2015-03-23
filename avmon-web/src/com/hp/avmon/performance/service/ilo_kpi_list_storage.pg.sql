select distinct a.instance as "storage",
                (case
                  WHEN b.str_value is null then
                   '0'
                  ELSE
                   b.str_value
                END) as "storageCrrntTemp",
                (case                 
                  WHEN c.str_value = '1' then
                  	'true'
                  ELSE
                   'false'
                END) as "storageTempStatus"
  from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001008') b on a.mo_id = b.mo_id
                                                and a.instance = b.instance

 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '5201001009') c on a.mo_id = c.mo_id
                                                and a.instance = c.instance
 where a.mo_id = '%s'