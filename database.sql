DROP TABLE IF EXISTS controls;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);


CREATE TABLE controls
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    impact INTEGER NOT NULL,
    likelihood INTEGER NOT NULL,
    owner INT NOT NULL,
    FOREIGN KEY (owner) REFERENCES users(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE audit_results
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    control_id INT NOT NULL,
    result VARCHAR(255) NOT NULL CHECK (result IN ('PASS', 'FAIL')),
    date DATE NOT NULL,
    FOREIGN KEY (control_id) REFERENCES controls(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO users (username, password) VALUES
   ('admin', 'admin123'),
   ('jane', 'password'),
   ('john', 'qwerty');

INSERT INTO controls (name, description, impact, likelihood, owner) VALUES
    ('Access Control', 'Restrict access to admin features', 4, 3, 1),
    ('Backup Procedure', 'Daily database backup', 3, 2, 2),
    ('Network Security', 'Firewall and VPN enforcement', 5, 4, 1);

INSERT INTO audit_results (control_id, result, date) VALUES
     (1, 'PASS', '2025-01-10'),
     (1, 'FAIL', '2025-03-15'),
     (1, 'PASS', '2025-06-20'),
     (2, 'PASS', '2025-02-05'),
     (2, 'PASS', '2025-05-08'),
     (3, 'FAIL', '2025-01-25'),
     (3, 'FAIL', '2025-04-30'),
     (3, 'PASS', '2025-09-12');