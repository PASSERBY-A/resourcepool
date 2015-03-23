select a.mo_id,case when b.hostname is null then a.ip else b.hostname end as hostname,a.ip
from
(select mo_id,value as IP from TD_AVMON_MO_INFO_ATTRIBUTE where name='ip' ) a
left join (select mo_id,value as HOSTNAME from TD_AVMON_MO_INFO_ATTRIBUTE where name='hostName') b on a.mo_id=b.mo_id
