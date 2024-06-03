package app.ClientInternals.POMAdders;

import app.ClientInternals.POM;
import app.DataObjectModels.ProtectionObject;
import app.DataObjectModels.Room;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.text.ParseException;

public class AR extends JInternalFrame {

    private JButton set = new JButton();
    private JTextField name = new JTextField();
    private JLabel nameLabel = new JLabel();
    private JLabel heightLabel;
    private JLabel widthLabel;
    private JLabel lenghtLabel;
    private JLabel maxVoltageLabel;
    private JFormattedTextField  height;
    private JFormattedTextField  width;
    private JFormattedTextField  lenght;
    private JFormattedTextField  maxVoltage;

    private POM pom;
    private ProtectionObject o;
    private Room r;
    public AR(POM pom, ProtectionObject o) {
        initComponents();

        set.setEnabled(false);
        this.pom = pom;
        this.o = o;
        name.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                set.setEnabled(name.getText().length() > 0);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                set.setEnabled(name.getText().length() > 0);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                set.setEnabled(name.getText().length() > 0);
            }
        });

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public AR(POM pom, ProtectionObject o, Room r){
        this(pom, o);
        set.setText("Изменить");

        this.r = r;
        name.setText(r.name);
        height.setText(String.format("%08.3f",r.height).replaceAll(",", "."));
        width.setText(String.format("%08.3f",r.width).replaceAll(",", "."));
        lenght.setText(String.format("%08.3f",r.lenght).replaceAll(",", "."));
        maxVoltage.setText(String.format("%07d",r.maxVoltage));
        name.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                set.setEnabled(name.getText().length() > 0);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                set.setEnabled(name.getText().length() > 0);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                set.setEnabled(name.getText().length() > 0);
            }
        });
    }

    private MaskFormatter getMaskFormatter(String format) {
        MaskFormatter mask = null;
        try {
            mask = new MaskFormatter(format);
            mask.setPlaceholderCharacter('0');
        }catch (ParseException ex) {
            ex.printStackTrace();
        }
        return mask;
    }

    private void initComponents() {
        setMinimumSize(new java.awt.Dimension(500, 50));
        setClosable(true);
        setTitle("Помещение");

        nameLabel.setText("Название помещения");
        set.setText("Добавить");
        set.addActionListener(this::setPress);

        heightLabel = new javax.swing.JLabel();
        widthLabel = new javax.swing.JLabel();
        lenghtLabel = new javax.swing.JLabel();
        maxVoltageLabel = new javax.swing.JLabel();
        height = new JFormattedTextField (getMaskFormatter("####.###"));
        width = new JFormattedTextField (getMaskFormatter("####.###"));
        lenght = new JFormattedTextField (getMaskFormatter("####.###"));
        maxVoltage = new JFormattedTextField (getMaskFormatter("#######"));

        heightLabel.setText("Высота помещения");

        widthLabel.setText("Ширина помещения");

        lenghtLabel.setText("Длина помещения");

        maxVoltageLabel.setText("Эл.напряжение");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(nameLabel)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(heightLabel)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(height))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(widthLabel)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(width))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lenghtLabel)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lenght))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(maxVoltageLabel)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(maxVoltage)))
                                        .addComponent(set))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nameLabel)
                                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(heightLabel)
                                        .addComponent(height, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(widthLabel)
                                        .addComponent(width, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lenghtLabel)
                                        .addComponent(lenght, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(maxVoltageLabel)
                                        .addComponent(maxVoltage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(set)
                                .addContainerGap())
        );

        pack();
    }

    private void setPress(ActionEvent e) {
        if(set.getText().equals("Добавить")){
            Room r = new Room(name.getText());
            r.height = Double.parseDouble(height.getText());
            r.width = Double.parseDouble(width.getText());
            r.lenght = Double.parseDouble(lenght.getText());
            r.area = r.width * r.lenght;
            r.volume = r.area * r.height;
            r.maxVoltage = Integer.parseInt(maxVoltage.getText());
            o.roomList.add(r);
            this.pom.roomModel.add(this.pom.roomModel.size(), r);
        }else if (set.getText().equals("Изменить")) {
            this.r.name = name.getText();
            this.r.height = Double.parseDouble(height.getText());
            this.r.width = Double.parseDouble(width.getText());
            this.r.lenght = Double.parseDouble(lenght.getText());
            this.r.area = this.r.width * this.r.lenght;
            this.r.volume = this.r.area * this.r.height;
            this.r.maxVoltage = Integer.parseInt(maxVoltage.getText());
        }
        this.dispose();
    }
}
