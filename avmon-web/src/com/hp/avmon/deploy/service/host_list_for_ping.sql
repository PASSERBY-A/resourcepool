 SELECT *
   FROM (SELECT temp.*, ROWNUM RN
           FROM (select mo_id as "MO_ID", host_name as "HOSTNAME", ip AS "IP"
                   from td_avmon_agent
                  order by ip) temp
         WHERE ROWNUM <= %s)
 WHERE RN >= %s