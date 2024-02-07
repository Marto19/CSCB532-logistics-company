package com.logistics.logisticsCompany.entities.users;

import com.logistics.logisticsCompany.entities.logisticsCompany.LogisticsCompany;
import com.logistics.logisticsCompany.entities.orders.Shipment;
import com.logistics.logisticsCompany.entities.offices.Office;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


/**
 * The Employee class is used to represent an employee entity.
 * It contains the id, first name, second name, user, current office, sent shipments, received shipments, and logistics company of the employee.
 */
@Entity
@Table(name = "employee")
public class Employee {

    /**
     * The id of the employee.
     * It is a unique identifier for the employee.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The first name of the employee.
     */
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    /**
     * The second name of the employee.
     */
    @Column(name = "second_name", nullable = false, length = 50)
    private String secondName;

    //Many-to-One relationships with User and Office
    /**
     * The user of the employee.
     * It is a many-to-one relationship between employee and user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

    /**
     * The current office of the employee.
     * It is a many-to-one relationship between employee and office.
     */
    @ManyToOne
    @JoinColumn(name = "current_office_id")
    private Office currentOffice;
    
    //One-to-Many relationships with Shipment (sender and receiver)
    /**
     * The set of sent shipments of the employee.
     * It is a one-to-many relationship between employee and shipment.
     */
    @OneToMany(mappedBy = "senderEmployee")
    private Set<Shipment> sentShipments = new HashSet<>();

    /**
     * The set of received shipments of the employee.
     * It is a one-to-many relationship between employee and shipment.
     */
    @OneToMany(mappedBy = "receiverEmployee")
    private Set<Shipment> receivedShipments = new HashSet<>();

    //adding relationship between employee-company n:1
    /**
     * The logistics company of the employee.
     * It is a many-to-one relationship between employee and logistics company.
     */
    @ManyToOne
    @JoinColumn(name = "company_id")
    private LogisticsCompany logisticsCompany;



    public Employee(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    //Constructors
    public Employee(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Office getCurrentOffice() {
        return currentOffice;
    }

    public void setCurrentOffice(Office currentOffice) {
        this.currentOffice = currentOffice;
    }

    public Set<Shipment> getSentShipments() {
        return sentShipments;
    }

    public void setSentShipments(Set<Shipment> sentShipments) {
        this.sentShipments = sentShipments;
    }

    public Set<Shipment> getReceivedShipments() {
        return receivedShipments;
    }

    public void setReceivedShipments(Set<Shipment> receivedShipments) {
        this.receivedShipments = receivedShipments;
    }

    public LogisticsCompany getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(LogisticsCompany logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }
}