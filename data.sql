-- USERS
data: ./users-db.png
password: 0990204404


-- EVENTS

INSERT INTO events (title, description, location, capacity, start_date, end_date, user_id)
VALUES
('Tech Conference', 'Conference about new tech', 'Madrid', 200, '2025-10-10', '2025-10-12', 3),
('Music Festival', 'Live music and DJs', 'Barcelona', 500, '2025-11-05', '2025-11-06', 3),
('Startup Meetup', 'Networking event for startups', 'Valencia', 100, '2025-09-20', '2025-09-20', 4),
('Art Expo', 'Modern art exhibition', 'Bilbao', 150, '2025-08-15', '2025-08-17', 4),
('Food Fair', 'Street food event', 'Sevilla', 300, '2025-07-01', '2025-07-02', 3),
('Gaming Tournament', 'E-sports competition', 'Madrid', 250, '2025-06-10', '2025-06-11', 3),
('Science Talk', 'Lecture series on science', 'Granada', 120, '2025-05-05', '2025-05-05', 4),
('Charity Run', 'Fundraising marathon', 'Valencia', 400, '2025-04-22', '2025-04-22', 4),
('Book Fair', 'International book exhibition', 'Zaragoza', 180, '2025-03-15', '2025-03-18', 3),
('Film Festival', 'Cinema and indie films', 'Barcelona', 350, '2025-02-10', '2025-02-14', 4);

-- RESERVATIONS

INSERT INTO reservations (event_id, user_id, seats, status, created_at)
VALUES
(1, 5, 2, 'CONFIRMED', '2025-01-15'),
(2, 6, 3, 'PENDING', '2025-01-16'),
(3, 5, 1, 'CONFIRMED', '2025-01-20'),
(4, 6, 2, 'CONFIRMED', '2025-01-21'),
(5, 5, 4, 'CANCELLED', '2025-01-25'),
(6, 6, 1, 'CONFIRMED', '2025-01-27'),
(7, 5, 2, 'CONFIRMED', '2025-01-28'),
(8, 6, 3, 'PENDING', '2025-01-29'),
(9, 5, 2, 'CONFIRMED', '2025-01-30'),
(10, 6, 1, 'CONFIRMED', '2025-02-01');
