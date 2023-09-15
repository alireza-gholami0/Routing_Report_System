package com.example.routingreportsystem.domain;

import com.example.routingreportsystem.myEnum.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user_table")
@NoArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role")
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Report> reports;
}
