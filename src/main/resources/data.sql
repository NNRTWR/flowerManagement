INSERT IGNORE INTO `member` (`id`, `name`, `password`, `role`, `username`)
VALUES
(UNHEX(REPLACE(UUID(), '-', '')), 'สมชาย ชาวสวน', '$2a$12$fXgzsQbCww4gefIfJmDqRuQmwKibAtTFregXfS5KhcyoIs9NDPo6i', 'GARDENER', 'gardener'),
(UNHEX(REPLACE(UUID(), '-', '')), 'คนขาย นะจ้ะ', '$2a$12$OjvSYMO6xfissB7UKEnWE.Dp8g67/b0XEkK9hzIkopDrfzkDXnXn6', 'SELLER', 'seller'),
(UNHEX(REPLACE(UUID(), '-', '')), 'คุณแหม่ม เจ้าของสวน', '$2a$12$h/dg4dWPeFRG8GA9Bbrin.qwpvG9Xxog0pvvd/oBK3wpCPiMLuCSm', 'OWNER', 'owner');

