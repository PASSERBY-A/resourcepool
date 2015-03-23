select 
    distinct (case WHEN b.str_value is null then '0%' ELSE b.str_value END) as "timePercent",
     (case WHEN c.str_value is null then '0%' ELSE c.str_value END) as "freePercent",
      (case WHEN d.str_value is null then '0 MB' ELSE d.str_value END) as  "used",
     a.instance as name

    from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key)  a 

inner join(    
select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key 
and  mo_id='{MO_ID}' and kpi_code='1701018004' ) b on a.mo_id=b.mo_id and a.instance=b.instance

inner join(    
select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key 
and  mo_id='{MO_ID}' and kpi_code='1701018005' ) c on a.mo_id=b.mo_id and a.instance=c.instance

inner join(    
select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key 
and  mo_id='{MO_ID}' and kpi_code='1701018008' ) d on a.mo_id=b.mo_id and a.instance=d.instance