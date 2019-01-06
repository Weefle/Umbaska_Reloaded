package com.mashape.unirest.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonNode
{
  private JSONObject jsonObject;
  private JSONArray jsonArray;
  private boolean array;
  
  public JsonNode(String json)
  {
    if ((json == null) || ("".equals(json.trim()))) {
      this.jsonObject = new JSONObject();
    } else {
      try
      {
        this.jsonObject = new JSONObject(json);
      }
      catch (JSONException e)
      {
        try
        {
          this.jsonArray = new JSONArray(json);
          this.array = true;
        }
        catch (JSONException e1)
        {
          throw new RuntimeException(e1);
        }
      }
    }
  }
  
  public JSONObject getObject()
  {
    return this.jsonObject;
  }
  
  public JSONArray getArray()
  {
    JSONArray result = this.jsonArray;
    if (!this.array)
    {
      result = new JSONArray();
      result.put(this.jsonObject);
    }
    return result;
  }
  
  public boolean isArray()
  {
    return this.array;
  }
  
  public String toString()
  {
    if (isArray())
    {
      if (this.jsonArray == null) {
        return null;
      }
      return this.jsonArray.toString();
    }
    if (this.jsonObject == null) {
      return null;
    }
    return this.jsonObject.toString();
  }
}
