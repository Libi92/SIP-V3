/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.geojit.libin.sip_v3.graphdatareader;

import java.util.Date;

/**
 * @author 10945
 */
public class GraphDailyData {
    private String SchemeCode;
    private String SchemeName;
    private float NAV;
    private java.util.Date Date;

    public String getSchemeCode() {
        return SchemeCode;
    }

    public void setSchemeCode(String SchemeCode) {
        this.SchemeCode = SchemeCode;
    }

    public String getSchemeName() {
        return SchemeName;
    }

    public void setSchemeName(String SchemeName) {
        this.SchemeName = SchemeName;
    }

    public float getNAV() {
        return NAV;
    }

    public void setNAV(float NAV) {
        this.NAV = NAV;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }


}
