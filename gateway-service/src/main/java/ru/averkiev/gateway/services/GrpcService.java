package ru.averkiev.gateway.services;

public interface GrpcService {
    String getReport(final String userId, final String accountId, final String startDate, final String endDate);
}
