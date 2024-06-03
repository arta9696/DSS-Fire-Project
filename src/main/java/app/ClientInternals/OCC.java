package app.ClientInternals;

import app.DataObjectModels.*;
import app.Database.RDFCon;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.event.ActionListener;
import java.util.*;

public class OCC extends JInternalFrame {

    private javax.swing.JButton soueButton;
    private javax.swing.JButton npvButton;
    private javax.swing.JButton ognetushButton;
    private javax.swing.JButton spsButton;
    private javax.swing.JButton vpvButton;
    private javax.swing.JButton aupButton;
    private javax.swing.JButton formConfigButton;
    private javax.swing.JComboBox<String> soueComboBox;
    private javax.swing.JComboBox<String> npvComboBox;
    private javax.swing.JComboBox<String> ognetushComboBox;
    private javax.swing.JComboBox<String> spsComboBox;
    private javax.swing.JComboBox<String> vpvComboBox;
    private javax.swing.JComboBox<String> aupComboBox;
    private JComboBox<ProtectionObject> objectJComboBox;
    private javax.swing.JComboBox<Room> roomJComboBox;

    public DefaultComboBoxModel<ProtectionObject> POs = new DefaultComboBoxModel<>();
    public DefaultComboBoxModel<String> soue = new DefaultComboBoxModel<>();
    public DefaultComboBoxModel<String> npv = new DefaultComboBoxModel<>();
    public DefaultComboBoxModel<String> ognetush = new DefaultComboBoxModel<>();
    public DefaultComboBoxModel<String> sps = new DefaultComboBoxModel<>();
    public DefaultComboBoxModel<String> vpv = new DefaultComboBoxModel<>();
    public DefaultComboBoxModel<String> aup = new DefaultComboBoxModel<>();
    public DefaultComboBoxModel<Room> Rs = new DefaultComboBoxModel<>();

    public OCC() {
        initComponents();

        setClosable(true);
        setIconifiable(false);
        setTitle("Расчет категории объекта защиты");

        addInternalFrameListener(
                new InternalFrameListener() {
                    @Override
                    public void internalFrameOpened(InternalFrameEvent e) {

                    }
                    @Override
                    public void internalFrameClosing(InternalFrameEvent e) {

                    }
                    @Override
                    public void internalFrameClosed(InternalFrameEvent e) {

                    }
                    @Override
                    public void internalFrameIconified(InternalFrameEvent e) {

                    }
                    @Override
                    public void internalFrameDeiconified(InternalFrameEvent e) {

                    }
                    @Override
                    public void internalFrameActivated(InternalFrameEvent e) {
                        POs.removeAllElements();
                        ArrayList<ProtectionObject> AllPOs = Collections.list(((POM)(Arrays.stream(getDesktopPane().getAllFrames()).filter(x->x.getClass()==POM.class).findAny().get())).objectModel.elements());
                        POs.addAll(AllPOs);
                        soueButton.setEnabled(false);
                        npvButton.setEnabled(false);
                        ognetushButton.setEnabled(false);
                        spsButton.setEnabled(false);
                        vpvButton.setEnabled(false);
                        aupButton.setEnabled(false);
                        formConfigButton.setEnabled(false);
                    }

                    @Override
                    public void internalFrameDeactivated(InternalFrameEvent e) {

                    }
                }
        );

        objectJComboBox.addActionListener(e -> {

            soue.removeAllElements();
            npv.removeAllElements();
            ognetush.removeAllElements();
            sps.removeAllElements();
            vpv.removeAllElements();
            aup.removeAllElements();
            Rs.removeAllElements();

            if(objectJComboBox.getSelectedIndex() != -1){
                ProtectionObject po = (ProtectionObject)(Objects.requireNonNull(objectJComboBox.getSelectedItem()));
                Rs.addAll(po.roomList);
                for (Room room: po.roomList) {
                    room.categories = new RoomCategories();
                }
                Rs.setSelectedItem(po.roomList.get(0));

                soueButton.setEnabled(true);
                npvButton.setEnabled(true);
                ognetushButton.setEnabled(true);
                spsButton.setEnabled(false);
                vpvButton.setEnabled(false);
                aupButton.setEnabled(false);
                formConfigButton.setEnabled(false);
            }
        });
        roomJComboBox.addActionListener(e -> {
            if(roomJComboBox.getSelectedIndex() != -1){
                Room room = (Room)(Objects.requireNonNull(roomJComboBox.getSelectedItem()));
                ognetush.removeAllElements();
                ognetush.addAll(room.categories.ognetush);
                ognetushComboBox.setSelectedIndex(room.categories.selectedOgnetush);
                sps.removeAllElements();
                sps.addAll(room.categories.sps);
                spsComboBox.setSelectedIndex(room.categories.selectedSps);
                aup.removeAllElements();
                aup.addAll(room.categories.aup);
                aupComboBox.setSelectedIndex(room.categories.selectedAup);
            }
        });

        soueComboBox.addActionListener(e -> {
            if(soueComboBox.getSelectedIndex() != -1){
                ProtectionObject po = (ProtectionObject)(Objects.requireNonNull(objectJComboBox.getSelectedItem()));
                po.SOUE = (String) soue.getSelectedItem();
                spsButton.setEnabled(true);
            }
        });
        npvComboBox.addActionListener(e -> {
            if(npvComboBox.getSelectedIndex() != -1){
                ProtectionObject po = (ProtectionObject)(Objects.requireNonNull(objectJComboBox.getSelectedItem()));
                po.NPV = (String) npv.getSelectedItem();
                vpvButton.setEnabled(true);
            }
        });
        ActionListener aupListener = e -> {
            if(spsComboBox.getSelectedIndex() != -1 && vpvComboBox.getSelectedIndex() != -1){
                aupButton.setEnabled(true);
            }
        };
        spsComboBox.addActionListener(e -> {
            if(spsComboBox.getSelectedIndex() != -1){
                Room r = (Room)(Objects.requireNonNull(roomJComboBox.getSelectedItem()));
                r.categories.selectedSps = spsComboBox.getSelectedIndex();
                aupListener.actionPerformed(e);
            }
        });
        vpvComboBox.addActionListener(e -> {
            if(vpvComboBox.getSelectedIndex() != -1){
                ProtectionObject po = (ProtectionObject)(Objects.requireNonNull(objectJComboBox.getSelectedItem()));
                po.VPV = (String) vpv.getSelectedItem();
                aupListener.actionPerformed(e);
            }
        });
        ActionListener formListener = e -> {
            if(aupComboBox.getSelectedIndex()!=-1&&ognetushComboBox.getSelectedIndex()!=-1){
                boolean check = true;
                ProtectionObject po = (ProtectionObject)(Objects.requireNonNull(objectJComboBox.getSelectedItem()));
                for (Room room: po.roomList) {
                    check = room.categories.selectedOgnetush!=-1&&room.categories.selectedSps!=-1&&room.categories.selectedAup!=-1;
                    if(!check){
                        break;
                    }
                }
                if(check) formConfigButton.setEnabled(true);
            }
        };
        aupComboBox.addActionListener(e -> {
            if(aupComboBox.getSelectedIndex()!=-1){
                Room r = (Room)(Objects.requireNonNull(roomJComboBox.getSelectedItem()));
                r.categories.selectedAup = aupComboBox.getSelectedIndex();
                formListener.actionPerformed(e);
            }
        });
        ognetushComboBox.addActionListener(e -> {
            if(ognetushComboBox.getSelectedIndex()!=-1){
                Room r = (Room)(Objects.requireNonNull(roomJComboBox.getSelectedItem()));
                r.categories.selectedOgnetush = ognetushComboBox.getSelectedIndex();
                formListener.actionPerformed(e);
            }
        });
    }

    private void initComponents() {

        soueButton = new javax.swing.JButton();
        soueButton.setEnabled(false);
        npvButton = new javax.swing.JButton();
        npvButton.setEnabled(false);
        ognetushButton = new javax.swing.JButton();
        ognetushButton.setEnabled(false);
        spsButton = new javax.swing.JButton();
        spsButton.setEnabled(false);
        vpvButton = new javax.swing.JButton();
        vpvButton.setEnabled(false);
        aupButton = new javax.swing.JButton();
        aupButton.setEnabled(false);
        soueComboBox = new javax.swing.JComboBox<>();
        soueComboBox.setEnabled(false);
        npvComboBox = new javax.swing.JComboBox<>();
        npvComboBox.setEnabled(false);
        ognetushComboBox = new javax.swing.JComboBox<>();
        ognetushComboBox.setEnabled(false);
        spsComboBox = new javax.swing.JComboBox<>();
        spsComboBox.setEnabled(false);
        vpvComboBox = new javax.swing.JComboBox<>();
        vpvComboBox.setEnabled(false);
        aupComboBox = new javax.swing.JComboBox<>();
        aupComboBox.setEnabled(false);
        formConfigButton = new javax.swing.JButton();
        formConfigButton.setEnabled(false);
        objectJComboBox = new JComboBox<ProtectionObject>();
        roomJComboBox = new javax.swing.JComboBox<>();

        soueButton.setText("СОУЭ");
        soueButton.addActionListener(this::jButton1ActionPerformed);

        npvButton.setText("НПВ");
        npvButton.addActionListener(this::jButton2ActionPerformed);

        ognetushButton.setText("Огнетушители");
        ognetushButton.addActionListener(this::jButton3ActionPerformed);

        spsButton.setText("СПС");
        spsButton.addActionListener(this::jButton4ActionPerformed);

        vpvButton.setText("ВПВ");
        vpvButton.addActionListener(this::jButton5ActionPerformed);

        aupButton.setText("АУП");
        aupButton.addActionListener(this::jButton6ActionPerformed);

        soueComboBox.setModel(soue);

        npvComboBox.setModel(npv);

        ognetushComboBox.setModel(ognetush);

        spsComboBox.setModel(sps);

        vpvComboBox.setModel(vpv);

        aupComboBox.setModel(aup);

        formConfigButton.setText("Сформировать конфигурацию");
        formConfigButton.addActionListener(this::jButton7ActionPerformed);

        objectJComboBox.setModel(POs);
        roomJComboBox.setModel(Rs);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(roomJComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(objectJComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(formConfigButton, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(ognetushButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(npvButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(soueButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(spsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(vpvButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(aupButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(soueComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(npvComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(ognetushComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(spsComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(vpvComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(aupComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(objectJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(soueButton)
                                        .addComponent(soueComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(npvButton)
                                        .addComponent(npvComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(vpvButton)
                                        .addComponent(vpvComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(roomJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(ognetushButton)
                                        .addComponent(ognetushComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(spsButton)
                                        .addComponent(spsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(aupButton)
                                        .addComponent(aupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(formConfigButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        soue.removeAllElements();
        RDFCon rdf = new RDFCon();
        soue.addAll(rdf.GetSoue((ProtectionObject)(this.POs.getSelectedItem())));
        rdf.close();
        soueComboBox.setSelectedIndex(-1);

        soueComboBox.setEnabled(true);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        npv.removeAllElements();
        RDFCon rdf = new RDFCon();
        npv.addAll(rdf.GetNpv((ProtectionObject)(this.POs.getSelectedItem())));
        rdf.close();
        npvComboBox.setSelectedIndex(-1);

        npvComboBox.setEnabled(true);
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        ProtectionObject po = (ProtectionObject)(Objects.requireNonNull(objectJComboBox.getSelectedItem()));
        RDFCon rdf = new RDFCon();
        for (Room room: po.roomList) {
            room.categories.ognetush = rdf.GetOgnetush(room);
            room.categories.selectedOgnetush = -1;
            ognetushComboBox.setEnabled(true);
        }
        rdf.close();
        Room r = (Room)(Objects.requireNonNull(roomJComboBox.getSelectedItem()));
        ognetush.removeAllElements();
        ognetush.addAll(r.categories.ognetush);
        ognetushComboBox.setSelectedIndex(-1);
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        ProtectionObject po = (ProtectionObject)(Objects.requireNonNull(objectJComboBox.getSelectedItem()));
        RDFCon rdf = new RDFCon();
        for (Room room: po.roomList) {
            room.categories.sps = rdf.GetSps(po, room);
            room.categories.selectedSps = -1;
            spsComboBox.setEnabled(true);
        }
        rdf.close();
        Room r = (Room)(Objects.requireNonNull(roomJComboBox.getSelectedItem()));
        sps.removeAllElements();
        sps.addAll(r.categories.sps);
        spsComboBox.setSelectedIndex(-1);
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        vpv.removeAllElements();
        RDFCon rdf = new RDFCon();
        vpv.addAll(rdf.GetVpv((ProtectionObject)(this.POs.getSelectedItem())));
        rdf.close();
        vpvComboBox.setSelectedIndex(-1);

        vpvComboBox.setEnabled(true);
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        ProtectionObject po = (ProtectionObject)(Objects.requireNonNull(objectJComboBox.getSelectedItem()));
        RDFCon rdf = new RDFCon();
        for (Room room: po.roomList) {
            room.categories.aup = rdf.GetAup(room);
            room.categories.selectedAup = -1;
            aupComboBox.setEnabled(true);
        }
        rdf.close();
        Room r = (Room)(Objects.requireNonNull(roomJComboBox.getSelectedItem()));
        aup.removeAllElements();
        aup.addAll(r.categories.aup);
        aupComboBox.setSelectedIndex(-1);
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        ProtectionObject po = ((ProtectionObject) POs.getSelectedItem());

        ConfigWindow configWindow = new ConfigWindow(po);
        configWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getDesktopPane().add(configWindow);
        configWindow.setVisible(true);
    }
}
