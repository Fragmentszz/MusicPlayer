import com.sun.deploy.net.URLEncoder;
import com.sun.imageio.plugins.jpeg.JPEG;
import javafx.scene.media.AudioClip;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
/*
 *    @Author FragmentsZZ
 *    @Time   2023/11/29 20:33
 */


public class musicPlayWindow extends JPanel {
    JButton play;
    JProgressBar probar;
    JPanel jp1;
    JLabel lyricsLabel;
    Media nowmusic;
    MediaPlayer player;
    public JLabel musicCover;
    Lyrics lyrics;
    int status;
    int maxsize = 0;
    String nowsong;
    JLabel tottimelabel,nowtimelabel;
    String lyspath;
    int height = 200,width = 700;
    public musicPlayWindow(JLabel _lyricsLabel) throws MalformedURLException, UnsupportedEncodingException {
        setSize(width,height);
        setLayout(null);
        //setLayout(new FlowLayout());
        lyricsLabel = _lyricsLabel;
        initMusic("����һ����.mp3");
        setVisible(true);
    }
    public void initMusic(String songname){

        nowsong = songname;
        if(jp1 != null){
            remove(jp1);
            if(probar != null) {
                jp1.remove(probar);
            }
        }
        boolean flag = true;
        if(player != null ) player.stop();
        else flag = false;
        jp1 = new JPanel();
        jp1.setBounds(0,0,width,height);
        jp1.setLayout(null);
        tottimelabel = new JLabel();
        nowtimelabel = new JLabel();
        musicCover = new JLabel("??");
        play = new JButton("");
        probar = new JProgressBar(0,100);
        //play.setSize(200,200);
        probar.setBounds(100 + 100 ,0,200,50);
        play.setBounds(50,0,80,50);
        nowtimelabel.setBounds(300 +100,50 / 4,50,30);
        tottimelabel.setBounds(330+100,50 / 4,50,30);




        add(jp1);
        jp1.add(play);
        jp1.add(probar);
        jp1.add(nowtimelabel);
        jp1.add(tottimelabel);



        String currentPath = System.getProperty("user.dir");
        currentPath = currentPath.replace('\\','/');
        String filepath = "file:////" + currentPath + "/music/songs/" + songname;
        int pos = songname.indexOf('.');
        String song = songname.substring(0,pos);
        System.out.println(song);
        lyspath = "./music/lyrics/" + song + ".lrc";

        // player initializetion
        String uri = filepath;
        nowmusic = new Media(uri);
        player = new MediaPlayer(nowmusic);
        lyrics = new Lyrics(lyspath);
        probar.setValue(0);

        player.setOnReady(() -> {
            Duration totalDuration = player.getTotalDuration();
            System.out.println(totalDuration);
            maxsize = (int) totalDuration.toMillis();
            probar.setMaximum(maxsize);
            int seconds = (int)totalDuration.toSeconds();
            int minutes = seconds / 60;
            seconds = seconds % 60;
            tottimelabel.setText("/" + String.format("%02d:%02d",minutes,seconds));
        });


        probar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //player.stop();
                int mouseX = e.getX();                  //��ȡ�������������λ��
                int progressBarWidth = probar.getWidth();               //�ܿ��
                int value = (int) ((double) mouseX / progressBarWidth * probar.getMaximum());       //������ñ�����Ľ���
                probar.setValue(value);
                player.seek(player.getTotalDuration().multiply(1.0*value / probar.getMaximum()));       //seek�������Ĳ������Ľ���
                lyrics = new Lyrics(lyspath);               //��ʼ��lyrics
                player.play();
            }
        });

        player.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            int seconds = (int)newValue.toSeconds();
            int misecond = (int) (newValue.toSeconds() * 100) % 100;       //double�ͣ���λΪ��
            int minutes = seconds / 60;
            seconds = seconds % 60;
            nowtimelabel.setText(String.format("%02d:%02d",minutes,seconds));           //����Label��ֵ
            lyricsLabel.setText(lyrics.getNow(String.format("%02d:%02d.%02d",minutes,seconds,misecond)));
            probar.setValue((int)newValue.toMillis());                              //����progressbar��ֵ
        });
        if(flag){
            player.play();
            play.setText("��ͣ");
            status = 0;
        }else{
            player.stop();
            play.setText("����");
            status = -1;
        }
        //��������
        play.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                double r = player.getRate();
                if(status == 1)  {
                    player.pause();
                    status = 0;
                    play.setText("����");
                }else if(status == 0){
                    status = 1;
                    player.play();
                    play.setText("��ͣ");
                }else if(status == -1)
                {
                    player.play();
                    status = 1;
                    play.setText("��ͣ");
                }
            }
        });

    }

}
