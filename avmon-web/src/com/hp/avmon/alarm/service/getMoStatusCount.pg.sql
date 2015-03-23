SELECT   
	mo_id,  
	sum(case when current_grade=1 then 1 else 0 end) as level0,  
	sum(case when current_grade=2 then 1 else 0 end) as level1,  
	sum(case when current_grade=3 then 1 else 0 end) as level2,  
	sum(case when current_grade=4 then 1 else 0 end) as level3,  
	sum(case when current_grade=5 then 1 else 0 end) as level4,  
	sum(case when status=0 then 1 else 0 end) as newalarm,  
	sum(case when confirm_user='%s' then 1 else 0 end) as userAlarm,  
count(*) as alarmCount   
FROM TF_AVMON_ALARM_DATA   
where %s 
group by mo_id