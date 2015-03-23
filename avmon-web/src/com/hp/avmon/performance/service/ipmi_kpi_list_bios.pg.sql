select distinct a.instance as "Bios",
                (case
                  WHEN e.str_value is null then
                   'NA'
                  ELSE
                   e.str_value
                END) as "isPowerOn",
                (case
                  WHEN f.str_value is null then
                   'NA'
                  ELSE
                   f.str_value
                END) as "isPowerFault",
                (case
                  WHEN g.str_value is null then
                   'NA'
                  ELSE
                   g.str_value
                END) as "isPowerOverload",
                (case
                  WHEN h.str_value is null then
                   'NA'
                  ELSE
                   h.str_value
                END) as "uidlightStatus"

  from (select a.mo_id, a.instance, a.kpi_code, b.*
          from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
         where a.value_key = b.value_key) a
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '8201001012') e on a.mo_id = e.mo_id
                                                and a.instance = e.instance
 INNER join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '8201001009') f on a.mo_id = f.mo_id
                                                and a.instance = f.instance
     LEFT join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '8201001013') g on a.mo_id = g.mo_id
                                                and a.instance = g.instance
     LEFT join (select a.mo_id, a.instance, b.str_value
               from tf_avmon_kpi_value_list a, tf_avmon_kpi_value_current b
              where a.value_key = b.value_key
                and a.kpi_code = '8201002001') h on a.mo_id = h.mo_id
                                                and a.instance = h.instance 

 where a.mo_id = '%s' 
 
 --and  a.instance='bios'

 order by a.instance