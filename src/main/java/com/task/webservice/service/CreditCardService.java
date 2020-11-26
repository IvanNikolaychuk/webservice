package com.task.webservice.service;

import com.task.webservice.model.CreditCard;
import com.task.webservice.model.Profile;
import com.task.webservice.model.User;
import com.task.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CreditCardService {

    @Autowired
    private UserRepository userRepository;

    public CreditCardService() {
        super();
    }

    public void updateCreditCardData(String userName, CreditCard creditCard) {
        User userFromDb = userRepository.findByUsername(userName);

        if (userFromDb != null && creditCard != null) {
            updateCreditData(userFromDb, creditCard);
            userRepository.save(userFromDb);
        }
    }

    private void updateCreditData(User userFromDb, CreditCard updatedCard) {

        Optional<CreditCard> creditCardOptional = findById(updatedCard.getId(), userFromDb);

        if (updatedCard.isDefaultCard()) {
            userFromDb.getCreditCards().forEach(card -> card.setDefaultCard(false));
        }

        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            creditCard.setExpirationDate(updatedCard.getExpirationDate());
            creditCard.setCardHolder(updatedCard.getCardHolder());
            creditCard.setCardNumber(updatedCard.getCardNumber());
            creditCard.setDefaultCard(updatedCard.isDefaultCard());
        } else {
            if (userFromDb.getCreditCards().isEmpty()) {
                updatedCard.setDefaultCard(true);
            }
            userFromDb.getCreditCards().add(updatedCard);
        }
    }

    public void deleteCreditData(String userName, CreditCard deletedCreditCard) {
        User userFromDb = userRepository.findByUsername(userName);
        if (userFromDb != null && deletedCreditCard != null && deletedCreditCard.getId() != null) {
            Optional<CreditCard> creditCardToBeDeleted = findById(deletedCreditCard.getId(), userFromDb);
            creditCardToBeDeleted.ifPresent(creditCard -> userFromDb.getCreditCards().remove(creditCard));
            creditCardToBeDeleted.ifPresent(oldCreditCard -> {
                if (oldCreditCard.isDefaultCard()) {
                    if (!userFromDb.getCreditCards().isEmpty()) {
                        userFromDb.getCreditCards().iterator().next().setDefaultCard(true);
                    }
                }
            });
            userRepository.save(userFromDb);
        }
    }

    public Optional<CreditCard> findById(Long profileId, User user) {
        return user.getCreditCards()
                .stream()
                .filter(creditCard -> creditCard.getId().equals(profileId))
                .findFirst();
    }

}
