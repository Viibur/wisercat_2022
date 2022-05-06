package net.devTestAssignment.devTest;

import net.devTestAssignment.devTest.entity.Criteria;
import net.devTestAssignment.devTest.entity.Filter;
import net.devTestAssignment.devTest.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DevTestApplication implements CommandLineRunner {

	public static void main(String[] args) { SpringApplication.run(DevTestApplication.class, args); }

	@Autowired
	private FilterRepository filterRepository;

	@Override
	public void run(String... args) throws Exception {

		Filter filter1 = new Filter("Andre");
		Criteria criteria1 = Criteria.builder().filter(filter1).type("Amount").option("More than").defaultValue("4").build();
		Criteria criteria2 = Criteria.builder().filter(filter1).type("Title").option("Starts with").defaultValue("Hello").build();
		Criteria criteria3 = Criteria.builder().filter(filter1).type("Date").option("none").defaultValue("today").build();
		List<Criteria> criteria = Arrays.asList(criteria1,criteria2,criteria3);
		filter1.setCriteria(criteria);

		Filter filter2 = Filter.builder().name("Taavi").build();
		Filter filter3 = Filter.builder().name("Raiko").build();


		filterRepository.save(filter1);
		filterRepository.save(filter2);
		filterRepository.save(filter3);
	}
}
