package com.beans.utils;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

public class ReportUtils extends JRDefaultScriptlet{
    private String TH_BATH = "บาท";
    private String TH_0 = "ศูนย์";
    private String TH_STANG = "สตางค์";
    private String TH_LIMITED = "ถ้วน";
    private String TH_1 = "หนึ่ง";
    private String TH_2 = "สอง";
    private String TH_3 = "สาม";
    private String TH_4 = "สี่";
    private String TH_5 = "ห้า";
    private String TH_6 = "หก";
    private String TH_7 = "เจ็ด";
    private String TH_8 = "แปด";
    private String TH_9 = "เก้า";
    private String TH_10 = "สิบ";
    private String TH_100 = "ร้อย";
    private String TH_1000 = "พัน";
    private String TH_10000 = "หมื่น";
    private String TH_100000 = "แสน";
    private String TH_1000000 = "ล้าน";
    private String TH_MINUS = "ลบ";
    private String TH_ONE = "หนึ่ง";
    private String TH_ED = "เอ็ด";
    private String TH_YEE = "ยี่";
    
    public ReportUtils() {}
    public String toThaiMoney(String num) throws JRScriptletException {
        String ret = "";
        String bath = "", rest = "";
        int pointPos = num.length();
        for (int i=0; i<num.length(); i++ ) {
            if (num.substring(i, i+1).equals(".")) { pointPos = i; }
        }
        bath = num.substring(0, pointPos);
        if (Integer.parseInt(bath) == 0) { bath = TH_0; }
        else if (bath.length() > 0) {
            bath = toThaiWord(bath);
        }
        
        if (pointPos != num.length()) {
            rest = num.substring(pointPos + 1, num.length());
            if (rest.length() > 0) {            
                if (rest.length() == 1) { rest = rest + "0"; }
                rest = toThaiWord(rest);
            } 
        }
        
        if (!bath.equals("")) { bath = bath + TH_BATH; }
        if (!rest.equals("")) { rest = rest + TH_STANG; } else { rest = TH_LIMITED; }
        ret = bath + rest;
        return ret;
    }
    
    private String toThaiWord(String num) {
        String ret = "";
        String sign = "";
        List number = new ArrayList();
        number.add("");
        number.add(TH_1);         number.add(TH_2);        number.add(TH_3);
        number.add(TH_4);        number.add(TH_5);        number.add(TH_6);
        number.add(TH_7);        number.add(TH_8);        number.add(TH_9);      
        
        List unit = new ArrayList();
        unit.add("");        
        unit.add(TH_10);         unit.add(TH_100);        unit.add(TH_1000);
        unit.add(TH_10000);        unit.add(TH_100000);        unit.add(TH_1000000);

        if (num.substring(0,1).equals("-")) {
            sign = TH_MINUS;
            num = num.substring(1, num.length());
        }
        
        String revStr = "";
        for (int i=0; i<num.length(); i++ ) {
            revStr = num.charAt(i) + revStr;
        }
        
        for (int i=0; i<revStr.length(); i++) {
            int numberPos = i % 6;
            int unitPos = i % 7;
            String nnum = "", uunit = "";
            if (i > 6) { unitPos = unitPos + 1; }
            nnum = "" + number.get(Integer.parseInt(revStr.substring(i, i+1)));
            
            if ((numberPos == 0) && (revStr.substring(i, i+1).equals("1")) && (i == revStr.length()-1)) {
                nnum = TH_ONE; 
            } else if ((numberPos == 0) && (revStr.substring(i, i+1).equals("1"))) {
                nnum = TH_ED;
            } else if ((numberPos == 1) && (revStr.substring(i, i+1).equals("1"))) {
                nnum = "";
            } else if ((numberPos == 1) && (revStr.substring(i, i+1).equals("2"))) {
                nnum = TH_YEE;
            } 

            if (!revStr.substring(i, i+1).equals("0")) { uunit = "" + unit.get(unitPos); }
            if (unitPos == 6) { uunit = "" + unit.get(unitPos); }
            ret = nnum + uunit + ret;
        }
        ret = sign + ret;
        return ret;
    }
}