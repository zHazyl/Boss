package main;

public class ListSalary {
    int id;
    double salary;

    public ListSalary(String id, double salary) {
        this.id = Integer.parseInt(id);
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
