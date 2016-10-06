package com.geojit.libin.sip_v3.modals;

import java.io.Serializable;

/**
 * Created by 10945 on 8/6/2016.
 */
public class FundDataModal implements Serializable {
    private String SchemeCode;
    private String SchemeName;
    private String FundType;
    private String NAV;
    private String Growth;
    private String GrowthPercentage;
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

    public void setSchemeName(String schemeName) {
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

    public String getGrowth() {
        return Growth;
    }

    public void setGrowth(String growth) {
        Growth = growth;
    }

    public String getGrowthPercentage() {
        return GrowthPercentage;
    }

    public void setGrowthPercentage(String growthPercentage) {
        GrowthPercentage = growthPercentage;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }
}
