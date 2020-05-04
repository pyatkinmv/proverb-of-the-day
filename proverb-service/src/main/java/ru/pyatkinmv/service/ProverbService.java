package ru.pyatkinmv.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.pyatkinmv.dao.ProverbRepository;
import ru.pyatkinmv.model.Proverb;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ProverbService {

    private final ProverbRepository proverbRepository;

    @Transactional
    public Proverb supply() {
        val optionalProverb = proverbRepository.findFirstThatIsNotSupplied();

        if (optionalProverb.isEmpty()) {
            throw new IllegalStateException("All proverbs are already supplied or there are no proverbs at all");
        }

        val proverb = optionalProverb.get();
        proverb.setSupplied(true);

        return proverb;
    }
}
