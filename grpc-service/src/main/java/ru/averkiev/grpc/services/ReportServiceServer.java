package ru.averkiev.grpc.services;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.averkiev.report.ReportServiceGrpc;
import ru.averkiev.report.ReportServiceOuterClass;

@GrpcService
public class ReportServiceServer extends ReportServiceGrpc.ReportServiceImplBase {
    private static final String USER_HOME_DIR = System.getProperty("user.home");

    @Override
    public void getReport(ReportServiceOuterClass.ReportRequest request, StreamObserver<ReportServiceOuterClass.ReportResponse> responseObserver) {
        ReportServiceOuterClass.ReportResponse response = ReportServiceOuterClass.ReportResponse.newBuilder()
                .setReportData(String.format("%s - Директория для сохранения файла. Параметры: userId = %s, accountId = %s, startDate = %s, endDate = %s", USER_HOME_DIR, request.getIdUser(), request.getIdAccount(), request.getStartDate(), request.getEndDate()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
