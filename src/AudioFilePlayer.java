import javax.sound.sampled.*;
import java.io.File;

public class AudioFilePlayer {

    public static void main(String[] args) {
        final AudioFilePlayer player = new AudioFilePlayer();
//        player.play("F:\\网易云\\ENA☆ - For you~月の光が降り注ぐテラス.ape");
//        player.play("F:\\网易云\\“千と千寻の神隠し”~いつも何度でも - 久石譲.mp3");
        player.play("./music/songs/融霜.flac");
    }

    public void play(String filePath) {
        try {
            // 文件流
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            // 文件编码
            AudioFormat audioFormat = audioInputStream.getFormat();
            // 转换文件编码
            if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                System.out.println(audioFormat.getEncoding());
                audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat.getSampleRate(), 16, audioFormat.getChannels(), audioFormat.getChannels() * 2, audioFormat.getSampleRate(), false);
                // 将数据流也转换成指定编码
                audioInputStream = AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
            }



            // 打开输出设备
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
            // 使数据行得到一个播放设备
            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            // 将数据行用指定的编码打开
            sourceDataLine.open(audioFormat);
            // 使数据行得到数据时就开始播放
            sourceDataLine.start();

            int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
            // 将流数据逐渐写入数据行,边写边播
            int numBytes = 1024 * bytesPerFrame;
            byte[] audioBytes = new byte[numBytes];
            while (audioInputStream.read(audioBytes) != -1) {
                sourceDataLine.write(audioBytes, 0, audioBytes.length);
            }
            sourceDataLine.drain();
            sourceDataLine.stop();
            sourceDataLine.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}