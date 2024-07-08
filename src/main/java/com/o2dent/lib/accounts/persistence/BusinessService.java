package com.o2dent.lib.accounts.persistence;

import com.o2dent.lib.accounts.entity.Business;
import com.o2dent.lib.accounts.helpers.exceptions.SubdomainExistsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService{
    private final BusinessRepository businessRepository;

    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    public Business update(Business business) {
        return this.businessRepository.saveAndFlush(business);
    }

    public Business create(Business business) throws SubdomainExistsException {
        Optional<List<Business>> businessFound = this.businessRepository.findBySubdomainUri(business.getSubdomainUri());
        if(businessFound.isPresent()
                && !businessFound.get().isEmpty()) {
            throw new SubdomainExistsException("Subdomain ["+business.getSubdomainUri().toString()+"] already exists");
        }

        return this.businessRepository.saveAndFlush(business);
    }

    public Business disable(Business business) {
        business.setEnabled(false);
        return this.businessRepository.saveAndFlush(business);
    }

    public Optional<Business> findById(Long businessId) {
        return this.businessRepository.findById(businessId);
    }
}
