package cbb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;
import javax.swing.JFrame;


public class Main extends JFrame { // 在一个JFrame框架中显示出来，而不是在dos控制台中显示

    // 初始化框架
    private static Main frame = new Main();

    // 一个文本区域，在其上面显示字符
    private JTextArea display = new JTextArea();

    // 计时器，参数"125"说明计时器每125毫秒触发一次"TimerListener"，会发生什么请查看最下方的TimerListener类;
    // 另外生成badapple.txt时的帧数是8帧/秒
    // 1秒=1000毫秒，也就是说每1帧占用125毫秒
    private Timer timer = new Timer(125, new TimerListener());

    // 用来装载每一帧图片的Vector容器，Vector<String>说明里面装的是String(字符串)
    private Vector<String> pictures = new Vector<>();

    // 帧数计算器，在TimerListener类中会用到
    private int count = 0;

    private Main() {

        // 设置文本区域的字体，"Courier New"可以保证各个字符的宽度相同，"BOLD"是加粗的意思，"6"指的是字体大小
        display.setFont(new Font("Courier New", Font.BOLD, 6));
        display.setBackground(Color.BLACK); // 设置文本区域的背景色为黑色
        display.setForeground(Color.WHITE); // 设置文本区域的前景色为白色
        display.setEditable(false); // 设置文本区域不可被编辑
        add(display); // 在框架中把文本区域添加进去，这样框架中就可以显示出文本区域了

        try {
            // 打开txt文件，我自己的文件相对路径为"src/badapple.txt"，请诸位根据自己的工程构成填写路径，再不行填类似"F://badapple.txt"这样的绝对路径也行
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src/badapple.txt"))));

            // badapple.txt中有104700行，每帧由60行组成（其中前58行为图像，59行是空白行，60行是一个标记）
            // 因此一共有104700 / 60 = 1745帧图像
            // 以下做的工作是事先把1745帧全部提取出来，放到pictures容器中备用
            for (int i = 0; i < 1745; i++) {
                StringBuilder onePicture = new StringBuilder();
                for (int j = 0; j < 58; j++)
                    onePicture.append(reader.readLine()).append("\n"); // 按行读取，读取58次，每行要在后面加上一个换行符

                reader.readLine(); // 一帧中的第59行，没用，读取即丢弃
                reader.readLine(); // 一帧中的第60行，没用，读取即丢弃
                pictures.add(onePicture.toString()); // 每读取完60行就把一帧图片塞进pictures中去
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in reading file");
        }
        timer.start(); // 开启计时器

    }
    // main函数，程序的入口
    public static void main(String[] args) {


        frame.setTitle("cbb Project - Bad Apple"); // 设置框架的标题
        frame.setSize(605, 427); // 设置框架的（宽度，高度），参数是根据字符大小而微调过得
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭框架的时候回收进程的资源，如果不设置的话要到任务管理器手动结束进程才行
        frame.setLocationRelativeTo(null); // 设置框架在屏幕中间出现
        frame.setVisible(true); // 使得框架显示出来，不设置的话是看不到框架的
        frame.setResizable(false); // 使得框架不能被拉伸，最大化也不行
        new PlayMusic();
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            display.setText(""); // 每次显示图片之前会把之前的字符都清空
            display.append(pictures.elementAt(count)); // 从pictures容器中读取一帧图片，显示区域加载一帧图片
            count++; // 计数器 + 1
            if (count >= 1745)  // 在播放完1745帧之后停止计时器
                timer.stop();
        }
    }
}