package com.geojit.libin.sip_v3.utils;

/**
 * Created by 10946 on 8/4/2016.
 */
public class SIPCalculator {

    private int year = 0;
    private int rate = 0;
    private int month = 0;
    private double investment = 0;
    private double amount = 0;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        month = (year*12);
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public double getInvestment() {
        return investment;
    }

    public void setInvestment(double investment) {
        this.investment = investment;
    }

    public double calculateSIP(){

        amount = ((((Math.pow((1+rate*.01/12),month))-1)/(rate*.01/12))*investment)*(1+rate*.01/12);

        amount = (double) Math.round(amount*100)/100;

        return amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double calculateEMI(){

        investment = (amount/(1+rate*.01/12))/(((Math.pow((1+rate*.01/12),month))-1)/(rate*.01/12));

        investment = Math.ceil(investment);

        return investment;
    }
}
