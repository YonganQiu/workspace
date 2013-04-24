package me.elvishew.cordovajqmdemo;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.widget.Toast;

public class TestPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args,
            CallbackContext callbackContext) throws JSONException {
        if ("toast".equals(action)) {
            String message = args.getString(0);
            Toast.makeText(cordova.getActivity(), message, Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
