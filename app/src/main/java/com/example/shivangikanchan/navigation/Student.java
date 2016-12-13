package com.example.shivangikanchan.navigation;

/**
 * Created by Shivangi Kanchan on 16-10-2016.
 */


public class Student {
    String name,y,b,em,re,hid,pass;
    int e,c;

   public Student(){
       e=0;
       pass=null;
   }
    public Student(String name,int e, String y, String b, String em,int c,String pass,String re) {
        this.name = name;
        this.e = e;
        this.y = y;
        this.b = b;
        this.em = em;
        this.c = c;
        this.re = re;
        //this.hid = hid;
        this.pass = pass;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getEm() {
        return em;
    }

    public void setEm(String em) {
        this.em = em;
    }

    public String getRe() {
        return re;
    }

    public void setRe(String re) {
        this.re = re;
    }

    /*public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }*/

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public Student(int e) {
        this.e = e;
        //this.pass=pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
