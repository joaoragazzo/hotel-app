package unifal.hotel.api.debug.arbitraryClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class PersonArbitrary implements Serializable {

    private Integer id;
    private String name;
}
