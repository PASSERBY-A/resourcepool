select  count(*)
  from 
    td_avmon_mo_info a,
  TD_AVMON_MO_TYPE b
  where 
    a.type_id = b.type_id and
    b.display_flag=1 and
  	a.type_id IN ('STORAGE','OTHERS','NETWORK') and 
    not exists( select  mo_id  from td_avmon_mo_info_attribute  where name='businessSystem' and a.mo_id = mo_id) 