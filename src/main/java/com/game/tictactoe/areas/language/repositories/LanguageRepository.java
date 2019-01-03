package com.game.tictactoe.areas.language.repositories;

import com.cyecize.summer.common.annotations.Service;
import com.game.tictactoe.areas.language.entities.Language;
import com.game.tictactoe.areas.language.enums.LanguageLocaleType;
import com.game.tictactoe.repositories.BaseRepository;

import java.util.List;

@Service
public class LanguageRepository extends BaseRepository {

    public Language findById(Long id) {
        return super.execute(actionResult -> actionResult.setResult(super.entityManager.createQuery("SELECT l FROM Language l WHERE l.id = :lid", Language.class)
                .setParameter("lid", id)
                .getResultStream().findFirst().orElse(null)
        )).getResult();
    }

    public Language findOneByLocaleType(LanguageLocaleType localeType) {
        return super.execute(actionResult -> actionResult.setResult(super.entityManager.createQuery(
                "SELECT l FROM Language l WHERE l.localeType = :ltype", Language.class)
                .setParameter("ltype", localeType)
                .getResultStream().findFirst().orElse(null)
        )).getResult();
    }

    public List<Language> findAll() {
        return super.execute(actionResult -> actionResult.setResult(super.entityManager.createQuery("SELECT l FROM Language l", Language.class).getResultList())).getResult();
    }
}
