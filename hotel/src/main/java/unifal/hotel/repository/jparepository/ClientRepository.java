package unifal.hotel.repository.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unifal.hotel.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>
{

    public Integer deleteByPersonId(Long PersonId);

}
