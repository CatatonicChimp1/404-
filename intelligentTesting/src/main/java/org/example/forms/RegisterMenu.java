package org.example.forms;

import com.google.gson.Gson;
import okhttp3.Response;
import org.example.api.MyRequest;
import org.example.global.GlobalVariables;
import org.example.model.User;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RegisterMenu {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField patronymicField;
    private JTextField birthdateField;
    private JTextField groupField;
    private JTextField secretQuestionField;
    private JTextField secretAnswerField;
    private JTextField emailField;
    private JTextField phoneField;
    private JButton registerButton;
    private JButton uploadPhotoButton;

    private JTextField loginFieldPlaneLogin;
    private JPasswordField passwordFieldPlaneLogin;
    private JButton singInPlaneLogin;

    private User newUser;
    private File icon;

    RegisterMenu() {
        JFrame frame = new JFrame();
        frame.setTitle("Интеликтуальное тестирование");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        loginField = new JTextField();
        passwordField = new JPasswordField();
        nameField = new JTextField();
        surnameField = new JTextField();
        patronymicField = new JTextField();
        birthdateField = new JTextField();
        groupField = new JTextField();
        secretQuestionField = new JTextField();
        secretAnswerField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        registerButton = new JButton("Зарегистрироваться");
        uploadPhotoButton = new JButton("Загрузить фото");

        JPanel registrationPlane = new JPanel();
        registrationPlane.setLayout(new BoxLayout(registrationPlane, BoxLayout.Y_AXIS));
        registrationPlane.add(new JLabel("Логин:"));
        registrationPlane.add(loginField);
        registrationPlane.add(new JLabel("Пароль:"));
        registrationPlane.add(passwordField);
        registrationPlane.add(new JLabel("Имя:"));
        registrationPlane.add(nameField);
        registrationPlane.add(new JLabel("Фамилия:"));
        registrationPlane.add(surnameField);
        registrationPlane.add(new JLabel("Отчество:"));
        registrationPlane.add(patronymicField);
        registrationPlane.add(new JLabel("Дата рождения:"));
        registrationPlane.add(birthdateField);
        registrationPlane.add(new JLabel("Группа:"));
        registrationPlane.add(groupField);
        registrationPlane.add(new JLabel("Секретный вопрос:"));
        registrationPlane.add(secretQuestionField);
        registrationPlane.add(new JLabel("Ответ на вопрос:"));
        registrationPlane.add(secretAnswerField);
        registrationPlane.add(new JLabel("Адрес электронной почты:"));
        registrationPlane.add(emailField);
        registrationPlane.add(new JLabel("Номер телефона:"));
        registrationPlane.add(phoneField);
        registrationPlane.add(uploadPhotoButton);
        registrationPlane.add(registerButton);

        registerButton.addActionListener(e -> {
            // Получение значений полей формы
            String login = loginField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String name = nameField.getText();
            String surname = surnameField.getText();
            String patronymic = patronymicField.getText();
            String birthdate = birthdateField.getText();
            int group = Integer.parseInt(groupField.getText());
            String secretQuestion = secretQuestionField.getText();
            String secretAnswer = secretAnswerField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            // Дополнительная обработка и проверка значений полей:
            // ...
            if (password.length() < 4) {
                JOptionPane.showMessageDialog(frame, "Пароль должен быть больше 4-х знаков");
            } else {
                newUser = new User(
                        login,
                        password,
                        name,
                        surname,
                        patronymic,
                        birthdate,
                        group,
                        secretQuestion,
                        secretAnswer,
                        email,
                        phone,
                        0,
                        false,
                        new HashMap<String, String>(),
                        "user"
                );
                if (icon != null) {
                    //Отпраляем на сервер всё
                    //Тестовый запрос, работает
                    MyRequest.requestAddUser(newUser);
                } else {
                    //Отправляем на сервер только пользователя, icon = null
                }
                System.out.println("newUser");
            }

            // Регистрация пользователя в системе
            // ...
        });

        AtomicInteger countErrorLogin = new AtomicInteger();

        uploadPhotoButton.addActionListener(e -> {
            // Открытие диалогового окна для выбора файла с фотографией
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(frame);

            // Получение выбранного файла
            // ...
            icon = fileChooser.getSelectedFile();
            System.out.println(icon);
        });

        loginFieldPlaneLogin = new JTextField();
        passwordFieldPlaneLogin = new JPasswordField();
        singInPlaneLogin = new JButton("Войти");
        JButton restorePlaneLogin = new JButton("Восстановить пароль");


        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));

        panelLogin.add(new JLabel("Логин"));
        panelLogin.add(loginFieldPlaneLogin);

        panelLogin.add(new JLabel("Пароль"));
        panelLogin.add(passwordFieldPlaneLogin);

        panelLogin.add(restorePlaneLogin);

        panelLogin.add(singInPlaneLogin);


        tabbedPane.add("Регистрация", registrationPlane);
        tabbedPane.add("Логин", panelLogin);
        frame.add(tabbedPane);
        frame.setVisible(true);

        restorePlaneLogin.addActionListener(e -> {
            PasswordRecovery passwordRecovery = new PasswordRecovery();
        });

        singInPlaneLogin.addActionListener(e -> {
            Response response = MyRequest.getLoginUser(loginFieldPlaneLogin.getText(), passwordFieldPlaneLogin.getText());
            Gson gson = new Gson();
            try {
                assert response.body() != null;
                GlobalVariables.USER = gson.fromJson(response.body().string(), User.class);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (GlobalVariables.USER == null || GlobalVariables.USER.getBlocked()) {
                JOptionPane.showMessageDialog(frame, "Не верно введён логин или пароль, осталось попыток " +
                        (5 - countErrorLogin.get()));
                countErrorLogin.getAndIncrement();
                if (countErrorLogin.get() > 5) {
                    //Блокирование пользователя по login
                    MyRequest.blockedUser(loginFieldPlaneLogin.getText());
                    MyRequest.requestAddAlert(loginFieldPlaneLogin.getText());
                }
            } else if (countErrorLogin.get() > 6 || GlobalVariables.USER.getBlocked()) {
                countErrorLogin.set(0);
                JOptionPane.showMessageDialog(frame, "Вы заблокированы!");
            } else {
                try {
                    Profile profile = new Profile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            //System.out.println("User");
    });

}

    public static void main(String[] args) {
        new RegisterMenu();
    }
}
