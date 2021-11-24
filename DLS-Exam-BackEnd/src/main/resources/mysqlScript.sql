# CREATE USER 'test1234'@'localhost' IDENTIFIED BY 'test1234';
# GRANT ALL PRIVILEGES ON StudentAttendance.* TO 'test1234'@'localhost';
# DROP TABLE student, teacher, subject, session, attendance;

CREATE schema IF NOT EXISTS attendance;

USE attendance;

CREATE TABLE IF NOT EXISTS student
(
    student_id		INT		   		PRIMARY KEY		AUTO_INCREMENT,
    first_name		VARCHAR(100)	NOT NULL,
    last_name		VARCHAR(100)	NOT NULL,
    mail			VARCHAR(100)	NOT NULL,
    password		VARCHAR(50)		NOT NULL,
    birthdate		VARCHAR(20)		NOT NULL
    );

CREATE TABLE IF NOT EXISTS teacher
(
    teacher_id		INT				PRIMARY KEY		AUTO_INCREMENT,
    first_name		VARCHAR(100)	NOT NULL,
    last_name		VARCHAR(100)	NOT NULL,
    mail			VARCHAR(100)	NOT NULL,
    password		VARCHAR(100)	NOT NULL
    );

CREATE TABLE IF NOT EXISTS subject
(
    subject_id			INT				PRIMARY KEY		AUTO_INCREMENT,
    subject_name		VARCHAR(100)	NOT NULL,
    num_of_students		INT				NOT NULL,
    teacher_id			INT,
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
    );

CREATE TABLE IF NOT EXISTS session
(
    session_id		INT				PRIMARY KEY		AUTO_INCREMENT,
    subject_id		INT				NOT NULL,
    session_date	DATE			NOT NULL,
    schedule_start	TIME			NOT NULL,
    schedule_end	TIME			NOT NULL,
    session_key		VARCHAR(20)		NULL,
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
    );

CREATE TABLE IF NOT EXISTS attendance
(
    attendance_id	INT			PRIMARY KEY		AUTO_INCREMENT,
    session_id		INT			NOT NULL,
    student_id		INT			NOT NULL,
    is_present		BOOLEAN,
    FOREIGN KEY (session_id) REFERENCES session(session_id),
    FOREIGN KEY (student_id) REFERENCES student(student_id)
    );

------------------------------------------------------------------------------

INSERT INTO student
(first_name, last_name, mail, password, birthdate)
VALUES
    ('Ole', 'Olsen', 'cph123@cphbusiness.dk', '1234', '19-08-1983'),
    ('Søren', 'Petersen', 'cph278@cphbusiness.dk', '1234', '26-08-1992'),
    ('Peter', 'Nielsen', 'cph198@cphbusiness.dk', '1234', '12-01-1985'),
    ('Kim', 'Jensen', 'cph821@cphbusiness.dk', '1234', '07-04-1988'),
    ('Henrik', 'Andreasen', 'cph043@cphbusiness.dk', '1234', '01-01-2001'),
    ('Jens', 'Nikolajsen', 'cph163@cphbusiness.dk', '1234', '14-07-1975'),
    ('Mikkel', 'Petersen', 'cph571@cphbusiness.dk', '1234', '23-08-1968'),
    ('Lars', 'Nielsen', 'cph478@cphbusiness.dk', '1234', '22-04-1977'),
    ('Thomas', 'Sørensen', 'cph284@cphbusiness.dk', '1234', '30-04-1968'),
    ('Morten', 'Andersen', 'cph403@cphbusiness.dk', '1234', '04-03-1999');

INSERT INTO teacher
(first_name, last_name, mail, password)
VALUES
    ('Andrea', 'Corradini', 'aco@cphbusiness.dk', '1234'),
    ('Steffen', 'Segovia Helbo', 'ssh@cphbusiness.dk', '1234'),
    ('Todorka', 'Stoyanova Dimitrova', 'tsd@cphbusiness.dk', '1234');

INSERT INTO subject
(subject_name, num_of_students, teacher_id)
VALUES
    ('Development of Large Systems', 30, 1),
    ('System Integration', 30, 3),
    ('Testing', 30, 2);

INSERT INTO session
(subject_id, session_date, schedule_start, schedule_end)
VALUES
    (3, '2021-09-02', '8:30:00', '10:00:00'),
    (3, '2021-09-02', '10:30:00', '12:00:00'),
    (3, '2021-09-02', '12:30:00', '14:00:00'),
    (3, '2021-09-02', '14:30:00', '16:00:00'),
    (1, '2021-09-06', '8:30:00', '10:00:00'),
    (1, '2021-09-06', '10:30:00', '12:00:00'),
    (1, '2021-09-06', '12:30:00', '14:00:00'),
    (1, '2021-09-06', '14:30:00', '16:00:00'),
    (2, '2021-09-07', '8:30:00', '10:00:00'),
    (2, '2021-09-07', '10:30:00', '12:00:00'),
    (2, '2021-09-07', '12:30:00', '14:00:00'),
    (2, '2021-09-07', '14:30:00', '16:00:00'),
    (3, '2021-09-09', '8:30:00', '10:00:00'),
    (3, '2021-09-09', '10:30:00', '12:00:00'),
    (3, '2021-09-09', '12:30:00', '14:00:00'),
    (3, '2021-09-09', '14:30:00', '16:00:00'),
    (1, '2021-09-13', '8:30:00', '10:00:00'),
    (1, '2021-09-13', '10:30:00', '12:00:00'),
    (1, '2021-09-13', '12:30:00', '14:00:00'),
    (1, '2021-09-13', '14:30:00', '16:00:00'),
    (2, '2021-09-14', '8:30:00', '10:00:00'),
    (2, '2021-09-14', '10:30:00', '12:00:00'),
    (2, '2021-09-14', '12:30:00', '14:00:00'),
    (2, '2021-09-14', '14:30:00', '16:00:00');

INSERT INTO attendance
    (session_id, student_id, is_present)
VALUES
    (1, 1, 1),
    (1, 2, 1),
    (1, 3, 1),
    (1, 5, 1),
    (1, 6, 1),
    (1, 7, 1),
    (1, 8, 1),
    (1, 9, 1),
    (2, 1, 1),
    (2, 3, 1),
    (2, 4, 1),
    (2, 5, 1),
    (2, 6, 1),
    (2, 7, 1),
    (2, 9, 1),
    (3, 2, 1),
    (3, 3, 1),
    (3, 4, 1),
    (3, 5, 1),
    (3, 6, 1),
    (3, 7, 1),
    (3, 8, 1),
    (3, 9, 1);
