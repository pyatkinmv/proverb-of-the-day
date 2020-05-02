package ru.pyatkinmv;


import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pyatkinmv.dao.ProverbRepository;

import static java.util.stream.StreamSupport.stream;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProverbRepositoryTest {

    @Autowired
    private ProverbRepository proverbRepository;

    @Test
    public void nonEmptyInitialDataTest() {
        val spliterator = proverbRepository.findAll().spliterator();
        val count = stream(spliterator, false).count();
        assertNotEquals(0, count);
    }

}
