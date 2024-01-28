package com.logistics.logisticsCompany.controller;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.logisticsCompany.DTO.CustomerDTO;
import com.logistics.logisticsCompany.entities.users.Customer;
import com.logistics.logisticsCompany.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;
import java.util.Optional;

import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.*;

import com.logistics.logisticsCompany.DTO.CustomerDTO;
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTestv2 {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Customer customer1, customer2;
	
	@BeforeEach
	void setUp() {
		customer1 = new Customer("FirstName1", "LastName1", "8888888888");
		customer2 = new Customer("FirstName2", "LastName2", "9999999999");
		
		customer1 = customerRepository.save(customer1);
		customer2 = customerRepository.save(customer2);
	}
	
	//@AfterEach
	//void tearDown() {
	//	customerRepository.deleteById(customer1.getId());
	//	customerRepository.deleteById(customer2.getId());
	//}
	
	@Test
	void createCustomer() throws Exception {
		Customer newCustomer = new Customer("NewFirstName", "NewLastName", "7777777777");
		String newCustomerJson = objectMapper.writeValueAsString(newCustomer);
		
		MvcResult result = mockMvc.perform(post("/api/v1/customers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(newCustomerJson))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isCreated())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		// You can now assert things about 'content' if needed
		assertTrue(content.contains("Customer created successfully"));
	}
	
	
/*	//todo - fix this test
	@Test
	void getAllCustomers() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/v1/customers"))
				.andExpect(status().isOk())
				.andReturn();
		
		List<CustomerDTO> customers = objectMapper.readValue(
				result.getResponse().getContentAsString(),
				new TypeReference<List<CustomerDTO>>() {}
		);
		
		assertTrue(customers.stream().anyMatch(c -> c.getPhone().equals(customer1.getPhone())));
		assertTrue(customers.stream().anyMatch(c -> c.getPhone().equals(customer2.getPhone())));
	}
	
	//fixme - fix this test
	@Test
	void getCustomerById() throws Exception {
		mockMvc.perform(get("/api/v1/customers/" + customer1.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(customer1.getId()))
				.andExpect(jsonPath("$.phone").value(customer1.getPhone()));
	}

	
	@Test
	void updateCustomer() throws Exception {
		customer1.setFirstName("UpdatedName");
		
		mockMvc.perform(put("/customers/" + customer1.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer1)))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Customer updated successfully")));
		
		Optional<Customer> updatedCustomer = customerRepository.findById(customer1.getId());
		assertTrue(updatedCustomer.isPresent());
		assertEquals("UpdatedName", updatedCustomer.get().getFirstName());
	}
	
	@Test
	void deleteCustomer() throws Exception {
		mockMvc.perform(delete("/customers/" + customer1.getId()))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Customer deleted successfully")));
		
		assertFalse(customerRepository.existsById(customer1.getId()));
		}
	*/
}
