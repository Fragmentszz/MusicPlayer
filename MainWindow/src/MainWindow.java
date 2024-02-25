import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/11/29 20:18
 */
public class MainWindow extends JFrame {
    musicPlayWindow mpW;
    ListsWindow musicList;
    JPanel jp1;
    JLabel lyricsLabel;

    int width,height;
    ImageIcon background,background2,b2;
    JLabel backgoundLabel,gif;

    void setBackground(String filename){
        background = new ImageIcon(filename);
        backgoundLabel.setIcon(background);
        backgoundLabel.setBounds(1000,0,background.getIconWidth(),background.getIconHeight());
    }

    void setBackground2(String filename){
        background2 = new ImageIcon(filename);
        backgoundLabel.setIcon(background2);
        backgoundLabel.setBounds(1000,0,background2.getIconWidth(),background2.getIconHeight());
    }
    void setGif(String filename){
        b2= new ImageIcon(filename);                    //获取Image对象
        gif.setIcon(b2);                                //直接设置
        gif.setBounds(0,0,b2.getIconWidth(),b2.getIconHeight());        //设置位置[自由布局]
    }
    public MainWindow() throws MalformedURLException, UnsupportedEncodingException, InterruptedException {
        setTitle("MusicPlayer");
        width = 1920;
        height = 1000;
        setSize(width,height);
        backgoundLabel = new JLabel();
        gif = new JLabel();
        add(gif);
        add(backgoundLabel);
        backgoundLabel.setBounds(0,0,width,height);

        lyricsLabel = new JLabel("Fragments的音乐播放器");
        add(lyricsLabel);
        lyricsLabel.setBounds(1200,100,500,300);




        Button bt = new Button("hhh");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp1 = new TestLayer();
        mpW = new musicPlayWindow(lyricsLabel);

        musicList = new ListsWindow("./music/songs",mpW);
        add(musicList);
        musicList.setBounds(0,300,300,300);

        setBackground("./imgs/m_emt3.png");
        setGif("./imgs/emt.gif");
        setTitle("Music Player");
        Container c = getContentPane();
        JLabel label1 = new JLabel("Label1");
        c.setLayout(null);


//
        mpW.setLocation(width / 2 -200 ,height -100);
        add(mpW);

        setVisible(true);

    }
}
