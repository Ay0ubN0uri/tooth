package com.a00n.web.rest;

import com.a00n.config.Constants;
import com.a00n.domain.Professor;
import com.a00n.domain.Student;
import com.a00n.domain.User;
import com.a00n.repository.ProfessorRepository;
import com.a00n.repository.StudentRepository;
import com.a00n.repository.UserRepository;
import com.a00n.security.AuthoritiesConstants;
import com.a00n.security.SecurityUtils;
import com.a00n.service.MailService;
import com.a00n.service.UserService;
import com.a00n.service.dto.AdminUserDTO;
import com.a00n.service.dto.PasswordChangeDTO;
import com.a00n.service.dto.ProfessorDTO;
import com.a00n.service.dto.StudentDTO;
import com.a00n.web.rest.errors.*;
import com.a00n.web.rest.vm.CheckVM;
import com.a00n.web.rest.vm.KeyAndPasswordVM;
import com.a00n.web.rest.vm.ManagedUserVM;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;

    private String uploadDir;

    public AccountResource(
        UserRepository userRepository,
        UserService userService,
        MailService mailService,
        ProfessorRepository professorRepository,
        StudentRepository studentRepository
    ) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * {@code POST  /register} : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordException  {@code 400 (Bad Request)} if the password
     *                                   is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is
     *                                   already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is
     *                                   already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (isPasswordLengthInvalid(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
    }

    /**
     * {@code GET  /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user
     *                          couldn't be activated.
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user
     *                          couldn't be returned.
     */
    @GetMapping("/account")
    public AdminUserDTO getAccount(HttpServletRequest request) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new AccountResourceException("User could not be found"));
        System.out.println("=====================================================");
        System.out.println(user.hasRole(AuthoritiesConstants.PROFESSOR));
        uploadDir = request.getServletContext().getRealPath(Constants.UPLOAD_DIRECTORY);
        var image = convertImageToBase64(uploadDir + File.separator + user.getImageUrl());
        if (image == null) {
            image = convertImageToBase64(uploadDir + File.separator + "user.png");
        }
        if (user.hasRole(AuthoritiesConstants.ADMIN)) {
            var tmp = new AdminUserDTO(user);
            tmp.setImage(image);
            return tmp;
        } else if (user.hasRole(AuthoritiesConstants.PROFESSOR)) {
            Professor professor = professorRepository
                .findByUser(user)
                .orElseThrow(() -> new AccountResourceException("User could not be found"));
            System.out.println(professor);
            ProfessorDTO professorDTO = new ProfessorDTO(user, professor);
            System.out.println(professorDTO);
            professorDTO.setImage(image);
            return professorDTO;
        }
        Student student = studentRepository.findByUser(user).orElseThrow(() -> new AccountResourceException("User could not be found"));
        StudentDTO studentDTO = new StudentDTO(user, student);
        studentDTO.setImage(image);
        return studentDTO;
    }

    public static byte[] convertImageToBase64(String imagePath) {
        try {
            byte[] imageBytes = Files.readAllBytes(Path.of(imagePath));

            String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);

            return imageBytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // @GetMapping("/account")
    // public AdminUserDTO getAccount() {
    // return userService
    // .getUserWithAuthorities()
    // .map(AdminUserDTO::new)
    // .orElseThrow(() -> new AccountResourceException("User could not be found"));
    // }

    @GetMapping("/a00n")
    public String a00n() {
        return "hello all";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("image") MultipartFile file, HttpServletRequest request) throws IOException {
        byte[] image = Files.readAllBytes(Paths.get("/home/ay0ub/Desktop/ayoub nouri.jpg"));
        uploadDir = request.getServletContext().getRealPath(Constants.UPLOAD_DIRECTORY);
        // uploadDir = "/home/ay0ub/Desktop/spring
        // projects/tooth/src/main/webapp/content/images/uploads";
        this.createFileIfNotExists();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        String fileName = file.getOriginalFilename();
        System.out.println(image.length);
        System.out.println(uploadDir);
        fileName = System.currentTimeMillis() + "_" + fileName;
        System.out.println(fileName);
        Path destPath = Path.of(uploadDir, fileName);
        Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);
        return "hello all";
    }

    private void createFileIfNotExists() {
        if (!Files.exists(Paths.get(uploadDir))) {
            try {
                Files.createDirectories(Paths.get(uploadDir));
                System.out.println("Folder created successfully.");
            } catch (Exception e) {
                System.err.println("Error creating folder: " + e.getMessage());
            }
        } else {
            System.out.println("Folder already exists.");
        }
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws IOException
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is
     *                                   already used.
     * @throws RuntimeException          {@code 500 (Internal Server Error)} if the
     *                                   user login wasn't found.
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody AdminUserDTO userDTO) throws IOException {
        System.out.println("++++++++++++++++++++++++++++++++++++++");
        System.out.println(userDTO instanceof StudentDTO);
        System.out.println(userDTO instanceof ProfessorDTO);
        System.out.println(userDTO);
        byte[] decodedBytes = userDTO.getImage();
        String fileName = System.currentTimeMillis() + "_" + userDTO.getImageUrl();
        var filePath = uploadDir + File.separator + fileName;
        System.out.println(filePath);
        this.saveProfileImage(filePath, decodedBytes);
        userDTO.setImageUrl(fileName);
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.orElseThrow().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(
            userDTO.getFirstName(),
            userDTO.getLastName(),
            userDTO.getEmail(),
            userDTO.getLangKey(),
            userDTO.getImageUrl()
        );
    }

    @PostMapping("/account/professor")
    public void saveProfessorAccount(@Valid @RequestBody ProfessorDTO userDTO) throws IOException {
        // System.out.println("++++++++++++++++++++++++++++++++++++++");
        // System.out.println(userDTO);
        byte[] decodedBytes = userDTO.getImage();
        String fileName = System.currentTimeMillis() + "_" + userDTO.getImageUrl();
        var filePath = uploadDir + File.separator + fileName;
        System.out.println(filePath);
        this.saveProfileImage(filePath, decodedBytes);
        userDTO.setImageUrl(fileName);
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.orElseThrow().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        User user = userRepository.findOneByLogin(userLogin).orElseThrow(() -> new AccountResourceException("User could not be found"));
        // Optional<User> user = userRepository.findOneByLogin(userLogin);
        // if (!user.isPresent()) {
        // throw new AccountResourceException("User could not be found");
        // }
        Professor prof = professorRepository.findByUser(user).orElseThrow(() -> new AccountResourceException("User could not be found"));
        prof.setGrade(userDTO.getGrade());
        userService.updateProfUser(
            userDTO.getFirstName(),
            userDTO.getLastName(),
            userDTO.getEmail(),
            userDTO.getLangKey(),
            userDTO.getImageUrl(),
            prof
        );
    }

    @PostMapping("/account/student")
    public void saveStudentAccount(@Valid @RequestBody StudentDTO userDTO, HttpServletRequest request) throws IOException {
        uploadDir = request.getServletContext().getRealPath(Constants.UPLOAD_DIRECTORY);
        this.createFileIfNotExists();
        // System.out.println("++++++++++++++++++++++++++++++++++++++");
        // System.out.println(userDTO);
        System.out.println(uploadDir);
        byte[] decodedBytes = userDTO.getImage();
        String fileName = System.currentTimeMillis() + "_" + userDTO.getImageUrl();
        var filePath = uploadDir + File.separator + fileName;
        System.out.println(filePath);
        this.saveProfileImage(filePath, decodedBytes);
        userDTO.setImageUrl(fileName);
        String userLogin = SecurityUtils
            .getCurrentUserLogin()
            .orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.orElseThrow().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        User user = userRepository.findOneByLogin(userLogin).orElseThrow(() -> new AccountResourceException("User could not be found"));
        // Optional<User> user = userRepository.findOneByLogin(userLogin);
        // if (!user.isPresent()) {
        // throw new AccountResourceException("User could not be found");
        // }
        Student student = studentRepository.findByUser(user).orElseThrow(() -> new AccountResourceException("User could not be found"));
        System.out.println(student);
        student.setCin(userDTO.getCin());
        student.setCne(userDTO.getCne());
        student.setBirthDay(userDTO.getBirthDay());
        userService.updateStudentUser(
            userDTO.getFirstName(),
            userDTO.getLastName(),
            userDTO.getEmail(),
            userDTO.getLangKey(),
            userDTO.getImageUrl(),
            student
        );
    }

    public void saveProfileImage(String filePath, byte[] decodedBytes) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(decodedBytes);
        }
    }

    /**
     * {@code POST  /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new
     *                                  password is incorrect.
     */
    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (isPasswordLengthInvalid(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the
     * password of the user.
     *
     * @param mail the mail of the user.
     */
    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
        Optional<User> user = userService.requestPasswordReset(mail);
        if (user.isPresent()) {
            mailService.sendPasswordResetMail(user.orElseThrow());
        } else {
            // Pretend the request has been successful to prevent checking which emails
            // really exist
            // but log that an invalid attempt has been made
            log.warn("Password reset requested for non existing mail");
        }
    }

    @PostMapping(path = "/account/reset-password/init/mobile")
    public void requestPasswordResetMobile(@RequestBody String mail) {
        Optional<User> user = userService.requestPasswordResetMobile(mail);
        if (user.isPresent()) {
            mailService.sendPasswordResetMailMobile(user.orElseThrow());
        } else {
            // Pretend the request has been successful to prevent checking which emails
            // really exist
            // but log that an invalid attempt has been made
            log.warn("Password reset requested for non existing mail");
        }
    }

    /**
     * {@code POST   /account/reset-password/finish} : Finish to reset the password
     * of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is
     *                                  incorrect.
     * @throws RuntimeException         {@code 500 (Internal Server Error)} if the
     *                                  password could not be reset.
     */
    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        System.out.println("=======================================");
        System.out.println(keyAndPassword);
        if (isPasswordLengthInvalid(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user = userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
    }

    @PostMapping(path = "/account/reset-password/check")
    public void isMobileCodeValidForEmail(@RequestBody CheckVM checkVM) {
        System.out.println(checkVM);
        var user = userRepository.findOneByEmailIgnoreCase(checkVM.getEmail()).orElse(null);
        System.out.println(user);
        if (user != null) {
            if (user.getResetKey() == null || !user.getResetKey().equals(checkVM.getKey())) {
                throw new BadRequestAlertException("Invalid Key", "Check", "idexists");
            }
        } else {
            throw new BadRequestAlertException("Invalid Email", "Check", "idexists");
        }
        // throw new BadRequestAlertException("Invalid Key", "Check", "idexists");
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
            StringUtils.isEmpty(password) ||
            password.length() < ManagedUserVM.PASSWORD_MIN_LENGTH ||
            password.length() > ManagedUserVM.PASSWORD_MAX_LENGTH
        );
    }
}
