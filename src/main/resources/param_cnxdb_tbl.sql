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
-- Structure de la table `param_cnxdb_tbl`
--

DROP TABLE IF EXISTS `param_cnxdb_tbl`;
CREATE TABLE IF NOT EXISTS `param_cnxdb_tbl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `port` int(11) NOT NULL,
  `pswd` varchar(255) DEFAULT NULL,
  `sid` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `param_cnxdb_tbl`
--

INSERT INTO `param_cnxdb_tbl` (`id`, `port`, `pswd`, `sid`, `url`, `user`) VALUES
(2, 1521, 'prodagc', 'attibank', '172.28.11.156', 'prodagc');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
