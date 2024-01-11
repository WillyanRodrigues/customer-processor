package nl.outtask.willyan.functions.customerprocessor.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Customer")
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String dateOfBirth;

    private String documentNumber;

    @Temporal(TemporalType.DATE)
    private LocalDate lastUpdate;

    public Customer(final String name, final String dateOfBirth, final String documentNumber) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.documentNumber = documentNumber;
        this.lastUpdate = LocalDate.now();


    }
}
