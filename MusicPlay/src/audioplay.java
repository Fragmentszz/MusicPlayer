import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;
import org.jaudiotagger.audio.AudioFile;
public class audioplay{
    public AudioClip adc;// ������Ƶ��������
    Tag musicTag;
    public URL url;    //wav�ļ�·��
    public String music;
    public boolean opened = false;
    public boolean isOpened(){
        return opened;              //���ر��
    }

    public void SetPlayAudioPath(String path) throws NotWAVException{       //ѡ�񲥷��ļ�
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
            opened = true;                  //�����ɹ��ı�Ǳ���
            music = path;
        }
        catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
    }
    public void play(){  //����wav�ļ�
        adc.play();
        System.out.println(music + "������...");
    }
    public void stop(){ //ֹͣ����wav�ļ�
        adc.stop();
        System.out.println(music + "ֹͣ����");
    }
}