package Student;


import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Frame implements ActionListener {
    JFrame frame = new JFrame();
    JComboBox combo_remove = new JComboBox();
    JComboBox combo_edit = new JComboBox();

    final String file_path = "data.csv";
    List<Student> students = HandleFile.read_csv(file_path);

    JLabel label_insert = new JLabel("Add a student");
    JTextField tf_add_name = new JTextField();
    JTextField tf_add_age = new JTextField();
    JLabel label_add_name = new JLabel("Name");
    JLabel label_add_age = new JLabel("Age");
    JButton btn_add = new JButton("OK");

    JLabel label_change = new JLabel("Edit a student");
    JTextField tf_edit_name = new JTextField();
    JTextField tf_edit_age = new JTextField();
    JLabel label_edit_id = new JLabel("Student ID");
    JLabel label_edit_name = new JLabel("New name");
    JLabel label_edit_age = new JLabel("New age");
    JButton btn_edit = new JButton("OK");

    JLabel label_remove = new JLabel("Remove a student");
    JButton btn_remove = new JButton("OK");
    JLabel label_remove_id = new JLabel("Student ID");

    JTable table = new JTable();
    JScrollPane scrollPane = new JScrollPane(table);
    DefaultTableModel table_model = new DefaultTableModel();

    ArrayList<String> choice = new ArrayList<>();

    Frame()
    {
        choice = get_all_ID(students);
        table();
        ui_add();
        ui_remove();
        ui_edit();
        add_func();

        remove_func();
        edit_func();
        create_table();
        setFrame();
    }

    public void setFrame()
    {
        JLabel title = new JLabel("STUDENTS INFORMATION");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        title.setBounds(200, 0, 500, 50);
        frame.add(title);
        frame.setSize(1200, 700);
        frame.setTitle("Menu");
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.add(label_insert);
        frame.add(label_add_name);
        frame.add(label_add_age);
        frame.add(tf_add_name);
        frame.add(tf_add_age);
        frame.add(btn_add);

        frame.add(label_edit_id);
        frame.add(label_change);
        frame.add(label_edit_name);
        frame.add(label_edit_age);
        frame.add(tf_edit_name);
        frame.add(tf_edit_age);
        frame.add(combo_edit);
        frame.add(btn_edit);

        frame.add(combo_remove);
        frame.add(label_remove_id);
        frame.add(btn_remove);
        frame.add(label_remove);
        frame.add(scrollPane);
    }

    public void ui_add()
    {
        label_insert.setFont(label_insert.getFont().deriveFont(Font.BOLD, 14f).deriveFont( Font.ITALIC,14f));
        label_insert.setForeground(Color.RED);
        label_insert.setBounds(650, 100, 100, 30);

        label_add_name.setBounds(650, 120,100,30);
        tf_add_name.setBounds(650,150,100,30);
        label_add_age.setBounds(780,120,100,30);
        tf_add_age.setBounds(780,150,50, 30);
        btn_add.setBounds(850, 150, 70, 30);

        btn_add.setBackground(Color.pink);
        btn_add.setBorder(new LineBorder(Color.black));
    }

    public void ui_edit()
    {
        label_change.setFont(label_change.getFont().deriveFont(Font.BOLD, 14f).deriveFont( Font.ITALIC,14f));
        label_change.setForeground(Color.RED);
        label_change.setBounds(650,300, 100, 30);

        combo_edit = new JComboBox(choice.toArray());
        label_edit_id.setBounds(650,320, 70, 30);
        combo_edit.setBounds(650,350,100,30);
        label_edit_name.setBounds(780,320,100, 30);
        tf_edit_name.setBounds(780,350,100,30);
        label_edit_age.setBounds(900,320, 100, 30);
        tf_edit_age.setBounds(900, 350, 50, 30);
        btn_edit.setBounds(980, 350, 100, 30);

        btn_edit.setBackground(Color.pink);
        btn_edit.setBorder(new LineBorder(Color.black));
    }

    public void ui_remove()
    {
        label_remove.setFont(label_remove.getFont().deriveFont(Font.BOLD, 14f).deriveFont( Font.ITALIC,14f));
        label_remove.setForeground(Color.RED);
        label_remove.setBounds(650, 200, 150, 30);
        combo_remove = new JComboBox(choice.toArray());

        label_remove_id.setBounds(650, 220, 100, 30);
        combo_remove.setBounds(650, 250, 100, 30);
        btn_remove.setBounds(780, 250,100, 30);

        btn_remove.setBackground(Color.pink);
        btn_remove.setBorder(new LineBorder(Color.black));
    }

    public void table()
    {
        table_model.addColumn("ID");
        table_model.addColumn("Name");
        table_model.addColumn("Age");
        table.setModel(table_model);
        table.setEnabled(false);
        table.setBounds(50,50, 500, 400);
        table.revalidate();
        scrollPane.setBounds(60, 50, 500, 400);

    }


    public void create_table()
    {
        for(Student i: students)
        {
            table_model.addRow(new String[]{i.getId(), i.getName(), Integer.toString(i.getAge())});
        }

    }


    public void add_func()
    {
        btn_add.addActionListener(e ->
        {
            String id = "SV";
            String temp = "0";
            for (Student i: students)
                temp = i.getId().substring(2, i.getId().length()); //coi lai

            id = id + (Integer.parseInt(temp) +1);

            String n = tf_add_name.getText();
            int a = Integer.parseInt(tf_add_age.getText());
            if (a > 0 & a < 100)
            {
                Student t = new Student(id ,n, a);
                table_model.addRow(new String[]{id,n,tf_add_age.getText()});
                students.add(t);
                combo_remove.addItem(id);
                combo_edit.addItem(id);
                try
                {
                    HandleFile.write_file(students);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

                tf_add_name.setText("");
                tf_add_age.setText("");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Invalid age", "ALERT", JOptionPane.INFORMATION_MESSAGE);

            }
            students = HandleFile.read_csv(file_path);
            choice = get_all_ID(students);
        });
    }


    public ArrayList<String> get_all_ID(List<Student> students)
    {
        for (Student i: students)
        {
            if (choice != null) {
                choice.add(i.getId());
            }
        }
        Arrays.sort(new ArrayList[]{choice});
        return choice;
    }

    public void remove_func()
    {
        btn_remove.addActionListener(e->
        {
            String choose = (String) combo_remove.getSelectedItem();
            combo_remove.removeItem(choose);
            combo_edit.removeItem(choose);
            int count = 0;
            for (Student i: students)
            {
                if (i.getId().equals(choose))
                {
                    table_model.removeRow(count);
                    i.setId("");
                    i.setName("");
                    i.setAge(0);
                    break;
                }
                count++;
            }
            try {
                HandleFile.write_file(students);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            students = HandleFile.read_csv(file_path);
        });
    }

    public  void edit_func()
    {
        btn_edit.addActionListener(e->
        {
            String temp = (String) combo_edit.getSelectedItem();
            assert temp != null;
            int t = Integer.parseInt(tf_edit_age.getText());
            int count = 0;
            for (Student i : students) {
                if (t < 0 || t>100)
                {
                    JOptionPane.showMessageDialog(null, "Invalid age", "ALERT", JOptionPane.INFORMATION_MESSAGE);
                }
                if (i.getId().equals(temp))
                {
                    i.setName(tf_edit_name.getText());
                    i.setAge(t);
                    Arrays.sort(new ArrayList[]{choice});
                    table_model.removeRow(count);
                    table_model.addRow(new String[] {i.getId(), i.getName(),Integer.toString(t)});
                    break;
                }
                count++;
            }
            try
            {
                HandleFile.write_file(students);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            tf_edit_name.setText("");
            tf_edit_age.setText("");
            combo_edit.setSelectedItem(0);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }
}
