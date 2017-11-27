package com.alin.hourse.mvplibrary.cache;

import android.os.Parcel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @创建者 hailin
 * @创建时间 2017/11/22 11:58
 * @描述 ${对象序列化、反序列化}.
 */

public class ParceFn {

    /**
     *  反序列化
     * @param
     */
    public static <T> T unMarshell(byte[] bytes){
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes,0,bytes.length);
        parcel.setDataPosition(0);
        Object value = parcel.readValue(ParceFn.class.getClassLoader());
        return (T) value;
    }

    /**
     *  序列化
     * @param object
     */
    public static byte[] marshell(Object object) {
        Parcel parcel = Parcel.obtain();
        parcel.writeValue(object);
        byte[] marshall = parcel.marshall();
        parcel.recycle();
        return marshall;
    }

    public static void a(Object object){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Bundle.txt"));
            oos.writeObject(object);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T b(){
        ObjectInputStream ois = null;
        Object object = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("Bundle.txt"));
            object = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return (T) object;
    }
}
