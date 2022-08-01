CREATE TABLE IF NOT EXISTS tournament (
                                          id VARCHAR_IGNORECASE(16) PRIMARY KEY,
                                          name VARCHAR_IGNORECASE(255) NOT NULL,
                                          title VARCHAR(255) NOT NULL DEFAULT 'Default Title',
                                          start TIMESTAMP NOT NULL,
                                          `end` TIMESTAMP NOT NULL,
                                          fields JSON NOT NULL,
                                          teams JSON NOT NULL,
                                          game_rounds JSON NOT NULL,
                                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP()
);