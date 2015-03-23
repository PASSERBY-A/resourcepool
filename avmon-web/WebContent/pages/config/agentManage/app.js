Ext.Loader.setConfig({
    enabled: true
});

Ext.application({
    models: [
        'AgentGridModel',
        'AgentAmpListModel',
        'AmpTypeComBoxModel',
        'StatusEnabledModel',
        'AmpInstAttrModel',
        'VmTreeModel',
        'IloHostModel',
        'AmpInstPolicyModel',
        'StatusEnabledModel',
        'AmpNormalScheduleModel'
    ],
    stores: [
        'AgentGridJsonStore',
        'AgentAmpListStore',

        'AmpTypeComBoxStore',
        'AmpInstAttrStore',
        'VmTreeStore',
        'IloHostListStore',
        'AmpInstPolicyStore',

        'AmpTypeComBoxStore',
        'StatusStoreList',
        'AmpNormalScheduleStore'
    ],
    views: [
        'AgentViewport',
        'AgentDetailWindow',
        'AmpAddWindow',
        'NormalAmpSetWindow',
        'VMAmpSetWindow',
        'ILOAmpSetWindow',
        'ILOConfigWindow',
        'MyPanel8'
    ],
    autoCreateViewport: true,
    name: 'MyApp'
});
