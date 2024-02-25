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
        String fileguide = "请输入文件名:";
        String filepath = "";
        File music = new File("");
        System.out.println(fileguide);
        do{
            filepath = sc.nextLine();
            try {
                music = new File(filepath);             //生成一个新的File类对象
                if (music.exists()) {
                    audio.SetPlayAudioPath(filepath);
                    System.out.println(filepath + "读取成功!");
                }
                else{
                    System.out.println("文件不存在！请重新输入文件名!");
                }
            }
            catch(Exception e){
                //System.out.println(e.toString());       //打印具体出错信息
                e.printStackTrace();                    //打印错误调用堆栈
            }
        }while(!audio.isOpened());              //音频成功读入就停止
        String op = "";
        do{
            System.out.println("===========菜单===========");
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
                System.out.println("无效指令!");
                continue;
            }
        }while(true);
        System.out.println("退出咯...");
    }
}
