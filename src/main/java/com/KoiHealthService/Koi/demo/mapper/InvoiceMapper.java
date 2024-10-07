package com.KoiHealthService.Koi.demo.mapper;


import com.KoiHealthService.Koi.demo.dto.request.InvoiceCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.UserRequest;
import com.KoiHealthService.Koi.demo.dto.response.UserResponse;
import com.KoiHealthService.Koi.demo.entity.Invoice;
import com.KoiHealthService.Koi.demo.entity.User;
import lombok.NonNull;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    @NonNull
    Invoice toInvoice(InvoiceCreationRequest request);
    @NonNull
    UserResponse toInvoiceResponse(Invoice invoice);
}
