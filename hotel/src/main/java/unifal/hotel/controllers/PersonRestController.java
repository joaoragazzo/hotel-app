package unifal.hotel.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import unifal.hotel.entity.Person;
import unifal.hotel.services.ClientService;
import unifal.hotel.services.PersonService;
import unifal.hotel.services.dto.PersonDTO;

import java.util.Objects;

@RestController
@Log4j2
@AllArgsConstructor
public class PersonRestController {

    private final ClientService clientService;
    private final PersonService personService;

    @GetMapping("/api/person/{id}")
    public ResponseEntity<PersonDTO> getPersonInfo(@PathVariable Long id) {
        Person person = personService.getPersonByID(id);

        if (Objects.isNull(person)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new PersonDTO(person.getName(), person.getSurname()));
    }

    @GetMapping("/api/person/")
    public ResponseEntity<PersonDTO> getPersonInfo() { return  ResponseEntity.notFound().build(); }



}
