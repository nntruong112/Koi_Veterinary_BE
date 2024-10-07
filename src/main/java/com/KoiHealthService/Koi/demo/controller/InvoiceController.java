package com.KoiHealthService.Koi.demo.controller;

import com.KoiHealthService.Koi.demo.dto.request.FishCreationRequest;
import com.KoiHealthService.Koi.demo.dto.request.InvoiceCreationRequest;
import com.KoiHealthService.Koi.demo.dto.response.ApiResponse;
import com.KoiHealthService.Koi.demo.entity.Fish;
import com.KoiHealthService.Koi.demo.entity.Invoice;
import com.KoiHealthService.Koi.demo.service.FishService;
import com.KoiHealthService.Koi.demo.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    @NonNull
    private InvoiceService invoiceService;

    @PostMapping("/create")
        //endpoint users voi method POST, users đặt có s vì nó là invention trong việc đặt tên API
    ApiResponse<Invoice> createInvoice(@RequestBody @Valid InvoiceCreationRequest request) {
        ApiResponse<Invoice> apiResponse = new ApiResponse<>();
        apiResponse.setResult(invoiceService.createInvoice(request));
        return apiResponse;
    }

    @GetMapping()
    ResponseEntity<List<Invoice>> getInvoice(){
        return ResponseEntity.ok(invoiceService.getInvoices());
    }

    @DeleteMapping("/{invoiceId}")
    String deleteInvoice(@PathVariable String invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return "Invoice has been deleted";
    }
}
