package io.invertase.firebase.fabric.crashlytics;

import android.util.Log;

import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import com.crashlytics.android.Crashlytics;


public class RNFirebaseCrashlytics extends ReactContextBaseJavaModule {

  private static final String TAG = "RNFirebaseCrashlytics";

  public RNFirebaseCrashlytics(ReactApplicationContext reactContext) {
    super(reactContext);
    Log.d(TAG, "New instance");
  }

  @Override
  public String getName() {
    return TAG;
  }

  @ReactMethod
  public void crash() {
    Crashlytics.getInstance().crash();
  }

  @ReactMethod
  public void log(final String message) {
    Crashlytics.log(message);
  }

  @ReactMethod
  public void recordError(final int code, final String domain) {
    Crashlytics.logException(new Exception(code + ": " + domain));
  }

  @ReactMethod
  public void setBoolValue(final String key, final boolean boolValue) {
    Crashlytics.setBool(key, boolValue);
  }

  @ReactMethod
  public void setFloatValue(final String key, final float floatValue) {
    Crashlytics.setFloat(key, floatValue);
  }

  @ReactMethod
  public void setIntValue(final String key, final int intValue) {
    Crashlytics.setInt(key, intValue);
  }

  @ReactMethod
  public void setStringValue(final String key, final String stringValue) {
    Crashlytics.setString(key, stringValue);
  }

  @ReactMethod
  public void setUserIdentifier(String userId) {
    Crashlytics.setUserIdentifier(userId);
  }

  @ReactMethod
  public void recordCustomExceptionName(String name, String reason, ReadableArray frameArray) {
    StackTraceElement[] stackTrace = new StackTraceElement[frameArray.size()];
    for (int i = 0; i < frameArray.size(); i++) {
      ReadableMap map = frameArray.getMap(i);
      String functionName = map.hasKey("functionName") ? map.getString("functionName") : "Unknown Function";
      StackTraceElement stack = new StackTraceElement(
        "",
        functionName,
        map.getString("fileName"),
        map.hasKey("lineNumber") ? map.getInt("lineNumber") : -1
      );
      stackTrace[i] = stack;
    }
    Exception e = new Exception(name + "\n" + reason);
    e.setStackTrace(stackTrace);
    Crashlytics.logException(e);
  }
}
