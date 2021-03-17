-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 18 sep. 2020 à 07:57
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
-- Structure de la table `matrice_tbl`
--

DROP TABLE IF EXISTS `matrice_tbl`;
CREATE TABLE IF NOT EXISTS `matrice_tbl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `matrice_name` varchar(255) DEFAULT NULL,
  `nbr_champ_dest` int(11) NOT NULL,
  `nbr_champ_src` int(11) NOT NULL,
  `nbr_modif` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=203 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `matrice_tbl`
--

INSERT INTO `matrice_tbl` (`id`, `matrice_name`, `nbr_champ_dest`, `nbr_champ_src`, `nbr_modif`) VALUES
(30, 'bktier', 71, 67, 4),
(31, 'bkteltie', 5, 4, 1),
(32, 'bktelcli', 5, 4, 1),
(33, 'bkpsc', 21, 19, 2),
(40, 'bkacp', 29, 29, 1),
(41, 'bkadcli', 26, 26, 1),
(42, 'bkicli', 9, 9, 1),
(43, 'bkicomtest', 10, 10, 1),
(44, 'bkprfcli', 7, 7, 1),
(45, 'bkprocli', 4, 4, 1),
(46, 'bkrecap', 5, 5, 1),
(47, 'bkcli', 103, 90, 13),
(34, 'bkcom', 95, 94, 1),
(59, 'bkemacli', 4, 4, 1),
(202, 'TestMatrix', 7, 5, 2),
(201, 'bkicom', 10, 10, 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
