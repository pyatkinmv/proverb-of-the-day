package ru.pyatkinmv;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pyatkinmv.dao.ProverbRepository;
import ru.pyatkinmv.model.Proverb;
import ru.pyatkinmv.service.ProverbService;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
public class ProverbServiceTest {

    @Autowired
    private ProverbService proverbService;

    @Autowired
    private ProverbRepository proverbRepository;

    @Test
    @Transactional
    public void supplyProverbTest() {
        proverbRepository.deleteAll();

        val expected = proverbRepository.save(new Proverb("text", "descr"));
        val actual = proverbService.supply();

        assertEquals(expected, actual);
        assertThrows(IllegalStateException.class, () -> proverbService.supply());
    }
}
