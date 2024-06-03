package app.ClientInternals;

import app.DataObjectModels.ProtectionObject;
import app.DataObjectModels.Room;
import app.Database.RDFCon;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class ConfigWindow extends JInternalFrame {

    ProtectionObject po;

    public ConfigWindow(ProtectionObject po) {
        setClosable(true);
        setResizable(true);
        setIconifiable(false);

        this.po = po;

        initComponents();

        ArrayList<String>[] a;
        try (RDFCon rdf = new RDFCon()) {
            a = rdf.GetTrebAndRec(po);
        }

        ArrayList<String> treb = a[0];
        String trebString = "";
        for (String tr : treb) {
            trebString += tr + " | ";
        }
        ArrayList<String> rec = a[1];
        String recString = "";
        for (String rc : rec) {
            recString += rc + " | ";
        }
        String text = "Конфигурация объекта защиты " + po.name + " состоит из следующих систем противопожарной защиты:\n"
                + "- Системы оповещения и управления эвакуацией: " + po.SOUE + "\n"
                + "(требуемое оборудование: " + trebString + "\nрекомендуемое: " + recString + ")\n"
                + "- Наружное пожарное водоснабжение: " + po.NPV + "\n"
                + "- Внутренний пожарный водопровод: " + po.VPV + "\n"
                + "К тому же, для каждой из комнат установлены нижеперечисленные системы системы.\n";
        for (Room room : po.roomList) {
            text = text + room.name + ":\n"
                    + room.categories.ognetush.get(room.categories.selectedOgnetush) + "\n"
                    + "Система пожарной сигнализации " + room.categories.sps.get(room.categories.selectedSps) + "\n"
                    + "Автоматическая установка пожаротушения: " + room.categories.aup.get(room.categories.selectedAup) + "\n";
        }
        jTextArea1.setText(text);
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jName.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel1.setText("Объект защиты");
        jName.setText(po.name);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea1.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    private javax.swing.JLabel jName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
}
