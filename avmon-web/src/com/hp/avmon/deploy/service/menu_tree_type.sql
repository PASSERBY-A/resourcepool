select type_id as "id",
    caption as "text",
    parent_id as "pid",
    type_views as "views",
    type_default_view as "defaultView",
    icon_cls as "iconCls",
    icon_cls_pause as "iconClsPause",
    icon_cls_error as "iconClsError",
    resource_picture as "resourcePicture",
    resource_picture_direction as "resourcePictureDirection",
    0 as "isInstance",
    'false' as "leaf",
    'false' as "expanded"
 from TD_AVMON_MO_TYPE where parent_id='%s' and display_flag=1 