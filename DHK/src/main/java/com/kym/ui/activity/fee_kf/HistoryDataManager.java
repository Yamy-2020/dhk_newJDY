package com.kym.ui.activity.fee_kf;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;


public class HistoryDataManager {
    public static String DefaultAccessKey;

    public class HistoryDataItem {
        public String accessKey;
        public String robotName;
    }

    private static HistoryDataManager instance;

    public static synchronized HistoryDataManager getInstance() {
        if (instance == null) instance = new HistoryDataManager();
        return instance;
    }

    public String getRecentAccessKey(Context context) {
        /*
        对应的ome进行判断,走的是那种方向
         */
        /*if (OMID.equals("1H1AJD6SLKVADDM6")) {*/
//            DefaultAccessKey = "MTU3MTEjODBiMjNiZTctZjk0NS00Y2UxLWFhMGMtMTFiZjdhN2VlNjdhIzhjZTQzNWY2LWI2Y2UtNDUzNi1iODExLWMxYTRmMTk3ZWY4YSMwNGQ5OTgxNTRhNDJjZWUwNDQzMjEzMmQ1ZmRlNTUxYg==";

            DefaultAccessKey = "MjM0NDkjM2FkMmUxYjEtMjllNy00Mjk0LWEyN2YtODQ0MjljN2ExZDQ2IzNhMGIxNTA5LTA5NDAtNGRiMi1hMmQwLWMzYTkwYzI0NzA4OSM3MGU1MmRiNGEzM2Q4Mjk3Y2RkOTE5YTZlY2YwZGY3Yg==";
        /*} else {
            DefaultAccessKey = "MTU2OTQjMzRiNGIyMzgtYjc0ZS00Yzg2LWEwYzUtMWRlMmM3MmIzMjYyIzQ5Y2UxYTY3LWE4ZTgtNDY5YS1hNDJiLWYwMWE0MDgwOTIxZSNhNWFhNzJjOGNmMmVkNDJmZjczYmM4OWQzYjgwY2E2OA==";
        }*/
        SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String accessKey = sharedPreferences.getString("AccessKey", DefaultAccessKey);
        return accessKey;
    }

    public void setRecentAccessKey(Context context, String accessKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("AccessKey", accessKey);
        sharedPreferencesEditor.commit();
    }

    public List<HistoryDataItem> getHistoryDataList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String accessKeyHistory = sharedPreferences.getString("AccessKeyHistory", "");

        List<HistoryDataItem> historyDataList = new ArrayList<HistoryDataItem>();
        for (String historyItem : accessKeyHistory.split(";")) {
            String[] items = historyItem.split(":");
            if (items.length < 2) continue;

            HistoryDataItem item = new HistoryDataItem();
            item.accessKey = items[0];
            item.robotName = items[1];
            historyDataList.add(item);
        }

        return historyDataList;
    }

    public void addHistory(Context context, String accessKey, String robotName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String accessKeyHistory = sharedPreferences.getString("AccessKeyHistory", "");

        StringBuilder historyDataList = new StringBuilder();
        historyDataList.append(accessKey);
        historyDataList.append(':');
        historyDataList.append(robotName);

        for (String historyItem : accessKeyHistory.split(";")) {
            if (historyItem.length() > 0 && !historyItem.startsWith(accessKey)) {
                historyDataList.append(';');
                historyDataList.append(historyItem);
            }
        }

        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("AccessKeyHistory", historyDataList.toString());
        sharedPreferencesEditor.commit();
    }
}
