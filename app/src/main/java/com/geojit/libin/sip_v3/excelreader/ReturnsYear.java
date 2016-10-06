/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geojit.libin.sip_v3.excelreader;

import java.io.Serializable;

/**
 * @author libin
 */
public class ReturnsYear implements Serializable {
    private float TotalAmountInvested;
    private float CurrentValue;
    private float XIRR;
    private float CAGR;

    public float getTotalAmountInvested() {
        return TotalAmountInvested;
    }

    public void setTotalAmountInvested(float TotalAmountInvested) {
        this.TotalAmountInvested = TotalAmountInvested;
    }

    public float getCurrentValue() {
        return CurrentValue;
    }

    public void setCurrentValue(float CurrentValue) {
        this.CurrentValue = CurrentValue;
    }

    public float getXIRR() {
        return XIRR;
    }

    public void setXIRR(float XIRR) {
        this.XIRR = XIRR;
    }

    public float getCAGR() {
        return CAGR;
    }

    public void setCAGR(float CAGR) {
        this.CAGR = CAGR;
    }
}
