package org.example.forms;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.example.api.MyRequest;
import org.example.global.GlobalVariables;
import org.example.model.Test.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ItogTest extends JFrame {
    private JLabel questionLabel;
    private JRadioButton[] answerButtons;
    private JButton nextButton;
    private ButtonGroup buttonGroup;
    private int currentQuestion = 0;
    ItogTest(){
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        java.lang.reflect.Type itemsMapType = new TypeToken<Test[]>() {}.getType();
        String responseString = null;
        try {
            responseString = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        AtomicReference<Integer> averageAnswer = new AtomicReference<>(0);
        AtomicInteger count = new AtomicInteger();
        nextButton = new JButton("Ответить");
        nextButton.addActionListener(e -> {
            // Обработка ответа
            if (currentQuestion < test.length) {
                questionLabel.setText(test[currentQuestion].getQuestion());
                for (int i = 0; i < answerButtons.length; i++) {
                    answerButtons[i].setText(test[currentQuestion].getAnswer().get(i));
                    answerButtons[i].setSelected(false);
                }
                if(answerButtons[test[currentQuestion].getIdTrueAnswer()].isSelected()){
                    count.getAndIncrement();
                    //System.out.println(count.get());
                }
            } else {
                if(answerButtons[test[currentQuestion-1].getIdTrueAnswer()].isSelected()){
                    count.getAndIncrement();
                    //System.out.println(count.get());
                }
                // Закончились вопросы
                JOptionPane.showMessageDialog(this, "Тест завершен");
                averageAnswer.set((count.get() * 100) / test.length);
                HashMap<String,String> testIdToGrade = GlobalVariables.USER.getTestIdToGrade();
                int testId = new Random().nextInt(0, 1024);
                testIdToGrade.put(String.valueOf(testId), averageAnswer.get().toString());
                GlobalVariables.USER.setTestIdToGrade(testIdToGrade);
                GlobalVariables.USER.setCountItogTest(GlobalVariables.USER.getCountItogTest()+1);
                MyRequest.requestUpdateUser(GlobalVariables.USER);
                dispose();
            }
            currentQuestion++;
        });
        panel.add(nextButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        ItogTest itogTest = new ItogTest();
    }
}
