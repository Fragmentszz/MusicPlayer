import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;
import org.jaudiotagger.audio.AudioFile;
public class audioplay{
    public AudioClip adc;// 声音音频剪辑对象
    Tag musicTag;
    public URL url;    //wav文件路径
    public String music;
    public boolean opened = false;
    public boolean isOpened(){
        return opened;              //返回标记
    }

    public void SetPlayAudioPath(String path) throws NotWAVException{       //选择播放文件
        try{
            if(!path.endsWith(".flac") || !path.endsWith(".mp3")){
                throw new NotWAVException();
            }
            url = new URL("file:"+path);
            if(path.endsWith(".mp3")) {
                musicTag = TagInfoUtil.Mp3InfoRead(path);
            }else if(path.endsWith(".flac")){
                musicTag = TagInfoUtil.FlacInfoRead(path);
            }
            adc = Applet.newAudioClip(url);
            opened = true;                  //读出成功的标记变量
            music = path;
        }
        catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
    }
    public void play(){  //播放wav文件
        adc.play();
        System.out.println(music + "播放中...");
    }
    public void stop(){ //停止播放wav文件
        adc.stop();
        System.out.println(music + "停止播放");
    }
}