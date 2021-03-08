package test;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * 目前可以在python中随意调用Java里的类了，非常方便。
 */
public class JavaBean {
    private String name;
    private List<String> data;

    public JavaBean(String n){
        this.name = n;
        data = new ArrayList<String>();
    }
    public JavaBean(JavaBean bean){
        name=bean.name;
        data=bean.data;
    }

    public void setData(String el){
        this.data.add(el);
    }

    public void print(){
        for (String it: data) {
            Log.d("Java Bean - "+this.name,it);
        }
    }
    public List<Sample> getSamples(){
        List<Sample> ret=new ArrayList<Sample>();
        for (int i=0;i<2;i++){
            ret.add(new Sample());
        }
        return ret;
    }
}
