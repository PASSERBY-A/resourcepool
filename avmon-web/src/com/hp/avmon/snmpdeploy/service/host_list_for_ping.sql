 SELECT *
   FROM (SELECT temp.*, ROWNUM RN
           FROM (select mo_id, host_name as , ip
                   from td_avmon_agent
                  order by ip) temp
         WHERE ROWNUM <= %s)
 WHERE RN >= %s