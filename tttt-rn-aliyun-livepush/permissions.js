
import {PermissionsAndroid, ToastAndroid} from "react-native";

export default async function requestPermissions() {
    try {
        const permissions = [
            PermissionsAndroid.PERMISSIONS.RECORD_AUDIO,
            PermissionsAndroid.PERMISSIONS.CAMERA,
            PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
            PermissionsAndroid.PERMISSIONS.READ_PHONE_STATE,
            PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE,
        ];
        //返回得是对象类型
        const granteds = await PermissionsAndroid.requestMultiple(permissions);
        var data = "是否同意录音权限: ";
        if (granteds["android.permission.RECORD_AUDIO"] === "granted") {
            data = data + "是\n";
        } else {
            data = data + "否\n";
        }
        data = data+"是否同意相机权限: ";
        if (granteds["android.permission.CAMERA"] === "granted") {
            data = data + "是\n"
        } else {
            data = data + "否\n"
        }
        data = data+"是否同意手机信息权限: ";
        if (granteds["android.permission.READ_PHONE_STATE"] === "granted") {
            data = data + "是\n"
        } else {
            data = data + "否\n"
        }
        data = data+"是否同意存储权限: "
        if (granteds["android.permission.WRITE_EXTERNAL_STORAGE"] === "granted") {
            data = data + "是\n"
        } else {
            data = data + "否\n"
        }
        if (granteds["android.permission.READ_EXTERNAL_STORAGE"] === "granted") {
            data = data + "是\n"
        } else {
            data = data + "否\n"
        }
        ToastAndroid.show(data, ToastAndroid.SHORT);
    } catch (err) {
        ToastAndroid.show(err.toString(), ToastAndroid.SHORT);
    }
}
