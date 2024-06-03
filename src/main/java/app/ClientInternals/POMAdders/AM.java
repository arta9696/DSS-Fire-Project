package app.ClientInternals.POMAdders;

import app.ClientInternals.POM;
import app.DataObjectModels.Material;
import app.DataObjectModels.Room;
import app.Database.RDFCon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class AM extends JInternalFrame {
    private JButton set = new JButton();
    private JComboBox<Material> name = new JComboBox();
    DefaultComboBoxModel<Material> nameModel;
    private JLabel nameLabel = new JLabel();

    private JTextField jTextField1;
    private JButton jSearchButton;

    private POM pom;
    private Room r;

    public AM(POM pom, Room r) {
        initComponents();

        this.pom = pom;
        this.r = r;

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        setMinimumSize(new java.awt.Dimension(500, 50));
        setClosable(true);
        setTitle("Материал");

        nameLabel.setText("Материал");
//        name.setPreferredSize(new Dimension(100,50));
        name.setMinimumSize(new Dimension(500,50));
        nameModel = new DefaultComboBoxModel<>();
        try(RDFCon con = new RDFCon()){
            nameModel.addAll(Arrays.stream(con.ReadUnchangeableIndividuals("Material")).map(Material::new).toList());
        }
        name.setModel(nameModel);
        set.setText("Добавить");
        set.addActionListener(this::setPress);

        jSearchButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jSearchButton.setText("Поиск");
        jSearchButton.addActionListener(this::jSearchButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jSearchButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(nameLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(name))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(set))
                                ).addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nameLabel)
                                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(set)
                                .addContainerGap()
                        )
        );

        pack();
    }

    private void jSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Material> mat_names = new ArrayList<>();
        String search = jTextField1.getText();

        if(search.equals("")){
            name.setModel(nameModel);
            return;
        }

        try(RDFCon con = new RDFCon()) {
            for (String to_check : con.ReadUnchangeableIndividuals("Material")) {
                String searchSmall = search.toLowerCase();
                String searchLarge = search.toUpperCase();
                String searchMain = searchLarge.charAt(0)+searchSmall.substring(1);
                if (to_check.contains(searchSmall)) {
                    mat_names.add(new Material(to_check));
                }
                if (to_check.contains(searchLarge)) {
                    mat_names.add(new Material(to_check));
                }
                if (to_check.contains(searchMain)) {
                    mat_names.add(new Material(to_check));
                }
            }
        }

        DefaultComboBoxModel<Material> newModel = new DefaultComboBoxModel<>();
        newModel.addAll(mat_names);

        name.setModel(newModel);
    }

    private void setPress(ActionEvent e) {
        if(set.getText().equals("Добавить")){
            Material m = (Material) name.getSelectedItem();
            r.materialList.add(m);
            this.pom.materialModel.add(this.pom.materialModel.size(), m);
        }
        this.dispose();
    }
}
