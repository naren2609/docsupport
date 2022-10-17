package com.docsupport.jp.controller;

import com.docsupport.jp.util.Utils;
import com.docsupport.jp.pojo.*;
import com.docsupport.jp.repositories.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Duration;
import java.util.*;

@RestController
@RequestMapping("")
@CrossOrigin
class DocSupportController {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Credential create(@RequestBody Credential cred) {
        String pwd = cred.getPassword();
        cred.setPassword(new BCryptPasswordEncoder().encode(pwd));
        cred = credentialRepository.save(cred);
        return cred;
    }

    @PostMapping("/registeremployer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Employer> create(@Valid @RequestBody Employer employer) {

        String pwd = employer.getPassword();
        employer.setPassword(new BCryptPasswordEncoder().encode(pwd));
        employer.setRoleType("EMPLOYER");
        if(!(null == credentialRepository.findByUserName(employer.getUserName())))
        {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User already exists!!");
        }
        try {
            employer = employerRepository.save(employer);
        }
        catch (DataIntegrityViolationException e) {
            String errorMessage = "";
            for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                if(SQLIntegrityConstraintViolationException.class.equals(t.getClass())) {
                    errorMessage  = "Email or Mobile already Exists.";
                }
            }
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, errorMessage, e);
        }
        catch(DataAccessException e) {
           // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).;
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "", e);
        }
        return ResponseEntity.ok(employer);
    }


    @PostMapping("/registerjs")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        String pwd = person.getPassword();
        person.setPassword(new BCryptPasswordEncoder().encode(pwd));
        person.setRoleType("USER");
        if(!(null == credentialRepository.findByUserName(person.getUserName())))
        {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User already exists!!");
        }
        try{
            person = personRepository.save(person);
        }
        catch (DataIntegrityViolationException e) {
            String errorMessage = "";
            for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                if(SQLIntegrityConstraintViolationException.class.equals(t.getClass())) {
                    errorMessage  = "Email or Mobile already Exists.";
                }
            }
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, errorMessage, e);
        }
        catch(DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "", e);
        }
        return ResponseEntity.ok(person);
    }


    @PostMapping("/generatetoken/{userName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Transactional
    public void generateToken(@PathVariable String userName) {
        Credential cred = credentialRepository.findByUserName(userName);
        if (cred == null) {
            throw new Exceptions.UserNotFoundException();
        }
        String emailId = Utils.extractEmailIdFromCred(cred);
       // String token = UUID.randomUUID().toString();
        long otp = new Random().nextInt(999999);
        String token = String.format("%06d", otp);
        PasswordResetToken myToken = passwordResetTokenRepository.findByUserName(cred.getUserName());
        if(myToken != null && Date.from(myToken.getExpiryDate().toInstant().plus(Duration.ofHours(24))).before(new Date()))
        {// earlier token is already 24 hrs ago.
            passwordResetTokenRepository.deleteByUserName(myToken.getUserName());// delete stale Token
            myToken = null; // to create new token.
        }
        else if(myToken != null && myToken.getInvalidAttempts() > PasswordResetToken.MAX_ATTEMPTS)
        {
            throw new Exceptions.TooManyAttemptsException();
        }

        if(myToken == null) {
            myToken = new PasswordResetToken();
            myToken.setUserName(cred.getUserName());
            myToken.setInvalidAttempts(1);
        }
        myToken.setToken(token+"");
        myToken.setExpiryDate(new Date(Calendar.getInstance().getTimeInMillis() +  (PasswordResetToken.EXPIRATION)) );
        int attempts = myToken.getInvalidAttempts();
        myToken.setInvalidAttempts(attempts + 1);
        passwordResetTokenRepository.save(myToken);
        emailService.sendSimpleMessage(emailId,"Your Code for Doc Support Change Password","One Time Unique Code is : "+token);
        return ;
    }

    @PostMapping("/newpassword")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void createNewPassword(@RequestBody ChgPwdRequest chgPwdRequest) {

        List<PasswordResetToken> passwordResetTokenList = passwordResetTokenRepository.findByToken(chgPwdRequest.getToken());
        for (PasswordResetToken passwordResetToken: passwordResetTokenList) {
            if(passwordResetToken.getUserName().equalsIgnoreCase(chgPwdRequest.getUserName())) {
                if(passwordResetToken.getExpiryDate().after(new Date())) {
                    Credential cred = credentialRepository.findByUserName(passwordResetToken.getUserName());
                    String emailId  = Utils.extractEmailIdFromCred(cred);
                    cred.setPassword(new BCryptPasswordEncoder().encode(chgPwdRequest.getNewPassword()));
                    credentialRepository.save(cred);
                    passwordResetTokenRepository.deleteByUserName(cred.getUserName());
                    emailService.sendSimpleMessage(emailId, "Your Doc Support Change Password is Changed", "Your Doc Support Change Password is Changed Successfully ");
                    return;
                }
            }
        }
        throw new Exceptions.InvalidUserOrTokenException();
    }
    @PostMapping("/changepassword")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void changePassword(@RequestBody ChgPwdRequest chgPwdRequest) {
        Credential cred = credentialRepository.findByUserName(chgPwdRequest.getUserName());
        String emailId  = Utils.extractEmailIdFromCred(cred);
        if(cred.getPassword().equals(new BCryptPasswordEncoder().encode(chgPwdRequest.getOldPassword()))) {
            cred.setPassword(new BCryptPasswordEncoder().encode(chgPwdRequest.getNewPassword()));
            credentialRepository.save(cred);
            emailService.sendSimpleMessage(emailId, "Your Doc Support Change Password is Changed", "Your Doc Support Change Password is Changed Successfully ");
            return;
        }
        else
        {
            throw new Exceptions.InvalidUserOrTokenException();
        }
    }

    @GetMapping("/login")
    public Credential userdetails(@AuthenticationPrincipal UserDetails userDetails) {
        String userName = userDetails.getUsername();
        Credential cred = credentialRepository.findByUserName(userName);
        if (cred == null) {
            throw new Exceptions.UserNotFoundException();
        }
        return cred;
    }

    @GetMapping("/health")
    public String health() {
        return "Hello & Welcome to DocSupport!!!";
    }

    @PostMapping("/apply/{jobId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void apply(@PathVariable Long jobId, @AuthenticationPrincipal UserDetails userDetails) {
        String userName = userDetails.getUsername();
        Credential cred = credentialRepository.findByUserName(userName);
        if (cred == null) {
            throw new Exceptions.UserNotFoundException();
        }
        if(!"USER".equals(cred.getRoleType()))
        {
            //throw new Exceptions.InvalidUserInfoException();
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Only User can Apply for Job");
        }
        Application appl = new Application();
        Job job = new Job();
        job.setJobId(jobId);
        Person p = new Person();
        p.setUserId(cred.getUserId());
        appl.setJob(job);
        appl.setPerson(p);
        try {
            applicationRepository.save(appl);
        }
        catch(Exception e)
        {
            System.out.println(e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Unable to save the Application");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            error.put(fieldName, errorMessage);
        });
        return error;
    }



//    @PostMapping("/generatecode")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Person generateCode(@RequestBody String userName) {
//
//        String pwd = person.getPassword();
//        person.setPassword(new BCryptPasswordEncoder().encode(pwd));
//        person = personRepository.save(person);
//        return person;
//    }





//    @GetMapping
//    public List<Foo> findAll() {
//        return service.findAll();
//    }

//    @GetMapping(value = "/{id}")
//    public Foo findById(@PathVariable("id") Long id) {
//        return RestPreconditions.checkFound(service.findById(id));
//    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Long create(@RequestBody Foo resource) {
//        Preconditions.checkNotNull(resource);
//        return service.create(resource);
//    }

//    @PutMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void update(@PathVariable( "id" ) Long id, @RequestBody Foo resource) {
//        Preconditions.checkNotNull(resource);
//        RestPreconditions.checkNotNull(service.getById(resource.getId()));
//        service.update(resource);
//    }

//    @DeleteMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void delete(@PathVariable("id") Long id) {
//        service.deleteById(id);
//    }

}
