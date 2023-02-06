package com.hua.springbatchplayground.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "student")
public class Student {
    @JsonProperty("ID")
    private Long id;
    @JsonProperty("First Name")
//    @XmlElement(name = "firstName")
    private String firstName;
    @JsonProperty("Last Name")
    private String lastName;
    @JsonProperty("Email")
    private String email;
}