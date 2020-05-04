package ru.pyatkinmv.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.pyatkinmv.dao.ProverbRepository;
import ru.pyatkinmv.model.Proverb;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProverbService {

    private final ProverbRepository proverbRepository;

    @Transactional
    public Proverb supply() {
        val optionalProverb = proverbRepository.findFirstThatIsNotSupplied();

        if (optionalProverb.isEmpty()) {
            val errorMessage = "All proverbs are already supplied or there are no proverbs at all";
            log.error(errorMessage);

            throw new IllegalStateException(errorMessage);
        }

        val proverb = optionalProverb.get();
        proverb.setSupplied(true);

        return proverb;
    }
}
