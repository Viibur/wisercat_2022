package net.devTestAssignment.devTest;

import net.devTestAssignment.devTest.entity.Filter;
import net.devTestAssignment.devTest.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevTestApplication implements CommandLineRunner {

	public static void main(String[] args) { SpringApplication.run(DevTestApplication.class, args); }

	@Autowired
	private FilterRepository filterRepository;

	@Override
	public void run(String... args) throws Exception {
		Filter filter1 = Filter.builder().name("Andre").build();
		Filter filter2 = Filter.builder().name("Taavi").build();
		Filter filter3 = Filter.builder().name("Raiko").build();

		filterRepository.save(filter1);
		filterRepository.save(filter2);
		filterRepository.save(filter3);
	}
}
