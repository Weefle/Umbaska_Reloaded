package com.mashape.unirest.http.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class MapUtil
{
  public static List<NameValuePair> getList(Map<String, List<Object>> parameters)
  {
    List<NameValuePair> result = new ArrayList();
    Iterator localIterator1;
    if (parameters != null)
    {
      TreeMap<String, List<Object>> sortedParameters = new TreeMap(parameters);
      for (localIterator1 = sortedParameters.entrySet().iterator(); localIterator1.hasNext();)
      {
        entry = (Map.Entry)localIterator1.next();
        List<Object> entryValue = (List)entry.getValue();
        if (entryValue != null) {
          for (Object cur : entryValue) {
            if (cur != null) {
              result.add(new BasicNameValuePair((String)entry.getKey(), cur.toString()));
            }
          }
        }
      }
    }
    Map.Entry<String, List<Object>> entry;
    return result;
  }
}
