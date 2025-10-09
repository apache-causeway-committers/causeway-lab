package demoapp.webapp.vaadin.dom;

import java.util.List;

/**
 * User: klausmeier
 * Date: 04.Juni.24
 */
public class DepartmentEntityFixture {
    public static List<DepartmentEntity> allValues() {
        return List.of(HR, IT, SALES, MARKETING);
    }

    public static final DepartmentEntity HR = new DepartmentEntity("HR", "Human Resources");
    public static final DepartmentEntity IT = new DepartmentEntity("IT", "Information Technology");
    public static final DepartmentEntity SALES = new DepartmentEntity("SALES", "Sales");
    public static final DepartmentEntity MARKETING = new DepartmentEntity("MARKETING", "Marketing");
}
