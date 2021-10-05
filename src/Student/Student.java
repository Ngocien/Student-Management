package Student;

public class Student {
    private String id;
    private String name;
    private int age;

    public Student(String id,String n, int a)
    {
        this.id= id;
        this.name= n;
        this.age =a;
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public String getId()
    {
        return id;
    }

    public void setName(String n)
    {
        this.name = n;
    }
    public void setAge(int a)
    {
        this.age = a;

    }
    public void setId(String id)
    {
        this.id = id;
    }

    public static Student createStudent(String[] metadata) {
        String id = metadata[0];
        String name = metadata[1];
        int age = Integer.parseInt(metadata[2]);
        return new Student(id,name,age);
    }

}
