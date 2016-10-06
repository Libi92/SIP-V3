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
public class FundModal implements Serializable {
    private String SchemeCode;
    private String SchemeName;
    private String Category;
    private float MonthlyAmount;
    private String Allocation;
    private String nav;
    private String change;
    private String percentage;
    private String image;
    private ReturnsYear year3;
    private ReturnsYear year5;
    private ReturnsYear year10;

    public String getSchemeCode() {
        return SchemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        SchemeCode = schemeCode;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getSchemeName() {
        return SchemeName;
    }

    public void setSchemeName(String SchemeName) {
        this.SchemeName = SchemeName;
    }

    public float getMonthlyAmount() {
        return MonthlyAmount;
    }

    public void setMonthlyAmount(float MonthlyAmount) {
        this.MonthlyAmount = MonthlyAmount;
    }

    public String getAllocation() {
        return Allocation;
    }

    public void setAllocation(String Allocation) {
        this.Allocation = Allocation;
    }

    public ReturnsYear getYear3() {
        return year3;
    }

    public void setYear3(ReturnsYear year3) {
        this.year3 = year3;
    }

    public ReturnsYear getYear5() {
        return year5;
    }

    public void setYear5(ReturnsYear year5) {
        this.year5 = year5;
    }

    public ReturnsYear getYear10() {
        return year10;
    }

    public void setYear10(ReturnsYear year10) {
        this.year10 = year10;
    }

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
