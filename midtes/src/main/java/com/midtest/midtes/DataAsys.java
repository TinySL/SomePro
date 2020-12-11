package com.midtest.midtes;

public class DataAsys {
    public static byte[] str2bytearray(String str){
        int length=str.length();
        int arrlength=length>>1;
        if ((length & 1)==1){
            arrlength++;
        }
        byte[] ret=new byte[arrlength];
        int i=0,j=0;
        char ch0,ch1;
        if ((length & 1)==1){
            ch1=str.charAt(i++);
            if (ch1<='9' && ch1>='0'){
                ch1-='0';
            }else if(ch1>='A' && ch1<='F'){
                ch1-=('A'-10);
            }
            ret[j++]=(byte)ch1;
        }
        for(;i<length;i+=2,j++){
            ch0=str.charAt(i);
            ch1=str.charAt(i+1);
            if (ch0<='9' && ch0>='0'){
                ch0-='0';
            }else if(ch0>='A' && ch0<='F'){
                ch0-=('A'-10);
            }
            if (ch1<='9' && ch1>='0'){
                ch1-='0';
            }else if(ch1>='A' && ch1<='F'){
                ch1-=('A'-10);
            }
            ret[j]=(byte)((ch0<<4)|ch1);
        }
        return ret;
    }

    public  static String ASCIIToConvert(byte[] buff){

        String value = "";
        StringBuilder sbs =new StringBuilder();
        //StringBuffer sbu = new StringBuffer();
        //String[] chars = value.split("  ");
        for (int i = 0; i < buff.length; i++) {
            value +=buff[i];
            // sbs.append()
        }


        return  value;

    }

    // byte[]转十六进制String
    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null){
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    //十六进制String转byte[]
    public static byte[] hexStrToByteArray(String str)
    {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++){
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte)Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }
}
