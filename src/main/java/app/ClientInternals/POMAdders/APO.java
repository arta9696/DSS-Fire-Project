package app.ClientInternals.POMAdders;

import app.ClientInternals.POM;
import app.DataObjectModels.ProtectionObject;
import app.Database.RDFCon;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.text.ParseException;

public class APO extends JInternalFrame {

    private JButton set = new JButton();
    private JTextField name = new JTextField();
    private JLabel nameLabel = new JLabel();
    private JLabel heightLabel;
    private JLabel widthLabel;
    private JLabel lenghtLabel;
    private JLabel maxPeopleCountLabel;
    private JLabel floorCountLabel;
    private JLabel funcClassLabel;
    private JFormattedTextField  height;
    private JFormattedTextField  width;
    private JFormattedTextField  lenght;
    private JFormattedTextField  maxPeopleCount;
    private JFormattedTextField  floorCount;
    private JComboBox<String> funcClassComboBox;
    private DefaultComboBoxModel<String> funcClass;

    private POM pom;
    private ProtectionObject o;
    public APO(POM pom) {
        initComponents();

        set.setEnabled(false);
        this.pom = pom;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

    public APO(POM pom, ProtectionObject o){
        this(pom);
        set.setText("Изменить");

        this.o = o;
        name.setText(o.name);
        height.setText(String.format("%08.3f",o.height).replaceAll(",", "."));
        width.setText(String.format("%08.3f",o.width).replaceAll(",", "."));
        lenght.setText(String.format("%08.3f",o.lenght).replaceAll(",", "."));
        maxPeopleCount.setText(String.format("%07d",o.maxPeopleCount));
        floorCount.setText(String.format("%04d",o.floorCount));
        funcClassComboBox.setSelectedIndex(funcClass.getIndexOf(o.funcClass));
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
        setClosable(true);
        setTitle("Объект защиты");

        nameLabel.setText("Название объекта");
        set.setText("Добавить");
        set.addActionListener(this::setPress);

        heightLabel = new javax.swing.JLabel();
        widthLabel = new javax.swing.JLabel();
        lenghtLabel = new javax.swing.JLabel();
        maxPeopleCountLabel = new javax.swing.JLabel();
        floorCountLabel = new javax.swing.JLabel();
        height = new JFormattedTextField (getMaskFormatter("####.###"));
        width = new JFormattedTextField (getMaskFormatter("####.###"));
        lenght = new JFormattedTextField (getMaskFormatter("####.###"));
        maxPeopleCount = new JFormattedTextField (getMaskFormatter("#######"));
        floorCount = new JFormattedTextField (getMaskFormatter("####"));

        funcClassLabel = new JLabel();
        funcClassComboBox = new JComboBox<>();
        try (RDFCon con = new RDFCon()){
            funcClass = new DefaultComboBoxModel<>(con.ReadClass("Functional"));
        }catch (Exception e){
            e.printStackTrace();
        }
        funcClassComboBox.setModel(funcClass);

        heightLabel.setText("Высота объекта");

        widthLabel.setText("Ширина объекта");

        lenghtLabel.setText("Длина объекта");

        maxPeopleCountLabel.setText("Посещаемость объекта");

        floorCountLabel.setText("Количество этажей");

        funcClassLabel.setText("Функциональный класс");

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
                                                        .addComponent(funcClassLabel)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(funcClassComboBox))
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
                                                        .addComponent(maxPeopleCountLabel)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(maxPeopleCount))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(floorCountLabel)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(floorCount)))
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
                                        .addComponent(funcClassLabel)
                                        .addComponent(funcClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                        .addComponent(maxPeopleCountLabel)
                                        .addComponent(maxPeopleCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(floorCountLabel)
                                        .addComponent(floorCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(set)
                                .addContainerGap())
        );

        pack();
    }

    private void setPress(ActionEvent e) {
        if(set.getText().equals("Добавить")){
            ProtectionObject po = new ProtectionObject(name.getText());
            po.height = Double.parseDouble(height.getText());
            po.width = Double.parseDouble(width.getText());
            po.lenght = Double.parseDouble(lenght.getText());
            po.area = po.width * po.lenght;
            po.volume = po.area * po.height;
            po.maxPeopleCount = Integer.parseInt(maxPeopleCount.getText());
            po.floorCount = Integer.parseInt(floorCount.getText());
            po.funcClass = (String) funcClass.getSelectedItem();
            this.pom.objectModel.add(this.pom.objectModel.size(), po);
        } else if (set.getText().equals("Изменить")) {
            this.o.name = name.getText();
            this.o.height = Double.parseDouble(height.getText());
            this.o.width = Double.parseDouble(width.getText());
            this.o.lenght = Double.parseDouble(lenght.getText());
            this.o.area = this.o.width * this.o.lenght;
            this.o.volume = this.o.area * this.o.height;
            this.o.maxPeopleCount = Integer.parseInt(maxPeopleCount.getText());
            this.o.floorCount = Integer.parseInt(floorCount.getText());
            this.o.funcClass = (String) funcClass.getSelectedItem();
        }
        this.dispose();
    }
}
