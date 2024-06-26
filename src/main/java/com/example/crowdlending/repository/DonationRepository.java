package com.example.crowdlending.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crowdlending.repository.model.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> {

}
