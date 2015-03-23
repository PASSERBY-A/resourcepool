package com.hp.avmon.performance.store;

public class StoreFactory {
    private String storeType;

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }
    
    public KpiDataStore getKpiDataStore(){
        return null;
    }
}
