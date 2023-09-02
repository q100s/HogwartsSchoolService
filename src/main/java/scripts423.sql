select student.name, student.age, student.faculty_id, faculty.name, faculty.color
from student inner join faculty ON student.faculty_id = faculty.id

SELECT * FROM student
JOIN avatar ON student.id = avatar.student_id
