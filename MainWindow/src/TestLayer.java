import javax.swing.*;
import java.awt.*;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/3 18:51
 */
public class TestLayer extends JPanel {
    public TestLayer(){
        Button bt = new Button("???");
        setSize(1920,1440);
        setLayout(null);
        bt.setSize(200,200);
        bt.setLocation(100,200);
        add(bt);
    }
}
