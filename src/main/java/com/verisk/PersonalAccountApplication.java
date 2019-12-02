package com.verisk;

import com.verisk.model.Account;
import com.verisk.model.User;
import com.verisk.repository.UserRepository;
import com.verisk.service.AccountService;
import com.verisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.util.Scanner;
//@EnableJpaAuditing
@SpringBootApplication
public class PersonalAccountApplication {

  public static void main(String[] args) {
    SpringApplication.run(PersonalAccountApplication.class, args);
  }
/*
  @Autowired
  private UserService userService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private AccountService accountService;

  @Override
  public void run(String... args) throws Exception {

    boolean flag = true;
    Scanner scanner = new Scanner(System.in);
    User user;

    do {


      System.out.println("Insert email");
      String email = scanner.nextLine();

      if(userService.findByEmail(email).isPresent()){
        user = userService.findByEmail(email).get();
        System.out.println("Insert password");
        String password = scanner.nextLine();
        System.out.println(email);
        System.out.println(password);
        boolean pass =bCryptPasswordEncoder.matches(password,user.getPassword());
        if(pass){

          System.out.print("\033[H\033[2J");
          System.out.flush();

          System.out.println("Menu \n" +
                  "1 Create new Account \n " +
                  "2 Close an Account \n " +
                  "3 Deposit \n " +
                  "4 withdrawal");
          int option = scanner.nextInt();

          switch(option){

            case 1:
              createAccount(user);

          }
          flag= false;

        }
      }else{
        System.out.println("User Doesn't exist");
      }

    }while (flag);


  }

  public void createAccount(User user){

    Scanner scanner = new Scanner(System.in);

    System.out.println("Type of Account \n " +
            "1 Debit \n " +
            "2 Credit");
    int option = scanner.nextInt();
    String type="";

    switch(option){
      case 1:
        type= "debit";
        break;
      case 2:
        type= "credit";
        break;
      default:
        System.out.println("Not a valid Value");
        type="debit";
        break;
    }

    System.out.println("Insert Amount");
    BigDecimal amount = scanner.nextBigDecimal();
    scanner.nextLine();
    System.out.println("Security pin");
    String pin = scanner.nextLine();

    Account account = Account.builder()
            .amount(amount)
            .pin(pin)
            .status("open")
            .type(type)
            .user(user).build();

    accountService.save(account);

  }
*/
}
