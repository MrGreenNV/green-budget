package ru.averkiev.grpc.ampq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.averkiev.grpc.configs.RabbitMQConfig;
import ru.averkiev.grpc.services.ReportServiceServer;

//@Service
public class RabbitMQConsumer {
    private final ReportServiceServer reportServiceServer;

    public RabbitMQConsumer(ReportServiceServer reportServiceServer) {
        this.reportServiceServer = reportServiceServer;
    }


}
