/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.geojit.libin.sip_v3.graphdatareader;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 10945
 */
public class GraphDataReader {

    private static final String FILE_NAME = "selectedNAV.CSV";
    private static HashMap<String, ArrayList<GraphDailyData>> data;

    public static ArrayList<GraphDailyData> getData(Context context, String schemeCode) {
        if (data == null) {
            GraphDataReader reader = new GraphDataReader();
            data = reader.read(context);
        }

        return data.get(schemeCode);
    }

    public HashMap<String, ArrayList<GraphDailyData>> read(Context context) {

        HashMap<String, ArrayList<GraphDailyData>> data = new HashMap<>();

        try {
            InputStream inputStream = context.getAssets().open(FILE_NAME);
            InputStreamReader reader = new InputStreamReader(inputStream);

            BufferedReader br = new BufferedReader(reader);

            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                GraphDailyData model = new GraphDailyData();
                model.setSchemeCode(split[0]);
                model.setSchemeName(split[1]);
                model.setNAV(Float.parseFloat(split[2]));
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(split[3]);
                model.setDate(date);


                if (data.get(split[0]) == null) {
                    data.put(split[0], new ArrayList<GraphDailyData>());
                }

                data.get(split[0]).add(model);
            }

        } catch (IOException ex) {
            Logger.getLogger(GraphDataReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }
}
