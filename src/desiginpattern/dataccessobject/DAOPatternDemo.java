package desiginpattern.dataccessobject;

public class DAOPatternDemo {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAOImpl();

        //输出所有的学生
        for (Student student : studentDAO.getAllStudents()) {
            System.out.println("Student: [RollNo : "
                    +student.getRollNo()+", Name : "+student.getName()+" ]");
        }


        //更新学生
        Student student =studentDAO.getAllStudents().get(0);
        student.setName("Michael");
        studentDAO.updateStudent(student);

        //获取学生
        studentDAO.getStudent(0);
        System.out.println("Student: [RollNo : "
                +student.getRollNo()+", Name : "+student.getName()+" ]");
    }
}
