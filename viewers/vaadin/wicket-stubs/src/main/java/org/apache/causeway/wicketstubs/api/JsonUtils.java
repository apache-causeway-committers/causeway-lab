package org.apache.causeway.wicketstubs.api;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;

public final class JsonUtils {
    private JsonUtils() {
    }

    public static JSONArray asArray(Map<String, Object> map) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        if (map != null) {
            Iterator var2 = map.entrySet().iterator();

            while (true) {
                while (var2.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry) var2.next();
                    String name = (String) entry.getKey();
                    Object value = entry.getValue();
                    if (value instanceof List) {
                        List<?> values = (List) value;
                        Iterator var14 = values.iterator();

                        while (var14.hasNext()) {
                            Object v = var14.next();
                            if (v != null) {
                                JSONObject object = new JSONObject();
                                object.put("name", name);
                                object.put("value", v);
                                jsonArray.put(object);
                            }
                        }
                    } else if (value != null) {
                        if (value.getClass().isArray()) {
                            Object[] array = (Object[]) value;
                            Object[] var7 = array;
                            int var8 = array.length;

                            for (int var9 = 0; var9 < var8; ++var9) {
                                Object v = var7[var9];
                                if (v != null) {
                                    JSONObject object = new JSONObject();
                                    object.put("name", name);
                                    object.put("value", v);
                                    jsonArray.put(object);
                                }
                            }
                        } else {
                            JSONObject object = new JSONObject();
                            object.put("name", name);
                            object.put("value", value);
                            jsonArray.put(object);
                        }
                    }
                }

                return jsonArray;
            }
        } else {
            return jsonArray;
        }
    }
}

