package Student;


import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Frame implements ActionListener {
    JFrame frame = new JFrame();
    JComboBox combo = new JComboBox();
    JComboBox comboBox = new JComboBox();
    List<Student> students = HandleFile.read_csv("src/data/data.csv");
    String add_id = "";
    ArrayList<String> choice = new ArrayList<>();
    Frame()
    {
        action();
        choice = get_all_ID(students);
        add_func();
        remove_func(choice);
        edit_func(choice);
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
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void action()
    {

        JLabel insert = new JLabel("Add a student");
        insert.setFont(insert.getFont().deriveFont(Font.BOLD, 14f).deriveFont( Font.ITALIC,14f));
        insert.setForeground(Color.RED);
        JLabel remove = new JLabel("Remove a student");
        remove.setFont(remove.getFont().deriveFont(Font.BOLD, 14f).deriveFont( Font.ITALIC,14f));
        remove.setForeground(Color.RED);
        JLabel change = new JLabel("Edit a student");
        change.setFont(change.getFont().deriveFont(Font.BOLD, 14f).deriveFont( Font.ITALIC,14f));
        change.setForeground(Color.RED);



        insert.setBounds(650, 100, 100, 30);
        remove.setBounds(650, 200, 150, 30);
        change.setBounds(650,300, 100, 30);

        frame.add(insert);
        frame.add(remove);
        frame.add(change);
    }

    public String[][] each_row(List<Student> students)
    {
//        ArrayList<String[]> result = new ArrayList<>();
        String[][] result;
        result = new String[students.size()][];
        int count = 0;

        for(Student i: students)
        {
            String[] temp;
            temp = new String[]{i.getId(), i.getName(), Integer.toString(i.getAge())};
            result[count] = temp;
            count += 1;
        }
        return result;
    }

    public void create_table()
    {
        String[] column= {"ID", "Name", "Age"};
        String[][] data= each_row(students);
        JTable table = new JTable(data, column);
        table.setEnabled(false);

        table.setBounds(50,50, 500, 400);
        table.revalidate();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(60, 50, 500, 400);

        frame.add(scrollPane);

//        Rectangle rect = table.getCellRect(0, 0, true);


    }

    public void add_func()
    {
        JTextField name = new JTextField();
        JTextField age = new JTextField();
        JLabel insert_name = new JLabel("Name");
        JLabel insert_age = new JLabel("Age");
        JButton add = new JButton("OK");

        insert_name.setBounds(650, 120,100,30);
        name.setBounds(650,150,100,30);
        insert_age.setBounds(780,120,100,30);
        age.setBounds(780,150,50, 30);
        add.setBounds(850, 150, 70, 30);

        add.setBackground(Color.pink);
        add.setBorder(new LineBorder(Color.black));

        frame.add(insert_name);
        frame.add(insert_age);
        frame.add(name);
        frame.add(age);
        frame.add(add);

        add.addActionListener(e ->
        {
            String id = "SV";
            String temp = "";
            for (Student i: students)
                temp = i.getId().substring(2, i.getId().length());

            id = id + (Integer.parseInt(temp) +1);

            String n = name.getText();
            int a = Integer.parseInt(age.getText());
            if (a > 0 & a < 100)
            {
                Student t = new Student(id ,n, a);
                students.add(t);
                combo.addItem(id);
                comboBox.addItem(id);
                try
                {
                    HandleFile.write_file(students);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

                name.setText("");
                age.setText("");
            }
            students = HandleFile.read_csv("src/data/data.csv");
            create_table();
            choice = get_all_ID(students);

        });
//        return students;
    }


    public ArrayList<String> get_all_ID(List<Student> students)
    {

        for (Student i: students)
        {
            assert choice != null;
            choice.add(i.getId());
        }
        return choice;
    }

    public void remove_func(ArrayList<String> choice)
    {
        combo = new JComboBox(choice.toArray());
        JButton submit = new JButton("OK");
        JLabel id = new JLabel("Student ID");

        id.setBounds(650, 220, 100, 30);
        combo.setBounds(650, 250, 100, 30);
        submit.setBounds(780, 250,100, 30);

        submit.setBackground(Color.pink);
        submit.setBorder(new LineBorder(Color.black));

        frame.add(combo);
        frame.add(id);
        frame.add(submit);

        submit.addActionListener(e->
        {
            String choose = (String)combo.getSelectedItem();
            combo.removeItem(choose);
            comboBox.removeItem(choose);
            for (Student i: students)
            {
                if (i.getId().equals(choose))
                {
                    i.setId("");
                    i.setName("");
                    i.setAge(0);
                    break;
                }
            }
            try {
                HandleFile.write_file(students);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            students = HandleFile.read_csv("src/data/data.csv");
            create_table();
        });
//        return students;
    }

    public  void edit_func(ArrayList<String> choice)
    {
        JTextField new_name = new JTextField();
        JTextField new_age = new JTextField();
        comboBox = new JComboBox(choice.toArray());
        JLabel id = new JLabel("Student ID");
        JLabel name = new JLabel("New name");
        JLabel age = new JLabel("New age");

        JButton submit = new JButton("OK");

        id.setBounds(650,320, 70, 30);
        comboBox.setBounds(650,350,100,30);
        name.setBounds(780,320,100, 30);
        new_name.setBounds(780,350,100,30);
        age.setBounds(900,320, 100, 30);
        new_age.setBounds(900, 350, 50, 30);
        submit.setBounds(980, 350, 100, 30);

        submit.setBackground(Color.pink);
        submit.setBorder(new LineBorder(Color.black));

        frame.add(id);
        frame.add(name);
        frame.add(age);
        frame.add(new_name);
        frame.add(new_age);
        frame.add(comboBox);
        frame.add(submit);

        submit.addActionListener(e->
        {
            String temp = (String)comboBox.getSelectedItem();
            assert temp != null;
            for (Student i : students) {
                int t = Integer.parseInt(new_age.getText());
                if (!i.getId().equals(temp) || t <= 0 || t >= 100) {
                    continue;
                }
                i.setName(new_name.getText());
                i.setAge(t);
                break;
            }
            try
            {
                HandleFile.write_file(students);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            new_name.setText("");
            new_age.setText("");
            comboBox.setSelectedItem(0);
            create_table();
        });
//        students = HandleFile.read_csv("src/data/data.csv");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }
}
