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
-- Structure de la table `champ_tbl`
--

DROP TABLE IF EXISTS `champ_tbl`;
CREATE TABLE IF NOT EXISTS `champ_tbl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action` varchar(255) DEFAULT NULL,
  `champ_name` varchar(255) DEFAULT NULL,
  `valeur` varchar(255) DEFAULT NULL,
  `matrice_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeg7sin18lj04o3idpnunhu338` (`matrice_id`)
) ENGINE=MyISAM AUTO_INCREMENT=340 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `champ_tbl`
--

INSERT INTO `champ_tbl` (`id`, `action`, `champ_name`, `valeur`, `matrice_id`) VALUES
(31, 'remplacer', 'champ68', '', 30),
(32, 'remplacer', 'champ69', '', 30),
(33, 'remplacer', 'champ70', '', 30),
(34, 'remplacer', 'champ71', '', 30),
(35, 'remplacer', 'champ5', '', 31),
(36, 'remplacer', 'champ5', '', 32),
(37, 'remplacer', 'champ20', '', 33),
(38, 'remplacer', 'champ21', '', 33),
(39, 'remplacer', 'champ95', '', 34),
(50, 'remplacer', 'champ1', 'champ1', 40),
(51, 'remplacer', 'champ1', 'champ1', 41),
(52, 'remplacer', 'champ1', 'champ1', 42),
(53, 'remplacer', 'champ1', 'champ1', 43),
(54, 'remplacer', 'champ1', 'champ1', 44),
(55, 'remplacer', 'champ1', 'champ1', 45),
(56, 'remplacer', 'champ1', 'champ1', 46),
(57, 'remplacer', 'champ91', '', 47),
(58, 'remplacer', 'champ92', '', 47),
(59, 'remplacer', 'champ93', '', 47),
(60, 'remplacer', 'champ94', '', 47),
(61, 'remplacer', 'champ95', '', 47),
(62, 'remplacer', 'champ96', '', 47),
(63, 'remplacer', 'champ97', '', 47),
(64, 'remplacer', 'champ98', '', 47),
(65, 'remplacer', 'champ99', '', 47),
(66, 'remplacer', 'champ100', '', 47),
(67, 'remplacer', 'champ101', '', 47),
(68, 'remplacer', 'champ102', '', 47),
(69, 'remplacer', 'champ103', '', 47),
(70, 'calculable', 'champ1', 'autoIncrement', 48),
(71, 'fige', 'champ2', 'bkcli', 48),
(72, 'remplacer', 'champ3', 'champ4', 48),
(73, 'calculable', 'champ1', 'autoIncrement', 49),
(74, 'fige', 'champ2', 'bkcli', 49),
(75, 'remplacer', 'champ3', 'champ4', 49),
(199, 'remplacer', 'champ1', 'champ1', 59),
(200, 'calculable', 'champ1', 'autoIncrement', 60),
(201, 'calculable', 'champ1', 'autoIncrement', 101),
(202, 'remplacer', 'champ3', 'champ4', 60),
(203, 'fige', 'champ2', 'bkcli', 61),
(204, 'remplacer', 'champ3', 'champ4', 61),
(205, 'fige', 'champ2', 'bkcli', 62),
(206, 'remplacer', 'champ3', 'champ4', 62),
(207, 'fige', 'champ2', 'bkcli', 63),
(208, 'remplacer', 'champ3', 'champ4', 63),
(209, 'fige', 'champ2', 'bkcli', 64),
(210, 'remplacer', 'champ3', 'champ4', 64),
(211, 'fige', 'champ2', 'bkcli', 65),
(212, 'remplacer', 'champ3', 'champ4', 65),
(213, 'fige', 'champ2', 'bkcli', 66),
(214, 'remplacer', 'champ3', 'champ4', 66),
(215, 'fige', 'champ2', 'bkcli', 67),
(216, 'remplacer', 'champ3', 'champ4', 67),
(217, 'fige', 'champ2', 'bkcli', 68),
(218, 'remplacer', 'champ3', 'champ4', 68),
(219, 'fige', 'champ2', 'bkcli', 69),
(220, 'remplacer', 'champ3', 'champ4', 69),
(221, 'fige', 'champ2', 'bkcli', 70),
(222, 'remplacer', 'champ3', 'champ4', 70),
(223, 'fige', 'champ2', 'bkcli', 71),
(224, 'remplacer', 'champ3', 'champ4', 71),
(225, 'fige', 'champ2', 'bkcli', 72),
(226, 'remplacer', 'champ3', 'champ4', 72),
(227, 'fige', 'champ2', 'bkcli', 73),
(228, 'remplacer', 'champ3', 'champ4', 73),
(229, 'fige', 'champ2', 'bkcli', 74),
(230, 'remplacer', 'champ3', 'champ4', 74),
(231, 'fige', 'champ2', 'bkcli', 75),
(232, 'remplacer', 'champ3', 'champ4', 75),
(233, 'fige', 'champ2', 'bkcli', 76),
(234, 'remplacer', 'champ3', 'champ4', 76),
(235, 'fige', 'champ2', 'bkcli', 77),
(236, 'remplacer', 'champ3', 'champ4', 77),
(237, 'fige', 'champ2', 'bkcli', 78),
(238, 'remplacer', 'champ3', 'champ4', 78),
(239, 'fige', 'champ2', 'bkcli', 79),
(240, 'remplacer', 'champ3', 'champ4', 79),
(241, 'fige', 'champ2', 'bkcli', 80),
(242, 'remplacer', 'champ3', 'champ4', 80),
(243, 'fige', 'champ2', 'bkcli', 81),
(244, 'remplacer', 'champ3', 'champ4', 81),
(245, 'fige', 'champ2', 'bkcli', 82),
(246, 'remplacer', 'champ3', 'champ4', 82),
(247, 'fige', 'champ2', 'bkcli', 83),
(248, 'remplacer', 'champ3', 'champ4', 83),
(249, 'fige', 'champ2', 'bkcli', 84),
(250, 'remplacer', 'champ3', 'champ4', 84),
(251, 'fige', 'champ2', 'bkcli', 85),
(252, 'remplacer', 'champ3', 'champ4', 85),
(253, 'fige', 'champ2', 'bkcli', 86),
(254, 'remplacer', 'champ3', 'champ4', 86),
(255, 'fige', 'champ2', 'bkcli', 87),
(256, 'remplacer', 'champ3', 'champ4', 87),
(257, 'fige', 'champ2', 'bkcli', 88),
(258, 'remplacer', 'champ3', 'champ4', 88),
(259, 'fige', 'champ2', 'bkcli', 89),
(260, 'remplacer', 'champ3', 'champ4', 89),
(261, 'fige', 'champ2', 'bkcli', 90),
(262, 'remplacer', 'champ3', 'champ4', 90),
(263, 'fige', 'champ2', 'bkcli', 91),
(264, 'remplacer', 'champ3', 'champ4', 91),
(265, 'fige', 'champ2', 'bkcli', 92),
(266, 'remplacer', 'champ3', 'champ4', 92),
(267, 'fige', 'champ2', 'bkcli', 93),
(268, 'remplacer', 'champ3', 'champ4', 93),
(269, 'fige', 'champ2', 'bkcli', 94),
(270, 'remplacer', 'champ3', 'champ4', 94),
(271, 'fige', 'champ2', 'bkcli', 95),
(272, 'remplacer', 'champ3', 'champ4', 95),
(273, 'fige', 'champ2', 'bkcli', 96),
(274, 'remplacer', 'champ3', 'champ4', 96),
(275, 'fige', 'champ2', 'bkcli', 97),
(276, 'remplacer', 'champ3', 'champ4', 97),
(277, 'fige', 'champ2', 'bkcli', 98),
(278, 'remplacer', 'champ3', 'champ4', 98),
(279, 'fige', 'champ2', 'bkcli', 99),
(280, 'remplacer', 'champ3', 'champ4', 99),
(281, 'fige', 'champ2', 'bkcli', 100),
(282, 'remplacer', 'champ3', 'champ4', 100),
(283, 'fige', 'champ2', 'bkcli', 101),
(284, 'remplacer', 'champ3', 'champ4', 101),
(285, 'fige', 'champ2', 'bkcli', 101),
(286, 'remplacer', 'champ3', 'champ4', 101),
(287, 'fige', 'champ2', 'bkcli', 101),
(288, 'remplacer', 'champ3', 'champ4', 101),
(289, 'fige', 'champ2', 'bkcli', 101),
(290, 'remplacer', 'champ3', 'champ4', 101),
(291, 'fige', 'champ2', 'bkcli', 101),
(292, 'remplacer', 'champ3', 'champ4', 101),
(293, 'fige', 'champ2', 'bkcli', 101),
(294, 'remplacer', 'champ3', 'champ4', 101),
(299, 'remplacer', 'champ1', 'champ1', 201),
(330, 'Calculable', 'champ3', 'champ102', 202),
(323, 'Fige', 'champ1', 'champ8', 202),
(328, 'Calculable', 'champ4', 'champ5', 202),
(335, 'Fige', 'champ16', 'champ1', 202),
(339, 'Calculable', 'champ2', 'champ12', 202);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
