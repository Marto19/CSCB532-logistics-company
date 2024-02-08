package com.logistics.logisticsCompany.entities.logisticsCompany;

import com.logistics.logisticsCompany.entities.offices.Office;
import com.logistics.logisticsCompany.entities.users.Employee;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

/**
 * The LogisticsCompany class is used to represent a logistics company entity.
 * It contains the id, name, and income of the logistics company.
 */
@Entity
@Table(name = "logistics_company")
public class LogisticsCompany {     //creating this entity in order to satisfy  3. a)
    /**
     * The id of the logistics company.
     * It is a unique identifier for the logistics company.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The name of the logistics company.
     */
    @Column(name = "company_name")
    private String name;

    /**
     * The income of the logistics company.
     */
    @Column(name = "income")
    private BigDecimal income;

    /**
     * The set of offices of the logistics company.
     */
    @OneToMany(mappedBy = "logisticsCompany",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Office> officeSet;

    /**
     * The set of employees of the logistics company.
     */
    @OneToMany(mappedBy = "logisticsCompany",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employeeSet;

    /**
     * The set of income histories of the logistics company.
     */
    @OneToMany(mappedBy = "logisticsCompany")
    private Set<IncomeHistory> incomeHistories;
    
    public LogisticsCompany(String name) {
        this.name = name;
    }

    public LogisticsCompany() {

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
