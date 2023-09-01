ALERT TABLE student
ADD CONSTRAINT age_check CHECK (age >= 16);

ALERT TABLE student
ADD CONSTRAINT name_unique CHECK (name IS NOT NULL),
ADD CONSTRAINT name_unique UNIQUE (name);

ALERT TABLE faculty
ADD CONSTRAINT color_name_unique UNIQUE (name, color);

ALERT TABLE student
ALERT COLUMN age SET DEFAULT 20;