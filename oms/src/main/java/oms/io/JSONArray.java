package oms.io;

import org.json.JSONException;


public class JSONArray {
    org.json.JSONArray jsonArray;
    public JSONArray(){
        this.jsonArray = new org.json.JSONArray();
    }
    public JSONArray(String text){
        try {
            this.jsonArray = new org.json.JSONArray(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    JSONArray(org.json.JSONArray array){
        this.jsonArray = array;
    }
    public int size(){
        return jsonArray.length();
    }
    public void put(int point,String s){
        try {
            jsonArray.put(point,s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void put(int point,int s){
        try {
            jsonArray.put(point,s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void put(int point,boolean s){
        try {
            jsonArray.put(point,s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void put(int point,double s){
        try {
            jsonArray.put(point,s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void put(int point, JSON s){
        try {
            jsonArray.put(point,s.jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void put(int point, JSONArray s){
        try {
            jsonArray.put(point,s.jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void put(String s){
        jsonArray.put(s);
    }
    public void put(double s){
        try {
            jsonArray.put(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void put(int s){
        jsonArray.put(s);
    }
    public void put(boolean s){
        jsonArray.put(s);
    }
    public void put(JSON json){
        jsonArray.put(json.jsonObject);
    }
    public void put(JSONArray array){
        jsonArray.put(array.jsonArray);
    }


    public JSON getJson(int text){
        try {
            return new JSON(this.jsonArray.getJSONObject(text));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getJsonArray(int text){
        try {
            return new JSONArray(this.jsonArray.getJSONArray(text));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getDouble(int t){
        try {
            return jsonArray.getDouble(t);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

     public int getInt(int t){
         try {
             return jsonArray.getInt(t);
         } catch (JSONException e) {
             e.printStackTrace();
             return 0;
         }
    }

    public String getString(int t){
        try {
            return jsonArray.getString(t);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }




}
