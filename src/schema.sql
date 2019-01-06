DELETE FROM keyword2url WHERE idkeyword > 0;
DELETE FROM keywords WHERE idkeywords > 0;

CREATE TABLE `keyword2url` (
  `idkeyword` int(11) NOT NULL,
  `url` int(11) NOT NULL,
  PRIMARY KEY (`idkeyword`,`url`),
  KEY `urls_idx` (`url`),
  CONSTRAINT `keywords` FOREIGN KEY (`idkeyword`) REFERENCES `keywords` (`idkeywords`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `urls` FOREIGN KEY (`url`) REFERENCES `url` (`idurl`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `keywords` (
  `idkeywords` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`idkeywords`),
  UNIQUE KEY `value_UNIQUE` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=189889 DEFAULT CHARSET=utf8;


CREATE TABLE `url` (
  `idurl` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(1000) NOT NULL,
  PRIMARY KEY (`idurl`),
  UNIQUE KEY `url_UNIQUE` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=1073 DEFAULT CHARSET=utf8;

CREATE INDEX indx_keywords
ON keywords (value);