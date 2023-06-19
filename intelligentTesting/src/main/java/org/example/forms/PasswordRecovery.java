package org.example.forms;

import com.google.gson.Gson;
import okhttp3.Response;
import org.example.api.MyRequest;
import org.example.model.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.SecureRandom;

public class PasswordRecovery extends JFrame {
    PasswordRecovery(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800,600));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JFrame frame = new JFrame("Восстановление пароля");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        JLabel labelLogin = new JLabel("Введите логин");
        JTextField loginField = new JTextField();
        JLabel labelQuestion = new JLabel("Введите секретный вопрос");
        JTextField secretQuestionField = new JTextField();
        JLabel labelAnswer = new JLabel("Введите секретный ответ");
        JTextField secretAnswerField = new JTextField();
        JButton sendButton = new JButton("Отправить ответ");

        panel.add(labelLogin);
        panel.add(loginField);
        panel.add(labelQuestion);
        panel.add(secretQuestionField);
        panel.add(labelAnswer);
        panel.add(secretAnswerField);
        panel.add(sendButton);

        sendButton.addActionListener(e->{
            Gson gson = new Gson();
            Response response = MyRequest.requestAllUser();
            try {
                User[] users = gson.fromJson(response.body().string(), User[].class);
                for (User user : users) {
                    if (user.getLogin().equals(loginField.getText().toString())) {
                        if (user.getSecretQuestion().equals(secretQuestionField.getText().toString()) &&
                                user.getAnswerOnQuestion().equals(secretAnswerField.getText().toString())) {
                            SecureRandom random = new SecureRandom();
                            String newPassword = random.ints(48, 122).filter(
                                    c -> Character.isAlphabetic(c) || Character.isDigit(c)
                            ).limit(10).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
                            user.setPassword(newPassword);
                            user.setBlocked(false);
                            MyRequest.requestUpdateUser(user);
                            JOptionPane.showMessageDialog(frame, "Вы восстановили доступ. Ваш новый пароль : " + newPassword);
                        }
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        PasswordRecovery passwordRecovery = new PasswordRecovery();
    }
}
