package com.KoiHealthService.Koi.demo.service;

import com.KoiHealthService.Koi.demo.dto.request.InvoiceCreationRequest;
import com.KoiHealthService.Koi.demo.entity.Appointment;
import com.KoiHealthService.Koi.demo.entity.Invoice;
import com.KoiHealthService.Koi.demo.exception.AnotherException;
import com.KoiHealthService.Koi.demo.exception.ErrorCode;
import com.KoiHealthService.Koi.demo.mapper.InvoiceMapper;
import com.KoiHealthService.Koi.demo.repository.AppointmentRepository;
import com.KoiHealthService.Koi.demo.repository.InvoiceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InvoiceService {
    private final AppointmentRepository appointmentRepository;

    @NonNull
    private InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;
    private Appointment appointment;
    
    private Invoice invoice;
    public Invoice createInvoice(InvoiceCreationRequest request){

        appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new AnotherException(ErrorCode.NO_APPOINTMENT_FOUND));

        invoice = Invoice.builder()
                .discount(request.getDiscount())
                .paymentMethod(request.getPaymentMethod())
                .total(request.getTotal())
                .paymentStatus(request.getPaymentStatus())
                .invoiceId(request.getInvoiceId())
                .appointment(appointment)
                .build();
        

        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    public void deleteInvoice(String invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }
}
