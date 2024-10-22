package com.KoiHealthService.Koi.demo.service;


import com.KoiHealthService.Koi.demo.dto.request.VeterinarianScheduleRequest;
import com.KoiHealthService.Koi.demo.dto.response.FishResponse;
import com.KoiHealthService.Koi.demo.dto.response.VeterinarianScheduleResponse;
import com.KoiHealthService.Koi.demo.entity.*;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.UserMapper;
import com.KoiHealthService.Koi.demo.mapper.VeterinarianScheduleMapper;
import com.KoiHealthService.Koi.demo.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VeterinarianScheduleService {


    VeterinarianScheduleRepository veterinarianScheduleRepository;

    VeterinarianProfileRepository veterinarianProfileRepository;

    FishSpecialtyRepository fishSpecialtyRepository;

    UserRepository userRepository;
    
    VeterinarianScheduleMapper veterinarianScheduleMapper;

    UserMapper userMapper;


    public VeterinarianScheduleResponse createSchedule(VeterinarianScheduleRequest request) {
        User veterinarian = userRepository.findById(request.getVeterinarianId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_VETERINARIAN_FOUND));

        VeterinarianSchedule schedule = VeterinarianSchedule.builder()
                .availableDate(request.getAvailableDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        veterinarianScheduleRepository.save(schedule);

        VeterinarianProfile veterinarianProfile = VeterinarianProfile.builder()
                .user(veterinarian)
                .veterinarianSchedule(schedule)
                .build();

        veterinarianProfileRepository.save(veterinarianProfile);

        // Create and return the response DTO
        VeterinarianScheduleResponse response = new VeterinarianScheduleResponse();
        response.setScheduleId(schedule.getScheduleId());
        response.setAvailableDate(schedule.getAvailableDate());
        response.setStartTime(schedule.getStartTime());
        response.setEndTime(schedule.getEndTime());
        response.setVeterinarianName(veterinarian.getFirstname() + " " + veterinarian.getLastname());
                                     //phải dùng tới cách truyền thống de nối chuỗi @@
        return response;
    }


    public List<VeterinarianSchedule> getAllVeterinarianSchedules() {
        return veterinarianScheduleRepository.findAll();
    }


    //get schedule by id ===============================================================================
    public VeterinarianSchedule getScheduleById(String id) {
        VeterinarianSchedule veterinarianSchedule = veterinarianScheduleRepository.findById(id).orElseThrow(() -> new AnotherException(ErrorCode.NO_FISH_FOUND) );

       // List<User> veterinarian = veterinarianSchedule.getVeterinarians();

        return VeterinarianSchedule.builder()
                .availableDate(veterinarianSchedule.getAvailableDate())
                .startTime(veterinarianSchedule.getStartTime())
                .endTime(veterinarianSchedule.getEndTime())
                .scheduleId(veterinarianSchedule.getScheduleId())
                .build();

    }

    public List<VeterinarianSchedule> getScheduleByVeterinarianId(String veterinarianId) {
        // Tìm User (bác sĩ thú y) dựa trên veterinarianId
        User veterinarian = userRepository.findById(veterinarianId)
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_VETERINARIAN_FOUND));

        // Lấy danh sách VeterinarianProfiles cho User (ở đây là Vet)
        List<VeterinarianProfile> veterinarianProfiles = veterinarianProfileRepository.findByUser(veterinarian);

        // Trích xuất danh sách VeterinarianSchedules từ danh sách VeterinarianProfiles
        return veterinarianProfiles.stream()
                .map(VeterinarianProfile::getVeterinarianSchedule)
                .filter(Objects::nonNull) // Bỏ qua các giá trị null
                .collect(Collectors.toList());

        //Phương thức stream() chuyển đổi danh sách veterinarianProfiles thành một luồng (stream) của các phần tử. Luồng cho phép bạn thực hiện các thao tác trên từng phần tử trong danh sách một cách tuần tự hoặc song song.

        //map() được sử dụng để chuyển đổi từng phần tử trong luồng. Trong trường hợp này, mỗi VeterinarianProfile trong danh sách được chuyển đổi thành đối tượng VeterinarianSchedule bằng cách gọi phương thức getVeterinarianSchedule().

        //map(), luồng sẽ chứa các đối tượng VeterinarianSchedule. Nếu một VeterinarianProfile không có lịch làm việc, giá trị trả về từ getVeterinarianSchedule() sẽ là null.

        //filter() được sử dụng để loại bỏ các phần tử không thỏa mãn điều kiện. Ở đây, Objects::nonNull là một phương thức tham chiếu (method reference) để kiểm tra xem giá trị có phải là null hay không.

        //collect() được sử dụng để thu thập các phần tử trong luồng thành một danh sách. Trong trường hợp này, Collectors.toList() sẽ tạo một danh sách (List<VeterinarianSchedule>) chứa tất cả các phần tử còn lại sau khi lọc.
        //Kết quả: Phương thức này sẽ trả về một danh sách chứa tất cả các lịch làm việc (VeterinarianSchedule) không bị null.
    }

//    public List<VeterinarianScheduleResponse> getScheduleByVeterinarianId(String veterinarianId) {
//        User veterinarian = userRepository.findById(veterinarianId).orElseThrow(() -> new AnotherException(ErrorCode.NO_VETERINARIAN_FOUND));
//        List<VeterinarianSchedule> schedules = veterinarianScheduleRepository.findScheduleByVeterinarianId(veterinarianId);
//        return schedules.stream()
//                .map(veterinarianScheduleMapper::toVeterinarianResponse)
//                .collect(Collectors.toList());
//    }

    

}
