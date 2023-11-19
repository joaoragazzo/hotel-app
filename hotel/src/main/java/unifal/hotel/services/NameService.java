package unifal.hotel.services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@NoArgsConstructor
public class NameService {
    private String name = "Início do trabalho de dados!";

    public String getName() {
        return this.name;
    }

}
