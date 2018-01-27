# City Category
INSERT INTO city_category (Id, Name, CostPerDay) VALUES (1, 'capital', 50);
INSERT INTO city_category (Id, Name, CostPerDay) VALUES (2, 'city', 40);
INSERT INTO city_category (Id, Name, CostPerDay) VALUES (3, 'district', 30);

# City
INSERT INTO city (Id, Name, TravelCost, Category_Id) VALUES (1, 'Kiev', 300, 1);
INSERT INTO city (Id, Name, TravelCost, Category_Id) VALUES (2, 'Odessa', 250, 2);
INSERT INTO city (Id, Name, TravelCost, Category_Id) VALUES (3, 'Kharkiv', 350, 2);
INSERT INTO city (Id, Name, TravelCost, Category_Id) VALUES (4, 'Manevichi', 400, 3);
INSERT INTO city (Id, Name, TravelCost, Category_Id) VALUES (5, 'Lutsk', 300, 2);

# Department
INSERT INTO department (Id, Name) VALUES (1, 'Statistics');
INSERT INTO department (Id, Name) VALUES (2, 'Accounting');
INSERT INTO department (Id, Name) VALUES (3, 'Development');

# Position
INSERT INTO accounting_department.position (Id, Name) VALUES (1, 'Developer');
INSERT INTO accounting_department.position (Id, Name) VALUES (2, 'Designer');
INSERT INTO accounting_department.position (Id, Name) VALUES (3, 'Team Lead');
INSERT INTO accounting_department.position (Id, Name) VALUES (4, 'SEO');
INSERT INTO accounting_department.position (Id, Name) VALUES (5, 'Analytic');
INSERT INTO accounting_department.position (Id, Name) VALUES (6, 'Manager');
INSERT INTO accounting_department.position (Id, Name) VALUES (7, 'HR');
INSERT INTO accounting_department.position (Id, Name) VALUES (8, 'Statistic');
INSERT INTO accounting_department.position (Id, Name) VALUES (9, 'Master');
INSERT INTO accounting_department.position (Id, Name) VALUES (10, 'Secretary');

# Department_position
INSERT INTO department_position (Department_Id, Position_Id) VALUES (1, 5);
INSERT INTO department_position (Department_Id, Position_Id) VALUES (1, 8);
INSERT INTO department_position (Department_Id, Position_Id) VALUES (1, 9);
INSERT INTO department_position (Department_Id, Position_Id) VALUES (2, 7);
INSERT INTO department_position (Department_Id, Position_Id) VALUES (2, 9);
INSERT INTO department_position (Department_Id, Position_Id) VALUES (2, 10);
INSERT INTO department_position (Department_Id, Position_Id) VALUES (3, 1);
INSERT INTO department_position (Department_Id, Position_Id) VALUES (3, 2);
INSERT INTO department_position (Department_Id, Position_Id) VALUES (3, 3);
INSERT INTO department_position (Department_Id, Position_Id) VALUES (3, 4);
INSERT INTO department_position (Department_Id, Position_Id) VALUES (3, 5);
INSERT INTO department_position (Department_Id, Position_Id) VALUES (3, 6);
INSERT INTO department_position (Department_Id, Position_Id) VALUES (3, 9);

# User
INSERT INTO user (FirstName, MiddleName, LastName, Password, Email, Enabled, Department_Id, Position_Id, UserRole)
VALUES ('Олексій', 'Миколайович', 'Талах' , '123', 'talax98@gmail.com',FALSE , 3, 1, 'USER');