package at.spengergasse.spring_thymeleaf.controllers;

import at.spengergasse.spring_thymeleaf.entities.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final PatientRepository patientRepository;
    private final DeviceRepository deviceRepository;

    public ReservationController(ReservationRepository reservationRepository, PatientRepository patientRepository, DeviceRepository deviceRepository) {
        this.reservationRepository = reservationRepository;
        this.patientRepository = patientRepository;
        this.deviceRepository = deviceRepository;
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("devices", deviceRepository.findAll());
        model.addAttribute("regions", List.of("Kopf", "Thorax", "Abdomen", "Extremität"));
        return "add_reservation";
    }

    @PostMapping("/add")
    public String addSubmit(@ModelAttribute Reservation reservation,
                            @RequestParam("patientId") Integer patientId,
                            @RequestParam("deviceId") String deviceId,
                            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Patient p = patientRepository.findById(patientId).orElse(null);
        Device d = deviceRepository.findById(deviceId).orElse(null);
        reservation.setPatient(p);
        reservation.setDevice(d);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservationRepository.save(reservation);
        return "redirect:/reservation/list?deviceId=" + deviceId;
    }

    @GetMapping("/list")
    public String list(@RequestParam(required = false) String deviceId, Model model) {
        if (deviceId != null && deviceId != "") {
            model.addAttribute("reservations", reservationRepository.findByDeviceIdOrderByStartTime(deviceId));
        } else {
            model.addAttribute("reservations", reservationRepository.findAll());
        }
        model.addAttribute("devices", deviceRepository.findAll());
        return "list_reservations";
    }
}

