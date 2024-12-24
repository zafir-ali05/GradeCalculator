// Inner class for assignment details
public class Assignment {
    private final String name;
    private final double grade;
    private final double weight;

    public Assignment(String name, double grade, double weight) {
        this.name = name;
        this.grade = grade;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    public double getWeight() {
        return weight;
    }
}