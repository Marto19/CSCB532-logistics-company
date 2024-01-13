package com.logistics.logisticsCompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LogisticsCompanyApplication {

//
//	@Autowired
//  	private OfficeServiceImpl officeService;
	public static void main(String[] args) {
		SpringApplication.run(LogisticsCompanyApplication.class, args);
	}
	//TODO:fix mappings

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
