package com.xsolar.Xsolar.service;

import com.xsolar.Xsolar.model.Installer;
import com.xsolar.Xsolar.repository.InstallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstallerService {
    @Autowired
    private InstallerRepository installerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean checkInstallerRole (String role) {
        if (role.equals("INSTALLER")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUserExistence(String installerEmail) {
        Optional<Installer> existingInstaller = Optional.ofNullable(installerRepository.findByEmail(installerEmail));
        // Check if the customer with the given ID exists and if their status is active
        return existingInstaller.isPresent() && existingInstaller.get().getStatusactive();
    }

    public boolean deleteInstallerById(Integer installerId) {
        // Check if the installer exists
        Optional<Installer> installerOptional = installerRepository.findById(installerId);
        if (installerOptional.isPresent()) {
            // Installer exists, delete it
            installerRepository.deleteById(installerId);
            return true; // Deletion successful
        } else {
            return false; // Installer not found
        }
    }

    public boolean disableInstaller(Integer installerid) {
        Optional<Installer> installerOptional = installerRepository.findById(installerid);
        if (installerOptional.isPresent()) {
            Installer installer = installerOptional.get();
            installer.setStatusactive(Boolean.valueOf(false));
            installerRepository.save(installer); // Save the updated homebase
            return true; // Update successful
        } else {
            return false; // Homebase not found
        }
    }

    public boolean enableInstaller(Integer installerid) {
        Optional<Installer> installerOptional = installerRepository.findById(installerid);
        if (installerOptional.isPresent()) {
            Installer installer = installerOptional.get();
            installer.setStatusactive(Boolean.valueOf(true));
            installerRepository.save(installer); // Save the updated homebase
            return true; // Update successful
        } else {
            return false; // Homebase not found
        }
    }

    public Boolean updateInstallerInformation (Installer installer) {

        Installer existingUser = installerRepository.findByEmail(installer.getEmail());
        if (existingUser != null) {
            existingUser.setUsername(installer.getUsername());
            // Not change Password.
            existingUser.setPassword(passwordEncoder.encode(installer.getPassword()));
            // Not change Email, Email is Default
            // existingUser.setEmail(installer.getEmail());
            existingUser.setFullname(installer.getFullname());
            existingUser.setRole(installer.getRole());
            existingUser.setPhonenumber(installer.getPhonenumber());
            installerRepository.save(existingUser);
            return true;
        } else {
            return false;
        }
    }



}
