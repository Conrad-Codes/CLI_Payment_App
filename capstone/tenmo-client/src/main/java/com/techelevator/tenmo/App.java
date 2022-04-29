package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransactionDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TEnmoService;

import java.math.BigDecimal;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private TEnmoService tEnmoService = new TEnmoService();
    Scanner scanner = new Scanner(System.in);

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
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: \n");
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
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: \n");
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
        System.out.println("Your current account balance is: $" + tEnmoService.getBalance());
    }

    private void viewTransferHistory() {
        TransactionDTO[] log = null;
        log = tEnmoService.transferLog();

        if (log.length == 0) {
            System.out.println("You have no transfer history!");
            return;
        } else {
            for (TransactionDTO tDTO : log) {
                System.out.println(tDTO.viewTransferLog());
            }
            Integer choice = 0;
            boolean keepGoing = true;
            do {
                try {
                    System.out.println("\nPlease enter transfer ID to view details (0 to cancel): ");
                    choice = Integer.parseInt(scanner.nextLine());

                    if (choice == 0){
                        keepGoing = false;
                    }else{
                        for (TransactionDTO tDTO : log) {
                            if(tDTO.getTransfer_id() == choice) {
                                System.out.println(tDTO.viewTransferDetails());
                            }
                        }
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!");
                }
            }while(keepGoing);
        }
    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }

    private void sendBucks() {
        User[] displayedUsers = null;
        displayedUsers = tEnmoService.listUsers();

        int account_to_id = 0;
        BigDecimal amount = new BigDecimal("0.00");
        boolean keepRunning = true;
        String response = "";

        System.out.println("");

        do {
            try {
                //Display list of possible receiving users
                for (User user : displayedUsers) {
                    System.out.println(user);
                }
                System.out.println("Enter ID of user you are sending to (0 to cancel):");
                account_to_id = Integer.parseInt(scanner.nextLine());

                if(account_to_id == 0) {
                    return;
                }

                System.out.println("Enter amount:");
                amount = new BigDecimal(scanner.nextLine());

                //Search the array of potential users
                for (User user : displayedUsers) {
                    //Check for valid receiving ID and also that entered amount is greater than 0
                    if (user.getId() == account_to_id && amount.compareTo(new BigDecimal("0.00")) > 0) {

                        TransactionDTO transactionDTO = new TransactionDTO();
                        transactionDTO.setAmount(amount);
                        transactionDTO.setAccount_to_id(account_to_id);
                        transactionDTO.setTransfer_type_desc("Send");

                        //Set response message to server response
                        response = tEnmoService.transfer(transactionDTO);

                        //Stop the loop
                        keepRunning = false;
                    }
                }
                //After iterating check response message. If blank prompt for invalid input
                if (response.equals("")) {
                    System.out.println("Invalid input, please try again.\n");
                } else {
                    System.out.println(response);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please try again.\n");
            }
        } while (keepRunning);
    }

    private void requestBucks() {
        // TODO Auto-generated method stub
    }
}
