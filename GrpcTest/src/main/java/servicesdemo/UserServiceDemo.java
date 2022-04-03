package servicesdemo;

import com.demo.grpc.Userdemo.*;
import com.demo.grpc.Userdemo;
import com.demo.grpc.userGrpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.logging.Logger;

public class UserServiceDemo extends userImplBase {
    static ArrayList<Userdemo.User>registerlist = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(UserServiceDemo.class.getName());
    @Override
    public void login(Userdemo.LoginReq request, StreamObserver<Userdemo.APIRes> responseObserver) {

        String userName = request.getUsername();
        String password = request.getPassword();

        logger.info("Request generated from user : " + userName);

        Userdemo.APIRes.Builder response = Userdemo.APIRes.newBuilder();
        response.setResCode(400).setMessage("Login failed");

        for (User user:registerlist) {

            if (user.getUsername().equals(userName)&& user.getPassword().equals(password)) {

                response.setResCode(200).setMessage("Successfully logged in");
            }

        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void register(Userdemo.User request, StreamObserver<Userdemo.APIRes> responseObserver) {
        String username = request.getUsername();
        String password = request.getPassword();

        Userdemo.APIRes.Builder response = Userdemo.APIRes.newBuilder();
        Boolean exist = false;
        for (User user:registerlist) {

            if(user.getUsername().equals(username))
            {
                response.setResCode(401).setMessage("User with this name already exists!");
               exist = true;
            }

        }
        if(!exist)
        {
            registerlist.add(request);
            response.setResCode(201).setMessage("User created successfully");
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }

    @Override
    public void logout(Userdemo.Empty request, StreamObserver<Userdemo.APIRes> responseObserver) {
        super.logout(request, responseObserver);
    }

}
