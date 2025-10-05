package org.eirzarog.pharmacy;

import org.eirzarog.pharmacy.entities.Drug;
import org.eirzarog.pharmacy.repositories.DrugRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackageClasses = Drug.class)
@EnableJpaRepositories(basePackageClasses = {DrugRepository.class})
public class PharmacyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmacyApplication.class, args);
    }

}
