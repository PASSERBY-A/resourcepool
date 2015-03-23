SELECT *
  FROM (SELECT VALUE AS XP_CP_IOPS, PATH 
          FROM (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) a
         WHERE mo_id = '%s' AND KPI_CODE = 'xp_cp_iops') a
       LEFT JOIN
       (SELECT VALUE AS XP_CP_R_MS, PATH
          FROM (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) a
         WHERE mo_id = '%s' AND KPI_CODE = 'xp_cp_r_ms') b ON a.PATH = b.PATH
       LEFT JOIN
       (SELECT VALUE AS XP_CP_W_MS, PATH
          FROM (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) a
         WHERE mo_id = '%s' AND KPI_CODE = 'xp_cp_w_ms') c ON a.PATH = c.PATH
order by a.PATH
