package ch.overney.springBootWatering;

import ch.overney.springBootWatering.app.SensorsStateUpdater;
import ch.overney.springBootWatering.data.MockDataHandler;
import ch.overney.springBootWatering.util.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWateringApplication {

	// CompanyId, to simplify things, let's assume the same user is always logged in and we only have one company.
	public static long DUMMY_COMPANY_ID = 12L;
	private static User loggedInUser = new User(DUMMY_COMPANY_ID);

	public static void main(String[] args) {
		MockDataHandler.getInstance().generateDataForCompany(loggedInUser.getCompanyId());
		SpringApplication.run(SpringBootWateringApplication.class, args);

		// Start the REST API consumer when server is ready.
		SensorsStateUpdater.runForever();
	}
}
