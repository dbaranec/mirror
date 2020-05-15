package com.magic.mirror;

import com.magic.mirror.clients.QouteClient;
import com.magic.mirror.model.QouteRequest;
import com.magic.mirror.model.QouteResponse;
import com.magic.mirror.model.entity.Qoute;
import com.magic.mirror.model.rest.QouteResponseOut;
import com.magic.mirror.service.QouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MirrorManager {
    private static Integer MAX_QUETE_LENGTH = 125;
    @Autowired
    private QouteClient qouteClient;

    @Autowired
    private QouteService qouteService;

    @Autowired
    private QouteRequest qouteRequest;

    public QouteResponseOut processQuote() {
        QouteResponseOut qouteResponseOut = findRandomQoute();
        saveNewQouteIfNotExist();
        return qouteResponseOut;
    }

    private QouteResponseOut findRandomQoute() {
        Qoute randomQoute = qouteService.findRandom();
        QouteResponseOut qouteResponseOut = new QouteResponseOut();
        qouteResponseOut.setQuoteAuthor(randomQoute.getAuthor());
        qouteResponseOut.setQuoteText(randomQoute.getQouteText());
        return qouteResponseOut;
    }

    private void saveNewQouteIfNotExist() {
        QouteResponse qoute = null;
        try {
            qoute = qouteClient.getQoute(qouteRequest.getLanguageCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (qoute != null && qoute.getContent().length() < MAX_QUETE_LENGTH) {
            if (!qouteService.findByText(qoute.getContent())) {
                Qoute qouteEntity = new Qoute();
                qouteEntity.setAuthor(qoute.getOriginator().getName());
                qouteEntity.setQouteText(qoute.getContent());
                qouteService.save(qouteEntity);
            }
        }
    }
}
