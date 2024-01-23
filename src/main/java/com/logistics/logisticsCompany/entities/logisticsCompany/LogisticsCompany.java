package com.logistics.logisticsCompany.entities.logisticsCompany;

import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Employee;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "logistics_company")
public class LogisticsCompany {     //creating this entity in order to satisfy  3. a)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "income")
    private BigDecimal income;

    @OneToMany(mappedBy = "logisticsCompany")
    private Set<Office> officeSet;

    @OneToMany(mappedBy = "logisticsCompany")
    private Set<Employee> employeeSet;

    public LogisticsCompany(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Set<Office> getOfficeSet() {
        return officeSet;
    }

    public void setOfficeSet(Set<Office> officeSet) {
        this.officeSet = officeSet;
    }

    public Set<Employee> getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(Set<Employee> employeeSet) {
        this.employeeSet = employeeSet;
    }

    @Override
    public String toString() {
        return "LogisticsCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", income=" + income +
                '}';
    }
}
