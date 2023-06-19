package org.example.forms;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import okhttp3.Response;
import org.example.api.MyRequest;
import org.example.global.GlobalVariables;
import org.example.model.Alert;
import org.example.model.Test.Test;
import org.example.model.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Profile extends JFrame {
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
    private JPanel personalInfoPanel;
    private JPanel testResultsPanel;
    private JButton deleteAccountButton;
    private JButton editPersonalInfoButton;

    private JButton buttonToTest;
    private JButton buttonToItogTest;

    private String[] columnNameForAdmin = new String[] {"login", "firstName", "lastName", "patronymic", "birthday",
    "group", "email", "phone", "countItogTest", "blocked"};

    public Profile() throws IOException {
        // настройка окна
        setTitle("Личный кабинет");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // создание панели для личной информации
        personalInfoPanel = new JPanel();

        personalInfoPanel.setBorder(BorderFactory.createTitledBorder("Личная информация"));
        personalInfoPanel.setLayout(new BoxLayout(personalInfoPanel, BoxLayout.Y_AXIS));

        JPanel panelForAdmin = new JPanel();

        panelForAdmin.setBorder(BorderFactory.createTitledBorder("Уведомления"));
        panelForAdmin.setLayout(new BoxLayout(panelForAdmin, BoxLayout.Y_AXIS));

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


        // добавление элементов на панель личной информации
        personalInfoPanel.add(new JLabel("Имя:"));
        personalInfoPanel.add(nameField);
        nameField.setText(GlobalVariables.USER.getFirstName());
        personalInfoPanel.add(new JLabel("Фамилия:"));
        personalInfoPanel.add(surnameField);
        surnameField.setText(GlobalVariables.USER.getLastName());
        personalInfoPanel.add(new JLabel("Отчество"));
        personalInfoPanel.add(patronymicField);
        passwordField.setText(GlobalVariables.USER.getPatronymic());
        personalInfoPanel.add(new JLabel("Email:"));
        personalInfoPanel.add(emailField);
        emailField.setText(GlobalVariables.USER.getEmail());
        personalInfoPanel.add(new JLabel("Пароль"));
        personalInfoPanel.add(passwordField);
        passwordField.setText(GlobalVariables.USER.getPassword());
        personalInfoPanel.add(new JLabel("День рождения"));
        personalInfoPanel.add(birthdateField);
        birthdateField.setText(GlobalVariables.USER.getBirthday());
        personalInfoPanel.add(new JLabel("Номер группы"));
        personalInfoPanel.add(groupField);
        groupField.setText(GlobalVariables.USER.getGroup().toString());
        personalInfoPanel.add(new JLabel("Номер телефона"));
        personalInfoPanel.add(phoneField);
        phoneField.setText(GlobalVariables.USER.getNumberPhone());

        // добавление кнопки для редактирования личной информации
        editPersonalInfoButton = new JButton("Редактировать");
        personalInfoPanel.add(editPersonalInfoButton);

        // добавление кнопки для удаления аккаунта
        deleteAccountButton = new JButton("Удалить аккаунт");
        personalInfoPanel.add(deleteAccountButton);

        buttonToTest = new JButton("Пройти тест");
        personalInfoPanel.add(buttonToTest);

        buttonToItogTest = new JButton("Пройти итоговый тест");
        personalInfoPanel.add(buttonToItogTest);

        // создание панели для результатов тестирования
        testResultsPanel = new JPanel();
        testResultsPanel.setBorder(BorderFactory.createTitledBorder("Результаты тестирования"));
        testResultsPanel.setLayout(new BoxLayout(testResultsPanel, BoxLayout.Y_AXIS));
        JLabel resultLabel = new JLabel(GlobalVariables.USER.getTestIdToGrade().toString());
        testResultsPanel.add(resultLabel);
        // создание таблицы для отображения результатов тестирования
        JScrollPane scrollPane = new JScrollPane();
        testResultsPanel.add(scrollPane);
        personalInfoPanel.add(testResultsPanel);


        List<User> usersList =  new ArrayList<>();

        Response responseAllUsers = MyRequest.requestAllUser();

        String responseStr = responseAllUsers.body().string();

        java.lang.reflect.Type listType = new TypeToken<List<User>>(){}.getType();
        usersList = new Gson().fromJson(responseStr,listType);

        String userToString[][] = new String[usersList.size()][columnNameForAdmin.length];

        for(int i=0;i< usersList.size();i++)
        {
                userToString[i][0] = usersList.get(i).getLogin();
                userToString[i][1] = usersList.get(i).getFirstName();
                userToString[i][2] = usersList.get(i).getLastName();
                userToString[i][3] = usersList.get(i).getPatronymic();
                userToString[i][4] = usersList.get(i).getBirthday();
                userToString[i][5] = String.valueOf(usersList.get(i).getGroup());
                userToString[i][6] = usersList.get(i).getEmail();
                userToString[i][7] = usersList.get(i).getNumberPhone();
                userToString[i][8] = String.valueOf(usersList.get(i).getCountItogTest());
                userToString[i][9] = String.valueOf(usersList.get(i).getBlocked());
        }

        //TableModel tableModel =  new DefaultTableModel(usersList.toArray(new User[][]{}), Arrays.stream(columnNameForAdmin).toArray());

        JTable tableAllUsers = new JTable(userToString,columnNameForAdmin); //TODO доделать
        JScrollPane scrollPaneAdmin = new JScrollPane(tableAllUsers);
        panelForAdmin.add(scrollPaneAdmin);
        panelForAdmin.add(tableAllUsers);

        panelForAdmin.add(tableAllUsers.getTableHeader(), BorderLayout.NORTH);


        JList alertList = new JList();
        alertList.setBorder(new LineBorder(Color.black));
        panelForAdmin.add(alertList);

        Gson gson = new Gson();

        JButton refresh = new JButton("Обновить");
        panelForAdmin.add(refresh);

        JButton export = new JButton("Экспорт данных");
        panelForAdmin.add(export);

        JButton importData = new JButton("Импортировать базу вопросов");
        panelForAdmin.add(importData);

        JLabel loginUpdateLabel = new JLabel("Кому дать права");
        JTextField loginUpdate = new JTextField();
        JLabel newRole = new JLabel("Какой тип роли дать (admin, analyst, user");
        JTextField newRoleField = new JTextField();
        JButton updateRole = new JButton("Обновить права");

        panelForAdmin.add(loginUpdateLabel);
        panelForAdmin.add(loginUpdate);
        panelForAdmin.add(newRole);
        panelForAdmin.add(newRoleField);
        panelForAdmin.add(updateRole);

        updateRole.addActionListener(e->{
            Response response = MyRequest.requestAllUser();
            try {
                User[] users = gson.fromJson(response.body().string(),User[].class);
                for (User user : users) {
                    if (user.getLogin().equals(loginUpdate.getText().toString()) && (
                            newRoleField.getText().toString().equals("admin") ||
                                    newRoleField.getText().toString().equals("user") ||
                            newRoleField.getText().toString().equals("analyst"))) {
                        user.setRole(newRoleField.getText().toString());
                        MyRequest.requestUpdateUser(user);
                        JOptionPane.showMessageDialog(this, "Роль успешно поменяна, попросите" +
                                " пользователя перезайти в систему");
                        break;
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        importData.addActionListener(e->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(this);

            File importFile = fileChooser.getSelectedFile();
            if(importFile.getName().indexOf("csv")>-1) {
                try {
                    CSVReader reader = new CSVReader(new FileReader(importFile));
                    try {
                        String[] headers = reader.readNext();
                        List<Test> testList = new ArrayList<>();
                        String[] line;

                        while ((line = reader.readNext()) != null) {
                            Test test = new Test();
                            test.setId(Integer.parseInt(line[0]));
                            test.setQuestion(line[1]);
                            test.setAnswer(Arrays.asList(line[2].split("\\\\|")));
                            test.setIdTrueAnswer(Integer.parseInt(line[3]));
                            test.setTestGroup(line[4]);
                            testList.add(test);
                        }

                        Test[] tests = testList.toArray(new Test[testList.size()]);
                        reader.close();
                        MyRequest.importTest(tests);
                    } catch (IOException | CsvValidationException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Не поддерживаемый формат файла");
            }
        });

        export.addActionListener(e->{
            try {
                Response response = MyRequest.requestAllTest();
                Test[] testArray = gson.fromJson(response.body().string(), Test[].class);
                List<Test> testList = Arrays.stream(testArray).toList();
                String cvs = "backup" + LocalDate.now() + "|"+ LocalTime.now() + ".csv";
                File cvsFile = new File("/home/iliya/IdeaProjects/intelligentTesting/src/main/resources/backup/"+cvs);
                FileWriter writer = new FileWriter(cvsFile);
                CSVWriter writerCSV = new CSVWriter(writer);
                String[] header = {"id", "question", "answer", "idTrueAnswer", "testGroup"};
                writerCSV.writeNext(header);
                for(Test test : testList) {
                    String[] row = {String.valueOf(test.getId()), test.getQuestion(), String.join("|",
                            test.getAnswer()), String.valueOf(test.getIdTrueAnswer()), test.getTestGroup()};
                    writerCSV.writeNext(row);
                }

                writerCSV.close();
        } catch (IOException ex) {
                throw new RuntimeException(ex);
            }});

        refresh.addActionListener(e->{
            Response response = MyRequest.requestAllAlert();
            assert response.body() != null;
            java.lang.reflect.Type listAlertType = new TypeToken<List<Alert>>(){}.getType();
            String jsonStr = null;
            try {
                jsonStr = response.body().string();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            List<Alert> alertList1 = new Gson().fromJson(jsonStr, listAlertType);
            for (Alert alert : alertList1) {
                panelForAdmin.add(new JLabel(String.valueOf(alert.getId())));
                panelForAdmin.add(new JLabel(alert.getDescription()));
            }
        });
        JPanel panelForAnalyst = new JPanel();
        panelForAnalyst.setLayout(new BoxLayout(panelForAnalyst, BoxLayout.Y_AXIS));
        TextField loginInAnalystPanel = new TextField();
        JButton viewGraphicsAnalystPanel = new JButton("Показать график");
        panelForAnalyst.add(loginInAnalystPanel);
        panelForAnalyst.add(viewGraphicsAnalystPanel);

        viewGraphicsAnalystPanel.addActionListener(e->{
            Response responseAllUser = MyRequest.requestAllUser();
            try {
                User[] allUser = gson.fromJson(responseAllUser.body().string(), User[].class);
                String searchPassword = null;
                for (User value : allUser) {
                    if (value.getLogin().equals(loginInAnalystPanel.getText().toString()))
                        searchPassword = value.getPassword();
                }
                    Response response = MyRequest.getLoginUser(loginInAnalystPanel.getText().toString(),searchPassword);
                try {
                    User user = gson.fromJson(response.body().string(), User.class);
                    List<String> stringList = user.getTestIdToGrade().values().stream().toList();
                    List<Integer> integerList = new ArrayList<>();
                    for (String s : stringList) integerList.add(Integer.parseInt(s));
                    GraphPanel.createAndShowGui(integerList);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane.add("Личная информация",personalInfoPanel);
        jTabbedPane.add("Меню администратора",panelForAdmin);
        jTabbedPane.add("Аналитика", panelForAnalyst);
        // добавление панелей на окно
        //getContentPane().add(personalInfoPanel, BorderLayout.WEST);
        //getContentPane().add(testResultsPanel, BorderLayout.CENTER);
        add(jTabbedPane);


        editPersonalInfoButton.addActionListener(e->{
            User updatingUser = new User(
                    GlobalVariables.USER.getLogin(),
                    passwordField.getText().toString(),
                    nameField.getText().toString(),
                    surnameField.getText().toString(),
                    patronymicField.getText().toString(),
                    birthdateField.getText().toString(),
                    Integer.parseInt(groupField.getText().toString()),
                    GlobalVariables.USER.getSecretQuestion(),
                    GlobalVariables.USER.getAnswerOnQuestion(),
                    emailField.getText().toString(),
                    phoneField.getText().toString(),
                    GlobalVariables.USER.getCountItogTest(),
                    GlobalVariables.USER.getBlocked(),
                    GlobalVariables.USER.getTestIdToGrade(),
                    GlobalVariables.USER.getRole()
            );
            //Отпраляем запрос на обновление информации
            MyRequest.requestUpdateUser(updatingUser);

            //Получаем новую тинформацию
            Response response = MyRequest.getLoginUser(updatingUser.getLogin(), updatingUser.getPassword());
            try {
                assert response.body() != null;
                GlobalVariables.USER = gson.fromJson(response.body().string(), User.class);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        deleteAccountButton.addActionListener(e->{
            MyRequest.deleteUser(GlobalVariables.USER.getLogin());
            JOptionPane.showMessageDialog(this, "Профиль удалён, выйдите из системы");
        });
        if(!GlobalVariables.USER.getRole().equals("admin") && !GlobalVariables.USER.getRole().equals("analyst")) {
            jTabbedPane.remove(panelForAdmin);
            jTabbedPane.remove(panelForAnalyst);

            jTabbedPane.repaint();
            jTabbedPane.revalidate();
        }

        buttonToTest.addActionListener(e->{
            try {
                TestGUI goToTest = new TestGUI();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        buttonToItogTest.addActionListener(e->{
            ItogTest itogTest = new ItogTest();
        });
            setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        Profile personalAccountUI = new Profile();
    }
}
