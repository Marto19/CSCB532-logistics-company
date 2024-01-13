package com.logistics.logisticsCompany.organizaiton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = { "com.logistics.logisticsCompany.entities" })
@EntityScan(basePackages = {"com.logistics.logisticsCompany.entities.offices", "com.logistics.logisticsCompany.entities.orders"})
@EnableJpaRepositories(basePackages = {"com.logistics.logisticsCompany.entities"})
@SpringBootApplication
@EnableConfigurationProperties
public class LogisticsCompanyApplication {

//
//	@Autowired
//  	private OfficeServiceImpl officeService;
	public static void main(String[] args) {
		SpringApplication.run(LogisticsCompanyApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		// Create a new Office
//		  Office office = new Office();
//		  // Set the properties of the office
//		  office.setOfficeName("Office Name");
//		  office.setCity("City");
//		  office.setPostcode(12345);
//		  office.setAddress("Address");
//		  // Save the office
////		  O(office);
//	}

//  @Bean
//  public CommandLineRunner demo(OfficeRepository repository) {
//	  return (args) -> {
//		  // Create a new Office
//		  Office office = new Office();
//		  // Set the properties of the office
//		  office.setOfficeName("Office Name");
//		  office.setCity("City");
//		  office.setPostcode(12345);
//		  office.setAddress("Address");
//		  // Save the office
//		  repository.save(office);
//	  };
//  }
}
