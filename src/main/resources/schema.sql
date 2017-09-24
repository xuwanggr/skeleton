CREATE TABLE receipts (
  id INT UNSIGNED AUTO_INCREMENT,
  uploaded TIME DEFAULT CURRENT_TIME(),
  merchant VARCHAR(255),
  amount DECIMAL(12,2),
  receipt_type INT UNSIGNED,
  PRIMARY KEY (id)
);

CREATE TABLE tags (
  receiptID INT UNSIGNED,
  tagName VARCHAR(255),
);

CREATE TABLE receiptTags (
  receiptID INT UNSIGNED,
  merchant VARCHAR(255),
  amount DECIMAL(12,2),
  tagName VARCHAR(255),
);