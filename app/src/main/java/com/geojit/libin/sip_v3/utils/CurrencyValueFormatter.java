package com.geojit.libin.sip_v3.utils;

/**
 * Created by 10946 on 8/5/2016.
 */
public class CurrencyValueFormatter {

    public static String generalFormatter(String temp){

        if(temp.contains("E"))
        {
            temp = exponentFormatter(temp);
        }

        if(temp.contains(".0"))
        {
            temp = temp.replace(".0","");
        }

        if(temp.contains(",0"))
        {
            temp = temp.replace(",0","0");
        }

        return temp;
    }

    public static String currencyFormatter(String temp){

        if(temp.endsWith("0000000")) {

            temp = temp.substring(0,(temp.length()-7))+"C";

        }else if(temp.endsWith("00000")){

            temp = temp.substring(0,(temp.length()-5))+"L";

        }else if(temp.endsWith("000"))  {

            temp = temp.substring(0,(temp.length()-3))+"K";
        }

        return temp;
    }

    public static String exponentFormatter(String temp)   {

        int exponent = Integer.parseInt(temp.substring(temp.length()-1));

        String powerValue = "";

        for(int i = 1; i< exponent; i++){

            powerValue += "0";

        }

        String normalValue = temp.substring(0,3);

        int result = (int) (Float.parseFloat(normalValue) * 10);

        temp = Integer.toString(result)+powerValue;

        return temp;
    }
}
