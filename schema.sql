-- =====================================================================
--  MediKiosk - Patient Case-Taking Software (student prototype)
--  Database setup script for MySQL 8.x
--  Run:  mysql -u root -p < database/schema.sql
--
--  ALL SAMPLE DATA BELOW IS FICTIONAL. No real patient is represented.
-- =====================================================================

DROP DATABASE IF EXISTS medikiosk;
CREATE DATABASE medikiosk CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE medikiosk;

-- ---------------------------------------------------------------------
-- users : staff who can log in (Administrator or Doctor)
-- ---------------------------------------------------------------------
CREATE TABLE users (
    user_id        INT AUTO_INCREMENT PRIMARY KEY,
    username       VARCHAR(50)  NOT NULL,
    password_hash  VARCHAR(255) NOT NULL,           -- PBKDF2$iterations$saltHex$hashHex (never plaintext)
    full_name      VARCHAR(100) NOT NULL,
    role           ENUM('ADMIN','DOCTOR') NOT NULL,
    is_active      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_users_username UNIQUE (username)  -- duplicate-account protection
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- patients : demographic data (one row per patient)
-- patient_id is auto-generated; it starts at 1001 so IDs look realistic.
-- ---------------------------------------------------------------------
CREATE TABLE patients (
    patient_id               INT AUTO_INCREMENT PRIMARY KEY,
    full_name                VARCHAR(100) NOT NULL,
    date_of_birth            DATE         NOT NULL,
    gender                   ENUM('MALE','FEMALE','OTHER') NOT NULL,
    phone                    VARCHAR(15)  NOT NULL,
    address                  VARCHAR(255) NOT NULL,
    preferred_language       VARCHAR(40)  NOT NULL,
    emergency_contact_name   VARCHAR(100) NULL,
    emergency_contact_phone  VARCHAR(15)  NULL,
    created_by               INT          NULL,
    created_at               TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_patients_created_by FOREIGN KEY (created_by)
        REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_patients_name  (full_name),
    INDEX idx_patients_phone (phone)
) ENGINE=InnoDB AUTO_INCREMENT=1001;

-- ---------------------------------------------------------------------
-- medical_histories : many history records per patient (one per visit)
-- ---------------------------------------------------------------------
CREATE TABLE medical_histories (
    history_id            INT AUTO_INCREMENT PRIMARY KEY,
    patient_id            INT          NOT NULL,
    recorded_by           INT          NULL,
    chief_complaint       VARCHAR(500) NOT NULL,
    symptoms              TEXT         NOT NULL,
    symptom_duration      VARCHAR(100) NOT NULL,
    past_medical_history  TEXT NULL,
    previous_surgeries    TEXT NULL,
    current_medications   TEXT NULL,
    known_allergies       TEXT NULL,
    family_history        TEXT NULL,
    personal_history      TEXT NULL,
    previous_investigations TEXT NULL,
    additional_notes      TEXT NULL,
    created_at            DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,   -- consultation date/time
    CONSTRAINT fk_history_patient FOREIGN KEY (patient_id)
        REFERENCES patients(patient_id) ON DELETE CASCADE,
    CONSTRAINT fk_history_user FOREIGN KEY (recorded_by)
        REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_history_patient (patient_id),
    INDEX idx_history_date    (created_at)
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- medical_documents : metadata + file location only (file stays on disk)
-- ---------------------------------------------------------------------
CREATE TABLE medical_documents (
    document_id        INT AUTO_INCREMENT PRIMARY KEY,
    patient_id         INT          NOT NULL,
    document_type      ENUM('PRESCRIPTION','LAB_REPORT','DISCHARGE_SUMMARY') NOT NULL,
    original_file_name VARCHAR(255) NOT NULL,
    stored_file_path   VARCHAR(500) NOT NULL,
    file_extension     VARCHAR(10)  NOT NULL,
    file_size_bytes    BIGINT       NOT NULL,
    description        VARCHAR(255) NULL,
    uploaded_by        INT          NULL,
    uploaded_at        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_document_patient FOREIGN KEY (patient_id)
        REFERENCES patients(patient_id) ON DELETE CASCADE,
    CONSTRAINT fk_document_user FOREIGN KEY (uploaded_by)
        REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_document_patient (patient_id)
) ENGINE=InnoDB;

-- =====================================================================
--  SAMPLE DATA (FICTIONAL - for demonstration only)
--  Demo logins:  admin / Admin@123      doctor1 / Doctor@123     doctor2 / Doctor@456
--  Change these passwords before showing the project outside your lab.
-- =====================================================================
INSERT INTO users (username, password_hash, full_name, role) VALUES
('admin',   'PBKDF2$120000$e620edb641cbce55745c49a4f797f6d2$384c58184aaf05cd26f8555ec0393203c5a5f7f1792a085390b680ab2e2f33b7', 'Demo Administrator',   'ADMIN'),
('doctor1', 'PBKDF2$120000$92868bf209a5bcaad553562c1c305a76$82eb6ea9e884119882acde383f0b65fbe820755e0eb81a10be39bb73b69e33b4', 'Dr. Asha Kulkarni (demo)', 'DOCTOR'),
('doctor2', 'PBKDF2$120000$c5619adb14e779d46cb29c26dfeb1004$4969c268506e073b518280cdc89c0f240a84799339f99c2a87a1c73152f1fcd3', 'Dr. Rohan Mehta (demo)',  'DOCTOR');

INSERT INTO patients (full_name, date_of_birth, gender, phone, address, preferred_language,
                      emergency_contact_name, emergency_contact_phone, created_by) VALUES
('Sample Patient One',   '1985-04-12', 'FEMALE', '9000000001', '12 Fictional Lane, Sampletown', 'Marathi', 'Sample Relative A', '9000000101', 1),
('Sample Patient Two',   '1972-11-03', 'MALE',   '9000000002', '45 Imaginary Road, Sampletown', 'Hindi',   'Sample Relative B', '9000000102', 1),
('Sample Patient Three', '1999-07-25', 'OTHER',  '9000000003', '7 Example Street, Sampletown',  'English', NULL, NULL, 1);

INSERT INTO medical_histories (patient_id, recorded_by, chief_complaint, symptoms, symptom_duration,
    past_medical_history, previous_surgeries, current_medications, known_allergies,
    family_history, personal_history, previous_investigations, additional_notes, created_at) VALUES
(1001, 2, 'Recurring headache', 'Throbbing headache, mild nausea, light sensitivity', '2 weeks',
    'Migraine (fictional)', 'None', 'Paracetamol 500 mg as needed', 'Penicillin',
    'Mother has migraine', 'Non-smoker, vegetarian diet', 'None done', 'Sample record only.', '2026-09-15 10:30:00'),
(1001, 2, 'Follow-up: headache', 'Headache frequency reduced', '4 weeks',
    'Migraine (fictional)', 'None', 'Paracetamol 500 mg as needed', 'Penicillin',
    'Mother has migraine', 'Non-smoker, vegetarian diet', 'None done', 'Sample follow-up.', '2026-10-01 11:00:00'),
(1002, 3, 'Chest discomfort on climbing stairs', 'Tightness in chest, breathlessness', '3 days',
    'Hypertension (fictional)', 'Appendectomy, 1995', 'Amlodipine 5 mg daily', 'No known allergies',
    'Father had heart disease', 'Occasional alcohol', 'ECG advised, not yet done', 'Sample record only.', '2026-10-05 09:15:00');
