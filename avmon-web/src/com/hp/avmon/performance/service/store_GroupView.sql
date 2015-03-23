
SELECT *
  FROM (SELECT nvl(VALUE, 0) AS XP_RG_IOPS, PATH 
          FROM (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key)
         WHERE mo_id = '%s' AND KPI_CODE = 'xp_rg_iops' AND AMP_INST_ID = 'wk_xprg_1005') a
       LEFT JOIN
       (SELECT nvl(VALUE, 0) AS XP_RG_MS, PATH
          FROM (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key)
         WHERE mo_id = '%s' AND KPI_CODE = 'xp_rg_ms' AND AMP_INST_ID = 'wk_xprg_1005') b ON a.PATH = b.PATH
order by a.PATH