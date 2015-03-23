/**
 * Simplified Chinese translation
 * By DavidHu
 * 09 April 2007
 *
 * update by andy_ghg
 * 2009-10-22 15:00:57
 */
Ext.onReady(function() {
    var cm = Ext.ClassManager,
        exists = Ext.Function.bind(cm.get, cm),
        parseCodes;

    if (Ext.Updater) {
        Ext.Updater.defaults.indicatorText = '<div class="loading-indicator">加载中...</div>';
    }

    Ext.define("Ext.locale.zh_CN.view.View", {
        override: "Ext.view.View",
        emptyText: ""
    });

    Ext.define("Ext.locale.zh_CN.grid.Panel", {
        override: "Ext.grid.Panel",
        ddText: "选择了 {0} 行"
    });

    Ext.define("Ext.locale.zh_CN.TabPanelItem", {
        override: "Ext.TabPanelItem",
        closeText: "关闭此标签"
    });

    Ext.define("Ext.locale.zh_CN.form.field.Base", {
        override: "Ext.form.field.Base",
        invalidText: "输入值非法"
    });

    // changing the msg text below will affect the LoadMask
    Ext.define("Ext.locale.zh_CN.view.AbstractView", {
        override: "Ext.view.AbstractView",
        msg: "讀取中..."
    });

    if (Ext.Date) {
        Ext.Date.monthNames = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];

        Ext.Date.dayNames = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];

        Ext.Date.formatCodes.a = "(this.getHours() < 12 ? '上午' : '下午')";
        Ext.Date.formatCodes.A = "(this.getHours() < 12 ? '上午' : '下午')";

        parseCodes = {
            g: 1,
            c: "if (/(上午)/i.test(results[{0}])) {\n"
                + "if (!h || h == 12) { h = 0; }\n"
                + "} else { if (!h || h < 12) { h = (h || 0) + 12; }}",
            s: "(上午|下午)",
            calcAtEnd: true
        };

        Ext.Date.parseCodes.a = Ext.Date.parseCodes.A = parseCodes;
    }

    if (Ext.MessageBox) {
        Ext.MessageBox.buttonText = {
            ok: "确定",
            cancel: "取消",
            yes: "是",
            no: "否"
        };
    }

    if (exists('Ext.util.Format')) {
        Ext.apply(Ext.util.Format, {
            thousandSeparator: ',',
            decimalSeparator: '.',
            currencySign: '\u00a5',
            // Chinese Yuan
            dateFormat: 'y年m月d日'
        });
    }

    Ext.define("Ext.locale.zh_CN.picker.Date", {
        override: "Ext.picker.Date",
        todayText: "今天",
        minText: "日期必须大于最小允许日期",
        //update
        maxText: "日期必须小于最大允许日期",
        //update
        disabledDaysText: "",
        disabledDatesText: "",
        monthNames: Ext.Date.monthNames,
        dayNames: Ext.Date.dayNames,
        nextText: '下个月 (Ctrl+Right)',
        prevText: '上个月 (Ctrl+Left)',
        monthYearText: '选择一个月 (Control+Up/Down 来改变年份)',
        //update
        todayTip: "{0} (空格键选择)",
        format: "y年m月d日",
        ariaTitle: '{0}',
        ariaTitleDateFormat: 'Y\u5e74m\u6708d\u65e5',
        longDayFormat: 'Y\u5e74m\u6708d\u65e5',
        monthYearFormat: 'Y\u5e74m\u6708',
        getDayInitial: function (value) {
            // Grab the last character
            return value.substr(value.length - 1);
        }
    });

    Ext.define("Ext.locale.zh_CN.picker.Month", {
        override: "Ext.picker.Month",
        okText: "确定",
        cancelText: "取消"
    });

    Ext.define("Ext.locale.zh_CN.toolbar.Paging", {
        override: "Ext.PagingToolbar",
        beforePageText: "第",
        //update
        afterPageText: "页,共 {0} 页",
        //update
        firstText: "第一页",
        prevText: "上一页",
        //update
        nextText: "下一页",
        lastText: "最后页",
        refreshText: "刷新",
        displayMsg: "显示 {0} - {1}条，共 {2} 条",
        //update
        emptyMsg: '没有数据'
    });

    Ext.define("Ext.locale.zh_CN.form.field.Text", {
        override: "Ext.form.field.Text",
        minLengthText: "该输入项的最小长度是 {0} 个字符",
        maxLengthText: "该输入项的最大长度是 {0} 个字符",
        blankText: "该输入项为必输项",
        regexText: "",
        emptyText: null
    });

    Ext.define("Ext.locale.zh_CN.form.field.Number", {
        override: "Ext.form.field.Number",
        minText: "该输入项的最小值是 {0}",
        maxText: "该输入项的最大值是 {0}",
        nanText: "{0} 不是有效数值"
    });

    Ext.define("Ext.locale.zh_CN.form.field.Date", {
        override: "Ext.form.field.Date",
        disabledDaysText: "禁用",
        disabledDatesText: "禁用",
        minText: "该输入项的日期必须在 {0} 之后",
        maxText: "该输入项的日期必须在 {0} 之前",
        invalidText: "{0} 是无效的日期 - 必须符合格式： {1}",
        format: "y年m月d日"
    });

    Ext.define("Ext.locale.zh_CN.form.field.ComboBox", {
        override: "Ext.form.field.ComboBox",
        valueNotFoundText: undefined
    }, function() {
        Ext.apply(Ext.form.field.ComboBox.prototype.defaultListConfig, {
            loadingText: "加载中..."
        });
    });

    if (exists('Ext.form.field.VTypes')) {
        Ext.apply(Ext.form.field.VTypes, {
            emailText: '该输入项必须是电子邮件地址，格式如： "user@example.com"',
            urlText: '该输入项必须是URL地址，格式如： "http:/' + '/www.example.com"',
            alphaText: '该输入项只能包含半角字母和_',
            //update
            alphanumText: '该输入项只能包含半角字母,数字和_' //update
        });
    }
    //add HTMLEditor's tips by andy_ghg
    Ext.define("Ext.locale.zh_CN.form.field.HtmlEditor", {
        override: "Ext.form.field.HtmlEditor",
        createLinkText: '添加超级链接:'
    }, function() {
        Ext.apply(Ext.form.field.HtmlEditor.prototype, {
            buttonTips: {
                bold: {
                    title: '粗体 (Ctrl+B)',
                    text: '将选中的文字设置为粗体',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                italic: {
                    title: '斜体 (Ctrl+I)',
                    text: '将选中的文字设置为斜体',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                underline: {
                    title: '下划线 (Ctrl+U)',
                    text: '给所选文字加下划线',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                increasefontsize: {
                    title: '增大字体',
                    text: '增大字号',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                decreasefontsize: {
                    title: '缩小字体',
                    text: '减小字号',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                backcolor: {
                    title: '以不同颜色突出显示文本',
                    text: '使文字看上去像是用荧光笔做了标记一样',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                forecolor: {
                    title: '字体颜色',
                    text: '更改字体颜色',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                justifyleft: {
                    title: '左对齐',
                    text: '将文字左对齐',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                justifycenter: {
                    title: '居中',
                    text: '将文字居中对齐',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                justifyright: {
                    title: '右对齐',
                    text: '将文字右对齐',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                insertunorderedlist: {
                    title: '项目符号',
                    text: '开始创建项目符号列表',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                insertorderedlist: {
                    title: '编号',
                    text: '开始创建编号列表',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                createlink: {
                    title: '转成超级链接',
                    text: '将所选文本转换成超级链接',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                sourceedit: {
                    title: '代码视图',
                    text: '以代码的形式展现文本',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                }
            }
        });
    });

    Ext.define("Ext.locale.zh_CN.grid.header.Container", {
        override: "Ext.grid.header.Container",
        sortAscText: "正序",
        //update
        sortDescText: "倒序",
        //update
        lockText: "锁定列",
        //update
        unlockText: "解除锁定",
        //update
        columnsText: "列"
    });

    Ext.define("Ext.locale.zh_CN.grid.PropertyColumnModel", {
        override: "Ext.grid.PropertyColumnModel",
        nameText: "名称",
        valueText: "值",
        dateFormat: "y年m月d日"
    });

});

var avmon = {};
avmon.common = {};
avmon.common.ok = '确定';
avmon.common.cancel = '取消';
avmon.common.save = '保存';
avmon.common.close = '关闭';
avmon.common.reset = '重置';
avmon.common.reminder = '提示';
avmon.common.saving = '正在保存，请稍等...';
avmon.common.log = '日志';
avmon.common.clear = '清除';
avmon.common.message = '信息';
avmon.common.errorMessage = '错误信息';
avmon.common.systemError = '系统错误';
avmon.common.failure = '失败';
avmon.common.deleted = '删除';
avmon.common.refresh = '刷新';
avmon.common.loading = '正在加载，请稍候...';
avmon.common.loadingWait = '正在努力加载，请稍候...';
avmon.common.host='主机';


avmon.alarm = {}; 
avmon.alarm.retrieve = '检索';
avmon.alarm.subscribeAlarm = '告警订阅';
avmon.alarm.alreadyClearAlarm = '已清除告警';  
avmon.alarm.normalAlarm = '一般告警';
avmon.alarm.minorAlarm = '次要告警';
avmon.alarm.mainAlarm = '主要告警';
avmon.alarm.seriousAlarm = '严重告警';
avmon.alarm.myAlarm = '我的告警 ';
avmon.alarm.myAlarm0 = '我的告警 0';
avmon.alarm.newAlarm = '新告警 ';
avmon.alarm.newAlarm0 = '新告警 0';
avmon.alarm.advancedSearch = '高级检索';
avmon.alarm.afterPageText = '页/{0}';
avmon.alarm.beforePageText = '第';
avmon.alarm.displayMsg = '显示第 {0} 条到 {1} 条记录，一共 {2} 条';
avmon.alarm.emptyMsg = '(无数据)';
avmon.alarm.firstText = '第一页';
avmon.alarm.lastText = '最后一页';
avmon.alarm.nextText = '下一页';
avmon.alarm.prevText = '上一页';
avmon.alarm.level = '级别';
avmon.alarm.status = '状态';
avmon.alarm.operationSystem = '业务系统';
avmon.alarm.devicename = '设备名称';
avmon.alarm.hostname = '主机名';
avmon.alarm.ipaddress = 'IP地址';
avmon.alarm.alarmTitle = '告警标题';
avmon.alarm.lastTime = '最后时间';
avmon.alarm.times = '次数';
avmon.alarm.startTime = '开始时间';
avmon.alarm.results = '处理结果';
avmon.alarm.actorUser = '处理人';
avmon.alarm.position = '位置';
avmon.alarm.usage = '用途';
avmon.alarm.administrator = '管理员';
avmon.alarm.alarmContent = '告警内容';
avmon.alarm.deviceSerialNumber = '设备序列号';
avmon.alarm.assetSerialNumber = '资产序列号';
avmon.alarm.alarmMessage = '告警信息';
avmon.alarm.facilityInformation = '设备信息';
avmon.alarm.severityLevel = '严重级别';
avmon.alarm.alarmStatus = '告警状态';
avmon.alarm.alarmOriginalContent = '告警原始内容';
avmon.alarm.finallyAlarmTime = '最后告警时间';
avmon.alarm.frequency = '发生次数';
avmon.alarm.firstAlarmTime = '首次告警时间';
avmon.alarm.claimTime = '认领时间';
avmon.alarm.claimant = '认领人';
avmon.alarm.alarmSource = '告警来源';
avmon.alarm.handlingSuggestion = '处理意见';
avmon.alarm.handlingTime = '处理时间';
avmon.alarm.historyAlarm = '历史告警';
avmon.alarm.clearingTime = '清除时间';
avmon.alarm.fortyEightClearedAlarm = '48小时内已清除告警';
avmon.alarm.alreadyLastQualifiedRecord = '已经是最后一条符合条件的记录了!';
avmon.alarm.notRetrievedMatchingRecords = '没有检索到匹配记录!';
avmon.alarm.SMSReceivingTime = '短信接收时间';
avmon.alarm.wholeTime = '全部时间';
avmon.alarm.workingTime = '工作时间';
avmon.alarm.viewID = '视图ID';
avmon.alarm.viewName = '视图名称';
avmon.alarm.receiveWarningLevel = '接收告警的级别';
avmon.alarm.receiveMode = '接收方式';
avmon.alarm.SMS = '短信';
avmon.alarm.dailyMaxNumberOfTextMessage = '短信每日最大数量';
avmon.alarm.whetherStarts = '是否启动';
avmon.alarm.lastUpdateTime = '最后更新时间';
avmon.alarm.alarmInformation = '告警相关信息';
avmon.alarm.timeHorizon = '时间范围';
avmon.alarm.alarmStartTime = '告警开始时间';
avmon.alarm.hour = '时';
avmon.alarm.minute = '分';
avmon.alarm.reach = '到';
avmon.alarm.claim = '认领';
avmon.alarm.selectOperateLine = '请先选择您要操作的行!';
avmon.alarm.knowledge = '知识';
avmon.alarm.classify = '分类';
avmon.alarm.detail = '详细内容';
avmon.alarm.checking = '检查中...';
avmon.alarm.processed = '已处理';
avmon.alarm.details = '详情';
avmon.alarm.chooseYourDataToOperate = '请先选择您要操作的数据!';
avmon.alarm.onlySelectARowToViewDetailedInformation = '只能选择一行数据进行查看详细信息!';
avmon.alarm.well = '信息';
avmon.alarm.processing = '处理中';
avmon.alarm.claimed = '已认领';
avmon.alarm.viewRealTimePerformance = '查看实时性能';
avmon.alarm.onlySelectARowToViewDetailedPerformance = '只能选择一条数据查看实时性能!';
avmon.alarm.alarmForward = '告警前转';
avmon.alarm.updateAlarmStatus = '批量更新告警状态';

avmon.alarm.alarmHost = '告警主机';
avmon.alarm.alarmIndicator = '告警指标';
avmon.alarm.alarmTime = '告警发生时间';
avmon.alarm.accepter = '接受人';
avmon.alarm.alarmLevel = '告警级别';
avmon.alarm.alarmForwardFailed = '告警前转失败! ';

avmon.batchdeploy = {};
avmon.batchdeploy.equipmentID = '设备ID';
avmon.batchdeploy.equipment = '设备';
avmon.batchdeploy.resourceType = '资源类型';
avmon.batchdeploy.ipaddress = 'IP地址';
avmon.batchdeploy.status = '状态';
avmon.batchdeploy.operatingSystem = '操作系统';
avmon.batchdeploy.version = '版本';
avmon.batchdeploy.batchDeploy = '批量部署 - 发现已安装Agent的设备';
avmon.batchdeploy.firstStep = '第一步：请输入ＩＰ段，并点击开始扫描';
avmon.batchdeploy.startIp = '开始IP';
avmon.batchdeploy.stopIp = '结束IP';
avmon.batchdeploy.startScan = '开始扫描';
avmon.batchdeploy.startDeploy = '开始部署';
avmon.batchdeploy.monitorInstance = 'Monitor实例';
avmon.batchdeploy.monitor = 'Monitor';
avmon.batchdeploy.message = '消息';
avmon.alarm.alarmsCleaned='清除成功';

avmon.config = {};
avmon.config.targetList = '指标列表';
avmon.config.kpiHistoryTendency = 'KPI历史趋势';
avmon.config.host = '主机';
avmon.config.hosts = '主机：';
avmon.config.time = '时间';
avmon.config.to = '至';
avmon.config.search = '查询';
avmon.config.selectKPI = '请选择KPI!';
avmon.config.kpiNumberNotMoreThanFive = 'KPI数量不能超过5个!';
avmon.config.times = '时间：';
avmon.config.kpiInstance = 'KPI实例：';
avmon.config.value = '值：';
avmon.config.loadFail = '加载失败!';
avmon.config.kpi = 'KPI：';
avmon.config.averageValue = '平均值：';
avmon.config.maxValue = '最大值：';
avmon.config.minValue = '最小值：';
avmon.config.seriousAlarmThresholds = '严重告警阀值：';
avmon.config.noHistoricalInformation = '没有历史信息!';
avmon.config.agentDetail = 'Agent 详细';
avmon.config.status = '心跳状态';
avmon.config.updateStatus = '数据传输状态';
avmon.config.startGathering = '启动采集';
avmon.config.stopGathering = '停止采集';
avmon.config.wrong = '错误';
avmon.config.ampList = 'AMP 列表';
avmon.config.deployDefaultAMP = '部署默认amp';
avmon.config.addAMP = '添加AMP';
avmon.config.ampUninstall = 'AMP卸载';
avmon.config.ampScriptIssued = 'AMP脚本下发';
avmon.config.ampSchedulingIssued = 'AMP调度下发';
avmon.config.ampInstanceID = 'AMP实例ID';
avmon.config.ampName = 'AMP名称';
avmon.config.installDirectory = '安装目录';
avmon.config.currentVersion = '当前版本';
avmon.config.schedulingConfigString = '调度配置串';
avmon.config.notInstall = '未安装';
avmon.config.running = '正在运行';
avmon.config.stopRun = '停止运行';
avmon.config.unkonwn = '未知';
avmon.config.ampType = 'AMP类型';
avmon.config.scriptIssuedTime = '脚本下发时间';
avmon.config.schedulIssuedTime = '调度下发时间';
avmon.config.configIssuedTime = '配置下发时间';
avmon.config.beforePageText = '第';
avmon.config.afterPageText = '页,共{0}页';
avmon.config.displayMsg = '显示 {0} - {1} 条，共计 {2} 条';
avmon.config.emptyMsg = '没有数据';
avmon.config.startingWait = '正在启动请稍等...';
avmon.config.starting = '启动中...';
avmon.config.operationFail = '操作失败！';
avmon.config.stoppingWait = '正在停止请稍等...';
avmon.config.stopping = '停止中...';
avmon.config.selectNeedUninstallAMP = '请选择需要卸载的AMP！';
avmon.config.uninstallAMPWait = '正在卸载AMP请稍等...';
avmon.config.uninstalling = '卸载中...';
avmon.config.selectNeedIssuedScriptAMP = '请选择需要下发脚本的AMP！';
avmon.config.beIssuedByScriptPleaseWait = '正在下发脚本请稍等...';
avmon.config.issuing = '下发中...';
avmon.config.selectNeedSchedulIssuedAMP = '请选择需要下发调度的AMP！';
avmon.config.issuingSchedulWait = '正在下发调度请稍等...';
avmon.config.selectNeedStartAMP = '请选择需要启动的AMP！';
avmon.config.startGatheringStart = '采集启动';
avmon.config.stopGatheringStop = '采集停止';
avmon.config.ampConfiguration = 'AMP 配置 - ';
avmon.config.agentManagement = 'Agent管理';
avmon.config.referenceHostName = '参考主机名';
avmon.config.systemId = '系统标识';
avmon.config.system = '操作系统';
avmon.config.systemVersion = '操作系统版本';
avmon.config.agentVersion = 'Agent版本';
avmon.config.lastActivity = '最后心跳时间';
avmon.config.lastUpdateTime='最后数据传输时间'
avmon.config.ampInstallSituation = 'AMP安装情况';
avmon.config.agentAcquisitionStartupState = 'agent采集启动状态';
avmon.config.agentStatus = 'Agent状态';
avmon.config.configuration = '配置';
avmon.config.updateAgent = '升级Agent';
avmon.config.retrieve = '检索';
avmon.config.whetherToDelete = '是否删除?';
avmon.config.whetherUpgrade = '是否进行升级?';
avmon.config.updatingAgentWait = '正在升级Agent，请稍等...';
avmon.config.updatingAgentWait = '升级Agent...';
avmon.config.netErrorUpdateFail = '发生网络错误，更新失败！';
avmon.config.normal = '正常';
avmon.config.cannotCreatObjectForAgent = '无法为Agent所在机器创建监控对象.';
avmon.config.addMonitorObjectFail = '添加监控对象失败';
avmon.config.ampInstance = 'Amp实例';
avmon.config.whetherStart = '是否启用';
avmon.config.waiting = '请等待';
avmon.config.dataSubmitting = '数据正在提交中......';
avmon.config.ampAddedSuccess = 'AMP添加成功！';
avmon.config.fillOutCompleteInformation = '请填写完整信息！';
avmon.config.ampConfig = 'AMP配置';
avmon.config.start = '启动';
avmon.config.stop = '停止';
avmon.config.issuedByScript = '下发脚本';
avmon.config.issueByConfiguration = '下发配置';
avmon.config.iloHostList = 'ILO主机列表';
avmon.config.addILOHost = '增加ILO主机';
avmon.config.configILOHost = '配置ILO主机';
avmon.config.deleteILOHost = '删除ILO主机';
avmon.config.IssuedDispatch = '下发调度';
avmon.config.iloHostIP = 'ILO主机IP';
avmon.config.hostname = '主机名';
avmon.config.userName = '用户名'; 
avmon.config.password = '密码';
avmon.config.selectILOHostForConfig = '请先选择且只选择一个要配置的ILO主机!';
avmon.config.selectILOHostForDelete = '请先选择您要删除的ILO主机!';
avmon.config.selectILOHost = '请先选择ILO主机!';
avmon.config.startingAMPInstance = '正在启动AMP实例请稍等...';
avmon.config.stoppingAMPInstance = '正在停止AMP实例请稍等...';
avmon.config.issuingConfigurationPleaseWait = '正在下发配置请稍等...';
avmon.config.iloHostConfiguration = 'ILO主机配置';
avmon.config.basicConfiguration = '基本配置';
avmon.config.hostIP = '主机IP';
avmon.config.reservedConfigurationItermOne = '预留配置项1';
avmon.config.reservedConfigurationItermTwo = '预留配置项2';
avmon.config.reservedConfigurationItermThree = '预留配置项3';
avmon.config.schedulingPolicy = '调度策略';
avmon.config.editSchedulingPolicy = '编辑调度策略';
avmon.config.issueByChosenPolicy = '下发选中策略';
avmon.config.issueByAllPolicy = '下发所有策略';
avmon.config.enableFlag = '是否启用';
avmon.config.kpiName = 'KPI 名称';
avmon.config.group = '组';
avmon.config.pleaseEnterIP = '请输入IP!';
avmon.config.pleaseEnterHostName = '请输入主机名!';
avmon.config.pleaseEnterUserName = '请输入用户名!';
avmon.config.pleaseEnterPassword = '请输入密码!';
avmon.config.pleaseSelectASetOfStrategiesForEdit = '请选择一组策略进行编辑！';
avmon.config.pleaseSelectNeedIssueStrategy = '请选择需要下发的策略！';
avmon.config.issuingStrategyPleaseWait = '正在下发策略请稍等...';
avmon.config.issueStrategySuccess = '策略下发成功！';
avmon.config.issueStrategyFailure = '下发策略失败';
avmon.config.savingDataPleaseWait = '数据正在保存请稍等...';
avmon.config.saving = '保存中...';
avmon.config.schedulingPolicyChangeSuccess = '调度策略修改成功！';
avmon.config.schedulingPolicyChangeFailure = '调度策略修改失败';
avmon.config.confirmChanges = '确认修改';
avmon.config.confirmTheOperation = '您所编辑的调度策略将应用于其他具有相同组的KPI Code，是否确认操作?';
avmon.config.parameterName = '参数名';
avmon.config.value = '值';
avmon.config.saveSuccess = '保存成功！';
avmon.config.saveFailure = '保存失败！';
avmon.config.ampConfiguration = 'AMP 配置';
avmon.config.mustFillOut = ' 必须填写！';
avmon.config.savingDataPleaseWait = '正在保存数据请稍等...';
avmon.config.dataSavingSuccessfully = '数据保存成功！';
avmon.config.virtualHost = '虚拟主机';
avmon.config.hostStatus = '主机状态';
avmon.config.monitoring = '监控';
avmon.config.noMonitoring = '未监控';
avmon.config.monitoringStatus = '监控状态';
avmon.config.refreshVirtualHostList = '刷新虚拟主机列表';
avmon.config.startMonitor = '启用监控';
avmon.config.stopMonitor = '停止监控';
avmon.config.remove = '移除';
avmon.config.collectionSchedule = '采集调度';
avmon.config.saveIssuedCollectionSchedule = '保存下发采集调度';
avmon.config.invokeInterfaceFailure = '调用接口失败!';
avmon.config.pleaseSelectNotMonitorObject = '请选择未监控对象！';
avmon.config.pleaseSelectMonitorObject = '请选择监控对象！';
avmon.config.pleaseSelectRemoveObject = '请选择移除对象！';
avmon.config.whetherRemove = '是否移除?';

avmon.config.chooseDevice = '选择设备';
avmon.config.deviceIP = '设备IP';
avmon.config.deviceType = '设备类型';
avmon.config.deviceDescription = '设备描述';
avmon.config.choose = '选择';
avmon.config.pleaseChooseDevice = '请选择设备!';
avmon.config.name = '名称';
avmon.config.updateTime = '更新时间';
avmon.config.add = '添加';
avmon.config.reportDataSource = '报表数据源';
avmon.config.selectOperateLine = '请先选择您要操作的行!';
avmon.config.modify = '修改';
avmon.config.root = '根节点';
avmon.config.theMenu = '所属菜单';
avmon.config.basicInformation = '基本信息';
avmon.config.reportNumber = '报表编号';
avmon.config.reportName = '报表名称';
avmon.config.dataSource = '数据源';
avmon.config.templateFile = '模板文件';
avmon.config.pleaseSelectTemplateFile = '请选择模板文件';
avmon.config.browse = '浏览';
avmon.config.type = '类型';
avmon.config.startTime = '开始时间';
avmon.config.cycle = '周期';
avmon.config.emailServer = '邮件服务器';
avmon.config.reportAttachment = '是否发附件';
avmon.config.reportAttachmentType = '附件格式';
avmon.config.reportAttachment = '是否发附件';
avmon.config.reportAttachmentType = '附件格式';
avmon.config.reportManagement = '报表管理';
avmon.config.templatePath = '模板路径';
avmon.config.networkInspectionData = '网络巡检数据';
avmon.config.monitorType = '监控类型';
avmon.config.kpiThresholdValue = 'KPI阀值';
avmon.config.all = '全部';
avmon.config.backupConfigurationInformation = '配置备份信息';
avmon.config.devicename = '设备名称';
avmon.config.configuartionFile = '配置文件';
avmon.config.operation = '操作';
avmon.config.monitorObjectNameOrIP = '监控对象（名称或IP）';
avmon.config.clearingTimeMinutes = '清除时限（分钟）';
avmon.config.alarmAutomaticallyClearRules = '告警自动清除规则';
avmon.config.monitoringObject = '监控对象';
avmon.config.alarmContentRegularExpression = '告警内容（正则表达式）';
avmon.config.alarmFilteringRules = '告警过滤规则';
avmon.config.aggregationType = '聚合类型';
avmon.config.dataType = '数据类型';
avmon.config.whetherRetainTheRecentRawData = '是否保留近期原始数据';
avmon.config.yes = '是';
avmon.config.no = '否';
avmon.config.storedToDatabase = '存储至数据库';
avmon.config.whetherComputationalKPI = '是否为计算型KPI';
avmon.config.kpiEncoding = 'KPI编码';
avmon.config.kpiEnglishName = 'KPI英文名称';
avmon.config.kpiChineseName = 'KPI中文名称';
avmon.config.decimal = '小数位数';
avmon.config.unit = '单位';
avmon.config.calculationMethod = '计算方法';
avmon.config.storageCycleMinutes = '存储周期（分钟）';
avmon.config.kpiInformation = 'KPI信息';
avmon.config.storageCycle = '存储周期';
avmon.config.selectKPINotMoreThanOne = '请先选择KPI且不能超出1个!';
avmon.config.alarmThresholdValue = '告警阀值';
avmon.config.judgmentMethod = '判断方法';
avmon.config.GreaterThan = '大于';
avmon.config.lessThan = '小于';
avmon.config.equalTo = '等于';
avmon.config.minorAlarm = '次要告警';
avmon.config.flashingNumber = '闪动次数';
avmon.config.kpiAlarmThresholdValue = 'KPI告警阀值';
avmon.config.performMergeTimeWindowMinute = '执行合并时间窗口（分）';
avmon.config.alarmMergerRules = '告警合并规则';
avmon.config.view = '视图';
avmon.config.alarmViewSubscriptionManagement = '告警视图订阅管理';
avmon.config.userId = '用户ID';
avmon.config.whetherReceiveEmail = '是否Email接收';
avmon.config.whetherReceiveSMS = '是否短信接收';
avmon.config.SMSAcceptTime = '短信接受时间';
avmon.config.AlarmValidationRules = '告警规则验证';
avmon.config.module = '模块';
avmon.config.verifyStatus = '验证状态';
avmon.config.validationRules = '验证规则';
avmon.config.originalContent = '原始内容';
avmon.config.translatedContent = '翻译后内容';
avmon.config.accumulativeAlarmTimes = '累计告警次数';
avmon.config.alarmTimeWindowMinute = '发生累计告警所在时间窗口（分）';
avmon.config.afterUpgradingAlarmLevel = '升级后告警级别';
avmon.config.alarmUpgradingRule = '告警升级规则';
avmon.config.deviceStatus = '设备状态';
avmon.config.recentReportTime = '最近报表时间';
avmon.config.networkMonitoringDeviceManagement = '网络监控设备管理';
avmon.config.lastestReportTime = '最新报表时间';
avmon.config.thresholdValueType = '阀值类型';
avmon.config.string = '字符串';
avmon.config.temperature = '温度';
avmon.config.integer = '整数';
avmon.config.notAllowModifyDevice = '不允许修改设备';
avmon.config.ignoreValue = '忽略值';
avmon.config.errorValues = '错误值';
avmon.config.networkMonitoringThresholdValueManagement = '网络监控指标阀值管理';
avmon.config.chineseName = '中文名称';
avmon.config.kpiType = 'KPI类型';
avmon.config.networkMonitoringIndexManagement = '网络监控指标管理';

avmon.config.inspectionOrders = '巡检命令';
avmon.config.exitCommand = '退出命令';
avmon.config.exitCommandFirst = '退出命令1';
avmon.config.exitCommandSecond = '退出命令2';
avmon.config.networkInspectionDeviceManagement = '网络巡检设备管理';
avmon.config.inspectionType = '巡检类型';
avmon.config.version = '版本号';
avmon.config.deployHost = '部署主机';
avmon.config.command = '命令 ';
avmon.config.pleaseInpuDeviceIP = '请录入设备IP!';
avmon.config.selectDeviceType = '请选择设备类型!';
avmon.config.exported = '导出';
avmon.config.exitWayFirst = '退出方式1';
avmon.config.exitWaySecond = '退出方式2';
avmon.config.deleteSuccess = '删除成功！';
avmon.config.exportSuccess = '导出成功！';
avmon.config.exportFailure = '导出失败！';
avmon.config.filterCondition = '过滤条件';
avmon.config.condition = '条件';
avmon.config.column = '字段';
avmon.config.operationalCharacter = '运算符';
avmon.config.refresh = '刷新';
avmon.config.directory = '目录';
avmon.config.monitorViewManagement = '监控视图管理';
avmon.config.newIncrease = '新增';
avmon.config.other = '其他';
avmon.config.combinationalRule = '组合规则';
avmon.config.pleaseEnterDirectoryName = '请输入目录名称:';
avmon.config.viewType = '视图类型';
avmon.config.publicView = '公共视图';
avmon.config.groupView = '组视图';
avmon.config.privateView = '私有视图';
avmon.config.directoryName = '目录名称';
avmon.config.pleaseEnterViewName = '请输入视图名称:';
avmon.config.selectNode = '请选择节点!';

avmon.config.netElmentARPInquire = '网元arp查询';
avmon.config.netElement = '网元';
avmon.config.date = '日期';
avmon.config.netElementInquire = '网元查询';
avmon.config.setLimitOfBase = '设置为基限';
avmon.config.netElementIP = '网元IP';
avmon.config.whetherLimitOfBase = '是否为基限?';
avmon.config.pleaseSelectnetElement = '请选择网元!';
avmon.config.selectNetElementNotInquireOrNull = '选择网元未查询或查询结果为空!';
avmon.config.promptMessage = '提示信息';
avmon.config.setSuccessfully =	'设置成功！'; 

avmon.config.kpiDynamicWarningThresholdValue = 'KPI动态告警阀值';
avmon.config.alarmLowestThresholdValue = '告警最低阀值';
avmon.config.alarmMaximumThresholdValue = '告警最大阀值';
avmon.config.stopTime = '结束时间';
avmon.config.alarmContentTemplate = '告警内容模板';
avmon.config.fixedThreshold = '固定阀值';
avmon.config.limitOfBaseThreshold = '基线阀值';
avmon.config.alarmUpperTolerability = '告警上容忍度';
avmon.config.alarmFloorTolerability = '告警下容忍度';
avmon.config.absoluteValue = '绝对值';
avmon.config.percent = '百分比';
avmon.config.samplingFrequency = '采集频率';
avmon.config.day = '日';
avmon.config.week = '周';
avmon.config.workday = '工作日';
avmon.config.holiday = '节假日';

avmon.config.startupMonitorning = '启动监控';
avmon.config.startingMonitorning = '正在启动监控...';
avmon.config.startupMonitorningFailed = '监控启动失败!';
avmon.config.onlyCanInputEnglishAndNumbers = '只能输入英文和数字';

avmon.config.monitorSaved = 'Monitor已保存！';
avmon.config.suspend = '暂停';
avmon.config.agentSuspended = 'Agent已暂停！';
avmon.config.restoreRun = '恢复运行';
avmon.config.agentSetRestoreRun = 'Agent已设置恢复运行！';
avmon.config.refreshMenu = '刷新菜单';
avmon.config.addHost = '添加主机';
avmon.config.inputContentCannotBeEmpty = '输入内容不能为空';
avmon.config.warning = '警告';
avmon.config.objectNumberCannotBeEmpty = '对象编号不能为空!';
avmon.config.objectNameCannotBeEmpty = '对象名称不能为空!';
avmon.config.parentCanNotBeEmpty='请选择父类!';
avmon.config.pleaseSelectObjectType = '请选择对象类型！';
avmon.config.pleaseSelectBusinessSystem = '请选择业务系统！';
avmon.config.wrongRetry = '发生错误，请稍候再试！';
avmon.config.newHost = '新主机';
avmon.config.pleaseConfirmWhetherDelete = '请确认是否删除?';
avmon.config.monitorObjectDeleted = '监控对象已删除！';
avmon.config.addMonitorHost = '添加监控主机';
avmon.config.monitoringObjectNumber = '监控对象编号';
avmon.config.hostName = '主机名称';
avmon.config.objectType = '对象类型';
avmon.config.ipaddress = 'IP地址';
avmon.config.operatingSystem = '操作系统';
avmon.config.agentLastUpdateTime = 'Agent最后更新时间';
avmon.config.hangUp = '挂起';
avmon.config.restore = '恢复';
avmon.config.CIProperty = 'CI属性';
avmon.config.installedEquipment = '已安装设备';
avmon.config.properties = '属性';
avmon.config.addMonitoringObjects = '添加监控对象';
avmon.config.chooseObjectType = '选择对象类型：';
avmon.config.objectNumberCannotBeModified = '(对象编号确定后无法修改，且必须唯一。)';
avmon.config.objectNumber = '对象编号';
avmon.config.objectName = '对象名称';
avmon.config.receivingPerformanceData = '(用于接收性能数据,主机对象可留空)';
avmon.config.allLevel = '所有级别';
avmon.config.typeId = '所属类别';
avmon.config.commentSql = 'Sql';

avmon.dashboard = {};
avmon.dashboard.content = '内容';
avmon.dashboard.severeAlarmInHalfAnHour = '半小时内严重告警';
avmon.dashboard.severeAlarmInAnHour = '一小时内严重告警';
avmon.dashboard.allOfSevereAlarm = '所有严重告警';
avmon.dashboard.allDevice = '所有设备';
avmon.dashboard.generalInfomation = '常规信息';
avmon.dashboard.cpuModel = 'CPU型号';
avmon.dashboard.cpu = 'CPU';
avmon.dashboard.operateFrequency = '运行频率';
avmon.dashboard.operateTime = '运行时间';
avmon.dashboard.currentUsers = '当前用户数';
avmon.dashboard.currentAlarmNumber = '当前告警数';
avmon.dashboard.keyProcesses = '关键进程';
avmon.dashboard.process = '进程';
avmon.dashboard.memory = '内存';
avmon.dashboard.userMemoryUsage = '[\'用户内存使用率\',\'系统\',\'可用\']';
avmon.dashboard.swapUsageRate = '[\'SWAP使用率\',\'空闲率\']';
avmon.dashboard.cacheReadHitRatio = 'Cache读命中率';
avmon.dashboard.cacheWriteHitRatio = 'Cache写命中率';
avmon.dashboard.dDayHHour = 'd日H时';
avmon.dashboard.latestWarning = '最新告警';
avmon.dashboard.networkPort = '网络接口';
avmon.dashboard.bandwidthAvailabilityRatio = '带宽利用率';
avmon.dashboard.sendingRate = '发送速率';
avmon.dashboard.receiveRate = '接收速率';
avmon.dashboard.packetSendSec = '发包/秒';
avmon.dashboard.packetReceptionSec = '收包/秒';
avmon.dashboard.topnProcesses = 'TOPN进程';
avmon.dashboard.diskIO = '磁盘IO';
avmon.dashboard.logicalVolume = '逻辑卷';
avmon.dashboard.powerSupplyFan = '电源风扇';
avmon.dashboard.powerSupplyConditionNormal = '电源状态：正常';
avmon.dashboard.fanConditionNormal = '风扇状态：正常';
avmon.dashboard.size = '大小';
avmon.dashboard.speed = '速度';
avmon.dashboard.disk = '磁盘';
avmon.dashboard.notDiskInfomationCanDisplay = '没有磁盘信息可以显示';
avmon.dashboard.networkCard = '网卡';
avmon.dashboard.powerSupply = '电源:';
avmon.dashboard.notRelatedDataCanDisplay = '没有相关数据可以显示';
avmon.dashboard.mainBoard = '主板';
avmon.dashboard.width = '宽度';
avmon.dashboard.fan = '风扇';
avmon.dashboard.rotateSpeed = '转速';
avmon.dashboard.crate = '机箱';
avmon.dashboard.resourceID = '资源ID';
avmon.dashboard.instance = '实例';
avmon.dashboard.ampInstanceNumber = 'Amp实例编号';
avmon.dashboard.aotoRefresh = '自动刷新';
avmon.dashboard.manualRefresh = '手工刷新';
avmon.dashboard.kpiDetail = 'KPI明细';
avmon.dashboard.networkCardName = '网卡名称';
avmon.dashboard.networkCardStatus = '网卡状态';
avmon.dashboard.networkFlowPKGTS = '网络流量 Pkgts';
avmon.dashboard.cumulativeSendPKGTS = '累积发送 Pkgts';
avmon.dashboard.cumulativeReceivePKGTS = '累积接收 Pkgts';
avmon.dashboard.userProcessesNumber = '用户进程数量';
avmon.dashboard.precessPercentage = '进程百分比';
avmon.dashboard.residualMemory = '剩余内存';
avmon.dashboard.usage = '使用率';
avmon.dashboard.writePS = '写入 p/s';
avmon.dashboard.readPS = '读出  p/s';
avmon.dashboard.literacyRateRWS = '读写速率 rw/s';
avmon.dashboard.path = '路径';
avmon.dashboard.notFindPartOfRealTimeData = '未发现该部分有效的实时数据。';
avmon.dashboard.mplogList = 'MPLog列表';
avmon.dashboard.keyword = '关键字';
avmon.dashboard.logEntry = '日志录入者';
avmon.dashboard.producer = '生产者';
avmon.dashboard.sensorType = '传感器类型';
avmon.dashboard.sensorNumber = '传感器数量';
avmon.dashboard.mpStatus = 'MP状态';
avmon.dashboard.noSubclassInfomation = '无子类信息.';
avmon.dashboard.alarm = '告警';
avmon.dashboard.item = '条';
avmon.dashboard.dashboard = '仪表板';
avmon.dashboard.manufacturer = '厂家';
avmon.dashboard.cpuUsageRate = 'CPU使用率';
avmon.dashboard.cpuVacancyRage = 'CPU闲置率';
avmon.dashboard.delayRate = '延迟率';
avmon.dashboard.cacheUsageRate = 'Cache使用率';
avmon.dashboard.writeDelayUtilization = '写延迟利用率';
avmon.dashboard.netPortInfomation = '网口信息';
avmon.dashboard.frontPort = '前端口';
avmon.dashboard.delayRead = '读延迟';
avmon.dashboard.delayWrite = '写延迟';
avmon.dashboard.rate = '速率';
avmon.dashboard.systemStatus = '系统状态';
avmon.dashboard.powerStatusUnknown = '电源状态：未知';
avmon.dashboard.fanStatusNuknown = '风扇状态：未知';
avmon.dashboard.groupInfomation = '组信息';
avmon.dashboard.groupName = '组名';
avmon.dashboard.groupIOPS = '组IOPS';
avmon.dashboard.groupDelayRate = '组延迟率';
avmon.dashboard.diskInformation = '盘信息';
avmon.dashboard.diskName = '盘名';
avmon.dashboard.diskIOPS = '盘IOPS';
avmon.dashboard.diskDelayRate = '盘延迟率';
avmon.dashboard.deviceList = '设备列表';
avmon.dashboard.alarmDevices = '告警设备';
avmon.dashboard.tai = '台';
avmon.dashboard.healthDegree = '健康度';
avmon.dashboard.point = '分';
avmon.dashboard.database = '数据库';
avmon.dashboard.ge = '个';
avmon.dashboard.network = '网络设备';
avmon.dashboard.availability = '可用性';
avmon.dashboard.storage = '存储';
avmon.dashboard.middleware = '中间件';
avmon.dashboard.business = '业务';
avmon.dashboard.vmhost = '虚拟机';
avmon.dashboard.alarmList = '告警列表';
avmon.dashboard.realipSetSuccess = '物理IP设置成功';
avmon.dashboard.realipSetFailed = '物理IP设置失败';
avmon.dashboard.hostDashboard='主机仪表板';
avmon.dashboard.kpiTrend='KPI趋势查询';
avmon.dashboard.kpiView='KPI视图';
avmon.dashboard.alarmList='告警列表';
avmon.dashboard.vmList='虚拟机列表';
avmon.dashboard.vmDashboard='虚拟机仪表板';
avmon.dashboard.iloDashboard='ILO主机仪表板';
avmon.dashboard.physicalEngineDashboard='物理主机仪表板';
avmon.dashboard.resourceView = '资源状态视图';
avmon.dashboard.deviceStatusView = '设备状态视图';
avmon.dashboard.totalView = '总体监控视图';
avmon.dashboard.storageDashboard='存储仪表板';
avmon.dashboard.dataCenter = '数据中心';

avmon.deploy = {};
avmon.deploy.addMonitorHost = '添加监控主机';
avmon.deploy.monitoringObjectNumber = '监控对象编号';
avmon.deploy.hostName = '主机名称';
avmon.deploy.objectType = '对象类型';
avmon.deploy.ipaddress = 'IP地址';
avmon.deploy.operatingSystem = '操作系统';
avmon.deploy.agentStatus = 'Agent状态';
avmon.deploy.unknown = '未知';
avmon.deploy.hangUp = '挂起';
avmon.deploy.restore = '恢复';
avmon.deploy.CIProperty = 'CI属性';
avmon.deploy.deployedEquipment = '已部署设备';
avmon.deploy.deployed = '已部署';
avmon.deploy.undeployed = '未部署';
avmon.deploy.download = '下载';
avmon.deploy.monitoringAgent = '监控Agent';
avmon.deploy.deployedStatus = '部署状态';
avmon.deploy.ampInstance = 'Amp实例';
avmon.deploy.title = '标题';
avmon.deploy.currentVersion = '当前版本';
avmon.deploy.installAndUpdateAMP = '更新AMP';
avmon.deploy.referenceHostName = '参考主机名';
avmon.deploy.systemId = '系统标识';
avmon.deploy.system = '操作系统';
avmon.deploy.systemVersion = '操作系统版本';
avmon.deploy.agentVersion = 'Agent版本';
avmon.deploy.lastActivity = '最后活动时间';
avmon.deploy.exceed = '超过';
avmon.deploy.unsentData = '分钟未送数据';
avmon.deploy.ampInstallSituation = 'AMP安装情况';
avmon.deploy.agentUpgrade = 'Agent升级';
avmon.deploy.hostPingStatus = '主机Ping状态';
avmon.deploy.collectTime = '采集时间';
avmon.deploy.pingStatus = 'PING状态';
avmon.deploy.retrieve = '检索';
avmon.deploy.choiceBeIssuedHost = '请先选择要下发的主机!';
avmon.deploy.deployingLaterOn = '正在部署，请稍后...';
avmon.deploy.beIssueResult = '下发结果';
avmon.deploy.changeTacticsIssued = '是否要修改下发策略?';
avmon.deploy.whetherUpgrade = '是否进行升级?';
avmon.deploy.choiseBeUpgradedHost = '请先选择要升级的主机!';
avmon.deploy.ampConfiguration = 'AMP 配置';
avmon.deploy.ampName = 'AMP名称';
avmon.deploy.status = '状态';
avmon.deploy.start = '启动';
avmon.deploy.stop = '停止';
avmon.deploy.issuedByScript = '下发脚本';
avmon.deploy.issueByConfiguration = '下发配置';
avmon.deploy.schedulingPolicy = '调度策略';
avmon.deploy.kpiName = 'KPI名称';
avmon.deploy.schedulingConfigurationString = '调度配置串';
avmon.deploy.group = '组';
avmon.deploy.editSchedulingPolicy = '编辑调度策略';
avmon.deploy.issueByChosenPolicy = '下发选中策略';
avmon.deploy.issueByAllPolicy = '下发所有策略';
avmon.deploy.startingAMPInstance = '正在启动AMP实例请稍等...';
avmon.deploy.starting = '启动中...';
avmon.deploy.running = '正在运行';
avmon.deploy.operationFailure = '操作失败！';
avmon.deploy.stoppingAMPInstance = '正在停止AMP实例请稍等...';
avmon.deploy.stopRunning = '停止运行';
avmon.deploy.beIssuedByScriptPleaseWait = '正在下发脚本请稍等...';
avmon.deploy.issuing = '下发中...';
avmon.deploy.issuingConfigurationPleaseWait = '正在下发配置请稍等...';
avmon.deploy.error = '错误';
avmon.deploy.mustFillOut = ' 必须填写！';
avmon.deploy.savingDataPleaseWait = '正在保存数据请稍等...';
avmon.deploy.saving = '保存中...';
avmon.deploy.dataSavingSuccessfully = '数据保存成功！';
avmon.deploy.pleaseSelectASetOfStrategiesForEdit = '请选择一组策略进行编辑！';
avmon.deploy.pleaseSelectNeedIssueStrategy = '请选择需要下发的策略！';
avmon.deploy.schedulingPolicyChangeSuccess = '调度策略修改成功！';
avmon.deploy.schedulingPolicyChangeFailure = '调度策略修改失败';
avmon.deploy.confirmChanges = '确认修改';
avmon.deploy.confirmTheOperation = '您所编辑的调度策略将应用于其他具有相同组的KPI Code，是否确认操作?';
avmon.deploy.issuingStrategyPleaseWait = '正在下发策略请稍等...';
avmon.deploy.issueStrategySuccess = '策略下发成功！';
avmon.deploy.issueStrategyFailure = '下发策略失败';
avmon.deploy.properties = '属性';
avmon.deploy.value = '值';
avmon.deploy.addMonitoringObjects = '添加监控对象';
avmon.deploy.chooseObjectType = '选择对象类型：';
avmon.deploy.objectNumberCannotBeModified = '(对象编号确定后无法修改，且必须唯一。)';
avmon.deploy.objectNumber = '对象编号';
avmon.deploy.objectName = '对象名称';
avmon.deploy.receivingPerformanceData = '(用于接收性能数据,主机对象可留空)';
avmon.deploy.physicsIP = '物理IP';
avmon.deploy.allStatus = '所有状态';
avmon.deploy.allOS = '全部';
avmon.deploy.unNormal = '异常';
avmon.deploy.deployedTime='部署时间';
avmon.deploy.targetMoType = 'AMP目标类型';
avmon.deploy.ampUpgrade ='AMP 升级';
avmon.deploy.ampUpgradeTime = 'AMP更新时间';
avmon.deploy.osVersion = 'OS 版本';
avmon.deploy.logs ='下发日志'; 
avmon.deploy.loadWin ='加载窗口';
avmon.deploy.ampDetail = 'AMP下发详情';
avmon.deploy.pushing = '下发中...';
avmon.deploy.process = '当前进度：';
avmon.deploy.timeout = '下发超时';
avmon.deploy.pushAmpFinish ='下发完毕!';
avmon.deploy.finish = '完成'; 

avmon.equipmentCenter = {};
avmon.equipmentCenter.addMonitorHost = '添加监控主机';
avmon.equipmentCenter.monitoringObjectNumber = '监控对象编号';
avmon.equipmentCenter.hostName = '主机名称';
avmon.equipmentCenter.objectType = '对象类型';
avmon.equipmentCenter.ipaddress = 'IP地址';
avmon.equipmentCenter.operatingSystem = '操作系统';
avmon.equipmentCenter.agentStatus = 'Agent状态';
avmon.equipmentCenter.unknown = '未知';
avmon.equipmentCenter.agentLastUpdateTime = 'Agent最后更新时间';
avmon.equipmentCenter.hangUp = '挂起';
avmon.equipmentCenter.restore = '恢复';
avmon.equipmentCenter.CIProperty = 'CI属性';
avmon.equipmentCenter.retrieve = '检索';
avmon.equipmentCenter.refresh = '刷新';
avmon.equipmentCenter.newlyIncreased = '新增';
avmon.equipmentCenter.remove = '移除';
avmon.equipmentCenter.installedEquipment = '已安装设备';
avmon.equipmentCenter.properties = '属性';
avmon.equipmentCenter.value = '值';
avmon.equipmentCenter.addMonitoringObjects = '添加监控对象';
avmon.equipmentCenter.chooseObjectType = '选择对象类型：';
avmon.equipmentCenter.objectNumberCannotBeModified = '(对象编号确定后无法修改，且必须唯一。)';
avmon.equipmentCenter.objectNumber = '对象编号';
avmon.equipmentCenter.objectName = '对象名称';
avmon.equipmentCenter.receivingPerformanceData = '(用于接收性能数据,主机对象可留空)';
avmon.equipmentCenter.protocol = '监控协议';
avmon.equipmentCenter.protocolCanNotEmpty='监控协议不可为空';
avmon.equipmentCenter.generateBitCode = '二维码生成';
avmon.equipmentCenter.queryBitCode = '二维码查看';
avmon.equipmentCenter.print='打印';
avmon.equipmentCenter.confirmGenerate2Dcode= '请确认是否生成二维码?';
avmon.equipmentCenter.BitCodeGenerated='二维码已生成!';
avmon.equipmentCenter.BitCodeGeneratedFailed = '二维码生成失败!';
avmon.equipmentCenter.confirmDelete ='请确认是否删除?';
avmon.equipmentCenter.moDeleteSuccess = '监控对象已删除';
avmon.equipmentCenter.moDeleteFailed = '监控对象删除失败';

avmon.kpiCompare = {};
avmon.kpiCompare.time = '时间';
avmon.kpiCompare.pkiValue = 'KPI值';
avmon.kpiCompare.timeTo = '时间：';
avmon.kpiCompare.pkiAverageValue = 'KPI平均值：';
avmon.kpiCompare.hostList = '主机列表';
avmon.kpiCompare.kpiContrast = 'KPI对比';
avmon.kpiCompare.to = '至';
avmon.kpiCompare.selectContrastHost = '请选择比较的主机!';
avmon.kpiCompare.contrastHostCannotMoreThanFive = '比较的主机不能超过5台!';
avmon.kpiCompare.selectContrastKPI = '请选择比较的KPI!';
avmon.kpiCompare.loading = '正在加载……';
avmon.kpiCompare.kpiInstance = 'KPI实例：';
avmon.kpiCompare.value = '值：';
avmon.kpiCompare.loadFail = '加载失败!';
avmon.kpiCompare.query = '查询';
avmon.kpiCompare.sizeSelect = '粒度选择';
avmon.kpiCompare.originalData = '原始数据';
avmon.kpiCompare.byHour = '按小时汇总';
avmon.kpiCompare.byDay = '按天汇总';

avmon.system = {};
avmon.system.root = '根节点';
avmon.system.businessSystemID = '业务系统ID';
avmon.system.businessSystemName = '业务系统名称';
avmon.system.add = '添加';
avmon.system.businessSystemManagement = '业务系统管理';
avmon.system.moProperties = '监控对象属性管理';
avmon.system.selectOperateLine = '请先选择您要操作的行!';
avmon.system.whetherToDelete = '删除监控对象系统属性有可能影响系统正常功能!是否删除?';
avmon.system.beforePageText = '第';
avmon.system.afterPageText = '页,共{0}页';
avmon.system.displayMsg = '显示 {0} - {1} 条，共计 {2} 条';
avmon.system.emptyMsg = '没有数据';
avmon.system.modify = '修改';
avmon.system.department = '所属部门';
avmon.system.departmentName = '部门名称';
avmon.system.departmentManagement = '部门管理';
avmon.system.loadFail = '加载失败!';
avmon.system.licenseMessage = 'License信息';
avmon.system.expiryDate = '到期日期';
avmon.system.maxMonitorNumber = '监控最大数量';
avmon.system.waiting = '请稍候';
avmon.system.loadingFormData = '正在加载表单数据，请稍候...';
avmon.system.parentMenuName = '父菜单名';
avmon.system.whetherDisplay = '是否显示';
avmon.system.accordingSequence = '显示序列';
avmon.system.menuName = '菜单名';
avmon.system.menuRUL = '菜单RUL';
avmon.system.note = '备注';
avmon.system.icon = '图标';
avmon.system.moduleManagement = '模块管理';
avmon.system.systemModule = '系统模块';
avmon.system.roleName = '角色名称';
avmon.system.roleDescription = '角色描述';
avmon.system.roleManagement = '角色管理';
avmon.system.roleAuthorization = '角色权限';
avmon.system.account = '账号';
avmon.system.password = '密码';
avmon.system.userName = '用户名称';
avmon.system.mobilePhone = '手机';
avmon.system.officePhone = '办公电话';
avmon.system.role = '角色';
avmon.system.userManagement = '用户管理';
avmon.system.userRole = '用户角色';
avmon.system.widget = '小工具';
avmon.system.regex = '正则表达式';
avmon.system.targetString = '目标字符串';
avmon.system.validate = '验证';
avmon.system.synchronousMemory = '同步内存';
avmon.system.message = '如果修改了告警处理的则，比如：告警过滤规则、合并规则、告警转译规则等，您需要同步内存以使其生效。如果对监控对象进行了调整，比如新增监控对象，修改关键属性，也需要同步。';
avmon.system.synchronousAlarmRuleIntoMemory = '同步告警处理规则到内存';
avmon.system.synchronousMonitoringObjectsIntoMemory = '同步监控对象到内存';
avmon.system.synchronousing = '正在同步内存，请稍候...';
avmon.system.updateMemory = '更新内存';
avmon.system.synchronousFail = '同步失败，请稍后再试。';
avmon.system.typeId = '所属类别';
avmon.system.attrId = '属性英文名';
avmon.system.caption = '属性名称';
avmon.system.classinfo = '类信息';
avmon.system.hide = '是否显示';
avmon.system.passwd = '使用密码';
avmon.system.valueType = '属性类型';
avmon.system.orderIndex = '顺序';
avmon.system.defaultValue = '默认值';
avmon.system.nullable = '允许空';
avmon.system.kpiCode = 'Kpi编码';
avmon.system.inputNotAllowed = '只允许输入英文字母字符';
avmon.system.inputExists = '该类别下英文名己经存在';
avmon.system.inputNotLongerThan100 = '不能超过100个字符';
avmon.system.DepartmentName = '所属部门名称';
avmon.system.moduleId = '模块ID';
avmon.system.onlywordsallowed = '只能输入文字';

avmon.ireport = {};
avmon.ireport.conditionSet = '条件设置';
avmon.ireport.startDate = '开始日期';
avmon.ireport.dueDate = '截止日期';
avmon.ireport.generateReport = '生成报表';
avmon.ireport.exportPDF = '导出pdf';
avmon.ireport.exportWord = '导出word';
avmon.ireport.exportExcel = '导出excel';
avmon.ireport.pleaseSelectDeviceForExportReport = '请先选择您要导出报表的设备!';
avmon.ireport.parameterValueCantBeEmpty = '参数值不能为空!';
avmon.ireport.selectDisplayAttributes = '选择显示属性';
avmon.ireport.properties = '属性';
avmon.ireport.propertiesName = '属性名称';
avmon.ireport.deviceIP = '设备IP';
avmon.ireport.system = '操作系统';
avmon.ireport.pleaseSelectAttributeOfExportReport = '请先选择您要导出报表的设备属性!';
avmon.ireport.loading = '正在加载……';
avmon.ireport.chooseDevice = '选择设备';
avmon.ireport.deviceIP = '设备IP';
avmon.ireport.lastestReportTime = '最新报表时间';
avmon.ireport.deviceType = '设备类型';
avmon.ireport.deviceDescription = '设备描述';
avmon.ireport.search = '查询';
avmon.ireport.time = '时间';
avmon.ireport.to = '至';
avmon.ireport.startTime = '开始时间';
avmon.ireport.dueTime = '截止时间';
avmon.ireport.dailyReport = '日报';
avmon.ireport.weeklyReport = '周报';
avmon.ireport.monthlyReport = '月报';
avmon.ireport.dateSelect ='选择日期';
avmon.ireport.reportType ='报表类型';
avmon.ireport.inspectionDate = '巡检日期';

avmon.modules = {};
avmon.modules.userId = '用户ID';
avmon.modules.password = '密码';
avmon.modules.defaultThemme = '默认主题';
avmon.modules.grayTheme = '灰色主题';
avmon.modules.blackTheme = '黑色主题';
avmon.modules.conciseTest = '简洁(测试)';
avmon.modules.login = '登录';
avmon.modules.reset = '重置';
avmon.modules.savePasswordInLocal = '在本地保存密码';
avmon.modules.licenseRegister = 'License注册';
avmon.modules.logining = '正在登录请稍等...';
avmon.modules.starting = '启动中...';
avmon.modules.loginFailed = '登录失败';
avmon.modules.wrong = '错误';
avmon.modules.wrongRetry = '发生错误，请稍候再试！';
avmon.modules.initialRegistrationCode ='初始注册码';
avmon.modules.registrationCode = '注册码';
avmon.modules.register = '注册';
avmon.modules.registering = '正在注册，请稍等...';
avmon.modules.registerLicense = '注册License';

avmon.modules.logout = '注销';
avmon.modules.alarmMonitoring = '告警监控';
avmon.modules.realtimePerformance = '实时性能';
avmon.modules.analysisPerformance = '性能分析';
avmon.modules.realTime = '实时状态';
avmon.modules.queryAndReport = '查询与报表';
avmon.modules.resourceManagement ='资源管理';
avmon.modules.configurationManagement = '配置管理';
avmon.modules.deploymentManagement = '部署管理';
avmon.modules.systemManagement = '系统管理';
avmon.modules.networkInspectionConfig = '网络巡检配置';
avmon.modules.oidManagement = 'OID管理';
avmon.modules.oidConfiguration = 'OID配置';

avmon.modules.kpi_getConfiguration = 'KPI-GET配置';
avmon.modules.currentUser = '当前用户：';
avmon.modules.changePassword = '修改密码';

avmon.modules.passwordNotMatch = '两次输入的密码不一致!';
avmon.modules.account = '账号';
avmon.modules.originalPassword = '原始密码';
avmon.modules.newPassword = '新密码';
avmon.modules.confirmPassword = '密码确认';

avmon.modules.startProgram = '启动程序...';
avmon.modules.loadUIComponents = '加载UI组件...';
avmon.modules.loadUICSS = '加载UI样式...';
avmon.modules.loadMainInterface = '加载主界面...';
avmon.config.notEqual='不等于';
avmon.config.notIn='不包含';
avmon.config.include='包含';
avmon.config.regex='正则表达式';
avmon.config.like='匹配';
avmon.config.notLike='不匹配';
avmon.config.oidname = 'OID名称';
avmon.config.kpiCode = 'KPI编码';

//vmlist
avmon.vmlist = {};
avmon.vmlist.physicsHost = '物理机';
avmon.vmlist.physicsHostName = '物理机名称';
avmon.vmlist.status = '状态';
avmon.vmlist.vmlist = '虚拟机列表';
avmon.vmlist.cpusage = 'CPU使用率';
avmon.vmlist.memusage = '内存使用率';
avmon.vmlist.memSize = '内存大小';

avmon.vmlist.vmName = '虚拟机名称';
avmon.vmlist.host = '主机';
avmon.vmlist.usedSpace = '已用空间';
avmon.vmlist.hostFre = '主机频率';
avmon.vmlist.hostMem = '主机内存';

avmon.vmlist.storage = '数据存储';
avmon.vmlist.storageNm = '名称';

