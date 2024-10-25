package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentRequest;
import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentResponse;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.AppointmentType;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.User;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.AppointmentMapper;
import com.KoiHealthService.Koi.demo.repository.AppointmentRepository;
import com.KoiHealthService.Koi.demo.repository.AppointmentTypeRepository;
import com.KoiHealthService.Koi.demo.repository.FishRepository;
import com.KoiHealthService.Koi.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentMapper appointmentMapper;
    private final FishRepository fishRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final AppointmentTypeRepository appointmentTypeRepository;
    

    //create new Appointment ========================================================================================
    public Appointment createAppointment(AppointmentRequest request) {
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_CUSTOMER_FOUND));
        User veterinarian = userRepository.findById(request.getVeterinarianId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_VETERINARIAN_FOUND));
        Fish fish = fishRepository.findById(request.getFishId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_FISH_FOUND));

        // Manually fetch AppointmentType from repository
        AppointmentType appointmentType = appointmentTypeRepository.findById(request.getAppointmentTypeId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_TYPE_FOUND));

        Appointment appointment = Appointment.builder()
                .appointmentId(request.getAppointmentId())
                .appointmentDate(request.getAppointmentDate())
                .appointmentType(appointmentType)
                .endTime(request.getEndTime())
                .location(request.getLocation())
                .startTime(request.getStartTime())
                .status(request.getStatus())
                .customer(customer)
                .fish(fish)
                .veterinarian(veterinarian)
                .paymentStatus(request.getPaymentStatus())
                .build();

        return appointmentRepository.save(appointment);
    }

    //get all Appointment ========================================================================================
    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    //get appointment by appointmentId
    public Appointment getAppointmentById(String appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_FOUND));
    }

    //update Appointment ========================================================================================
    public AppointmentResponse updateAppointment(String appointmentId, AppointmentUpdateRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_FOUND));

        appointmentMapper.toUpdateAppointment(appointment, request);
        appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentResponse(appointment);
    }

    //delete Appointment ========================================================================================
    public void deleteAppointment(String appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

//    //get Appointment by customer Id ========================================================================================
//    public List<AppointmentResponse> getAppointmentsByCustomerId(String customerId) {
//        // Fetch appointments by customerId
//        List<Appointment> appointments = appointmentRepository.findAppointmentsByCustomerId(customerId);
//
//
//        // Print out appointment data for debugging
//        appointments.forEach(a -> {
//            System.out.println("Appointment ID: " + a.getAppointmentId());
//            System.out.println("Customer ID: " + (a.getCustomer() != null ? a.getCustomer().getUserId() : "null"));
//            System.out.println("Veterinarian ID: " + (a.getVeterinarian() != null ? a.getVeterinarian().getUserId() : "null"));
//            System.out.println("Fish ID: " + (a.getFish() != null ? a.getFish().getFishId() : "null"));
//            System.out.println("Appointment Type ID: " + (a.getAppointmentType() != null ? a.getAppointmentType().getAppointmentTypeId() : "null"));
//        });
//        // Map to response DTOs
//        return appointments.stream()
//                .map(appointmentMapper::toAppointmentResponse)
//                .collect(Collectors.toList());
//    }

    public List<Appointment> getAppointmentsByCustomerId(String customerId) {
        // Fetch appointments by customerId and return the Appointment entity directly
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_CUSTOMER_FOUND));
        // Fetch appointments directly from the repository
        return appointmentRepository.findAppointmentsByCustomerId(customerId);
    }



    public List<Appointment> getAppointmentsByVeterinarianId(String veterinarianId) {
        // Fetch appointments by vetId and return the Appointment entity directly
        User veterinarian = userRepository.findById(veterinarianId)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_CUSTOMER_FOUND));
        // Fetch appointments directly from the repository
        return appointmentRepository.findAppointmentsByVeterinarianId(veterinarianId);
    }

    public Long countAppointments() {
        return appointmentRepository.count(); // This counts all records in the 'appointments' table
    }

    public Long calculateTotalIncome() {
        List<Appointment> paidAppointments = appointmentRepository.findByPaymentStatus("PAID");
        return paidAppointments.stream()
                .map(appointment -> appointment.getAppointmentType().getPrice())
                .reduce(0L, Long::sum); // Sum all the prices
    }

    public Fish findFishByAppointmentId(String appointmentId) {
        return appointmentRepository.findFishByAppointmentId(appointmentId);
    }

}
