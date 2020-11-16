SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `RentCar_Test_DB` DEFAULT CHARACTER SET utf8;
USE `RentCar_Test_DB`;

CREATE TABLE IF NOT EXISTS `RentCar_Test_DB`.`Roles`
(
    `id` INT NOT NULL
    AUTO_INCREMENT,
    `
    role
    `
    VARCHAR
(
    45
) NOT NULL DEFAULT 'CLIENT',
    PRIMARY KEY
(
    `id`
),
    UNIQUE INDEX ` role_UNIQUE `
(
    `role` ASC
) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RentCar_Test_DB`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ` RentCar_Test_DB `.` Users `
(
    `
    id
    `
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `
    login
    `
    VARCHAR
(
    45
) NOT NULL,
    ` password ` VARCHAR
(
    65
) NOT NULL,
    ` surname ` VARCHAR
(
    75
) NOT NULL,
    ` name ` VARCHAR
(
    75
) NOT NULL,
    ` passport_id_number ` VARCHAR
(
    14
) NOT NULL,
    ` driver_license ` VARCHAR
(
    14
) NOT NULL,
    ` date_of_birth ` DATE NOT NULL,
    ` e_mail ` VARCHAR
(
    45
) NULL,
    ` phone ` VARCHAR
(
    45
) NOT NULL,
    ` is_deleted ` TINYINT NOT NULL DEFAULT '0',
    ` Role_id ` INT NOT NULL DEFAULT '1',
    PRIMARY KEY
(
    `id`
),
    UNIQUE INDEX ` login_UNIQUE `
(
    `login` ASC
) VISIBLE,
    INDEX ` fk_User_Role1_idx `
(
    `Role_id` ASC
) VISIBLE,
    CONSTRAINT ` fk_User_Role1 `
    FOREIGN KEY
(
    `Role_id`
)
    REFERENCES ` RentCar_Test_DB `.` Roles `
(
    `id`
)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RentCar_Test_DB`.`CarTypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ` RentCar_Test_DB `.` CarTypes `
(
    `
    id
    `
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `
    type
    `
    VARCHAR
(
    45
) NOT NULL,
    PRIMARY KEY
(
    `id`
),
    UNIQUE INDEX ` type_UNIQUE `
(
    `type` ASC
) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RentCar_Test_DB`.`TransmissionTypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ` RentCar_Test_DB `.` TransmissionTypes `
(
    `
    id
    `
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `
    type
    `
    VARCHAR
(
    15
) NOT NULL,
    PRIMARY KEY
(
    `id`
))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RentCar_Test_DB`.`CarBrands`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ` RentCar_Test_DB `.` CarBrands `
(
    `
    id
    `
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `
    brand_name
    `
    VARCHAR
(
    45
) NOT NULL,
    PRIMARY KEY
(
    `id`
),
    UNIQUE INDEX ` modelName_UNIQUE `
(
    `brand_name` ASC
) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RentCar_Test_DB`.`CarModels`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ` RentCar_Test_DB `.` CarModels `
(
    `
    id
    `
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `
    model_name
    `
    VARCHAR
(
    45
) NULL,
    ` CarBrands_id ` INT NOT NULL,
    PRIMARY KEY
(
    `id`
),
    INDEX ` fk_CarModels_CarBrands1_idx `
(
    `CarBrands_id` ASC
) VISIBLE,
    CONSTRAINT ` fk_CarModels_CarBrands1 `
    FOREIGN KEY
(
    `CarBrands_id`
)
    REFERENCES ` RentCar_Test_DB `.` CarBrands `
(
    `id`
)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RentCar_Test_DB`.`Cars`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ` RentCar_Test_DB `.` Cars `
(
    `
    id
    `
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `
    VIN
    `
    VARCHAR
(
    45
) NOT NULL,
    ` manufacture_date ` INT NOT NULL,
    ` engine_power ` INT NOT NULL,
    ` fuel_consumption ` DOUBLE NOT NULL,
    ` is_avaliable_to_rent ` TINYINT NOT NULL DEFAULT 1,
    ` is_deleted ` TINYINT NOT NULL DEFAULT 0,
    ` price_per_day ` DECIMAL NOT NULL,
    ` TransmissionType_id ` INT NOT NULL,
    ` CarType_id ` INT NOT NULL,
    ` CarModels_id ` INT NOT NULL,
    PRIMARY KEY
(
    `id`
),
    INDEX ` fk_Car_TransmissionType1_idx `
(
    `TransmissionType_id` ASC
) VISIBLE,
    INDEX ` fk_Car_CarType1_idx `
(
    `CarType_id` ASC
) VISIBLE,
    INDEX ` fk_Cars_CarModels1_idx `
(
    `CarModels_id` ASC
) VISIBLE,
    UNIQUE INDEX ` VIN_UNIQUE `
(
    `VIN` ASC
) VISIBLE,
    CONSTRAINT ` fk_Car_TransmissionType1 `
    FOREIGN KEY
(
    `TransmissionType_id`
)
    REFERENCES ` RentCar_Test_DB `.` TransmissionTypes `
(
    `id`
)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT ` fk_Car_CarType1 `
    FOREIGN KEY
(
    `CarType_id`
)
    REFERENCES ` RentCar_Test_DB `.` CarTypes `
(
    `id`
)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT ` fk_Cars_CarModels1 `
    FOREIGN KEY
(
    `CarModels_id`
)
    REFERENCES ` RentCar_Test_DB `.` CarModels `
(
    `id`
)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RentCar_Test_DB`.`Orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ` RentCar_Test_DB `.` Orders `
(
    `
    id
    `
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `
    order_date
    `
    DATETIME
    NOT
    NULL,
    `
    start_rent
    `
    DATE
    NOT
    NULL,
    `
    end_rent
    `
    DATE
    NOT
    NULL,
    `
    total_price
    `
    DECIMAL
(
    5,
    2
) NOT NULL,
    ` is_order_accepted ` TINYINT NOT NULL DEFAULT 0,
    ` is_order_canceled ` TINYINT NOT NULL DEFAULT 0,
    ` is_order_closed ` TINYINT NOT NULL DEFAULT 0,
    ` user_id ` INT NOT NULL,
    ` car_id ` INT NOT NULL,
    PRIMARY KEY
(
    `id`
),
    INDEX ` fk_Order_User1_idx `
(
    `user_id` ASC
) VISIBLE,
    INDEX ` fk_Order_Car1_idx `
(
    `car_id` ASC
) VISIBLE,
    CONSTRAINT ` fk_Order_User1 `
    FOREIGN KEY
(
    `user_id`
)
    REFERENCES ` RentCar_Test_DB `.` Users `
(
    `id`
)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT ` fk_Order_Car1 `
    FOREIGN KEY
(
    `car_id`
)
    REFERENCES ` RentCar_Test_DB `.` Cars `
(
    `id`
)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RentCar_Test_DB`.`RentPeriods`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ` RentCar_Test_DB `.` RentPeriods `
(
    `
    id
    `
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `
    days_count
    `
    INT
    NOT
    NULL,
    PRIMARY
    KEY
(
    `
    id
    `
),
    UNIQUE INDEX ` daysCount_UNIQUE `
(
    `days_count` ASC
) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RentCar_Test_DB`.`RentPrices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ` RentCar_Test_DB `.` RentPrices `
(
    `
    Car_id
    `
    INT
    NOT
    NULL,
    `
    RentPeriod_id
    `
    INT
    NOT
    NULL,
    `
    price
    `
    DECIMAL
    NOT
    NULL,
    INDEX
    `
    fk_RentPrice_Car1_idx
    `
(
    `
    Car_id
    `
    ASC
) VISIBLE,
    INDEX ` fk_RentPrice_RentPeriod1_idx `
(
    `RentPeriod_id` ASC
) VISIBLE,
    PRIMARY KEY
(
    `
    Car_id
    `,
    `
    RentPeriod_id
    `
),
    CONSTRAINT ` fk_RentPrice_Car1 `
    FOREIGN KEY
(
    `Car_id`
)
    REFERENCES ` RentCar_Test_DB `.` Cars `
(
    `id`
)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT ` fk_RentPrice_RentPeriod1 `
    FOREIGN KEY
(
    `RentPeriod_id`
)
    REFERENCES ` RentCar_Test_DB `.` RentPeriods `
(
    `id`
)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RentCar_Test_DB`.`AdditionalPayments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ` RentCar_Test_DB `.` AdditionalPayments `
(
    `
    id
    `
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `
    cost_by_overdue_period
    `
    DECIMAL
    NOT
    NULL
    DEFAULT
    0,
    `
    cost_by_fuel
    `
    INT
    NOT
    NULL
    DEFAULT
    0,
    `
    cost_by_mileage
    `
    DECIMAL
    NOT
    NULL
    DEFAULT
    0,
    `
    cost_by_parking_penalty
    `
    DECIMAL
    NOT
    NULL
    DEFAULT
    0,
    `
    cost_by_police_penalty
    `
    DECIMAL
    NOT
    NULL
    DEFAULT
    0,
    `
    cost_by_damage
    `
    DECIMAL
    NOT
    NULL
    DEFAULT
    0,
    `
    cost_by_other_penalty
    `
    DECIMAL
    NOT
    NULL
    DEFAULT
    0,
    `
    Orders_id
    `
    INT
    NOT
    NULL,
    PRIMARY
    KEY
(
    `
    id
    `
),
    CONSTRAINT ` fk_AdditionalPayments_Orders1 `
    FOREIGN KEY
(
    `Orders_id`
)
    REFERENCES ` RentCar_Test_DB `.` Orders `
(
    `id`
)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RentCar_Test_DB`.`CarPhotos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ` RentCar_Test_DB `.` CarPhotos `
(
    `
    id
    `
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `
    picture_URL
    `
    VARCHAR
(
    45
) NOT NULL,
    ` Cars_id ` INT NOT NULL,
    PRIMARY KEY
(
    `id`
),
    INDEX ` fk_CarPhotos_Cars1_idx `
(
    `Cars_id` ASC
) VISIBLE,
    CONSTRAINT ` fk_CarPhotos_Cars1 `
    FOREIGN KEY
(
    `Cars_id`
)
    REFERENCES ` RentCar_Test_DB `.` Cars `
(
    `id`
)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
