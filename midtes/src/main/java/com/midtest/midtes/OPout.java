package com.midtest.midtes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class OPout {

    public String socolour ;
    public String size;
    public String desc;

    public String getSocolour() {
        return socolour;
    }

    public void setSocolour(String socolour) {
        this.socolour = socolour;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getboxinfo(String socolour,String size, String desc){

        return socolour+size+desc;
    }
    public String newsome(String reads){
        Date now = new Date();
        String ss = "";
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        //String nowtime = dateFormat.format( now );
        //reads = reads +"\r\n";
        //reads = reads +nowtime;

        CallClentTask callclenttask = new CallClentTask(reads);
        FutureTask futuretask = new FutureTask(callclenttask);
        new Thread(futuretask).start();
       // do{

        //}while (futuretask.isDone());

        try{
            Object ssie = futuretask.get() ;
            ss =  (String) ssie;
            //reads += ss;
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        //System.out.println(hehe);
        return  ss;
    }

}
