package client;

import network.SocketThread;
import network.SocketThreadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler, SocketThreadListener {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("Siatria_Kate");
    private final JPasswordField tfPassword = new JPasswordField("777");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private final JButton btnSendAll = new JButton("SendAll");       //кнопка отправить всем

    private final JList<String> userList = new JList<>();
    private boolean shownIoErrors = false;
    private SocketThread socketThread;

    private ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle("Коточатик ^^,");
        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUser = new JScrollPane(userList);
        String[] users = {"user1", "user2", "user3", "user4", "user5",
                "user_with_an_exceptionally_long_name_in_this_chat"};
        userList.setListData(users);
        scrollUser.setPreferredSize(new Dimension(100, 0));

        cbAlwaysOnTop.addActionListener(this);
        btnSend.addActionListener(this);
        btnSendAll.addActionListener(this);       //кнопка отправить всем
        tfMessage.addActionListener(this);
        btnLogin.addActionListener(this);
        btnDisconnect.addActionListener(this);    //реализация кнопки дисконнект

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);

        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);

        add(scrollLog, BorderLayout.CENTER);
        add(scrollUser, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        panelBottom.setVisible(false);           //по умолчанию нижняя панель не видна
        panelTop.setVisible(true);               //верхняя с логином видна
        //минус только в том, что неавторизованные
        //пользователи по сути не смогут отправлять сообщения вообще
        //возможно потребуется реализовать какого нибудь анонимуса
        //чтобы можно было отправлять сообщения без регистрации,
        //но с ограниченными правами отправки сообщений

        setVisible(true);
    }

    private void connect() {
        try {
            Socket socket = new Socket(tfIPAddress.getText(), Integer.parseInt(tfPort.getText()));
            socketThread = new SocketThread(this, tfLogin.getText(), socket);
        } catch (IOException e) {
            uncaughtException(Thread.currentThread(), e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { // Event Dispatching Thread
                new ClientGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend || src == tfMessage) {
            sendMessage();
        } else if (src == btnLogin){
            connect();
        } else if (src == btnDisconnect){        //реализация кнопки дисконнект
            socketThread.close();                //закрытие потока
        } else {
            throw new RuntimeException("Unknown source: " + src);
        }
    }

    private void sendMessage() {
        //показываем в чатике
        //showInChatField("<" + TIME_FORMAT.format(LocalTime.now()) + "> " + tfLogin.getText() + ": " + tfMessage.getText());
        socketThread.sendMessage("<" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "> " + tfLogin.getText() + ": " + tfMessage.getText());
        //записываем лог
        writeLogToFile(tfMessage.getText(), tfLogin.getText());
        //обнуляем текст в поле ввода
        tfMessage.setText(null);
        //оставляем курсор в поле ввода для дальнейшей печати, чтоб не тыкать в поле каждый раз после отправки
        tfMessage.requestFocusInWindow();
    }

    private void writeLogToFile(String msg, String username) {
        if ("".equals(msg)) return;
        try (FileWriter file = new FileWriter("log.txt", true)) {
            file.write("<" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "> " + username + ": " + msg + "\n");
            file.flush();
        } catch (IOException e) {
            myException(e);
        }
    }

    private void showInChatField(String msg) {
        if ("".equals(msg)) return;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        msg = "Exception in " + t.getName() + " " +
                e.getClass().getCanonicalName() + ": " +
                e.getMessage() + "\n\t at " + ste[0];
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    public void myException(Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        msg = e.getClass().getCanonicalName() + ": " +
                e.getMessage() + "\n\t at " + ste[0];
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    /**
     * Socket thread listener methods
     * */

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        showInChatField("Start");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        showInChatField("Stop");
        panelBottom.setVisible(false);                   //нижняя панель убирается
        panelTop.setVisible(true);                       //верхняя появляется
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        showInChatField("Ready");
        panelTop.setVisible(false);                      //когда после старта потока готов сокет убирается верхняя панель
        panelBottom.setVisible(true);                    //и появляется нижняя
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        showInChatField(msg);
    }

    @Override
    public void onSocketException(SocketThread thread, Exception exception) {
        //пропускаем исключение, даем возможность завершиться потокам и закрыться сокету
        exception.printStackTrace();
    }

}
