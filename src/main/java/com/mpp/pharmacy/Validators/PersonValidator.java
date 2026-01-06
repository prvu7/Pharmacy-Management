package com.mpp.pharmacy.Validators;

import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Enum.Role;
import com.mpp.pharmacy.Enum.Sex;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class PersonValidator {

    // Valid values for enumerated fields
    private static final List<String> VALID_SEX_VALUES = Arrays.asList("M", "F", "O");
    private static final List<String> VALID_ROLES = Arrays.asList("patient", "doctor", "pharmacist");

    // Business rule constants
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 150;
    private static final int MAX_ADDRESS_LENGTH = 200;

    // Regex patterns for validation
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?[0-9]{10,15}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s'-]+$");

    // Date format for parsing
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd

    public void validateForCreation(Person person) {
        ValidationResult result = new ValidationResult();

        if (person == null) {
            throw new InvalidRequestException("Person object cannot be null");
        }

        // Validate required fields
        validateFirstName(person.getFirstName(), result, true);
        validateLastName(person.getLastName(), result, true);
        validateSex(person.getSex(), result, true);
        validateDateOfBirth(person.getDateOfBirth(), result, true);
        validatePhone(person.getPhone(), result, true);
        validateEmail(person.getEmail(), result, true);
        validateAddress(person.getAddress(), result, false); // Address is optional
        validateRole(person.getRole(), result, true);

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    public void validateForUpdate(Person person) {
        ValidationResult result = new ValidationResult();

        if (person == null) {
            throw new InvalidRequestException("Person object cannot be null");
        }

        if (person.getPersonId() == null) {
            result.addError("Person ID is required for update operations");
        }

        // Validate only non-null fields
        if (person.getFirstName() != null) {
            validateFirstName(person.getFirstName(), result, false);
        }
        if (person.getLastName() != null) {
            validateLastName(person.getLastName(), result, false);
        }
        if (person.getSex() != null) {
            validateSex(person.getSex(), result, false);
        }
        if (person.getDateOfBirth() != null) {
            validateDateOfBirth(person.getDateOfBirth(), result, false);
        }
        if (person.getPhone() != null) {
            validatePhone(person.getPhone(), result, false);
        }
        if (person.getEmail() != null) {
            validateEmail(person.getEmail(), result, false);
        }
        if (person.getAddress() != null) {
            validateAddress(person.getAddress(), result, false);
        }
        if (person.getRole() != null) {
            validateRole(person.getRole(), result, false);
        }

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    // Individual field validators

    private void validateFirstName(String firstName, ValidationResult result, boolean required) {
        if (firstName == null || firstName.trim().isEmpty()) {
            if (required) {
                result.addError("First name is required");
            }
            return;
        }

        String trimmedName = firstName.trim();
        if (trimmedName.length() < MIN_NAME_LENGTH) {
            result.addError(String.format("First name must be at least %d characters long", MIN_NAME_LENGTH));
        }
        if (trimmedName.length() > MAX_NAME_LENGTH) {
            result.addError(String.format("First name must not exceed %d characters", MAX_NAME_LENGTH));
        }
        if (!NAME_PATTERN.matcher(trimmedName).matches()) {
            result.addError("First name can only contain letters, spaces, hyphens, and apostrophes");
        }
    }

    private void validateLastName(String lastName, ValidationResult result, boolean required) {
        if (lastName == null || lastName.trim().isEmpty()) {
            if (required) {
                result.addError("Last name is required");
            }
            return;
        }

        String trimmedName = lastName.trim();
        if (trimmedName.length() < MIN_NAME_LENGTH) {
            result.addError(String.format("Last name must be at least %d characters long", MIN_NAME_LENGTH));
        }
        if (trimmedName.length() > MAX_NAME_LENGTH) {
            result.addError(String.format("Last name must not exceed %d characters", MAX_NAME_LENGTH));
        }
        if (!NAME_PATTERN.matcher(trimmedName).matches()) {
            result.addError("Last name can only contain letters, spaces, hyphens, and apostrophes");
        }
    }

    private void validateSex(Sex sex, ValidationResult result, boolean required) {
        if (sex == null || sex.name().trim().isEmpty()) {
            if (required) {
                result.addError("Sex is required");
            }
            return;
        }

        String normalizedSex = sex.name().trim().toUpperCase();
        if (!VALID_SEX_VALUES.contains(normalizedSex)) {
            result.addError(String.format("Invalid sex value '%s'. Valid values: %s",
                    sex, String.join(", ", VALID_SEX_VALUES)));
        }
    }

    private void validateDateOfBirth(LocalDate dateOfBirth, ValidationResult result, boolean required) {
        if (dateOfBirth == null) {
            if (required) {
                result.addError("Date of birth is required");
            }
            return;
        }

        try {
            LocalDate today = LocalDate.now();

            // Check if date is in the future
            if (dateOfBirth.isAfter(today)) {
                result.addError("Date of birth cannot be in the future");
                return;
            }

            // Calculate age
            int age = today.getYear() - dateOfBirth.getYear();
            if (today.getDayOfYear() < dateOfBirth.getDayOfYear()) {
                age--;
            }

            // Validate age range
            if (age < MIN_AGE) {
                result.addError("Invalid date of birth");
            }
            if (age > MAX_AGE) {
                result.addError(String.format("Age cannot exceed %d years", MAX_AGE));
            }

        } catch (DateTimeParseException e) {
            result.addError("Invalid date format. Expected format: yyyy-MM-dd (e.g., 1990-01-15)");
        }
    }

    private void validatePhone(String phone, ValidationResult result, boolean required) {
        if (phone == null || phone.trim().isEmpty()) {
            if (required) {
                result.addError("Phone number is required");
            }
            return;
        }

        // Remove common formatting characters for validation
        String cleanedPhone = phone.replaceAll("[\\s()-]", "");

        if (!PHONE_PATTERN.matcher(cleanedPhone).matches()) {
            result.addError("Invalid phone number format. Must be 10-15 digits, optionally starting with '+'");
        }
    }

    private void validateEmail(String email, ValidationResult result, boolean required) {
        if (email == null || email.trim().isEmpty()) {
            if (required) {
                result.addError("Email is required");
            }
            return;
        }

        String trimmedEmail = email.trim();
        if (!EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            result.addError("Invalid email format");
        }
        if (trimmedEmail.length() > 100) {
            result.addError("Email must not exceed 100 characters");
        }
    }

    private void validateAddress(String address, ValidationResult result, boolean required) {
        if (address == null || address.trim().isEmpty()) {
            if (required) {
                result.addError("Address is required");
            }
            return;
        }

        String trimmedAddress = address.trim();
        if (trimmedAddress.length() > MAX_ADDRESS_LENGTH) {
            result.addError(String.format("Address must not exceed %d characters", MAX_ADDRESS_LENGTH));
        }
    }

    private void validateRole(Role role, ValidationResult result, boolean required) {
        if (role == null || role.name().trim().isEmpty()) {
            if (required) {
                result.addError("Role is required");
            }
            return;
        }

        String normalizedRole = role.name().trim().toLowerCase();
        if (!VALID_ROLES.contains(normalizedRole)) {
            result.addError(String.format("Invalid role '%s'. Valid roles: %s",
                    role, String.join(", ", VALID_ROLES)));
        }
    }

    public void validateRoleForAction(Person person, String requiredRole, String action) {
        if (person == null) {
            throw new InvalidRequestException("Person cannot be null");
        }

        if (person.getRole() == null || person.getRole().name().trim().isEmpty()) {
            throw new InvalidRequestException("Person role is not set");
        }

        String normalizedRole = person.getRole().name().trim().toLowerCase();
        String normalizedRequired = requiredRole.trim().toLowerCase();

        if (!normalizedRole.equals(normalizedRequired)) {
            throw new InvalidRequestException(
                    String.format("Only %ss can %s. Current role: %s",
                            requiredRole, action, person.getRole())
            );
        }
    }

    public void validateMinimumAge(Person person, int minimumAge, String reason) {
        if (person == null || person.getDateOfBirth() == null) {
            throw new InvalidRequestException("Cannot validate age: person or date of birth is null");
        }

        try {
            LocalDate dob = person.getDateOfBirth();
            LocalDate today = LocalDate.now();

            int age = today.getYear() - dob.getYear();
            if (today.getDayOfYear() < dob.getDayOfYear()) {
                age--;
            }

            if (age < minimumAge) {
                throw new InvalidRequestException(
                        String.format("Person must be at least %d years old %s. Current age: %d",
                                minimumAge, reason, age)
                );
            }
        } catch (DateTimeParseException e) {
            throw new InvalidRequestException("Invalid date of birth format");
        }
    }

    private static class ValidationResult {
        private final List<String> errors = new ArrayList<>();

        public void addError(String error) {
            errors.add(error);
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }

        public String getErrorMessage() {
            return String.join("; ", errors);
        }

        public List<String> getErrors() {
            return new ArrayList<>(errors);
        }
    }
}