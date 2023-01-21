package com.dsv.converter.model;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * The Employee class represents an Employee object with its properties such as firstName, middleName, lastName, gender, dob and salary.
 *
 * @author Dilraj vyas
 * @JsonFormat annotation is used to format the LocalDate dob property to the desired format "YYYY-MM-dd"
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private String firstName;
    private String middleName;

    private String lastName;

    private String gender;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private LocalDate dob;

    private double salary;


}
