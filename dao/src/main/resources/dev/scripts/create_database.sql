-- MySQL Script generated by MySQL Workbench
-- Sat May  7 16:21:08 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cert_dev
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cert_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cert_dev` DEFAULT CHARACTER SET utf8;
USE `cert_dev`;

-- -----------------------------------------------------
-- Table `cert_dev`.`gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cert_dev`.`gift_certificate`
(
    `id`               INT          NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(45)  NOT NULL,
    `description`      VARCHAR(255) NOT NULL,
    `price`            VARCHAR(45)  NOT NULL,
    `duration`         VARCHAR(45)  NOT NULL,
    `create_date`      DATETIME     NOT NULL,
    `last_update_date` DATETIME     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cert_dev`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cert_dev`.`tag`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cert_dev`.`gift_certificate_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cert_dev`.`gift_certificate_tag`
(
    `gift_certificate_id` INT NOT NULL,
    `tag_id`              INT NOT NULL,
    PRIMARY KEY (`gift_certificate_id`, `tag_id`),
    INDEX `fk_gift_certificate_has_tag_tag1_idx` (`tag_id` ASC) VISIBLE,
    INDEX `fk_gift_certificate_has_tag_gift_certificate_idx` (`gift_certificate_id` ASC) VISIBLE,
    CONSTRAINT `fk_gift_certificate_has_tag_gift_certificate`
        FOREIGN KEY (`gift_certificate_id`)
            REFERENCES `cert_dev`.`gift_certificate` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_gift_certificate_has_tag_tag1`
        FOREIGN KEY (`tag_id`)
            REFERENCES `cert_dev`.`tag` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
