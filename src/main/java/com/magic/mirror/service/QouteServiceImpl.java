package com.magic.mirror.service;

import com.magic.mirror.model.entity.Qoute;
import com.magic.mirror.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class QouteServiceImpl implements QouteService {

    @Autowired
    private QuoteRepository repository;


    @Override
    public boolean findByText(String qouteText) {
        return repository.existsQouteByQouteText(qouteText);
    }

    @Override
    public void save(Qoute quote) {
        repository.save(quote);
    }

    @Override
    public Qoute findRandom() {
        int idx = (int) (Math.random() * repository.count());
        Page<Qoute> qoutePage = repository.findAll(PageRequest.of(idx, 1));
        Qoute qoute = null;
        if (qoutePage.hasContent()) {
            qoute = qoutePage.getContent().get(0);
        }
        return qoute;
    }
}
