package org.example.forms;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.example.model.Test.Test;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class TestGUI extends JFrame {
    private JLabel questionLabel;
    private JRadioButton[] answerButtons;
    private JButton nextButton;
    private ButtonGroup buttonGroup;
    private int currentQuestion = 0;
    private String[] questions = {"Вопрос 1", "Вопрос 2", "Вопрос 3", "Вопрос 4", "Вопрос 5"};
    private String[][] answers = {
            {"Ответ 1.1", "Ответ 1.2", "Ответ 1.3", "Ответ 1.4", "Ответ 1.5"},
            {"Ответ 2.1", "Ответ 2.2", "Ответ 2.3", "Ответ 2.4", "Ответ 2.5"},
            {"Ответ 3.1", "Ответ 3.2", "Ответ 3.3", "Ответ 3.4", "Ответ 3.5"},
            {"Ответ 4.1", "Ответ 4.2", "Ответ 4.3", "Ответ 4.4", "Ответ 4.5"},
            {"Ответ 5.1", "Ответ 5.2", "Ответ 5.3", "Ответ 5.4", "Ответ 5.5"}
    };

    public TestGUI() throws IOException {
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://localhost:8080/tests/all")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();


        java.lang.reflect.Type itemsMapType = new TypeToken<Test[]>() {}.getType();
        String responseString = response.body().string();
        Test[] test = new Gson().fromJson(responseString, itemsMapType);
        answerButtons = new JRadioButton[test[0].getAnswer().size()];
        buttonGroup = new ButtonGroup();

        questionLabel = new JLabel(test[currentQuestion].getQuestion());
        panel.add(questionLabel);

        for (int i = 0; i < test[0].getAnswer().size(); i++) {
                answerButtons[i] = new JRadioButton(test[currentQuestion].getAnswer().get(i));
                buttonGroup.add(answerButtons[i]);
                panel.add(answerButtons[i]);
        }

        nextButton = new JButton("Ответить");
        nextButton.addActionListener(e -> {
            // Обработка ответа
            if (currentQuestion < test.length) {
                questionLabel.setText(test[currentQuestion].getQuestion());
                for (int i = 0; i < answerButtons.length; i++) {
                    answerButtons[i].setText(test[currentQuestion].getAnswer().get(i));
                    answerButtons[i].setSelected(false);
                }
            } else {
                // Закончились вопросы
                JOptionPane.showMessageDialog(this, "Тест завершен");
                dispose();
            }
            currentQuestion++;
        });
        panel.add(nextButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new TestGUI();
    }
}
