package ru.averkiev.gateway.services.impl;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.averkiev.report.ReportServiceGrpc;
import ru.averkiev.report.ReportServiceOuterClass;
import ru.averkiev.gateway.services.GrpcService;

@Service
public class GrpcServiceImpl implements GrpcService {

    @GrpcClient("reportService")
    private ReportServiceGrpc.ReportServiceBlockingStub blockingStub;

    public GrpcServiceImpl(ReportServiceGrpc.ReportServiceBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    public GrpcServiceImpl() {
    }

    @Override
    public String getReport(String userId, String accountId, String startDate, String endDate) {

        ReportServiceOuterClass.ReportRequest request = ReportServiceOuterClass.ReportRequest.newBuilder()
                .setIdUser(userId)
                .setIdAccount(accountId)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .build();

        ReportServiceOuterClass.ReportResponse response = blockingStub.getReport(request);

        return response.getReportData();
    }
}
