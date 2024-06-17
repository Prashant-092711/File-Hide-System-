package Views;

import dao.userDAO;
import model.user;
import service.GenerateOTP;
import service.SendOTPService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

import static service.UserService.saveUser;

public class welcome {
    public void welcomeScreen(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome To App");
        System.out.println("Press 1 To Log-in");
        System.out.println("Press 2 To Sign-up");
        System.out.println("Press 0 To Exit");
        int choice =0;
        try{
            choice = Integer.parseInt(br.readLine());
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        switch (choice){
            case 1 -> login();
            case 2 -> signup();
            case 0 -> System.exit(0);
        }
    }
    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Email");
        String email = sc.nextLine();
        try {
            if(userDAO.isExists(email)){
                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email,genOTP);
                System.out.println("Enter the OTP: ");
                String otp = sc.nextLine();
                if (otp.equals(genOTP)){
                    new UserView(email).home();
                }else {
                    System.out.println("Wrong OTP");
                }
            }
            else {
                System.out.println("User Not Found");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void signup() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Name: ");
        String name = sc.nextLine();
        System.out.println("Enter Email: ");
        String email =  sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email,genOTP);
        System.out.println("Enter the OTP: ");
        String otp = sc.nextLine();
        if (otp.equals(genOTP)){
            user User = new user(name,email);
            int  response  = saveUser(User);
            switch (response){
                case 0 -> System.out.println("User Registered");
                case 1-> System.out.println("user already Exists");
            }
        }else {
            System.out.println("Wrong OTP");
        }

    }

}
