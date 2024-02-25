

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import javafx.scene.media.MediaPlayer;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.flac.FlacFileReader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.jaudiotagger.tag.images.Artwork;
/**
 * @date 2017��3��16�� ����11:47:16
 * @version 1.0
 * Ŀǰ����֧��MP3��flac��������ʽ�д�����
 */
public class TagInfoUtil {


    public static Tag Mp3InfoRead(String path){
        MP3File file;
        try {
            file = new MP3File(path);

            String songName=file.getID3v2Tag().frameMap.get("TIT2").toString();
            String artist=file.getID3v2Tag().frameMap.get("TPE1").toString();
            String album=file.getID3v2Tag().frameMap.get("TALB").toString();
            String length=file.getMP3AudioHeader().getTrackLengthAsString();
            songName=songName.substring(6, songName.length()-3);
            artist=artist.substring(6, artist.length()-3);
            album=album.substring(6, album.length()-3);

            Tag tag = new Tag();
            tag.setSongName(songName);
            tag.setAlbum(album);
            tag.setArtist(artist);
            tag.setLength(length);
            return tag;
        } catch (IOException | TagException | ReadOnlyFileException | CannotReadException
                 | InvalidAudioFrameException e) {
            e.printStackTrace();
            throw new RuntimeException("��ȡMp3 tag��Ϣ����");

        }

//          System.out.println("����"+songName);
//          System.out.println("����"+singer);
//          System.out.println("ר��:"+author);
    }

    public static Image getMp3Picture(String mp3path){
        Image img;
        try {
            String url = mp3path;
            File sourceFile = new File(url);
            MP3File mp3file = new MP3File(sourceFile);

            AbstractID3v2Tag tag = mp3file.getID3v2Tag();
            AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");
            FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
            byte[] imageData = body.getImageData();
            img = Toolkit.getDefaultToolkit().createImage(imageData, 0,imageData.length);
            ImageIcon icon = new ImageIcon(img);
            String storePath=mp3path;
            storePath = storePath.substring(0, storePath.length()-3);
            storePath+="jpg";
            FileOutputStream fos = new FileOutputStream(storePath);
            fos.write(imageData);
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("��ȡMp3ͼƬ����");
        }
        return img;
    }

    public static Tag FlacInfoRead(String path){
        try {
            FlacFileReader fileReader=new FlacFileReader();

            AudioFile read = fileReader.read(new File(path));
            org.jaudiotagger.tag.Tag tag = read.getTag();
            String songName = tag.getFirst(FieldKey.TITLE);
            String artist = tag.getFirst(FieldKey.ARTIST);
            String lyrics = tag.getFirst(FieldKey.LYRICIST);
            System.out.println(tag.getAll(FieldKey.LYRICS));
            String album=tag.getFirst(FieldKey.ALBUM);

            int trackLength = read.getAudioHeader().getTrackLength();
            int min=trackLength/60;
            int second=trackLength%60;
            String length=min+":"+second;
            System.out.println("����:"+length);
            Tag tag2 = new Tag();
            tag2.setSongName(songName);
            tag2.setArtist(artist);
            tag2.setAlbum(album);
            return tag2;
//          System.out.println(songName);
//          System.out.println(artist);
//          System.out.println(album);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("��ȡFlac��Ϣʱ����");
        }
    }

    public static Image getFlacPicture(String flacpath){
        try {
            FlacFileReader fileReader=new FlacFileReader();

            AudioFile read = fileReader.read(new File(flacpath));

            org.jaudiotagger.tag.Tag tag = read.getTag();
            Artwork firstArtwork = tag.getFirstArtwork();
            byte[] imageData = firstArtwork.getBinaryData();
            Image image=Toolkit.getDefaultToolkit().createImage(imageData, 0,imageData.length);
            ImageIcon icon = new ImageIcon(image);
            String storePath=flacpath;
            storePath = storePath.substring(0, storePath.length()-4);
            storePath+="jpg";
            System.out.println(storePath);
            FileOutputStream fos = new FileOutputStream(storePath);
            fos.write(imageData);
            fos.close();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("��ȡFlacͼƬʱ����");
        }
    }

}
