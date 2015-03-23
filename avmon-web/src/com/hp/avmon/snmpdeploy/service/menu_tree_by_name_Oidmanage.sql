 select a.mo_id              as "id",
    a.caption                as "text",
    a.type_id                as "pid",
    b.instance_views         as "views",
    b.instance_default_view  as "defaultView",
    b.icon_cls               as "iconCls",
    b.icon_cls_pause         as "iconClsPause",
    b.icon_cls_error         as "iconClsError",
    b.resource_picture       as "resourcePicture",
    b.resource_picture_direction as "resourcePictureDirection",
    1                        as "isInstance",
    'true'         	         as "leaf",
    'false'                  as "expanded"
  from 
    td_avmon_snmpoid_info a,
    td_avmon_snmp_type b
  where 
    a.type_id = b.type_id and
    b.display_flag=1 