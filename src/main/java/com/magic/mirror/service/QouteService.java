package com.magic.mirror.service;

import com.magic.mirror.model.entity.Qoute;

public interface QouteService {

    boolean findByText(String qouteText);

    void save(Qoute quote);

    Qoute findRandom();
}
