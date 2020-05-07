package corejava.logging;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.*;

public class LoggingImageView {
    public static void main(String[] args) {
        //尝试生成log文件并获得log的Handler
    if(System.getProperty("java.util.logging.config.class") == null &&
            System.getProperty("java.util.logging.config.file") == null){
        try {
            Logger.getLogger("com.hoo.imageView").setLevel(Level.ALL);
            final int LOG_ROTATION_COUNT = 10;
            Handler handler = new FileHandler("%h/LoggingImageView.log",0,LOG_ROTATION_COUNT);
            Logger.getLogger("com.hoo.imageView").addHandler(handler);
        }
        catch (IOException e){
            Logger.getLogger("com.hoo.imageView").log(Level.SEVERE,"Cant't create log file handler",e);
        }
    }

    EventQueue.invokeLater(()->{
       Handler windowHandler = new WindowHandler();
       windowHandler.setLevel(Level.ALL);
       Logger.getLogger("com.hoo.imageView").addHandler(windowHandler);

       JFrame frame = new ImageViewFrame();
       frame.setTitle("LoggingImageViewer");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       Logger.getLogger("com.hoo.imageView").fine("Showing frame");
       frame.setVisible(true);
    });

    }
}

class ImageViewFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;
    private JLabel label;
    private static Logger logger = Logger.getLogger("com.hoo.imageView");

    public ImageViewFrame(){
        logger.entering("ImageViewerFrame","<init>");
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        //set up menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        //为文件打开添加监听器
        openItem.addActionListener(new FileOpenListener());

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.fine("Exiting.");
                System.exit(0);
            }
        });

        //use a label to display the images
        label = new JLabel();
        add(label);
        logger.exiting("ImageViewFrame","<init>");
    }

private class FileOpenListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            logger.entering("ImageViewFrame.FileOpenListener","actionPerformed",event);
            //set up file chooser
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));

            //accept all files ending with jpg
            chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
            {
                public boolean accept(File f){
                    return f.getName().toLowerCase().endsWith(".jpg")||f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "JPG Images";
                }
            });
            //show file chooser dialog
            int r = chooser.showOpenDialog(ImageViewFrame.this);
            //if image file accepted ,set it as icon of the label
            if(r == JFileChooser.APPROVE_OPTION){
                String name = chooser.getSelectedFile().getPath();
                logger.log(Level.FINE,"Reading file{0}",name);
                label.setIcon(new ImageIcon(name));
            }
            else
                logger.fine("File open dialog canceled.");
            logger.exiting("ImageViewFrame.FileOpenListener","actionPerformed");
        }

    }
}

//在窗口中显示日志
class WindowHandler extends StreamHandler{
    private JFrame frame;
    public WindowHandler(){
        frame = new JFrame();
        final JTextArea output = new JTextArea();
        output.setEditable(false);
        frame.setSize(200,200);
        //添加滚动条
        frame.add(new JScrollPane(output));
        frame.setFocusableWindowState(false);
        frame.setVisible(true);
        setOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                output.append(new String(b,off,len));
            }
        });
    }
    //处理器会缓存记录只有在缓存满时才会写入流中，覆盖publish方法，在处理器获得每个记录后刷新缓冲区
    public void publish(LogRecord record){
        if(!frame.isVisible())return;
        super.publish(record);
        flush();
    }
}
