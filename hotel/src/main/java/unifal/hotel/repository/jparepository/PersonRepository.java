package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unifal.hotel.entity.Person;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>
{
    @Override // SELECT * FROM person;
    List<Person> findAll();

    @Override
    List<Person> findAllById(Iterable<Long> longs);
}
