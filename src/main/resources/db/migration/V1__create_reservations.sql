DROP TABLE IF EXISTS reservations;

CREATE TABLE reservations (
    id BIGSERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    room_id INTEGER NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL
);

INSERT INTO reservations(user_id, room_id, start_date, end_date, status)
VALUES (11, 22, '2024-10-12', '2024-11-12', 'PENDING'),
       (12, 24, '2024-12-12', '2024-12-22', 'APPROVED'),
       (13, 25, '2024-10-10', '2024-10-20', 'CANCELLED');
