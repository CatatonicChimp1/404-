package org.example.forms;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.example.global.GlobalVariables;
import org.example.model.Alert;
import org.example.model.Test.Test;
import org.example.model.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Array;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private JTable testResultsTable;
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

        // создание таблицы для отображения результатов тестирования
        testResultsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(testResultsTable);
        testResultsPanel.add(scrollPane);
        personalInfoPanel.add(testResultsPanel);

        List<User> usersList =  new ArrayList<>();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request requestAllUsers = new Request.Builder()
                .url("http://localhost:8080/user/all")
                .method("GET", null)
                .build();
        Response responseAllUsers = client.newCall(requestAllUsers).execute();

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

        Gson gsonAlert = new Gson();

        JButton refresh = new JButton("Обновить");
        panelForAdmin.add(refresh);

        refresh.addActionListener(e->{
            OkHttpClient clientAllUsers = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaTypeAllUsers = MediaType.parse("text/plain");
            RequestBody bodyAllUsers = RequestBody.create(mediaTypeAllUsers, "");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/alert/all")
                    .method("GET", null)
                    .build();
            try {
                Response response = clientAllUsers.newCall(request).execute();
                assert response.body() != null;
                java.lang.reflect.Type listAlertType = new TypeToken<List<Alert>>(){}.getType();
                String jsonStr = response.body().string();
                List<Alert> alertList1 = new Gson().fromJson(jsonStr, listAlertType);
                for(int i =0;i<alertList1.size();i++)
                {
                    panelForAdmin.add(new JLabel(String.valueOf(alertList1.get(i).getId())));
                    panelForAdmin.add(new JLabel(alertList1.get(i).getDescription()));
                }
            } catch (IOException ee) {
                throw new RuntimeException(ee);
            }
        });
        JPanel panelForAnalyst = new JPanel();

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
            //Отпраляем запрос на обновление информации
            MediaType mediaTypeUpdate = MediaType.parse("application/json");
            RequestBody bodyUpdate = RequestBody.create(mediaTypeUpdate, " {\n  " +
                    "\"login\" : \""+ GlobalVariables.USER.getLogin() +"\",\n  " +
                    "\"password\" : \""+passwordField.getText()+"\",\n  " +
                    "\"firstName\" : \""+nameField.getText()+"\",\n  " +
                    "\"lastName\" : \""+surnameField.getText()+"\",\n  " +
                    "\"patronymic\" : \""+patronymicField.getText()+"\",\n  " +
                    "\"birthday\" : \""+birthdateField.getText()+"\",\n  " +
                    "\"group\" : "+groupField.getText()+",\n  " +
                    "\"secretQuestion\" : \""+GlobalVariables.USER.getSecretQuestion()+"\",\n  " +
                    "\"answerOnQuestion\" : \""+GlobalVariables.USER.getAnswerOnQuestion()+"\",\n  " +
                    "\"email\" : \""+emailField.getText()+"\",\n  " +
                    "\"numberPhone\" : \""+phoneField.getText()+"\"\n} ");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/user/update?login="+GlobalVariables.USER.getLogin())
                    .method("PUT", bodyUpdate)
                    .addHeader("Content-Type", "application/json")
                    .build();
            try {
                Response response = client.newCall(request).execute();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            //Получаем новую тинформацию
            Request requestFromClient = new Request.Builder()
                    .url("http://localhost:8080/user/login?login="+GlobalVariables.USER.getLogin()+
                            "password="+passwordField.getText())
                    .method("GET",null)
                    .build();
            try {
                Gson gson = new Gson();
                Response response = client.newCall(requestFromClient).execute();
                GlobalVariables.USER = gson.fromJson(response.body().string(), User.class);
                //System.out.println("User");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        deleteAccountButton.addActionListener(e->{
            MediaType mediaTypeDelete = MediaType.parse("text/plain");
            RequestBody bodyDelete = RequestBody.create(mediaTypeDelete, "");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/user/delete?login="+GlobalVariables.USER.getLogin())
                    .method("DELETE", null)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                JOptionPane.showMessageDialog(this, "Профиль удалён, выйдите из системы");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
            setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        Profile personalAccountUI = new Profile();
    }
}
