package unifal.hotel.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name="account")
public class Account implements Serializable {
    
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name="person_id")
    private Person person;
    private String username;
    private String password;

}
