package app.ClientInternals;

import app.BadBase;
import app.ClientInternals.POMAdders.*;
import app.DataObjectModels.*;
import app.Database.RDFCon;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class POM extends JInternalFrame {
    private javax.swing.JButton AddObj;
    private javax.swing.JButton AddRoom;
    private javax.swing.JButton AddMat;
    private javax.swing.JButton DelObj;
    private javax.swing.JButton DelRoom;
    private javax.swing.JButton DelMat;
    private javax.swing.JButton EditObj;
    private javax.swing.JButton EditRoom;
    private javax.swing.JButton Calculate;
    private javax.swing.JScrollPane objectScrollPane;
    private javax.swing.JScrollPane roomScrollPane;
    private javax.swing.JScrollPane materialScrollPane;

    private javax.swing.JList<Material> materialList;
    private javax.swing.JList<ProtectionObject> objectList;
    private javax.swing.JList<Room> roomList;
    public DefaultListModel<Material> materialModel = new DefaultListModel<>();
    public DefaultListModel<ProtectionObject> objectModel = new DefaultListModel<>();
    public DefaultListModel<Room> roomModel = new DefaultListModel<>();

    public POM() {
        initComponents();

        setClosable(true);
        setIconifiable(false);
        setTitle("Управление объектом защиты");

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

                    }

                    @Override
                    public void internalFrameDeactivated(InternalFrameEvent e) {
                        CalculateActionPerformed(new ActionEvent(e, 0, ""));
                    }
                }
        );

        //objectModel.add(0, BadBase.test());
    }

    private void initComponents() {

        AddObj = new javax.swing.JButton();
        EditObj = new javax.swing.JButton();
        DelObj = new javax.swing.JButton();
        AddRoom = new javax.swing.JButton();
        DelRoom = new javax.swing.JButton();
        EditRoom = new javax.swing.JButton();
        AddMat = new javax.swing.JButton();
        DelMat = new javax.swing.JButton();
        Calculate = new javax.swing.JButton();


        objectScrollPane = new javax.swing.JScrollPane();
        objectList = new javax.swing.JList<>(objectModel);
        roomScrollPane = new javax.swing.JScrollPane();
        roomList = new javax.swing.JList<>(roomModel);
        materialScrollPane = new javax.swing.JScrollPane();
        materialList = new javax.swing.JList<>(materialModel);

        AddObj.setText("Добавить объект защиты");
        AddObj.addActionListener(this::AddObjActionPerformed);

        EditObj.setText("Изменить объет защиты");
        EditObj.addActionListener(this::EditObjActionPerformed);

        DelObj.setText("Удалить объект защиты");
        DelObj.addActionListener(this::DelObjActionPerformed);

        AddRoom.setText("Добавить помещение");
        AddRoom.addActionListener(this::AddRoomActionPerformed);

        DelRoom.setText("Удалить помещение");
        DelRoom.addActionListener(this::DelRoomActionPerformed);

        EditRoom.setText("Изменить помещение");
        EditRoom.addActionListener(this::EditRoomActionPerformed);

        AddMat.setText("Добавить материал");
        AddMat.addActionListener(this::AddMatActionPerformed);

        DelMat.setText("Удалить материал");
        DelMat.addActionListener(this::DelMatActionPerformed);

        Calculate.setText("Вычислить категории");
        Calculate.addActionListener(this::CalculateActionPerformed);

        objectList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        objectList.addListSelectionListener(this::listObjectsValueChanged);
        objectScrollPane.setViewportView(objectList);

        roomList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        roomList.addListSelectionListener(this::listRoomsValueChanged);
        roomScrollPane.setViewportView(roomList);

        materialList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        materialScrollPane.setViewportView(materialList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(AddObj, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(EditObj, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(DelObj, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(objectScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(DelRoom, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                                        .addComponent(roomScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(materialScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(6, 6, 6)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(DelMat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(AddMat, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(0, 0, Short.MAX_VALUE))))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(AddRoom, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                                                        .addComponent(EditRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                //.addComponent(Calculate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        ))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(AddObj)
                                        .addComponent(AddRoom)
                                        //.addComponent(Calculate)
                                )
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(EditObj)
                                        .addComponent(EditRoom)
                                        .addComponent(AddMat))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(DelObj)
                                        .addComponent(DelRoom)
                                        .addComponent(DelMat))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(materialScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                                        .addComponent(roomScrollPane)
                                        .addComponent(objectScrollPane)))
        );

        pack();
    }

    private void CalculateActionPerformed(java.awt.event.ActionEvent actionEvent) {
        ArrayList<ProtectionObject> AllPOs = Collections.list(this.objectModel.elements());
        try(RDFCon conn = new RDFCon()){
            for (ProtectionObject obj:AllPOs) {
                conn.Add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AddObjActionPerformed(java.awt.event.ActionEvent evt) {
        APO apo = new APO(this);
        this.getDesktopPane().add(apo);
        apo.setVisible(true);
    }

    private void DelObjActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            objectList.getSelectedValue().equals(null);

            if (JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить этот объект и всю информацию о нем?", "Удаление объекта защиты", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                objectModel.removeElement(objectList.getSelectedValue());
            }
        } catch (NullPointerException e) {
        }
    }

    private void listObjectsValueChanged(javax.swing.event.ListSelectionEvent evt) {
        try {
            objectList.getSelectedValue().equals(null);//проверка на null

            roomModel = new DefaultListModel<>();
            roomModel.addAll(objectList.getSelectedValue().roomList);
            roomList.setModel(roomModel);
        } catch (NullPointerException e) {
            roomModel = new DefaultListModel<>();
            roomList.setModel(roomModel);
        }
    }

    private void listRoomsValueChanged(javax.swing.event.ListSelectionEvent evt) {
        try {
            roomList.getSelectedValue().equals(null);//проверка на null

            materialModel = new DefaultListModel<>();
            materialModel.addAll(roomList.getSelectedValue().materialList);
            materialList.setModel(materialModel);
        } catch (NullPointerException e) {
            materialModel = new DefaultListModel<>();
            materialList.setModel(materialModel);
        }
    }

    private void AddRoomActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            objectList.getSelectedValue().equals(null);
            AR ar = new AR(this, objectList.getSelectedValue());
            this.getDesktopPane().add(ar);
            ar.setVisible(true);
        }catch (NullPointerException e){}
    }

    private void DelRoomActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            roomList.getSelectedValue().equals(null);
            objectList.getSelectedValue().equals(null);

            if (JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить это помещение и всю информацию о нем?", "Удаление помещения", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                try (RDFCon con = new RDFCon()){
                    con.DeleteLink(objectList.getSelectedValue(), roomList.getSelectedValue());
                    con.FullDelete(roomList.getSelectedValue());
                }
                objectList.getSelectedValue().roomList.remove(roomList.getSelectedValue());
                roomModel.removeElement(roomList.getSelectedValue());
            }
        } catch (NullPointerException e) {
        }
    }

    private void AddMatActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            roomList.getSelectedValue().equals(null);
            AM am = new AM(this, roomList.getSelectedValue());
            this.getDesktopPane().add(am);
            am.setVisible(true);
        }catch (NullPointerException e){}
    }

    private void DelMatActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            materialList.getSelectedValue().equals(null);
            roomList.getSelectedValue().equals(null);

            if (JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить этот материал из помещения?", "Удаление материала", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                try (RDFCon con = new RDFCon()){
                    con.DeleteLink(roomList.getSelectedValue(), materialList.getSelectedValue());
                }
                roomList.getSelectedValue().materialList.remove(materialList.getSelectedValue());
                materialModel.removeElement(materialList.getSelectedValue());
            }
        } catch (NullPointerException e) {
        }
    }

    private void EditObjActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            objectList.getSelectedValue().equals(null);

            APO apo = new APO(this, objectList.getSelectedValue());
            this.getDesktopPane().add(apo);
            apo.setVisible(true);
        } catch (NullPointerException e) {
        }
    }

    private void EditRoomActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            objectList.getSelectedValue().equals(null);
            roomList.getSelectedValue().equals(null);

            AR ar = new AR(this, objectList.getSelectedValue(), roomList.getSelectedValue());
            this.getDesktopPane().add(ar);
            ar.setVisible(true);
        } catch (NullPointerException e) {
        }
    }

}
