package ru.averkiev.grpc.services;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;
import ru.averkiev.report.ReportServiceGrpc;
import ru.averkiev.report.ReportServiceOuterClass;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Base64;

@GrpcService
public class ReportServiceServer extends ReportServiceGrpc.ReportServiceImplBase {
    private static final String USER_HOME_DIR = System.getProperty("user.home");

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public void getReport(ReportServiceOuterClass.ReportRequest request, StreamObserver<ReportServiceOuterClass.ReportResponse> responseObserver) {

        final String path = createReport(request.getIdUser());

        ReportServiceOuterClass.ReportResponse response = ReportServiceOuterClass.ReportResponse.newBuilder()
                .setReportData(path)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public String createReport(final String userId) {
        final String query = "select u.username, a.account_name, a.balance " +
                "from users u " +
                "join financial_accounts a on u.id = a.user_account_id " +
                "where u.id = ?";
        final Path pathToReport;
        try (final Connection con = DriverManager.getConnection(jdbcUrl, username, password);
             final PreparedStatement prSt = con.prepareStatement(query)) {

            prSt.setInt(1, Integer.parseInt(userId));
            final ResultSet rs = prSt.executeQuery();

            final StringBuilder report = new StringBuilder();
            report.append("============================================================================").append("\n");
            while (rs.next()) {
                report.append("----------------------------------------------------------------------------").append("\n");
                final String uName = new String(Base64.getDecoder().decode(rs.getString(1)));
                final String aName = new String(Base64.getDecoder().decode(rs.getString(2)));
                final String aBalance = rs.getString(3);

                report.append("Пользователь: ").append(aName).append("\n");
                report.append("Счет: ").append(uName).append("\n");
                report.append("Остаток: ").append(aBalance).append("\n");
                report.append("----------------------------------------------------------------------------").append("\n");

            }

            report.append("============================================================================").append("\n\n");

            final String filename = "report.txt";
            final File file = new File(USER_HOME_DIR, filename);
            try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(report.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            pathToReport = Paths.get(USER_HOME_DIR, filename);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pathToReport.toString();
    }

}
