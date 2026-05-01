package com.tutorial.rest_api.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private UserDetailsRepository userDetailsRepository;

    public UserDetailsCommandLineRunner(UserDetailsRepository userDetailsRepository) {
        super();
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // logger.info(Arrays.toString(args));

        userDetailsRepository.save(new UserDetails("anhtu", "admin"));
        userDetailsRepository.save(new UserDetails("phuongtrinh", "admin"));
        userDetailsRepository.save(new UserDetails("tranlebao", "user"));
        userDetailsRepository.save(new UserDetails("tranhuynhi", "user"));
        userDetailsRepository.save(new UserDetails("buihoangyen", "user"));
        userDetailsRepository.save(new UserDetails("tranhaiha", "user"));
        userDetailsRepository.save(new UserDetails("vongocanh", "user"));
        userDetailsRepository.save(new UserDetails("nguyenthikimanh", "user"));

        List<UserDetails> users = userDetailsRepository.findAll();
        // List<UserDetails> users = userDetailsRepository.findByRole("user");
        users.forEach(user -> logger.info(user.toString()));

        // List<UserDetails> user = userDetailsRepository.findByName("anhtu");
        // logger.info("User: {} ", user);
    }

}
