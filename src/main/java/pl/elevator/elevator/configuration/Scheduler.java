package pl.elevator.elevator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import pl.elevator.elevator.services.ElevatorService;


@Component
public class Scheduler {

        private ElevatorService elevatorService;
    @Autowired
    public Scheduler(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        @Scheduled(fixedRate = 1000 * 20)
        public void reportCurrentTime() {
            elevatorService.moveAllFloor();
        }


}
