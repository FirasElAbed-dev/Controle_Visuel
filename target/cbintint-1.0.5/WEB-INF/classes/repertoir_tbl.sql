-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 18 sep. 2020 à 07:56
-- Version du serveur :  5.7.31
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `attijari-db`
--

-- --------------------------------------------------------

--
-- Structure de la table `repertoir_tbl`
--

DROP TABLE IF EXISTS `repertoir_tbl`;
CREATE TABLE IF NOT EXISTS `repertoir_tbl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rep_archiv` varchar(255) DEFAULT NULL,
  `rep_in` varchar(255) DEFAULT NULL,
  `rep_out` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `repertoir_tbl`
--

INSERT INTO `repertoir_tbl` (`id`, `rep_archiv`, `rep_in`, `rep_out`) VALUES
(1, 'D:/rep/repArch/', '//bank-sud.tn/shared_doc/rep_travail_app/', 'D:/rep/repOut/');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
