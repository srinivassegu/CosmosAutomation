CREATE TABLE `consultant_tbl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email_id` varchar(100) NOT NULL,
  `mobile_contact` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COLLATE=utf8mb4_general_ci  DEFAULT CHARSET=utf8mb4;


CREATE TABLE `consultant_timesheet_tbl` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `consultant_id` bigint(20) NOT NULL,
  `week_ending` date NOT NULL,
  `is_submitted` tinyint(4) DEFAULT '0',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `consultant_id` (`consultant_id`),
  CONSTRAINT `consultant_week_tbl_ibfk_1` FOREIGN KEY (`consultant_id`) REFERENCES `consultant_tbl` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB COLLATE=utf8mb4_general_ci  DEFAULT CHARSET=utf8mb4;

CREATE TABLE `department_tbl` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(45) NOT NULL,
  `organization_id` int(20) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `org_id_idx` (`organization_id`),
  CONSTRAINT `org_id` FOREIGN KEY (`organization_id`) REFERENCES `organization_tbl` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB COLLATE=utf8mb4_general_ci  DEFAULT CHARSET=utf8mb4;

CREATE TABLE `employee_tbl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email_id` varchar(100) NOT NULL,
  `department_id` int(20) NOT NULL,
  `mobile_contact` varchar(45) NOT NULL,
  `fax` varchar(45) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `employee_tbl_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department_tbl` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB COLLATE=utf8mb4_general_ci  DEFAULT CHARSET=utf8mb4;

CREATE TABLE `organization_tbl` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `address` text NOT NULL,
  `email_id` varchar(100) NOT NULL,
  `mobile_contact` varchar(45) NOT NULL,
  `fax_contact` varchar(45) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COLLATE=utf8mb4_general_ci  DEFAULT CHARSET=utf8mb4;

CREATE TABLE `reminder_config_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `week_frequency` varchar(45) DEFAULT '1',
  `hour_frequency` int(11) NOT NULL DEFAULT '24',
  `vendor_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `vendor_id_idx` (`vendor_id`),
  CONSTRAINT `vendor_id` FOREIGN KEY (`vendor_id`) REFERENCES `vendor_tbl` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB COLLATE=utf8mb4_general_ci  DEFAULT CHARSET=utf8mb4;


CREATE TABLE `vendor_consultant_xref_tbl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vendor_id` int(11) DEFAULT NULL,
  `consultant_id` bigint(20) DEFAULT NULL,
  `is_active` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `consultant_id_UNIQUE` (`consultant_id`),
  KEY `vendor_id_idx` (`vendor_id`),
  KEY `consultant_id_idx` (`consultant_id`),
  CONSTRAINT `consultant_xref_id` FOREIGN KEY (`consultant_id`) REFERENCES `consultant_tbl` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `vendor_xref_id` FOREIGN KEY (`vendor_id`) REFERENCES `vendor_tbl` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB COLLATE=utf8mb4_general_ci  DEFAULT CHARSET=utf8mb4;

CREATE TABLE `vendor_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `organization_name` varchar(100) NOT NULL,
  `email_id` varchar(100) NOT NULL,
  `mobile_contact` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COLLATE=utf8mb4_general_ci  DEFAULT CHARSET=utf8mb4;