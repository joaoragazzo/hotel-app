package unifal.hotel.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="account")
public class Account {
    
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name="person_id")
    private Person person;
    private String username;
    private String password;

}
