package client;

import com.demo.grpc.Userdemo;
import com.demo.grpc.userGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;
import java.util.logging.Logger;

public class ClientGrpc {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        userGrpc.userBlockingStub userStub = userGrpc.newBlockingStub(channel);

        while (true) {

            Scanner sc = new Scanner(System.in);
            System.out.println("Choose 1 to log in and 2 to register:");
            int choice = Integer.parseInt(sc.nextLine());
            System.out.println("Enter your name here:");
            String username = sc.nextLine();
            System.out.println("Enter your password here:");
            String password = sc.nextLine();

            if (choice == 1) {
                Userdemo.LoginReq request = Userdemo.LoginReq.newBuilder()
                        .setUsername(username)
                        .setPassword(password)
                        .build();
                Userdemo.APIRes apiRes = userStub.login(request);

                System.out.println(apiRes);
            } else {
                Userdemo.User req = Userdemo.User.newBuilder()
                        .setUsername(username)
                        .setPassword(password)
                        .build();
                Userdemo.APIRes apiResponse = userStub.register(req);
                System.out.println(apiResponse);
            }

        }

    }
}
