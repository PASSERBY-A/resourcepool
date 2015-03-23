SELECT *
  FROM (SELECT CAST(coalesce(VALUE, '0') as integer) AS XP_CP_IOPS, PATH 
          FROM (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) a
         WHERE mo_id = '%s' AND KPI_CODE = 'xp_cp_iops' AND AMP_INST_ID = 'wk_xpcp_1002') a
       LEFT JOIN
       (SELECT CAST(coalesce(VALUE, '0') as integer) AS XP_CP_R_MS, PATH
          FROM (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) a
         WHERE mo_id = '%s' AND KPI_CODE = 'xp_cp_r_ms' AND AMP_INST_ID = 'wk_xpcp_1002') b ON a.PATH = b.PATH
       LEFT JOIN
       (SELECT CAST(coalesce(VALUE, '0') as integer) AS XP_CP_W_MS, PATH
          FROM (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) a
         WHERE mo_id = '%s' AND KPI_CODE = 'xp_cp_w_ms' AND AMP_INST_ID = 'wk_xpcp_1002') c ON a.PATH = c.PATH
order by a.PATH