package com.geojit.libin.sip_v3.modals;

/**
 * Created by 10946 on 8/17/2016.
 */
public class PortfolioDataModel {

    private String SchemeCode;
    private String SchemeName;
    private String FundType;
    private String NAV;
    private String holdingUnit;
    private String holdingValue;
    private String Icon;

    public String getSchemeCode() {
        return SchemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        SchemeCode = schemeCode;
    }

    public String getSchemeName() {
        return SchemeName;
    }

    public void setSchemeName(String schemeName         ) {
        SchemeName = schemeName;
    }

    public String getFundType() {
        return FundType;
    }

    public void setFundType(String fundType) {
        FundType = fundType;
    }

    public String getNAV() {
        return NAV;
    }

    public void setNAV(String NAV) {
        this.NAV = NAV;
    }

    public String getHoldingUnit() {
        return holdingUnit;
    }

    public void setHoldingUnit(String holdingUnit) {
        this.holdingUnit = holdingUnit;
    }

    public String getHoldingValue() {
        return holdingValue;
    }

    public void setHoldingValue(String holdingValue) {
        this.holdingValue = holdingValue;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }
}
