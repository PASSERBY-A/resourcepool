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
    td_avmon_mo_info a,
    TD_AVMON_MO_TYPE b
  where 
    a.type_id = b.type_id and
    b.display_flag=1 and
    not exists( select  mo_id  from   td_avmon_mo_info_attribute  where name='businessSystem' and a.mo_id = mo_id) and
    a.type_id='%s'
 
 order by 2