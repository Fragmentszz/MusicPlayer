import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/4 11:25
 */
public class Lyrics {
    String lyricsString;
    String lyricsRow [];
    String lyrics [][];
    int now;
    public Lyrics(String filepath){
        File file = new File(filepath);
        FileInputStream fileInputStream ;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader = null;
        try{
            file = new File(filepath);
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream,"gbk");
            bufferedReader = new BufferedReader(inputStreamReader);
        }catch (Exception e){
            e.printStackTrace();
        }
        lyricsString = "";
        if(!file.exists()){
            lyricsString = "[00:00.00]没有找到歌词~~";
        }else{
            String now;
            try{
                while((now = bufferedReader.readLine()) != null)
                {
                    lyricsString = lyricsString + now;
                }
            }catch (Exception e){
                lyricsString = "[00:00.00]歌词有错误!~~显示不出来！";
                e.printStackTrace();
            }

            System.out.println(lyricsString);
            int start = lyricsString.indexOf("[00:00.00]");
            if(start == -1){
                start = lyricsString.indexOf("[00:00:00]");
            }
            if(start != -1){
                lyricsString = lyricsString.substring(start);
            }else{
                lyricsString = "[00:00.00]歌词有错误!~~显示不出来！";
            }
        }
        now = 1;
        //解析歌词内容和歌词时间：
        lyricsRow = lyricsString.split("\\[");
        lyrics = new String[lyricsRow.length][2];
        for (int i = 0; i < lyricsRow.length; i++) {
            String[] start = lyricsRow[i].split("]");
            lyrics[i][0] = start[0];
            if(start.length == 1) {
                lyrics[i][1] = "...";
            }else{
                lyrics[i][1] = start[1];
            }
        }
    }
    //得到当前歌词，上一句和下一句
    String getNow(String nowtime){
        if(now < lyrics.length-1){
            while(now < lyrics.length-1 && lyrics[now + 1][0].compareTo(nowtime) < 0){
                now = now + 1;
            }
        }
        String result = "<html>";
        if(now - 1 >= 0){
            result += "<font size=\"6\">" + lyrics[now-1][1] + "</font><br>";
        }
        result += "<font color=\"red\" size=\"9\">" + lyrics[now][1] + "</font><br>";
        if(now + 1 < lyrics.length){
            result += "<font size=\"6\">"  + lyrics[now + 1][1] + "</font><br>";
        }else{
            result += "<br>";
        }
        result += "</html>";
        //System.out.println(result);
        return result;
    }
}
