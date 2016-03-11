/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import static chatclient.UpdateMangeChatG.UpdateMangeChatGFun;
import chatclient.contactlist.ContactList;
import chatclient.contactlist.ContactListGUILoder;
import chatserver.ChatInt;
import chatserver.DataReceiver;
import chatserver.DataReceiverInt;
import chatserver.DataSender;
import chatserver.DataSenderInt;
import chatserver.Message;
import chatserver.MessageInt;
import chatserver.User;
import clienthandeler.ClientHandeler;
import javax.swing.UIManager;
import de.javasoft.plaf.synthetica.*;
import createcontactgroup.ContactGroupGUILoder;
import createcontactgroup.ContactGroupHandeler;
import createcontactgroup.ContactGroupManageGUILoder;
import createcontactgroup.ContactGroupMethods;
import static createcontactgroup.ContactGroupMethods.createBtn;
import creategroupchat.ChatGroupGUILoder;
import creategroupchat.ChatGroupManageGUILoder;
import creategroupchat.GroupChatHandeler;
import creategroupchat.GroupChatMethods;
import creategroupchat.ShowChatGroupManageGUILoder;
import creategroupchat.ShowGroupChatManageGUI;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import msgcache.MainCache;
import msgcache.Msg;
import msgcache.*;

/**
 *
 * @author root
 */
public class ClientGUI extends JFrame implements Runnable {
    String repo;
    JFileChooser fc;
    File f;
    ClientHandeler clientHandeler;
    Thread th;
    int row = 0;
    ChatInt ch;
    private int crruid;
    /**
     * Creates new form ClientGUI
     */
    GroupChatHandeler groupChatHandeler;
    ContactListGUILoder contactListGUILoder;

    ContactGroupHandeler contactGroupHandeler;

    JMenuItem signOutNow;
    JMenuItem about;
    JMenuItem GTKLookAndFeel;
    JMenuItem PlainLookAndFeel;
    JMenuItem defaultTheme;
    JMenuItem WindowsLookAndFeel;
    public ClientGUI() {

        try {
            UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
        } catch (UnsupportedLookAndFeelException | ParseException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        row = 0;
        repo = "/home/omar/Desktop";
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                try {

                    String myID = "" + CurrentUser.currentuser.id;

                    clientHandeler.signOut(myID);
                } catch (Exception ex) {
                    System.exit(0);
                    //Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        try {
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Server Is Offline", "Error", JOptionPane.ERROR_MESSAGE);

            System.exit(0);

        }

        clientHandeler = new ClientHandeler();
        th = new Thread(this);
        initComponents();

        String imgstr;
        BufferedImage newImg;
        ImageIcon userIMG;
        signOutNow = new JMenuItem("Sign Out");
        about = new JMenuItem("About");
        GTKLookAndFeel = new JMenuItem("GTKLookAndFeel");
        PlainLookAndFeel = new JMenuItem("PlainLookAndFeel");
        WindowsLookAndFeel = new JMenuItem("WindowsLookAndFeel");
        defaultTheme = new JMenuItem("defaultTheme");
        
        
        aboutMenu.add(about);
        fileMenu.add(signOutNow);

        signOutNow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    // TODO add your handling code here:
                    currentUser = clientHandeler.getUserObject(myNAMEU);

                    String myID = "" + currentUser.id;
                    System.out.println(myID);

                    clientHandeler.signOut(myID);
                } catch (RemoteException ex) {
                    //  Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(0);

                }
                System.exit(0);
            }
        });
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        aboutMenu.add(about);
        theme.add(GTKLookAndFeel);
        theme.add(PlainLookAndFeel);
        theme.add(WindowsLookAndFeel);
        theme.add(defaultTheme);

        GTKLookAndFeel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                            SwingUtilities.updateComponentTreeUI(mainCardLayout);
                        } catch (Exception exception) {

                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);

            }
        });
        PlainLookAndFeel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
                            SwingUtilities.updateComponentTreeUI(mainCardLayout);
                        } catch (Exception exception) {

                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);

            }
        });
        WindowsLookAndFeel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                            //UIManager.setLookAndFeel("com.jtattoo.plaf.custom.flx.FLXLookAndFeel");
                            SwingUtilities.updateComponentTreeUI(mainCardLayout);
                        } catch (Exception exception) {

                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);

            }
        });
        defaultTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
                            SwingUtilities.updateComponentTreeUI(mainCardLayout);
                        } catch (Exception exception) {

                        }
                    }
                };
                SwingUtilities.invokeLater(runnable);

            }
        });

        try {
            imgstr = CurrentUser.currentuser.image;
            newImg = ImageBase64Converter.decodeToImage(imgstr);
            userIMG = new ImageIcon(newImg.getScaledInstance(50, 50, Image.SCALE_DEFAULT));

        } catch (Exception ex) {
            imgstr = ImageBase64Converter.img;
            newImg = ImageBase64Converter.decodeToImage(imgstr);
            userIMG = new ImageIcon(newImg.getScaledInstance(50, 50, Image.SCALE_DEFAULT));

        }

        myImage.setIcon(userIMG);

        f = null;
        fc = new JFileChooser();
        try {
            ch = (ChatInt) Naming.lookup("//localhost:3333/chat");
            ContactList.setChatObj(ch);
            ShowGroupChatManageGUI.setChatObj(ch);
            new Msg(chatArea);
            ContactList.setJTextArea(chatArea);
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(this, "Email already Exists", "error", JOptionPane.ERROR_MESSAGE);

            ex.printStackTrace();
        }
        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        mainCardLayout = new javax.swing.JPanel();
        loginPanel = new javax.swing.JPanel();
        leftLapels = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        loginBtn = new javax.swing.JButton();
        centerLapels = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        userNameTxt = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        passwordTxt = new javax.swing.JPasswordField();
        jPanel30 = new javax.swing.JPanel();
        signUpBtn = new javax.swing.JButton();
        topTitile = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        signUpPanel = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        firstNameText = new javax.swing.JTextField();
        lastNameText = new javax.swing.JTextField();
        userNameText = new javax.swing.JTextField();
        passwordText = new javax.swing.JPasswordField();
        passwordConfText = new javax.swing.JPasswordField();
        emailText = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        maleRadio = new javax.swing.JRadioButton();
        femaleRadio = new javax.swing.JRadioButton();
        countryList = new javax.swing.JComboBox<>();
        browsBtn = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        confirmSignUpBtn = new javax.swing.JButton();
        chatPanel = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel22 = new javax.swing.JPanel();
        ChatControls = new javax.swing.JPanel();
        m = new javax.swing.JPanel();
        myImage = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        editLabel = new javax.swing.JLabel();
        statusComboBox = new javax.swing.JComboBox<>();
        jPanel34 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        addFriend = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        manageContactGroupBtn = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        contactChatBtn = new javax.swing.JButton();
        groupChatBtn = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        contactListPanel = new javax.swing.JPanel();
        contactListHolder = new javax.swing.JPanel();
        contactListScroll = new javax.swing.JScrollPane();
        contactList = new javax.swing.JPanel();
        chatGroupHolder = new javax.swing.JPanel();
        chatGroupScroll = new javax.swing.JScrollPane();
        groupList = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        notePanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jPanel32 = new javax.swing.JPanel();
        etx = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        sendMsgBtn = new javax.swing.JButton();
        btn_browse = new javax.swing.JButton();
        lbl_file = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sendMsgText = new javax.swing.JTextArea();
        chatGroupManage = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        chatGroupCreate = new javax.swing.JButton();
        backBTN500 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        chatGroupManagePanel = new javax.swing.JPanel();
        chatGroup = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        chatGroupName = new javax.swing.JTextField();
        backBtn2 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        createChatGoup = new javax.swing.JButton();
        jPanel39 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        chatGroupMembers = new javax.swing.JPanel();
        contactGroupManage = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        contactGroupCreate = new javax.swing.JButton();
        backBTN7888 = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        contactGroupManagePanel = new javax.swing.JPanel();
        contactGroup = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        contactGroupName = new javax.swing.JTextField();
        backBTN1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        createContactGroup = new javax.swing.JButton();
        jPanel46 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        contactGroupMembers = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        theme = new javax.swing.JMenu();
        fileMenu = new javax.swing.JMenu();
        aboutMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainCardLayout.setPreferredSize(new java.awt.Dimension(260, 130));
        mainCardLayout.setLayout(new java.awt.CardLayout());

        loginPanel.setName("loginPanel"); // NOI18N
        loginPanel.setPreferredSize(new java.awt.Dimension(80, 50));
        loginPanel.setLayout(new java.awt.BorderLayout());

        leftLapels.setLayout(new java.awt.GridLayout(3, 0));

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setText("User Name: ");
        jPanel2.add(jLabel1);

        leftLapels.add(jPanel2);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jLabel2.setText("Password:");
        jPanel3.add(jLabel2);

        leftLapels.add(jPanel3);

        jPanel4.setLayout(new java.awt.GridLayout(1, 2, 2, 0));

        loginBtn.setText("Login");
        loginBtn.setName("loginBtn"); // NOI18N
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });
        jPanel7.add(loginBtn);

        jPanel4.add(jPanel7);

        leftLapels.add(jPanel4);

        loginPanel.add(leftLapels, java.awt.BorderLayout.WEST);

        centerLapels.setLayout(new java.awt.GridLayout(3, 0));

        jPanel1.setLayout(new java.awt.BorderLayout());

        userNameTxt.setName("userNameTxt"); // NOI18N
        userNameTxt.setPreferredSize(new java.awt.Dimension(100, 28));
        jPanel1.add(userNameTxt, java.awt.BorderLayout.CENTER);
        userNameTxt.getAccessibleContext().setAccessibleName("");

        centerLapels.add(jPanel1);

        jPanel5.setLayout(new java.awt.BorderLayout());

        passwordTxt.setName("passwordTxt"); // NOI18N
        passwordTxt.setPreferredSize(new java.awt.Dimension(100, 28));
        jPanel5.add(passwordTxt, java.awt.BorderLayout.CENTER);

        centerLapels.add(jPanel5);

        signUpBtn.setText("SignUp");
        signUpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signUpBtnActionPerformed(evt);
            }
        });
        jPanel30.add(signUpBtn);

        centerLapels.add(jPanel30);

        loginPanel.add(centerLapels, java.awt.BorderLayout.CENTER);

        topTitile.setLayout(new javax.swing.BoxLayout(topTitile, javax.swing.BoxLayout.LINE_AXIS));

        jLabel3.setText("Login");
        topTitile.add(jLabel3);

        loginPanel.add(topTitile, java.awt.BorderLayout.NORTH);

        mainCardLayout.add(loginPanel, "loginPanel");

        signUpPanel.setName("signUpPanel"); // NOI18N
        signUpPanel.setPreferredSize(new java.awt.Dimension(800, 300));
        signUpPanel.setLayout(new java.awt.BorderLayout());

        jPanel38.setLayout(new javax.swing.BoxLayout(jPanel38, javax.swing.BoxLayout.LINE_AXIS));

        jLabel4.setText("SignUp");
        jPanel38.add(jLabel4);

        signUpPanel.add(jPanel38, java.awt.BorderLayout.NORTH);

        jPanel31.setLayout(new java.awt.GridLayout(9, 0));

        firstNameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameTextActionPerformed(evt);
            }
        });
        jPanel31.add(firstNameText);
        jPanel31.add(lastNameText);
        jPanel31.add(userNameText);
        jPanel31.add(passwordText);
        jPanel31.add(passwordConfText);
        jPanel31.add(emailText);

        jPanel19.setLayout(new java.awt.GridLayout(2, 0));

        buttonGroup.add(maleRadio);
        maleRadio.setText("Male");
        jPanel19.add(maleRadio);

        buttonGroup.add(femaleRadio);
        femaleRadio.setText("Female");
        jPanel19.add(femaleRadio);

        jPanel31.add(jPanel19);

        countryList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Egypt", "USA", "Ducks", "KSA", "Canada" }));
        jPanel31.add(countryList);

        browsBtn.setText("Browse");
        browsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browsBtnActionPerformed(evt);
            }
        });
        jPanel31.add(browsBtn);

        signUpPanel.add(jPanel31, java.awt.BorderLayout.CENTER);

        jPanel8.setLayout(new java.awt.GridLayout(9, 0));

        jLabel9.setText("First Name");
        jPanel8.add(jLabel9);

        jLabel10.setText("LastName");
        jPanel8.add(jLabel10);

        jLabel5.setText("User Name");
        jPanel8.add(jLabel5);

        jLabel6.setText("Password");
        jPanel8.add(jLabel6);

        jLabel7.setText("ConfirmPassword");
        jPanel8.add(jLabel7);

        jLabel8.setText("Email");
        jPanel8.add(jLabel8);

        jLabel12.setText("Gender");
        jPanel8.add(jLabel12);

        jLabel11.setText("Country");
        jPanel8.add(jLabel11);

        jLabel13.setText("Image");
        jPanel8.add(jLabel13);

        signUpPanel.add(jPanel8, java.awt.BorderLayout.WEST);

        confirmSignUpBtn.setText("SignUp");
        confirmSignUpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmSignUpBtnActionPerformed(evt);
            }
        });
        jPanel37.add(confirmSignUpBtn);

        signUpPanel.add(jPanel37, java.awt.BorderLayout.PAGE_END);

        mainCardLayout.add(signUpPanel, "signUpPanel");

        chatPanel.setBorder(null);
        chatPanel.setName("chatPanel"); // NOI18N
        chatPanel.setLayout(new java.awt.BorderLayout());

        jPanel22.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 4, 4, 1));
        jPanel22.setLayout(new java.awt.BorderLayout());

        ChatControls.setLayout(new java.awt.BorderLayout());

        m.add(myImage);

        ChatControls.add(m, java.awt.BorderLayout.NORTH);

        jPanel26.setLayout(new java.awt.GridLayout(3, 0));

        jPanel33.add(editLabel);

        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "available", "busy", "idle" }));
        statusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusComboBoxActionPerformed(evt);
            }
        });
        jPanel33.add(statusComboBox);

        jPanel26.add(jPanel33);

        jButton6.setText("Mange Chat Group");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel34.add(jButton6);

        addFriend.setText("Add User");
        addFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFriendActionPerformed(evt);
            }
        });
        jPanel34.add(addFriend);

        jPanel26.add(jPanel34);

        manageContactGroupBtn.setText("Manage Contact Group");
        manageContactGroupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageContactGroupBtnActionPerformed(evt);
            }
        });
        jPanel41.add(manageContactGroupBtn);

        jPanel26.add(jPanel41);

        ChatControls.add(jPanel26, java.awt.BorderLayout.PAGE_END);

        jPanel22.add(ChatControls, java.awt.BorderLayout.NORTH);

        jPanel27.setLayout(new java.awt.BorderLayout());

        jPanel28.setLayout(new java.awt.GridLayout(1, 0));

        contactChatBtn.setText("Contact List");
        contactChatBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactChatBtnActionPerformed(evt);
            }
        });
        jPanel28.add(contactChatBtn);

        groupChatBtn.setText("Group Chats");
        groupChatBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupChatBtnActionPerformed(evt);
            }
        });
        jPanel28.add(groupChatBtn);

        jPanel27.add(jPanel28, java.awt.BorderLayout.PAGE_START);

        jPanel29.setLayout(new java.awt.BorderLayout());

        contactListPanel.setLayout(new java.awt.CardLayout());

        contactListHolder.setName("contactListHolder"); // NOI18N
        contactListHolder.setLayout(new java.awt.BorderLayout());

        contactList.setLayout(new java.awt.GridLayout(1, 0));
        contactListScroll.setViewportView(contactList);

        contactListHolder.add(contactListScroll, java.awt.BorderLayout.CENTER);

        contactListPanel.add(contactListHolder, "contactList");

        chatGroupHolder.setLayout(new java.awt.BorderLayout());

        groupList.setLayout(new java.awt.GridLayout(1, 0));
        chatGroupScroll.setViewportView(groupList);

        chatGroupHolder.add(chatGroupScroll, java.awt.BorderLayout.CENTER);

        contactListPanel.add(chatGroupHolder, "groupContactList");

        jPanel29.add(contactListPanel, java.awt.BorderLayout.CENTER);

        jPanel27.add(jPanel29, java.awt.BorderLayout.CENTER);

        jPanel22.add(jPanel27, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel22);

        jPanel23.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 4, 4));
        jPanel23.setLayout(new java.awt.BorderLayout());

        notePanel.setLayout(new javax.swing.BoxLayout(notePanel, javax.swing.BoxLayout.LINE_AXIS));

        jLabel14.setText("Notification");
        notePanel.add(jLabel14);

        jPanel23.add(notePanel, java.awt.BorderLayout.PAGE_START);

        jPanel25.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel35.setLayout(new java.awt.BorderLayout());

        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        jPanel35.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel32.add(etx);

        jButton10.setText("Font");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel32.add(jButton10);

        jButton11.setText("Color");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel32.add(jButton11);

        jPanel35.add(jPanel32, java.awt.BorderLayout.PAGE_END);

        jPanel25.add(jPanel35, java.awt.BorderLayout.CENTER);

        jPanel20.setLayout(new java.awt.BorderLayout(20, 20));

        sendMsgBtn.setText("Send");
        sendMsgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMsgBtnActionPerformed(evt);
            }
        });
        jPanel21.add(sendMsgBtn);

        btn_browse.setText("Browse");
        btn_browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_browseActionPerformed(evt);
            }
        });
        jPanel21.add(btn_browse);

        lbl_file.setText("no file");
        jPanel21.add(lbl_file);

        jPanel20.add(jPanel21, java.awt.BorderLayout.LINE_END);

        sendMsgText.setColumns(20);
        sendMsgText.setRows(5);
        jScrollPane2.setViewportView(sendMsgText);

        jPanel20.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel25.add(jPanel20, java.awt.BorderLayout.PAGE_END);

        jPanel23.add(jPanel25, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel23);

        chatPanel.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        mainCardLayout.add(chatPanel, "chatPanel");

        chatGroupManage.setLayout(new java.awt.BorderLayout());

        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        chatGroupCreate.setText("Create");
        chatGroupCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatGroupCreateActionPerformed(evt);
            }
        });
        jPanel6.add(chatGroupCreate);

        backBTN500.setText("Back");
        backBTN500.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBTN500ActionPerformed(evt);
            }
        });
        jPanel6.add(backBTN500);

        jPanel11.add(jPanel6, java.awt.BorderLayout.CENTER);

        jLabel15.setText("Manage Groups");
        jPanel13.add(jLabel15);

        jPanel11.add(jPanel13, java.awt.BorderLayout.WEST);

        chatGroupManage.add(jPanel11, java.awt.BorderLayout.NORTH);

        jPanel12.setPreferredSize(new java.awt.Dimension(10, 100));
        jPanel12.setLayout(new java.awt.BorderLayout());

        chatGroupManagePanel.setLayout(new java.awt.BorderLayout());
        jScrollPane4.setViewportView(chatGroupManagePanel);

        jPanel12.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        chatGroupManage.add(jPanel12, java.awt.BorderLayout.CENTER);

        mainCardLayout.add(chatGroupManage, "chatGroupManage");

        chatGroup.setLayout(new java.awt.BorderLayout());

        jPanel16.setLayout(new java.awt.BorderLayout());

        jLabel18.setText("Create Group");
        jPanel17.add(jLabel18);

        jPanel16.add(jPanel17, java.awt.BorderLayout.WEST);

        jLabel19.setText("GoupName");
        jPanel18.add(jLabel19);

        chatGroupName.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel18.add(chatGroupName);

        backBtn2.setText("Back");
        backBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtn2ActionPerformed(evt);
            }
        });
        jPanel18.add(backBtn2);

        jPanel16.add(jPanel18, java.awt.BorderLayout.CENTER);

        chatGroup.add(jPanel16, java.awt.BorderLayout.NORTH);

        createChatGoup.setText("Create Group");
        createChatGoup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createChatGoupActionPerformed(evt);
            }
        });
        jPanel9.add(createChatGoup);

        chatGroup.add(jPanel9, java.awt.BorderLayout.SOUTH);

        jPanel39.setPreferredSize(new java.awt.Dimension(10, 100));
        jPanel39.setLayout(new java.awt.BorderLayout());

        chatGroupMembers.setLayout(new java.awt.BorderLayout());
        jScrollPane3.setViewportView(chatGroupMembers);

        jPanel39.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        chatGroup.add(jPanel39, java.awt.BorderLayout.CENTER);

        mainCardLayout.add(chatGroup, "chatGroup");

        contactGroupManage.setLayout(new java.awt.BorderLayout());

        jPanel14.setLayout(new java.awt.BorderLayout());

        jLabel17.setText("Manage Contact Groups");
        jPanel15.add(jLabel17);

        jPanel14.add(jPanel15, java.awt.BorderLayout.WEST);

        jPanel36.setLayout(new java.awt.GridLayout(1, 0));

        contactGroupCreate.setText("Create");
        contactGroupCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactGroupCreateActionPerformed(evt);
            }
        });
        jPanel36.add(contactGroupCreate);

        backBTN7888.setText("Back");
        backBTN7888.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBTN7888ActionPerformed(evt);
            }
        });
        jPanel36.add(backBTN7888);

        jPanel14.add(jPanel36, java.awt.BorderLayout.CENTER);

        contactGroupManage.add(jPanel14, java.awt.BorderLayout.NORTH);

        jPanel40.setPreferredSize(new java.awt.Dimension(10, 100));
        jPanel40.setLayout(new java.awt.BorderLayout());

        contactGroupManagePanel.setLayout(new java.awt.BorderLayout());
        jScrollPane5.setViewportView(contactGroupManagePanel);

        jPanel40.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        contactGroupManage.add(jPanel40, java.awt.BorderLayout.CENTER);

        mainCardLayout.add(contactGroupManage, "contactGroupManage");

        contactGroup.setLayout(new java.awt.BorderLayout());

        jPanel42.setLayout(new java.awt.BorderLayout());

        jLabel20.setText("Create Group");
        jPanel44.add(jLabel20);

        jPanel42.add(jPanel44, java.awt.BorderLayout.WEST);

        jLabel21.setText("GoupName");
        jPanel45.add(jLabel21);

        contactGroupName.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel45.add(contactGroupName);

        backBTN1.setText("Back");
        backBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBTN1ActionPerformed(evt);
            }
        });
        jPanel45.add(backBTN1);

        jPanel42.add(jPanel45, java.awt.BorderLayout.CENTER);

        contactGroup.add(jPanel42, java.awt.BorderLayout.NORTH);

        createContactGroup.setText("Create Group");
        createContactGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createContactGroupActionPerformed(evt);
            }
        });
        jPanel10.add(createContactGroup);

        contactGroup.add(jPanel10, java.awt.BorderLayout.SOUTH);

        jPanel46.setPreferredSize(new java.awt.Dimension(10, 100));
        jPanel46.setLayout(new java.awt.BorderLayout());

        contactGroupMembers.setLayout(new java.awt.BorderLayout());
        jScrollPane6.setViewportView(contactGroupMembers);

        jPanel46.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        contactGroup.add(jPanel46, java.awt.BorderLayout.CENTER);

        mainCardLayout.add(contactGroup, "contactGroup");

        getContentPane().add(mainCardLayout, java.awt.BorderLayout.CENTER);

        theme.setText("Themes");
        jMenuBar1.add(theme);

        fileMenu.setText("File");
        jMenuBar1.add(fileMenu);

        aboutMenu.setText("About");
        jMenuBar1.add(aboutMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void firstNameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameTextActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        ContactList pp = new ContactList();
        contactList.add(pp);
        row++;
        contactList.setLayout(new GridLayout(row, 1));
        contactList.updateUI();
        System.out.println("ooo");


    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton11ActionPerformed

    private void addFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFriendActionPerformed
        // TODO add your handling code here:

        String userName = JOptionPane.showInputDialog("Please Enter User Email");
        int userfid = clientHandeler.getUserId(userName);
        String useridstring = userfid + "";
        System.out.println("" + user.id);
        System.out.println(clientHandeler.addUserContactListHandeler("" + user.id, useridstring));

        new UpdateGui(groupList, contactList, mainCardLayout);

    }//GEN-LAST:event_addFriendActionPerformed

    private void signUpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signUpBtnActionPerformed
        // TODO add your handling code here:
        CardLayout c = (CardLayout) mainCardLayout.getLayout();
        c.show(mainCardLayout, "signUpPanel");
        //mainCardLayou t.setSize(400, 600);
        //mainCardLayout.resize(800, 800);
        //  mainCardLayout.repaint();
        //  mainCardLayout.updateUI();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


    }//GEN-LAST:event_signUpBtnActionPerformed
    String username, password, email, image, firstname, lastname, country, gender;
    User newUserSignUp;

    public boolean signUpIsValide(String username, String password, String email, String firstname, String lastname, String country, String gender) {
        if (username.equals("")) {
            return false;
        }
        if (password.equals("")) {
            return false;
        }
        if (email.equals("")) {
            return false;
        }
        if (firstname.equals("")) {
            return false;
        }
        if (country.equals("")) {
            return false;
        }
        if (gender.equals("")) {
            return false;
        }

        return true;
    }

    @SuppressWarnings("empty-statement")
    private void confirmSignUpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmSignUpBtnActionPerformed

        femaleRadio.setActionCommand("Female");
        maleRadio.setActionCommand("Male");

        String pass = passwordText.getText();
        String passcon = passwordConfText.getText();
        if (pass.equals(passcon)) {

            firstname = firstNameText.getText();
            lastname = lastNameText.getText();
            username = userNameText.getText();

            email = emailText.getText();
            try {
                gender = buttonGroup.getSelection().getActionCommand();
                System.out.println(gender);
            } catch (Exception e) {

                JOptionPane.showMessageDialog(this, "Choose your gender", "Missing", JOptionPane.ERROR_MESSAGE);

            }
            country = countryList.getSelectedItem().toString();
            password = passwordText.getText();
            image = "";
            //System.out.println(clientHandeler.userIsExist(email) && clientHandeler.userNameIsExist(username)) ;
            if (signUpIsValide(username, password, email, firstname, lastname, country, gender)) {
                try {
                    if (clientHandeler.userIsExist(email) && clientHandeler.userNameIsExist(username)) {
                        JOptionPane.showMessageDialog(this, "Email already Exists", "error", JOptionPane.ERROR_MESSAGE);
                    } else {

                        try {

                            newUserSignUp = new User(username, password, email, imgstr, firstname, lastname, country, gender);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (clientHandeler.addUser(newUserSignUp)) {
                            JOptionPane.showMessageDialog(this, "Sign Up Successfully");
                            CardLayout c = (CardLayout) mainCardLayout.getLayout();
                            c.show(mainCardLayout, "loginPanel");

                        } else {
                            JOptionPane.showMessageDialog(this, "Server error try again", "error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception e) {
                }
            } else {
                JOptionPane.showMessageDialog(this, "Missing Reg Field", "Missing", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Wrong Password", "Failure", JOptionPane.ERROR_MESSAGE);

        }


    }//GEN-LAST:event_confirmSignUpBtnActionPerformed
    User user;
    String myNAMEU;
    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        // TODO add your handling code here:
        boolean res = clientHandeler.login(userNameTxt.getText(), passwordTxt.getText());
        System.out.println(res);
        if (res) {
            CardLayout c = (CardLayout) mainCardLayout.getLayout();
            c.show(mainCardLayout, "chatPanel");
            myNAMEU = userNameTxt.getText();
            try {
                user = clientHandeler.getUserObject(myNAMEU);
                new CurrentUser(user);
                crruid = user.id;
                ch.getOfflineMessages(crruid);
                th.start();
                contactListGUILoder = new ContactListGUILoder();
                new UpdateGui(groupList, contactList, mainCardLayout);
                Icon icon = ImageBase64Converter.resizer("editbtn.png", 50, 50);
                editLabel.setIcon(icon);
                editLabel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        EditMyProfile edit = new EditMyProfile(user);
                        edit.setSize(400, 600);
                        edit.setVisible(true);

                    }
                });
            } catch (RemoteException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            etx.setText(myNAMEU);
        } else {
            JOptionPane.showMessageDialog(this, new String("Invalid username or password"));
        }


    }//GEN-LAST:event_loginBtnActionPerformed
    String textSend;
    Message message;
    private void sendMsgBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMsgBtnActionPerformed
        if (!sendMsgText.getText().equals("")) {
            String str = sendMsgText.getText();
            System.out.println("Sending message : " + str);
          //  System.out.println("dest id : " + ContactList.conid);
            
            try {
                
                MessageInt msg;
                ChatType ctype;
                if(ContactList.isChatTypeSet()){
                    ctype = ContactList.getChatType();
                    System.out.println("single id : " + ctype.getId());
                    msg = new Message(CurrentUser.currentuser.id, ctype.getId(), str, false, false, false);
                }else{
                    ctype = ShowGroupChatManageGUI.getChatType();
                                        System.out.println("group id : " + ctype.getId());

                    msg = new Message(CurrentUser.currentuser.id, ctype.getId(), str, true, false, false);
                }
                ch.appendMessage(msg);
                sendMsgText.setText("");
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
        if (f != null) {

            try {
                MessageInt msg;
                ChatType ctype;
                if(ContactList.isChatTypeSet()){
                    ctype = ContactList.getChatType();
                    msg = new Message(CurrentUser.currentuser.id, ctype.getId(), f.getName(), false, true, false);
                }else{
                    ctype = ShowGroupChatManageGUI.getChatType();
                    msg = new Message(CurrentUser.currentuser.id, ctype.getId(), f.getName(), false, true, false);
                }
                f = null;
                lbl_file.setText("no file");
                ch.appendMessage(msg);
                DataReceiverInt fr = (DataReceiverInt) Naming.lookup("//localhost:4444/freceiver");
                DataSender fs = new DataSender(f);
                fs.exportData(fr);
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_sendMsgBtnActionPerformed
    User currentUser;
    private void statusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusComboBoxActionPerformed
        String status = statusComboBox.getSelectedItem().toString();
        try {
            // TODO add your handling code here:
            currentUser = clientHandeler.getUserObject(myNAMEU);

            String myID = "" + currentUser.id;
            System.out.println(myID + status);

            clientHandeler.changeStatus(myID, status);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        // currentUser.id;

    }//GEN-LAST:event_statusComboBoxActionPerformed

    private void createChatGoupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createChatGoupActionPerformed
        // TODO add your handling code here:
        if (!evt.getActionCommand().equals("Save")) {

            GroupChatMethods.createBtnAction();

        } else {

            GroupChatMethods.saveBtnAction();
        }
        UpdateMangeChatG.UpdateMangeChatGFun();
        CardLayout c = (CardLayout) mainCardLayout.getLayout();
        c.show(mainCardLayout, "chatGroupManage");

    }//GEN-LAST:event_createChatGoupActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        GroupChatMethods groupChatMethods = new GroupChatMethods(mainCardLayout, chatGroupManage, chatGroup, chatGroupName, createChatGoup);
        new UpdateMangeChatG(chatGroupMembers, chatGroupManagePanel, mainCardLayout);
        CardLayout c = (CardLayout) mainCardLayout.getLayout();
        c.show(mainCardLayout, "chatGroupManage");

    }//GEN-LAST:event_jButton6ActionPerformed
    private void chatGroupCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatGroupCreateActionPerformed
        // TODO add your handling code here:
        GroupChatMethods.createBtn();
        createChatGoup.setActionCommand("Create");

        UpdateMangeChatG.UpdateMangeChatGFun();
        CardLayout c = (CardLayout) mainCardLayout.getLayout();
        c.show(mainCardLayout, "chatGroup");


    }//GEN-LAST:event_chatGroupCreateActionPerformed

    private void backBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtn2ActionPerformed
        // TODO add your handling code here:
        createChatGoup.setActionCommand("Create");
        ContactGroupMethods.createBtn();

        UpdateContactGroup.UpdateContactGroupFunc();

        CardLayout c = (CardLayout) mainCardLayout.getLayout();
        c.show(mainCardLayout, "chatGroupManage");

    }//GEN-LAST:event_backBtn2ActionPerformed

    private void btn_browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_browseActionPerformed
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            lbl_file.setText(f.getName());

        }
    }//GEN-LAST:event_btn_browseActionPerformed

    BufferedImage img;
    String imgstr;
    private void browsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browsBtnActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getPath();
            try {
                img = ImageIO.read(new File(path));
                imgstr = ImageBase64Converter.encodeToString(img, "png");
            } catch (IOException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_browsBtnActionPerformed

    private void groupChatBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupChatBtnActionPerformed
        // TODO add your handling code here:

        CardLayout c = (CardLayout) contactListPanel.getLayout();
        c.show(contactListPanel, "groupContactList");
        // ChatGroupManageGUILoder gui=new ChatGroupManageGUILoder();

    }//GEN-LAST:event_groupChatBtnActionPerformed

    private void contactChatBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactChatBtnActionPerformed
        // TODO add your handling code here:
        CardLayout c = (CardLayout) contactListPanel.getLayout();
        c.show(contactListPanel, "contactList");
    }//GEN-LAST:event_contactChatBtnActionPerformed

    private void contactGroupCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactGroupCreateActionPerformed
        // TODO add your handling code here:
        createContactGroup.setActionCommand("Create Group");
        createBtn();
        UpdateContactGroup.UpdateContactGroupFunc();
        CardLayout c = (CardLayout) mainCardLayout.getLayout();
        c.show(mainCardLayout, "contactGroup");
    }//GEN-LAST:event_contactGroupCreateActionPerformed

    private void backBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBTN1ActionPerformed
        UpdateContactGroup.UpdateContactGroupFunc();
        createContactGroup.setActionCommand("Create Group");
        createBtn();
        UpdateContactGroup.UpdateContactGroupFunc();

        CardLayout c = (CardLayout) mainCardLayout.getLayout();
        c.show(mainCardLayout, "contactGroupManage");
    }//GEN-LAST:event_backBTN1ActionPerformed

    private void createContactGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createContactGroupActionPerformed
        // TODO add your handling code here:
        if (!evt.getActionCommand().equals("Save")) {

            ContactGroupMethods.createBtnAction();

        } else {

            ContactGroupMethods.saveBtnAction();
        }
        createContactGroup.setActionCommand("Create Group");
        createBtn();
        UpdateContactGroup.UpdateContactGroupFunc();

        CardLayout c = (CardLayout) mainCardLayout.getLayout();
        c.show(mainCardLayout, "contactGroupManage");
    }//GEN-LAST:event_createContactGroupActionPerformed

    private void manageContactGroupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageContactGroupBtnActionPerformed
        // TODO add your handling code here:

        ContactGroupMethods groupChatMethods = new ContactGroupMethods(mainCardLayout, contactGroupManage, contactGroup, contactGroupName, createContactGroup);
        new UpdateContactGroup(mainCardLayout, contactGroupMembers, contactGroupManagePanel);
        CardLayout c = (CardLayout) mainCardLayout.getLayout();
        c.show(mainCardLayout, "contactGroupManage");

    }//GEN-LAST:event_manageContactGroupBtnActionPerformed

    private void backBTN500ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBTN500ActionPerformed
        // TODO add your handling code here:
        CardLayout c = (CardLayout) mainCardLayout.getLayout();
        c.show(mainCardLayout, "chatPanel");
    }//GEN-LAST:event_backBTN500ActionPerformed

    private void backBTN7888ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBTN7888ActionPerformed
        // TODO add your handling code here:
        createContactGroup.setActionCommand("Create Group");
        createBtn();
        UpdateContactGroup.UpdateContactGroupFunc();
        CardLayout c = (CardLayout) mainCardLayout.getLayout();
        c.show(mainCardLayout, "chatPanel");
    }//GEN-LAST:event_backBTN7888ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChatControls;
    private javax.swing.JMenu aboutMenu;
    private javax.swing.JButton addFriend;
    private javax.swing.JButton backBTN1;
    private javax.swing.JButton backBTN500;
    private javax.swing.JButton backBTN7888;
    private javax.swing.JButton backBtn2;
    private javax.swing.JButton browsBtn;
    private javax.swing.JButton btn_browse;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JPanel centerLapels;
    public javax.swing.JTextArea chatArea;
    private javax.swing.JPanel chatGroup;
    private javax.swing.JButton chatGroupCreate;
    private javax.swing.JPanel chatGroupHolder;
    private javax.swing.JPanel chatGroupManage;
    private javax.swing.JPanel chatGroupManagePanel;
    private javax.swing.JPanel chatGroupMembers;
    public javax.swing.JTextField chatGroupName;
    private javax.swing.JScrollPane chatGroupScroll;
    private javax.swing.JPanel chatPanel;
    private javax.swing.JButton confirmSignUpBtn;
    private javax.swing.JButton contactChatBtn;
    private javax.swing.JPanel contactGroup;
    private javax.swing.JButton contactGroupCreate;
    private javax.swing.JPanel contactGroupManage;
    private javax.swing.JPanel contactGroupManagePanel;
    private javax.swing.JPanel contactGroupMembers;
    public javax.swing.JTextField contactGroupName;
    private javax.swing.JPanel contactList;
    private javax.swing.JPanel contactListHolder;
    private javax.swing.JPanel contactListPanel;
    private javax.swing.JScrollPane contactListScroll;
    private javax.swing.JComboBox<String> countryList;
    private javax.swing.JButton createChatGoup;
    private javax.swing.JButton createContactGroup;
    private javax.swing.JLabel editLabel;
    private javax.swing.JTextField emailText;
    private javax.swing.JLabel etx;
    public javax.swing.JRadioButton femaleRadio;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JTextField firstNameText;
    private javax.swing.JButton groupChatBtn;
    private javax.swing.JPanel groupList;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField lastNameText;
    private javax.swing.JLabel lbl_file;
    private javax.swing.JPanel leftLapels;
    private javax.swing.JButton loginBtn;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPanel m;
    private javax.swing.JPanel mainCardLayout;
    private javax.swing.JRadioButton maleRadio;
    private javax.swing.JButton manageContactGroupBtn;
    private javax.swing.JLabel myImage;
    private javax.swing.JPanel notePanel;
    private javax.swing.JPasswordField passwordConfText;
    private javax.swing.JPasswordField passwordText;
    private javax.swing.JPasswordField passwordTxt;
    private javax.swing.JButton sendMsgBtn;
    private javax.swing.JTextArea sendMsgText;
    private javax.swing.JButton signUpBtn;
    private javax.swing.JPanel signUpPanel;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JMenu theme;
    private javax.swing.JPanel topTitile;
    private javax.swing.JTextField userNameText;
    private javax.swing.JTextField userNameTxt;
    // End of variables declaration//GEN-END:variables
String xx;
    User y;
  
    public void handleMessage(MessageInt msg) throws RemoteException, NotBoundException, MalformedURLException {
        String str = null;
        String chatsession = chatArea.getText();
        str = msg.isDelivered()+"Devlivered"+msg.getMessageId()+msg.getMessageSender() + " : " + msg.getMessage();
        if (str != null) {
            //System.out.println(str);
            new Msg(chatArea);
           MainCache.addMsg(msg);
           // System.err.println("pp");
            
            //chatArea.setText(chatsession + "\n" + str);
        }
        if (msg.isFile()) {
            System.out.println("receiving file ...");
            DataReceiver dr = new DataReceiver(repo + File.separator + msg.getMessage());
            DataSenderInt ds = (DataSenderInt) Naming.lookup("//localhost:4444/fsender");
            ds.exportData(dr);
        }
    }

    @Override
    public void run() {
        MessageInt msg = null;
        do {
            try {
               // System.out.println("messages : " + ch.getMessagesSize());
                String chatsession = chatArea.getText();
                msg = ch.retrieveMessage(crruid);
                if (msg != null) {
                    handleMessage(msg);
                }
                Thread.sleep(1);
            } catch (RemoteException | InterruptedException | NotBoundException | MalformedURLException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

}
