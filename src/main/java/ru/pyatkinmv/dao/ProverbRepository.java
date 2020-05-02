package ru.pyatkinmv.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pyatkinmv.model.Proverb;

import java.util.Optional;

@Repository
public interface ProverbRepository extends CrudRepository<Proverb, Long> {

    @Query(value = "SELECT * FROM proverb " +
            "WHERE is_supplied = FALSE " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<Proverb> findFirstThatIsNotSupplied();
}
