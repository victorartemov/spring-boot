package com.example.demo;

import com.example.demo.model.Agent;
import com.example.demo.model.RealEstate;
import com.example.demo.repository.AgentRepository;
import com.example.demo.service.RealEstateService;
import com.example.demo.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        RealEstateService realEstateService = context.getBean(RealEstateService.class);
        ReportService reportService = context.getBean(ReportService.class);

        emulateRealEstateOperations(realEstateService);

        checkFirstAgentSoldProperties(context);

        checkTop5Report(reportService);
    }

    private static void checkFirstAgentSoldProperties(ConfigurableApplicationContext context) {
        AgentRepository agentRepository = context.getBean(AgentRepository.class);
        Optional<Agent> agent = agentRepository.findById(1l);
        agent.ifPresent(a -> a.getSoldProperties().forEach(System.out::println));
    }

    private static void checkTop5Report(ReportService reportService) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date startDate = dateFormat.parse("17/07/2019");
            Date endDate = dateFormat.parse("15/10/2020");

            List<Agent> topFiveAgentsWithinDateRange = reportService.getTopFiveAgentsWithinDateRange(startDate, endDate);

            topFiveAgentsWithinDateRange.forEach(System.out::println);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void emulateRealEstateOperations(RealEstateService realEstateService) throws InterruptedException {

        List<RealEstate> allRealEstates = realEstateService.findAll();

        for (int i = 0; i < 3; i++) {
            allRealEstates.forEach(
                    realEstate -> realEstateService.changePrice(
                            realEstate.getTitle(), realEstate.getPrice() + 100_000)
            );
            TimeUnit.SECONDS.sleep(5);
        }

        realEstateService.sell("VDNH flat");
    }

    @Bean
    @Order(2)
    public CommandLineRunner commandLineRunner(RealEstateService realEstateService) {
        return (String[] args) -> {
            realEstateService.sell("Sokolniki Park House");
            realEstateService.sell("Gomel River Palace");
        };
    }

    @Bean
    @Order(1)
    public ApplicationRunner applicationRunner(AgentRepository agentRepository) {
        return (ApplicationArguments args) -> {
            loadInitialData(agentRepository);
        };
    }

    private void loadInitialData(AgentRepository agentRepository) {

        logger.info("Adding initial data to the app");

        Agent moscowAgent = new Agent("Moscow Rieltor");

        RealEstate bigFlatInMoscow = new RealEstate(100_000_000, "Moscow River Palace");
        RealEstate mediumFlatInMoscow = new RealEstate(10_000_000, "Sokolniki Park House");
        RealEstate smallFlatInMoscow = new RealEstate(3_000_000, "VDNH flat");

        moscowAgent.addRealEstate(bigFlatInMoscow);
        moscowAgent.addRealEstate(mediumFlatInMoscow);
        moscowAgent.addRealEstate(smallFlatInMoscow);

        agentRepository.save(moscowAgent);

        Agent gomelAgent = new Agent("Gomel Rieltor");

        RealEstate bigFlatInGomel = new RealEstate(100_000_000, "Gomel River Palace");
        RealEstate mediumFlatInGomel = new RealEstate(10_000_000, "Gomel Park House");
        RealEstate smallFlatInGomel = new RealEstate(3_000_000, "Usual flat");

        gomelAgent.addRealEstate(bigFlatInGomel);
        gomelAgent.addRealEstate(mediumFlatInGomel);
        gomelAgent.addRealEstate(smallFlatInGomel);

        agentRepository.save(gomelAgent);

        //add more test data. Keep the saving order
        //data may be seen on http://localhost:8080/h2-console/ in jdbc:h2:mem:test db
    }
}
