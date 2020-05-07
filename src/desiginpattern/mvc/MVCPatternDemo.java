package desiginpattern.mvc;

public class MVCPatternDemo {
    public static void main(String[] args) {

        Student model = retrieveStudentFromDatabase();

        StudentView view = new StudentView();

        StudentController studentController = new StudentController(model,view);

        studentController.updateView();

    }

    private static Student retrieveStudentFromDatabase(){
        Student student = new Student();

        student.setName("Robert");
        student.setRollNo("10");
        return student;
    }
}
