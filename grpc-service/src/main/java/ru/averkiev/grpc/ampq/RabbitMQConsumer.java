package ru.averkiev.grpc.ampq;

import io.grpc.netty.shaded.io.netty.util.internal.logging.InternalLogger;
import io.grpc.netty.shaded.io.netty.util.internal.logging.Log4J2LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.averkiev.grpc.configs.RabbitMQConfig;
import ru.averkiev.grpc.services.ReportServiceServer;

@Service
public class RabbitMQConsumer {
    private final ReportServiceServer reportServiceServer;
    private final InternalLogger logger = Log4J2LoggerFactory.getInstance(this.getClass());

    public RabbitMQConsumer(ReportServiceServer reportServiceServer) {
        this.reportServiceServer = reportServiceServer;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(String message) {
        logger.info("Получено сообщение из RabbitMQ: " + message);

        // Вызываем gRPC-сервис с переданным сообщением
        String path = reportServiceServer.createReport(message);
        logger.info("Ответ от gRPC-сервиса: " + path);
    }

}
