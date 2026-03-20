package com.cardprocessor.helper;

import com.cardprocessor.model.Status;
import com.cardprocessor.model.entity.CardEntity;
import com.cardprocessor.repository.ProcessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CardDataLoader implements CommandLineRunner {

    private final ProcessorRepository processorRepository;

    @Override
    public void run(String... args) {

        if (processorRepository.count() > 0) {
            return; // prevent duplicate inserts
        }

        Instant now = Instant.now();

        List<CardEntity> cards = List.of(
                createCard("crd-0000001", "barc0001", "4111111111111111", "123"),
                createCard("crd-0000002", "barc0002", "4111111111112222", "234"),
                createCard("crd-0000003", "barc0003", "4111111111113333", "345"),
                createCard("crd-0000004", "barc0004", "4111111111114444", "456"),
                createCard("crd-0000005", "barc0005", "4111111111115555", "567"),
                createCard("crd-0000006", "barc0006", "4111111111116666", "678"),
                createCard("crd-0000007", "barc0007", "4111111111117777", "789"),
                createCard("crd-0000008", "barc0008", "4111111111118888", "890"),
                createCard("crd-0000009", "barc0009", "4111111111119999", "901"),
                createCard("crd-0000010", "barc0010", "4111111111110000", "012")
        );

        processorRepository.saveAll(cards);
    }

    private CardEntity createCard(String cardId, String barcode, String cardNumber, String cvv) {

        CardEntity card = new CardEntity();

        card.setCardId(cardId);
        card.setBarcode(barcode);
        card.setCardNumber(cardNumber);
        card.setCvv(cvv);
        card.setExpiry("12/28");
        card.setLastFour(cardNumber.substring(cardNumber.length() - 4));
        card.setStatus(Status.EMBOSSED.toString());
        card.setCreatedAt(Instant.now());

        card.setUserId(null);
        card.setAccountId(null);
        card.setActivationDate(null);

        return card;
    }
}
