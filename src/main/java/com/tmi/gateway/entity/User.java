package com.tmi.gateway.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "TB_USER")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Setter
    @Column(nullable = false)
    private String pw;

//    @Setter
//    @Column(nullable = false, length = 50)
//    @Enumerated(EnumType.STRING)
//    private UserRole role;
}
