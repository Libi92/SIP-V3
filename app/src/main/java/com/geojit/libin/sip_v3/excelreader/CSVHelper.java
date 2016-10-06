/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geojit.libin.sip_v3.excelreader;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author libin
 */
public class CSVHelper {
    private static final String VERY_AGGRESSIVE = "Very Aggressive";
    private static final String AGGRESSIVE = "Aggressive";
    private static final String MODERATE = "Moderate";
    private static final String CONSERVATIVE = "Conservative";
    private static final String FILE_NAME = "portfolio_new.csv";
    private static String currentMode = null;
    private static PortfolioReturns portfolioReturns;
    private static HashMap<String, ArrayList<FundModal>> funds;
    private static int count = 0;

    public static PortfolioReturns read(Context context) {

        try {
            InputStream inputStream = context.getAssets().open(FILE_NAME);
            InputStreamReader reader = new InputStreamReader(inputStream);
            portfolioReturns = new PortfolioReturns();
            funds = new HashMap<>();

            funds.put(VERY_AGGRESSIVE, new ArrayList<FundModal>());
            funds.put(AGGRESSIVE, new ArrayList<FundModal>());
            funds.put(MODERATE, new ArrayList<FundModal>());
            funds.put(CONSERVATIVE, new ArrayList<FundModal>());

            BufferedReader br = new BufferedReader(reader);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(VERY_AGGRESSIVE)) {
                    currentMode = VERY_AGGRESSIVE;
                    continue;
                } else if (line.startsWith(AGGRESSIVE)) {
                    currentMode = AGGRESSIVE;
                    continue;
                } else if (line.startsWith(MODERATE)) {
                    currentMode = MODERATE;
                    continue;
                } else if (line.startsWith(CONSERVATIVE)) {
                    currentMode = CONSERVATIVE;
                    continue;
                }

                if (currentMode != null) {

                    String[] split = line.split(",");
                    String SchemeName = split[1];
                    String Category = split[2];
                    String MonthlyAmount = split[3];
                    String Allocation = split[4];

                    String year3InvestedAmount = split[5];
                    String year3CurrentValue = split[6];
                    String year3XIRR = split[7];
                    String year3CAGR = split[8];

                    String year5InvestedAmount = split[9];
                    String year5CurrentValue = split[10];
                    String year5XIRR = split[11];
                    String year5CAGR = split[12];

                    String year10InvestedAmount = split[13];
                    String year10CurrentValue = split[14];
                    String year10XIRR = split[15];
                    String year10CAGR = split[16];

                    String nav = split[17];
                    String change = split[18];
                    String percentage = split[19];
                    String image = split[20];
                    String schemeCode = split[21];

                    ReturnsYear year3 = new ReturnsYear();
                    year3.setTotalAmountInvested(Float.parseFloat(year3InvestedAmount));
                    year3.setCurrentValue(Float.parseFloat(year3CurrentValue));
                    year3.setXIRR(Float.parseFloat(year3XIRR));
                    year3.setCAGR(Float.parseFloat(year3CAGR));

                    ReturnsYear year5 = new ReturnsYear();
                    year5.setTotalAmountInvested(Float.parseFloat(year5InvestedAmount));
                    year5.setCurrentValue(Float.parseFloat(year5CurrentValue));
                    year5.setXIRR(Float.parseFloat(year5XIRR));
                    year5.setCAGR(Float.parseFloat(year5CAGR));

                    ReturnsYear year10 = new ReturnsYear();
                    year10.setTotalAmountInvested(Float.parseFloat(year10InvestedAmount));
                    year10.setCurrentValue(Float.parseFloat(year10CurrentValue));
                    year10.setXIRR(Float.parseFloat(year10XIRR));
                    year10.setCAGR(Float.parseFloat(year10CAGR));

                    FundModal modal = new FundModal();
                    modal.setSchemeName(SchemeName);
                    modal.setCategory(Category);
                    modal.setMonthlyAmount(Float.parseFloat(MonthlyAmount));
                    modal.setAllocation(Allocation);
                    modal.setYear3(year3);
                    modal.setYear5(year5);
                    modal.setYear10(year10);
                    modal.setNav(nav);
                    modal.setChange(change);
                    modal.setPercentage(percentage);
                    modal.setImage(image);
                    modal.setSchemeCode(schemeCode);

                    funds.get(currentMode).add(modal);

                    count++;
                    if (count == 10) {
                        line = br.readLine();

                        split = line.split(",");
                        year3InvestedAmount = split[5];
                        year3CurrentValue = split[6];
                        year3XIRR = split[7];
                        year3CAGR = split[8];

                        year5InvestedAmount = split[9];
                        year5CurrentValue = split[10];
                        year5XIRR = split[11];
                        year5CAGR = split[12];

                        year10InvestedAmount = split[13];
                        year10CurrentValue = split[14];
                        year10XIRR = split[15];
                        year10CAGR = split[16];

                        year3 = new ReturnsYear();
                        year3.setTotalAmountInvested(Float.parseFloat(year3InvestedAmount));
                        year3.setCurrentValue(Float.parseFloat(year3CurrentValue));
                        year3.setXIRR(Float.parseFloat(year3XIRR));
                        year3.setCAGR(Float.parseFloat(year3CAGR));

                        year5 = new ReturnsYear();
                        year5.setTotalAmountInvested(Float.parseFloat(year5InvestedAmount));
                        year5.setCurrentValue(Float.parseFloat(year5CurrentValue));
                        year5.setXIRR(Float.parseFloat(year5XIRR));
                        year5.setCAGR(Float.parseFloat(year5CAGR));

                        year10 = new ReturnsYear();
                        year10.setTotalAmountInvested(Float.parseFloat(year10InvestedAmount));
                        year10.setCurrentValue(Float.parseFloat(year10CurrentValue));
                        year10.setXIRR(Float.parseFloat(year10XIRR));
                        year10.setCAGR(Float.parseFloat(year10CAGR));

                        if (currentMode.equals(AGGRESSIVE)) {
                            portfolioReturns.setAggressiveYear3(year3);
                            portfolioReturns.setAggressiveYear5(year5);
                            portfolioReturns.setAggressiveYear10(year10);
                        } else if (currentMode.equals(VERY_AGGRESSIVE)) {
                            portfolioReturns.setVeryAggressiveYear3(year3);
                            portfolioReturns.setVeryAggressiveYear5(year5);
                            portfolioReturns.setVeryAggressiveYear10(year10);
                        } else if (currentMode.equals(MODERATE)) {
                            portfolioReturns.setModerateYear3(year3);
                            portfolioReturns.setModerateYear5(year5);
                            portfolioReturns.setModerateYear10(year10);
                        } else if (currentMode.equals(CONSERVATIVE)) {
                            portfolioReturns.setConservativeYear3(year3);
                            portfolioReturns.setConservativeYear5(year5);
                            portfolioReturns.setConservativeYear10(year10);
                        }

                        count = 0;
                        currentMode = null;
                    }
                }
            }
            portfolioReturns.putAll(funds);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CSVHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return portfolioReturns;
    }
}
