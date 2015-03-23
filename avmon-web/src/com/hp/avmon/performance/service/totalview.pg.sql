SELECT a.biz,
       --a.hostmaxalarmlevel, 
       --a.storemaxalarmlevel, 
       
       a.hostitemcount,
       a.storeitemcount,
       a.dbitemcount,
       a.middleitemcount,
       a.veitemcount,
       a.networkitemcount,
       a.hardwareitemcount,
       a.hardwareitemothercount,
       
       a.hostkpi1,
       a.storekpi1,
       a.dbkpi1,
       a.middlekpi1,
       a.vekpi1,
       a.networkkpi1,
       a.hardwarekpi1,
       a.hardwareotherkpi1,
       
       CASE
         WHEN hostkpi2 < 10 THEN
          0
         ELSE
          hostkpi2
       END AS hostkpi2,
       CASE
         WHEN storekpi2 < 10 THEN
          0
         ELSE
          storekpi2
       END AS storekpi2,
       CASE
         WHEN dbkpi2 < 10 THEN
          0
         ELSE
          dbkpi2
       END AS dbkpi2,
       CASE
         WHEN middlekpi2 < 10 THEN
          0
         ELSE
          middlekpi2
       END AS middlekpi2,
       CASE
         WHEN vekpi2 < 10 THEN
          0
         ELSE
          vekpi2
       END AS vekpi2,
       CASE
         WHEN networkkpi2 < 10 THEN
          0
         ELSE
          networkkpi2
       END AS networkkpi2,
       CASE
         WHEN hardwarekpi2 < 10 THEN
          0
         ELSE
          hardwarekpi2
       END AS hardwarekpi2,

        CASE
         WHEN hardwareotherkpi2 < 10 THEN
          0
         ELSE
          hardwareotherkpi2
       END AS hardwareotherkpi2,
       
       CASE
         WHEN hostkpi3 < 10 THEN
          0
         ELSE
          hostkpi3
       END AS hostkpi3,
       CASE
         WHEN storekpi3 < 10 THEN
          0
         ELSE
          storekpi3
       END AS storekpi3,
       CASE
         WHEN dbkpi3 < 10 THEN
          0
         ELSE
          dbkpi3
       END AS dbkpi3,
       CASE
         WHEN middlekpi3 < 10 THEN
          0
         ELSE
          middlekpi3
       END AS middlekpi3,
       CASE
         WHEN vekpi3 < 10 THEN
          0
         ELSE
          vekpi3
       END AS vekpi3,
       CASE
         WHEN networkkpi3 < 10 THEN
          0
         ELSE
          networkkpi3
       END AS networkkpi3,
       CASE
         WHEN hardwarekpi3 < 10 THEN
          0
         ELSE
          hardwarekpi3
       END AS hardwarekpi3,

       CASE
         WHEN hardwareotherkpi3 < 10 THEN
          0
         ELSE
          hardwareotherkpi3
       END AS hardwareotherkpi3

  FROM (SELECT a.biz,
               --a.hostmaxalarmlevel, a.storemaxalarmlevel,
               
               a.hostitemcount,
               a.storeitemcount,
               a.dbitemcount,
               a.middleitemcount,
               a.veitemcount,
               a.networkitemcount,
               a.hardwareitemcount,
               a.hardwareitemothercount,
               
               a.alarmhostcount     AS hostkpi1,
               a.alarmmiddlecount   AS middlekpi1,
               a.alarmstorecount    AS storekpi1,
               a.alarmdbcount       AS dbkpi1,
               a.alarmvecount       AS vekpi1,
               a.alarmnetworkcount  AS networkkpi1,
               a.alarmhardwarecount AS hardwarekpi1,
               a.alarmhardwareothercount AS hardwareotherkpi1,
               
               100 - hostlevel2alarmcount * 0.5 - hostlevel3alarmcount -
               hostlevel4alarmcount * 1.5 AS hostkpi2,
               100 - dblevel2alarmcount * 0.5 - dblevel3alarmcount -
               dblevel4alarmcount * 1.5 AS dbkpi2,
               100 - storelevel2alarmcount * 0.5 - storelevel3alarmcount -
               storelevel4alarmcount * 1.5 AS storekpi2,
               100 - middlelevel2alarmcount * 0.5 - middlelevel3alarmcount -
               middlelevel4alarmcount * 1.5 AS middlekpi2,
               100 - velevel2alarmcount * 0.5 - velevel3alarmcount -
               velevel4alarmcount * 1.5 AS vekpi2,
               100 - networklevel2alarmcount * 0.5 - networklevel3alarmcount -
               networklevel4alarmcount * 1.5 AS networkkpi2,
               100 - hardwarelevel2alarmcount * 0.5 -hardwarelevel3alarmcount - hardwarelevel4alarmcount * 1.5 AS hardwarekpi2,
               100 - hardwareotherlevel2alarmcount * 0.5 -hardwareotherlevel3alarmcount - hardwareotherlevel4alarmcount * 1.5 AS hardwareotherkpi2,
               
               
               100 - b.warnhostcount * 10 AS hostkpi3,
               100 - b.warndbcount * 10 AS dbkpi3,
               100 - b.warnstorecount * 10 AS storekpi3,
               100 - b.warnmiddlecount * 10 AS middlekpi3,
               100 - b.warnvecount * 10 AS vekpi3,
               100 - b.warnnetworkcount * 10 AS networkkpi3,
               100 - b.warnhardwarecount * 10 AS hardwarekpi3,
               100 - b.warnhardwareothercount * 10 AS hardwareotherkpi3
        
          FROM (SELECT a.biz,
                       --MAX(CASE WHEN a.type_id LIKE 'HOST_%' AND b.current_grade IS NOT NULL   THEN b.current_grade    ELSE 0   END ) AS hostmaxalarmlevel,
                       --MAX (CASE   WHEN a.type_id like 'STORAGE_%'  AND b.current_grade IS NOT NULL    THEN b.current_grade ELSE 0 END ) AS storemaxalarmlevel,
                       -- MAX (CASE   WHEN a.type_id like 'DATABASE%'  AND b.current_grade IS NOT NULL    THEN b.current_grade ELSE 0 END ) AS dbmaxalarmlevel,
                       -- MAX (CASE   WHEN a.type_id like 'MIDDLEWARE%'  AND b.current_grade IS NOT NULL    THEN b.current_grade ELSE 0 END ) AS middlewaremaxalarmlevel,
                       COUNT(DISTINCT CASE
                               WHEN a.type_id LIKE 'HOST_%' THEN
                                a.mo_id
                               ELSE
                                NULL
                             END) AS hostitemcount,
                       COUNT(DISTINCT CASE
                               WHEN a.type_id LIKE 'HOST_%' THEN
                                b.mo_id
                               ELSE
                                NULL
                             END) AS alarmhostcount,
                       
                       COUNT(DISTINCT CASE
                               WHEN a.type_id like 'STORAGE_%' THEN
                                a.mo_id
                               ELSE
                                NULL
                             END) AS storeitemcount,
                       COUNT(DISTINCT CASE
                               WHEN a.type_id LIKE 'STORAGE_%' THEN
                                b.mo_id
                               ELSE
                                NULL
                             END) AS alarmstorecount,
                       
                       COUNT(DISTINCT CASE
                               WHEN a.type_id like 'NETWORK_%' THEN
                                a.mo_id
                               ELSE
                                NULL
                             END) AS networkitemcount,
                       COUNT(DISTINCT CASE
                               WHEN a.type_id LIKE 'NETWORK_%' THEN
                                b.mo_id
                               ELSE
                                NULL
                             END) AS alarmnetworkcount,
                       
                       COUNT(DISTINCT CASE
                               WHEN a.type_id LIKE 'DATABASE_%' THEN
                                a.mo_id
                               ELSE
                                NULL
                             END) AS dbitemcount,
                       COUNT(DISTINCT CASE
                               WHEN a.type_id LIKE 'DATABASE_%' THEN
                                b.mo_id
                               ELSE
                                NULL
                             END) AS alarmdbcount,
                       
                       COUNT(DISTINCT CASE
                               WHEN a.type_id LIKE 'MIDDLEWARE_%' THEN
                                a.mo_id
                               ELSE
                                NULL
                             END) AS middleitemcount,
                       COUNT(DISTINCT CASE
                               WHEN a.type_id LIKE 'MIDDLEWARE_%' THEN
                                b.mo_id
                               ELSE
                                NULL
                             END) AS alarmmiddlecount,
                       
                       COUNT(DISTINCT CASE
                               WHEN a.type_id LIKE 'VE_%' THEN
                                a.mo_id
                               ELSE
                                NULL
                             END) AS veitemcount,
                       COUNT(DISTINCT CASE
                               WHEN a.type_id LIKE 'VE_%' THEN
                                b.mo_id
                               ELSE
                                NULL
                             END) AS alarmvecount,
                       
                       COUNT(DISTINCT CASE
                               WHEN a.type_id LIKE 'HARDWARE_HP' THEN
                                a.mo_id
                               ELSE
                                NULL
                             END) AS hardwareitemcount,
                       COUNT(DISTINCT CASE
                               WHEN (split_part(a.type_id, '_', 2) <> 'HP' and split_part(a.type_id, '_', 2) <> 'DELL') 
and (split_part(a.type_id, '_', 1) = 'HARDWARE') THEN
                                a.mo_id
                               ELSE
                                NULL
                             END) AS hardwareitemothercount,
                       COUNT(DISTINCT CASE
                               WHEN a.type_id LIKE 'HARDWARE_HP' THEN
                                b.mo_id
                               ELSE
                                NULL
                             END) AS alarmhardwarecount,
                       COUNT(DISTINCT CASE
                               WHEN (split_part(a.type_id, '_', 2) <> 'HP' and split_part(a.type_id, '_', 2) <> 'DELL') 
and (split_part(a.type_id, '_', 1) = 'HARDWARE') THEN
                                b.mo_id
                               ELSE
                                NULL
                             END) AS alarmhardwareothercount,
                       
                       SUM(CASE
                             WHEN a.type_id LIKE 'HOST_%' AND b.current_grade = 2 THEN
                              1
                             ELSE
                              0
                           END) AS hostlevel2alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'HOST_%' AND b.current_grade = 3 THEN
                              1
                             ELSE
                              0
                           END) AS hostlevel3alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'HOST_%' AND b.current_grade = 4 THEN
                              1
                             ELSE
                              0
                           END) AS hostlevel4alarmcount,
                       
                       SUM(CASE
                             WHEN a.type_id LIKE 'STORAGE_%' AND
                                  b.current_grade = 2 THEN
                              1
                             ELSE
                              0
                           END) AS storelevel2alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'STORAGE_%' AND
                                  b.current_grade = 3 THEN
                              1
                             ELSE
                              0
                           END) AS storelevel3alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'STORAGE_%' AND
                                  b.current_grade = 4 THEN
                              1
                             ELSE
                              0
                           END) AS storelevel4alarmcount,
                       
                       SUM(CASE
                             WHEN a.type_id LIKE 'NETWORK_%' AND
                                  b.current_grade = 2 THEN
                              1
                             ELSE
                              0
                           END) AS networklevel2alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'NETWORK_%' AND
                                  b.current_grade = 3 THEN
                              1
                             ELSE
                              0
                           END) AS networklevel3alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'NETWORK_%' AND
                                  b.current_grade = 4 THEN
                              1
                             ELSE
                              0
                           END) AS networklevel4alarmcount,
                       
                       SUM(CASE
                             WHEN a.type_id LIKE 'DATABASE_%' AND
                                  b.current_grade = 2 THEN
                              1
                             ELSE
                              0
                           END) AS dblevel2alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'DATABASE_%' AND
                                  b.current_grade = 3 THEN
                              1
                             ELSE
                              0
                           END) AS dblevel3alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'DATABASE_%' AND
                                  b.current_grade = 4 THEN
                              1
                             ELSE
                              0
                           END) AS dblevel4alarmcount,
                       
                       SUM(CASE
                             WHEN a.type_id LIKE 'MIDDLEWARE_%' AND
                                  b.current_grade = 2 THEN
                              1
                             ELSE
                              0
                           END) AS middlelevel2alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'MIDDLEWARE_%' AND
                                  b.current_grade = 3 THEN
                              1
                             ELSE
                              0
                           END) AS middlelevel3alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'MIDDLEWARE_%' AND
                                  b.current_grade = 4 THEN
                              1
                             ELSE
                              0
                           END) AS middlelevel4alarmcount,
                       
                       SUM(CASE
                             WHEN a.type_id LIKE 'VE_%' AND b.current_grade = 2 THEN
                              1
                             ELSE
                              0
                           END) AS velevel2alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'VE_%' AND b.current_grade = 3 THEN
                              1
                             ELSE
                              0
                           END) AS velevel3alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'VE_%' AND b.current_grade = 4 THEN
                              1
                             ELSE
                              0
                           END) AS velevel4alarmcount,
                       
                       SUM(CASE
                             WHEN a.type_id LIKE 'HARDWARE_HP' AND
                                  b.current_grade = 2 THEN
                              1
                             ELSE
                              0
                           END) AS hardwarelevel2alarmcount,
                       SUM(CASE
                             WHEN (split_part(a.type_id, '_', 2) <> 'HP' and split_part(a.type_id, '_', 2) <> 'DELL') 
and (split_part(a.type_id, '_', 1) = 'HARDWARE') AND
                                  b.current_grade = 2 THEN
                              1
                             ELSE
                              0
                           END) AS hardwareotherlevel2alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'HARDWARE_HP' AND
                                  b.current_grade = 3 THEN
                              1
                             ELSE
                              0
                           END) AS hardwarelevel3alarmcount,
                       SUM(CASE
                             WHEN (split_part(a.type_id, '_', 2) <> 'HP' and split_part(a.type_id, '_', 2) <> 'DELL') 
and (split_part(a.type_id, '_', 1) = 'HARDWARE') AND
                                  b.current_grade = 3 THEN
                              1
                             ELSE
                              0
                           END) AS hardwareotherlevel3alarmcount,
                       SUM(CASE
                             WHEN a.type_id LIKE 'HARDWARE_HP' AND
                                  b.current_grade = 4 THEN
                              1
                             ELSE
                              0
                           END) AS hardwarelevel4alarmcount,
                        SUM(CASE
                             WHEN (split_part(a.type_id, '_', 2) <> 'HP' and split_part(a.type_id, '_', 2) <> 'DELL') 
and (split_part(a.type_id, '_', 1) = 'HARDWARE') AND
                                  b.current_grade = 4 THEN
                              1
                             ELSE
                              0
                           END) AS hardwareotherlevel4alarmcount
                
                  FROM (SELECT a.type_id, a.mo_id, 'others' AS biz
                          FROM td_avmon_mo_info a
                          LEFT JOIN td_avmon_mo_info_attribute b
                            ON a.mo_id = b.mo_id
                           AND b.NAME = 'businessSystem'
                         where b.value not in
                               (select businessname
                                  from tf_avmon_biz_dictionary)
                            or b.value is null
                        union all
                        SELECT a.type_id, a.mo_id, b.value AS biz
                          FROM td_avmon_mo_info a
                          LEFT JOIN td_avmon_mo_info_attribute b
                            ON a.mo_id = b.mo_id
                           AND b.NAME = 'businessSystem'
                         where b.value in
                               (select businessname
                                  from tf_avmon_biz_dictionary)
                           and b.value is not null) a
                  LEFT JOIN tf_avmon_alarm_data b
                    ON a.mo_id = b.mo_id
                 GROUP BY a.biz) a
          LEFT JOIN (SELECT a.biz,
                           COUNT(DISTINCT CASE
                                   WHEN a.type_id like 'HOST_%' AND
                                        c.KPI_CODE = '1001022001' AND
                                        str_value = 'DOWN' THEN
                                    c.mo_id
                                   ELSE
                                    NULL
                                 END) AS warnhostcount,
                           COUNT(DISTINCT CASE
                                   WHEN a.type_id like 'STORAGE_%' AND
                                        c.KPI_CODE = '1001022001' AND
                                        str_value = 'DOWN' THEN
                                    c.mo_id
                                   ELSE
                                    NULL
                                 END) AS warnstorecount,
                           COUNT(DISTINCT CASE
                                   WHEN a.type_id like 'NETWORK_%' AND
                                        c.KPI_CODE = '1001022001' AND
                                        str_value = 'DOWN' THEN
                                    c.mo_id
                                   ELSE
                                    NULL
                                 END) AS warnnetworkcount,
                           COUNT(DISTINCT CASE
                                   WHEN a.type_id like 'DATABASE_%' AND
                                        c.KPI_CODE = '1001022001' AND
                                        str_value = 'DOWN' THEN
                                    c.mo_id
                                   ELSE
                                    NULL
                                 END) AS warndbcount,
                           COUNT(DISTINCT CASE
                                   WHEN a.type_id like 'MIDDLEWARE_%' AND
                                        c.KPI_CODE = '1001022001' AND
                                        str_value = 'DOWN' THEN
                                    c.mo_id
                                   ELSE
                                    NULL
                                 END) AS warnmiddlecount,
                           COUNT(DISTINCT CASE
                                   WHEN a.type_id like 'VE_%' AND
                                        c.KPI_CODE = '1001022001' AND
                                        str_value = 'DOWN' THEN
                                    c.mo_id
                                   ELSE
                                    NULL
                                 END) AS warnvecount,
                           COUNT(DISTINCT CASE
                                   WHEN a.type_id like 'HARDWARE_HP' AND
                                        c.KPI_CODE = '1001022001' AND
                                        (c.num_value >= 95 OR c.num_value < 80) THEN
                                    c.mo_id
                                   ELSE
                                    NULL
                                 END) AS warnhardwarecount,
                            COUNT(DISTINCT CASE
                                   WHEN 
                                   --a.type_id like 'HARDWARE_OTHER'
                                   (split_part(a.type_id, '_', 2) <> 'HP' and split_part(a.type_id, '_', 2) <> 'DELL') 
                                    and (split_part(a.type_id, '_', 1) = 'HARDWARE') AND
                                        c.KPI_CODE = '1001022001' AND
                                        (c.num_value >= 95 OR c.num_value < 80) THEN
                                    c.mo_id
                                   ELSE
                                    NULL
                                 END) AS warnhardwareothercount    
                    
                      FROM (SELECT a.type_id, a.mo_id, 'others' AS biz
                              FROM td_avmon_mo_info a
                              LEFT JOIN td_avmon_mo_info_attribute b
                                ON a.mo_id = b.mo_id
                               AND b.NAME = 'businessSystem'
                             where b.value not in
                                   (select businessname
                                      from tf_avmon_biz_dictionary)
                                or b.value is null
                            union all
                            SELECT a.type_id, a.mo_id, b.value AS biz
                              FROM td_avmon_mo_info a
                              LEFT JOIN td_avmon_mo_info_attribute b
                                ON a.mo_id = b.mo_id
                               AND b.NAME = 'businessSystem'
                             where b.value in
                                   (select businessname
                                      from tf_avmon_biz_dictionary)
                               and b.value is not null) a
                      LEFT JOIN (select a.mo_id, a.instance, a.kpi_code, b.*
                                  from tf_avmon_kpi_value_list    a,
                                       tf_avmon_kpi_value_current b
                                 where a.value_key = b.value_key) c
                        ON a.mo_id = c.mo_id
                     GROUP BY a.biz) b
            ON a.biz = b.biz) a