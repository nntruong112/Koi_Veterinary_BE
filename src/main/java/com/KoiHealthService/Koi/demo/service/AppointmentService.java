package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentRequest;
import com.KoiHealthService.Koi.demo.dto.request.appointment.AppointmentUpdateRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentResponse;
import com.KoiHealthService.Koi.demo.dto.response.AppointmentTypeResponse;
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
    public Appointment updateAppointment(String appointmentId, AppointmentUpdateRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_FOUND));

        AppointmentType appointmentType = appointmentTypeRepository.findById(request.getAppointmentTypeId()).orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_TYPE_FOUND));
        User user = userRepository.findById(request.getCustomerId()).orElseThrow(() -> new AnotherException(ErrorCode.USER_NOT_EXISTED));
        Fish fish = fishRepository.findById(request.getFishId()).orElseThrow(() -> new AnotherException(ErrorCode.NO_FISH_FOUND));
        User vet = userRepository.findById(request.getVeterinarianId()).orElseThrow(() -> new AnotherException(ErrorCode.NO_VETERINARIAN_FOUND));

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentType(appointmentType);
        appointment.setEndTime(request.getEndTime());
        appointment.setLocation(request.getLocation());
        appointment.setStartTime(request.getStartTime());
        appointment.setStatus(request.getStatus());
        appointment.setCustomer(user);
        appointment.setVeterinarian(vet);
        appointment.setPaymentStatus(request.getPaymentStatus());
        appointment.setFish(fish);

        return appointmentRepository.save(appointment);
    }

    //delete Appointment ========================================================================================
    public void deleteAppointment(String appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }


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
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_VETERINARIAN_FOUND));
        // Fetch appointments directly from the repository
        return appointmentRepository.findAppointmentsByVeterinarianId(veterinarianId);
    }

    public Long countAppointments() {
        return appointmentRepository.count(); // This counts all records in the 'appointments' table
    }

    public Long calculateTotalIncome() {
        List<Appointment> paidAppointments = appointmentRepository.findByPaymentStatus("paid");
        return paidAppointments.stream()
                .map(appointment -> appointment.getAppointmentType().getPrice())
                .reduce(0L, Long::sum); // Sum all the prices
    }

    public Fish findFishByAppointmentId(String appointmentId) {
        return appointmentRepository.findFishByAppointmentId(appointmentId);
    }

    //count the role
    public long getCountByAppointmentType(String type) {
        if (type != null) {
            return userRepository.countByRoles(type);
        } else {
            throw new RuntimeException("Cannot find role");
        }
    }

    public Long getAppointmentCountByType(String appointmentTypeId) {
        if (!appointmentTypeRepository.existsById(appointmentTypeId)) {
            throw new AnotherException(ErrorCode.NO_APPOINTMENT_TYPE_FOUND);
        }

        return appointmentRepository.countByAppointmentTypeId(appointmentTypeId);

    }

}
