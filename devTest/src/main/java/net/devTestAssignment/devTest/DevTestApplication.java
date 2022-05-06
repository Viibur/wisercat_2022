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

		Filter filter1 = new Filter("Filter1");
		Criteria criteria1 = Criteria.builder().filter(filter1).type("Amount").option("More than").defaultValue("4").build();
		Criteria criteria2 = Criteria.builder().filter(filter1).type("Title").option("Starts with").defaultValue("Hello").build();
		Criteria criteria3 = Criteria.builder().filter(filter1).type("Date").option("none").defaultValue("today").build();
		List<Criteria> criteriaList1 = Arrays.asList(criteria1,criteria2,criteria3);
		filter1.setCriteria(criteriaList1);

		Filter filter2 = new Filter("Filter2");
		Criteria criteria4 = Criteria.builder().filter(filter2).type("Amount").option("More than").defaultValue("41").build();
		Criteria criteria5 = Criteria.builder().filter(filter2).type("Title").option("Ends with").defaultValue("l.").build();
		List<Criteria> criteriaList2 = Arrays.asList(criteria4,criteria5);
		filter2.setCriteria(criteriaList2);

		filterRepository.save(filter1);
		filterRepository.save(filter2);
	}
}
