package ru.averkiev.gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.averkiev.gateway.services.GrpcService;

@RestController
@RequestMapping("api/v1/get-report")
public class ReportController {
    private final GrpcService grpcService;

    @Autowired
    public ReportController(GrpcService grpcService) {
        this.grpcService = grpcService;
    }

    @GetMapping
    public ResponseEntity<String> getReport(@RequestParam final String userId,
                                            @RequestParam final String accountId,
                                            @RequestParam final String startDate,
                                            @RequestParam final String endDate) {

        final String pathOfReport = grpcService.getReport(userId, accountId, startDate, endDate);
        return ResponseEntity.ok(pathOfReport);
    }
}
