import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/6 10:09
 */

class DoubleClickListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            JButton button = (JButton) e.getSource();
            String songName = button.getText();
            // 在这里处理播放歌曲的逻辑
            System.out.println("播放歌曲：" + songName);
        }
    }
}

public class ListsWindow extends JPanel {
    ArrayList<JButton> buttons;
    ArrayList<String> musicList;
    ArrayList<String> time;
    ArrayList<String> minfo;
    public ArrayList<Tag> musicTags;
    public musicPlayWindow mpW;

    public void initList(String songpath) {
        File folder = new File(songpath);
        File[] files = folder.listFiles();
        musicList = new ArrayList<String>();
        time = new ArrayList<String>();
        minfo = new ArrayList<String>();
        musicTags = new ArrayList<Tag>();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith("mp3") || file.getName().endsWith("wav")) {         //找出所有文件

                    String nowname = file.getName();
                    int pos = file.getName().indexOf(" (V0)");
                    if (pos != -1) {
                        String newname = nowname.substring(0, pos) + ".mp3";
                        File newFile = new File(file.getParent(), newname);
                        boolean renamed = file.renameTo(newFile);
                        nowname = newname;
                    }
                    musicList.add(nowname);
                    if(nowname.endsWith("mp3")) {
                        try{
                            Tag tag = TagInfoUtil.Mp3InfoRead(songpath + "\\" + file.getName());
                            musicTags.add(tag);
                        }catch (Exception e){
                            musicTags.add(null);
                        }

                    }else{
                        musicTags.add(null);
                    }

                }
            }
        }
    }

    public ListsWindow(String songpath,musicPlayWindow _mpW) throws InterruptedException {
        initList(songpath);

        mpW = _mpW;
        buttons = new ArrayList<JButton>();
        setLayout(new FlowLayout());
        for (int i = 0; i < musicList.size(); i++) {
            String nowinfo = "";
            int pos = musicList.get(i).indexOf(".");
            String musicname = musicList.get(i);
            if(pos != -1) {
                musicname = musicname.substring(0, pos);
            }
            nowinfo = musicname;
            JButton button = new JButton(musicList.get(i));
            button.setName(musicList.get(i));
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        JButton button = (JButton) e.getSource();
                        String songName = button.getText();
                        mpW.initMusic(button.getName());            //切换歌曲
                    }
                }
            });

            if(musicTags.get(i) != null){
                String str = musicTags.get(i).getArtist();
                String result = str.trim().replaceAll("^\"|\"$", "");
                nowinfo += "--艺术家:" + result;

            }else{
                nowinfo += "--艺术家:未知";
            }
            String currentPath = System.getProperty("user.dir");
            currentPath = currentPath.replace('\\','/');
            String filepath = "file:////" + currentPath + "/music/songs/" + musicList.get(i);
            String uri = filepath;
            Media nowmusic = new Media(uri);
            buttons.add(button);

            MediaPlayer player = new MediaPlayer(nowmusic);
            String finalNowinfo = nowinfo;
            int finalI = i;
            player.setOnReady(() -> {
                Duration totalDuration = player.getTotalDuration();
                System.out.println(totalDuration);
                int seconds = (int) totalDuration.toSeconds();
                int minutes = seconds / 60;
                seconds = seconds % 60;
                buttons.get(finalI).setText(finalNowinfo + ("/时长:" + String.format("%02d:%02d",minutes,seconds)));
            });
            button.setText(nowinfo);
            add(button);
        }
    }
}
