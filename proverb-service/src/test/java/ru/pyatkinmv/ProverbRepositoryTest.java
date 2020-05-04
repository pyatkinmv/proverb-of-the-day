package ru.pyatkinmv;


import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.pyatkinmv.dao.ProverbRepository;
import ru.pyatkinmv.model.Proverb;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = Application.class)
@EnableTransactionManagement
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
    @Transactional
    public void findFirstThatIsNotSuppliedTest() {
        proverbRepository.deleteAll();

        val expected = new Proverb("three", "three");
        val proverbs = List.of(
                new Proverb("one", "one"),
                new Proverb("two", "two"),
                expected
        );

        val saved = proverbRepository.saveAll(proverbs);

        StreamSupport.stream(saved.spliterator(), false)
                .filter(it -> !it.equals(expected))
                .forEach(it -> it.setSupplied(true));

        val optionalProverb = proverbRepository.findFirstThatIsNotSupplied();
        assertTrue(optionalProverb.isPresent());
        val actual = optionalProverb.get();
        assertEquals(expected, actual);
    }
}
