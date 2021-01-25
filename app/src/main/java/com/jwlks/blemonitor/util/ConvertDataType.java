package com.jwlks.blemonitor.util;

import java.util.Arrays;

public class ConvertDataType {

    // Single(4bit) = 1 Hex Number / Range of Hex Value : 0 ~ F / Range of Decimal Value : 0 ~ 15( 0 ~ 2^4-1 )
    // Byte(8bit) = 2 Hex Numbers  / Range of Hex Value : 0 ~ FF / Range of Decimal Value : 0 ~ 255( 0 ~ 2^8-1 )
    // Short(16bit) = 4 Hex Numbers  / Range of Hex Value : 0 ~ FFFF / Range of Decimal Value : 0 ~ 65535( 0 ~ 2^16-1 )
    // Int(32bit) = 8 Hex Numbers  / Range of Hex Value : 0 ~ FFFFFFFF / Range of Decimal Value : 0 ~ 4,294,967,295( 0 ~ 2^32-1 )


    /*
     * int to 4hex Number : Example 10 => 000A
     */

    public static String intTo4HexString(int intValue) {

        String result;

        if(intValue < 65536 && intValue > 0){
            result  = Integer.toHexString(0x10000 | intValue).substring(1).toUpperCase();
        } else {
            result = "error";
        }


        return result;
    }



    /*
     *  int 32bit 8hex or short 16bit 4hex  => byte Array
     */


    public static byte[] intToByteBuffer(int intValue) {

        byte[] buffer = new byte[4];

        buffer[0] = (byte) ((intValue & 0xff000000) >> 24);
        buffer[1] = (byte) ((intValue & 0x00ff0000) >> 16);
        buffer[2] = (byte) ((intValue & 0x0000ff00) >> 8);
        buffer[3] = (byte) ((intValue & 0x000000ff) >> 0);

        return buffer;
    }

    public static byte[] shortToByteBuffer(short addr, short data) {

        byte[] buffer = new byte[4];

        buffer[0] = (byte) ((addr & 0xff00) >> 8);
        buffer[1] = (byte) (addr & 0x00ff);
        buffer[2] = (byte) ((data & 0xff00) >> 8);
        buffer[3] = (byte) (data & 0x00ff);

        return buffer;
    }

    /*
     * byte Array => int
     */
    public static int byteArrayToInt(byte[] bytes) {
        return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
    }

    /*
     *  byte Array => String or String Array
     */
    public static String byteArrayToHexString(byte[] bytes){

        StringBuilder sb = new StringBuilder();

        for(byte b : bytes){

            sb.append(String.format("%02X", b&0xff));
        }

        return sb.toString();
    }

    public static String[] byteArrayToHexStringArray(byte[] bytes){
        int len = bytes.length;
        int stringCounter = 0;
        int byteCounter = 1;
        if(len%4 !=0){
            for(int i = 0 ; i < bytes.length ; i += 4){
                bytes = Arrays.copyOfRange(bytes, i, i+4);
            }
        }
        String[] hexStringArray = new String[len/4];
        StringBuilder sb = new StringBuilder();

        for(byte b : bytes){
            sb.append(String.format("%02X", b&0xff));
            if(byteCounter % 4 == 0) {
                hexStringArray[stringCounter] = sb.toString();
                sb =  new StringBuilder();
                stringCounter++;
            }
            byteCounter++;
        }

        return hexStringArray;
    }

    /*
     *  String or String Array => byte Array or short Array or int Array
     */
    public static byte[] hexStringToByte8bit2HexArray(String s) {
        int len = s.length();
        byte[] dataByte = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            dataByte[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return dataByte;
    }

    public static short[] hexStringToShort16bit4HexArray(String s) {
        int len = s.length();
        byte[] dataByte;

        if(len%4 !=0){
            s = s.substring(0,len-(len%4));
        }
        short[] dataShort = new short[len / 4];

        dataByte = hexStringToByte8bit2HexArray(s);

        for (int i = 0, counter = 0 ; i < dataByte.length; i += 2, counter++) {
            dataShort[counter] = (short) ( (dataByte[i] & 0xFF) << 8 | (dataByte[i+1] & 0xFF) ) ;
        }

        return dataShort;
    }

    public static int[] hexStringToInt32bit8HexArray(String s) {
        int len = s.length();
        byte[] dataByte;

        if(len%8 !=0){
            s = s.substring(0,len-(len%8));
        }

        int[] dataInt = new int[len / 8];

        dataByte = hexStringToByte8bit2HexArray(s);

        for (int i = 0, counter = 0 ; i < dataByte.length; i += 4, counter++) {
            dataInt[counter] = (int) ( (dataByte[i] & 0xFF) << 24 | (dataByte[i+1] & 0xFF) << 16 | (dataByte[i+2] & 0xFF) << 8 | (dataByte[i+3] & 0xFF) );
        }

        return dataInt;
    }

    /*
     *  String Array => byte Array or int Array
     */

    public static byte[] hexStringArrayToByte8bit2HexArray(String[] sArray){
        int len = sArray.length;
        byte[] dataByte = new byte[len * 4];

        for(int i = 0; i < sArray.length; i++) {
            for(int j = 0 ; j < sArray[0].length()/2; j++){
                dataByte[i*4 + j] = hexStringToByte8bit2HexArray(sArray[i])[3-j];
            }
        }

        return dataByte;
    }


    public static int[] hexStringArrayToInt32bit8HexArray(String[] sArray){
        int len = sArray.length;
        int dataInt[] = new int[len];

        for(int i = 0; i < sArray.length; i++) {
            dataInt[i] = hexStringToInt32bit8HexArray(sArray[i])[0];
        }

        return dataInt;
    }

    /*
     *  String List => byte Array or int Array
     */


}
