SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`offices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`offices` (
  `idoffices` INT NOT NULL AUTO_INCREMENT,
  `location` VARCHAR(45) NULL,
  `employee_type` VARCHAR(45) NULL,
  PRIMARY KEY (`idoffices`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`employee_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`employee_type` (
  `idemployee_type` INT NOT NULL AUTO_INCREMENT,
  `employee_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idemployee_type`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`processing_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`processing_order` (
  `idorder_history` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `order_status` VARCHAR(45) NULL,
  PRIMARY KEY (`idorder_history`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`employees` (
  `idemployees` INT NOT NULL AUTO_INCREMENT,
  `namel` VARCHAR(45) NULL,
  `offices_idoffices` INT NULL,
  `employee_type_idemployee_type` INT NOT NULL,
  `current_order_idorder_history` INT NOT NULL,
  PRIMARY KEY (`idemployees`, `offices_idoffices`, `employee_type_idemployee_type`, `current_order_idorder_history`),
  INDEX `fk_employees_offices_idx` (`offices_idoffices` ASC) VISIBLE,
  INDEX `fk_employees_employee_type1_idx` (`employee_type_idemployee_type` ASC) VISIBLE,
  INDEX `fk_employees_current_order1_idx` (`current_order_idorder_history` ASC) VISIBLE,
  CONSTRAINT `fk_employees_offices`
    FOREIGN KEY (`offices_idoffices`)
    REFERENCES `mydb`.`offices` (`idoffices`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_employees_employee_type1`
    FOREIGN KEY (`employee_type_idemployee_type`)
    REFERENCES `mydb`.`employee_type` (`idemployee_type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_employees_current_order1`
    FOREIGN KEY (`current_order_idorder_history`)
    REFERENCES `mydb`.`processing_order` (`idorder_history`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`delivery_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`delivery_type` (
  `iddelivery_type` INT NOT NULL,
  `delivery_typel` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`iddelivery_type`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`arrived_packages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`arrived_packages` (
  `id_arrived_package` INT NOT NULL AUTO_INCREMENT,
  `senders_name` VARCHAR(45) NOT NULL,
  `recipients_name` VARCHAR(45) NOT NULL,
  `delivery_address` VARCHAR(45) NULL,
  `weight` VARCHAR(45) NOT NULL,
  `offices_idoffices` INT NOT NULL,
  `delivery_type_iddelivery_type` INT NOT NULL,
  PRIMARY KEY (`id_arrived_package`, `offices_idoffices`, `delivery_type_iddelivery_type`),
  INDEX `fk_arrived_packages_offices1_idx` (`offices_idoffices` ASC) VISIBLE,
  INDEX `fk_arrived_packages_delivery_type1_idx` (`delivery_type_iddelivery_type` ASC) VISIBLE,
  CONSTRAINT `fk_arrived_packages_offices1`
    FOREIGN KEY (`offices_idoffices`)
    REFERENCES `mydb`.`offices` (`idoffices`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_arrived_packages_delivery_type1`
    FOREIGN KEY (`delivery_type_iddelivery_type`)
    REFERENCES `mydb`.`delivery_type` (`iddelivery_type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`delivered_packages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`delivered_packages` (
  `id_delivery_package` INT NOT NULL AUTO_INCREMENT,
  `senders_name` VARCHAR(45) NOT NULL,
  `recipients_name` VARCHAR(45) NOT NULL,
  `delivery_address` VARCHAR(45) NULL,
  `weight` VARCHAR(45) NOT NULL,
  `offices_idoffices` INT NOT NULL,
  `delivery_type_iddelivery_type` INT NOT NULL,
  PRIMARY KEY (`id_delivery_package`, `offices_idoffices`, `delivery_type_iddelivery_type`),
  INDEX `fk_arrived_packages_offices1_idx` (`offices_idoffices` ASC) VISIBLE,
  INDEX `fk_delivery_packages_delivery_type1_idx` (`delivery_type_iddelivery_type` ASC) VISIBLE,
  CONSTRAINT `fk_arrived_packages_offices10`
    FOREIGN KEY (`offices_idoffices`)
    REFERENCES `mydb`.`offices` (`idoffices`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_delivery_packages_delivery_type1`
    FOREIGN KEY (`delivery_type_iddelivery_type`)
    REFERENCES `mydb`.`delivery_type` (`iddelivery_type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`delivery_packages_has_employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`delivery_packages_has_employees` (
  `delivery_packages_id_delivery_packages` INT NOT NULL,
  `delivery_packages_offices_idoffices` INT NOT NULL,
  `employees_idemployees` INT NOT NULL,
  `employees_offices_idoffices` INT NOT NULL,
  PRIMARY KEY (`delivery_packages_id_delivery_packages`, `delivery_packages_offices_idoffices`, `employees_idemployees`, `employees_offices_idoffices`),
  INDEX `fk_delivery_packages_has_employees_employees1_idx` (`employees_idemployees` ASC, `employees_offices_idoffices` ASC) VISIBLE,
  INDEX `fk_delivery_packages_has_employees_delivery_packages1_idx` (`delivery_packages_id_delivery_packages` ASC, `delivery_packages_offices_idoffices` ASC) VISIBLE,
  CONSTRAINT `fk_delivery_packages_has_employees_delivery_packages1`
    FOREIGN KEY (`delivery_packages_id_delivery_packages` , `delivery_packages_offices_idoffices`)
    REFERENCES `mydb`.`delivered_packages` (`id_delivery_package` , `offices_idoffices`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_delivery_packages_has_employees_employees1`
    FOREIGN KEY (`employees_idemployees` , `employees_offices_idoffices`)
    REFERENCES `mydb`.`employees` (`idemployees` , `offices_idoffices`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`employees_has_arrived_packages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`employees_has_arrived_packages` (
  `employees_idemployees` INT NOT NULL,
  `employees_offices_idoffices` INT NOT NULL,
  `arrived_packages_id_arrived_packages` INT NOT NULL,
  `arrived_packages_offices_idoffices` INT NOT NULL,
  PRIMARY KEY (`employees_idemployees`, `employees_offices_idoffices`, `arrived_packages_id_arrived_packages`, `arrived_packages_offices_idoffices`),
  INDEX `fk_employees_has_arrived_packages_arrived_packages1_idx` (`arrived_packages_id_arrived_packages` ASC, `arrived_packages_offices_idoffices` ASC) VISIBLE,
  INDEX `fk_employees_has_arrived_packages_employees1_idx` (`employees_idemployees` ASC, `employees_offices_idoffices` ASC) VISIBLE,
  CONSTRAINT `fk_employees_has_arrived_packages_employees1`
    FOREIGN KEY (`employees_idemployees` , `employees_offices_idoffices`)
    REFERENCES `mydb`.`employees` (`idemployees` , `offices_idoffices`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_employees_has_arrived_packages_arrived_packages1`
    FOREIGN KEY (`arrived_packages_id_arrived_packages` , `arrived_packages_offices_idoffices`)
    REFERENCES `mydb`.`arrived_packages` (`id_arrived_package` , `offices_idoffices`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`customer` (
  `idcustomer` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `current_order_idorder_history` INT NOT NULL,
  PRIMARY KEY (`idcustomer`, `current_order_idorder_history`),
  INDEX `fk_customer_current_order1_idx` (`current_order_idorder_history` ASC) VISIBLE,
  CONSTRAINT `fk_customer_current_order1`
    FOREIGN KEY (`current_order_idorder_history`)
    REFERENCES `mydb`.`processing_order` (`idorder_history`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`packages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`packages` (
  `idpackages` INT NOT NULL,
  `senders_name` VARCHAR(45) NOT NULL,
  `recipients_name` VARCHAR(45) NOT NULL,
  `delivery_adress` VARCHAR(45) NOT NULL,
  `weight` VARCHAR(45) NOT NULL,
  `current_order_idorder_history` INT NOT NULL,
  PRIMARY KEY (`idpackages`, `current_order_idorder_history`),
  INDEX `fk_packages_current_order1_idx` (`current_order_idorder_history` ASC) VISIBLE,
  CONSTRAINT `fk_packages_current_order1`
    FOREIGN KEY (`current_order_idorder_history`)
    REFERENCES `mydb`.`processing_order` (`idorder_history`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`order_status_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`order_status_type` (
  `idorder_status_type` INT NOT NULL AUTO_INCREMENT,
  `order_status_type` VARCHAR(45) NULL,
  PRIMARY KEY (`idorder_status_type`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`order_status_type_has_processing_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`order_status_type_has_processing_order` (
  `order_status_type_idorder_status_type` INT NOT NULL,
  `processing_order_idorder_history` INT NOT NULL,
  PRIMARY KEY (`order_status_type_idorder_status_type`, `processing_order_idorder_history`),
  INDEX `fk_order_status_type_has_processing_order_processing_order1_idx` (`processing_order_idorder_history` ASC) VISIBLE,
  INDEX `fk_order_status_type_has_processing_order_order_status_type_idx` (`order_status_type_idorder_status_type` ASC) VISIBLE,
  CONSTRAINT `fk_order_status_type_has_processing_order_order_status_type1`
    FOREIGN KEY (`order_status_type_idorder_status_type`)
    REFERENCES `mydb`.`order_status_type` (`idorder_status_type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_status_type_has_processing_order_processing_order1`
    FOREIGN KEY (`processing_order_idorder_history`)
    REFERENCES `mydb`.`processing_order` (`idorder_history`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
