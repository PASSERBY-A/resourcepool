/**
 * List compiled by mystix on the extjs.com forums.
 * Thank you Mystix!
 *
 * English Translations
 * updated to 2.2 by Condor (8 Aug 2008)
 */
Ext.onReady(function() {
    var cm = Ext.ClassManager,
        exists = Ext.Function.bind(cm.get, cm);

    if (Ext.Updater) {
        Ext.Updater.defaults.indicatorText = '<div class="loading-indicator">Loading...</div>';
    }

    if (exists('Ext.data.Types')) {
        Ext.data.Types.stripRe = /[\$,%]/g;
    }

    Ext.define("Ext.locale.en.view.View", {
        override: "Ext.view.View",
        emptyText: ""
    });

    Ext.define("Ext.locale.en.grid.Panel", {
        override: "Ext.grid.Panel",
        ddText: "{0} selected row{1}"
    });

    // changing the msg text below will affect the LoadMask
    Ext.define("Ext.locale.en.view.AbstractView", {
        override: "Ext.view.AbstractView",
        msg: "Loading..."
    });

    if (Ext.Date) {
        Ext.Date.monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

        Ext.Date.getShortMonthName = function(month) {
            return Ext.Date.monthNames[month].substring(0, 3);
        };

        Ext.Date.monthNumbers = {
            Jan: 0,
            Feb: 1,
            Mar: 2,
            Apr: 3,
            May: 4,
            Jun: 5,
            Jul: 6,
            Aug: 7,
            Sep: 8,
            Oct: 9,
            Nov: 10,
            Dec: 11
        };

        Ext.Date.getMonthNumber = function(name) {
            return Ext.Date.monthNumbers[name.substring(0, 1).toUpperCase() + name.substring(1, 3).toLowerCase()];
        };

        Ext.Date.dayNames = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

        Ext.Date.getShortDayName = function(day) {
            return Ext.Date.dayNames[day].substring(0, 3);
        };

        Ext.Date.parseCodes.S.s = "(?:st|nd|rd|th)";
    }

    if (Ext.MessageBox) {
        Ext.MessageBox.buttonText = {
            ok: "OK",
            cancel: "Cancel",
            yes: "Yes",
            no: "No"
        };
    }

    if (exists('Ext.util.Format')) {
        Ext.apply(Ext.util.Format, {
            thousandSeparator: ',',
            decimalSeparator: '.',
            currencySign: '$',
            dateFormat: 'm/d/Y'
        });
    }

    Ext.define("Ext.locale.en.picker.Date", {
        override: "Ext.picker.Date",
        todayText: "Today",
        minText: "This date is before the minimum date",
        maxText: "This date is after the maximum date",
        disabledDaysText: "",
        disabledDatesText: "",
        monthNames: Ext.Date.monthNames,
        dayNames: Ext.Date.dayNames,
        nextText: 'Next Month (Control+Right)',
        prevText: 'Previous Month (Control+Left)',
        monthYearText: 'Choose a month (Control+Up/Down to move years)',
        todayTip: "{0} (Spacebar)",
        format: "m/d/y",
        startDay: 0
    });

    Ext.define("Ext.locale.en.picker.Month", {
        override: "Ext.picker.Month",
        okText: "&#160;OK&#160;",
        cancelText: "Cancel"
    });

    Ext.define("Ext.locale.en.toolbar.Paging", {
        override: "Ext.PagingToolbar",
        beforePageText: "Page",
        afterPageText: "of {0}",
        firstText: "First Page",
        prevText: "Previous Page",
        nextText: "Next Page",
        lastText: "Last Page",
        refreshText: "Refresh",
        displayMsg: "Displaying {0} - {1} of {2}",
        emptyMsg: 'No data to display'
    });

    Ext.define("Ext.locale.en.form.Basic", {
        override: "Ext.form.Basic",
        waitTitle: "Please Wait..."
    });

    Ext.define("Ext.locale.en.form.field.Base", {
        override: "Ext.form.field.Base",
        invalidText: "The value in this field is invalid"
    });

    Ext.define("Ext.locale.en.form.field.Text", {
        override: "Ext.form.field.Text",
        minLengthText: "The minimum length for this field is {0}",
        maxLengthText: "The maximum length for this field is {0}",
        blankText: "This field is required",
        regexText: "",
        emptyText: null
    });

    Ext.define("Ext.locale.en.form.field.Number", {
        override: "Ext.form.field.Number",
        decimalSeparator: ".",
        decimalPrecision: 2,
        minText: "The minimum value for this field is {0}",
        maxText: "The maximum value for this field is {0}",
        nanText: "{0} is not a valid number"
    });

    Ext.define("Ext.locale.en.form.field.Date", {
        override: "Ext.form.field.Date",
        disabledDaysText: "Disabled",
        disabledDatesText: "Disabled",
        minText: "The date in this field must be after {0}",
        maxText: "The date in this field must be before {0}",
        invalidText: "{0} is not a valid date - it must be in the format {1}",
        format: "m/d/y",
        altFormats: "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d"
    });

    Ext.define("Ext.locale.en.form.field.ComboBox", {
        override: "Ext.form.field.ComboBox",
        valueNotFoundText: undefined
    }, function() {
        Ext.apply(Ext.form.field.ComboBox.prototype.defaultListConfig, {
            loadingText: "Loading..."
        });
    });

    if (exists('Ext.form.field.VTypes')) {
        Ext.apply(Ext.form.field.VTypes, {
            emailText: 'This field should be an e-mail address in the format "user@example.com"',
            urlText: 'This field should be a URL in the format "http:/' + '/www.example.com"',
            alphaText: 'This field should only contain letters and _',
            alphanumText: 'This field should only contain letters, numbers and _'
        });
    }

    Ext.define("Ext.locale.en.form.field.HtmlEditor", {
        override: "Ext.form.field.HtmlEditor",
        createLinkText: 'Please enter the URL for the link:'
    }, function() {
        Ext.apply(Ext.form.field.HtmlEditor.prototype, {
            buttonTips: {
                bold: {
                    title: 'Bold (Ctrl+B)',
                    text: 'Make the selected text bold.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                italic: {
                    title: 'Italic (Ctrl+I)',
                    text: 'Make the selected text italic.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                underline: {
                    title: 'Underline (Ctrl+U)',
                    text: 'Underline the selected text.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                increasefontsize: {
                    title: 'Grow Text',
                    text: 'Increase the font size.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                decreasefontsize: {
                    title: 'Shrink Text',
                    text: 'Decrease the font size.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                backcolor: {
                    title: 'Text Highlight Color',
                    text: 'Change the background color of the selected text.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                forecolor: {
                    title: 'Font Color',
                    text: 'Change the color of the selected text.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                justifyleft: {
                    title: 'Align Text Left',
                    text: 'Align text to the left.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                justifycenter: {
                    title: 'Center Text',
                    text: 'Center text in the editor.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                justifyright: {
                    title: 'Align Text Right',
                    text: 'Align text to the right.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                insertunorderedlist: {
                    title: 'Bullet List',
                    text: 'Start a bulleted list.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                insertorderedlist: {
                    title: 'Numbered List',
                    text: 'Start a numbered list.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                createlink: {
                    title: 'Hyperlink',
                    text: 'Make the selected text a hyperlink.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                },
                sourceedit: {
                    title: 'Source Edit',
                    text: 'Switch to source editing mode.',
                    cls: Ext.baseCSSPrefix + 'html-editor-tip'
                }
            }
        });
    });

    Ext.define("Ext.locale.en.grid.header.Container", {
        override: "Ext.grid.header.Container",
        sortAscText: "Sort Ascending",
        sortDescText: "Sort Descending",
        columnsText: "Columns"
    });

    Ext.define("Ext.locale.en.grid.GroupingFeature", {
        override: "Ext.grid.GroupingFeature",
        emptyGroupText: '(None)',
        groupByText: 'Group By This Field',
        showGroupsText: 'Show in Groups'
    });

    Ext.define("Ext.locale.en.grid.PropertyColumnModel", {
        override: "Ext.grid.PropertyColumnModel",
        nameText: "Name",
        valueText: "Value",
        dateFormat: "m/j/Y",
        trueText: "true",
        falseText: "false"
    });

    Ext.define("Ext.locale.en.grid.BooleanColumn", {
        override: "Ext.grid.BooleanColumn",
        trueText: "true",
        falseText: "false",
        undefinedText: '&#160;'
    });

    Ext.define("Ext.locale.en.grid.NumberColumn", {
        override: "Ext.grid.NumberColumn",
        format: '0,000.00'
    });

    Ext.define("Ext.locale.en.grid.DateColumn", {
        override: "Ext.grid.DateColumn",
        format: 'm/d/Y'
    });

    Ext.define("Ext.locale.en.form.field.Time", {
        override: "Ext.form.field.Time",
        minText: "The time in this field must be equal to or after {0}",
        maxText: "The time in this field must be equal to or before {0}",
        invalidText: "{0} is not a valid time",
        format: "g:i A",
        altFormats: "g:ia|g:iA|g:i a|g:i A|h:i|g:i|H:i|ga|ha|gA|h a|g a|g A|gi|hi|gia|hia|g|H"
    });

    Ext.define("Ext.locale.en.form.CheckboxGroup", {
        override: "Ext.form.CheckboxGroup",
        blankText: "You must select at least one item in this group"
    });

    Ext.define("Ext.locale.en.form.RadioGroup", {
        override: "Ext.form.RadioGroup",
        blankText: "You must select one item in this group"
    });
});



var avmon = {};
avmon.common = {};
avmon.common.ok = 'OK';
avmon.common.cancel = 'Cancel';
avmon.common.save = 'Save';
avmon.common.close = 'Close';
avmon.common.reset = 'Reset';
avmon.common.reminder = 'Prompt';
avmon.common.saving = 'Saving, please wait...';
avmon.common.log = 'Log';
avmon.common.clear = 'Clear';
avmon.common.message = 'Message';
avmon.common.errorMessage = 'Error Message';
avmon.common.systemError = 'System Message';
avmon.common.failure = 'Failed';
avmon.common.deleted = 'Delete';
avmon.common.refresh = 'Refresh';
avmon.common.loading = 'Loading, please wait...';
avmon.common.loadingWait = 'Trying to load, please wait...';
avmon.common.host='Host';

avmon.alarm = {}; 
avmon.alarm.retrieve = 'Search';
avmon.alarm.subscribeAlarm = 'Alarm Subscription';
avmon.alarm.alreadyClearAlarm = 'Cleared Alarm';  
avmon.alarm.normalAlarm = 'Regular Alarm';
avmon.alarm.minorAlarm = 'Secondary Alarm';
avmon.alarm.mainAlarm = 'Primary Alarm';
avmon.alarm.seriousAlarm = 'Critical Alarm';
avmon.alarm.myAlarm = 'My Alarm ';
avmon.alarm.myAlarm0 = 'My Alarm 0';
avmon.alarm.newAlarm = 'New Alarm ';
avmon.alarm.newAlarm0 = 'New Alarm 0';
avmon.alarm.advancedSearch = 'Advanced Search';
avmon.alarm.afterPageText = '/{0} pages';
avmon.alarm.beforePageText = 'Page';
avmon.alarm.displayMsg = 'Showing Item {0}-{1} of {2} items';
avmon.alarm.emptyMsg = '(No data available)';
avmon.alarm.firstText = 'First page';
avmon.alarm.lastText = 'Last Page';
avmon.alarm.nextText = 'Next page';
avmon.alarm.prevText = 'Previous page';
avmon.alarm.level = 'Severity';
avmon.alarm.status = 'Status';
avmon.alarm.operationSystem = 'Business System';
avmon.alarm.devicename = 'Device';
avmon.alarm.hostname = 'Host Name';
avmon.alarm.ipaddress = 'IP';
avmon.alarm.alarmTitle = 'Alarm Title';
avmon.alarm.lastTime = 'Ending Time';
avmon.alarm.times = 'Frequency';
avmon.alarm.startTime = 'Starting Time';
avmon.alarm.results = 'Processing Result';
avmon.alarm.actorUser = 'Processed by';
avmon.alarm.position = 'Location';
avmon.alarm.usage = 'Usage';
avmon.alarm.administrator = 'Administrator';
avmon.alarm.alarmContent = 'Alarm Content';
avmon.alarm.deviceSerialNumber = 'Device SN';
avmon.alarm.assetSerialNumber = 'Asset SN';
avmon.alarm.alarmMessage = 'Alarm Information';
avmon.alarm.facilityInformation = 'Device Information';
avmon.alarm.severityLevel = 'Severity';
avmon.alarm.alarmStatus = 'Alarm Status';
avmon.alarm.alarmOriginalContent = 'Original Alarm Content';
avmon.alarm.finallyAlarmTime = 'Last Alarm Time';
avmon.alarm.frequency = 'Occurrence';
avmon.alarm.firstAlarmTime = 'First Alarm Time';
avmon.alarm.claimTime = 'Claiming Time';
avmon.alarm.claimant = 'Claimed by';
avmon.alarm.alarmSource = 'Alarm Source';
avmon.alarm.handlingSuggestion = 'Processing Opinion';
avmon.alarm.handlingTime = 'Processing Time';
avmon.alarm.historyAlarm = 'Historical Alarm';
avmon.alarm.clearingTime = 'Clear Time';
avmon.alarm.fortyEightClearedAlarm = 'Alarms Cleared Within 48 Hours';
avmon.alarm.alreadyLastQualifiedRecord = 'This is the last eligible entry!';
avmon.alarm.notRetrievedMatchingRecords = 'No match found! ';
avmon.alarm.SMSReceivingTime = 'Short Message Receiving Time';
avmon.alarm.wholeTime = 'All Time';
avmon.alarm.workingTime = 'Working Hours';
avmon.alarm.viewID = 'View ID';
avmon.alarm.viewName = 'View Name';
avmon.alarm.receiveWarningLevel = 'Alarm Receive Time';
avmon.alarm.receiveMode = 'Receive Mode';
avmon.alarm.SMS = 'Short Message';
avmon.alarm.dailyMaxNumberOfTextMessage = 'Maximum Short Messages Per Day';
avmon.alarm.whetherStarts = 'Whether to start';
avmon.alarm.lastUpdateTime = 'Last Updated';
avmon.alarm.alarmInformation = 'Alarm-Related Information';
avmon.alarm.timeHorizon = 'Time Range';
avmon.alarm.alarmStartTime = 'Starting Time';
avmon.alarm.hour = 'Hr';
avmon.alarm.minute = 'Mi';
avmon.alarm.reach = 'to';
avmon.alarm.claim = 'Claim';
avmon.alarm.selectOperateLine = 'Select a row to be processed!';
avmon.alarm.knowledge = 'Knowledge';
avmon.alarm.classify = 'Category';
avmon.alarm.detail = 'Details';
avmon.alarm.checking = 'Checking...';
avmon.alarm.processed = 'Processed';
avmon.alarm.details = 'Details';
avmon.alarm.chooseYourDataToOperate = 'Select data to be processed first!';
avmon.alarm.onlySelectARowToViewDetailedInformation = 'Only one row of data can be selected for viewing details!';
avmon.alarm.well = 'Message';
avmon.alarm.processing = 'Processing';
avmon.alarm.claimed = 'Claimed';
avmon.alarm.viewRealTimePerformance = 'View Real-time Performance';
avmon.alarm.onlySelectARowToViewDetailedPerformance = 'Only one data entry can be selected for viewing real-time performance!';
avmon.alarm.alarmForward = 'Alarm Forward';
avmon.alarm.updateAlarmStatus = 'Update Alarm Status in Batches';

avmon.alarm.alarmHost = 'Alarm Host';
avmon.alarm.alarmIndicator = 'Alarm Index';
avmon.alarm.alarmTime = 'Time';
avmon.alarm.accepter = 'Receiver';
avmon.alarm.alarmLevel = 'Severity';
avmon.alarm.alarmForwardFailed = 'Failed to forward alarm! ';
avmon.alarm.alarmsCleaned='Alarms Status has been Updated Successfully';

avmon.batchdeploy = {};
avmon.batchdeploy.equipmentID = 'Device ID';
avmon.batchdeploy.equipment = 'Device';
avmon.batchdeploy.resourceType = 'Resource Type';
avmon.batchdeploy.ipaddress = 'IP Address';
avmon.batchdeploy.status = 'Status';
avmon.batchdeploy.operatingSystem = 'Operating System';
avmon.batchdeploy.version = 'Version';
avmon.batchdeploy.batchDeploy = 'Deploy in Batches - Discover devices installed with the Agent';
avmon.batchdeploy.firstStep = 'Step 1: Enter the IP range and click Start to Scan';
avmon.batchdeploy.startIp = 'Starting IP';
avmon.batchdeploy.stopIp = 'Ending IP';
avmon.batchdeploy.startScan = 'Start to Scan';
avmon.batchdeploy.startDeploy = 'Start to Deploy';
avmon.batchdeploy.monitorInstance = 'Monitoring Instance';
avmon.batchdeploy.monitor = 'Monitor';
avmon.batchdeploy.message = 'Message';

avmon.config = {};
avmon.config.targetList = 'Index List';
avmon.config.kpiHistoryTendency = 'Historical KPI Trend';
avmon.config.host = 'Host';
avmon.config.hosts = 'Host:';
avmon.config.time = 'Time';
avmon.config.to = 'To';
avmon.config.search = 'Query';
avmon.config.selectKPI = 'Please select KPIs!';
avmon.config.kpiNumberNotMoreThanFive = 'Number of KPIs may not exceed 5!';
avmon.config.times = 'Time:';
avmon.config.kpiInstance = 'KPI Instance:';
avmon.config.value = 'Value:';
avmon.config.loadFail = 'Failed to load!';
avmon.config.kpi = 'KPI：';
avmon.config.averageValue = 'Average:';
avmon.config.maxValue = 'Maximum:';
avmon.config.minValue = 'Minimum:';
avmon.config.seriousAlarmThresholds = 'Critical Alarm Threshold:';
avmon.config.noHistoricalInformation = 'No historical information available!';
avmon.config.agentDetail = 'Agent details';
avmon.config.status = 'Heartbeat status';
avmon.config.updateStatus = 'Data Transfer Status';
avmon.config.startGathering = 'Start Collecting';
avmon.config.stopGathering = 'Stop Collecting';
avmon.config.wrong = 'Error';
avmon.config.ampList = 'AMP List';
avmon.config.deployDefaultAMP = 'Deploy the default AMP';
avmon.config.addAMP = 'Add an AMP';
avmon.config.ampUninstall = 'AMP Uninstallation';
avmon.config.ampScriptIssued = 'AMP Script issuing';
avmon.config.ampSchedulingIssued = 'AMP Schedule issuing';
avmon.config.ampInstanceID = 'AMP Instance ID';
avmon.config.ampName = 'AMP Name';
avmon.config.installDirectory = 'Installation Directory';
avmon.config.currentVersion = 'Current Version';
avmon.config.schedulingConfigString = 'Schedule Configuration String';
avmon.config.notInstall = 'Not Installed';
avmon.config.running = 'Running';
avmon.config.stopRun = 'Stop Running';
avmon.config.unkonwn = 'Unknown';
avmon.config.ampType = 'AMP Type';
avmon.config.scriptIssuedTime = 'Script Issuing Time';
avmon.config.schedulIssuedTime = 'Schedule Issuing Time';
avmon.config.configIssuedTime = 'Configure Issuing Time';
avmon.config.beforePageText = 'Page';
avmon.config.afterPageText = ' of {0} pages';
avmon.config.displayMsg = 'Showing Item {0} - {1} of {2} items';
avmon.config.emptyMsg = 'No data available';
avmon.config.startingWait = 'Starting, please wait...';
avmon.config.starting = 'Starting...';
avmon.config.operationFail = 'The operation failed!';
avmon.config.stoppingWait = 'Stopping, please wait...';
avmon.config.stopping = 'Stopping...';
avmon.config.selectNeedUninstallAMP = 'Please select the AMP to be uninstalled!';
avmon.config.uninstallAMPWait = 'Uninstalling the AMP, please wait...';
avmon.config.uninstalling = 'Uninstalling...';
avmon.config.selectNeedIssuedScriptAMP = 'Please select the AMP whose script needs to be issued!';
avmon.config.beIssuedByScriptPleaseWait = 'Issuing scripts, please wait...';
avmon.config.issuing = 'Issuing...';
avmon.config.selectNeedSchedulIssuedAMP = 'Please select the AMP whose schedule needs to be issued!';
avmon.config.issuingSchedulWait = 'Issuing schedule, please wait...';
avmon.config.selectNeedStartAMP = 'Please select the AMP to be started!';
avmon.config.startGatheringStart = 'Collection Start';
avmon.config.stopGatheringStop = 'Collection Stop';
avmon.config.ampConfiguration = 'AMP Configuration - ';
avmon.config.agentManagement = 'Agent Management';
avmon.config.referenceHostName = 'Reference Host Name';
avmon.config.systemId = 'System Identification';
avmon.config.system = 'Operating System';
avmon.config.systemVersion = 'Operating System Version';
avmon.config.agentVersion = 'Agent Version';
avmon.config.lastActivity = 'Last Heartbeat Time';
avmon.config.lastUpdateTime='Last Data Transfering Time'
avmon.config.ampInstallSituation = 'AMP Installation';
avmon.config.agentAcquisitionStartupState = 'Agent Collection Start Status';
avmon.config.agentStatus = 'Agent Status';
avmon.config.configuration = 'Configuration';
avmon.config.updateAgent = 'Upgrade Agent';
avmon.config.retrieve = 'Search';
avmon.config.whetherToDelete = 'Do you want to delete?';
avmon.config.whetherUpgrade = 'Do you want to upgrade?';
avmon.config.updatingAgentWait = 'Upgrading Agent, please wait...';
avmon.config.updatingAgentWait = 'Upgrading Agent..';
avmon.config.netErrorUpdateFail = 'Network error. Failed to update!';
avmon.config.normal = 'Normal';
avmon.config.cannotCreatObjectForAgent = 'Failed to create a monitoring object for the machine where the Agent locates. ';
avmon.config.addMonitorObjectFail = 'Failed to add a monitoring object';
avmon.config.ampInstance = 'Amp Instance';
avmon.config.whetherStart = 'Whether to Enable';
avmon.config.waiting = 'Please wait...';
avmon.config.dataSubmitting = 'Submitting Data...';
avmon.config.ampAddedSuccess = 'AMP has been added successfully!';
avmon.config.fillOutCompleteInformation = 'Please fill in information completely!';
avmon.config.ampConfig = 'AMP Configuration';
avmon.config.start = 'Start';
avmon.config.stop = 'Stop';
avmon.config.issuedByScript = 'Issue Script';
avmon.config.issueByConfiguration = 'Issue Configuration';
avmon.config.iloHostList = 'ILO Host List';
avmon.config.addILOHost = 'Add ILO Host';
avmon.config.configILOHost = 'Configure ILO Host';
avmon.config.deleteILOHost = 'Delete ILO Host';
avmon.config.IssuedDispatch = 'Issue Schedule';
avmon.config.iloHostIP = 'ILO Host IP';
avmon.config.hostname = 'Host Name';
avmon.config.userName = 'User Name'; 
avmon.config.password = 'Password';
avmon.config.selectILOHostForConfig = 'Please select only one ILO host to be configured!';
avmon.config.selectILOHostForDelete = 'Please select the ILO host you want to delete first!';
avmon.config.selectILOHost = 'Please select an ILO host first!';
avmon.config.startingAMPInstance = 'Starting the AMP instance, please wait...';
avmon.config.stoppingAMPInstance = 'Stopping the AMP instance, please wait...';
avmon.config.issuingConfigurationPleaseWait = 'Issuing configuration, please wait...';
avmon.config.iloHostConfiguration = 'ILO Host Configuration';
avmon.config.basicConfiguration = 'Basic Configuration';
avmon.config.hostIP = 'Host IP';
avmon.config.reservedConfigurationItermOne = 'Preserved Configuration Item 1';
avmon.config.reservedConfigurationItermTwo = 'Preserved Configuration Item 2';
avmon.config.reservedConfigurationItermThree = 'Preserved Configuration Item 3';
avmon.config.schedulingPolicy = 'Scheduling Policy';
avmon.config.editSchedulingPolicy = 'Edit Scheduling Policy';
avmon.config.issueByChosenPolicy = 'Issue Selected Policy';
avmon.config.issueByAllPolicy = 'Issue All Policies';
avmon.config.enableFlag = 'Whether to Enable';
avmon.config.kpiName = 'KPI Name';
avmon.config.group = 'Group';
avmon.config.pleaseEnterIP = 'Please enter the IP!';
avmon.config.pleaseEnterHostName = 'Pleas enter the host name!';
avmon.config.pleaseEnterUserName = 'Please enter the user name!';
avmon.config.pleaseEnterPassword = 'Please enter the password!';
avmon.config.pleaseSelectASetOfStrategiesForEdit = 'Please select a group of policies to be edited!';
avmon.config.pleaseSelectNeedIssueStrategy = 'Please select policies to be issued!';
avmon.config.issuingStrategyPleaseWait = 'Issuing policies, please wait...';
avmon.config.issueStrategySuccess = 'Policies Issued successfully!';
avmon.config.issueStrategyFailure = 'Failed to issue policies';
avmon.config.savingDataPleaseWait = 'Saving data, please wait...';
avmon.config.saving = 'Saving...';
avmon.config.schedulingPolicyChangeSuccess = 'Scheduling policies have been modified successfully!';
avmon.config.schedulingPolicyChangeFailure = 'Failed to modify scheduling policies';
avmon.config.confirmChanges = 'Confirm Modification';
avmon.config.confirmTheOperation = 'The scheduling policies you are editing will apply to other KPI codes in the same group. Do you want to continue?';
avmon.config.parameterName = 'Parameter Name';
avmon.config.value = 'Value';
avmon.config.saveSuccess = 'Saved successfully!';
avmon.config.saveFailure = 'Failed to save!';
avmon.config.ampConfiguration = 'AMP Configuration';
avmon.config.mustFillOut = ' Required!';
avmon.config.savingDataPleaseWait = 'Saving data, please wait...';
avmon.config.dataSavingSuccessfully = 'Data has been saved successfully!';
avmon.config.virtualHost = 'Virtual Host';
avmon.config.hostStatus = 'Host Status';
avmon.config.monitoring = 'Monitor';
avmon.config.noMonitoring = 'Unmonitored';
avmon.config.monitoringStatus = 'Monitoring Status';
avmon.config.refreshVirtualHostList = 'Refresh Virtual Host List';
avmon.config.startMonitor = 'Enable Monitoring';
avmon.config.stopMonitor = 'Stop Monitoring';
avmon.config.remove = 'Remove';
avmon.config.collectionSchedule = 'Collection Scheduling';
avmon.config.saveIssuedCollectionSchedule = 'Save issued collection scheduling';
avmon.config.invokeInterfaceFailure = 'Failed to call the interface!';
avmon.config.pleaseSelectNotMonitorObject = 'Please select any unmonitored object(s)!';
avmon.config.pleaseSelectMonitorObject = 'Please select any monitoring object(s)!';
avmon.config.pleaseSelectRemoveObject = 'Please select the object(s) to be removed!';
avmon.config.whetherRemove = 'Do you want to remove them? ';

avmon.config.chooseDevice = 'Select Device';
avmon.config.deviceIP = 'Device IP';
avmon.config.deviceType = 'Device Type';
avmon.config.deviceDescription = 'Device Description';
avmon.config.choose = 'Select';
avmon.config.pleaseChooseDevice = 'Please select a device!';
avmon.config.name = 'Name';
avmon.config.updateTime = 'Updating Time';
avmon.config.add = 'Add';
avmon.config.reportDataSource = 'Report Data Source';
avmon.config.selectOperateLine = 'Select a row to be processed!';
avmon.config.modify = 'Modify';
avmon.config.root = 'Root Node';
avmon.config.theMenu = 'Affiliated Menu';
avmon.config.basicInformation = 'Basic Information';
avmon.config.reportNumber = 'Report No.';
avmon.config.reportName = 'Report Name';
avmon.config.dataSource = 'Data Source';
avmon.config.templateFile = 'Template File';
avmon.config.pleaseSelectTemplateFile = 'Please select a template file';
avmon.config.browse = 'Browse';
avmon.config.type = 'Type';
avmon.config.startTime = 'Starting Time';
avmon.config.cycle = 'Period';
avmon.config.emailServer = 'Mail Server';
avmon.config.reportAttachment = 'With Attachment';
avmon.config.reportAttachmentType = 'Format';
avmon.config.reportManagement = 'Report Management';
avmon.config.templatePath = 'Template Path';
avmon.config.networkInspectionData = 'Network Inspection Data';
avmon.config.monitorType = 'Monitoring Type';
avmon.config.kpiThresholdValue = 'KPI Threshold';
avmon.config.all = 'All';
avmon.config.backupConfigurationInformation = 'Configuration Backup Information';
avmon.config.devicename = 'Device Name';
avmon.config.configuartionFile = 'Profile';
avmon.config.operation = 'Action';
avmon.config.monitorObjectNameOrIP = 'Monitoring Object (Name or IP)';
avmon.config.clearingTimeMinutes = 'Clearing Time Limit (Minutes)';
avmon.config.alarmAutomaticallyClearRules = 'Alarm Automatic Clearing Rule';
avmon.config.monitoringObject = 'Monitoring Object';
avmon.config.alarmContentRegularExpression = 'Alarm Content (Regular Expression)';
avmon.config.alarmFilteringRules = 'Alarm Filtering Rule';
avmon.config.aggregationType = 'Aggregation Type';
avmon.config.dataType = 'Data Type';
avmon.config.whetherRetainTheRecentRawData = 'Do you want to retain recent raw data';
avmon.config.yes = 'Yes';
avmon.config.no = 'No';
avmon.config.storedToDatabase = 'Store to Database';
avmon.config.whetherComputationalKPI = 'Is it a calculation KPI';
avmon.config.kpiEncoding = 'KPI Code';
avmon.config.kpiEnglishName = 'KPI English Name';
avmon.config.kpiChineseName = 'KPI Chinese Name';
avmon.config.decimal = 'Decimal';
avmon.config.unit = 'Unit';
avmon.config.calculationMethod = 'Calculation Method';
avmon.config.storageCycleMinutes = 'Storage Period (Minutes)';
avmon.config.kpiInformation = 'KPI Information';
avmon.config.storageCycle = 'Storage Period';
avmon.config.selectKPINotMoreThanOne = 'Please select no more than one KPI!';
avmon.config.alarmThresholdValue = 'Alarm Threshold';
avmon.config.judgmentMethod = 'Determination Method';
avmon.config.GreaterThan = 'Greater than';
avmon.config.lessThan = 'Smaller than';
avmon.config.equalTo = 'Equal to';
avmon.config.minorAlarm = 'Secondary Alarm';
avmon.config.flashingNumber = 'Flash Times';
avmon.config.kpiAlarmThresholdValue = 'KPI Alarm Threshold';
avmon.config.performMergeTimeWindowMinute = 'Merge Execution Window (minutes)';
avmon.config.alarmMergerRules = 'Alarm Merging Rule';
avmon.config.view = 'View';
avmon.config.alarmViewSubscriptionManagement = 'Alarm View Subscription Management';
avmon.config.userId = 'User ID';
avmon.config.whetherReceiveEmail = 'Whether to Receive Emails';
avmon.config.whetherReceiveSMS = 'Whether to Receive Short Messages';
avmon.config.SMSAcceptTime = 'Short Message Receiving Time';
avmon.config.AlarmValidationRules = 'Alarm Rule Verification';
avmon.config.module = 'Module';
avmon.config.verifyStatus = 'Verification Status';
avmon.config.validationRules = 'Verification Rule';
avmon.config.originalContent = 'Original Content';
avmon.config.translatedContent = 'Translated Content';
avmon.config.accumulativeAlarmTimes = 'Accumulated Alarm Occurrences';
avmon.config.alarmTimeWindowMinute = 'Window for Accumulated Alarms (Minutes)';
avmon.config.afterUpgradingAlarmLevel = 'Alarm Severity After Escalation';
avmon.config.alarmUpgradingRule = 'Alarm Escalation Rule';
avmon.config.deviceStatus = 'Device Status';
avmon.config.recentReportTime = 'Recent Reporting Time';
avmon.config.networkMonitoringDeviceManagement = 'Network Monitoring Device Management';
avmon.config.lastestReportTime = 'Latest Reporting Time';
avmon.config.thresholdValueType = 'Threshold Type';
avmon.config.string = 'String';
avmon.config.temperature = 'Temperature';
avmon.config.integer = 'Integer';
avmon.config.notAllowModifyDevice = 'You are not allowed to modify the device';
avmon.config.ignoreValue = 'Ignored Value';
avmon.config.errorValues = 'Incorrect Value';
avmon.config.networkMonitoringThresholdValueManagement = 'Network Monitoring Index Threshold Management';
avmon.config.chineseName = 'Chinese Name';
avmon.config.kpiType = 'KPI Type';
avmon.config.networkMonitoringIndexManagement = 'Network Monitoring Index Management';

avmon.config.inspectionOrders = 'Inspection Command';
avmon.config.exitCommand = 'Exit Command';
avmon.config.exitCommandFirst = 'Exit Command 1';
avmon.config.exitCommandSecond = 'Exit Command 2';
avmon.config.networkInspectionDeviceManagement = 'Network Inspection Device Management';
avmon.config.inspectionType = 'Inspection Type';
avmon.config.version = 'Version No.';
avmon.config.deployHost = 'Deployment Host';
avmon.config.command = 'Command ';
avmon.config.pleaseInpuDeviceIP = 'Please enter the device IP!';
avmon.config.selectDeviceType = 'Please select a device type!';
avmon.config.exported = 'Export';
avmon.config.exitWayFirst = 'Exit Mode 1';
avmon.config.exitWaySecond = 'Exit Mode 2';
avmon.config.deleteSuccess = 'Delete successfully!';
avmon.config.exportSuccess = 'Exported successfully!';
avmon.config.exportFailure = 'Failed to export!';
avmon.config.filterCondition = 'Filter Criteria';
avmon.config.condition = 'Criteria';
avmon.config.column = 'Field';
avmon.config.operationalCharacter = 'Operator';
avmon.config.refresh = 'Refresh';
avmon.config.directory = 'Directory';
avmon.config.monitorViewManagement = 'Monitoring View Management';
avmon.config.newIncrease = 'New';
avmon.config.other = 'Other';
avmon.config.combinationalRule = 'Combination Rule';
avmon.config.pleaseEnterDirectoryName = 'Please enter the directory name:';
avmon.config.viewType = 'View Type';
avmon.config.publicView = 'Public View';
avmon.config.groupView = 'Group View';
avmon.config.privateView = 'Private View';
avmon.config.directoryName = 'Directory Name';
avmon.config.pleaseEnterViewName = 'Please enter the view name:';
avmon.config.selectNode = 'Please select a node!';

avmon.config.netElmentARPInquire = 'NE ARP Query';
avmon.config.netElement = 'NE';
avmon.config.date = 'Date';
avmon.config.netElementInquire = 'NE Query';
avmon.config.setLimitOfBase = 'Set as Baseline';
avmon.config.netElementIP = 'NE IP';
avmon.config.whetherLimitOfBase = 'Is it a baseline?';
avmon.config.pleaseSelectnetElement = 'Please select an NE!';
avmon.config.selectNetElementNotInquireOrNull = 'The selected NE has not been queried or the query result is empty!';
avmon.config.promptMessage = 'Prompt Message';
avmon.config.setSuccessfully =  'Set successfully!'; 

avmon.config.kpiDynamicWarningThresholdValue = 'KPI Dynamic Alarm Threshold';
avmon.config.alarmLowestThresholdValue = 'Alarm Lower Threshold';
avmon.config.alarmMaximumThresholdValue = 'Alarm Upper Threshold';
avmon.config.stopTime = 'Finishing Time';
avmon.config.alarmContentTemplate = 'Alarm Content Template';
avmon.config.fixedThreshold = 'Fixed Threshold';
avmon.config.limitOfBaseThreshold = 'Baseline Threshold';
avmon.config.alarmUpperTolerability = 'Upper Tolerance of Alarm';
avmon.config.alarmFloorTolerability = 'Lower Tolerance of Alarm';
avmon.config.absoluteValue = 'Absolute Value';
avmon.config.percent = 'Percentage';
avmon.config.samplingFrequency = 'Collection Frequency';
avmon.config.day = 'Date';
avmon.config.week = 'Week';
avmon.config.workday = 'Working Day';
avmon.config.holiday = 'Holiday';

avmon.config.startupMonitorning = 'Start Monitoring';
avmon.config.startingMonitorning = 'Monitoring...';
avmon.config.startupMonitorningFailed = 'Failed to enable monitoring!';
avmon.config.onlyCanInputEnglishAndNumbers = 'Only English letters and digits are allowed';

avmon.config.monitorSaved = 'Monitor has been saved!';
avmon.config.suspend = 'Pause';
avmon.config.agentSuspended = 'Agent has been paused!';
avmon.config.restoreRun = 'Resume Running';
avmon.config.agentSetRestoreRun = 'Agent has been set to resume running!';
avmon.config.refreshMenu = 'Refresh Menu';
avmon.config.addHost = 'Add Host';
avmon.config.inputContentCannotBeEmpty = 'This field cannot be left blank';
avmon.config.warning = 'Warning';
avmon.config.objectNumberCannotBeEmpty = 'The object number cannot be left blank!';
avmon.config.objectNameCannotBeEmpty = 'The object name cannot be left blank!';
avmon.config.parentCanNotBeEmpty='The object parent cannot be left blank!';
avmon.config.pleaseSelectObjectType = 'Please select an object type!';
avmon.config.pleaseSelectBusinessSystem = 'Please select a business system!';
avmon.config.wrongRetry = 'An error occurred. Please try later!';
avmon.config.newHost = 'New Host';
avmon.config.pleaseConfirmWhetherDelete = 'Are you sure you want to delete it?';
avmon.config.monitorObjectDeleted = 'The monitoring object has been deleted!';
avmon.config.addMonitorHost = 'Add Monitoring Host';
avmon.config.monitoringObjectNumber = 'Monitoring Object Number';
avmon.config.hostName = 'Host Name';
avmon.config.objectType = 'Object Type';
avmon.config.ipaddress = 'IP Address';
avmon.config.operatingSystem = 'Operating System';
avmon.config.agentLastUpdateTime = 'Agent Last Updated';
avmon.config.hangUp = 'Suspend';
avmon.config.restore = 'Resume';
avmon.config.CIProperty = 'CI Attribute';
avmon.config.installedEquipment = 'Installed Device';
avmon.config.properties = 'Attribute';
avmon.config.addMonitoringObjects = 'Add Monitoring Object';
avmon.config.chooseObjectType = 'Select Object Type:';
avmon.config.objectNumberCannotBeModified = '(The object number cannot be changed after it is created and must be unique.)';
avmon.config.objectNumber = 'Object Number';
avmon.config.objectName = 'Object Name';
avmon.config.receivingPerformanceData = '(Used to receive performance data; the host object can be left empty)';
avmon.config.allLevel = 'All Level';
avmon.config.typeId = 'Type';
avmon.config.commentSql = 'Sql';


avmon.dashboard = {};
avmon.dashboard.content = 'Content';
avmon.dashboard.severeAlarmInHalfAnHour = 'Critical Alarm(s) Within Half an Hour';
avmon.dashboard.severeAlarmInAnHour = 'Critical Alarm(s) Within an Hour';
avmon.dashboard.allOfSevereAlarm = 'All Critical Alarms';
avmon.dashboard.allDevice = 'All Devices';
avmon.dashboard.generalInfomation = 'General Information';
avmon.dashboard.cpuModel = 'CPU Model';
avmon.dashboard.cpu = 'CPU';
avmon.dashboard.operateFrequency = 'Running Frequency';
avmon.dashboard.operateTime = 'Running Time';
avmon.dashboard.currentUsers = 'Number of Current Users';
avmon.dashboard.currentAlarmNumber = 'Number of Current Alarms';
avmon.dashboard.keyProcesses = 'Key Processes';
avmon.dashboard.process = 'Process';
avmon.dashboard.memory = 'Memory';
avmon.dashboard.userMemoryUsage = '[\'User Memory Utilization Ratio\', \'System\', \'Available\']';
avmon.dashboard.swapUsageRate = '[\'SWAP Utilization Ratio\', \'Idleness Ratio\']';
avmon.dashboard.cacheReadHitRatio = 'Cache Read Hit Ratio';
avmon.dashboard.cacheWriteHitRatio = 'Cache Write Hit Ratio';
avmon.dashboard.dDayHHour = 'DDHH';
avmon.dashboard.latestWarning = 'Latest Alarms';
avmon.dashboard.networkPort = 'Network Interface';
avmon.dashboard.bandwidthAvailabilityRatio = 'Bandwidth Utilization Ratio';
avmon.dashboard.sendingRate = 'Sending Rate';
avmon.dashboard.receiveRate = 'Receiving Rate';
avmon.dashboard.packetSendSec = 'Number of Packets Sent per second';
avmon.dashboard.packetReceptionSec = 'Number of Packets Received per second';
avmon.dashboard.topnProcesses = 'TOPN Process';
avmon.dashboard.diskIO = 'Disk IO';
avmon.dashboard.logicalVolume = 'Logical Volume';
avmon.dashboard.powerSupplyFan = 'PSU Fan';
avmon.dashboard.powerSupplyConditionNormal = 'Power Status: Normal';
avmon.dashboard.fanConditionNormal = 'Fan Status: Normal';
avmon.dashboard.size = 'Size';
avmon.dashboard.speed = 'Speed';
avmon.dashboard.disk = 'Disk';
avmon.dashboard.notDiskInfomationCanDisplay = 'No disk information is available';
avmon.dashboard.networkCard = 'NIC';
avmon.dashboard.powerSupply = 'Power Supply:';
avmon.dashboard.notRelatedDataCanDisplay = 'No related data is available';
avmon.dashboard.mainBoard = 'Mother Board';
avmon.dashboard.width = 'Width';
avmon.dashboard.fan = 'Fan';
avmon.dashboard.rotateSpeed = 'Rotational Speed';
avmon.dashboard.crate = 'Enclosure';
avmon.dashboard.resourceID = 'Resource ID';
avmon.dashboard.instance = 'Instance';
avmon.dashboard.ampInstanceNumber = 'AMP Instance Number';
avmon.dashboard.aotoRefresh = 'Automatically Refresh';
avmon.dashboard.manualRefresh = 'Manually Refresh';
avmon.dashboard.kpiDetail = 'KPI Details';
avmon.dashboard.networkCardName = 'NIC Name';
avmon.dashboard.networkCardStatus = 'NIC Status';
avmon.dashboard.networkFlowPKGTS = 'Network Traffic Pkgts';
avmon.dashboard.cumulativeSendPKGTS = 'Accumulated Pkgts Sent ';
avmon.dashboard.cumulativeReceivePKGTS = 'Accumulated Pkgts Received';
avmon.dashboard.userProcessesNumber = 'Number of User Processes';
avmon.dashboard.precessPercentage = 'Process Percentage';
avmon.dashboard.residualMemory = 'Remaining Memory';
avmon.dashboard.usage = 'Utilization Ratio';
avmon.dashboard.writePS = 'Write p/s';
avmon.dashboard.readPS = 'Read p/s';
avmon.dashboard.literacyRateRWS = 'Read and Write Ratio rw/s';
avmon.dashboard.path = 'Path';
avmon.dashboard.notFindPartOfRealTimeData = 'No valid real-time data for this section is found';
avmon.dashboard.mplogList = 'MPLog List';
avmon.dashboard.keyword = 'Keyword';
avmon.dashboard.logEntry = 'Logs Entered by';
avmon.dashboard.producer = 'Produced by';
avmon.dashboard.sensorType = 'Sensor Type';
avmon.dashboard.sensorNumber = 'Number of Sensors';
avmon.dashboard.mpStatus = 'MP Status';
avmon.dashboard.noSubclassInfomation = 'No sub-category information is available.';
avmon.dashboard.alarm = 'Alarm';
avmon.dashboard.item = 'Items';
avmon.dashboard.dashboard = 'Dashboard';
avmon.dashboard.manufacturer = 'Manufacturer';
avmon.dashboard.cpuUsageRate = 'CPU Utilization Ratio';
avmon.dashboard.cpuVacancyRage = 'CPU Idleness Ratio';
avmon.dashboard.delayRate = 'Delaying Percentage';
avmon.dashboard.cacheUsageRate = 'Cache Utilization Ratio';
avmon.dashboard.writeDelayUtilization = 'Write Delay Utilization Ratio';
avmon.dashboard.netPortInfomation = 'Network Port Information';
avmon.dashboard.frontPort = 'Front Port';
avmon.dashboard.delayRead = 'Read Delay';
avmon.dashboard.delayWrite = 'Write Delay';
avmon.dashboard.rate = 'Rate';
avmon.dashboard.systemStatus = 'System Status';
avmon.dashboard.powerStatusUnknown = 'Power Supply Status: Unknown';
avmon.dashboard.fanStatusNuknown = 'Fan Status: Unknown';
avmon.dashboard.groupInfomation = 'Group Information';
avmon.dashboard.groupName = 'Group Name';
avmon.dashboard.groupIOPS = 'Group IOPS';
avmon.dashboard.groupDelayRate = 'Percentage of Group Latency';
avmon.dashboard.diskInformation = 'Disk Information';
avmon.dashboard.diskName = 'Disk Name';
avmon.dashboard.diskIOPS = 'Disk IOPS';
avmon.dashboard.diskDelayRate = 'Ratio of Disk Latency';
avmon.dashboard.deviceList = 'Device List';
avmon.dashboard.alarmDevices = 'Alarm Device';
avmon.dashboard.tai = 'Devices';
avmon.dashboard.healthDegree = 'Score ';
avmon.dashboard.point = 'Minute';
avmon.dashboard.database = 'Database';
avmon.dashboard.ge = 'Databases';
avmon.dashboard.network = 'Network';
avmon.dashboard.availability = 'Availability';
avmon.dashboard.storage = 'Storage';
avmon.dashboard.middleware = 'Middleware';
avmon.dashboard.business = 'Business';
avmon.dashboard.vmhost = 'Virtual Machine';
avmon.dashboard.alarmList = 'Alarm List';
avmon.dashboard.realipSetSuccess = 'The physical IP has been set successfully';
avmon.dashboard.realipSetFailed = 'Failed to set the physical IP';
avmon.dashboard.hostDashboard='Host Dashboard';
avmon.dashboard.kpiTrend='KPI Trend';
avmon.dashboard.kpiView='Kpi View';
avmon.dashboard.alarmList='Alarms';
avmon.dashboard.vmList='VM List';
avmon.dashboard.vmDashboard='VM Dashboard';
avmon.dashboard.iloDashboard='ILO Dashboard';
avmon.dashboard.physicalEngineDashboard='PhysicalEngine Dashboard';
avmon.dashboard.resourceView = 'Resource View';
avmon.dashboard.deviceStatusView = 'Device Status';
avmon.dashboard.totalView = 'Total View';
avmon.dashboard.storageDashboard='Storage Dashboard';
avmon.dashboard.dataCenter='Data Center';

avmon.deploy = {};
avmon.deploy.addMonitorHost = 'Add Monitoring Host';
avmon.deploy.monitoringObjectNumber = 'Monitoring Object Number';
avmon.deploy.hostName = 'Host Name';
avmon.deploy.objectType = 'Object Type';
avmon.deploy.ipaddress = 'IP';
avmon.deploy.operatingSystem = 'OS';
avmon.deploy.agentStatus = 'Agent Status';
avmon.deploy.unknown = 'Unknown';
avmon.deploy.hangUp = 'Suspend';
avmon.deploy.restore = 'Resume';
avmon.deploy.CIProperty = 'CI Attribute';
avmon.deploy.deployedEquipment = 'Deployed Device';
avmon.deploy.deployed = 'Deployed';
avmon.deploy.undeployed = 'Not Deployed';
avmon.deploy.download = 'Download';
avmon.deploy.monitoringAgent = 'Monitoring Agent';
avmon.deploy.deployedStatus = 'Status';
avmon.deploy.ampInstance = 'Amp Instance';
avmon.deploy.title = 'Title';
avmon.deploy.currentVersion = 'Current Version';
avmon.deploy.installAndUpdateAMP = 'Update AMP';
avmon.deploy.referenceHostName = 'Reference Host Name';
avmon.deploy.systemId = 'System Identification';
avmon.deploy.system = 'Operating System';
avmon.deploy.systemVersion = 'Operating System Version';
avmon.deploy.agentVersion = 'Agent Version';
avmon.deploy.lastActivity = 'Last Activity Time';
avmon.deploy.exceed = 'Exceeds';
avmon.deploy.unsentData = 'Amount of Data Not Sent Per Minute';
avmon.deploy.ampInstallSituation = 'AMP Installation';
avmon.deploy.agentUpgrade = 'Agent Upgrade';
avmon.deploy.hostPingStatus = 'Host Ping Status';
avmon.deploy.collectTime = 'Collection Time';
avmon.deploy.pingStatus = 'PING Status';
avmon.deploy.retrieve = 'Search';
avmon.deploy.choiceBeIssuedHost = 'Please select the host to which you want the policy to be issued!';
avmon.deploy.deployingLaterOn = 'Deploying, please wait...';
avmon.deploy.beIssueResult = 'Issuing Result';
avmon.deploy.changeTacticsIssued = 'Do you want to modify the issuing policy?';
avmon.deploy.whetherUpgrade = 'Do you want to upgrade?';
avmon.deploy.choiseBeUpgradedHost = 'Please select the host you want upgrade!';
avmon.deploy.ampConfiguration = 'AMP Configuration';
avmon.deploy.ampName = 'AMP Name';
avmon.deploy.status = 'Status';
avmon.deploy.start = 'Start';
avmon.deploy.stop = 'Stop';
avmon.deploy.issuedByScript = 'Issue Script';
avmon.deploy.issueByConfiguration = 'Issue Configuration';
avmon.deploy.schedulingPolicy = 'Scheduling Policy';
avmon.deploy.kpiName = 'KPI Name';
avmon.deploy.schedulingConfigurationString = 'Schedule Configuration String';
avmon.deploy.group = 'Group';
avmon.deploy.editSchedulingPolicy = 'Edit Scheduling Policy';
avmon.deploy.issueByChosenPolicy = 'Issue Selected Policy';
avmon.deploy.issueByAllPolicy = 'Issue All Policies';
avmon.deploy.startingAMPInstance = 'Starting the AMP instance, please wait...';
avmon.deploy.starting = 'Starting...';
avmon.deploy.running = 'Running';
avmon.deploy.operationFailure = 'The operation failed!';
avmon.deploy.stoppingAMPInstance = 'Stopping the AMP instance, please wait...';
avmon.deploy.stopRunning = 'Stop Running';
avmon.deploy.beIssuedByScriptPleaseWait = 'Issuing scripts, please wait...';
avmon.deploy.issuing = 'Issuing...';
avmon.deploy.issuingConfigurationPleaseWait = 'Issuing configuration, please wait...';
avmon.deploy.error = 'Error';
avmon.deploy.mustFillOut = ' Required!';
avmon.deploy.savingDataPleaseWait = 'Saving data, please wait...';
avmon.deploy.saving = 'Saving...';
avmon.deploy.dataSavingSuccessfully = 'Data has been saved successfully!';
avmon.deploy.pleaseSelectASetOfStrategiesForEdit = 'Please select a group of policies to be edited!';
avmon.deploy.pleaseSelectNeedIssueStrategy = 'Please select policies to be issued!';
avmon.deploy.schedulingPolicyChangeSuccess = 'Scheduling policies have been modified successfully!';
avmon.deploy.schedulingPolicyChangeFailure = 'Failed to modify scheduling policies';
avmon.deploy.confirmChanges = 'Confirm Modification';
avmon.deploy.confirmTheOperation = 'The scheduling policies you are editing will apply to other KPI codes in the same group. Do you want to continue?';
avmon.deploy.issuingStrategyPleaseWait = 'Issuing policies, please wait...';
avmon.deploy.issueStrategySuccess = 'Policies Issued successfully!';
avmon.deploy.issueStrategyFailure = 'Failed to issue policies';
avmon.deploy.properties = 'Attribute';
avmon.deploy.value = 'Value';
avmon.deploy.addMonitoringObjects = 'Add Monitoring Object';
avmon.deploy.chooseObjectType = 'Select Object Type:';
avmon.deploy.objectNumberCannotBeModified = '(The object number cannot be changed after it is created and must be unique.)';
avmon.deploy.objectNumber = 'Object Number';
avmon.deploy.objectName = 'Object Name';
avmon.deploy.receivingPerformanceData = '(Used to receive performance data; the host object can be left empty)';
avmon.deploy.physicsIP = 'Physical IP';
avmon.deploy.allStatus = 'All Statuses';
avmon.deploy.allOS = 'All';
avmon.deploy.unNormal = 'Exception';
avmon.deploy.deployedTime='Upgrade Time';
avmon.deploy.targetMoType = 'AMP Target Type';
avmon.deploy.ampUpgrade ='AMP Upgrade';
avmon.deploy.ampUpgradeTime = 'lastUpgrade Time';
avmon.deploy.osVersion = 'OS Version';
avmon.deploy.logs ='logs';
avmon.deploy.loadWin ='AMP Updating';
avmon.deploy.ampDetail = 'updating detail';
avmon.deploy.pushing = 'pushing...';
avmon.deploy.process = 'current process:';
avmon.deploy.timeout = 'time out';
avmon.deploy.pushAmpFinish ='Amp updated!';
avmon.deploy.finish = 'finished'; 

avmon.equipmentCenter = {};
avmon.equipmentCenter.addMonitorHost = 'Add Monitoring Host';
avmon.equipmentCenter.monitoringObjectNumber = 'Monitoring Object Number';
avmon.equipmentCenter.hostName = 'Host Name';
avmon.equipmentCenter.objectType = 'Object Type';
avmon.equipmentCenter.ipaddress = 'IP';
avmon.equipmentCenter.operatingSystem = 'Operating System';
avmon.equipmentCenter.agentStatus = 'Agent Status';
avmon.equipmentCenter.unknown = 'Unknown';
avmon.equipmentCenter.agentLastUpdateTime = 'Agent Last Updated';
avmon.equipmentCenter.hangUp = 'Suspend';
avmon.equipmentCenter.restore = 'Resume';
avmon.equipmentCenter.CIProperty = 'CI Attribute';
avmon.equipmentCenter.retrieve = 'Search';
avmon.equipmentCenter.refresh = 'Refresh';
avmon.equipmentCenter.newlyIncreased = 'New';
avmon.equipmentCenter.remove = 'Remove';
avmon.equipmentCenter.installedEquipment = 'Installed Device';
avmon.equipmentCenter.properties = 'Attribute';
avmon.equipmentCenter.value = 'Value';
avmon.equipmentCenter.addMonitoringObjects = 'Add Monitoring Object';
avmon.equipmentCenter.chooseObjectType = 'Select Object Type:';
avmon.equipmentCenter.objectNumberCannotBeModified = '(The object number cannot be changed after it is created and must be unique.)';
avmon.equipmentCenter.objectNumber = 'Object ID';
avmon.equipmentCenter.objectName = 'Object Name';
avmon.equipmentCenter.receivingPerformanceData = '(Used to receive performance data; the host object can be left empty)';
avmon.equipmentCenter.protocol = 'protocol';
avmon.equipmentCenter.protocolCanNotEmpty='The protocol cannot be left blank!';
avmon.equipmentCenter.generateBitCode = 'Generate 2D Codes';
avmon.equipmentCenter.queryBitCode = 'Query 2D Codes';
avmon.equipmentCenter.print='Print';
avmon.equipmentCenter.confirmGenerate2Dcode= 'Do you want to generate 2D codes?';
avmon.equipmentCenter.BitCodeGenerated='2D codes Generated!';
avmon.equipmentCenter.BitCodeGeneratedFailed = '2D codes Generating Failed!';
avmon.equipmentCenter.confirmDelete ='Do you want to delete the MO?';
avmon.equipmentCenter.moDeleteSuccess = 'MO deleted success!';
avmon.equipmentCenter.moDeleteFailed = 'MO delete Failed!';

avmon.kpiCompare = {};
avmon.kpiCompare.time = 'Time';
avmon.kpiCompare.pkiValue = 'KPI Value';
avmon.kpiCompare.timeTo = 'Time:';
avmon.kpiCompare.pkiAverageValue = 'KPI Average:';
avmon.kpiCompare.hostList = 'Host List';
avmon.kpiCompare.kpiContrast = 'KPI Comparison';
avmon.kpiCompare.to = 'To';
avmon.kpiCompare.selectContrastHost = 'Please select host(s) to be compared!';
avmon.kpiCompare.contrastHostCannotMoreThanFive = 'The number of hosts to be compared cannot exceed 5!';
avmon.kpiCompare.selectContrastKPI = 'Please select KPIs to be compared!';
avmon.kpiCompare.loading = 'Loading...';
avmon.kpiCompare.kpiInstance = 'KPI Instance:';
avmon.kpiCompare.value = 'Value:';
avmon.kpiCompare.loadFail = 'Failed to load!';
avmon.kpiCompare.query = 'Query';
avmon.kpiCompare.sizeSelect = 'Size Select';
avmon.kpiCompare.originalData = 'Original Data';
avmon.kpiCompare.byHour = 'By Hour';
avmon.kpiCompare.byDay = 'By Day';

avmon.system = {};
avmon.system.root = 'Root Node';
avmon.system.businessSystemID = 'Business System ID';
avmon.system.businessSystemName = 'Business System Name';
avmon.system.add = 'Add';
avmon.system.businessSystemManagement = 'Business System Management';
avmon.system.moProperties = 'Monitor Properties';
avmon.system.selectOperateLine = 'Select a row to be processed!';
avmon.system.whetherToDelete = 'If you deleted one of the system attributes of a monitoring object, the system might not function properly! Are you sure you want to delete it?';
avmon.system.beforePageText = 'Page';
avmon.system.afterPageText = ' of {0} pages';
avmon.system.displayMsg = 'Showing Item {0} - {1} of {2} items';
avmon.system.emptyMsg = 'No data available';
avmon.system.modify = 'Modify';
avmon.system.department = 'Department';
avmon.system.departmentName = 'Department Name';
avmon.system.departmentManagement = 'Department Management';
avmon.system.loadFail = 'Failed to load!';
avmon.system.licenseMessage = 'License Information';
avmon.system.expiryDate = 'Due Date';
avmon.system.maxMonitorNumber = 'Maximum Monitoring Amount';
avmon.system.waiting = 'Please wait';
avmon.system.loadingFormData = 'Loading report data, please wait...';
avmon.system.parentMenuName = 'Parent Menu Name';
avmon.system.whetherDisplay = 'Whether to Display';
avmon.system.accordingSequence = 'Display Sequence';
avmon.system.menuName = 'Menu Name';
avmon.system.menuRUL = 'Menu URL';
avmon.system.note = 'Remark';
avmon.system.icon = 'Icon';
avmon.system.moduleManagement = 'Module Management';
avmon.system.systemModule = 'System Module';
avmon.system.roleName = 'Role Name';
avmon.system.roleDescription = 'Role Description';
avmon.system.roleManagement = 'Role Management';
avmon.system.roleAuthorization = 'Role Permission';
avmon.system.account = 'Account';
avmon.system.password = 'Password';
avmon.system.userName = 'User Name';
avmon.system.mobilePhone = 'Mobile Phone';
avmon.system.officePhone = 'Office Phone';
avmon.system.role = 'Role';
avmon.system.userManagement = 'User Management';
avmon.system.userRole = 'User Role';
avmon.system.widget = 'Gadget';
avmon.system.regex = 'Regular Expression';
avmon.system.targetString = 'Target String';
avmon.system.validate = 'Verify';
avmon.system.synchronousMemory = 'Synchronize Memory';
avmon.system.message = 'If any alarm rule (such as Alarm Filtering Rule, Alarm Merging Rule, and Alarm Translating Rule) is modified, you need to synchronize the memory to make your modification become effective.If you have performed any action on a monitoring object, such as adding a monitoring object and modify its key attributes, you need to synchronize the memory as well.';
avmon.system.synchronousAlarmRuleIntoMemory = 'Synchronize the alarm processing rule to the memory';
avmon.system.synchronousMonitoringObjectsIntoMemory = 'Synchronize the monitoring object to the memory';
avmon.system.synchronousing = 'Synchronizing the memory, please wait...';
avmon.system.updateMemory = 'Update the memory';
avmon.system.synchronousFail = 'Failed to synchronize the memory. Please try again later.';
avmon.system.typeId = 'Category';
avmon.system.attrId = 'English Name of Attribute';
avmon.system.caption = 'Attribute Name';
avmon.system.classinfo = 'Category Information';
avmon.system.hide = 'Whether to Display';
avmon.system.passwd = 'User Password';
avmon.system.valueType = 'Attribute Type';
avmon.system.orderIndex = 'Sequence';
avmon.system.defaultValue = 'Default Value';
avmon.system.nullable = 'can be left blank';
avmon.system.kpiCode = 'KPI Code';
avmon.system.inputNotAllowed = 'Only English letter characters are allowed';
avmon.system.inputExists = 'A category with this English name already exists';
avmon.system.inputNotLongerThan100 = 'The number of characters cannot exceed 100';
avmon.system.DepartmentName = 'Department Name';
avmon.system.moduleId = 'Module ID';
avmon.system.onlywordsallowed = 'only words are allowed!';

avmon.ireport = {};
avmon.ireport.conditionSet = 'Criteria Setting';
avmon.ireport.startDate = 'Starting Date';
avmon.ireport.dueDate = 'Ending Date';
avmon.ireport.generateReport = 'Generate Report';
avmon.ireport.exportPDF = 'Export to PDF';
avmon.ireport.exportWord = 'Export to Word';
avmon.ireport.exportExcel = 'Export to Excel';
avmon.ireport.pleaseSelectDeviceForExportReport = 'Please select the device to which you want to export the report!';
avmon.ireport.parameterValueCantBeEmpty = 'The parameter value cannot be null!';
avmon.ireport.selectDisplayAttributes = 'Select Display Attribute';
avmon.ireport.properties = 'Attribute';
avmon.ireport.propertiesName = 'Attribute Name';
avmon.ireport.deviceIP = 'Device IP';
avmon.ireport.system = 'Operating System';
avmon.ireport.pleaseSelectAttributeOfExportReport = 'Please select the attribute of the device to which you want to export the report!';
avmon.ireport.loading = 'Loading...';
avmon.ireport.chooseDevice = 'Select Device';
avmon.ireport.deviceIP = 'Device IP';
avmon.ireport.lastestReportTime = 'Latest Reporting Time';
avmon.ireport.deviceType = 'Device Type';
avmon.ireport.deviceDescription = 'Device Description';
avmon.ireport.search = 'Query';
avmon.ireport.time = 'Time';
avmon.ireport.to = 'To';
avmon.ireport.startTime = 'Starting Time';
avmon.ireport.dueTime = 'Deadline';
avmon.ireport.dailyReport='Daily Report';
avmon.ireport.weeklyReport = 'Weekly Report';
avmon.ireport.monthlyReport = 'Monthly Report';
avmon.ireport.dateSelect ='Date Select';
avmon.ireport.reportType ='Report Type';
avmon.ireport.inspectionDate = 'Inspection Date';
	
avmon.modules = {};
avmon.modules.userId = 'User ID';
avmon.modules.password = 'Password';
avmon.modules.defaultThemme = 'Default Theme';
avmon.modules.grayTheme = 'Gray Theme';
avmon.modules.blackTheme = 'Black Theme';
avmon.modules.conciseTest = 'Simplified (Testing)';
avmon.modules.login = 'Login';
avmon.modules.reset = 'Reset';
avmon.modules.savePasswordInLocal = 'Save your password';
avmon.modules.licenseRegister = 'License Register';
avmon.modules.logining = 'Logging in, please wait...';
avmon.modules.starting = 'Starting...';
avmon.modules.loginFailed = 'Login failed';
avmon.modules.wrong = 'Error';
avmon.modules.wrongRetry = 'An error occurred. Please try later!';
avmon.modules.initialRegistrationCode ='Initial Code';
avmon.modules.registrationCode = 'Registration Code';
avmon.modules.register = 'Register';
avmon.modules.registering = 'Registering, please wait...';
avmon.modules.registerLicense = 'Register a License';

avmon.modules.logout = 'Logoff';
avmon.modules.alarmMonitoring = 'Alarm';
avmon.modules.realtimePerformance = 'Performance';
avmon.modules.analysisPerformance = 'Analysis';
avmon.modules.realTime = 'Real-time Status';
avmon.modules.queryAndReport = 'Report';
avmon.modules.resourceManagement ='Resource';
avmon.modules.configurationManagement = 'Configuration';
avmon.modules.deploymentManagement = 'Deployment';
avmon.modules.systemManagement = 'System';
avmon.modules.networkInspectionConfig = 'Network Inspection Configuration';
avmon.modules.oidManagement = 'OID Management';
avmon.modules.oidConfiguration = 'OID Configuration';

avmon.modules.kpi_getConfiguration = 'KPI-GET Configuration';
avmon.modules.currentUser = 'Current User:';
avmon.modules.changePassword = 'Change Password';

avmon.modules.passwordNotMatch = 'The password and confirmation password you entered are different!';
avmon.modules.account = 'Account';
avmon.modules.originalPassword = 'Original Password';
avmon.modules.newPassword = 'New Password';
avmon.modules.confirmPassword = 'Password Confirmation';

avmon.modules.startProgram = 'Starting the program...';
avmon.modules.loadUIComponents = 'Loading UI components...';
avmon.modules.loadUICSS = 'Loading UI patterns...';
avmon.modules.loadMainInterface = 'Loading the main interface...';
avmon.config.notEqual='Not Equal to';
avmon.config.notIn='Exclude';
avmon.config.include='Include';
avmon.config.regex='Regular Expression';
avmon.config.like='like';
avmon.config.notLike='notlike';
avmon.config.oidname = 'OID Name';
avmon.config.kpiCode = 'KPI Code';

avmon.vmlist = {};
avmon.vmlist.physicsHost = 'Physics Host';
avmon.vmlist.physicsHostName = 'Host Name';
avmon.vmlist.status = 'Status';
avmon.vmlist.vmlist = 'VM List';
avmon.vmlist.cpusage = 'CPU Usage';
avmon.vmlist.memusage = 'Mem Usage';
avmon.vmlist.memSize = 'Mem Size';

avmon.vmlist.vmName = 'VM Name';
avmon.vmlist.host = 'Host';
avmon.vmlist.usedSpace = 'Used Space';
avmon.vmlist.hostFre = 'Host Frequency';
avmon.vmlist.hostMem = 'Host Mem';
avmon.vmlist.storage = 'Storage';
avmon.vmlist.storageNm = 'Name';