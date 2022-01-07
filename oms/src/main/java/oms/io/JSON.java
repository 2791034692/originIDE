package oms.io;

import org.json.JSONException;
import org.json.JSONObject;


public class JSON {
    JSONObject jsonObject;
    public JSON(){
        this.jsonObject = new JSONObject();
    }
    JSON(JSONObject object){
        this.jsonObject = object;
    }

    public JSON(String text){
        try {
            this.jsonObject = new JSONObject(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void put(String key,int value){
        try {
            jsonObject.putOpt(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void put(String key,double value){
        try {
            jsonObject.putOpt(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void put(String key,boolean value){
        try {
            jsonObject.putOpt(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void put(String key,String value){
        try {
            jsonObject.putOpt(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void put(String key, JSON json){
        try {
            jsonObject.putOpt(key,json.jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getString(String text){
        try {
            return (String) jsonObject.get(text);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean getBoolean(String b){
        try {
            return (boolean) this.jsonObject.get(b);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public JSON getJson(String text){
        try {
            return new JSON((JSONObject) this.jsonObject.get(text));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getJsonArray(String text){
        try {
            return new JSONArray((org.json.JSONArray) this.jsonObject.get(text));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getDouble(String text){
        try {
            return (double)jsonObject.get(text);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0.0;
        }
    }


    public int getInt(String text){
        try {
            return (int)jsonObject.get(text);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }
}
