package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import unifal.hotel.services.entities.Person;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Override // SELECT * FROM person;
    List<Person> findAll();

    @Override
    List<Person> findAllById(Iterable<Long> longs);
}
