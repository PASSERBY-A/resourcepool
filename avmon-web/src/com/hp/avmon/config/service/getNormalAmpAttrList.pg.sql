select a1.AMP_ID as "ampId",a1.NAME as "name",a1.DEFAULT_VALUE as "defaultValue" ,a2.value as "ampInstValue",a1.nullable as "nullAble"
from TD_AVMON_AMP_ATTR a1
left join TD_AVMON_AMP_INST_ATTR a2 on(a1.NAME = a2.name)