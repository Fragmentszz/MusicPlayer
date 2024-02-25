import java.io.File;
import java.util.ArrayList;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/6 9:56
 */
public class MusicList {
    public ArrayList<String>   musicList;

    public MusicList(String songpath){
        File folder = new File(songpath);
        File[] files = folder.listFiles();
        musicList = new ArrayList<String>();
        if (files != null) {
            for (File file : files) {
                if(file.getName().endsWith("mp3") || file.getName().endsWith("wav")){
                    String nowname = file.getName();
                    int pos = file.getName().indexOf(" (V0)");
                    if(pos != -1){
                        String newname = nowname.substring(0,pos) + ".mp3";
                        File newFile = new File(file.getParent(), newname);
                        boolean renamed = file.renameTo(newFile);
                        nowname = newname;
                    }
                    musicList.add(nowname);
                }
            }
        }
        for(String music:musicList){
            System.out.println(music);
        }
    }


}
