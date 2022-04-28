package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransactionDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TEnmoService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private TEnmoService tEnmoService = new TEnmoService();

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        tEnmoService.setAuthToken(currentUser.getToken());
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        System.out.println(tEnmoService.getBalance());
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {

        User[] displayedUsers = null;
        displayedUsers = tEnmoService.listUsers();

        Scanner scanner = new Scanner(System.in);
        int receiverID = 0;
        BigDecimal amount = new BigDecimal("0.00");
        boolean keepRunning = true;
        String response = "";
        System.out.println("\n");
        do{
            try {

                for (User user : displayedUsers){
                    System.out.println(user);
                }

                System.out.println("Please enter the user ID of the receiver: ");
                receiverID = Integer.parseInt(scanner.nextLine());

                System.out.println("Please enter the amount to transfer: ");
                amount = new BigDecimal(scanner.nextLine());

                for (User user : displayedUsers){
                    if (user.getId() == receiverID && amount.compareTo(new BigDecimal ("0.00")) >0){
                        TransactionDTO transactionDTO = new TransactionDTO();
                        transactionDTO.setAmount(amount);
                        transactionDTO.setReceiverID(receiverID);

                        response = tEnmoService.transfer(transactionDTO);
                        keepRunning = false;
                    }
                }
                if (response.equals("")) {
                    System.out.println("\nInvalid input, please try again \n");
                }else{
                    System.out.println(response);
                }
            }catch( NumberFormatException e){
                System.out.println("\nInvalid input, please try again\n");
            }
        }while(keepRunning);
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
	}
}
