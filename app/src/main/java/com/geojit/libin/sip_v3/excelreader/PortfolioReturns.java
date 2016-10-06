package com.geojit.libin.sip_v3.excelreader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by libin on 21/07/16.
 */
public class PortfolioReturns implements Serializable {
    public static final String AGGRESSIVE = "Aggressive";
    public static final String VERY_AGGRESSIVE = "Very Aggressive";
    public static final String MODERATE = "Moderate";
    public static final String CONSERVATIVE = "Conservative";

    private HashMap<String, ArrayList<FundModal>> data;
    private ReturnsYear aggressiveYear3;
    private ReturnsYear aggressiveYear5;
    private ReturnsYear aggressiveYear10;

    private ReturnsYear veryAggressiveYear3;
    private ReturnsYear veryAggressiveYear5;
    private ReturnsYear veryAggressiveYear10;

    private ReturnsYear moderateYear3;
    private ReturnsYear moderateYear5;
    private ReturnsYear moderateYear10;

    private ReturnsYear conservativeYear3;
    private ReturnsYear conservativeYear5;
    private ReturnsYear conservativeYear10;

    public void putAll(HashMap<String, ArrayList<FundModal>> data) {
        this.data = data;
    }

    public ReturnsYear getAggressiveYear3() {
        return aggressiveYear3;
    }

    public void setAggressiveYear3(ReturnsYear aggressiveYear3) {
        this.aggressiveYear3 = aggressiveYear3;
    }

    public ReturnsYear getAggressiveYear5() {
        return aggressiveYear5;
    }

    public void setAggressiveYear5(ReturnsYear aggressiveYear5) {
        this.aggressiveYear5 = aggressiveYear5;
    }

    public ReturnsYear getAggressiveYear10() {
        return aggressiveYear10;
    }

    public void setAggressiveYear10(ReturnsYear aggressiveYear10) {
        this.aggressiveYear10 = aggressiveYear10;
    }

    public ReturnsYear getVeryAggressiveYear3() {
        return veryAggressiveYear3;
    }

    public void setVeryAggressiveYear3(ReturnsYear veryAggressiveYear3) {
        this.veryAggressiveYear3 = veryAggressiveYear3;
    }

    public ReturnsYear getVeryAggressiveYear5() {
        return veryAggressiveYear5;
    }

    public void setVeryAggressiveYear5(ReturnsYear veryAggressiveYear5) {
        this.veryAggressiveYear5 = veryAggressiveYear5;
    }

    public ReturnsYear getVeryAggressiveYear10() {
        return veryAggressiveYear10;
    }

    public void setVeryAggressiveYear10(ReturnsYear veryAggressiveYear10) {
        this.veryAggressiveYear10 = veryAggressiveYear10;
    }

    public ReturnsYear getModerateYear3() {
        return moderateYear3;
    }

    public void setModerateYear3(ReturnsYear moderateYear3) {
        this.moderateYear3 = moderateYear3;
    }

    public ReturnsYear getModerateYear5() {
        return moderateYear5;
    }

    public void setModerateYear5(ReturnsYear moderateYear5) {
        this.moderateYear5 = moderateYear5;
    }

    public ReturnsYear getModerateYear10() {
        return moderateYear10;
    }

    public void setModerateYear10(ReturnsYear moderateYear10) {
        this.moderateYear10 = moderateYear10;
    }

    public ReturnsYear getConservativeYear3() {
        return conservativeYear3;
    }

    public void setConservativeYear3(ReturnsYear conservativeYear3) {
        this.conservativeYear3 = conservativeYear3;
    }

    public ReturnsYear getConservativeYear5() {
        return conservativeYear5;
    }

    public void setConservativeYear5(ReturnsYear conservativeYear5) {
        this.conservativeYear5 = conservativeYear5;
    }

    public ReturnsYear getConservativeYear10() {
        return conservativeYear10;
    }

    public void setConservativeYear10(ReturnsYear conservativeYear10) {
        this.conservativeYear10 = conservativeYear10;
    }

    public Set<String> keySet() {
        return data.keySet();
    }

    public ArrayList<FundModal> get(String key) {
        return data.get(key);
    }

    public float getXIRRSum(String key, int year) {
        ArrayList<FundModal> funds = get(key);
        float sum = 0f;

        for (FundModal model : funds) {
            switch (year) {
                case 3:
                    sum += model.getYear3().getXIRR();
                    break;

                case 5:
                    sum += model.getYear5().getXIRR();
                    break;

                case 10:
                    sum += model.getYear10().getXIRR();
                    break;

                default:
                    break;
            }

        }

        return sum;
    }

    public void setData(HashMap<String, ArrayList<FundModal>> data) {
        this.data = data;
    }
}
