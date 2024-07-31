package MVCPatternExample;

public class MVCPatternTest {

    public static void main(String[] args) {
        // Create a model
        Student student = new Student("John Doe", 1, "A");

        // Create a view
        StudentView view = new StudentView();

        // Create a controller
        StudentController controller = new StudentController(student, view);

        // Display initial student details
        controller.updateView();

        // Update student details
        controller.setStudentName("Jane Doe");
        controller.setStudentId(2);
        controller.setStudentGrade("B");

        // Display updated student details
        controller.updateView();
    }
}

