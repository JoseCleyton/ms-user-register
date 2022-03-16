package com.user.register.services;

import com.user.register.configs.RabbitMqConfig;
import com.user.register.models.EmailModel;
import com.user.register.models.UserModel;
import com.user.register.repositories.UserRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    AmqpTemplate amqpTemplate;

    public UserModel save(UserModel userModel) {
        var userSaved = this.userRepository.save(userModel);
        if (userSaved != null) {
            var emailModel = new EmailModel();
            emailModel.setEmailTo(userModel.getEmail());
            emailModel.setEmailFrom(emailFrom);
            emailModel.setSubject("Cadastro Realizado com Sucesso");
            emailModel.setText("Olá, " + userModel.getName() + ", agradecemos pelo seu cadastro.");
            this.amqpTemplate.convertAndSend(RabbitMqConfig.NAME_EXCHANGE, RabbitMqConfig.ROUTING_KEY,
                    emailModel);
        }
        return userSaved;
    }

    public UserModel saveByApi(UserModel userModel) {
        var userSaved = this.userRepository.save(userModel);
        if (userSaved != null) {
            var emailModel = new EmailModel();
            emailModel.setEmailTo(userModel.getEmail());
            emailModel.setEmailFrom(emailFrom);
            emailModel.setSubject("Cadastro Realizado com Sucesso");
            emailModel.setText("Olá, " + userModel.getName() + ", agradecemos pelo seu cadastro.");

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<EmailModel> request = new HttpEntity<>(emailModel);
            var emailSaved =
                    restTemplate
                            .postForObject("http://localhost:8081/sending-email", request, EmailModel.class);

        }
        return userSaved;
    }

    public Page<UserModel> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }


}
