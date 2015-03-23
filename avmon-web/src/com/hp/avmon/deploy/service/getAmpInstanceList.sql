SELECT   a.agent_id || '.' || a.amp_inst_id AS "id", a.agent_id AS "agentId",
         a.amp_inst_id AS "path", a.amp_id AS "name",
         a.caption AS "caption", a.AMP_VERSION AS "version",
         b.VERSION AS "latestVersion", a.status AS "status",
         c.os as "os",
         c.os_version as "osVersion",
         c.ip as "ip",
         d.value as "hostName",
         CASE
            WHEN a.status = 1
               THEN '已部署'
            ELSE '未部署'
         END AS "deploy"
         ,to_char(a.last_amp_update_time,'YYYY-MM-DD HH24:MI:SS') as "lastUpdateTime"
         ,b.TARGET_MO_TYPE as "type"
    FROM td_avmon_amp_inst a, td_avmon_amp b, td_avmon_agent c left join td_avmon_mo_info_attribute d on (c.mo_id=d.mo_id and d.name='hostName')
   WHERE a.amp_id = b.amp_id and a.agent_id=c.agent_id
   %s
ORDER BY a.amp_id, a.amp_inst_id

