package com.yash.smart_parking_reservation.config;

import com.yash.smart_parking_reservation.entity.ParkingSlot;
import com.yash.smart_parking_reservation.repository.ParkingSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ParkingSlotRepository parkingSlotRepository;

    @Override
    public void run(String... args) throws Exception {
        if (parkingSlotRepository.count() == 0) {
            IntStream.rangeClosed(1, 20).forEach(i -> {
                ParkingSlot slot = ParkingSlot.builder()
                        .slotNumber("Slot " + i)
                        .reserved(false)
                        .build();
                parkingSlotRepository.save(slot);
            });
        }
    }
}
