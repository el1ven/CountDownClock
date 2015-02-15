/**
 * Created by el1ven on 14/2/15.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.Timer;

class Test2 extends JFrame implements ActionListener {

    public static void main(String[] args){
        new Test2();
    }

    private JTextField input;
    private JButton resetBtn;
    private JButton twoFuncBtn;
    private JPanel jp1,jp2;

    private String flag = "start";
    private String flag2 = "noReset";
    private Timer timer;
    private int hour;
    private int minute;
    private int second;
    private int totalSeconds;
    private int totalSecondsCopy;
    private String originTime;

    private SimpleDateFormat sdf;
    private String inputData1;
    private String inputData2;

    public Test2(){

        input = new JTextField("HH:MM:SS");
        resetBtn = new JButton("RESET");
        twoFuncBtn = new JButton("START/PAUSE");
        jp1 = new JPanel();
        jp2 = new JPanel();

        this.setLayout(new GridLayout(2,1));//2行1列
        jp1.add(input);
        jp2.add(resetBtn);
        jp2.add(twoFuncBtn);

        resetBtn.addActionListener(this);
        twoFuncBtn.addActionListener(this);

        add(jp1);
        add(jp2);

        sdf = new SimpleDateFormat("HH:mm:ss");

        inputData1 = JOptionPane.showInputDialog("Start Time:");
        inputData2 = JOptionPane.showInputDialog("Warning Time:");
        try {
            Date startTime = sdf.parse(inputData1);
            Date warnTime = sdf.parse(inputData2);
            Date basicTime = sdf.parse("00:00:00");

            if(warnTime.after(startTime)){
                inputData1 = JOptionPane.showInputDialog("Start Time:");
                inputData2 = JOptionPane.showInputDialog("Warning Time:");
            }else{
                input.setText(inputData1);
                startTime = sdf.parse(inputData1);
                totalSeconds = (int) ((startTime.getTime()-basicTime.getTime())/1000);
                totalSecondsCopy = totalSeconds;
            }
            originTime = inputData1;
            timer = new Timer();
            timer.schedule(new myTask(){
                @Override
                public void run(){
                    if(totalSeconds > 0){
                        if(input.getText().equals(inputData2)){
                            JOptionPane.showMessageDialog(null, "It's the warning time!", "CountDown Tips", JOptionPane.OK_OPTION);
                        }
                        if(flag.equals("start")){
                            if(flag2.equals("reset")){
                                totalSeconds = totalSecondsCopy;
                                flag = "pause";
                                flag2 = "noReset";
                            }
                            totalSeconds--;
                            hour = totalSeconds/3600;
                            minute = (totalSeconds - 3600*hour)/60;
                            second = totalSeconds - 3600*hour - 60*minute;
                            String clockData = hour + ":" + minute + ":" +second + "";
                            String clockData2 = "";
                            try{
                                SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm:ss");
                                Date date= sdf2.parse(clockData);
                                clockData2 = sdf2.format(date);
                            }catch(ParseException e){
                                e.printStackTrace();
                            }
                            input.setText(clockData2);
                        }
                        if(flag.equals("pause")){
                            hour = totalSeconds/3600;
                            minute = (totalSeconds - 3600*hour)/60;
                            second = totalSeconds - 3600*hour - 60*minute;
                            String clockData = hour + ":" + minute + ":" +second + "";
                            String clockData2 = "";
                            try{
                                SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm:ss");
                                Date date= sdf2.parse(clockData);
                                clockData2 = sdf2.format(date);
                            }catch(ParseException e){
                                e.printStackTrace();
                            }
                            input.setText(clockData2);
                        }
                        if(flag.equals("reset")){
                            input.setText(originTime);
                            flag = "start";
                            flag2 = "reset";
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "There is no time left!", "CountDown Tips", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }, 0, 1000);
        }catch(ParseException e){
            e.printStackTrace();
        }

        setTitle("Clock CountDown Demo");
        setSize(500, 300);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == twoFuncBtn && flag.equals("start")){
            flag = "pause";
        }else if(e.getSource() == twoFuncBtn && flag.equals("pause")){
            flag = "start";
        }else if(e.getSource() == resetBtn && flag.equals("pause")){
            flag = "reset";
        }
    }

    class myTask extends TimerTask{

        @Override
        public void run() {
        }
    }
}