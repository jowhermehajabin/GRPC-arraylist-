package servers;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import servicesdemo.UserServiceDemo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GrpcServer {


    private static final Logger logger = Logger.getLogger(GrpcServer.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new UserServiceDemo())
                .build();
        server.start();

        logger.info("Server started at port : " + server.getPort());
        server.awaitTermination(300, TimeUnit.SECONDS);
    }
}
