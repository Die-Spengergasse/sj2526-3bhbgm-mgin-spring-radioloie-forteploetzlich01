package at.spengergasse.spring_thymeleaf;

import at.spengergasse.spring_thymeleaf.entities.Device;
import at.spengergasse.spring_thymeleaf.entities.DeviceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringThymeleafApplication.class, args);
    }

    @Bean
    CommandLineRunner initDevices(DeviceRepository deviceRepository) {
        return args -> {
            if (deviceRepository.count() == 0) {
                Device d1 = new Device();
                d1.setId("MR-01");
                d1.setType("MR");
                d1.setLocation("Raum 101");
                deviceRepository.save(d1);

                Device d2 = new Device();
                d2.setId("CT-01");
                d2.setType("CT");
                d2.setLocation("Raum 102");
                deviceRepository.save(d2);

                Device d3 = new Device();
                d3.setId("XR-01");
                d3.setType("Röntgen");
                d3.setLocation("Raum 100");
                deviceRepository.save(d3);
            }
        };
    }

}
