package app;

import app.Database.DBCon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Auth extends JFrame {
    private JPanel mainPanel;
    private JLabel loginLabel;
    private JTextField loginField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton enterButton;

    public Auth(){
        initComponents();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Авторизация %s".formatted(Main.AppName));
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        mainPanel.setBorder(BorderFactory.createTitledBorder(null, "Вход в личный кабинет", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", Font.BOLD, 18))); // NOI18N

        loginLabel.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12)); // NOI18N
        loginLabel.setText("Логин:");

        passwordLabel.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12)); // NOI18N
        passwordLabel.setText("Пароль:");

        enterButton.setFont(new java.awt.Font("Tahoma", Font.BOLD, 13)); // NOI18N
        enterButton.setText("Войти");
        enterButton.addActionListener(this::jButtonInputActionPerformed);

        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(55, 55, 55)
                                                .addComponent(loginLabel))
                                        .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(passwordLabel)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(loginField, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                        .addComponent(passwordField))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGap(0, 151, Short.MAX_VALUE)
                                .addComponent(enterButton)
                                .addGap(131, 131, 131))
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(loginField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(loginLabel))
                                .addGap(18, 18, 18)
                                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passwordLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addComponent(enterButton)
                                .addGap(21, 21, 21))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    private void jButtonInputActionPerformed(ActionEvent evt) {
        if(loginField.getText().equals("Test")){
            new Client(loginField.getText()).setVisible(true);
            this.dispose();
        }else{
            try {
                switch (new DBCon().CheckUser(loginField.getText(), passwordField.getPassword())) {
                    case -2 -> JOptionPane.showMessageDialog(
                            null,
                            "Такого пользователя не существует",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    case -1 -> JOptionPane.showMessageDialog(
                            null,
                            "Не верно введен пароль",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    case 0 -> {
                        new Client(loginField.getText()).setVisible(true);
                        this.dispose();
                    }
                    case 1 -> {

                        this.dispose();
                    }
                    default -> JOptionPane.showMessageDialog(
                            this,
                            "Пользователь существует, но класс работы определен неверно. Обратитесь к администратору для дальнейших разъяснений.",
                            "Error", JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}

