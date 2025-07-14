package com.example.phoneinfogetter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class RootUtil {
    public static boolean isDeviceRooted(Context context){

        return checkForKnownRootFiles() || checkForSUBinary() || checkForBusyBox() || checkForMagisk(context) || isSystemIntegrityCompromised() || checkRootManagementApps(context);
    }

    private static boolean checkForKnownRootFiles(){
        String[] paths=new String[]{
                "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su",
                "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su",
                "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su"
        };
        for(String path:paths){
            File file=new File(path);
            if(file.exists()){
                return true;
            }
        }
        return false;

    }

    private static boolean checkForSUBinary(){
        return checkCommand("which su");
    }
    private static boolean checkForBusyBox(){
        return checkCommand("which busybox");
    }

    private static boolean checkCommand(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return reader.readLine() != null;
        } catch (Exception e) {
            return false;
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }
    private static boolean checkRootManagementApps(Context context) {
        String[] rootApps = new String[]{
                "com.noshufou.android.su",
                "com.thirdparty.superuser",
                "eu.chainfire.supersu",
                "com.topjohnwu.magisk"
        };

        for (String app : rootApps) {
            if (packageExists(context, app)) {
                return true;
            }
        }
        return false;
    }

    private static boolean packageExists(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private static boolean isSystemIntegrityCompromised(){
        var buildTags=Build.TAGS;
        return buildTags!=null && buildTags.contains("test-keys");

    }
    private static boolean checkForMagisk(Context context){
        return new File("/sbin/.magisk").exists() ||
                new File("/sbin/.core/mirror").exists() ||
                packageExists(context, "com.topjohnwu.magisk");
    }
}
