package app;

import app.ClientInternals.*;

import javax.swing.*;
import java.awt.*;

public class Client extends JFrame {

    private javax.swing.JButton contactButton;
    private javax.swing.JButton pomButton;
    private javax.swing.JButton fenButton;
    private javax.swing.JButton fmButton;
    private javax.swing.JButton occButton;
    private javax.swing.JLabel loginLabel;

    POM pom;
    FEN fen;
    FM fm;
    OCC occ;

    public Client(String login) {
        initComponents();
        //fenButton.setEnabled(false);

        loginLabel.setText(login);
        pomButton.setText("<html><center>"+"Управление"+"<br>"+"объектом"+"<br>"+"защиты"+"</center></html>");
        contactButton.setText("<html><center>"+"Сообщение"+"<br>"+"инженеру"+"</center></html>");
        fenButton.setText("<html><center>"+"Формирование"+"<br>"+"объяснительной"+"<br>"+"записки"+"</center></html>");
        fmButton.setText("<html><center>"+"Поиск"+"<br>"+"материалов"+"</center></html>");
        occButton.setText("<html><center>"+"Расчет категории"+"<br>"+"объекта защиты"+"</center></html>");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client Fire System DSS");
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(680,480));
    }

    private void initComponents() {
        JPanel buttonPanel = new JPanel();
        loginLabel = new javax.swing.JLabel();
        pomButton = new javax.swing.JButton();
        contactButton = new javax.swing.JButton();
        fenButton = new javax.swing.JButton();
        fmButton = new javax.swing.JButton();
        occButton = new javax.swing.JButton();
        JDesktopPane desktopPane = new JDesktopPane();

        pom = new POM();
        fen = new FEN();
        fm = new FM();
        occ = new OCC();

        pom.setDefaultCloseOperation(HIDE_ON_CLOSE);
        fen.setDefaultCloseOperation(HIDE_ON_CLOSE);
        fm.setDefaultCloseOperation(HIDE_ON_CLOSE);
        occ.setDefaultCloseOperation(HIDE_ON_CLOSE);

        desktopPane.add(pom);
        desktopPane.add(fen);
        desktopPane.add(fm);
        desktopPane.add(occ);

        buttonPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buttonPanel.setMaximumSize(new java.awt.Dimension(120, 32767));

        loginLabel.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14)); // NOI18N
        loginLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginLabel.setText("LoginName");
        loginLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        loginLabel.setFocusable(false);
        loginLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loginLabel.setMaximumSize(new java.awt.Dimension(100, 23));
        loginLabel.setMinimumSize(new java.awt.Dimension(100, 23));
        loginLabel.setPreferredSize(new java.awt.Dimension(100, 23));

        pomButton.addActionListener(e->pom.setVisible(true));
        contactButton.addActionListener(this::contactActionPerformed);
        fenButton.addActionListener(e->fen.setVisible(true));
        fmButton.addActionListener(e->fm.setVisible(true));
        occButton.addActionListener(e->occ.setVisible(true));

        javax.swing.GroupLayout buttonGroupLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonGroupLayout);
        buttonGroupLayout.setHorizontalGroup(
                buttonGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pomButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(loginLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(contactButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        //.addComponent(fenButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fmButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(occButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        buttonGroupLayout.setVerticalGroup(
                buttonGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(buttonGroupLayout.createSequentialGroup()
                                .addComponent(loginLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pomButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fmButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(occButton)
                                //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                //.addComponent(fenButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 325, Short.MAX_VALUE)
                                .addComponent(contactButton))
        );

        desktopPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
                desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1094, Short.MAX_VALUE)
        );
        desktopPaneLayout.setVerticalGroup(
                desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(desktopPane)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(desktopPane)
                                .addContainerGap())
        );

        pack();
    }

    private void contactActionPerformed(java.awt.event.ActionEvent evt) {
        new Send(this, false).setVisible(true);
    }
}
