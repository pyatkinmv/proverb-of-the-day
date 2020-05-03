package ru.pyatkinmv;


import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pyatkinmv.dao.ProverbRepository;
import ru.pyatkinmv.model.Proverb;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProverbRepositoryTest {

    @Autowired
    private ProverbRepository proverbRepository;

    @Test
    public void nonEmptyInitialDataTest() {
        val proverbs = proverbRepository.findAll();
        assertFalse(proverbs.isEmpty());
    }

    @Test
    public void saveAndFindTest() {
        val proverb = new Proverb("text", "descr");

        proverbRepository.save(proverb);

        val found = proverbRepository.findById(proverb.getId());

        assertTrue(found.isPresent());

        assertEquals(proverb, found.get());
    }

    @Test
    public void findFirstThatIsNotSuppliedTest() {
        val expected = new Proverb("three", "three");
        val proverbs = List.of(
                new Proverb("one", "one"),
                new Proverb("two", "two"),
                expected
        );

        proverbRepository.deleteAll();
        proverbRepository.saveAll(proverbs);

        proverbs.stream()
                .filter(it -> !it.equals(expected))
                .forEach(it -> it.setSupplied(true));

        val optionalProverb = proverbRepository.findFirstThatIsNotSupplied();

        assertTrue(optionalProverb.isPresent());

        val actual = optionalProverb.get();

        assertEquals(expected, actual);
    }
}
