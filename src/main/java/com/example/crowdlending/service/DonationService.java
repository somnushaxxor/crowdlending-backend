package com.example.crowdlending.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.crowdlending.repository.DonationRepository;
import com.example.crowdlending.repository.model.Donation;
import com.example.crowdlending.repository.model.Project;
import com.example.crowdlending.repository.model.User;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    public void saveDonation(User user, Project project, Long donateAmount) {
        Donation donation = new Donation();
        donation.setUser(user);
        donation.setProject(project);
        donation.setAmount(donateAmount);
        donationRepository.save(donation);
    }
}
