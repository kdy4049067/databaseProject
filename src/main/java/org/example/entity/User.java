package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;

@Entity(name = "User")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    public User(String uid, String password){
        this.uid = uid;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String uid;
    @Column
    private String password;
    @Column
    private String role;

    public UserDto toUserDto(){
        return new UserDto(
                this.id,
                this.uid,
                this.password,
                this.role);
    }

}
