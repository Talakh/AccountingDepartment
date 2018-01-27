DROP TABLE IF EXISTS cash_order;
DROP TABLE IF EXISTS prepayment_report;
DROP TABLE IF EXISTS department_position;
DROP TABLE IF EXISTS travel_allowance;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS city_category;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS department;


CREATE TABLE city_category (
  Id         INT         NOT NULL AUTO_INCREMENT,
  Name       VARCHAR(45) NOT NULL,
  CostPerDay REAL        NOT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE city (
  Id          INT         NOT NULL AUTO_INCREMENT,
  Name        VARCHAR(45) NOT NULL,
  TravelCost  REAL        NOT NULL,
  Category_Id INT         NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (Category_Id) REFERENCES city_category (Id)
);

CREATE TABLE department (
  Id   INT         NOT NULL AUTO_INCREMENT,
  Name VARCHAR(45) NOT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE position (
  Id   INT         NOT NULL AUTO_INCREMENT,
  Name VARCHAR(45) NOT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE department_position (
  Department_Id INT NOT NULL,
  Position_Id   INT NOT NULL,
  PRIMARY KEY (Department_Id, Position_Id),
  FOREIGN KEY (Department_Id) REFERENCES department (Id),
  FOREIGN KEY (Position_Id) REFERENCES position (Id)

);

CREATE TABLE user (
  Id            INT          NOT NULL AUTO_INCREMENT,
  FirstName     VARCHAR(45)  NOT NULL,
  MiddleName    VARCHAR(45)  NOT NULL,
  LastName      VARCHAR(45)  NOT NULL,
  Password      VARCHAR(255) NOT NULL,
  Email         VARCHAR(255) NOT NULL,
  Enabled       BOOLEAN      NOT NULL,
  Department_Id INT          NOT NULL,
  Position_Id   INT          NOT NULL,
  UserRole      VARCHAR(5)   NOT NULL,
  Image         LONGBLOB,
  PRIMARY KEY (Id),
  FOREIGN KEY (Department_Id) REFERENCES department (Id),
  FOREIGN KEY (Position_Id) REFERENCES position (Id)
);


CREATE TABLE travel_allowance (
  Id                    INT  NOT NULL AUTO_INCREMENT,
  DateOfIssue           DATE NOT NULL,
  User_Id               INT  NOT NULL,
  City_Id               INT  NOT NULL,
  BusinessTripStartDate DATE NOT NULL,
  BusinessTripEndDate   DATE NOT NULL,
  PRIMARY KEY (Id, DateOfIssue),
  FOREIGN KEY (City_Id) REFERENCES city (Id),
  FOREIGN KEY (User_Id) REFERENCES user (Id),
  CHECK (DateOfIssue < BusinessTripStartDate),
  CHECK (BusinessTripStartDate < BusinessTripEndDate)
);
CREATE TABLE cash_order (
  Id                 INT  NOT NULL AUTO_INCREMENT,
  User_Id            INT  NOT NULL,
  TravelAllowance_Id INT  NOT NULL,
  Sum                REAL NOT NULL,
  DateReceiptOfMoney DATE NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (User_Id) REFERENCES user (Id),
  FOREIGN KEY (TravelAllowance_Id) REFERENCES travel_allowance (id)
);

CREATE TABLE prepayment_report (
  Id                     INT  NOT NULL AUTO_INCREMENT,
  TravelAllowance_Id     INT  NOT NULL,
  Fare                   REAL NOT NULL,
  SeatReservation        REAL NOT NULL,
  HotelAccommodation     REAL NOT NULL,
  TelephoneConversations REAL NOT NULL,
  SumPerDiems            REAL NOT NULL,
  PreparationDate        DATE NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (TravelAllowance_Id) REFERENCES travel_allowance (Id)
);