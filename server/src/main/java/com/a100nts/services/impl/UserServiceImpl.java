package com.a100nts.services.impl;

import com.a100nts.models.Site;
import com.a100nts.models.User;
import com.a100nts.repositories.SiteRepository;
import com.a100nts.repositories.UserRepository;
import com.a100nts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.a100nts.utils.ListToStringConverter.DATE_TIME_FORMATTER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User userDB = userRepository.findById(user.getId()).get();
        userDB.setFirstName(user.getFirstName());
        userDB.setLastName(user.getLastName());
        userDB.setEmail(user.getEmail());
        if (!user.getPassword().isBlank()) {
            userDB.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDB.setRanking(user.isRanking());
        return userRepository.save(userDB);
    }

    @Override
    @Transactional
    public User visitSites(Long userId, Long[] siteIds) {
        User user = userRepository.findById(userId).get();
        List<Site> sites = new ArrayList<>(user.getSites());
        List<Site> newSites = Arrays.stream(siteIds)
                .map(s -> siteRepository.findById(s).get())
                .collect(Collectors.toList());
        sites.addAll(newSites);
        user.setSites(sites);
        List<String> sitesTime = new ArrayList<>(user.getSitesTime());
        for (Site site : newSites) {
            sitesTime.add(site.getId() + "+" + LocalDateTime.now().format(DATE_TIME_FORMATTER));
        }
        user.setSitesTime(sitesTime);
        return userRepository.save(user);
    }

}
