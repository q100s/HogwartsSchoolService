package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptions.DataNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Transactional
public class AvatarService {
    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Value("${students.avatars.dir.path}")
    private Path avatarsDir;

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Avatar getById(Long id) {
        logger.info("getById method has been invoked");
        logger.debug("Requesting info for avatar with id: {}", id);
        logger.error("There is no avatar with id: " + id);
        return avatarRepository.findById(id).orElseThrow(DataNotFoundException::new);
    }

    public List<Avatar> getAll(Integer pageNumber, Integer pageSize) {
        logger.info("getAll method has been invoked");
        logger.debug("Requesting all the avatars");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    public Long save(Long studentId, MultipartFile multipartFile) throws IOException {
        String absolutePath = saveToDisk(studentId, multipartFile);
        Avatar avatar = saveToDataBase(studentId, multipartFile, absolutePath);

        return avatar.getId();
    }

    private String saveToDisk(Long studentId, MultipartFile multipartFile) throws IOException {
        logger.info("saveToDisk method has been invoked");
        Files.createDirectories(avatarsDir);
        String originalFilename = multipartFile.getOriginalFilename();
        int dotIndex = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(dotIndex);
        String fileMame = studentId + extension;
        String absolutePath = avatarsDir.toAbsolutePath() + "/" + fileMame;
        FileOutputStream fos = new FileOutputStream(absolutePath);
        multipartFile.getInputStream().transferTo(fos);
        fos.close();
        return absolutePath;
    }

    private Avatar saveToDataBase(Long studentId, MultipartFile multipartFile, String absolutePath) throws IOException {
        logger.info("saveToDataBase method has been invoked");
        Student studentReference = studentRepository.getReferenceById(studentId);
        Avatar avatar = avatarRepository.findByStudent(studentReference).orElse(new Avatar());

        avatar.setStudent(studentReference);
        avatar.setMediaType(multipartFile.getContentType());
        avatar.setFilePath(absolutePath);
        avatar.setFileSize(multipartFile.getSize());
        avatar.setData(multipartFile.getBytes());
        avatarRepository.save(avatar);
        return avatar;
    }

}
