import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class filemusic{
    public static void main(String[] args) {
        audioplay audio =new audioplay();
        Scanner sc = new Scanner(System.in);
        String fileguide = "�������ļ���:";
        String filepath = "";
        File music = new File("");
        System.out.println(fileguide);
        do{
            filepath = sc.nextLine();
            try {
                music = new File(filepath);             //����һ���µ�File�����
                if (music.exists()) {
                    audio.SetPlayAudioPath(filepath);
                    System.out.println(filepath + "��ȡ�ɹ�!");
                }
                else{
                    System.out.println("�ļ������ڣ������������ļ���!");
                }
            }
            catch(Exception e){
                //System.out.println(e.toString());       //��ӡ���������Ϣ
                e.printStackTrace();                    //��ӡ������ö�ջ
            }
        }while(!audio.isOpened());              //��Ƶ�ɹ������ֹͣ
        String op = "";
        do{
            System.out.println("===========�˵�===========");
            System.out.printf("1.play\n2.stop\n3.exit\n");
            System.out.println("=========================");
            op = sc.nextLine();
            if(op.equals("play")){
                System.out.println("option:" + op);
                audio.play();
            }else if(op.equals("stop")) {
                System.out.println("option:" + op);
                audio.stop();
            }else if(op.equals("exit")) {
                System.out.println("option:" + op);
                audio.stop();
                break;
            }
            else {
                System.out.println("��Чָ��!");
                continue;
            }
        }while(true);
        System.out.println("�˳���...");
    }
}
