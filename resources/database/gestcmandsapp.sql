-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Serveur: localhost
-- Généré le : Mer 20 Décembre 2017 à 19:22
-- Version du serveur: 5.1.36
-- Version de PHP: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `gestcmandsapp`
--

-- --------------------------------------------------------

--
-- Structure de la table `articles`
--

CREATE TABLE IF NOT EXISTS `articles` (
  `code` varchar(6) NOT NULL,
  `code_categorie` char(3) NOT NULL,
  `designation` varchar(100) NOT NULL,
  `quantite` int(11) NOT NULL,
  `prix_unitaire` double NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`code`),
  KEY `code_categorie` (`code_categorie`),
  KEY `designation` (`designation`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `articles`
--

INSERT INTO `articles` (`code`, `code_categorie`, `designation`, `quantite`, `prix_unitaire`, `date`) VALUES
('FAMI21', 'ECR', 'Famir 21', 20, 150, '2008-11-10'),
('XENO25', 'POR', 'Xenon 25', 15, 850, '2020-12-11'),
('ZENI77', 'TAB', 'Zenith 77', 30, 500, '2011-11-11');

-- --------------------------------------------------------

--
-- Structure de la table `categories`
--

CREATE TABLE IF NOT EXISTS `categories` (
  `code` char(3) NOT NULL,
  `designation` varchar(30) NOT NULL,
  PRIMARY KEY (`code`),
  KEY `designation` (`designation`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `categories`
--

INSERT INTO `categories` (`code`, `designation`) VALUES
('ECR', 'écran'),
('POR', 'portable'),
('TAB', 'tablette');

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

CREATE TABLE IF NOT EXISTS `clients` (
  `code` varchar(6) NOT NULL,
  `nom` varchar(15) NOT NULL,
  `prenom` varchar(15) NOT NULL,
  `carte_fidele` tinyint(1) NOT NULL,
  `date` date NOT NULL,
  `adresse` varchar(15) NOT NULL,
  `code_postal` varchar(8) NOT NULL,
  `ville` varchar(15) NOT NULL,
  `tel_fixe` varchar(13) NOT NULL,
  `mobilis` varchar(13) NOT NULL,
  `email` varchar(30) NOT NULL,
  `remarques` text NOT NULL,
  PRIMARY KEY (`code`),
  KEY `nom` (`nom`,`prenom`,`carte_fidele`,`date`),
  KEY `prenom` (`prenom`),
  KEY `carte_fidele` (`carte_fidele`,`date`),
  KEY `date` (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `clients`
--

INSERT INTO `clients` (`code`, `nom`, `prenom`, `carte_fidele`, `date`, `adresse`, `code_postal`, `ville`, `tel_fixe`, `mobilis`, `email`, `remarques`) VALUES
('DRJE02', 'DROUAN', 'Jean', 1, '2017-12-20', '', '', '', '', '', '', ''),
('GIPA01', 'GIELAU', 'Pascal', 1, '2017-12-20', '', '', '', '', '', '', ''),
('PLSY01', 'PLAFONI', 'Sylvie', 0, '2017-12-20', '', '', '', '', '', '', '');

-- --------------------------------------------------------

--
-- Structure de la table `commandes`
--

CREATE TABLE IF NOT EXISTS `commandes` (
  `code` varchar(15) NOT NULL,
  `code_client` varchar(6) NOT NULL,
  `total_ttc` double NOT NULL,
  `code_mode_reglement` int(11) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`code`),
  KEY `code` (`code`,`code_client`,`code_mode_reglement`),
  KEY `code_client` (`code_client`),
  KEY `code_mode_reglement` (`code_mode_reglement`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `commandes`
--

INSERT INTO `commandes` (`code`, `code_client`, `total_ttc`, `code_mode_reglement`, `date`) VALUES
('COM1', 'GIPA01', 0, 2, '2017-12-20'),
('COM2', 'PLSY01', 0, 3, '2017-12-20');

-- --------------------------------------------------------

--
-- Structure de la table `lignes_commandes`
--

CREATE TABLE IF NOT EXISTS `lignes_commandes` (
  `code_commande` varchar(15) NOT NULL,
  `code_article` varchar(6) NOT NULL,
  `quantite` int(11) NOT NULL,
  `prix_unitaire` double NOT NULL,
  `total` double NOT NULL,
  PRIMARY KEY (`code_commande`,`code_article`),
  KEY `code_article` (`code_article`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `lignes_commandes`
--

INSERT INTO `lignes_commandes` (`code_commande`, `code_article`, `quantite`, `prix_unitaire`, `total`) VALUES
('COM1', 'XENO25', 1, 850, 1025.1),
('COM1', 'ZENI77', 1, 500, 630),
('COM2', 'FAMI21', 2, 150, 361);

-- --------------------------------------------------------

--
-- Structure de la table `mode_reglements`
--

CREATE TABLE IF NOT EXISTS `mode_reglements` (
  `code` int(1) NOT NULL,
  `type` varchar(8) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `mode_reglements`
--

INSERT INTO `mode_reglements` (`code`, `type`) VALUES
(1, 'espèce'),
(2, 'chèque'),
(3, 'carte');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `articles`
--
ALTER TABLE `articles`
  ADD CONSTRAINT `articles_ibfk_1` FOREIGN KEY (`code_categorie`) REFERENCES `categories` (`code`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `commandes`
--
ALTER TABLE `commandes`
  ADD CONSTRAINT `commandes_ibfk_2` FOREIGN KEY (`code_mode_reglement`) REFERENCES `mode_reglements` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `commandes_ibfk_1` FOREIGN KEY (`code_client`) REFERENCES `clients` (`code`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `lignes_commandes`
--
ALTER TABLE `lignes_commandes`
  ADD CONSTRAINT `lignes_commandes_ibfk_4` FOREIGN KEY (`code_article`) REFERENCES `articles` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `lignes_commandes_ibfk_3` FOREIGN KEY (`code_commande`) REFERENCES `commandes` (`code`) ON DELETE CASCADE ON UPDATE CASCADE;
